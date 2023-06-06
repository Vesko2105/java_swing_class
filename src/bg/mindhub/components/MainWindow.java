package bg.mindhub.components;

import bg.mindhub.Movie;
import bg.mindhub.SystemSettings;
import bg.mindhub.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

// TODO: 5/18/2023 Trim input from text fields

public class MainWindow extends JFrame implements ActionListener, ListSelectionListener, KeyListener {

    private SearchControlPanel searchControlPanel;

    private MovieDataTable movieDataTable;

    private SearchResultsPanel searchResultsPanel;

    private DataControlPanel dataControlPanel;

    public MainWindow() {
        super();
    }

    public void init() {
        //-------- Frame settings -----------
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(SystemSettings.mainBackgroundColor);
        this.setLocationRelativeTo(null);

        //-------- Title area --------

        TitlePanel titlePanel = new TitlePanel("My Movie Manager");
        this.add(titlePanel);

        //-------- Search controls area --------

        searchControlPanel = new SearchControlPanel(this, this);
        this.add(searchControlPanel);

        //-------- Movie Data --------

        movieDataTable = new MovieDataTable(this);
        movieDataTable.loadTestData();

        //-------- Search results area --------

        searchResultsPanel = new SearchResultsPanel(movieDataTable);
        this.add(searchResultsPanel);

        //-------- Data control area --------
        dataControlPanel = new DataControlPanel(this);
        this.add(dataControlPanel);

        this.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedTableRow = movieDataTable.getSelectedRow();
        if (selectedTableRow < 0) {
            dataControlPanel.clearDataFields();
            return;
        }

        Movie selectedMovie = movieDataTable.getSelectedMovie();
        dataControlPanel.updateFields(selectedMovie);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "create":
                if(!dataControlPanel.checkDataValidity()) {
                    break;
                }

                List<String> movieData = dataControlPanel.getMovieData();

                Movie newMovie = new Movie(
                        movieData.get(1),
                        Integer.parseInt(movieData.get(2)),
                        movieData.get(3),
                        movieData.get(4)
                );

                boolean addedSuccessfully = movieDataTable.addMovie(newMovie);

                if (!addedSuccessfully) {
                    JOptionPane.showMessageDialog(this, "Movie record already exists!", "Conflict", JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "delete":
                long idToDelete = dataControlPanel.getMovieId();

                if(idToDelete == -1) {
                    JOptionPane.showMessageDialog(this, "No record selected!", "Deletion Error", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                String[] options = {"Delete", "Cancel"};
                int optionChosen = JOptionPane.showOptionDialog(
                        this,
                        "Are you sure you want to delete movie with ID: " + idToDelete,
                        "Delete Confirmation",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]
                );

                if (optionChosen == 0) {
                    movieDataTable.deleteMovie(idToDelete);
                }

                break;

            case "update":
                long idToUpdate = dataControlPanel.getMovieId();

                if(idToUpdate == -1) {
                    JOptionPane.showMessageDialog(this, "No record selected!", "Update Error", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                if(!dataControlPanel.checkDataValidity()) {
                    break;
                }

                List<String> data = dataControlPanel.getMovieData();
                movieDataTable.updateMovie(data);

                break;

            case "clear":
                dataControlPanel.clearDataFields();
                movieDataTable.clearSelection();
                break;

            case "search":
                searchResultsPanel.searchFor(searchControlPanel.getSearchBarContent());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getComponent().getName().equals("searchBar")) {
            searchControlPanel.activateSearch();
        }
    }
}
