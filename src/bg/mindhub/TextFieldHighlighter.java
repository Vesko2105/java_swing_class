package bg.mindhub;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TextFieldHighlighter {
    public static void markAsError(JTextComponent textComponent)  {
        textComponent.setBorder(BorderFactory.createLineBorder(SystemSettings.errorBorderColor));
        textComponent.setBackground(SystemSettings.errorBackgroundColor);
    }
    public static void reset(JTextComponent textComponent) {
        textComponent.setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        textComponent.setBackground(SystemSettings.textFieldBackgroundColor);
    }
}
