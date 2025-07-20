

/*document.getElementById('searchBtn').addEventListener('click', function() {
    const searchQuery = document.getElementById('searchInput').value.trim();
    const journalList = document.getElementById('journalList');

    // Clear previous results
    journalList.innerHTML = '';

    // Retrieve the JWT token (assumed to be stored in localStorage or passed in model attribute)
    //const jwtToken = localStorage.getItem('jwtToken'); // Or retrieve from the model if passed via Thymeleaf

    // Ensure JWT token exists
    if (!jwtToken) {
        journalList.innerHTML = '<p>Unauthorized: JWT token not found</p>';
        return;
    }

    // Fetch journals from the secured backend endpoint with JWT token
    fetch('/journal/getall', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`, // Include JWT token in Authorization header
            'Content-Type': 'application/json'
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
                    journalEntry.innerHTML = `<h3>${journal.title}</h3><p>${journal.content}</p>`;
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
}); */


const jwtToken = localStorage.getItem('jwtToken');
console.log('JWT Token found:', jwtToken);
//console.log('JWT Token:', jwtToken); // Debug the token
// Fetch latest 10 journals and display them on page load

document.addEventListener('DOMContentLoaded', function() {
    fetchLatestJournals();
});

// Function to fetch the latest 10 journals
function fetchLatestJournals() {
    fetch('/journal/latest', {
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
            const formattedContent = journal.content.replace(/(?:\r\n|\r|\n)/g, '<br>');
            const journalEntry = document.createElement('div');
            journalEntry.classList.add('journal-entry');
            // Assuming 'journal.date' is stored as a string, you can format it:
            const formattedDate = new Date(journal.date).toLocaleDateString();
            journalEntry.innerHTML = `<h3>${journal.title}</h3><p2>Story by ${journal.author} • ${formattedDate}</p2><p>${formattedContent}</p>`;
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
        fetch('/journal/getall', {
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


/*// Function to Set Profile Initials or Image
function setUserProfile(user) {
    const profileIcon = document.getElementById('profileIcon');
    const initials = user.name.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase();
    profileIcon.textContent = initials;
}

// Example User Object (This will be set from your backend after login)
const currentUser = {
    name: "Pranshu Priyadarshi"
};

// Set the profile icon for the logged-in user
setUserProfile(currentUser);*/

// Assuming the JWT token is passed as a variable or stored in localStorage
//const jwtToken = /* You can retrieve this token from model attribute or localStorage */

// Function to set profile icon (image or initials)
function setUserProfile(token) {
    try {
        const profileIcon = document.getElementById('profileIcon');

        // Decode the JWT token
        const decodedToken = jwt_decode(token);
        // Log the decoded token to inspect the contents
        console.log('Decoded JWT Token:', decodedToken);

        // Check if the username is present in the 'sub' claim (usually, the username is stored here)
        const username = decodedToken.sub || 'Guest';

        // Log the profileImage for debugging
        const profileImage = decodedToken.profileImage;
        console.log('Profile Image:', profileImage);
        // Set profile image or initials
        if (decodedToken.profileImage) {
            //profileIcon.innerHTML = `<img src="${decodedToken.profileImage}" alt="Profile Image" />`;
            profileIcon.innerHTML = `<img src="${profileImage}" alt="Profile Image" style="width: 35px; height: 35px; border-radius: 50%;" />`;
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


// Handle the click event for Add Journal
/*document.getElementById('addJournalLink').addEventListener('click', function(event) {
   event.preventDefault(); // Prevent the default link behavior

   //const jwtToken = localStorage.getItem('jwtToken');

   if (!jwtToken) {
       alert('User is not authenticated. Redirecting to login.');
       window.location.href = '/login';
       return;
   }

   // Make a GET request to the secured /journal/add-journal endpoint with the JWT token
   fetch('/journal/add-journal', {
      method: 'GET',
      headers: {
         'Authorization': `Bearer ${jwtToken}`, // Include JWT token in Authorization header
         'Content-Type': 'application/json'
      }

   })
   .then(response => {
      if (response.status === 200) {
      // If the request is successful, redirect the user to the Add Journal page
      //window.location.href = '/journal/add-journal';
      console.log(jwtToken);
      console.log(response);
      window.location.replace(response.url);
      } else if (response.status === 401) {
         alert('Unauthorized: Invalid or expired token. Redirecting to login.');
         window.location.href = '/login'; // Redirect to login if token is invalid
      } else {
         alert('Failed to load the Add Journal page. Please try again.');
      }
   })
   .catch(error => {
      console.error('Error fetching Add Journal page:', error);
      alert('An error occurred. Please try again later.');
   });

});*/

/*document.addEventListener('DOMContentLoaded', function() {
    // Ensure the DOM is fully loaded before running the script

    // Find the Add Journal link element by its ID
    const addJournalLink = document.getElementById('addJournalLink');

    // Ensure that the element exists before adding the event listener
    if (addJournalLink) {
        addJournalLink.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent the default link behavior

            // Retrieve the JWT token
            const jwtToken = localStorage.getItem('jwtToken');

            if (!jwtToken) {
                alert('User is not authenticated. Redirecting to login.');
                window.location.href = '/login'; // Redirect to login if token is missing
                return;
            }

            // Make a GET request to the secured /journal/add-journal endpoint with the JWT token
            fetch('/journal/add-journal', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`, // Include JWT token in Authorization header
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (response.status === 200) {
                    // If the request is successful, redirect the user to the Add Journal page
                    window.location.href = '/journal/add-journal';
                } else if (response.status === 401) {
                    alert('Unauthorized: Invalid or expired token. Redirecting to login.');
                    window.location.href = '/login'; // Redirect to login if token is invalid
                } else {
                    alert('Failed to load the Add Journal page. Please try again.');
                }
            })
            .catch(error => {
                console.error('Error fetching Add Journal page:', error);
                alert('An error occurred. Please try again later.');
            });
        });
    } else {
        console.error('Element with ID "addJournalLink" not found.');
    }
});*/

// Handle the click event for Add Journal
/*document.getElementById('addJournalLink').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default link behavior

    if (!jwtToken) {
        alert('User is not authenticated. Redirecting to login.');
        window.location.href = '/login';
        return;
    }

    // Redirect to the secured /journal/add-journal endpoint with the JWT token
    // We're redirecting the user to the page, not making a fetch request
    //window.location.href = '/journal/add-journal?token=' + encodeURIComponent(jwtToken);
    window.location.href = `/journal/add-journal?token=${jwtToken}`;
    //console.log(jwtToken);
});*/

// Handle the click event for Add Journal
/*document.getElementById('addJournalLink').addEventListener('click', function(event) {
   event.preventDefault(); // Prevent the default link behavior

   if (!jwtToken) {
       alert('User is not authenticated. Redirecting to login.');
       window.location.href = '/login';
       return;
   }

   // Make a GET request to the secured /journal/add-journal endpoint with the JWT token
   fetch('/journal/add-journal', {
      method: 'GET',
      headers: {
         'Authorization': `Bearer ${jwtToken}`, // Include JWT token in Authorization header
         'Content-Type': 'application/json'
      }
   })
   .then(response => {
      // Check if the response is okay (status code 200-299)
      if (response.ok) {
         // If the request is successful, redirect the user to the Add Journal page using response.url
         return response.url;
      } else if (response.status === 401) {
         alert('Unauthorized: Invalid or expired token. Redirecting to login.');
         window.location.href = '/login'; // Redirect to login if token is invalid
      } else {
         alert('Failed to load the Add Journal page. Please try again.');
      }
   })
   .then(redirectUrl => {
      if (redirectUrl) {
         // Redirect to the add-journal page if response.url is valid
         //document.location.href = redirectUrl;
         th:href="@{redirectUrl}";
      }
   })
   .catch(error => {
      console.error('Error fetching Add Journal page:', error);
      alert('An error occurred. Please try again later.');
   });
}); */

//Response {type: 'basic', url: 'http://localhost:8080/journal/add-journal', redirected: false, status: 200, ok: true, …}