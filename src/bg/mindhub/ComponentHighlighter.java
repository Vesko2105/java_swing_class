package bg.mindhub;

import javax.swing.*;

public class ComponentHighlighter {
    public static void markAsError(JComponent component)  {
        component.setBorder(BorderFactory.createLineBorder(SystemSettings.errorBorderColor));
        component.setBackground(SystemSettings.errorBackgroundColor);
    }
    public static void reset(JComponent component) {
        component.setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        component.setBackground(SystemSettings.textFieldBackgroundColor);
    }
}
