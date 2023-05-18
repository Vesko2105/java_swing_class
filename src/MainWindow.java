import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener, ListSelectionListener {
    private JFrame window;
    private static final Font GLOBAL_FONT = new Font("Consolas", Font.PLAIN, 14);
    private static Color mainBackgroundColor = new Color(38, 142, 213, 255);
    private static Color mainDarkColor = new Color(15, 58, 87);

    private JTable searchResultTable;

    private JTextField[] movieDataFields;
    private JTextField searchBar;

    public MainWindow() {
        init();
    }

    private void init() {
        //-------- Frame settings -----------
        window = new JFrame();
        window.setSize(600, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.setBackground(mainBackgroundColor);
        window.setLocationRelativeTo(null);

        //-------- Title area --------
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(13, 50, 79));
        window.add(titlePanel);

        JLabel title = new JLabel("My Movie Manager");
        title.setFont(GLOBAL_FONT.deriveFont(32F));
        title.setForeground(new Color(218, 221, 222, 255));
        titlePanel.add(title);

        //-------- Search controls area --------
        JPanel searchControlsPanel = new JPanel(new FlowLayout());
        searchControlsPanel.setBackground(mainBackgroundColor);
        window.add(searchControlsPanel);

        searchBar = new JTextField(30);
        searchBar.setFont(GLOBAL_FONT);
        searchControlsPanel.add(searchBar);

        JButton searchButton = new JButton("SEARCH");
        searchButton.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        searchButton.setActionCommand("search");
        searchButton.addActionListener(this);
        searchControlsPanel.add(searchButton);

        //-------- Search results area --------
        JPanel searchResultPanel = new JPanel(new BorderLayout());
        searchResultPanel.setBackground(mainBackgroundColor);
        searchResultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 50, 0, 50),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(mainDarkColor),
                        "Results",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        ));
        window.add(searchResultPanel);

        TableDataModel tableDataModel = new TableDataModel();
        TableRowSorter<TableDataModel> tableRowSorter = new TableRowSorter<>(tableDataModel);

        searchResultTable = new JTable(tableDataModel);
        searchResultTable.setRowSorter(tableRowSorter);
        searchResultTable.setFont(GLOBAL_FONT);
        searchResultTable.setBorder(null);

        Movie.getTestData().forEach(tableDataModel::addMovie);

        JScrollPane scrollablePane = new JScrollPane(searchResultTable);
        scrollablePane.setBackground(mainBackgroundColor);
        scrollablePane.setCorner(
                JScrollPane.UPPER_RIGHT_CORNER,
                new TableCorner(searchResultTable.getTableHeader().getBackground())
        );
        scrollablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollablePane.setPreferredSize(new Dimension(400, 150));
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
        dataControlPanel.setBackground(mainBackgroundColor);
        window.add(dataControlPanel);

        GridBagConstraints constraints = new GridBagConstraints();

        //********** Data control text fields **********
        movieDataFields = new JTextField[tableDataModel.getColumnCount()];

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.ipady = 5;
        constraints.insets = new Insets(10, 30, 10, 30);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < movieDataFields.length; i++) {

            movieDataFields[i] = new JTextField(10);
            movieDataFields[i].setBorder(BorderFactory.createLineBorder(mainDarkColor));
            movieDataFields[i].setFont(GLOBAL_FONT);

            JScrollPane scrollPane = new JScrollPane(movieDataFields[i]);
            scrollPane.setBorder(null);
            dataControlPanel.add(scrollPane, constraints);
            constraints.gridy++;
        }

        movieDataFields[0].setEditable(false);

        //********** Data control labels **********
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.insets = new Insets(10, 30, 10, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        for (int i = 0; i < movieDataFields.length; i++) {
            JLabel label = new JLabel(tableDataModel.getColumnName(i));
            label.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
            dataControlPanel.add(label, constraints);
            constraints.gridy++;
        }

        //********** Data control buttons **********
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.ipady = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;

        Dimension preferredButtonDimension = new Dimension(100, 30);

        JButton buttonSave = new JButton("SAVE");
        buttonSave.setPreferredSize(preferredButtonDimension);
        buttonSave.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonSave.setActionCommand("save");
        buttonSave.addActionListener(this);
        dataControlPanel.add(buttonSave, constraints);

        JButton buttonAdd = new JButton("ADD");
        buttonAdd.setActionCommand("add");
        buttonAdd.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonAdd.setPreferredSize(preferredButtonDimension);
        buttonAdd.addActionListener(this);
        constraints.gridx = 1;
        dataControlPanel.add(buttonAdd, constraints);

        JButton buttonDelete = new JButton("DELETE");
        buttonDelete.setActionCommand("delete");
        buttonDelete.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonDelete.setPreferredSize(preferredButtonDimension);
        buttonDelete.addActionListener(this);
        constraints.gridx = 2;
        dataControlPanel.add(buttonDelete, constraints);

        ListSelectionModel tableSelectionModel = searchResultTable.getSelectionModel();
        tableSelectionModel.addListSelectionListener(this);

        window.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedTableRow = searchResultTable.getSelectionModel().getMinSelectionIndex();
        if (selectedTableRow < 0) {
            for (JTextField movieDataField : movieDataFields) {
                movieDataField.setText("");
            }
            return;
        }

        int movieIndex = searchResultTable.getRowSorter().convertRowIndexToModel(selectedTableRow);
        for (int i = 0; i < movieDataFields.length; i++) {
            movieDataFields[i].setText((String) searchResultTable.getModel().getValueAt(movieIndex, i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TableDataModel tableDataModel = (TableDataModel) searchResultTable.getModel();

        switch (e.getActionCommand()) {
            case "add":
                boolean addedSuccessfully = tableDataModel.addMovie(new Movie(
                        movieDataFields[1].getText(),
                        Integer.parseInt(movieDataFields[2].getText()),
                        movieDataFields[3].getText()
                ));
                if (!addedSuccessfully) {
                    JOptionPane.showMessageDialog(null, "Movie record already exists!");
                }
                break;
            case "delete":
                tableDataModel.deleteMovie(Long.parseLong(movieDataFields[0].getText()));
                break;
            case "save":
                tableDataModel.updateMovie(new Movie(
                        Long.parseLong(movieDataFields[0].getText()),
                        movieDataFields[1].getText(),
                        Integer.parseInt(movieDataFields[2].getText()),
                        movieDataFields[3].getText()
                ));
                break;
            case "search":
                String searchTerm = searchBar.getText().trim();
                TableRowSorter<TableDataModel> tableSorter = (TableRowSorter<TableDataModel>) searchResultTable.getRowSorter();
                RowFilter<TableDataModel, Object> rowFilter = RowFilter.regexFilter(searchTerm);
                tableSorter.setRowFilter(rowFilter);
                tableDataModel.fireTableDataChanged();
        }
    }
}
