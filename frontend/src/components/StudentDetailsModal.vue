<template>
  <div v-if="isOpen" class="modal-backdrop" @click="$emit('close')">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h3>Student Details</h3>
        <button class="close-btn" @click="$emit('close')">×</button>
      </div>

      <div class="modal-body">
        <div class="profile-section">
          <img
            v-if="student.picture && !imageError"
            :src="student.picture"
            alt="Profile"
            class="profile-large"
            @error="imageError = true"
          />
          <div v-else class="avatar-placeholder-large">
            {{ getInitials(student.fName, student.lName) }}
          </div>
        </div>

        <div class="details-grid">
          <div class="detail-item">
            <label>First Name</label>
            <p>{{ student.fName }}</p>
          </div>
          <div class="detail-item">
            <label>Last Name</label>
            <p>{{ student.lName }}</p>
          </div>
          <div class="detail-item">
            <label>Contact No</label>
            <p>{{ student.contact }}</p>
          </div>
          <div class="detail-item full-width">
            <label>Address</label>
            <p>{{ student.address }}</p>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-secondary" @click="$emit('close')">Close</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
  isOpen: Boolean,
  student: Object,
});

defineEmits(['close']);

const imageError = ref(false);

// Reset error state when student changes
watch(
  () => props.student,
  () => {
    imageError.value = false;
  }
);

function getInitials(fName, lName) {
  return (fName?.charAt(0) + lName?.charAt(0))?.toUpperCase() || '';
}
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
  backdrop-filter: blur(4px);
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: var(--radius-lg);
  width: 100%;
  max-width: 500px;
  box-shadow: var(--shadow-md);
  animation: popIn 0.2s ease-out;
}

@keyframes popIn {
  from {
    transform: scale(0.95);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: var(--color-text-muted);
}

.profile-section {
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}

.profile-large {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 4px solid var(--color-bg);
  box-shadow: var(--shadow-sm);
}

.avatar-placeholder-large {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: #e2e8f0;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.5rem;
  font-weight: 600;
  color: var(--color-text-muted);
  border: 4px solid var(--color-bg);
}

.details-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.detail-item label {
  display: block;
  font-size: 0.75rem;
  text-transform: uppercase;
  color: var(--color-text-muted);
  font-weight: 600;
  margin-bottom: 0.25rem;
}

.detail-item p {
  font-size: 1rem;
  color: var(--color-text-main);
  font-weight: 500;
}

.full-width {
  grid-column: span 2;
}

.modal-footer {
  margin-top: 2rem;
  display: flex;
  justify-content: flex-end;
}
</style>
