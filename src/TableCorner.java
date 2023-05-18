import javax.swing.*;
import java.awt.*;

public class TableCorner extends JComponent {
    private Color color;

    public TableCorner(Color color) {
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
