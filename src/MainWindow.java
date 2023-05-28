import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

// TODO: 5/18/2023 Trim input from text fields

public class MainWindow extends JFrame implements ActionListener, ListSelectionListener, KeyListener, FocusListener {

    private JTable searchResultTable;
    TableColumnAdjuster tableColumnAdjuster;


    private List<JTextComponent> movieDataFields;

    private JTextField searchBar;
    private JButton searchButton;

    public MainWindow() {
        super();
        init();
    }

    private void init() {
        //-------- Frame settings -----------
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(SystemSettings.mainBackgroundColor);
        this.setLocationRelativeTo(null);

        //-------- Title area --------
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(13, 50, 79));
        this.add(titlePanel);

        JLabel title = new JLabel("My Movie Manager");
        title.setFont(SystemSettings.GLOBAL_FONT.deriveFont(32F));
        title.setForeground(new Color(218, 221, 222, 255));
        titlePanel.add(title);

        //-------- Search controls area --------
        JPanel searchControlsPanel = new JPanel(new FlowLayout());
        searchControlsPanel.setBackground(SystemSettings.mainBackgroundColor);
        this.add(searchControlsPanel);

        searchBar = new JTextField(30);
        searchBar.setFont(SystemSettings.GLOBAL_FONT);
        searchBar.addKeyListener(this);
        searchControlsPanel.add(searchBar);

        searchButton = new JButton("SEARCH");
        searchButton.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        searchButton.setActionCommand("search");
        searchButton.addActionListener(this);
        searchControlsPanel.add(searchButton);

        //-------- Search results area --------
        JPanel searchResultPanel = new JPanel(new BorderLayout());
        searchResultPanel.setBackground(SystemSettings.mainBackgroundColor);
        searchResultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 50, 0, 50),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(SystemSettings.mainDarkColor),
                        "Results",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        ));
        this.add(searchResultPanel);

        TableDataModel tableDataModel = new TableDataModel();
        TableRowSorter<TableDataModel> tableRowSorter = new TableRowSorter<>(tableDataModel);

        searchResultTable = new JTable(tableDataModel);
        searchResultTable.setRowSorter(tableRowSorter);
        searchResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        searchResultTable.setFont(SystemSettings.GLOBAL_FONT);
        searchResultTable.setBorder(null);
        searchResultTable.addKeyListener(this);

        Movie.getTestData().forEach(tableDataModel::addMovie);

        tableColumnAdjuster = new TableColumnAdjuster(searchResultTable);
        tableColumnAdjuster.adjustColumns();

        JScrollPane scrollablePane = new JScrollPane(searchResultTable);
        scrollablePane.setBackground(SystemSettings.mainBackgroundColor);
        scrollablePane.setCorner(
                JScrollPane.UPPER_RIGHT_CORNER,
                new TableCorner(searchResultTable.getTableHeader().getBackground())
        );
        scrollablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        searchResultPanel.add(scrollablePane, BorderLayout.CENTER);

        //-------- Data control area --------
        JPanel dataControlPanel = new JPanel(new GridBagLayout());
        dataControlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 100, 20, 100),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(184, 207, 229)),
                        "Edit Data",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        ));
        dataControlPanel.setBackground(SystemSettings.mainBackgroundColor);
        this.add(dataControlPanel);

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

        for (int i = 0; i < Movie.TABLE_COLUMN_NAMES.length - 1; i++) {
            JLabel label = new JLabel(tableDataModel.getColumnName(i));
            label.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
            dataControlPanel.add(label, constraints);
            constraints.gridy++;
        }

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.weightx = 0.5;
        constraints.insets.right = 30;
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel(tableDataModel.getColumnName(Movie.TABLE_COLUMN_NAMES.length - 1));
        label.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        dataControlPanel.add(label, constraints);

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
            currentTextField.addFocusListener(this);

            dataControlPanel.add(currentTextField, constraints);
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
        textArea.addFocusListener(this);
        movieDataFields.add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        dataControlPanel.add(scrollPane, constraints);

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
        dataControlPanel.add(buttonsPanel, constraints);

        Dimension preferredButtonSize = new Dimension(100, 30);
        Insets buttonMargins = new Insets(0, 0, 0, 0);

        ImageIcon saveIcon = new ImageIcon("icons/saveIcon.png");
        Image saveIconImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        saveIcon = new ImageIcon(saveIconImage);

        JButton buttonSave = new JButton("SAVE", saveIcon);
        buttonSave.setMargin(buttonMargins);
        buttonSave.setPreferredSize(preferredButtonSize);
        buttonSave.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonSave.setActionCommand("save");
        buttonSave.addActionListener(this);
        buttonsPanel.add(buttonSave);

        ImageIcon createIcon = new ImageIcon("icons/createIcon.png");
        Image createIconImage = createIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        createIcon = new ImageIcon(createIconImage);

        JButton buttonCreate = new JButton("CREATE", createIcon);
        buttonCreate.setActionCommand("create");
        buttonCreate.setMargin(buttonMargins);
        buttonCreate.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonCreate.setPreferredSize(preferredButtonSize);
        buttonCreate.addActionListener(this);
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
        buttonDelete.addActionListener(this);
        constraints.gridx = 2;
        buttonsPanel.add(buttonDelete);

        ListSelectionModel tableSelectionModel = searchResultTable.getSelectionModel();
        tableSelectionModel.addListSelectionListener(this);

        this.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedTableRow = searchResultTable.getSelectionModel().getMinSelectionIndex();
        if (selectedTableRow < 0) {
            for (JTextComponent movieDataField : movieDataFields) {
                movieDataField.setText("");
            }
            return;
        }

        int movieIndex = searchResultTable.getRowSorter().convertRowIndexToModel(selectedTableRow);
        for (int i = 0; i < movieDataFields.size(); i++) {
            movieDataFields.get(i).setText((String) searchResultTable.getModel().getValueAt(movieIndex, i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TableDataModel tableDataModel = (TableDataModel) searchResultTable.getModel();

        switch (e.getActionCommand()) {
            case "create":
                if (!checkDataValidity()) {
                    return;
                }
                boolean createdSuccessfully = tableDataModel.addMovie(new Movie(
                        movieDataFields.get(1).getText().trim(),
                        Integer.parseInt(movieDataFields.get(2).getText().trim()),
                        movieDataFields.get(3).getText().trim(),
                        movieDataFields.get(4).getText().trim()
                ));
                if (!createdSuccessfully) {
                    JOptionPane.showMessageDialog(this, "Movie record already exists!", "Conflict", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "delete":
                String id = movieDataFields.get(0).getText().trim();
                if (id.isBlank()) {
                    JOptionPane.showMessageDialog(this, "No record selected!", "Deletion Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String[] options = {"Delete", "Cancel"};
                int optionChosen = JOptionPane.showOptionDialog(
                        this,
                        "Are you sure you want to delete movie with ID: " + id,
                        "Delete Confirmation",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]
                );
                if (optionChosen == 0) {
                    tableDataModel.deleteMovie(Long.parseLong(id));
                }
                break;
            case "save":
                if (!checkForEmptySelection() || !checkDataValidity()) {
                    return;
                }
                checkDataValidity();
                tableDataModel.updateMovie(
                        Long.parseLong(movieDataFields.get(0).getText().trim()),
                        movieDataFields.get(1).getText().trim(),
                        Integer.parseInt(movieDataFields.get(2).getText().trim()),
                        movieDataFields.get(3).getText().trim(),
                        movieDataFields.get(4).getText().trim()
                );
                break;
            case "search":
                String searchTerm = searchBar.getText().trim();
                TableRowSorter<TableDataModel> tableSorter = (TableRowSorter<TableDataModel>) searchResultTable.getRowSorter();
                RowFilter<TableDataModel, Object> rowFilter = RowFilter.regexFilter(searchTerm);
                tableSorter.setRowFilter(rowFilter);
                tableDataModel.fireTableDataChanged();
                searchResultTable.getSelectionModel().clearSelection();
                tableColumnAdjuster.adjustColumns();
        }
    }
    
    private boolean checkForEmptySelection() {
        String id = movieDataFields.get(0).getText();
        if (id.isBlank()) {
            JOptionPane.showMessageDialog(this, "No record selected!", "Deletion Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkDataValidity() {
        StringJoiner emptyFields = new StringJoiner(", ");
        short emptyFieldsCount = 0;

        for (int i = 1; i < movieDataFields.size(); i++) {
            if (movieDataFields.get(i).getText().isBlank()) {
                ((ErrorHighlightable) movieDataFields.get(i)).markAsError();
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


        try {
            Integer.parseInt(movieDataFields.get(0).getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid movie release year!", "Invalid Data", JOptionPane.ERROR_MESSAGE);
        }

        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getComponent() == searchBar) {
            searchButton.doClick();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent() instanceof ErrorHighlightable) {
            ((ErrorHighlightable) e.getComponent()).reset();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {}
}
