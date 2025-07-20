const jwtToken = localStorage.getItem('jwtToken');

// Fetch all journals when the page loads and populate the dropdown
window.onload = function() {
    fetchJournals();
};
// Fetch all journals
function fetchJournals() {
    fetch('/journal/myjournal', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => populateJournalDropdown(data))
    .then(console.log(data))
    .catch(error => console.error('Error fetching journal entries:', error));
}

// Populate dropdown with journal titles
function populateJournalDropdown(journals) {
    const dropdown = document.getElementById('journalDropdown');
    dropdown.innerHTML = '';  // Clear existing options

    journals.forEach(journal => {
        console.log('Journal Object:', journal);  // Debugging: Check full journal object
        const option = document.createElement('option');
        option.value = journal.id.$oid || journal.id;  // Use journal.id or journal.id.$oid if using MongoDB ObjectId
        option.textContent = journal.title;  // Journal Title
        dropdown.appendChild(option);
    });
}

// Load selected journal data into the form
function loadJournalData() {
    const myId = document.getElementById('journalDropdown').value;
    console.log('Selected Journal ID:', myId);  // Debugging: Check the journal ID being passed

    fetch(`/journal/journalEntries/${myId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch journal data');
        }
        return response.json();
    })
    .then(journal => {
        document.getElementById('title').value = journal.title;
        document.getElementById('sentiment').value = journal.sentiment;
        document.getElementById('content').value = journal.content;
    })
    .catch(error => console.error('Error fetching journal data:', error));
}

// Submit updated journal data
function submitUpdate(event) {
    event.preventDefault();  // Prevent form from reloading the page

    const myId = document.getElementById('journalDropdown').value;
    console.log('Submitting Update for Journal ID:', myId);  // Debugging: Check the journal ID being passed

    const updatedJournal = {
        title: document.getElementById('title').value,
        sentiment: document.getElementById('sentiment').value,
        content: document.getElementById('content').value
    };

    fetch(`/journal/update/${myId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${jwtToken}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedJournal)
    })
    .then(response => {
        if (response.ok) {
            alert('Journal updated successfully!');
        } else {
            alert('Failed to update journal');
        }
    })
    .catch(error => console.error('Error updating journal:', error));
}


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

//Example call after retrieving the token
if (jwtToken) {
    setUserProfile(jwtToken);
} else {
    console.error('JWT Token not found.');
}