<template>
  <div class="card table-container">
    <table>
      <thead>
        <tr>
          <th style="width: 80px">Photo</th>
          <th>Name</th>
          <th>Contact</th>
          <th>Address</th>
          <th style="width: 120px; text-align: right">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="students.length === 0">
          <td
            colspan="5"
            style="text-align: center; padding: 3rem; color: #64748b"
          >
            No students found. Click "Add Student" to create one.
          </td>
        </tr>
        <tr v-for="student in students" :key="student.id">
          <td>
            <img
              v-if="student.picture && !imageLoadErrors[student.id]"
              :src="student.picture"
              alt="Profile"
              class="avatar"
              @error="handleImageError(student.id)"
            />
            <div v-else class="avatar avatar-placeholder">
              {{ getInitials(student.fName, student.lName) }}
            </div>
          </td>
          <td>
            <div style="font-weight: 500">
              {{ student.fName }} {{ student.lName }}
            </div>
          </td>
          <td>{{ student.contact }}</td>
          <td>{{ student.address }}</td>
          <td style="text-align: right">
            <div class="flex gap-2 justify-end">
              <button
                @click="$emit('view', student)"
                class="btn btn-icon"
                title="View Details"
                :disabled="isStudentLoading(student.id)"
              >
                👁️
              </button>
              <button
                @click="$emit('edit', student)"
                class="btn btn-icon"
                title="Edit"
                :disabled="isStudentLoading(student.id)"
              >
                <span
                  v-if="
                    props.loadingAction === 'edit' &&
                    props.loadingStudentId === student.id
                  "
                  class="spinner"
                ></span>
                <span v-else>✏️</span>
              </button>
              <button
                @click="$emit('delete', student)"
                class="btn btn-icon"
                style="color: var(--color-danger)"
                title="Delete"
                :disabled="isStudentLoading(student.id)"
              >
                <span
                  v-if="
                    props.loadingAction === 'delete' &&
                    props.loadingStudentId === student.id
                  "
                  class="spinner"
                ></span>
                <span v-else>🗑️</span>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const imageLoadErrors = ref({});

const props = defineProps({
  students: {
    type: Array,
    required: true,
  },
  loadingStudentId: {
    type: Number,
    default: null,
  },
  loadingAction: {
    type: String,
    default: null,
  },
});

defineEmits(['view', 'edit', 'delete']);

function handleImageError(id) {
  imageLoadErrors.value[id] = true;
}

function getInitials(fName, lName) {
  return (fName.charAt(0) + lName.charAt(0)).toUpperCase();
}

function isStudentLoading(studentId) {
  return props.loadingStudentId === studentId;
}
</script>

<style scoped>
.btn-icon:hover {
  background-color: #f1f5f9;
}
</style>
