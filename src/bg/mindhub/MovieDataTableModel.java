package bg.mindhub;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieDataTableModel extends AbstractTableModel {
    private List<Movie> movies;

    public MovieDataTableModel() {
        movies = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return Movie.TABLE_COLUMN_NAMES.length;
    }

    public String getColumnName(int col) {
        return Movie.TABLE_COLUMN_NAMES[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return movies.get(rowIndex).toTableData()[columnIndex];
    }

    public Movie getMovieAt(int index) {
        return movies.get(index);
    }

    public boolean addMovie(Movie movie) {
        if (movie == null || movies.contains(movie)) {
            return false;
        }
        this.fireTableDataChanged();
        movies.add(movie);
        return true;
    }

    public void deleteMovie(long ID) {
        movies.removeIf(movie -> movie.getId() == ID);
        this.fireTableDataChanged();
    }

    public void updateMovie(Map<String, String> movieData) {
        int id = Integer.parseInt(movieData.get(Movie.ID));
        String title = movieData.get(Movie.TITLE);
        int yearReleased = Integer.parseInt(movieData.get(Movie.YEAR_RELEASED));
        String director = movieData.get(Movie.DIRECTOR);
        String description = movieData.get(Movie.DESCRIPTION);

        movies.stream().filter(movie -> movie.getId() == id).findFirst().ifPresent(
                movie -> {
                    movie.setTitle(title);
                    movie.setYearReleased(yearReleased);
                    movie.setDirector(director);
                    movie.setDescription(description);
                }
        );

        this.fireTableDataChanged();
    }
}


