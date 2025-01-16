// Function to enable or disable a user
function toggleUserEnabled(userId, enable) {
    //check current status and do the alternate
    enable = enable !== true;
    const url = `/admin/edit/${userId}/enabled?enabled=${enable}`;

    // Use Fetch API to send the PUT request
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials:"include"
    })
        .then(response => {
            if (response.ok) {
                // Reload the page to reflect the changes
                location.reload();
            } else {
                alert('Failed to update user status.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error occurred while updating user status.');
        });
}

function updateUserRole(userId){
    const roleSelect = document.getElementById('roleDdl');
    const selectedValue = roleSelect.value;
    const url = `/admin/edit/${userId}/role`;

    // Use Fetch API to send the PUT request
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials:"include",
        body:JSON.stringify(selectedValue)
    })
        .then(response => {
            if (response.ok) {
                // Reload the page to reflect the changes
                location.reload();
            } else {
                alert('Failed to update user role.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error occurred while updating user role.');
        });
}

function disableColorChange(){
    var actionBtn = document.querySelectorAll("#btn-enable-disable");
    actionBtn.forEach(e =>{
        if(e.innerText.toLowerCase().includes('disable')){
            e.style.backgroundColor='#E5E4E6';
            e.style.color='#09090B';
        }
    });
}
disableColorChange();
