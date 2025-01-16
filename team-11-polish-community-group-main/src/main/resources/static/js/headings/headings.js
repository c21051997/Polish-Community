// get category from url params
const urlParams = new URLSearchParams(window.location.search);
const categoryId = window.location.pathname.split('/')[2];  // Assuming /categories/{id}
const categoryName = urlParams.get('categoryName');

// fetch category headings from api
const apiUrl = '/getInfoByCategoryId';

async function fetchCategoryHeadings() {
    try {
        const response = await fetch(`${apiUrl}/${categoryId}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        const headings = await response.json();
        renderHeadings(headings);
    } catch (error) {
        console.error("Error fetching category headings:", error);
    }
}

// list headings as clickable links
function renderHeadings(headings) {
    const container = document.getElementById('headings-container');
    container.innerHTML = '';

    headings.forEach(heading => {
        const listItem = document.createElement('div');
        listItem.classList.add('list-item')

        const input = document.createElement('input');
        input.type='checkbox';

        const title = document.createElement('h2')
        title.innerHTML = `<i class="bi bi-file-earmark-text"></i> ${heading.infoTitle} `

        // const description = document.createElement('p');
        // description.textContent = heading.description

        const link = document.createElement('a')
        link.href = `/info/${heading.informationId}`;
        link.classList.add('heading-link');

        const readMore = document.createElement('button')
        readMore.innerHTML = 'Read More <i class="bi bi-chevron-right"></i>'
        readMore.classList.add('read-more-btn')

        link.appendChild(readMore);

        listItem.appendChild(title)
        // listItem.appendChild(description)
        listItem.appendChild(link)
        container.appendChild(listItem)
    });
}

// change the title of the page bases on category
document.getElementById('category-name').textContent = categoryName ? `${categoryName}` : 'Category Name Not Found';

// Fetch and render the category headings when the page loads
fetchCategoryHeadings();


// Get the category id from the url
function getCategoryIdFromUrl() {
    const path = window.location.pathname;
    const parts = path.split("/");
    return parts[2];
}

// Function to dynamically add href url to add information button
function generateAddInfoPageUrl() {
    // Extract categoryId from the URL
    const categoryId = getCategoryIdFromUrl();

    // Create url from category id
    const url = `/info/add/${categoryId}`;
    const link = document.getElementById("addInfo");
    link.setAttribute("href", url);
}

generateAddInfoPageUrl();


// Function to toggle edit mode to enable multi-select for deletion
function toggleEditMode() {
    const container = document.getElementById('headings-container');
    const editButton = document.getElementById('edit-mode-button');
    const deleteButton = document.getElementById('delete-button');
    const isEditMode = container.classList.toggle('edit-mode');

    const listItems = container.querySelectorAll('.list-item');

    if (isEditMode) {
        // Add checkboxes to each heading list item
        listItems.forEach(item => {
            if (!item.querySelector('input[type="checkbox"]')) {
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.classList.add('multi-select-checkbox');
                checkbox.addEventListener('change', toggleDeleteButtonState);

                // Inserts the checkbox before the first child of the list item
                item.insertBefore(checkbox, item.firstChild);
            }
        });
        editButton.textContent = 'Done';
        // Enable the delete button
        deleteButton.disabled = false;
        deleteButton.style.visibility="visible"
    } else {
        // Remove checkboxes from each list item
        listItems.forEach(item => {
            const checkbox = item.querySelector('.multi-select-checkbox');
            if (checkbox) {
                item.removeChild(checkbox);
            }
        });
        editButton.textContent = 'Edit';
        // Disables and hides the delete button
        deleteButton.disabled = true;
        deleteButton.style.visibility="hidden"
    }
}

// Function to toggle the delete button state based on checkbox selection
function toggleDeleteButtonState() {
    const selectedItems = document.querySelectorAll('.multi-select-checkbox:checked');
    const deleteButton = document.getElementById('delete-button');
    deleteButton.disabled = selectedItems.length === 0;
}

// Function to delete selected items
async function deleteSelectedItems() {
    const selectedItems = document.querySelectorAll('.multi-select-checkbox:checked');

    // Get the list of heading ids to be deleted
    const idsToDelete = Array.from(selectedItems).map(checkbox => {
        const listItem = checkbox.closest('.list-item');
        const anchor = listItem.querySelector('a.heading-link');
        if (anchor) {
            const href = anchor.getAttribute('href');
            const idMatch = href.match(/info\/(\d+)/); // Extract the ID using regex
            return idMatch ? idMatch[1] : null; // Return the ID if matched
        }
        return null; // Skip if no anchor element or invalid format
    }).filter(id => id !== null);

    if (idsToDelete.length === 0) {
        alert("No headings selected for deletion.");
        return;
    }

    // Check confirmation before deletion
    const confirmed = confirm("Are you sure you want to delete the selected list of headings?");
    if (!confirmed) return;

    // Convert array of heading IDs to a comma-separated string to be passed as a parameter
    const idList = idsToDelete.join(',');

    // Fetch api call to delete the list of headings at server side
    try {
        const response = await fetch('/api/info/delete', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `deleteIdList=${encodeURIComponent(idList)}`
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Remove deleted items from the DOM
        selectedItems.forEach(checkbox => {
            const listItem = checkbox.closest('.list-item');
            listItem.remove();
        });

        alert("Items deleted successfully.");

    } catch (error) {
        console.error("Error deleting items:", error);
        alert("Failed to delete items. Please try again.");
    }
}

// Attach event listeners to toggle edit button and perform deletion
document.getElementById('edit-mode-button').addEventListener('click', toggleEditMode);
document.getElementById('delete-button').addEventListener('click', deleteSelectedItems);