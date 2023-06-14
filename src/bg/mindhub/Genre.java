package bg.mindhub;

public enum Genre {
    ACTION, COMEDY, THRILLER, HORROR, DRAMA, ROMANCE, CRIMINAL, SCI_FI, FICTION, WESTERN, ANIMATION, DOCUMENTARY, ADVENTURE;

    public static Genre from(String string) {
        try {
            return valueOf(string.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
