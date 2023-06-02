package bg.mindhub;

import java.util.List;
import java.util.Objects;

public class Movie {
    private long id;
    private String title;
    private int yearReleased;
    private String director;
    private String description;

    private static int CURRENT_ID = 0;

    public static final String[] TABLE_COLUMN_NAMES = {
            "ID",
            "Title",
            "Year Released",
            "Director",
            "Description"
    };

    public Movie() {
    }

    protected Movie(long id, String title, int yearReleased, String director, String description) {
        this.id = id;
        this.title = title;
        this.yearReleased = yearReleased;
        this.director = director;
        this.description = description;
    }

    public Movie(String title, int yearReleased, String director, String description) {
        this(CURRENT_ID++, title, yearReleased, director, description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Movie> getTestData() {
        return List.of(
                new Movie("Movie 1", 2001, "Someone 1", "This is the description of the movie \"Movie 1\""),
                new Movie("Movie 2", 2002, "Someone 2", "This is the description of the movie \"Movie 2\""),
                new Movie("Movie 3", 2003, "Someone 3", "This is the description of the movie \"Movie 3\""),
                new Movie("Movie 4", 2004, "Someone 4", "This is the description of the movie \"Movie 4\""),
                new Movie("Movie 5", 2005, "Someone 5", "This is the description of the movie \"Movie 5\""),
                new Movie("Movie 6", 2006, "Someone 6", "This is the description of the movie \"Movie 6\""),
                new Movie("Movie 7", 2007, "Someone 7", "This is the description of the movie \"Movie 7\""),
                new Movie("Movie 6", 2006, "Someone 8", "This is the description of the movie \"Movie 8\""),
                new Movie("Movie 6", 2006, "Someone 9", "This is the description of the movie \"Movie 9\""),
                new Movie("Movie 6", 2006, "Someone 10", "This is the description of the movie \"Movie 10\""),
                new Movie("Movie 6", 2006, "Someone 11", "This is the description of the movie \"Movie 11\""),
                new Movie("Movie 6", 2006, "Someone 12", "This is the description of the movie \"Movie 12\""),
                new Movie("Movie 6", 2006, "Someone 13", "This is the description of the movie \"Movie 13\""),
                new Movie("Movie 6", 2006, "Someone 14", "This is the description of the movie \"Movie 14\""),
                new Movie("Movie 6", 2006, "Someone 15", "This is the description of the movie \"Movie 15\""),
                new Movie("Movie 6", 2006, "Someone 16", "This is the description of the movie \"Movie 16\""),
                new Movie("Movie 6", 2006, "Someone 17", "This is the description of the movie \"Movie 17\""),
                new Movie("Movie 6", 2006, "Someone 18", "This is the description of the movie \"Movie 18\""),
                new Movie("Movie 6", 2006, "Someone 19", "This is the description of the movie \"Movie 19\""),
                new Movie("Movie 6", 2006, "Someone 20", "This is the description of the movie \"Movie 20\""),
                new Movie("Movie 6", 2006, "Someone 21", "This is the description of the movie \"Movie 21\"")
        );
    }

    public String[] toTableData() {
        return new String[]{
                String.valueOf(id),
                title,
                String.valueOf(yearReleased),
                director,
                description
        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, yearReleased, director);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return yearReleased == movie.yearReleased && Objects.equals(title, movie.title) && Objects.equals(director, movie.director);
    }
}