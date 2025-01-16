// Wait until the DOM is fully loaded before attaching event listeners
document.addEventListener("DOMContentLoaded", () => {
    // fetch the input fields
    const fullnameField = document.getElementById("fullname");
    const emailField = document.getElementById("email");
    const passwordField = document.getElementById("password");
    const confirmPasswordField = document.getElementById("confirmPassword");

    // Add event listeners to input fields to validate as the user types
    fullnameField.addEventListener("input", () => 
        // validate fullname with a regex to allow only letters and spaces
        validateField(fullnameField, /^[A-Za-z\s]+$/, "Full name must only contain letters."));
    
    emailField.addEventListener("input", () => 
         // validate email with a regex pattern
        validateField(emailField, /^[^\s@]+@[^\s@]+\.[^\s@]+$/, "Enter a valid email address."));
    
    passwordField.addEventListener("input", () => {
         // validate password with a regex for minimum length, at least 1 number and special character
        validateField(passwordField, /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
            "Password must be at least 8 characters long, include 1 number, and 1 special character.");
        // update the strength bar based on the current password 
        updateStrengthBar(passwordField);
    });
    confirmPasswordField.addEventListener("input", () => 
        // validate that the passwords match
        validatePasswords(passwordField, confirmPasswordField));
});

// function that updates the strength bar of the password
function updateStrengthBar(passwordField) {
    const password = passwordField.value;
    const strengthMessage = document.getElementById("strengthMessage");
    const strengthBar = document.getElementById("strengthBar");

    let strength = 0;

    // add values based on what the password contains
    if (password.length >= 8) strength += 20;
    if (/[A-Za-z]/.test(password)) strength += 20;
    if (/\d/.test(password)) strength += 20;
    if (/[@$!%*?&]/.test(password)) strength += 20;
    if (password.length >= 12) strength += 20;

    // update the bar
    strengthBar.value = strength;

    // change the colour of the bar based on how strong the password is
    if (strength < 40) {
        strengthMessage.textContent = "Weak";
        strengthMessage.style.color = "red";
    } else if (strength < 80) {
        strengthMessage.textContent = "Moderate";
        strengthMessage.style.color = "orange";
    } else {
        strengthMessage.textContent = "Strong";
        strengthMessage.style.color = "green";
    }
}

// function for checking a fields against the regex inputs
function validateField(field, regex, errorMessage) {
    // find the error message <span>
    const errorSpan = field.parentElement.querySelector(".error");

    // show the error message if the regex test fails
    if (!regex.test(field.value)) {
        errorSpan.textContent = errorMessage;
        // add styling 
        field.classList.add("invalid");
    } else {
        // remove the error if the regex test is successful
        errorSpan.textContent = "";
        // remove styling
        field.classList.remove("invalid");
    }
}

// function that checks if the password and confirm password are the same
function validatePasswords(passwordField, confirmPasswordField) {
    const errorSpan = confirmPasswordField.parentElement.querySelector(".error");

    // show error if the password and confirm password don't match
    if (passwordField.value !== confirmPasswordField.value) {
        errorSpan.textContent = "Passwords do not match.";
        confirmPasswordField.classList.add("invalid");
    } else {
        errorSpan.textContent = "";
        confirmPasswordField.classList.remove("invalid");
    }
}

