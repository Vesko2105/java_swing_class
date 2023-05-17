import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Movie {
    private long id;
    private String title;
    private int yearReleased;
    private String director;

    private static int CURRENT_ID = 0;

    public static final String[] TABLE_COLUMN_NAMES = {
            "ID",
            "Title",
            "Year Released",
            "Director"
    };

    public Movie() {
    }

    protected Movie(long id, String title, int yearReleased, String director) {
        this.id = id;
        this.title = title;
        this.yearReleased = yearReleased;
        this.director = director;
    }

    public Movie(String title, int yearReleased, String director) {
        this(CURRENT_ID++, title, yearReleased, director);
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

    public static List<Movie> getTestData() {
        return List.of(
                new Movie("Movie 1", 2001, "Someone 1"),
                new Movie("Movie 2", 2002, "Someone 2"),
                new Movie("Movie 3", 2003, "Someone 3"),
                new Movie("Movie 4", 2004, "Someone 4"),
                new Movie("Movie 5", 2005, "Someone 5"),
                new Movie("Movie 6", 2006, "Someone 6"),
                new Movie("Movie 7", 2007, "Someone 7")
        );
    }

    public String[] toTableData() {
        return new String[] {
                String.valueOf(id),
                title,
                String.valueOf(yearReleased),
                director
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
