<template>
  <div class="toast-container">
    <TransitionGroup name="toast">
      <div
        v-for="time in toasts"
        :key="time.id"
        class="toast"
        :class="time.type"
      >
        <div class="icon">
          <span v-if="time.type === 'success'">✅</span>
          <span v-else-if="time.type === 'error'">❌</span>
          <span v-else>ℹ️</span>
        </div>
        <div class="content">
          <div class="title">{{ time.title }}</div>
          <div class="message">{{ time.message }}</div>
        </div>
        <button class="close-btn" @click="remove(time.id)">×</button>
      </div>
    </TransitionGroup>
  </div>
</template>

<script setup>
import { ref, defineExpose } from 'vue';

const toasts = ref([]);
let idCounter = 0;

function add(notification) {
  const id = idCounter++;
  const toast = {
    id,
    type: 'info', // success, error, info
    title: '',
    message: '',
    duration: 3000,
    ...notification,
  };

  toasts.value.push(toast);

  if (toast.duration > 0) {
    setTimeout(() => remove(id), toast.duration);
  }
}

function remove(id) {
  const index = toasts.value.findIndex(t => t.id === id);
  if (index !== -1) {
    toasts.value.splice(index, 1);
  }
}

defineExpose({ add });
</script>

<style scoped>
.toast-container {
  position: fixed;
  top: 1rem;
  right: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  z-index: 9999;
  pointer-events: none; /* Let clicks pass through container */
}

.toast {
  pointer-events: auto; /* Re-enable clicks on toasts */
  background: white;
  border-radius: 0.5rem;
  box-shadow:
    0 4px 6px -1px rgb(0 0 0 / 0.1),
    0 2px 4px -2px rgb(0 0 0 / 0.1);
  display: flex;
  align-items: flex-start;
  padding: 1rem;
  min-width: 300px;
  max-width: 400px;
  border-left: 4px solid #3b82f6; /* Default blue for info */
  position: relative;
  overflow: hidden;
}

.toast.success {
  border-left-color: #10b981;
}
.toast.error {
  border-left-color: #ef4444;
}

.icon {
  margin-right: 0.75rem;
  font-size: 1.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content {
  flex: 1;
}

.title {
  font-weight: 600;
  font-size: 0.875rem;
  margin-bottom: 0.25rem;
  color: #1e293b;
}

.message {
  font-size: 0.875rem;
  color: #64748b;
  line-height: 1.4;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #94a3b8;
  cursor: pointer;
  padding: 0 0.5rem;
  margin-left: 0.5rem;
  line-height: 1;
}
.close-btn:hover {
  color: #1e293b;
}

/* Transitions */
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>
