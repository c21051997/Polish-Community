console.log("JS is loaded");

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryId = window.location.pathname.split('/')[2]; // Assuming /categories/{id}
    const breadcrumbContainer = document.getElementById('breadcrumb');

    // If we are on the category page, set the breadcrumb as Home > Categories > Category Name
    if (window.location.pathname.includes('/categories/')) {
        const categoryName = urlParams.get('categoryName'); // Get category name from URL params
        
        if (categoryName) {
            // Generate the category URL dynamically
            const categoryUrl = `/categories/${categoryId}?categoryName=${encodeURIComponent(categoryName)}`;
            
            // Store the values in sessionStorage
            sessionStorage.setItem('categoryId', categoryId);
            sessionStorage.setItem('categoryName', categoryName);
            sessionStorage.setItem('categoryUrl', categoryUrl);
            
            breadcrumbContainer.innerHTML = `
                <li><a href="/">Home</a> > </li>
                <li><a href="/categories">Categories</a> > </li>
                <li>${categoryName}</li>
            `;
        } else {
            console.warn("Category name is missing in the URL parameters.");
        }
    }

    // If we are on the heading page, set the breadcrumb as Home > Categories > Category Name > Heading Name
    else if (window.location.pathname.includes('/info/')) {
        const headingName = document.querySelector('h1').textContent || 'Heading'; // Get the heading name from the page
        
        const categoryId = sessionStorage.getItem('categoryId');
        const categoryName = sessionStorage.getItem('categoryName');
        const categoryUrl = sessionStorage.getItem('categoryUrl');

        if (categoryName && categoryId) {
            breadcrumbContainer.innerHTML = `
                <li><a href="/">Home</a> > </li>
                <li><a href="/categories">Categories</a> > </li>
                <li><a href="${categoryUrl}">${categoryName}</a> > </li>
                <li>${headingName}</li> <!-- Add Heading name in breadcrumb -->
            `;
        } else {
            console.warn("Category name or ID is missing in sessionStorage.");
        }
    }
});

