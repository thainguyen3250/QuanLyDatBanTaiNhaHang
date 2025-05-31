package EDIT;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class EditScrollPane extends JScrollPane {

    public EditScrollPane(Component view) {
        super(view);
        setBorder(BorderFactory.createEmptyBorder());
        getVerticalScrollBar().setUI(new ModernScrollBarUI());
        getHorizontalScrollBar().setUI(new ModernScrollBarUI());

        // Tăng tốc độ cuộn
        getVerticalScrollBar().setUnitIncrement(20);
        getHorizontalScrollBar().setUnitIncrement(20);
    }

    private static class ModernScrollBarUI extends BasicScrollBarUI {
        private final Dimension d = new Dimension();

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(d);
            button.setMinimumSize(d);
            button.setMaximumSize(d);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            return button;
        }

        @Override
        protected void configureScrollBarColors() {
            
            thumbColor = Color.decode("#76D7C4");    
            trackColor = new Color(213, 245, 227);    
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(thumbColor);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(trackColor);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }
    }
}


