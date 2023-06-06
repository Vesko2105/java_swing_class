package bg.mindhub.components;

import bg.mindhub.SystemSettings;
import bg.mindhub.TextFieldHighlighter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField implements FocusListener {
    private static final Font font = SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD);

    public MyTextField() {
        this.setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        this.setFont(font);
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        TextFieldHighlighter.reset(this);
    }

    @Override
    public void focusLost(FocusEvent e) {}
}
