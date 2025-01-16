const API_BASE_URL = '/api/feed';

const postFeed = document.getElementById('postFeed');
const postTemplate = document.getElementById('post-template');
const addNewPost = document.getElementById('add-post');
const closeModalBtn = document.getElementById('closeModalBtn');
const modal = document.getElementById('create-new-modal');
const postForm = document.getElementById('post-form');

let isEditing = false;
let editPostId = null;

// maintaining state
let posts = [];

// had issues with form so func to reset it
function resetForm() {
    postForm.reset();
    isEditing = false;
    editPostId = null;

    const preview = document.getElementById('imagePreview');
    if (preview) {
        preview.innerHTML = ''; // Remove any existing preview content
    }
}

addNewPost.addEventListener('click', () => {
    resetForm();
    modal.style.display = 'flex';
});

closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
    resetForm();
});

// getting all posts using api
async function fetchPosts() {
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) throw new Error('Failed to fetch posts');
        posts = await response.json();
        renderPosts();
    } catch (error) {
        console.error('Error fetching posts:', error);
    }
}

// render all posts after getting them
function renderPosts() {
    postFeed.innerHTML = ''; // clear any posts
    posts.forEach(post => renderPost(post)); // go over each item in post array and call the function renderPost on it
}

// a single post
async function renderPost(post) {
    const postElement = postTemplate.content.cloneNode(true);

    const deleteButton = postElement.querySelector('.delete-post');
    const editButton = postElement.querySelector('.edit-post');
    deleteButton.style.display = post.isDeletable ? 'block' : 'none';
    editButton.style.display = post.isEditable ? 'block' : 'none';
    postElement.querySelector('.author').textContent = post.authorName;
    postElement.querySelector('.author-title').textContent = post.authorOrganization;
    postElement.querySelector('.post-title').textContent = post.postTitle;
    postElement.querySelector('.post-description').textContent = post.postDescription;

    const postImage = postElement.querySelector('.post-image img');
    if (post.postImageUrl) {
        postImage.src = post.postImageUrl;
    } else {
        postImage.style.display = 'none';
    }

    // render the tags
    const tagsContainer = postElement.querySelector('.post-tags');
    if (post.tags && post.tags.length > 0) {
        post.tags.forEach(tag => {
            const tagSpan = document.createElement('span');
            tagSpan.innerHTML = `<i class="bi bi-tag"></i> ${tag}`;
            tagsContainer.appendChild(tagSpan);
        });
    }

    // set the count of the likes
    const likeButton = postElement.querySelector('.like-button');
    const likeCount = postElement.querySelector('.like-count');
    likeCount.textContent = post.likesCount || 0;

    likeButton.addEventListener('click', () => handleLike(post.postId, likeCount, likeButton));


    // timestamp
    const timestamp = postElement.querySelector('.timestamp');
    timestamp.textContent = new Date(post.postTime).toLocaleDateString();

    // share button func
    const shareButton = postElement.querySelector('.share-post');
    shareButton.addEventListener('click', () => sharePost(post.postId));

    // Set data attributes for reference
    const postDiv = postElement.querySelector('.post');
    postDiv.dataset.postId = post.postId;

    //deleting post
    if (post.isDeletable) {
        deleteButton.addEventListener('click', async () => {
            if (confirm('Are you sure you want to delete this post?')) {
                try {
                    const response = await fetch(`${API_BASE_URL}/${post.postId}`, {
                        method: 'DELETE',
                        credentials: 'include'
                    });

                    if (!response.ok) {
                        throw new Error('Failed to delete post');
                    }

                    const postDiv = deleteButton.closest('.post');
                    postDiv.remove();
                    posts = posts.filter(p => p.postId !== post.postId);
                } catch (error) {
                    console.error('Error deleting post:', error);
                    alert('Error deleting post. Please try again.');
                }
            }
        });
    }

    postFeed.appendChild(postElement);
}

// add a like if user had not already liked and remove a like if already liked
async function handleLike(postId, likeCountElement, likeButton) {
    try {
        const response = await fetch(`${API_BASE_URL}/${postId}/hasLiked`, {
            method: 'GET',
            credentials: 'include'
        });
        if (!response.ok) throw new Error('Failed to check like status');
        const hasLiked = await response.json();

        const method = hasLiked ? 'DELETE' : 'POST';
        const likeResponse = await fetch(`${API_BASE_URL}/${postId}/like`, {
            method: method,
            credentials: 'include'
        });
        if (!likeResponse.ok) throw new Error('Failed to update like');

        const currentCount = parseInt(likeCountElement.textContent);
        likeCountElement.textContent = hasLiked ? currentCount - 1 : currentCount + 1;

        likeButton.classList.toggle('liked', !hasLiked);
    } catch (error) {
        console.error('Error updating like:', error);
        alert('Error updating like. Please try again.');
    }
}

// share funtionality
function sharePost(postId) {
    const postUrl = encodeURIComponent(`${window.location.origin}/post/${postId}`);
    const facebookUrl = `https://www.facebook.com/sharer/sharer.php?u=${postUrl}`;
    const twitterUrl = `https://twitter.com/intent/tweet?url=${postUrl}`;
    const linkedinUrl = `https://www.linkedin.com/sharing/share-offsite/?url=${postUrl}`;

    const shareModal = document.createElement('div');
    shareModal.innerHTML = `
    <div class="share-box">
        <div class="share-controls"><button class="close-share"><i class="bi bi-x-lg"></button></i></div>
        <div class="share-options">
            <a href="${facebookUrl}" target="_blank" style="color:#4267B2"><i class="bi bi-facebook"></i></a>
            <a href="${twitterUrl}" target="_blank" style="color:#1DA1F2"><i class="bi bi-twitter"></i></a>
            <a href="${linkedinUrl}" target="_blank" style="color:#0A66C2"><i class="bi bi-linkedin"></i></a>
            <button id="copyLink"><i class="bi bi-link-45deg"></i></button>
        </div>
    </div>
    `;

    document.body.appendChild(shareModal);
    shareModal.classList.add('share-modal');
    shareModal.style.display = 'flex';

    shareModal.querySelector('.close-share').addEventListener('click', () => {
        shareModal.remove();
    })

    document.getElementById('copyLink').addEventListener('click', () => {
        navigator.clipboard.writeText(decodeURIComponent(postUrl)).then(() => {
            alert('Post link copied to clipboard!');
        }).catch(err => {
            console.error('Failed to copy: ', err);
        });
    });
}



// handling form submission  whether update or post
postForm.addEventListener('submit', async (event) => {
    event.preventDefault();


    const formData = new FormData();

    // getting post data
    const postData = {
        postTitle: document.getElementById('postTitle').value,
        postDescription: document.getElementById('postDescription').value,
        tags: document.getElementById('postTags').value
            .split(',')
            .map(tag => tag.trim())
            .filter(tag => tag.length > 0)
    };

    formData.append('post', new Blob([JSON.stringify(postData)], {
        type: 'application/json'
    }));

    // handle the case of an image selected
    const imageFile = document.getElementById('postImage').files[0];
    if (imageFile) {
        formData.append('image', imageFile);
    }

    try {
        let url = `${API_BASE_URL}/add`;
        let method = 'POST';

        if (isEditing && editPostId) {
            url = `${API_BASE_URL}/${editPostId}`;
            method = 'PATCH';
        }

        const response = await fetch(url, {
            method: method,
            credentials: 'include',
            body: formData
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        alert(isEditing ? 'Post updated successfully' : 'Post added successfully');
        modal.style.display = 'none';
        resetForm();
        fetchPosts();
    } catch (error) {
        console.error('Error:', error);
        alert(isEditing ? 'Error updating post' : 'Error adding post');
    }
});

//previewing the image selected
document.getElementById('postImage').addEventListener('change', function(e) {
    const file = e.target.files[0];
    const preview = document.getElementById('imagePreview');

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.innerHTML = `<img src="${e.target.result}" style="max-width: 200px; max-height: 200px;">`;
        }
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = '';
    }
});

// onclick handling for edit
document.addEventListener('click', async (event) => {
    const editButton = event.target.closest('.edit-post');
    if (!editButton) return;

    const postDiv = editButton.closest('.post');
    const postId = postDiv.dataset.postId;

    try {
        const response = await fetch(`${API_BASE_URL}/${postId}`);
        if (!response.ok) throw new Error('Failed to fetch post data');

        const postData = await response.json();

        // set edit state ti true
        isEditing = true;
        editPostId = postId;


        document.getElementById('postTitle').value = postData.postTitle;
        document.getElementById('postDescription').value = postData.postDescription;
        document.getElementById('postTags').value = postData.tags.join(', ');

        // Show existing image in preview if available
        const preview = document.getElementById('imagePreview');
        if (postData.postImageUrl) {
            preview.innerHTML = `<img src="${postData.postImageUrl}" style="max-width: 200px; max-height: 200px;">`;
        } else {
            preview.innerHTML = ''; // Clear preview if no image exists
        }


        modal.style.display = 'flex';
    } catch (error) {
        console.error('Error fetching post data:', error);
        alert('Error fetching post data. Please try again.');
    }
});

// initialize on page load posts on page load
document.addEventListener('DOMContentLoaded', () => {
    fetchPosts();
});

//  refresh posts periodically
// setInterval(fetchPosts, 60000);