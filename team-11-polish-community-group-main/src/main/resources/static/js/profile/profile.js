document.addEventListener('DOMContentLoaded', function () {
    const profileCard = document.getElementById('profile-card');
    const editForm = document.getElementById('edit-profile-form');
    const editBtn = document.getElementById('edit-profile-btn');
    const cancelBtn = document.getElementById('cancel-edit');
    const profilePictureInput = document.getElementById('profile-picture-input');
    const changeProfilePictureBtn = document.getElementById('change-profile-picture-btn');
    const editProfilePicture = document.getElementById('edit-profile-picture');
    fetch('/profile-json')
        .then(response => response.json())
        .then(profile => {
            document.getElementById('profile-picture').src = profile.profilePicture || '/assets/default-profile.jpg';
            /*document.getElementById('profile-heading').innerHTML = `${profile.fullName}'s bio`;*/
            document.getElementById('full-name').innerHTML = profile.fullName;
            document.getElementById('email').innerHTML = profile.email;
            if (profile.organisation) {
                document.getElementById('organisation').innerHTML = profile.organisation;
            }
            if (profile.bio) {
                document.getElementById('bio').innerHTML = profile.bio;
            }
            if (profile.showDob && profile.dob) {
                document.getElementById('date-of-birth').innerHTML = profile.dob;
            }
            if (profile.showPhoneNumber && profile.phoneNumber) {
                document.getElementById('phone-number').innerHTML = `Phone: ${profile.phoneNumber}`;
            }
            populateEditForm(profile);
            updateProfileCard(profile);

        })
        .catch(error => {
            console.error('Error:', error);
            console.error('Error stack:', error.stack);
            document.getElementById('profile-container').innerHTML = 'Error loading profile.';
        });
    editBtn.addEventListener('click', () => {
        profileCard.style.display = 'none';
        editForm.style.display = 'block';
    });

    cancelBtn.addEventListener('click', () => {
        editForm.style.display = 'none';
        profileCard.style.display = 'block';
    });
    changeProfilePictureBtn.addEventListener('click', () => {
        profilePictureInput.click();
    });

    profilePictureInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                editProfilePicture.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    function updateProfileCard(profile) {
        document.getElementById('profile-picture').src = profile.profilePicture || '/assets/default-profile.jpg';
        document.getElementById('full-name').textContent = profile.fullName;
        document.getElementById('email').textContent = profile.email;
        document.getElementById('organisation').textContent = profile.organisation || '';
        document.getElementById('bio').textContent = profile.bio || '';
        document.getElementById('date-of-birth').textContent = profile.showDob && profile.dob ? profile.dob :  '';
        document.getElementById('phone-number').textContent = profile.showPhoneNumber && profile.phoneNumber ? `Phone: ${profile.phoneNumber}` : '';
    }

    function populateEditForm(profile) {
        document.getElementById('edit-profile-picture').src = profile.profilePicture || '/assets/default-profile.jpg';
        document.getElementById('original-profile-picture').value =  profile.profilePicture;
        document.getElementById('edit-full-name').value = profile.fullName;
        document.getElementById('edit-email').value = profile.email;
        document.getElementById('edit-organisation').value = profile.organisation || '';
        document.getElementById('edit-bio').value = profile.bio || '';
        document.getElementById('edit-date-of-birth').value = profile.dob || '';
        document.getElementById('edit-phone-number').value = profile.phoneNumber || '';
        document.getElementById('show-dob').checked = profile.showDob;
        document.getElementById('show-number').checked = profile.showPhoneNumber;
    }
});