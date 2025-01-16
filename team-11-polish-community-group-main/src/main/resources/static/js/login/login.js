function validateForm() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("errorMessage");

    if (email.trim() === "" || password.trim() === "") {
        errorMessage.textContent = "Please fill in both email and password fields.";
        errorMessage.style.display = "block";
        return false;
    }
    return true;
}