package bg.mindhub.components.panels;

import bg.mindhub.SystemSettings;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    /**
     * @param titleText the text to be used for the title
     */
    public TitlePanel(String titleText) {
        super(new FlowLayout());
        this.setBackground(SystemSettings.mainDarkColor);

        //Label settings
        JLabel title = new JLabel(titleText);
        title.setFont(SystemSettings.GLOBAL_FONT.deriveFont(32F));
        title.setForeground(new Color(218, 221, 222, 255));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(title);
    }
}