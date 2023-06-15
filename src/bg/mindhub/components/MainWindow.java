package bg.mindhub.components;

import bg.mindhub.Genre;
import bg.mindhub.Movie;
import bg.mindhub.SystemSettings;
import bg.mindhub.components.panels.DataControlPanel;
import bg.mindhub.components.panels.SearchControlPanel;
import bg.mindhub.components.panels.SearchResultsPanel;
import bg.mindhub.components.panels.TitlePanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame implements ActionListener, ListSelectionListener, ItemListener {

    private SearchControlPanel searchControlPanel;

    private MovieDataTable movieDataTable;

    private DataControlPanel dataControlPanel;

    public MainWindow() {
        super();
    }

    public void init() {
        //-------- Frame settings -----------
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon(SystemSettings.APP_ICON_PATH);
        this.setIconImage(imageIcon.getImage());
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

        SearchResultsPanel searchResultsPanel = new SearchResultsPanel(movieDataTable);
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

                Map<String, String> movieData = dataControlPanel.getMovieData();

                Movie newMovie = new Movie(
                        movieData.get(Movie.TITLE),
                        Integer.parseInt(movieData.get(Movie.YEAR_RELEASED)),
                        Genre.from(movieData.get(Movie.GENRE)),
                        movieData.get(Movie.DIRECTOR),
                        movieData.get(Movie.DESCRIPTION)
                );

                boolean addedSuccessfully = movieDataTable.addMovie(newMovie);

                if (!addedSuccessfully) {
                    JOptionPane.showMessageDialog(this, "Movie record already exists!", "Conflict", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Movie record created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(SystemSettings.SUCCESS_ICON_PATH));
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

                JOptionPane.showMessageDialog(this, "Movie record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(SystemSettings.SUCCESS_ICON_PATH));

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

                Map<String, String> data = dataControlPanel.getMovieData();
                movieDataTable.updateMovie(data);

                JOptionPane.showMessageDialog(this, "Movie record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(SystemSettings.SUCCESS_ICON_PATH));

                break;

            case "clear":
                dataControlPanel.clearDataFields();
                movieDataTable.clearSelection();
                break;

            case "search":
                movieDataTable.searchFor(searchControlPanel.getSearchBarContent());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (!(e.getSource() instanceof MyCheckbox)) {
            return;
        }

        MyCheckbox eventSource = (MyCheckbox) e.getSource();

        Genre selectedGenre = Genre.from(eventSource.getText());

        if (selectedGenre == null) {
            return;
        }

        if (e.getStateChange() == ItemEvent.SELECTED) {
            movieDataTable.addGenreFilter(selectedGenre);
        } else {
            movieDataTable.removeGenreFilter(selectedGenre);
        }
    }
}
