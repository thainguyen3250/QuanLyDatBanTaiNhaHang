package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ConnectDB.ConnectDB;
import DAO.LoaiMonAn_DAO;
import DAO.MonAn_DAO;
import EDIT.EditScrollPane;
import Entity.LoaiMonAn;
import Entity.MonAn;

import java.io.File;
import java.text.DecimalFormat;

import javax.swing.*;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class DanhMucMonAnGUI extends JPanel implements MouseListener{

    // Global variables for JTextFields (RoundedTextField), JTable, and DefaultTableModel
    private RoundedTextField dishIdField;
    private RoundedTextField dishNameField;
    private RoundedTextField priceField;
    private RoundedTextField unitField;
    private RoundedTextField descriptionField;
    private RoundedTextField searchField;
    private JTable table;
    private DefaultTableModel tableModel;
    private MonAn_DAO mad;
    private LoaiMonAn_DAO lmad;
    private ArrayList<MonAn> dsma;
    private ArrayList<LoaiMonAn> dslma;
	private JComboBox<String> dishTypeCombo;
	private JComboBox categoryCombo;
	private JLabel imagePlaceholder;
	public DecimalFormat format = new DecimalFormat("#, ### VND");

    // Custom JTextField with rounded corners and modern styling
    static class RoundedTextField extends JTextField {
        private int padding = 8; // Padding inside the text field
        private Color borderColor = Color.decode("#B0BEC5"); // Default border color
        private Color focusedBorderColor = Color.decode("#42A5F5"); // Border color when focused

        public RoundedTextField(String text) {
            super(text);
            setOpaque(false); // Make the background transparent for custom painting
            setBorder(new RoundedBorder());
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setForeground(Color.decode("#212121")); // Dark text color
            setBackground(Color.WHITE); // White background
            setCaretColor(Color.decode("#42A5F5")); // Blue caret

            // Add focus listener for focus effect
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent evt) {
                    borderColor = focusedBorderColor;
                    repaint();
                }

                public void focusLost(FocusEvent evt) {
                    borderColor = Color.decode("#B0BEC5");
                    repaint();
                }
            });

            // Add mouse listener for hover effect
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    if (!isFocusOwner()) {
                        borderColor = Color.decode("#90CAF9"); // Light blue on hover
                        repaint();
                    }
                }

                public void mouseExited(MouseEvent evt) {
                    if (!isFocusOwner()) {
                        borderColor = Color.decode("#B0BEC5");
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Paint the background
            g2.setColor(getBackground());
            g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);

            super.paintComponent(g2);
            g2.dispose();
        }

        // Custom border for rounded corners and padding
        private class RoundedBorder extends AbstractBorder {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(borderColor);
                g2.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(padding, padding, padding, padding);
            }

            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                insets.left = insets.right = padding;
                insets.top = insets.bottom = padding;
                return insets;
            }
        }
    }

    public DanhMucMonAnGUI() {
    	ConnectDB.getInstance().connect();
        mad = new MonAn_DAO();
        lmad = new LoaiMonAn_DAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#F5F5F5"));

 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(Color.decode("#E3F2FD")); 


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.decode("#E3F2FD"));


        imagePlaceholder = new JLabel();
        imagePlaceholder.setPreferredSize(new Dimension(200, 200)); // Square placeholder
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 2));
        imagePlaceholder.setHorizontalAlignment(JLabel.CENTER);
        imagePlaceholder.setVerticalAlignment(JLabel.CENTER); // Placeholder text
        imagePlaceholder.setForeground(Color.GRAY);
        leftPanel.add(imagePlaceholder);

        // Add some spacing
        leftPanel.add(Box.createVerticalStrut(5));

        // "Thêm ảnh" button with image loading functionality



        // Right panel for text fields
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.decode("#E3F2FD"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow components to expand horizontally

        // Font for labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // Form fields with the custom RoundedTextField
        // Row 1: Mã món ăn, Tên món ăn
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel dishIdLabel = new JLabel("Mã món ăn:");
        dishIdLabel.setFont(labelFont);
        rightPanel.add(dishIdLabel, gbc);

        gbc.gridx = 1;
        dishIdField = new RoundedTextField("MA000011");
        dishIdField.setPreferredSize(new Dimension(250, 35)); // Wider fields
        rightPanel.add(dishIdField, gbc);

        gbc.gridx = 2;
        JLabel dishNameLabel = new JLabel("Tên món ăn:");
        dishNameLabel.setFont(labelFont);
        rightPanel.add(dishNameLabel, gbc);

        gbc.gridx = 3;
        dishNameField = new RoundedTextField("");
        dishNameField.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(dishNameField, gbc);

        // Row 2: Danh mục, Loại món
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel categoryLabel = new JLabel("Danh mục:");
        categoryLabel.setFont(labelFont);
        rightPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        categoryCombo = new JComboBox<>(new String[]{"Đang Phục Vụ", "Ngừng Phục Vụ"});
        categoryCombo.setSelectedItem("Đang Phục Vụ");
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryCombo.setBackground(Color.WHITE);
        categoryCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        categoryCombo.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(categoryCombo, gbc);

        gbc.gridx = 2;
        JLabel dishTypeLabel = new JLabel("Loại món:");
        dishTypeLabel.setFont(labelFont);
        rightPanel.add(dishTypeLabel, gbc);

        gbc.gridx = 3;
        dishTypeCombo = new JComboBox<String>();
        dishTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dishTypeCombo.setBackground(Color.WHITE);
        dishTypeCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        dishTypeCombo.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(dishTypeCombo, gbc);

        // Row 3: Giá tiền, Đơn Vị Tính
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel priceLabel = new JLabel("Giá tiền:");
        priceLabel.setFont(labelFont);
        rightPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        priceField = new RoundedTextField("");
        priceField.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(priceField, gbc);

        gbc.gridx = 2;
        JLabel unitLabel = new JLabel("Đơn Vị Tính:");
        unitLabel.setFont(labelFont);
        rightPanel.add(unitLabel, gbc);

        gbc.gridx = 3;
        unitField = new RoundedTextField("");
        unitField.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(unitField, gbc);

        // Row 4: Mô tả
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel descriptionLabel = new JLabel("Mô tả:");
        descriptionLabel.setFont(labelFont);
        rightPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3; // Span across 3 columns
        descriptionField = new RoundedTextField("");
        descriptionField.setPreferredSize(new Dimension(550, 35)); // Wider to span the row
        rightPanel.add(descriptionField, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Add left and right panels to the top panel
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.CENTER);

        // Search text field, button, and Category Filter dropdown
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.decode("#E3F2FD")); // Match the form background

        // Add the search text field
        searchField = new RoundedTextField("");
        searchField.setPreferredSize(new Dimension(200, 35)); // Set width and height for the search field
        searchField.setText("Nhập từ khóa tìm kiếm...");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (searchField.getText().equals("Nhập từ khóa tìm kiếm...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.decode("#212121"));
                }
            }
            public void focusLost(FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Nhập từ khóa tìm kiếm...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        buttonPanel.add(searchField);

        JLabel filterLabel = new JLabel("Danh mục:");
        filterLabel.setFont(labelFont);
        buttonPanel.add(filterLabel);

        JComboBox<String> filterCategoryCombo = new JComboBox<>(new String[]{"Món ngon", "Món nước", "Món Hầm", "Món Xào", "Món Khô"});
        filterCategoryCombo.setSelectedItem("Món ngon");
        filterCategoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterCategoryCombo.setBackground(Color.WHITE);
        filterCategoryCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        buttonPanel.add(filterCategoryCombo);

        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(Color.decode("#42A5F5")); // Blue background for the button
        searchButton.setForeground(Color.BLACK); // White text
        searchButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        searchButton.setFocusPainted(false); // Remove focus border
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        buttonPanel.add(searchButton);

        // Add the top panel and button panel to the NORTH
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.decode("#E3F2FD"));
        northPanel.add(topPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        // Create the table
        String[] columnNames = {"Mã Món Ăn", "Tên Món Ăn", "Giá Tiền", "Thông Tin Món", "Đơn vị tính","Hình Ảnh","Trạng Thái","Loại Món"};
        tableModel = new DefaultTableModel(columnNames, 0);

       

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30); // Increase row height for better readability
        table.setGridColor(Color.decode("#CFD8DC")); // Light gray grid lines
        table.setShowGrid(true);

        // Center-align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Style the table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#1976D2")); // Dark blue header background
        tableHeader.setForeground(Color.WHITE); // White header text
        tableHeader.setBorder(BorderFactory.createLineBorder(Color.decode("#1976D2")));

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#E8EAF6")); // White and light blue rows
                }
                return c;
            }
        });

        JScrollPane scrollPane = new EditScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        add(scrollPane, BorderLayout.CENTER);
        napDuLieuLenTable();
        napLoaiMonAn();
        table.addMouseListener(this);
    }
    public void napDuLieuLenTable() {
        this.dsma = mad.getAllMonAn();// Gọi DAO trả về danh sách món ăn
        for (MonAn mon : this.dsma) {
            tableModel.addRow(new Object[]{
                mon.getMaMonAn(),
                mon.getTenMonAn(),
                format.format(mon.getGiaMonAn()),
                mon.getMoTa(),
                mon.getDonViTinh(),
                mon.getHinhAnh(),
                mon.getTrangThai(),
                mon.getMaLoaiMonAn().getTenLoaiMonAn()
            });
        }
    }
    public void napLoaiMonAn()
    {
    	dslma = lmad.getAllLoaiMonAn();
    	for(LoaiMonAn lma : dslma)
    	{
    		dishTypeCombo.addItem(lma.getTenLoaiMonAn());
    	}
    }
 // Hàm đẩy dữ liệu từ bảng lên các field
    private void loadSelectedRowToFields(int row) {
    	//{"Mã Món Ăn", "Tên Món Ăn", "Giá Tiền", "Thông Tin Món", "Đơn vị tính","Hình Ảnh","Trạng Thái","Loại Món"};
        if (row >= 0 && row < table.getRowCount()) {
            dishIdField.setText(table.getValueAt(row, 0).toString());
            dishNameField.setText(table.getValueAt(row, 1).toString());
            dishTypeCombo.setSelectedItem(table.getValueAt(row, 7).toString());
            priceField.setText(table.getValueAt(row, 2).toString());
            unitField.setText(table.getValueAt(row, 4).toString());
            descriptionField.setText(table.getValueAt(row, 3).toString());
            categoryCombo.setSelectedItem(table.getValueAt(row, 6).toString());
            String imagePath = table.getValueAt(row, 5).toString();
            setImageToLabel(imagePath); // hiển thị hình
        }
    }
    private void setImageToLabel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(imagePlaceholder.getWidth(), imagePlaceholder.getHeight(), Image.SCALE_SMOOTH);
        imagePlaceholder.setIcon(new ImageIcon(img));
        imagePlaceholder.setText(""); // clear text placeholder
    }

    // Hàm xóa trắng các field
    private void clearFields() {
        dishIdField.setText("");
        dishNameField.setText("");
        categoryCombo.setSelectedIndex(0);
        dishTypeCombo.setSelectedIndex(0);
        priceField.setText("");
        unitField.setText("");
        descriptionField.setText("");
        imagePlaceholder.setIcon(null);
        imagePlaceholder.setText("Hình ảnh");
    }
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = table.getSelectedRow();
        if (evt.getClickCount() == 1) {
            loadSelectedRowToFields(selectedRow);
        } else if (evt.getClickCount() == 2) {
            clearFields();
        }
    }
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}