package trivia;

public enum Categories {
    POP("pop.properties"),
    SCIENCE("science.properties"),
    SPORTS("sports.properties"),
    ROCK("rock.properties"),
    GEOGRAPHY("geography.properties");
    
    private final String filename;
    
    Categories(String filename) {
        this.filename = filename;
    }
    
    /**
     * Retourne le nom du fichier de questions associé à la catégorie.
     * @return le nom du fichier de questions associé à la catégorie.
     */
    public String getFilename() {
        return filename;
    }
    
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}