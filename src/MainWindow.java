import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener {
    private JFrame window;
    private static final Font GLOBAL_FONT = new Font("Consolas", Font.PLAIN, 14);
    private static Color mainBackgroundColor = new Color(38, 142, 213, 255);
    private static Color mainDarkColor = new Color(15, 58, 87);

    private TableDataModel tableDataModel;

    private JTextField[] textFields;

    public MainWindow() {
        init();
    }

    private void init() {
        //Frame settings
        window = new JFrame();
        window.setSize(600, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.setBackground(mainBackgroundColor);
        window.setLocationRelativeTo(null);

        //Title area
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(13, 50, 79));
        window.add(titlePanel);

        JLabel title = new JLabel("My Movie Manager");
        title.setFont(GLOBAL_FONT.deriveFont(32F));
        title.setForeground(new Color(218, 221, 222, 255));
        titlePanel.add(title);

        //Search controls area
        JPanel searchControlsPanel = new JPanel(new FlowLayout());
        searchControlsPanel.setBackground(mainBackgroundColor);
        window.add(searchControlsPanel);

        JTextField searchBar = new JTextField(30);
        searchBar.setFont(GLOBAL_FONT);
        searchControlsPanel.add(searchBar);

        JButton searchButton = new JButton("SEARCH");
        searchButton.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        searchControlsPanel.add(searchButton);

        //Search results area
        JPanel searchResultPanel = new JPanel();
        searchResultPanel.setBackground(mainBackgroundColor);
        searchResultPanel.setBorder(BorderFactory.createLineBorder(mainDarkColor, 1));
        window.add(searchResultPanel);

        tableDataModel = new TableDataModel();
        Movie.getTestData().forEach(tableDataModel::addMovie);
        JTable searchResultTable = new JTable(tableDataModel);
        searchResultTable.setFont(GLOBAL_FONT);
        searchResultTable.getColumnModel().getColumn(0).setPreferredWidth(20);

        JScrollPane scrollablePane = new JScrollPane(searchResultTable);
        scrollablePane.setPreferredSize(new Dimension(500, 200));
        searchResultPanel.add(scrollablePane);

        //Data control area
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

        JLabel label1 = new JLabel(tableDataModel.getColumnName(0));
        label1.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.insets = new Insets(10, 30, 10, 0);
        constraints.anchor = GridBagConstraints.LINE_START;
        dataControlPanel.add(label1, constraints);

        JLabel label2 = new JLabel(tableDataModel.getColumnName(1));
        label2.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        constraints.gridy = 1;
        dataControlPanel.add(label2, constraints);

        JLabel label3 = new JLabel(tableDataModel.getColumnName(2));
        label3.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        constraints.gridy = 2;
        dataControlPanel.add(label3, constraints);

        JLabel label4 = new JLabel(tableDataModel.getColumnName(3));
        label4.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        constraints.gridy = 3;
        dataControlPanel.add(label4, constraints);

        textFields = new JTextField[tableDataModel.getColumnCount()];

        textFields[0] = new JTextField(10);
        textFields[0].setBorder(BorderFactory.createLineBorder(mainDarkColor));
        textFields[0].setFont(GLOBAL_FONT);
        textFields[0].setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.ipady = 5;
        constraints.insets = new Insets(10, 30, 10, 30);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JScrollPane scrollPane1 = new JScrollPane(textFields[0]);
        scrollPane1.setBorder(null);
        dataControlPanel.add(scrollPane1, constraints);

        textFields[1] = new JTextField(10);
        textFields[1].setFont(GLOBAL_FONT);
        textFields[1].setBorder(BorderFactory.createLineBorder(mainDarkColor));
        constraints.gridy = 1;

        JScrollPane scrollPane2 = new JScrollPane(textFields[1]);
        scrollPane2.setBorder(null);
        dataControlPanel.add(scrollPane2, constraints);

        textFields[2] = new JTextField(10);
        textFields[2].setFont(GLOBAL_FONT);
        textFields[2].setBorder(BorderFactory.createLineBorder(mainDarkColor));
        constraints.gridy = 2;

        JScrollPane scrollPane3 = new JScrollPane(textFields[2]);
        scrollPane3.setBorder(null);
        dataControlPanel.add(scrollPane3, constraints);

        textFields[3] = new JTextField(10);
        textFields[3].setFont(GLOBAL_FONT);
        textFields[3].setBorder(BorderFactory.createLineBorder(mainDarkColor));
        constraints.gridy = 3;

        JScrollPane scrollPane4 = new JScrollPane(textFields[3]);
        scrollPane4.setBorder(null);
        dataControlPanel.add(scrollPane4, constraints);

        JButton buttonSave = new JButton("SAVE");
        buttonSave.setPreferredSize(new Dimension(60, 30));
        buttonSave.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonSave.setActionCommand("save");
        buttonSave.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.ipady = 0;
        constraints.insets = new Insets(10, 15, 10, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        dataControlPanel.add(buttonSave, constraints);

        JButton buttonAdd = new JButton("ADD");
        buttonAdd.setActionCommand("add");
        buttonAdd.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonAdd.setPreferredSize(new Dimension(60, 30));
        buttonAdd.addActionListener(this);
        constraints.gridx = 1;
        dataControlPanel.add(buttonAdd, constraints);

        JButton buttonDelete = new JButton("DELETE");
        buttonDelete.setActionCommand("delete");
        buttonDelete.setFont(GLOBAL_FONT.deriveFont(Font.BOLD));
        buttonDelete.setPreferredSize(new Dimension(60, 30));
        buttonDelete.addActionListener(this);
        constraints.gridx = 2;
        constraints.insets = new Insets(0, 15, 0, 15);
        dataControlPanel.add(buttonDelete, constraints);

        ListSelectionModel tableSelectionModel = searchResultTable.getSelectionModel();
        tableSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tableSelectionModel.getMinSelectionIndex();
                if (selectedRow < 0) {
                    return;
                }
                for (int i = 0; i < textFields.length; i++) {
                    textFields[i].setText((String) tableDataModel.getValueAt(selectedRow, i));
                }
            }
        });

        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                boolean addedSuccessfully = tableDataModel.addMovie(new Movie(
                        textFields[1].getText(),
                        Integer.parseInt(textFields[2].getText()),
                        textFields[3].getText()
                ));
                if (!addedSuccessfully) {
                    JOptionPane.showMessageDialog(null, "Movie record already exists!");
                }
                break;
            case "delete":
                tableDataModel.deleteMovie(Long.parseLong(textFields[0].getText()));
                break;
            case "save":
                tableDataModel.updateMovie(new Movie(
                        Long.parseLong(textFields[0].getText()),
                        textFields[1].getText(),
                        Integer.parseInt(textFields[2].getText()),
                        textFields[3].getText()
                ));
                break;
        }
    }
}
