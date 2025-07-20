// Function to fetch greeting using JWT token
function fetchGreeting(token) {
    fetch('/user/greeting', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById('greetingMessage').textContent = data;
    })
    .catch(error => {
        console.error('Error fetching greeting:', error);
    });
}

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

// Example usage: Fetch greeting and set profile using the JWT token
const jwtToken = localStorage.getItem('jwtToken');

if (jwtToken) {
    fetchGreeting(jwtToken);
    setUserProfile(jwtToken);
} else {
    console.error('JWT Token not found.');
}
