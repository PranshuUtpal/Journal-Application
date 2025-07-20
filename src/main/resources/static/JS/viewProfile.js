document.addEventListener('DOMContentLoaded', function() {
    const jwtToken = localStorage.getItem('jwtToken');

    if (jwtToken) {
        fetchUserProfile(jwtToken);  // Load user's profile details
        setUserProfile(jwtToken);
    } else {
        console.error('JWT Token not found.');
        window.location.href = '/signin';  // Redirect to login if no token
    }

    // Delete profile button and popup logic
        const deleteProfileButton = document.getElementById('deleteProfileButton');
        const deleteConfirmationPopup = document.getElementById('deleteConfirmationPopup');
        const confirmDeleteButton = document.getElementById('confirmDeleteButton');
        const cancelDeleteButton = document.getElementById('cancelDeleteButton');

        // Show the confirmation popup when "Delete Profile" is clicked
        deleteProfileButton.addEventListener('click', function() {
            deleteConfirmationPopup.style.display = 'flex';
        });

        // If "No" is clicked, hide the popup
        cancelDeleteButton.addEventListener('click', function() {
            deleteConfirmationPopup.style.display = 'none';
        });

        // If "Yes" is clicked, delete the profile
        confirmDeleteButton.addEventListener('click', function() {
            deleteUserProfile(jwtToken);
        });
});

// Function to fetch the user's profile details
function fetchUserProfile(token) {
    fetch('/user/userDetails', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch user profile');
        }
        return response.json();
    })
    .then(user => {
        displayUserProfile(user);
    })
    .catch(error => {
        console.error('Error fetching profile details:', error);
        window.location.href = '/signin';  // Redirect to login if unauthorized
    });
}

// Function to display user profile details on the page
function displayUserProfile(user) {
    document.getElementById('profileImage').src = user.profileImage || '/default-profile.png';
    document.getElementById('fullName').textContent = user.fullName;
    document.getElementById('userName').textContent = user.userName;
    document.getElementById('email').textContent = user.email;
    document.getElementById('roles').textContent = user.roles.join(', ');
    document.getElementById('sentimentAnalysis').textContent = user.sentimentAnalysis ? 'True' : 'False';
    document.getElementById('journalEntries').textContent = user.journalEntries.join(', ');
}



// Function to delete user profile
function deleteUserProfile(token) {
    fetch('/user/deleteUser', {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            alert('Profile deleted successfully.');
            localStorage.removeItem('jwtToken');  // Clear the JWT token
            window.location.href = '/signin';  // Redirect to login after deletion
        } else {
            throw new Error('Failed to delete profile');
        }
    })
    .catch(error => {
        console.error('Error deleting profile:', error);
    });
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
//const jwtToken = localStorage.getItem('jwtToken');
// Example call after retrieving the token
//if (jwtToken) {
//    setUserProfile(jwtToken);
//} else {
//    console.error('JWT Token not found.');
//}
