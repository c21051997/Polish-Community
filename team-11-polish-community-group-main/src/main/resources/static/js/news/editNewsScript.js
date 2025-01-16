// function for basic validation of the form, checks to see if any field is empty
function validateForm(event) {

    // there are different funtions depending on what button is pressed by the user
    // :focus is used to to having multiple submit buttons on the form
    const action = event.target.querySelector('[type="submit"]:focus').value;

    // if the user clicked the save changes button
    if (action === 'edit') {
        // get all input fields
        const inputs = document.querySelectorAll('input[type="text"], input[type="url"], input[type="date"], textarea');
        
        // loop through each input field to check if it's empty
        for (let i = 0; i < inputs.length; i++) {
            // check if the field is empty
            // .trim() is used to avoid accepting cases where there is spaces in the fields
            if (inputs[i].value.trim() === "") {
                alert("Please fill out all fields.");
                // prevent form submission
                event.preventDefault(); 
                return false; 
            }
        }
        return true;
    }
}

// function for handling the delete button, 
// this does not need the validation above as it does not matter if the fields are left empty
function confirmDelete(event) {
    // confirmation message to the user
    const confirmation = confirm('Are you sure you want to delete this news article?');
    if (!confirmation) {
        event.preventDefault(); 
        return false;
    }
    return true;
}