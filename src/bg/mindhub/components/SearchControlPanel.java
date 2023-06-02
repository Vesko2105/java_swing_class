package bg.mindhub.components;

import bg.mindhub.SystemSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class SearchControlPanel extends JPanel {
    private JTextField searchBar;
    private JButton searchButton;

    public static String SEARCH_ACTION_COMMAND = "search";


    /**
     * @param keyListener the key listener that will be added to the search bar
     * @param actionListener the action listener that will be added to the button
     */
    public SearchControlPanel(KeyListener keyListener, ActionListener actionListener) {
        super(new FlowLayout());
        this.setBackground(SystemSettings.mainBackgroundColor);

        //The search bar settings
        searchBar = new JTextField(30);
        searchBar.setName("searchBar");
        searchBar.setFont(SystemSettings.GLOBAL_FONT);
        searchBar.addKeyListener(keyListener);
        this.add(searchBar);

        //The search button settings
        searchButton = new JButton("SEARCH");
        searchButton.setFont(SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD));
        searchButton.setActionCommand(SEARCH_ACTION_COMMAND);
        searchButton.addActionListener(actionListener);
        this.add(searchButton);
    }


    /**
     * @return the contents of the search bar
     */
    public String getSearchBarContent() {
        return searchBar.getText().trim();
    }


    /**
     * Activates the search button
     */
    public void activateSearch() {
        searchButton.doClick();
    }
}
