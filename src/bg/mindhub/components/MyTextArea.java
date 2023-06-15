package bg.mindhub.components;

import bg.mindhub.SystemSettings;
import bg.mindhub.ComponentHighlighter;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextArea extends JTextArea implements FocusListener {

    public MyTextArea() {
        setBorder(BorderFactory.createLineBorder(SystemSettings.MAIN_DARK_COLOR));
        setFont(SystemSettings.GLOBAL_FONT);
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        ComponentHighlighter.reset(this);
    }

    @Override
    public void focusLost(FocusEvent e) {}
}
