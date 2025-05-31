package EDIT;

import javax.swing.*;
import java.awt.*;

public class EditJPanel extends JPanel{
	private float opacity = 0.6f; // Độ mờ
    private Color fillColor = Color.WHITE; // Màu nền
    private int arcWidth = 30, arcHeight = 30; // Độ bo góc

    public EditJPanel() {
        setOpaque(false); // Để JPanel có thể vẽ với độ trong suốt
    }

    public EditJPanel(float opacity, Color fillColor, int arcWidth, int arcHeight) {
        this.opacity = opacity;
        this.fillColor = fillColor;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setColor(fillColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2d.dispose();
    }
}
