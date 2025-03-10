package trivia;

public enum Categories {
    POP, SCIENCE, SPORTS, ROCK;
    
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
