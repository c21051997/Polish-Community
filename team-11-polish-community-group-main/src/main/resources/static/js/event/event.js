document.addEventListener('DOMContentLoaded', function () {
    var image = document.getElementById('myImage');
    var fallbackImagePath = 'assets/fallback_image_image_not_found.jpg';

    image.addEventListener('error', function() {
        image.src = fallbackImagePath;  // Replace with your fallback image URL
        image.alt = 'Fallback image';  // Optional: Set alt text for fallback image
        console.log('Image failed to load, fallback is now shown.');
    });
});

// Show event details when the event poster is hovered upon
function showEventDetails() {
    const eventDetails = document.getElementById("event-card");
    eventDetails.style.display = "flex";  // Show the details modal
}