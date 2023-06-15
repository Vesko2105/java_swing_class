package bg.mindhub;

import javax.swing.*;

public class ComponentHighlighter {
    public static void markAsError(JComponent component)  {
        component.setBorder(BorderFactory.createLineBorder(SystemSettings.ERROR_BORDER_COLOR));
        component.setBackground(SystemSettings.ERROR_BACKGROUND_COLOR);
    }
    public static void reset(JComponent component) {
        component.setBorder(BorderFactory.createLineBorder(SystemSettings.MAIN_DARK_COLOR));
        component.setBackground(SystemSettings.TEXT_FIELD_BACKGROUND_COLOR);
    }
}
