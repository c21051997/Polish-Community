// const turndownService = new TurndownService();
// var markdown;
//
// function htmlToMarkdown(){
//     const html = document.getElementById('info').innerHTML;
//     markdown = turndownService.turndown(html);
//     var par = document.createElement('p');
//     par.innerText = markdown + 'Hello World';
//     document.getElementById('markdowncontent').appendChild(par);
// }
//
// function markdownToHtml(){
//     // markdown = /*[[${dbInfo.infoDescription}]]*/ '';
//     const html = marked.parse(markdown);
//     document.getElementById('markupcontent').innerHTML=html;
// }
//
// // document.addEventListener('onload',htmlToMarkdown());
// document.addEventListener('onload',markdownToHtml());

document.addEventListener("DOMContentLoaded", () => {
    function getHeadingsUrl() {
        const categoryId = sessionStorage.getItem('categoryId');
        const categoryName = sessionStorage.getItem('categoryName');

        // Ensure categoryId and categoryName exist
        if (!categoryId || !categoryName) {
            console.error('Category id or name is not available in the session');
        }

        const url = `/categories/${categoryId}?categoryName=${categoryName}`;
        return url
    }

    function addUrlToElement(elementId){
        const link = document.getElementById(elementId);
        if (link!=null) {
            link.setAttribute("href", getHeadingsUrl());
        } else {
            console.error('Anchor element is null');
        }
    }

    addUrlToElement('backToHeadings');
    addUrlToElement('cancelButton');
});