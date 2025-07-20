document.addEventListener('DOMContentLoaded', function() {
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
       setUserProfile(jwtToken);
    } else {
       console.error('JWT Token not found.');
       window.location.href = '/signin';  // Redirect to login if no token
    }
    // Fetch the current user details and pre-fill the form
    function fetchUserDetails() {
        fetch('/user/userDetails', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            // Fill form fields with user details
            document.getElementById('username').value = data.userName;
            document.getElementById('email').value = data.email;
            document.getElementById('fullName').value = data.fullName;
            document.getElementById('profileImage').value = data.profileImage;
            if (data.profileImage) {
                document.getElementById('profileImagePreview').src = data.profileImage;
                document.getElementById('profileImagePreview').style.display = 'block';
            }
            document.getElementById('sentimentAnalysis').checked = data.sentimentAnalysis;
        })
        .catch(error => {
            console.error('Error fetching user details:', error);
        });
    }

    fetchUserDetails();

    // Handle file input and display the uploaded image as a preview
    const profileImageInput = document.getElementById('profileImageInput');
    const profileImagePreview = document.getElementById('profileImagePreview');

    profileImageInput.addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                profileImagePreview.src = e.target.result;
                profileImagePreview.style.display = 'block';
            };
            reader.readAsDataURL(file); // Convert image file to base64 URL
        }
    });

    // Handle form submission for updating profile
    document.getElementById('updateProfileForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const updatedUser = {
            userName: document.getElementById('username').value,
            email: document.getElementById('email').value,
            fullName: document.getElementById('fullName').value,
            password: document.getElementById('password').value,
            profileImage: profileImagePreview.src, // Base64 image URL
            sentimentAnalysis: document.getElementById('sentimentAnalysis').checked
        };

        // Send the updated user data via PUT request
        fetch('/user/updateUser', {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUser)
        })
        .then(response => {
            if (response.ok) {
                alert('Profile updated successfully!');
                window.location.href = '/journalapp/view-profile'; // Redirect to profile page
            } else {
                alert('Failed to update profile.');
            }
        })
        .catch(error => {
            console.error('Error updating profile:', error);
        });
    });
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
