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
        console.log('Login response:', data); // Debugging
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
        console.log(`${API_BASE_URL}/papers/all`);
        console.log(getToken());
        const response = await fetch(`${API_BASE_URL}/papers/all`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`,
            },
        });
        const result = await response.json();
        console.log(result);
        if (result.data) {
            displayPapers(result.data);
        } else {
            showError('No papers found');
        }
    } catch (error) {
        showError('An error occurred while loading papers');
    }
}

async function loadConferences() {
    try {
        console.log(`${API_BASE_URL}/conferences/all`);
        const response = await fetch(`${API_BASE_URL}/conferences/all`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`,
            },
        });
        const result = await response.json();
        console.log(result);
        if (result.data) {
            displayConferences(result.data);
        } else {
            showError('No conferences found');
        }
    } catch (error) {
        showError('An error occurred while loading conferences');
    }
}

async function searchPapers(topic) {
    try {
        const response = await fetch(`${API_BASE_URL}/papers/search?topic=${encodeURIComponent(topic)}`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`,
            },
        });
        const result = await response.json();
        if (response.data) {
            displayPapers(result.data);
        } else {
            showError('No papers found for the given topic');
        }
    } catch (error) {
        showError('An error occurred while searching for papers');
    }
}

// Display papers in the UI
function displayPapers(papers) {
    const paperList = document.querySelector('.paper-list');
    if (!paperList) {
        console.error('Paper list element not found');
        return;
    }

    if (!papers || papers.length === 0) {
        paperList.innerHTML = '<p>No papers found.</p>';
        return;
    }

    // Limit to a maximum of 2 papers
    const limitedPapers = papers.slice(0, 10);
    
    paperList.innerHTML = limitedPapers.map(paper => `
        <div class="paper-card">
            <h3 class="paper-title">${paper.title || 'Untitled'}</h3>
            <div class="paper-meta">
                <span><strong>Author:</strong> ${paper.authors|| 'Unknown'}</span>
                <span><strong>Submitted:</strong> ${paper.published_date}</span>
            </div>
           <p>${(paper.summary || 'No abstract available').slice(0, 100)}...</p>
            <div class="mt-3">
                <span>${paper.id}</span>
                <button class="btn btn-primary" onclick="viewPaper('${encodeURIComponent(paper.id || 0)}')">View Details</button>
                <button class="btn btn-secondary" onclick="toggleLike('${encodeURIComponent(paper.id)}')">Like</button>
            </div>
        </div>
    `).join('');
}

function filterPapers(papers) {
    const paperList = document.querySelector('.paper-list');
    if (!paperList) {
        console.error('Paper list element not found');
        return;
    }

    if (!papers || papers.length === 0) {
        paperList.innerHTML = '<p>No papers found.</p>';
        return;
    }

    const titleFilter = document.getElementById('filter-title').value.toLowerCase();
    const authorFilter = document.getElementById('filter-author').value.toLowerCase();
    const statusFilter = document.getElementById('filter-status').value;

    // Filter papers based on user input
    const filteredPapers = papers.filter(paper => {
        const titleMatch = paper.title.toLowerCase().includes(titleFilter);
        const authorMatch = paper.authors.toLowerCase().includes(authorFilter);
        const statusMatch = !statusFilter || paper.status.toLowerCase() === statusFilter.toLowerCase();

        return titleMatch && authorMatch && statusMatch;
    });

    // Limit to a maximum of 10 papers
    const limitedPapers = filteredPapers.slice(0, 10);

    // Render the filtered papers
    paperList.innerHTML = limitedPapers.map(paper => `
        <div class="paper-card">
            <h3 class="paper-title">${paper.title || 'Untitled'}</h3>
            <div class="paper-meta">
                <span><strong>Author:</strong> ${paper.authors || 'Unknown'}</span>
                <span><strong>Submitted:</strong> ${paper.published_date || 'N/A'}</span>
            </div>
            <p>${(paper.summary || 'No abstract available').slice(0, 100)}...</p>
            <div class="mt-3">
                <button class="btn btn-primary" onclick="viewPaper('${encodeURIComponent(paper.id || 0)}')">View Details</button>
                <button class="btn btn-secondary" onclick="toggleLike('${encodeURIComponent(paper.id)}')">Like</button>
            </div>
        </div>
    `).join('');
}

async function toggleLike(paperId) {
    

    console.log("Toggling like for paperId:", paperId); // Debugging log
    
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const user_id = String(user.id);
    console.log("Toggling like for userID:", user);
    console.log("Type of user:", typeof user_id);
    if (!user || !user.id) {
        showError('User not logged in');
        return;
    }
    console.log("Toggling like for userID:", user_id); // Debugging log
    try {
        const response = await fetch(`${API_BASE_URL}/papers/${paperId}/like`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'userId':user_id,
                'Authorization': `Bearer ${getToken()}`,
            },
        });

        const result = await response.json();
        console.log("API Response:", result); // Debugging log

        if (response.ok && result.success) {
            showSuccess(result.message || 'Paper liked/unliked successfully');
            // Optionally reload papers or update the UI dynamically
            loadPapers(); // Refresh the paper list
        } else {
            showError(result.error || 'Failed to like/unlike the paper');
        }
    } catch (error) {
        console.error("Error in toggleLike:", error); // Debugging log
        showError('An error occurred while liking the paper');
    }
}

// Function to load liked papers
async function loadLikedPapers() {
    try {
        const response = await fetch(`${API_BASE_URL}/papers/liked`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`,
            },
        });
        const result = await response.json();
        if (response.ok && result.data) {
            displayLikedPapers(result.data);
        } else {
            showError('No liked papers found');
        }
    } catch (error) {
        showError('An error occurred while loading liked papers');
    }
}

// Function to display liked papers
function displayLikedPapers(papers) {
    const likedPaperList = document.querySelector('.liked-paper-list');
    if (!likedPaperList) {
        console.error('Liked paper list element not found');
        return;
    }

    if (!papers || papers.length === 0) {
        likedPaperList.innerHTML = '<p>No liked papers found.</p>';
        return;
    }

    likedPaperList.innerHTML = papers.map(paper => `
        <div class="paper-card">
            <h3 class="paper-title">${paper.title || 'Untitled'}</h3>
            <div class="paper-meta">
                <span><strong>Author:</strong> ${paper.authors || 'Unknown'}</span>
                <span><strong>Submitted:</strong> ${paper.published_date || 'N/A'}</span>
            </div>
            <p>${(paper.summary || 'No abstract available').slice(0, 100)}...</p>
        </div>
    `).join('');
}

function viewPaper(paperId) {
    if (paperId) {
        // Redirect to the new page with paperId as query parameter
        window.location.href = `/paper-details.html?id=${paperId}`;
    } else {
        alert("Invalid paper ID");
    }
}

function displayConferences(conferences) {
    const conferenceList = document.querySelector('.conference-list');
    if (!conferenceList) {
        console.error('Conference list element not found');
        return;
    }

    if (!conferences || conferences.length === 0) {
        conferenceList.innerHTML = '<p>No conferences found.</p>';
        return;
    }

    // Limit to a maximum of 2 conferences
    const limitedConferences = conferences.slice(0, 2);

    conferenceList.innerHTML = limitedConferences.map(conference => `
        <div class="conference-card">
            <h3 class="conference-title">${conference.title || 'Untitled'}</h3>
            <div class="conference-meta">
                <span><strong>Date:</strong> ${conference.date ? new Date(conference.date).toLocaleDateString() : 'N/A'}</span>
                <span><strong>Location:</strong> ${conference.location || 'Unknown'}</span>
            </div>
            <p>${conference.description || 'No description available'}</p>
            <div class="mt-3">
                <button class="btn btn-primary" onclick="viewConference(${conference.conferenceId || 0})">View Details</button>
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
        console.log('Dashboard loaded');
        loadPapers();
        loadConferences();
    }
}); 