package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ConnectDB.ConnectDB;
import DAO.KhachHang_DAO;
import EDIT.EditScrollPane;
import Entity.KhachHang;

public class DanhMucKhachHangGUI extends JPanel implements MouseListener{

    // Các thành phần UI dưới dạng biến toàn cục
    private RoundedTextField phoneField, nameField, pointsField, emailField, searchField;
    private JComboBox<String> rankCombo, filterRankCombo;
    private JTable table;
    private DefaultTableModel tableModel;
	private KhachHang_DAO khd;
	private ArrayList<KhachHang> dskh;
    // Custom JTextField with rounded corners and modern styling
    static class RoundedTextField extends JTextField {
        private int padding = 8;
        private Color borderColor = Color.decode("#B0BEC5");
        private Color focusedBorderColor = Color.decode("#42A5F5");

        public RoundedTextField(String text) {
            super(text);
            setOpaque(false);
            setBorder(new RoundedBorder());
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setForeground(Color.decode("#212121"));
            setBackground(Color.WHITE);
            setCaretColor(Color.decode("#42A5F5"));

            addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    borderColor = focusedBorderColor;
                    repaint();
                }

                public void focusLost(java.awt.event.FocusEvent evt) {
                    borderColor = Color.decode("#B0BEC5");
                    repaint();
                }
            });

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!isFocusOwner()) {
                        borderColor = Color.decode("#90CAF9");
                        repaint();
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
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
            g2.setColor(getBackground());
            g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
            super.paintComponent(g2);
            g2.dispose();
        }

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

    public DanhMucKhachHangGUI() {
    	ConnectDB.getInstance().connect();
    	khd = new KhachHang_DAO();

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#F5F5F5"));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(Color.decode("#E3F2FD"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Số Điện Thoại Khách Hàng:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        phoneField = new RoundedTextField("");
        phoneField.setPreferredSize(new Dimension(300, 35));
        topPanel.add(phoneField, gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Tên Khách Hàng:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 3;
        nameField = new RoundedTextField("");
        nameField.setPreferredSize(new Dimension(300, 35));
        topPanel.add(nameField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Điểm tích lũy hiện tại:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        pointsField = new RoundedTextField("");
        pointsField.setPreferredSize(new Dimension(300, 35));
        topPanel.add(pointsField, gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Email khách hàng:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 3;
        emailField = new RoundedTextField("");
        emailField.setPreferredSize(new Dimension(300, 35));
        topPanel.add(emailField, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        topPanel.add(new JLabel("Xếp hạng:") {{ setFont(labelFont); }}, gbc);
        gbc.gridx = 1;
        rankCombo = new JComboBox<>(new String[]{"Đồng", "Bạc", "Vàng", "Bạch Kim"});
        rankCombo.setSelectedItem("Đồng");
        rankCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rankCombo.setBackground(Color.WHITE);
        rankCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        rankCombo.setPreferredSize(new Dimension(150, 35));
        topPanel.add(rankCombo, gbc);

        // Search Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.decode("#E3F2FD"));

        searchField = new RoundedTextField("");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Nhập số điện thoại cần tìm kiếm...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.decode("#212121"));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Nhập số điện thoại cần tìm kiếm...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        buttonPanel.add(searchField);
        searchField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sdt = searchField.getText();
				if(sdt.length()==0)
				{
					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					uploadTable();
					return;
					
				}
				if(table.getRowCount()>0)
				{
					tableModel.setRowCount(0);
				}
				KhachHang kh = khd.timKhachHang(sdt);
				if(kh!=null)
				{
					String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
		    		tableModel.addRow(row);
				}
				
			}
		});
        
        JLabel filterLabel = new JLabel("Xếp hạng:");
        filterLabel.setFont(labelFont);
        buttonPanel.add(filterLabel);

        filterRankCombo = new JComboBox<>(new String[]{"Tất cả", "Đồng", "Bạc", "Vàng", "Bạch Kim"});
        filterRankCombo.setSelectedItem("Tất cả");
        filterRankCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterRankCombo.setBackground(Color.WHITE);
        filterRankCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        buttonPanel.add(filterRankCombo);
        filterRankCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String hang = (String) filterRankCombo.getSelectedItem();
				if(hang.equals("Tất cả"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					uploadTable();
					return;
				}
				if(hang.equals("Đồng"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dskh = khd.timKhachHangTheoLoai("ML00001");
					if(dskh.size()>0)
					{
						for(KhachHang kh : dskh)
						{
							String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
				    		tableModel.addRow(row);
						}
					}
					return;
					
				}
				if(hang.equals("Bạc"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dskh = khd.timKhachHangTheoLoai("ML00002");
					if(dskh.size()>0)
					{
						for(KhachHang kh : dskh)
						{
							String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
				    		tableModel.addRow(row);
						}
					}
					return;
				}
				if(hang.equals("Vàng"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dskh = khd.timKhachHangTheoLoai("ML00003");
					if(dskh.size()>0)
					{
						for(KhachHang kh : dskh)
						{
							String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
				    		tableModel.addRow(row);
						}
					}
					return;
				}
				if(hang.equals("Kim Cương"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dskh = khd.timKhachHangTheoLoai("ML00004");
					if(dskh.size()>0)
					{
						for(KhachHang kh : dskh)
						{
							String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
				    		tableModel.addRow(row);
						}
					}
					return;
				}
			}
		});

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.decode("#E3F2FD"));
        northPanel.add(topPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Số Điện Thoại", "Tên Khách Hàng", "Email", "Điểm Tích Lũy", "Xếp Hạng"};
        tableModel = new DefaultTableModel(columnNames, 0)
        {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};		

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.decode("#CFD8DC"));
        table.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#1976D2"));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(BorderFactory.createLineBorder(Color.decode("#1976D2")));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#E8EAF6"));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new EditScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        add(scrollPane, BorderLayout.CENTER);
        uploadTable();
        table.addMouseListener(this);
        disableFields();
    }
    public void disableFields() {
        phoneField.setEnabled(false);
        nameField.setEnabled(false);
        emailField.setEnabled(false);
        pointsField.setEnabled(false);
        rankCombo.setEnabled(false);
    }

    public void uploadTable()
    {
    	try {
			dskh = khd.getALLKhachHang();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(dskh!=null)
    	for (KhachHang kh:dskh)
    	{
    		String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
    		tableModel.addRow(row);
    	}
    }
    public void resetFields() {
        phoneField.setText("");
        nameField.setText("");
        pointsField.setText("");
        emailField.setText("");
        rankCombo.setSelectedIndex(0); // Đặt lại về "Đồng"

        // Focus vào trường đầu tiên (Số điện thoại)
        phoneField.requestFocusInWindow();
    }
    public void populateFieldsFromSelectedRow() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            // Lấy dữ liệu từ bảng
            String phone = table.getValueAt(selectedRow, 0).toString();
            String name = table.getValueAt(selectedRow, 1).toString();
            String email = table.getValueAt(selectedRow, 2).toString();
            String points = table.getValueAt(selectedRow, 3).toString();
            String rank = table.getValueAt(selectedRow, 4).toString();

            // Đẩy vào các field
            phoneField.setText(phone);
            nameField.setText(name);
            emailField.setText(email);
            pointsField.setText(points);
            rankCombo.setSelectedItem(rank);

            // Focus về field đầu tiên nếu muốn
            phoneField.requestFocusInWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để hiển thị thông tin.",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

	@Override
	public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 1) { // hoặc 2 nếu muốn double-click
            populateFieldsFromSelectedRow();
        }
        if(evt.getClickCount() == 2)
        {
        	resetFields();
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
