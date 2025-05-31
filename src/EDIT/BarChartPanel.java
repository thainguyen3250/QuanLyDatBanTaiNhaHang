package EDIT;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

public class BarChartPanel extends JPanel {
    private List<String> labels;
    private List<Integer> values;

    public BarChartPanel(List<String> labels, List<Integer> values) {
        this.labels = labels;
        this.values = values;
        setBackground(Color.white);
    }

    public void setData(List<String> labels, List<Integer> values) {
        this.labels = labels;
        this.values = values;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (labels == null || values == null || labels.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int BAR_HEIGHT = 25;
        int BAR_GAP = 10;
        int LEFT_MARGIN = 130;
        int RIGHT_MARGIN = 60;
        int TOP_MARGIN = 30;

        int max = values.stream().max(Integer::compare).orElse(1);
        int chartWidth = getWidth() - LEFT_MARGIN - RIGHT_MARGIN;

        for (int i = 0; i < labels.size(); i++) {
            int barWidth = (int) ((values.get(i) / (double) max) * chartWidth);
            int y = TOP_MARGIN + i * (BAR_HEIGHT + BAR_GAP);

            // Tên
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.drawString(labels.get(i), LEFT_MARGIN - 10 - g2.getFontMetrics().stringWidth(labels.get(i)), y + BAR_HEIGHT / 2 + 5);

            // Thanh
            g2.setColor(i % 2 == 0 ? new Color(79, 129, 189) : new Color(158, 202, 225));
            g2.fillRect(LEFT_MARGIN, y, barWidth, BAR_HEIGHT);

            // Giá trị
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(values.get(i)), LEFT_MARGIN + barWidth + 5, y + BAR_HEIGHT / 2 + 5);
        }
    }
}


