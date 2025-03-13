// API endpoints
const API_BASE_URL = '/api';

// User authentication
async function login(email, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/users/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });
        const data = await response.json();
        if (response.ok) {
            localStorage.setItem('user', JSON.stringify(data));
            window.location.href = '/dashboard.html';
        } else {
            showError(data.message);
        }
    } catch (error) {
        showError('An error occurred during login');
    }
}

// Paper submission
async function submitPaper(paperData) {
    try {
        const response = await fetch(`${API_BASE_URL}/papers`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`,
            },
            body: JSON.stringify(paperData),
        });
        const data = await response.json();
        if (response.ok) {
            showSuccess('Paper submitted successfully');
            loadPapers();
        } else {
            showError(data.message);
        }
    } catch (error) {
        showError('An error occurred while submitting the paper');
    }
}

// Load papers
async function loadPapers() {
    try {
        const response = await fetch(`${API_BASE_URL}/papers`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`,
            },
        });
        const papers = await response.json();
        displayPapers(papers);
    } catch (error) {
        showError('An error occurred while loading papers');
    }
}

// Display papers in the UI
function displayPapers(papers) {
    const paperList = document.querySelector('.paper-list');
    if (!paperList) return;

    paperList.innerHTML = papers.map(paper => `
        <div class="paper-card">
            <h3 class="paper-title">${paper.title}</h3>
            <div class="paper-meta">
                <span>Author: ${paper.author.name}</span>
                <span>Submitted: ${new Date(paper.submissionDate).toLocaleDateString()}</span>
            </div>
            <p>${paper.abstract_}</p>
            <div class="mt-3">
                <button class="btn btn-primary" onclick="viewPaper(${paper.paperId})">View Details</button>
            </div>
        </div>
    `).join('');
}

// Add comment
async function addComment(paperId, content) {
    try {
        const response = await fetch(`${API_BASE_URL}/papers/${paperId}/comments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`,
            },
            body: JSON.stringify({ content }),
        });
        const data = await response.json();
        if (response.ok) {
            showSuccess('Comment added successfully');
            loadComments(paperId);
        } else {
            showError(data.message);
        }
    } catch (error) {
        showError('An error occurred while adding the comment');
    }
}

// Utility functions
function getToken() {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return user.token;
}

function showError(message) {
    const errorDiv = document.getElementById('error-message');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 3000);
    }
}

function showSuccess(message) {
    const successDiv = document.getElementById('success-message');
    if (successDiv) {
        successDiv.textContent = message;
        successDiv.style.display = 'block';
        setTimeout(() => {
            successDiv.style.display = 'none';
        }, 3000);
    }
}

// Event listeners
document.addEventListener('DOMContentLoaded', () => {
    // Login form
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            login(email, password);
        });
    }

    // Paper submission form
    const paperForm = document.getElementById('paper-form');
    if (paperForm) {
        paperForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const paperData = {
                title: document.getElementById('title').value,
                abstract_: document.getElementById('abstract').value,
                content: document.getElementById('content').value,
            };
            submitPaper(paperData);
        });
    }

    // Load papers on dashboard
    if (window.location.pathname === '/dashboard.html') {
        loadPapers();
    }
}); 