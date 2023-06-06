package bg.mindhub.components;

import bg.mindhub.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchResultsPanel extends JPanel {

    private final MovieDataTable movieDataTable;

    public SearchResultsPanel(MovieDataTable movieDataTable) {
        super(new BorderLayout());

        this.setMaximumSize(new Dimension(1000, 100));
        this.setBackground(SystemSettings.mainBackgroundColor);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 50, 0, 50),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(SystemSettings.mainDarkColor),
                        "Results",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        ));

        this.movieDataTable = movieDataTable;
        movieDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        movieDataTable.setFont(SystemSettings.GLOBAL_FONT);
        movieDataTable.setBorder(null);

        TableColumnAdjuster tableColumnAdjuster = new TableColumnAdjuster(movieDataTable);
        tableColumnAdjuster.setDynamicAdjustment(true);
        tableColumnAdjuster.adjustColumns();

        JScrollPane scrollablePane = new JScrollPane(movieDataTable);
        scrollablePane.setBackground(SystemSettings.mainBackgroundColor);
        scrollablePane.setCorner(
                JScrollPane.UPPER_RIGHT_CORNER,
                new TableCorner(movieDataTable.getTableHeader().getBackground())
        );
        scrollablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(scrollablePane, BorderLayout.CENTER);
    }

    public void searchFor(String searchTerm) {
        //This makes a case-insensitive regex derived
        //from the input in the search bar
        String regex = "(?i)" + searchTerm;
        RowFilter<MovieDataTableModel, Object> rowFilter = RowFilter.regexFilter(regex);
        movieDataTable.filterData(rowFilter);
    }
}
