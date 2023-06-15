package bg.mindhub.components;

import bg.mindhub.SystemSettings;
import bg.mindhub.ComponentHighlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField implements FocusListener {
    private static final Font font = SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD);

    public MyTextField() {
        this.setBorder(BorderFactory.createLineBorder(SystemSettings.MAIN_DARK_COLOR));
        this.setFont(font);
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        ComponentHighlighter.reset(this);
    }

    @Override
    public void focusLost(FocusEvent e) {}
}
