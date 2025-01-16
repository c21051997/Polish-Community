
const commentForms = document.getElementsByClassName('comment-submission');
for (let i = 0; i < commentForms.length; i++) {
    let form = commentForms[i];
    form.addEventListener("submit", submitComment);
    form.addEventListener()
}
function submitComment(event) {
    event.preventDefault();

    const form = event.target;
    const postId = form.querySelector('input[name="postId"]').value;
    const content = form.querySelector('textarea[name="content"]').value;

    // Create a new XMLHttpRequest object
    const xhr = new XMLHttpRequest();


    xhr.open('POST', '/feed/comments/comments/publish', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    // Prepare data to send
    const data = JSON.stringify({
        postId: postId,
        content: content
    });

    // Setup a function to handle the response
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            // Parse JSON response
            const comment = JSON.parse(xhr.responseText);

            // Append new comment to comments section
            const commentsSection = document.getElementById('comments-' + postId);
            const newCommentHtml = `
                        <div class="comment">
                            <p>${comment.content}</p>
                            <small>By ${comment.username} on ${new Date(comment.createdDate).toLocaleString()}</small>
                        </div>`;
            commentsSection.insertAdjacentHTML('beforeend', newCommentHtml);

            // Clear textarea
            form.querySelector('textarea[name="content"]').value = '';
        } else {
            console.error('Error posting comment: %o', xhr);
        }
    };

    xhr.onerror = function () {
        console.error('Request failed');
    };

    // Send the request over the network
    xhr.send(data);

}
function deleteComment(commentId, postId) {
    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', `/feed/comments/${commentId}`, true);
    xhr.onload = function() {
        if (xhr.status === 204) {
            // Remove the comment from the DOM
            const commentElement = document.getElementById(`comment-${commentId}`);
            if (commentElement) {
                commentElement.remove();
            }
        } else {
            console.error('Error deleting comment');
        }
    };
    xhr.onerror = function() {
        console.error('Request failed');
    };
    xhr.send();
}
