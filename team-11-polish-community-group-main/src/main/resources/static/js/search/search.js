const userInput = document.getElementById("searchInput");


function performSearch() {
    const inputValue = userInput.value
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", `/searchresults?q=${encodeURIComponent(inputValue)}`, true);
    xhttp.responseType = 'json';
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4) {
            if (xhttp.status === 200) {
                handleSuccessfulSearchResponse(xhttp);
            } else {
                handleErrorSearchResponse(xhttp);
            }
        }
    };
    xhttp.onerror = function (e) {
        handleSearchError(e);
    }
    xhttp.send();
}

function handleSuccessfulSearchResponse(xhttp) {
    let results = xhttp.response;
    if (results.length === 0) {
        const list = document.createElement("ul");
        let li = document.createElement("li");
        li.innerText = "No results found"
        list.appendChild(li)
        updateSearchResults(list)
    } else {
        const list = document.createElement("ul");
        results.forEach((item) => {
            let li = document.createElement("li");
            list.appendChild(li);
            let a = document.createElement("a")
            a.href = "/getInfo/" + item.infoID;
            a.innerText = item.infoTitle;
            li.appendChild(a);
        });
        updateSearchResults(list)
    }
}

function handleErrorSearchResponse(xhttp) {
    console.error(xhttp.statusText);
    const list = document.createElement("ul");
    let li = document.createElement("li");
    li.innerText = xhttp.response.message
    li.className = "errorMessage";
    list.appendChild(li)
    updateSearchResults(list)
}

function handleSearchError(e) {
    console.error(e);
    const list = document.createElement("ul");
    let li = document.createElement("li");
    li.innerText = xhttp.response.message
    li.className = "An unexpected error occurred";
    list.appendChild(li)
    updateSearchResults(list)
}

function updateSearchResults (results) {
    const searchResults = document.getElementById("searchResultsContainer");
    searchResults.innerHTML = "";
    searchResults.className = "";
    let cancelButton = document.createElement("button");
    cancelButton.className = "topRight cancelButton";
    cancelButton.addEventListener('click', function () {
        clearTimeout(searchTimeout);
        searchResults.className = "hidden";
        searchResults.innerHTML = "";
    })
    let cancelIcon = document.createElement("i");
    cancelIcon.className = "bi bi-x-circle";
    cancelButton.appendChild(cancelIcon);
    searchResults.appendChild(cancelButton);
    searchResults.appendChild(results);
}

let searchTimeout = null;

document.addEventListener('DOMContentLoaded', function () {
    //add event listener to the searchbar input field
    userInput.addEventListener('input', function () {
        if (searchTimeout !== null) {
            clearTimeout(searchTimeout);
        }
        searchTimeout = setTimeout(function () {
            performSearch();
        }, 700);
    })
})
