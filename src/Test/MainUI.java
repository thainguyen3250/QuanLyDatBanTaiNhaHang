package Test;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MainUI extends JFrame {
    
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    
    public MainUI() {
        setTitle("Raven Channel");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Main components
        createHeader();
        createSidebar();
        createContentPanel();
        
        setVisible(true);
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 68)); // Dark green
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        
        // Logo
        JLabel logoLabel = new JLabel("Raven Channel");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(logoLabel);
        
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(0, 128, 85)); // Slightly lighter green
        sidebarPanel.setPreferredSize(new Dimension(250, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        // Add menu items
        addMenuItem("Dashboard", "icons/dashboard.png", false);
        addMenuItem("Email", "icons/email.png", true);
        
        // Sub-items for Email
        addSubMenuItem("Inbox");
        addSubMenuItem("Read");
        addSubMenuItem("Compost");
        
        addMenuItem("Chat", "icons/chat.png", false);
        addMenuItem("Calendar", "icons/calendar.png", false);
        addMenuItem("UI Kit", "icons/ui-kit.png", true);
        addMenuItem("Advanced UI", "icons/advanced-ui.png", true);
        addMenuItem("Forms", "icons/forms.png", true);
        addMenuItem("Charts", "icons/charts.png", true);
        addMenuItem("Table", "icons/table.png", true);
        addMenuItem("Icons", "icons/icons.png", true);
        addMenuItem("Special Pages", "icons/special-pages.png", true);
        
        add(sidebarPanel, BorderLayout.WEST);
    }
    
    private void addMenuItem(String text, String iconPath, boolean hasDropdown) {
        JPanel menuItem = new JPanel();
        menuItem.setLayout(new BorderLayout());
        menuItem.setBackground(new Color(0, 128, 85)); // Same as sidebar
        menuItem.setMaximumSize(new Dimension(250, 40));
        
        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        innerPanel.setBackground(new Color(0, 128, 85));
        
        // Would load actual icon in real implementation
        JLabel iconLabel = new JLabel("\u2022");
        iconLabel.setForeground(Color.WHITE);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        innerPanel.add(iconLabel);
        innerPanel.add(textLabel);
        
        menuItem.add(innerPanel, BorderLayout.WEST);
        
        // Khai báo dropdownPanel trước khi sử dụng
        JPanel dropdownPanel = null;
        
        if (hasDropdown) {
            JLabel dropdownLabel = new JLabel("▼");
            dropdownLabel.setForeground(Color.WHITE);
            dropdownPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            dropdownPanel.setBackground(new Color(0, 128, 85));
            dropdownPanel.add(dropdownLabel);
            menuItem.add(dropdownPanel, BorderLayout.EAST);
        }
        
        // Lưu tham chiếu cuối cùng của dropdownPanel để sử dụng trong MouseListener
        final JPanel finalDropdownPanel = dropdownPanel;
        
        // Add hover effect
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(new Color(0, 150, 100));
                innerPanel.setBackground(new Color(0, 150, 100));
                if (hasDropdown && finalDropdownPanel != null) {
                    finalDropdownPanel.setBackground(new Color(0, 150, 100));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(new Color(0, 128, 85));
                innerPanel.setBackground(new Color(0, 128, 85));
                if (hasDropdown && finalDropdownPanel != null) {
                    finalDropdownPanel.setBackground(new Color(0, 128, 85));
                }
            }
        });
        
        sidebarPanel.add(menuItem);
    }
    
    private void addSubMenuItem(String text) {
        JPanel subMenuItem = new JPanel();
        subMenuItem.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
        subMenuItem.setBackground(new Color(0, 128, 85));
        subMenuItem.setMaximumSize(new Dimension(250, 30));
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        
        subMenuItem.add(textLabel);
        
        // Add hover effect
        subMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                subMenuItem.setBackground(new Color(0, 150, 100));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                subMenuItem.setBackground(new Color(0, 128, 85));
            }
        });
        
        sidebarPanel.add(subMenuItem);
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        
        // Empty content area
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new MainUI();
        });
    }
}