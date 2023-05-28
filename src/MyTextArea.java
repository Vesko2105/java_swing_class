import javax.swing.*;
import java.awt.*;

public class MyTextArea extends JTextArea implements ErrorHighlightable {
    private final Color defaultBackgroundColor;
    public MyTextArea() {
        setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        setFont(SystemSettings.GLOBAL_FONT);
        defaultBackgroundColor = getBackground();
    }

    @Override
    public void markAsError()  {
        this.setBorder(BorderFactory.createLineBorder(new Color(213, 0, 0)));
        this.setBackground(new Color(255, 185, 185));
    }

    @Override
    public void reset() {
        setBorder(BorderFactory.createLineBorder(SystemSettings.mainDarkColor));
        this.setBackground(defaultBackgroundColor);
    }
}
