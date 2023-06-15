package bg.mindhub;

import java.util.List;
import java.util.Objects;

public class Movie {
    private long id;
    private String title;
    private int yearReleased;
    private String director;
    private String description;
    private Genre genre;

    private static int CURRENT_ID = 0;


    public final static String ID = "ID";
    public final static String TITLE = "Title";
    public final static String YEAR_RELEASED = "Year Released";
    public final static String GENRE = "Genre";
    public final static String DIRECTOR = "Director";
    public final static String DESCRIPTION = "Description";
    public static final String[] TABLE_COLUMN_NAMES = {
            ID,
            TITLE,
            YEAR_RELEASED,
            GENRE,
            DIRECTOR,
            DESCRIPTION
    };

    public Movie() {
    }

    protected Movie(long id, String title, int yearReleased, Genre genre, String director, String description) {
        this.id = id;
        this.title = title;
        this.yearReleased = yearReleased;
        this.genre = genre;
        this.director = director;
        this.description = description;
    }

    public Movie(String title, int yearReleased, Genre genre, String director, String description) {
        this(CURRENT_ID++, title, yearReleased, genre, director, description);
    }

    public long getId() {
        return id;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
                new Movie("Movie 1", 2001, Genre.COMEDY, "Someone 1", "This is the description of the movie \"Movie 1\""),
                new Movie("Movie 2", 2002, Genre.ACTION, "Someone 2", "This is the description of the movie \"Movie 2\""),
                new Movie("Movie 3", 2003, Genre.COMEDY, "Someone 3", "This is the description of the movie \"Movie 3\""),
                new Movie("Movie 4", 2004, Genre.THRILLER, "Someone 4", "This is the description of the movie \"Movie 4\""),
                new Movie("Movie 5", 2005, Genre.ACTION, "Someone 5", "This is the description of the movie \"Movie 5\""),
                new Movie("Movie 6", 2006, Genre.HORROR, "Someone 6", "This is the description of the movie \"Movie 6\""),
                new Movie("Movie 7", 2007, Genre.ACTION, "Someone 7", "This is the description of the movie \"Movie 7\""),
                new Movie("Movie 6", 2006, Genre.COMEDY, "Someone 8", "This is the description of the movie \"Movie 8\""),
                new Movie("Movie 6", 2006, Genre.HORROR, "Someone 9", "This is the description of the movie \"Movie 9\""),
                new Movie("Movie 6", 2006, Genre.THRILLER, "Someone 10", "This is the description of the movie \"Movie 10\""),
                new Movie("Movie 6", 2006, Genre.COMEDY, "Someone 11", "This is the description of the movie \"Movie 11\""),
                new Movie("Movie 6", 2006, Genre.COMEDY, "Someone 12", "This is the description of the movie \"Movie 12\""),
                new Movie("Movie 6", 2006, Genre.HORROR, "Someone 13", "This is the description of the movie \"Movie 13\""),
                new Movie("Movie 6", 2006, Genre.HORROR, "Someone 14", "This is the description of the movie \"Movie 14\""),
                new Movie("Movie 6", 2006, Genre.ACTION, "Someone 15", "This is the description of the movie \"Movie 15\""),
                new Movie("Movie 6", 2006, Genre.HORROR, "Someone 16", "This is the description of the movie \"Movie 16\""),
                new Movie("Movie 6", 2006, Genre.COMEDY, "Someone 17", "This is the description of the movie \"Movie 17\""),
                new Movie("Movie 6", 2006, Genre.THRILLER, "Someone 18", "This is the description of the movie \"Movie 18\""),
                new Movie("Movie 6", 2006, Genre.HORROR, "Someone 19", "This is the description of the movie \"Movie 19\""),
                new Movie("Movie 6", 2006, Genre.ACTION, "Someone 20", "This is the description of the movie \"Movie 20\""),
                new Movie("Movie 6", 2006, Genre.ACTION, "Someone 21", "This is the description of the movie \"Movie 21\"")
        );
    }

    public String[] toTableData() {
        return new String[]{
                String.valueOf(id),
                title,
                String.valueOf(yearReleased),
                genre.toString().toUpperCase(),
                director,
                description
        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, yearReleased, genre, director);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return yearReleased == movie.yearReleased && Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre) && Objects.equals(director, movie.director);
    }
}