package polish_community_group_11.polish_community.translation.model;

public class Translation {
    private int id;
    private String key;
    private String language;
    private String value;

    public Translation () {

    }

    public Translation(int id, String key, String language, String value){
        this.id = id;
        this.key = key;
        this.language = language;
        this.value = value;

    }

    public Translation(String key, String language, String value){
        this.key = key;
        this.language = language;
        this.value = value;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // some utility methods for debugging
    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", language='" + language + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    // cheking if two translations equal each other
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translation that = (Translation) o;

        if (id != that.id) return false;
        if (!key.equals(that.key)) return false;
        if (!language.equals(that.language)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + key.hashCode();
        result = 31 * result + language.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
