const apiUrl = '/api/categories'; // the url for our categories api

//  fetching categories from the server
async function fetchCategories() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const categories = await response.json();
        renderCategories(categories);
    } catch (error) {
        console.error("Error fetching categories:", error);
    }
}

// rendering the  categories
function renderCategories(categories) {
    const container = document.getElementById('card-container');
    container.innerHTML = '';

    categories.forEach(category => {
        const cardLink = document.createElement('a');
        cardLink.href = `/categories/${category.categoryId}?categoryName=${encodeURIComponent(category.categoryTitle)}`;
        cardLink.classList.add('card');
        const cardContent = document.createElement('div');
        cardContent.innerHTML = `
        <h2>${category.categoryTitle}</h2>
        <p>${category.categoryDescription}</p>
    `;

        // Append content to the clickable link
        cardLink.appendChild(cardContent);

        // Add the clickable card to the container
        container.appendChild(cardLink);
    });

}

function openAddCategoryModal() {
    alert("Prompt user to add a new category maybe in a modal");
}

// Fetch and render categories on page load
fetchCategories();


/* ********* Modal opening and closing and adding of new categories ************************ */

const modal = document.getElementById('create-new-modal');
const openModalBtn = document.getElementById('add-new-category');
const closeModalButtons = document.querySelectorAll('.close-modal');

// open modal
openModalBtn.addEventListener('click', () => {
    modal.style.display = 'flex';
});

// close the modal, iterate over each button and add onclick
closeModalButtons.forEach(button => {
    button.addEventListener('click', () => {
        modal.style.display = 'none'
    })
})

// close the modal when one clicks outside of it
window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});


//* ************************* Submitting the form *********************** *//
const form = document.getElementById('category-form');

form.addEventListener('submit', (event) => {
    event.preventDefault() // prevent page reloading and default behaviour of form

    // get the data
    const categoryTitle = document.getElementById('categoryTitle').value;
    const categoryDescription = document.getElementById('categoryDescription').value;

    const data = {
        categoryTitle, categoryDescription
    }
    console.log(data)

    // send post request
    fetch('/api/categories/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            alert('Category added successfully')
            modal.style.display = 'none'
            fetchCategories()
        })
        .catch(error => {
            console.error('Error', error)
        })


})