// Function to check if a URL is valid
async function isValidUrl(href) {
    try {
        const url = new URL(href); // Ensure it's a valid URL format

        // Using no-cors as we just need to check the status of the url
        const response = await fetch(url, { method: "HEAD", mode: "no-cors" });
        return response.ok || response.type === "opaque"; // Check if reachable
    } catch (error) {
        console.error("URL validation error:", href, error);
        return false;
    }
}

// Attach click listeners to all anchor links and perform validations
document.addEventListener("DOMContentLoaded", () => {
    const links = document.querySelectorAll(".text-container a, .news-card a");
    for (const link of links) {
        link.addEventListener("click", async function (event) {
            event.preventDefault(); // Prevent default navigation
            let href= this.href !=window.location.href ? this.href : '';
            if (href) {
                const isValid = await isValidUrl(href);
                if (!isValid) {
                    alert("This news source is not reachable.");
                } else {
                    window.open(href, '_blank'); // Open in a new tab
                }
            }
            else{
                alert("The news source you are trying to reach in not available.")
            }
        });
    }
});


// // This will handle the modal opening when the Add News tile is clicked
function openNewsForm() {
    const openModalBtn = document.getElementById("openModalBtn");
    // Open the modal
    openModalBtn.onclick = function() {
        modal.style.display = "flex";
    }

    const newsFormSection = document.getElementById('newsForm');
    if(newsFormSection.style.display == 'none'){
        newsFormSection.style.display = 'block';
    }else
    newsFormSection.style.display = 'none';
}

