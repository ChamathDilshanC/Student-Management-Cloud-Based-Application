<template>
  <div v-if="isOpen" class="modal-overlay">
    <div class="modal card">
      <div class="flex justify-between items-center mb-6">
        <h2>{{ isEdit ? 'Edit Student' : 'New Student' }}</h2>
        <button class="btn btn-icon close-btn" @click="$emit('cancel')">
          ✕
        </button>
      </div>

      <form @submit.prevent="handleSubmit">
        <!-- Photo Upload Area -->
        <div
          class="upload-area"
          @dragover.prevent="isDragging = true"
          @dragleave.prevent="isDragging = false"
          @drop.prevent="handleDrop"
          @click="$refs.fileInput.click()"
        >
          <input
            type="file"
            ref="fileInput"
            class="hidden"
            accept="image/*"
            @change="handleFileSelect"
          />

          <div v-if="previewUrl" class="preview-container">
            <img :src="previewUrl" class="preview-image" />
            <div class="overlay">Change Photo</div>
          </div>
          <div v-else class="placeholder">
            <div class="icon">📷</div>
            <p><strong>Click to upload</strong> or drag and drop</p>
            <p class="text-sm text-muted">SVG, PNG, JPG (max. 10MB)</p>
          </div>
        </div>

        <div class="grid-cols-2">
          <div class="form-group">
            <label class="form-label">First Name</label>
            <input
              v-model="form.fName"
              type="text"
              class="form-control"
              required
              placeholder="e.g. John"
            />
          </div>
          <div class="form-group">
            <label class="form-label">Last Name</label>
            <input
              v-model="form.lName"
              type="text"
              class="form-control"
              required
              placeholder="e.g. Doe"
            />
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">Contact Number</label>
          <input
            v-model="form.contact"
            type="tel"
            class="form-control"
            required
            placeholder="e.g. 0771234567"
          />
        </div>

        <div class="form-group">
          <label class="form-label">Address</label>
          <input
            v-model="form.address"
            type="text"
            class="form-control"
            required
            placeholder="e.g. 123 Main St, Colombo"
          />
        </div>

        <div class="flex justify-end gap-4 mt-8">
          <button
            type="button"
            class="btn btn-secondary"
            @click="$emit('cancel')"
          >
            Cancel
          </button>
          <button
            type="submit"
            class="btn btn-primary"
            :disabled="props.isSubmitting"
          >
            {{ props.isSubmitting ? 'Saving...' : 'Save Student' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue';

const props = defineProps({
  isOpen: Boolean,
  student: Object,
  isSubmitting: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(['cancel', 'save']);

const isDragging = ref(false);
const previewUrl = ref(null);
const selectedFile = ref(null);
const fileInput = ref(null);

const form = reactive({
  fName: '',
  lName: '',
  contact: '',
  address: '',
});

const isEdit = computed(() => !!props.student);

watch(
  () => props.student,
  newVal => {
    if (newVal) {
      Object.assign(form, newVal);
      previewUrl.value = newVal.picture || null;
    } else {
      // Reset form
      Object.assign(form, { fName: '', lName: '', contact: '', address: '' });
      previewUrl.value = null;
    }
    selectedFile.value = null;
  },
  { immediate: true }
);

function handleFileSelect(event) {
  const file = event.target.files[0];
  if (file) processFile(file);
}

function handleDrop(event) {
  isDragging.value = false;
  const file = event.dataTransfer.files[0];
  if (file && file.type.startsWith('image/')) {
    processFile(file);
  }
}

function processFile(file) {
  selectedFile.value = file;
  const reader = new FileReader();
  reader.onload = e => (previewUrl.value = e.target.result);
  reader.readAsDataURL(file);
}

function handleSubmit() {
  if (props.isSubmitting) return; // Prevent double submission
  
  const formData = new FormData();
  formData.append('fName', form.fName);
  formData.append('lName', form.lName);
  formData.append('contact', form.contact);
  formData.append('address', form.address);

  if (selectedFile.value) {
    formData.append('profileImage', selectedFile.value);
  }

  emit('save', formData);
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center; /* Center horizontally */
  align-items: center; /* Center vertically */
  z-index: 100;
  backdrop-filter: blur(2px);
}

.modal {
  width: 100%;
  max-width: 500px;
  /* Removed height: 100% and border resets for slide-over */
  max-height: 90vh; /* Prevent overflowing screen on small devices */
  overflow-y: auto;
  animation: popIn 0.2s ease-out;
  display: flex;
  flex-direction: column;
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

.hidden {
  display: none;
}

.upload-area {
  border: 2px dashed var(--color-border);
  border-radius: var(--radius-lg);
  padding: 2rem;
  text-align: center;
  cursor: pointer;
  margin-bottom: 2rem;
  transition: all 0.2s;
  background: #f8fafc;
  position: relative;
  overflow: hidden;
}

.upload-area:hover,
.upload-area.dragging {
  border-color: var(--color-primary);
  background: #eff6ff;
}

.preview-container {
  width: 100%;
  height: 200px;
  position: relative;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.2s;
  font-weight: 500;
}
.preview-container:hover .overlay {
  opacity: 1;
}

.icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  opacity: 0.5;
}

.grid-cols-2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.mb-6 {
  margin-bottom: 1.5rem;
}
.mt-8 {
  margin-top: 2rem;
}
.close-btn {
  font-size: 1.25rem;
}
</style>
