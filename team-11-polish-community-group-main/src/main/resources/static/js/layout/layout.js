let translations = [];
let currentLanguage = "en";

/**
 * Fetch translations for the given language.
 * @param {string} language - Language code to fetch translations for.
 */
async function fetchTranslations(language) {
    try {
        const response = await fetch(`/api/translations/language/${language}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch translations: ${response.statusText}`);
        }
        translations = await response.json(); // This is now an array of translation objects
        currentLanguage = language;
        console.log("Translations updated:", translations);
        updateUIWithTranslations();
        document.dispatchEvent(new Event("translations-updated"));
    } catch (error) {
        console.error("Error fetching translations:", error);
    }
}

function updateUIWithTranslations() {
    const elementsToTranslate = document.querySelectorAll("[data-translate-key]");

    elementsToTranslate.forEach(element => {
        const translationKey = element.getAttribute("data-translate-key");
        // Find the translation object with matching key
        const translation = translations.find(t => t.key === translationKey);
        if (translation) {
            element.textContent = translation.value;
        } else {
            console.warn(`No translation found for key: ${translationKey}`);
        }
    });
}

// fetch translations on page load
document.addEventListener("DOMContentLoaded", () => {
    fetchTranslations(currentLanguage);
    console.log("I have run");

    // handle language selection
    const languageSelector = document.querySelector(".languageSelector select");
    if (languageSelector) {
        languageSelector.addEventListener("change", async (event) => {
            const selectedLanguage = event.target.value;
            if (selectedLanguage !== currentLanguage) {
                await fetchTranslations(selectedLanguage);
            }
        });
    }
});