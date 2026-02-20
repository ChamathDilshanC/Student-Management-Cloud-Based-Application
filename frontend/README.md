# Student Management Frontend

A modern, responsive Single Page Application (SPA) built with **Vue 3** and **Vite** for the Student Management System.

## Features

- **Dashboard**: View all students with profile pictures.
- **Create**: Add new students with drag-and-drop image upload.
- **Update**: Edit student details and update profile pictures.
- **Delete**: Remove students with confirmation dialog.
- **Cloud Integration**: Profile pictures are uploaded directly to Google Cloud Storage (via backend).

## Tech Stack

- **Framework**: Vue.js 3 (Composition API)
- **Build Tool**: Vite
- **Profiling**: Vanilla CSS (Custom Design System)
- **HTTP Client**: Axios

## Project Setup

### Prerequisites

- Node.js (v16+)
- Backend running on `http://localhost:8080`

### Installation

```sh
cd frontend
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

The application will be available at `http://localhost:5173`.

### Build for Production

```sh
npm run build
```

## API Configuration

The frontend is configured to proxy requests to the backend:

- **Vite Proxy**: `/api` requests are forwarded to `http://localhost:8080`
- **Configuration**: See `vite.config.js`
