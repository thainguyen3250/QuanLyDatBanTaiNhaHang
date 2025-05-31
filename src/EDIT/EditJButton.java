package EDIT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditJButton extends JButton {
    private int radius;
    private boolean isPressed = false;

    public EditJButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false); 
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFont(new Font("Times New Roman", Font.BOLD, 20));
        setBackground(Color.decode("#808080"));
        setPreferredSize(new Dimension(200, 40));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

   
        if (isPressed) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

   
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

    }
}
