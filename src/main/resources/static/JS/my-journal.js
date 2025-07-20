
const jwtToken = localStorage.getItem('jwtToken');
//console.log('JWT Token:', jwtToken); // Debug the token
// Fetch latest 10 journals and display them on page load

document.addEventListener('DOMContentLoaded', function() {
    fetchLatestJournals();
});

// Function to fetch the latest 10 journals
function fetchLatestJournals() {
    fetch('/journal/myjournal', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}` // Send the JWT token in the Authorization header
        }
    })
    .then(response => response.json())
    .then(journals => {
        displayJournals(journals); // Call a function to display the fetched journals
    })
    .catch(error => console.error('Error fetching latest journals:', error));
}

// Function to display a list of journals
function displayJournals(journals) {
    const journalList = document.getElementById('journalList');
    journalList.innerHTML = ''; // Clear previous entries

    if (journals && journals.length > 0) {
        journals.forEach(journal => {
            const journalEntry = document.createElement('div');
            journalEntry.classList.add('journal-entry');
            journalEntry.innerHTML = `<h3>${journal.title}</h3><p2>Story by ${journal.author} • ${journal.date}</p2><p>${journal.content}</p>`;
            journalList.appendChild(journalEntry);
        });
    } else {
        journalList.innerHTML = '<p>No journals found</p>';
    }
}

// Event listener for the search button
document.getElementById('searchBtn').addEventListener('click', function() {
    const searchQuery = document.getElementById('searchInput').value.trim();
    const journalList = document.getElementById('journalList');
    journalList.innerHTML = ''; // Clear previous results

    if (searchQuery) {
        // Search functionality
        fetch('/journal/myjournal', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwtToken}` // Include JWT token in Authorization header
                    //'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (response.ok) {
                    return response.json(); // Convert the response to JSON
                } else if (response.status === 401) {
                    throw new Error('Unauthorized: Invalid or expired token');
                } else {
                    throw new Error('Failed to fetch journals');
                }
            })
            .then(journals => {
                // Check if search query is not empty
                if (searchQuery) {
                    // Filter based on search query
                    const filteredJournals = journals.filter(journal =>
                        journal.title.toLowerCase().includes(searchQuery.toLowerCase())
                    );

                    // Sort journals by ascending order of ID
                    filteredJournals.sort((a, b) => a.id - b.id);

                    // Display the filtered journals
                    if (filteredJournals.length > 0) {
                        filteredJournals.forEach(journal => {
                            const journalEntry = document.createElement('div');
                            journalEntry.classList.add('journal-entry');
                            journalEntry.innerHTML = `<h3>${journal.title}</h4><p2>Story by ${journal.author} • ${journal.date}</p2><p>${journal.content}</p>`;
                            journalList.appendChild(journalEntry);
                        });
                    } else {
                        journalList.innerHTML = '<p>No journals found</p>';
                    }
                }
            })
            .catch(error => {
                console.error('Error fetching journals:', error);
                journalList.innerHTML = `<p>${error.message}</p>`;
            });
    } else {
        // If search box is cleared, fetch and display the latest 10 journals
        fetchLatestJournals();
    }
});

// Event listener for clearing the search input
document.getElementById('searchInput').addEventListener('input', function() {
    if (!this.value.trim()) {
        fetchLatestJournals(); // If search input is cleared, fetch and display the latest 10 journals
    }
});

// Profile Section Toggle for Dropdown
document.getElementById('profileSection').addEventListener('click', function() {
    const dropdown = document.getElementById('profileDropdown');
    dropdown.style.display = dropdown.style.display === 'none' || dropdown.style.display === '' ? 'block' : 'none';
    event.stopPropagation(); // Prevent the event from bubbling up to the document
});

// Close the dropdown if clicking outside of the profile section
document.addEventListener('click', function(event) {
    // If the click is outside the profile section and dropdown, close the dropdown
    if (!profileSection.contains(event.target)) {
        profileDropdown.style.display = 'none';
    }
});


// Function to set profile icon (image or initials)
function setUserProfile(token) {
    try {
        const profileIcon = document.getElementById('profileIcon');

        // Decode the JWT token
        const decodedToken = jwt_decode(token);

        // Check if the username is present in the 'sub' claim (usually, the username is stored here)
        const username = decodedToken.sub || 'Guest';

        // Set profile image or initials
        if (decodedToken.profileImage) {
            profileIcon.innerHTML = `<img src="${decodedToken.profileImage}" alt="Profile Image" />`;
        } else {
            // If no image, display the user's initials
            const initials = username.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase();
            profileIcon.textContent = initials;
        }
    } catch (error) {
        console.error('Error decoding JWT or setting user profile:', error);
    }
}
//const jwtToken = localStorage.getItem('jwtToken');
// Example call after retrieving the token
if (jwtToken) {
    setUserProfile(jwtToken);
} else {
    console.error('JWT Token not found.');
}
tUserProfile(currentUser);