
// Get the modal and button
const modal = document.getElementById("modal");
const closeBtn = document.getElementsByClassName("close-btn")[0];
const cancelButton = document.getElementById("cancelButton");
const overlay = document.getElementById("overlay");

// Close the modal
closeBtn.onclick = function() {
    modal.style.display = "none";
}

// Close the modal if the overlay is clicked
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Close the modal when the user clicks the "Cancel" button
cancelButton.addEventListener("click", function() {
    modal.style.display = "none";
});

// Close the modal when the user clicks outside of the modal content
overlay.addEventListener("click", function() {
    modal.style.display = "none";
});