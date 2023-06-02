package bg.mindhub.components;

import bg.mindhub.Movie;
import bg.mindhub.MovieDataTableModel;
import bg.mindhub.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class MovieDataTable extends JTable {
    private MovieDataTableModel movieDataTableModel;
    private TableRowSorter<MovieDataTableModel> tableRowSorter;
    private TableColumnAdjuster tableColumnAdjuster;
    public MovieDataTable() {
        movieDataTableModel = new MovieDataTableModel();
        tableRowSorter = new TableRowSorter<>(movieDataTableModel);
        tableColumnAdjuster = new TableColumnAdjuster(this);

        this.setModel(movieDataTableModel);
        this.setRowSorter(tableRowSorter);

        this.refresh();
    }

    public void addListSelectionListener(ListSelectionListener listSelectionListener) {
        this.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    public Movie getSelectedMovie() {
        int movieIndex = tableRowSorter.convertRowIndexToModel(getSelectedRow());
        return movieDataTableModel.getMovieAt(movieIndex);
    }

    public boolean addMovie(Movie movie) {
        boolean addedSuccessfully = movieDataTableModel.addMovie(movie);
        this.refresh();
        return addedSuccessfully;
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
            this.refresh();
        } catch (Exception ignored) {}
    }

    public void deleteMovie(long id) {
        movieDataTableModel.deleteMovie(id);
        this.refresh();
    }

    public void loadTestData() {
        if(movieDataTableModel.getRowCount() == 0) {
            Movie.getTestData().forEach(movieDataTableModel::addMovie);
        }
    }

    public void setRowFilter(RowFilter<MovieDataTableModel, Object> rowFilter) {
        tableRowSorter.setRowFilter(rowFilter);
    }

    public void refresh() {
        tableColumnAdjuster.adjustColumns();
    }
}
