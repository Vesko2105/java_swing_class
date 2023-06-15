package bg.mindhub.components.panels;

import bg.mindhub.*;
import bg.mindhub.components.MyButton;
import bg.mindhub.components.MyDropdown;
import bg.mindhub.components.MyTextArea;
import bg.mindhub.components.MyTextField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataControlPanel extends JPanel {

    private static final int DROPDOWN_Y = 3;
    private List<JTextComponent> movieDataFields;
    private MyDropdown genreDropdown;

    public DataControlPanel(ActionListener buttonActionListener) {
        super(new GridBagLayout());

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 100, 20, 100),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(SystemSettings.MAIN_DARK_COLOR),
                        "Edit Data",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        ));
        this.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();

        //********** Data control labels **********
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.1;
        constraints.insets.set(10, 30, 10, 0);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;

        int tableColumnCount = Movie.TABLE_COLUMN_NAMES.length;
        String[] columnNames = Movie.TABLE_COLUMN_NAMES;

        for (int i = 0; i < tableColumnCount - 1; i++) {
            JLabel label = new JLabel(columnNames[i]);
            this.add(label, constraints);
            constraints.gridy++;
        }

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 0.5;
        constraints.insets.right = 30;
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel(columnNames[tableColumnCount - 1]);
        this.add(label, constraints);

        //********** Data control text fields **********
        movieDataFields = new ArrayList<>();

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.weightx = 0.4;
        constraints.ipady = 5;
        constraints.insets.set(10, 0, 10, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < Movie.TABLE_COLUMN_NAMES.length - 1; i++) {
            if (i != DROPDOWN_Y) {
                JTextField currentTextField = new MyTextField();

                movieDataFields.add(currentTextField);

                this.add(currentTextField, constraints);
            }
            constraints.gridy++;
        }

        constraints.gridy = DROPDOWN_Y;

        genreDropdown = new MyDropdown(Genre.values(), "Select genre...");
        this.add(genreDropdown, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = movieDataFields.size() - 1;
        constraints.weightx = 0.5;
        constraints.insets.set(10, 30, 10, 30);
        constraints.fill = GridBagConstraints.BOTH;

        JTextArea textArea = new MyTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        movieDataFields.add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, constraints);

        movieDataFields.get(0).setEditable(false);

        //********** Data control buttons **********
        constraints.gridx = 0;
        constraints.gridy = movieDataFields.size();
        constraints.gridwidth = 6;
        constraints.weightx = 1;
        constraints.ipady = 0;
        constraints.insets.set(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        FlowLayout buttonPanelLayout = new FlowLayout();
        buttonPanelLayout.setHgap(30);

        JPanel buttonsPanel = new JPanel(buttonPanelLayout);
        buttonsPanel.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);
        this.add(buttonsPanel, constraints);

        MyButton clearButton = new MyButton("CLEAR", "clear", buttonActionListener, SystemSettings.CLEAR_ICON_PATH);
        buttonsPanel.add(clearButton);

        MyButton updateButton = new MyButton("UPDATE", "update", buttonActionListener, SystemSettings.UPDATE_ICON_PATH);
        buttonsPanel.add(updateButton);

        MyButton createButton = new MyButton("CREATE", "create", buttonActionListener, SystemSettings.CREATE_ICON_PATH);
        buttonsPanel.add(createButton);

        MyButton deleteButton = new MyButton("DELETE", "delete", buttonActionListener, SystemSettings.DELETE_ICON_PATH);
        buttonsPanel.add(deleteButton);
    }

    public void updateFields(Movie movie) {
        movieDataFields.get(0).setText(String.valueOf(movie.getId()));
        movieDataFields.get(1).setText(movie.getTitle());
        movieDataFields.get(2).setText(String.valueOf(movie.getYearReleased()));
        genreDropdown.setSelectedItem(movie.getGenre().toString());
        movieDataFields.get(3).setText(movie.getDirector());
        movieDataFields.get(4).setText(movie.getDescription());

        for (JTextComponent movieDataField : movieDataFields) {
            ComponentHighlighter.reset(movieDataField);
        }
        ComponentHighlighter.reset(genreDropdown);
    }

    public void clearDataFields() {
        for (JTextComponent movieDataField : movieDataFields) {
            movieDataField.setText("");
            ComponentHighlighter.reset(movieDataField);
        }
        genreDropdown.setSelectedIndex(0);
        ComponentHighlighter.reset(genreDropdown);
    }

    public long getMovieId() {
        String id = movieDataFields.get(0).getText().trim();
        if (id.isBlank()) {
            return -1L;
        }
        return Long.parseLong(id);
    }

    public Map<String, String> getMovieData() {
        Map<String, String> movieData = new HashMap<>();
        movieData.put(Movie.ID, movieDataFields.get(0).getText());
        movieData.put(Movie.TITLE, movieDataFields.get(1).getText());
        movieData.put(Movie.YEAR_RELEASED, movieDataFields.get(2).getText());
        movieData.put(Movie.GENRE, genreDropdown.getSelection());
        movieData.put(Movie.DIRECTOR, movieDataFields.get(3).getText());
        movieData.put(Movie.DESCRIPTION, movieDataFields.get(4).getText());

        return movieData;
    }

    public boolean checkDataValidity() {
        StringJoiner emptyFields = new StringJoiner(", ");
        short emptyFieldsCount = 0;

        for (int i = 1; i < movieDataFields.size(); i++) {
            if (movieDataFields.get(i).getText().isBlank()) {
                ComponentHighlighter.markAsError(movieDataFields.get(i));
                emptyFields.add(Movie.TABLE_COLUMN_NAMES[i]);
                emptyFieldsCount++;
            }
        }

        if (Genre.from((String) genreDropdown.getSelectedItem()) == null) {
            ComponentHighlighter.markAsError(genreDropdown);
            emptyFields.add(Movie.TABLE_COLUMN_NAMES[DROPDOWN_Y]);
            emptyFieldsCount++;
        }

        if (emptyFieldsCount > 0) {
            boolean multipleEmptyFields = emptyFieldsCount > 1;
            String message = String.format(
                    "The following data field%s %s empty: %s",
                    multipleEmptyFields ? "s" : "",
                    multipleEmptyFields ? "are" : "is",
                    emptyFields
            );
            JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return false;
        }


        JTextComponent yearReleasedField = movieDataFields.get(2);
        try {
            Integer.parseInt(yearReleasedField.getText().trim());
        } catch (NumberFormatException e) {
            ComponentHighlighter.markAsError(yearReleasedField);
            JOptionPane.showMessageDialog(this, "Invalid movie release year!", "Invalid Data", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
