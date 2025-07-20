const wrapper = document.querySelector('.wrapper')
const registerLink = document.querySelector('.register-link')
const loginLink = document.querySelector('.login-link')

// Show the Sign Up page by default when the page loads
/*document.addEventListener('DOMContentLoaded', () => {
    // Automatically display the Sign Up form on page load
    wrapper.classList.add('active'); // Add 'active' class to show the Sign Up form first
});  */
registerLink.onclick = () => {
    wrapper.classList.add('active')
}

loginLink.onclick = () => {
    wrapper.classList.remove('active')
}

// JavaScript for enabling and disabling submit button
//document.getElementById('terms').addEventListener('change', function() {
//    document.getElementById('submit-btn').disabled = !this.checked;
//});

// Wait for the DOM to load
document.addEventListener('DOMContentLoaded', () => {
    // Get references to the checkbox and submit button
    const termsCheckbox = document.getElementById('terms');
    const submitButton = document.getElementById('submit-btn');

    // Function to toggle the submit button based on checkbox status
    function toggleSubmitButton() {
        submitButton.disabled = !termsCheckbox.checked;
    }

    // Initialize the submit button state
    toggleSubmitButton();

    // Add event listener to the checkbox to toggle button state on change
    termsCheckbox.addEventListener('change', toggleSubmitButton);
});