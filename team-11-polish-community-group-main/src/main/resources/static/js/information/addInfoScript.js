const quill = new Quill('#editor', {
    theme: 'snow'
});


// Add event listener to save content
document.getElementById("addInfoForm").addEventListener("submit",
    function (event) {

    const turndownService = new TurndownService();
    // const delta = quill.getContents(); // Get Quill Delta
    const html = quill.root.innerHTML; // Get HTML content
    const markdown = turndownService.turndown(html); // Convert HTML to Markdown
    var elem = document.getElementById("infoDescription");
    elem.value = markdown;
});

function markdownToHtml(markdown){
    return marked.parse(markdown);
}

function addContentToRichText(){
    debugger
    const descriptionElem = document.getElementById("infoDescription");
    const convertedHtml = markdownToHtml(descriptionElem.defaultValue);
    quill.root.innerHTML = convertedHtml;
}

addContentToRichText();


