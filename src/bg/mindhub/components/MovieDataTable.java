package bg.mindhub.components;

import bg.mindhub.Movie;
import bg.mindhub.MovieDataTableModel;
import bg.mindhub.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class MovieDataTable extends JTable {
    private final MovieDataTableModel movieDataTableModel;

    private final TableRowSorter<MovieDataTableModel> tableRowSorter;

    public MovieDataTable(ListSelectionListener listSelectionListener) {
        movieDataTableModel = new MovieDataTableModel();
        tableRowSorter = new TableRowSorter<>(movieDataTableModel);

        this.setModel(movieDataTableModel);
        this.setRowSorter(tableRowSorter);
        this.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    public Movie getSelectedMovie() {
        int movieIndex = tableRowSorter.convertRowIndexToModel(getSelectedRow());
        return movieDataTableModel.getMovieAt(movieIndex);
    }

    public boolean addMovie(Movie movie) {
        return movieDataTableModel.addMovie(movie);
    }

    public void updateMovie(List<String> data) {
        try {
            movieDataTableModel.updateMovie(
                    Long.parseLong(data.get(0)),
                    data.get(1),
                    Integer.parseInt(data.get(2)),
                    data.get(3),
                    data.get(4)
            );
        } catch (Exception ignored) {}
    }

    public void deleteMovie(long id) {
        movieDataTableModel.deleteMovie(id);
    }

    public void filterData(RowFilter<MovieDataTableModel, Object> rowFilter) {
        tableRowSorter.setRowFilter(rowFilter);
    }

    public void loadTestData() {
        if(movieDataTableModel.getRowCount() == 0) {
            Movie.getTestData().forEach(movieDataTableModel::addMovie);
        }
    }
}
