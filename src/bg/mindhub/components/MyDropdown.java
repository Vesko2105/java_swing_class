package bg.mindhub.components;

import bg.mindhub.ComponentHighlighter;
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

        this.setBorder(BorderFactory.createLineBorder(SystemSettings.MAIN_DARK_COLOR));
        this.setBackground(SystemSettings.TEXT_FIELD_BACKGROUND_COLOR);
        this.addFocusListener(this);
    }

    public String getSelection() {
        Object selectedItem = super.getSelectedItem();
        return selectedItem == null ? "null" : String.valueOf(selectedItem);
    }

    @Override
    public void focusGained(FocusEvent e) {
        ComponentHighlighter.reset(this);
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
