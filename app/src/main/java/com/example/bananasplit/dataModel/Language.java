package com.example.bananasplit.dataModel;

/**
 * Enum for language localization
 * @author Dennis Brockmeyer
 */
public enum Language {
    DE("Deutsch", "de-de"),
    EN("English", "en-us");

    private final String name;
    private final String languageCode;

    Language(String name, String languageCode) {
        this.name = name;
        this.languageCode = languageCode;
    }

    /**
     * Returns the name for displaying purposes
     * @return the name as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Languagecode for displaying purposes
     * @return the Languagecode as a string
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Creates a Language Object from the given String
     *
     * @param string the language String
     * @return the language object or null
     */
    public Language from(String string) {
        for (Language l : Language.values()) {
            if (l.name.equalsIgnoreCase(string)) {
                return l;
            }
        }
        return null;
    }
}
