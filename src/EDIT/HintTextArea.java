package EDIT;

import javax.swing.*;
import java.awt.*;

public class HintTextArea extends JTextArea {
    private String hint;

    public HintTextArea(String hint) {
        this.hint = hint;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.setColor(Color.GRAY);
            Insets insets = getInsets();
            g2.drawString(hint, insets.left + 5, insets.top + getFontMetrics(getFont()).getHeight());
            g2.dispose();
        }
    }
}

