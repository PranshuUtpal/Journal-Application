// Function to handle posting journal
document.getElementById('postJournalBtn').addEventListener('click', function() {
    const jwtToken = localStorage.getItem('jwtToken'); // Retrieve the JWT token from localStorage

    if (!jwtToken) {
        alert('User is not authenticated. Redirecting to login.');
        window.location.href = '/login'; // Redirect to login if token is missing
        return;
    }

    const title = document.getElementById('title').value.trim();
    const sentiment = document.getElementById('sentiment').value;
    const content = document.getElementById('content').value.trim();

    if (title && sentiment && content) {
        const journalEntry = {
            title: title,
            sentiment: sentiment,
            content: content
        };

        // Send a POST request to backend with the JWT token
        fetch('/journal', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}` // Include JWT token in Authorization header
            },
            body: JSON.stringify(journalEntry)
        })
        .then(response => {
            if (response.ok) {
                alert('Journal added successfully!');
                //window.location.href = '/homepage'; // Redirect to dashboard after success
            } else {
                alert('Failed to add journal. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    } else {
        alert('Please fill in all fields.');
    }
});
