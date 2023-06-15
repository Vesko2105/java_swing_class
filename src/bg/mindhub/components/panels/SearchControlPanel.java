package bg.mindhub.components.panels;

import bg.mindhub.Genre;
import bg.mindhub.SystemSettings;
import bg.mindhub.components.MyCheckbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchControlPanel extends JPanel implements KeyListener {
    private final JTextField searchBar;
    private final JButton searchButton;

    public static String SEARCH_ACTION_COMMAND = "search";


    /**
     * @param actionListener the action listener that will be added to the button
     */
    public SearchControlPanel(ActionListener actionListener, ItemListener itemListener) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);

        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBarPanel.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);

        //The search bar settings
        searchBar = new JTextField(30);
        searchBar.setName("searchBar");
        searchBar.setFont(SystemSettings.GLOBAL_FONT);
        searchBar.addKeyListener(this);
        searchBarPanel.add(searchBar);

        //The search button settings
        searchButton = new JButton("SEARCH");
        searchButton.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        searchButton.setActionCommand(SEARCH_ACTION_COMMAND);
        searchButton.addActionListener(actionListener);
        searchBarPanel.add(searchButton);

        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filtersPanel.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);
        filtersPanel.setPreferredSize(new Dimension(1000, 200));

        for (Genre genre : Genre.values()) {
            MyCheckbox newCheckbox = new MyCheckbox(genre.toString(), itemListener);
            filtersPanel.add(newCheckbox);
        }

        this.add(searchBarPanel);
        this.add(filtersPanel);
    }


    /**
     * @return the contents of the search bar
     */
    public String getSearchBarContent() {
        return searchBar.getText().trim();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getComponent().getName().equals("searchBar")) {
            searchButton.doClick();
        }
    }
}
