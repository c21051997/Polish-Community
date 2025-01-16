document.addEventListener('DOMContentLoaded', function() {
    const accountOptions = document.getElementById('account-options');
    const dropdownContent = document.getElementsByClassName('dropdown-content');

    accountOptions.addEventListener('click', function (e) {
            [...dropdownContent].forEach(dropdown => {
                dropdown.style.display = dropdown.style.display ===
                'block' ? 'none' : 'block'
            });
    });

    //hide dropdown when user clicks outside of element
    document.addEventListener('click', function(e) {
        if (!accountOptions.contains(e.target)) {
            [...dropdownContent].style.display = 'none';
        }
    });
});