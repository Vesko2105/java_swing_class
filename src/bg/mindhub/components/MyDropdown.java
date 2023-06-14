package bg.mindhub.components;

import bg.mindhub.ComponentHighlighter;
import bg.mindhub.Genre;
import bg.mindhub.SystemSettings;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

public class MyDropdown extends JComboBox<String> implements FocusListener {
    public MyDropdown(Object[] items, String placeholder) {
        super();

        this.addItem(placeholder);
        Arrays.stream(items).map(String::valueOf).forEach(this::addItem);

        this.setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        this.setBackground(SystemSettings.textFieldBackgroundColor);
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        ComponentHighlighter.reset(this);
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
