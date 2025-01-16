// check if  error code is passed from the backend via the model.
let errorCode = window.errorCode ||
    new URLSearchParams(window.location.search).get('code') || // Check query parameter
    500; // Default to 500 if neither is available

// Mapping error code to message
const errorMessages = {
    404: "The page you are looking for was not found.",
    403: "You don't have permission to access this resource.",
    500: "An internal server error occurred. Please try again later."
};

const errorCodeElement = document.getElementById("error-code");
const errorMessageElement = document.getElementById("error-message");


errorCodeElement.textContent = `Error ${errorCode}`;
errorMessageElement.textContent = errorMessages[errorCode] || "An unexpected error occurred.";
