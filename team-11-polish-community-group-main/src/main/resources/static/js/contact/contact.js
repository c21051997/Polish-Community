// initalise the chart when the page loads, could also initialize map but didn't work like the chart did
// also sort out the animations of the contact form
document.addEventListener('DOMContentLoaded', () => {

    // call the chart initialization method
    initUserChart();
    
    // input focus and blur animation for the text
    document.querySelectorAll('input, textarea').forEach(field => {
        field.addEventListener('focus', () => {
            anime({
                targets: field,
                scale: 1.05,  // enlarge teh box slightly on focus
                duration: 300,
                easing: 'easeInOutQuad'
            });
        });

        field.addEventListener('blur', () => {
            anime({
                targets: field,
                scale: 1,  // return the box to the original size 
                duration: 300,
                easing: 'easeInOutQuad'
            });
        });
    });

    // submit button hover bounce effect 
    const submitButton = document.getElementById('submitButton');
    submitButton.addEventListener('mouseover', () => {
        anime({
            targets: submitButton,
            translateY: [-10, 0],
            duration: 500,
            easing: 'easeOutBounce'
        });
    });

    // FAQ functionality that includes the map initialization
    document.querySelectorAll('.faq-question').forEach(question => {
        question.addEventListener('click', function () {
            // answer of the questions
            const answer = this.nextElementSibling; 
            // the map element
            const mapContainer = this.nextElementSibling.nextElementSibling; 

            // toggle visibility of the answer (block = visible, none = hidden)
            const isHidden = answer.style.display === 'none' || answer.style.display === '';
            answer.style.display = isHidden ? 'block' : 'none'; 
    
            // if the map container is hidden, initialize the map
            if (mapContainer.style.display === 'none' || mapContainer.style.display === '') {
                // show the map
                mapContainer.style.display = 'block';  
                // call the map initialization method
                initMap(mapContainer);  
            } else {
                // hide the map
                mapContainer.style.display = 'none';  
            }

        });
    });

    // form submission that displays a feedback message
    document.getElementById('contactForm').addEventListener('submit', function (event) {
        event.preventDefault();
    
        // get all the inputs from the fields
        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('email').value.trim();
        const message = document.getElementById('message').value.trim();
    
        // get the feedback element and clear the previous feedback
        const feedback = document.getElementById('formFeedback');
        feedback.textContent = ''; 
        feedback.style.color = '';
    
        let isValid = true;
    
        // check for empty fields
        if (!name || !email || !message) {
            feedback.textContent = 'Please fill out all fields.';
            feedback.style.color = 'red';
            isValid = false;
        }
    
        // check for valid email 
        if (email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                feedback.textContent = 'Please enter a valid email address.';
                feedback.style.color = 'red';
                isValid = false;
            }
        }
    
        // if everything is valid
        if (isValid) {
            feedback.textContent = 'Thank you for your message! We will get back to you within 2-3 days.';
            feedback.style.color = 'green';
    
            // reset the form fields
            this.reset();
        }
    });
    
});

// funtion for initialising the map
function initMap(container) {
    // using Cardiff location coordinates
    const location = { lat: 51.4778, lng: -3.1776 }; 

    // create the map that centers at Cardiff
    const map = new google.maps.Map(container, {
        zoom: 12,
        center: location
    });

    // add the marker that is placed at Cardiff
    const marker = new google.maps.Marker({
        position: location,
        map: map,
        title: "Our Location",
    });
}

// funtion for initialising the chart
function initUserChart() {
    // get the html chart
    const ctx = document.getElementById('userChart').getContext('2d');

    // mock data for years and the corresponding number of users
    // in future versions, this would collect data from the users database
    const years = [2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024];
    const numberOfUsers = [500, 700, 900, 1200, 1500, 2000, 2500, 3000, 4000, 5000, 6000];

    // set up the chart
    new Chart("userChart", {
        type: "line", // line chart
        data: {
            labels: years,  
            datasets: [{
                label: "Number of Users",
                backgroundColor: "rgba(0,0,255,0.2)",  
                borderColor: "rgba(0,0,255,1.0)",  
                data: numberOfUsers,  
                fill: true,  // fill the area below the line
            }]
        },
        // styling options for the chart
        options: {
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Number of Users'  
                    },
                    beginAtZero: true,
                },
                y: {
                    title: {
                        display: true,
                        text: 'Year'  
                    }
                }
            },
            responsive: true,
            
        }
    });
}
