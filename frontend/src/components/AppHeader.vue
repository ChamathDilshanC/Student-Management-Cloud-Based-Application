<template>
  <header class="app-header">
    <div class="container-wide flex items-center justify-between">
      <div class="logo flex items-center gap-4">
        <div class="logo-icon">🎓</div>
        <h1>Student Management</h1>
      </div>
      <div>
        <!-- future user menu -->
      </div>
    </div>
  </header>
</template>

<style scoped>
.app-header {
  background: white;
  border-bottom: 1px solid var(--color-border);
  padding: 1rem 0;
  box-shadow: var(--shadow-sm);
}

.logo-icon {
  font-size: 1.5rem;
  background: #eff6ff;
  padding: 0.5rem;
  border-radius: var(--radius-md);
}

h1 {
  font-size: 1.25rem;
  color: var(--color-text-main);
}
</style>
