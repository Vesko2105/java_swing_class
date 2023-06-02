package bg.mindhub.components;

import bg.mindhub.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataControlPanel extends JPanel {

    private List<JTextComponent> movieDataFields;

    public DataControlPanel(ActionListener buttonActionListener) {
        super(new GridBagLayout());


        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 100, 20, 100),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(184, 207, 229)),
                        "Edit Data",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        ));
        this.setBackground(SystemSettings.mainBackgroundColor);

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
        String[] columnNames = Arrays.copyOf(Movie.TABLE_COLUMN_NAMES, tableColumnCount);

        for (int i = 0; i < tableColumnCount - 1; i++) {
            JLabel label = new JLabel(columnNames[i]);
            label.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
            this.add(label, constraints);
            constraints.gridy++;
        }

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.weightx = 0.5;
        constraints.insets.right = 30;
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel(columnNames[tableColumnCount - 1]);
        label.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
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

            JTextField currentTextField = new MyTextField();
            movieDataFields.add(i, currentTextField);
            currentTextField.setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
            currentTextField.setFont(SystemSettings.GLOBAL_FONT);

            this.add(currentTextField, constraints);
            constraints.gridy++;
        }

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 3;
        constraints.weightx = 0.5;
        constraints.insets.set(10, 30, 10, 30);
        constraints.fill = GridBagConstraints.BOTH;

        JTextArea textArea = new MyTextArea();
        textArea.setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        textArea.setFont(SystemSettings.GLOBAL_FONT);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        movieDataFields.add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, constraints);

        movieDataFields.get(0).setEditable(false);

        //********** Data control buttons **********
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 6;
        constraints.weightx = 1;
        constraints.ipady = 0;
        constraints.insets.set(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(SystemSettings.mainBackgroundColor);
        ((FlowLayout)buttonsPanel.getLayout()).setHgap(30);
        this.add(buttonsPanel, constraints);

        Dimension preferredButtonSize = new Dimension(100, 30);
        Insets buttonMargins = new Insets(0, 0, 0, 0);

        ImageIcon clearIcon = new ImageIcon("icons/clearIcon.png");
        Image clearIconImage = clearIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        clearIcon = new ImageIcon(clearIconImage);

        JButton buttonclear = new JButton("CLEAR", clearIcon);
        buttonclear.setMargin(buttonMargins);
        buttonclear.setPreferredSize(preferredButtonSize);
        buttonclear.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonclear.setActionCommand("clear");
        buttonclear.addActionListener(buttonActionListener);
        buttonsPanel.add(buttonclear);

        ImageIcon updateIcon = new ImageIcon("icons/updateIcon.png");
        Image updateIconImage = updateIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        updateIcon = new ImageIcon(updateIconImage);

        JButton buttonUpdate = new JButton("UPDATE", updateIcon);
        buttonUpdate.setMargin(buttonMargins);
        buttonUpdate.setPreferredSize(preferredButtonSize);
        buttonUpdate.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonUpdate.setActionCommand("update");
        buttonUpdate.addActionListener(buttonActionListener);
        buttonsPanel.add(buttonUpdate);

        ImageIcon createIcon = new ImageIcon("icons/createIcon.png");
        Image createIconImage = createIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        createIcon = new ImageIcon(createIconImage);

        JButton buttonCreate = new JButton("CREATE", createIcon);
        buttonCreate.setActionCommand("create");
        buttonCreate.setMargin(buttonMargins);
        buttonCreate.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonCreate.setPreferredSize(preferredButtonSize);
        buttonCreate.addActionListener(buttonActionListener);
        constraints.gridx = 1;
        buttonsPanel.add(buttonCreate);

        ImageIcon deleteIcon = new ImageIcon("icons/deleteIcon.png");
        Image deleteIconImage = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        deleteIcon = new ImageIcon(deleteIconImage);

        JButton buttonDelete = new JButton("DELETE", deleteIcon);
        buttonDelete.setActionCommand("delete");
        buttonDelete.setMargin(buttonMargins);
        buttonDelete.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonDelete.setPreferredSize(preferredButtonSize);
        buttonDelete.addActionListener(buttonActionListener);
        constraints.gridx = 2;
        buttonsPanel.add(buttonDelete);
    }

    public void updateFields(Movie movie) {
        String[] data = movie.toTableData();
        for (int i = 0; i < movieDataFields.size(); i++) {
            movieDataFields.get(i).setText(data[i]);
        }
    }

    public void clearDataFields() {
        for (JTextComponent movieDataField : movieDataFields) {
            movieDataField.setText("");
        }
    }

    public long getIdFieldValue() {
        String id = movieDataFields.get(0).getText().trim();
        if (id.isBlank()) {
            return -1L;
        }
        return Long.parseLong(id);
    }

    public List<String> getFieldValues() {
        if (!checkDataValidity()) {
            return Collections.emptyList();
        }
        return movieDataFields.stream().map(JTextComponent::getText).collect(Collectors.toList());
    }

    public Movie createMovie() {
        if (!checkDataValidity()) {
            return null;
        }
        return new Movie(
                movieDataFields.get(1).getText().trim(),
                Integer.parseInt(movieDataFields.get(2).getText().trim()),
                movieDataFields.get(3).getText().trim(),
                movieDataFields.get(4).getText().trim()
        );
    }

    private boolean checkDataValidity() {
        StringJoiner emptyFields = new StringJoiner(", ");
        short emptyFieldsCount = 0;

        for (int i = 1; i < movieDataFields.size(); i++) {
            if (movieDataFields.get(i).getText().isBlank()) {
                TextFieldHighlighter.markAsError(movieDataFields.get(i));
                emptyFields.add(Movie.TABLE_COLUMN_NAMES[i]);
                emptyFieldsCount++;
            }
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
            TextFieldHighlighter.markAsError(yearReleasedField);
            JOptionPane.showMessageDialog(this, "Invalid movie release year!", "Invalid Data", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
