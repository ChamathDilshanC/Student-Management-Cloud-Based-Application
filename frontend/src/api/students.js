import axios from 'axios';

// Vite proxies /api to http://localhost:8080
const apiClient = axios.create({
  baseURL: '/api/v1/students',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
  getAll() {
    return apiClient.get('');
  },

  getById(id) {
    return apiClient.get(`/${id}`);
  },

  create(formData) {
    return apiClient.post('', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  update(id, formData) {
    return apiClient.put(`/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  delete(id) {
    return apiClient.delete(`/${id}`);
  },
};
