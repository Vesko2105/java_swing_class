package bg.mindhub.components;

import bg.mindhub.SystemSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyButton extends JButton {

    private static final Dimension preferredButtonSize = new Dimension(100, 30);
    private static final Insets buttonMargin = new Insets(0, 0, 0, 0);
    private static final Font buttonFont = SystemSettings.GLOBAL_FONT.deriveFont(Font.BOLD);

    public MyButton(String text, String actionName, ActionListener actionListener, String iconPath) {
        super();

        this.setText(text);
        this.setActionCommand(actionName);
        this.addActionListener(actionListener);
        this.setPreferredSize(preferredButtonSize);
        this.setFont(buttonFont);
        this.setMargin(buttonMargin);

        ImageIcon icon = new ImageIcon(iconPath);
        Image clearIconImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(clearIconImage);
        this.setIcon(icon);
    }

}
