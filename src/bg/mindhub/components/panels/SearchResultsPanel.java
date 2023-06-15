package bg.mindhub.components.panels;

import bg.mindhub.*;
import bg.mindhub.components.MovieDataTable;
import bg.mindhub.components.TableCorner;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchResultsPanel extends JPanel {

    private final MovieDataTable movieDataTable;

    public SearchResultsPanel(MovieDataTable movieDataTable) {
        super(new BorderLayout());

        this.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 50, 0, 50),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(SystemSettings.MAIN_DARK_COLOR),
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
        scrollablePane.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);
        scrollablePane.setCorner(
                JScrollPane.UPPER_RIGHT_CORNER,
                new TableCorner(movieDataTable.getTableHeader().getBackground())
        );
        scrollablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(scrollablePane, BorderLayout.CENTER);
    }
}
