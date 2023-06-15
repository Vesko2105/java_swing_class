package bg.mindhub.components;

import bg.mindhub.Genre;
import bg.mindhub.Movie;
import bg.mindhub.MovieDataTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.util.*;

public class MovieDataTable extends JTable {
    private final MovieDataTableModel movieDataTableModel;

    private final TableRowSorter<MovieDataTableModel> tableRowSorter;

    private String currentSearchTerm;

    private Set<Genre> currentGenres;

    public MovieDataTable(ListSelectionListener listSelectionListener) {
        movieDataTableModel = new MovieDataTableModel();
        tableRowSorter = new TableRowSorter<>(movieDataTableModel);
        currentGenres = new HashSet<>();
        currentSearchTerm = "";

        this.setModel(movieDataTableModel);
        this.setRowSorter(tableRowSorter);
        this.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    public Movie getSelectedMovie() {
        int movieIndex = tableRowSorter.convertRowIndexToModel(getSelectedRow());
        return movieDataTableModel.getMovieAt(movieIndex);
    }

    public boolean addMovie(Movie movie) {
        boolean success = movieDataTableModel.addMovie(movie);
        if (success) {
            int createdMovieIndex = this.convertRowIndexToView(movieDataTableModel.getRowCount() - 1);
            this.getSelectionModel().setSelectionInterval(createdMovieIndex, createdMovieIndex);
            return true;
        }
        return false;
    }

    public void updateMovie(Map<String, String> data) {
        movieDataTableModel.updateMovie(data);
        int movieId = Integer.parseInt(data.get(Movie.ID));
        int createdMovieIndex = this.convertRowIndexToView(movieId);
        this.getSelectionModel().setSelectionInterval(createdMovieIndex, createdMovieIndex);
    }

    public void deleteMovie(long id) {
        movieDataTableModel.deleteMovie(id);
    }

    public void filterData() {
        RowFilter<MovieDataTableModel, Integer> searchFilter = RowFilter.regexFilter(currentSearchTerm);

        StringJoiner genreRegex = new StringJoiner("|");
        for (Genre genre : currentGenres) {
            genreRegex.add(genre.toString());
        }

        RowFilter<MovieDataTableModel, Integer> genreFilter = RowFilter.regexFilter(genreRegex.toString());

        List<RowFilter<? super MovieDataTableModel, ? super Integer>> filters = new LinkedList<>();
        filters.add(searchFilter);
        filters.add(genreFilter);
        tableRowSorter.setRowFilter(RowFilter.andFilter(filters));
    }

    public void loadTestData() {
        if(movieDataTableModel.getRowCount() == 0) {
            Movie.getTestData().forEach(movieDataTableModel::addMovie);
        }
    }

    public void addGenreFilter(Genre selectedGenre) {
        currentGenres.add(selectedGenre);
        filterData();
    }

    public void removeGenreFilter(Genre selectedGenre) {
        currentGenres.remove(selectedGenre);
        filterData();
    }

    public void searchFor(String string) {
        currentSearchTerm = string;
        filterData();
    }
}
