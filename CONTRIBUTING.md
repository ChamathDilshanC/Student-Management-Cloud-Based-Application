# Contributing to Student Management Application

First off, thank you for considering contributing to this project! 🎉

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the issue list as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples**
- **Describe the behavior you observed and what you expected**
- **Include screenshots if relevant**
- **Include your environment details** (OS, Java version, Node version, etc.)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. Create an issue and provide:

- **Use a clear and descriptive title**
- **Provide a detailed description of the suggested enhancement**
- **Explain why this enhancement would be useful**
- **List some examples of how it would be used**

### Pull Requests

1. Fork the repo and create your branch from `main`
2. If you've added code that should be tested, add tests
3. Ensure the test suite passes
4. Make sure your code follows the existing style
5. Write a clear commit message
6. Include relevant issue numbers in the PR description

### Development Setup

1. Clone your fork
2. Install dependencies:

   ```bash
   # Backend
   ./mvnw clean install

   # Frontend
   cd frontend
   npm install
   ```

3. Set up your local database and configuration files
4. Create a new branch: `git checkout -b feature/my-new-feature`
5. Make your changes and commit: `git commit -am 'Add some feature'`
6. Push to your fork: `git push origin feature/my-new-feature`
7. Submit a pull request

### Code Style

**Java/Spring Boot:**

- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods small and focused

**Vue.js/JavaScript:**

- Use ES6+ features
- Follow Vue.js style guide
- Use meaningful component and variable names
- Add comments for complex logic

### Commit Messages

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters
- Reference issues and pull requests liberally after the first line

### Testing

- Write tests for new features
- Ensure all tests pass before submitting PR
- Aim for good test coverage

## Questions?

Feel free to open an issue with your question or reach out to the maintainers.

Thank you! 🙏
