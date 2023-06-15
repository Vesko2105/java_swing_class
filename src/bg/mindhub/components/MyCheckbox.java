package bg.mindhub.components;

import bg.mindhub.SystemSettings;

import javax.swing.*;
import java.awt.event.ItemListener;

public class MyCheckbox extends JCheckBox {
    public MyCheckbox(String text, ItemListener itemListener) {
        super(text);
        this.addItemListener(itemListener);
        this.setBackground(SystemSettings.MAIN_BACKGROUND_COLOR);
        this.setFocusPainted(false);
    }
}
