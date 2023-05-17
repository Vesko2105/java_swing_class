import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableDataModel extends AbstractTableModel {
    List<Movie> movies;

    public TableDataModel() {
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

    public boolean addMovie(Movie movie) {
        if (movies.contains(movie)) {
            return false;
        }
        fireTableDataChanged();
        movies.add(movie);
        return true;
    }

    public void deleteMovie(long ID) {
        movies.removeIf(movie -> movie.getId() == ID);
        fireTableDataChanged();
    }

    public void updateMovie(Movie updatedMovie) {
        movies.stream().filter(movie -> movie.getId() == updatedMovie.getId()).findFirst().ifPresent(
                movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setDirector(updatedMovie.getDirector());
                    movie.setYearReleased(updatedMovie.getYearReleased());
                }
        );
        fireTableDataChanged();
    }
//    private String[] columnNames;
//    private Object[][] data;
//
//    public TableDataModel(String[] columnNames, Object[][] data) {
//        this.columnNames = columnNames;
//        this.data = data;
//    }
//
//    public int getColumnCount() {
//        return columnNames.length;
//    }
//
//    public int getRowCount() {
//        return data.length;
//    }
//
//    public String getColumnName(int col) {
//        return columnNames[col];
//    }
//
//    public Object getValueAt(int row, int col) {
//        return data[row][col];
//    }
//
//    public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//    }
//
//    /*
//     * Don't need to implement this method unless your table's
//     * editable.
//     */
//    public boolean isCellEditable(int row, int col) {
//        return col > 0;
//    }
//
//    public void setValueAt(Object value, int row, int col) {
//        data[row][col] = value;
//        fireTableCellUpdated(row, col);
//    }
}


