package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ConnectDB.ConnectDB;
import DAO.NhanVien_DAO;
import DAO.TaiKhoan_DAO;
import EDIT.EditScrollPane;
import Entity.KhachHang;
import Entity.NhanVien;

public class DanhMucNhanVienGUI extends JPanel implements MouseListener{

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

            // Add mouse listener for hover effect
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!isFocusOwner()) {
                        borderColor = Color.decode("#90CAF9"); // Light blue on hover
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

    // Declare global variables for form fields, table, etc.
    private RoundedTextField employeeIdField;
    private RoundedTextField employeeNameField;
    private JComboBox<String> genderCombo;
    private JComboBox<String> statusCombo;
    private RoundedTextField salaryField;
    private JComboBox<String> positionCombo;
    private RoundedTextField citizenIdField;
    private RoundedTextField emailField;
    private RoundedTextField searchField;
    private JComboBox<String> filterPositionCombo;
    private JTable table;
    private DefaultTableModel tableModel;
	private NhanVien_DAO nvd;
	private TaiKhoan_DAO tkd;
	private ArrayList<NhanVien> dsnv;
    public DanhMucNhanVienGUI() {
    	ConnectDB.getInstance().connect();
    	nvd = new NhanVien_DAO();
    	tkd = new TaiKhoan_DAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#F5F5F5")); // Light gray background for the frame

        // Create the top panel for the form (filter section)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(Color.decode("#E3F2FD")); // Light blue background for the form

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow components to expand horizontally

        // Font for labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // Form fields with the custom RoundedTextField
        // Row 1: Mã Nhân Viên, Tên Nhân Viên, Giới Tính
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel employeeIdLabel = new JLabel("Mã Nhân Viên:");
        employeeIdLabel.setFont(labelFont);
        topPanel.add(employeeIdLabel, gbc);

        gbc.gridx = 1;
        employeeIdField = new RoundedTextField("NV0021");
        employeeIdField.setPreferredSize(new Dimension(150, 35)); // Consistent width
        topPanel.add(employeeIdField, gbc);

        gbc.gridx = 2;
        JLabel employeeNameLabel = new JLabel("Tên Nhân Viên:");
        employeeNameLabel.setFont(labelFont);
        topPanel.add(employeeNameLabel, gbc);

        gbc.gridx = 3;
        employeeNameField = new RoundedTextField("");
        employeeNameField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(employeeNameField, gbc);

        gbc.gridx = 4;
        JLabel genderLabel = new JLabel("Giới Tính:");
        genderLabel.setFont(labelFont);
        topPanel.add(genderLabel, gbc);

        gbc.gridx = 5;
        genderCombo = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderCombo.setSelectedItem("Nam");
        genderCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        genderCombo.setBackground(Color.WHITE);
        genderCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        genderCombo.setPreferredSize(new Dimension(150, 35));
        topPanel.add(genderCombo, gbc);

        // Row 2: Trạng thái, Lương Gió, Chức Vụ
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setFont(labelFont);
        topPanel.add(statusLabel, gbc);

        gbc.gridx = 1;
        statusCombo = new JComboBox<>(new String[]{"Đang làm", "Nghỉ việc"});
        statusCombo.setSelectedItem("Đang làm");
        statusCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusCombo.setBackground(Color.WHITE);
        statusCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        statusCombo.setPreferredSize(new Dimension(150, 35));
        topPanel.add(statusCombo, gbc);

        gbc.gridx = 2;
        JLabel salaryLabel = new JLabel("Lương Gió:");
        salaryLabel.setFont(labelFont);
        topPanel.add(salaryLabel, gbc);

        gbc.gridx = 3;
        salaryField = new RoundedTextField("");
        salaryField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(salaryField, gbc);

        gbc.gridx = 4;
        JLabel positionLabel = new JLabel("Chức Vụ:");
        positionLabel.setFont(labelFont);
        topPanel.add(positionLabel, gbc);

        gbc.gridx = 5;
        positionCombo = new JComboBox<>(new String[]{"Nhân Viên", "Người Quản Lý"});
        positionCombo.setSelectedItem("Nhân Viên");
        positionCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        positionCombo.setBackground(Color.WHITE);
        positionCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        positionCombo.setPreferredSize(new Dimension(150, 35));
        topPanel.add(positionCombo, gbc);

        // Row 3: Mã CCCD, Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel citizenIdLabel = new JLabel("Mã CCCD:");
        citizenIdLabel.setFont(labelFont);
        topPanel.add(citizenIdLabel, gbc);

        gbc.gridx = 1;
        citizenIdField = new RoundedTextField("");
        citizenIdField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(citizenIdField, gbc);

        gbc.gridx = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        topPanel.add(emailLabel, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1; // No longer spanning multiple columns
        emailField = new RoundedTextField("");
        emailField.setPreferredSize(new Dimension(225, 35)); // Shortened to half (450 / 2 = 225)
        topPanel.add(emailField, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // Search text field, button, and Position Filter dropdown
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.decode("#E3F2FD")); // Match the form background

        // Add the search text field
        searchField = new RoundedTextField("");
        searchField.setPreferredSize(new Dimension(200, 35)); // Set width and height for the search field
        searchField.setText("Nhập từ khóa tìm kiếm...");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Nhập từ khóa tìm kiếm...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.decode("#212121"));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Nhập từ khóa tìm kiếm...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        buttonPanel.add(searchField);
        searchField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String tenNv = searchField.getText();
				if(tenNv.length()==0)
				{
					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dayNhanVien();
					return;
					
				}
				if(table.getRowCount()>0)
				{
					tableModel.setRowCount(0);
				}
				dsnv = nvd.getAllNhanVienTheoTen(tenNv);
				if(dsnv.size()!=0)
		    	for(NhanVien nv : dsnv)
		    	{
		    		String maNhanVien = nv.getMaNhanVien();
					String tenNhanVien = nv.getTenNhanVien();
					int tuoi = nv.getTuoi();
					String maCCCD = nv.getMaCCCD();
					String email = nv.getEmail();	
					
					String gioiTinh = nv.isGioiTinh()? "Nam":"Nữ";
					String trangThai = nv.getTrangThai();
					float heSoLuong = nv.getHeSoLuong();
					String maChucVu = nv.getMaLoaiNhanVien().getMaLoaiNhanVien();
					String tenChucVu = "Người Quản Lý";
					if(maChucVu.equals("LNV02".trim()))
					{
						tenChucVu = "Nhân Viên";
					}
					String[] row= {maNhanVien,tenNhanVien,tuoi+"",maCCCD,email,gioiTinh,trangThai,heSoLuong+"",tenChucVu};
					tableModel.addRow(row);
		    	}
				
			}
		});
        JLabel filterLabel = new JLabel("Chức Vụ:");
        filterLabel.setFont(labelFont);
        buttonPanel.add(filterLabel);

        filterPositionCombo = new JComboBox<String>(new String[]{"Nhân Viên", "Người Quản Lý"});
        filterPositionCombo.setSelectedItem("Tất cả");
        filterPositionCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterPositionCombo.setBackground(Color.WHITE);
        filterPositionCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        filterPositionCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String hang = (String) filterPositionCombo.getSelectedItem();
				
				if(hang.equals("Nhân Viên"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dsnv = nvd.getAllNhanVienTheoLoai("LNV02");
					if(dsnv.size()>0)
					{
						for(NhanVien nv : dsnv)
				    	{
				    		String maNhanVien = nv.getMaNhanVien();
							String tenNhanVien = nv.getTenNhanVien();
							int tuoi = nv.getTuoi();
							String maCCCD = nv.getMaCCCD();
							String email = nv.getEmail();	
							
							String gioiTinh = nv.isGioiTinh()? "Nam":"Nữ";
							String trangThai = nv.getTrangThai();
							float heSoLuong = nv.getHeSoLuong();
							String maChucVu = nv.getMaLoaiNhanVien().getMaLoaiNhanVien();
							String tenChucVu = "Người Quản Lý";
							if(maChucVu.equals("LNV02".trim()))
							{
								tenChucVu = "Nhân Viên";
							}
							String[] row= {maNhanVien,tenNhanVien,tuoi+"",maCCCD,email,gioiTinh,trangThai,heSoLuong+"",tenChucVu};
							tableModel.addRow(row);
				    	}
					}
					return;
					
				}
				if(hang.equals("Người Quản Lý"))
				{

					if(table.getRowCount()>0)
					{
						tableModel.setRowCount(0);
						
					}
					dsnv = nvd.getAllNhanVienTheoLoai("LNV01");
					if(dsnv.size()>0)
					{
						for(NhanVien nv : dsnv)
				    	{
				    		String maNhanVien = nv.getMaNhanVien();
							String tenNhanVien = nv.getTenNhanVien();
							int tuoi = nv.getTuoi();
							String maCCCD = nv.getMaCCCD();
							String email = nv.getEmail();	
							
							String gioiTinh = nv.isGioiTinh()? "Nam":"Nữ";
							String trangThai = nv.getTrangThai();
							float heSoLuong = nv.getHeSoLuong();
							String maChucVu = nv.getMaLoaiNhanVien().getMaLoaiNhanVien();
							String tenChucVu = "Người Quản Lý";
							if(maChucVu.equals("LNV02".trim()))
							{
								tenChucVu = "Nhân Viên";
							}
							String[] row= {maNhanVien,tenNhanVien,tuoi+"",maCCCD,email,gioiTinh,trangThai,heSoLuong+"",tenChucVu};
							tableModel.addRow(row);
				    	}
					}
					return;
				}
			}
		});
        buttonPanel.add(filterPositionCombo);

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
        String[] columnNames = {"Mã NV", "Tên NV", "Tuổi", "CCCD", "Email", "Giới Tính", "Trạng Thái", "Lương Gió", "Chức Vụ"};
        tableModel = new DefaultTableModel(columnNames, 0)
        {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};	

        // Populate the table with sample data (as shown in the screenshot)
        
        dayNhanVien();

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionBackground(Color.decode("#42A5F5")); // Blue selection background
        table.setSelectionForeground(Color.WHITE); // White text when selected
        JScrollPane scrollPane = new EditScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        table.addMouseListener(this);
        disableAllFields();
    }

    // Main method to launch the application
    
    public void dayNhanVien()
    {
    	dsnv = nvd.getAllNhanVien();
    	for(NhanVien nv : dsnv)
    	{
    		String maNhanVien = nv.getMaNhanVien();
			String tenNhanVien = nv.getTenNhanVien();
			int tuoi = nv.getTuoi();
			String maCCCD = nv.getMaCCCD();
			String email = nv.getEmail();	
			
			String gioiTinh = nv.isGioiTinh()? "Nam":"Nữ";
			String trangThai = nv.getTrangThai();
			float heSoLuong = nv.getHeSoLuong();
			String maChucVu = nv.getMaLoaiNhanVien().getMaLoaiNhanVien();
			String tenChucVu = "Người Quản Lý";
			if(maChucVu.equals("LNV02".trim()))
			{
				tenChucVu = "Nhân Viên";
			}
			String[] row= {maNhanVien,tenNhanVien,tuoi+"",maCCCD,email,gioiTinh,trangThai,heSoLuong+"",tenChucVu};
			tableModel.addRow(row);
    	}
    }
    private void fillFieldsFromTable() {
        // Lấy dòng được chọn từ JTable
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy dữ liệu từ dòng được chọn và điền vào các trường
            employeeIdField.setText((String) table.getValueAt(selectedRow, 0));  // Mã Nhân Viên
            employeeNameField.setText((String) table.getValueAt(selectedRow, 1)); // Tên Nhân Viên
            // Tiếp tục với các trường còn lại
            // Tuổi, CCCD, Email, Giới tính, Trạng thái, Lương Gió, Chức Vụ...
            // Bạn cần kiểm tra nếu có các trường hợp khác biệt (vd: nếu là combo box hoặc text field)
            genderCombo.setSelectedItem(table.getValueAt(selectedRow, 5)); // Giới tính
            statusCombo.setSelectedItem(table.getValueAt(selectedRow, 6)); // Trạng thái
            salaryField.setText((String) table.getValueAt(selectedRow, 7)); // Lương Gió
            positionCombo.setSelectedItem(table.getValueAt(selectedRow, 8)); // Chức Vụ
            citizenIdField.setText((String) table.getValueAt(selectedRow, 3)); // Mã CCCD
            emailField.setText((String) table.getValueAt(selectedRow, 4)); // Email
        }
    }
    private void clearFields() {
        // Xóa trắng tất cả các trường nhập liệu
        employeeIdField.setText("");
        employeeNameField.setText("");
        genderCombo.setSelectedItem("Nam"); // Hoặc thiết lập giá trị mặc định
        statusCombo.setSelectedItem("Đang làm"); // Hoặc thiết lập giá trị mặc định
        salaryField.setText("");
        positionCombo.setSelectedItem("Nhân Viên"); // Hoặc thiết lập giá trị mặc định
        citizenIdField.setText("");
        emailField.setText("");
    }
    private void disableAllFields() {
        // Vô hiệu hóa các JTextField
        employeeIdField.setEditable(false);
        employeeNameField.setEditable(false);
        citizenIdField.setEditable(false);
        emailField.setEditable(false);
        salaryField.setEditable(false);

        // Vô hiệu hóa các JComboBox
        genderCombo.setEnabled(false);
        statusCombo.setEnabled(false);
        positionCombo.setEnabled(false);
        
        // Vô hiệu hóa các JButton nếu có (nếu có)
        // Ví dụ:
        // submitButton.setEnabled(false); // nếu bạn có button submit
    }

	@Override
	public void mouseClicked(MouseEvent e) {
        // Kiểm tra số lần nhấp chuột vào dòng
        if (e.getClickCount() == 1) {

            fillFieldsFromTable();  // Đẩy dữ liệu từ dòng lên các field
        } else if (e.getClickCount() == 2) {

            clearFields();  // Xóa trắng các trường
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
