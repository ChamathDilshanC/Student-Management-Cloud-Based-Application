<template>
  <div class="page-wrapper">
    <!-- Morning Haze Background -->
    <div
      class="background-haze"
      :style="{
        backgroundImage: `
          radial-gradient(circle at 50% 100%, rgba(253, 224, 71, 0.4) 0%, transparent 60%),
          radial-gradient(circle at 50% 100%, rgba(251, 191, 36, 0.4) 0%, transparent 70%),
          radial-gradient(circle at 50% 100%, rgba(244, 114, 182, 0.5) 0%, transparent 80%)
        `,
      }"
    ></div>

    <!-- Content -->
    <div class="content-wrapper">
      <AppHeader />

      <main class="container-wide mt-8">
        <div class="flex justify-between items-center mb-6">
          <div>
            <h2 style="margin-bottom: 0.25rem">All Students</h2>
            <p class="text-sm text-muted">Manage your student records</p>
          </div>
          <button class="btn btn-primary" @click="openCreateModal">
            + Add Student
          </button>
        </div>

        <StudentTable
          :students="students"
          :loading-student-id="loadingStudentId"
          :loading-action="loadingAction"
          @view="openViewModal"
          @edit="openEditModal"
          @delete="confirmDelete"
        />

        <StudentForm
          :is-open="showForm"
          :student="selectedStudent"
          :is-submitting="isSubmittingForm"
          @cancel="closeForm"
          @save="handleSave"
        />

        <StudentDetailsModal
          :is-open="showView"
          :student="selectedStudent"
          @close="closeViewModal"
        />

        <ConfirmDialog
          :is-open="showDelete"
          title="Delete Student?"
          message="Are you sure you want to delete this student? This action cannot be undone."
          @cancel="showDelete = false"
          @confirm="handleDelete"
        />

        <ToastNotification ref="toast" />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import AppHeader from '../components/AppHeader.vue';
import StudentTable from '../components/StudentTable.vue';
import StudentForm from '../components/StudentForm.vue';
import StudentDetailsModal from '../components/StudentDetailsModal.vue';
import ConfirmDialog from '../components/ConfirmDialog.vue';
import ToastNotification from '../components/ToastNotification.vue';
import api from '../api/students';

const students = ref([]);
const showForm = ref(false);
const showView = ref(false);
const showDelete = ref(false);
const selectedStudent = ref(null);
const toast = ref(null);
const loadingStudentId = ref(null);
const loadingAction = ref(null);
const isSubmittingForm = ref(false);

onMounted(fetchStudents);

async function fetchStudents() {
  try {
    const res = await api.getAll();
    students.value = res.data;
  } catch (err) {
    console.error('Failed to fetch students', err);
    toast.value?.add({
      type: 'error',
      title: 'Connection Error',
      message: 'Failed to load students. Is the backend running?',
    });
  }
}

function openCreateModal() {
  selectedStudent.value = null;
  showForm.value = true;
}

function openEditModal(student) {
  if (loadingStudentId.value) return; // Prevent if already loading
  selectedStudent.value = student;
  showForm.value = true;
}

function openViewModal(student) {
  selectedStudent.value = student;
  showView.value = true;
}

function closeViewModal() {
  showView.value = false;
  selectedStudent.value = null;
}

function closeForm() {
  showForm.value = false;
  selectedStudent.value = null;
  isSubmittingForm.value = false;
}

async function handleSave(formData) {
  if (isSubmittingForm.value) return; // Prevent double submission
  
  isSubmittingForm.value = true;
  
  try {
    if (!selectedStudent.value) {
      // Creating new student
      await api.create(formData);
      toast.value?.add({
        type: 'success',
        title: 'Created Successfully',
        message: 'New student has been added.',
      });
      await fetchStudents();
      closeForm();
    } else {
      // Updating existing student
      loadingStudentId.value = selectedStudent.value.id;
      loadingAction.value = 'edit';
      
      await api.update(selectedStudent.value.id, formData);
      toast.value?.add({
        type: 'success',
        title: 'Updated Successfully',
        message: 'Student details have been updated.',
      });
      await fetchStudents();
      closeForm();
      
      loadingStudentId.value = null;
      loadingAction.value = null;
    }
  } catch (err) {
    console.error('Save failed', err);
    toast.value?.add({
      type: 'error',
      title: 'Save Failed',
      message: err.response?.data?.message || err.message,
    });
  } finally {
    isSubmittingForm.value = false;
  }
}

function confirmDelete(student) {
  if (loadingStudentId.value) return; // Prevent if already loading
  selectedStudent.value = student;
  showDelete.value = true;
}

async function handleDelete() {
  if (!selectedStudent.value) return;

  loadingStudentId.value = selectedStudent.value.id;
  loadingAction.value = 'delete';

  try {
    await api.delete(selectedStudent.value.id);
    await fetchStudents();
    showDelete.value = false;
    selectedStudent.value = null;
    toast.value?.add({
      type: 'success',
      title: 'Deleted Successfully',
      message: 'Student has been removed.',
    });
  } catch (err) {
    console.error('Delete failed', err);
    toast.value?.add({
      type: 'error',
      title: 'Delete Failed',
      message: 'Could not delete the student.',
    });
  } finally {
    loadingStudentId.value = null;
    loadingAction.value = null;
  }
}
</script>

<style scoped>
.page-wrapper {
  min-height: 100vh;
  width: 100%;
  background-color: white; /* bg-white */
  position: relative;
}

.background-haze {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0; /* inset-0 */
  z-index: 0;
}

.content-wrapper {
  position: relative;
  z-index: 10;
}

.mb-6 {
  margin-bottom: 1.5rem;
}
</style>
