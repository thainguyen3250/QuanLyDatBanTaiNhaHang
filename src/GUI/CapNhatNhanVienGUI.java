package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.NhanVien_DAO;
import DAO.TaiKhoan_DAO;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.LoaiNhanVien;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;



public class CapNhatNhanVienGUI extends JPanel implements ActionListener,MouseListener{
    private JTextField txtMaNV, txtTenNV, txtTuoi, txtCCCD, txtEmail,  txtTTTK, txtMK;
    public JButton them, xoa, sua, xoaRong, luu;
    
    private JTable table;
    private DefaultTableModel model;
	private JTextField txtsdt;

	private String sodienthoai_temp="";
	private JTextField txtLuong;
	private JTextField txtTrangThaiTK;
	private JComboBox<LoaiNhanVien> cbChucVu;
	private JComboBox<String> cbGT;
	private JLabel nameUser;
	private JLabel chucVu;
	private JLabel lblrow1;
	private JLabel lblrow2;
	private JLabel lblrow3;
	private JLabel lblrow4;
	private JLabel lblrow5;
	private JLabel lblrow6;
	private JLabel lblrow7;
	private JComboBox<String> cbtrangThai;
	private JLabel lblrow8;
	private JLabel lblrow9;
	private JComboBox<String> timTheoTen;
	public TaiKhoan tk;
	
	private String cccd_temp="";
	
	private NhanVien_DAO nvd;
	private TaiKhoan_DAO tkd;
	
	private ArrayList<NhanVien> dsnv;
	
	
    public CapNhatNhanVienGUI(TaiKhoan tk) {
    	this.tk=tk;
    	ConnectDB.getInstance().connect();
    	nvd = new NhanVien_DAO();
    	tkd = new TaiKhoan_DAO();
    	
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        // North
        EditJPanel north = new EditJPanel(0.6f, Color.decode("#fdcf84"), 30, 30);
        north.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(north, BorderLayout.NORTH);
        
        EditJPanel user = new EditJPanel(0.8f, Color.white, 40, 40);
        user.setLayout(new BoxLayout(user, BoxLayout.X_AXIS));
        user.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 50));
        user.setBackground(Color.white);

        ImageIcon userIcon = new ImageIcon("icons/icons8-user.png");
        JLabel uI = new JLabel(new ImageIcon(userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        Box info = Box.createVerticalBox();
        info.add(nameUser = new JLabel(tk.getMaNhanVien().getTenNhanVien()));
        info.add(chucVu = new JLabel(tk.getMaNhanVien().getMaLoaiNhanVien().getTenLoai()));

        user.add(info);
        user.add(uI);
        
        north.add(info);
        north.add(uI);
        north.add(Box.createHorizontalStrut(20));
        
        
        //CENTER 
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        
        Box topCenter = Box.createHorizontalBox();
        Box left = Box.createVerticalBox();
        Box right = Box.createVerticalBox();
        
        
        Box row1 = Box.createHorizontalBox();
        row1.add(lblrow1 = new JLabel("Mã Nhân Viên:  "));
        row1.add(txtMaNV = new JTextField(10));
        
        
        
        Box row2 = Box.createHorizontalBox();
        row2.add(lblrow2 = new JLabel("Tên Nhân Viên:  "));
        row2.add(txtTenNV = new JTextField(10));
        
        
        Box row3 = Box.createHorizontalBox();
        row3.add(lblrow3 = new JLabel("Tuổi:   "));
        row3.add(txtTuoi = new JTextField(10));
        
        
        Box row4 = Box.createHorizontalBox();
        row4.add(lblrow4 = new JLabel("Mã CCCD:   "));
        row4.add(txtCCCD = new JTextField(10));
        
        
        Box row5 = Box.createHorizontalBox();
        row5.add(lblrow5 = new JLabel("Email:   "));
        row5.add(txtEmail = new JTextField(10));
        
        Box row10 = Box.createHorizontalBox();
        row10.add(lblrow6 = new JLabel("Giới Tính: "));
        row10.add(cbGT = new JComboBox<String>());
        cbGT.addItem("Nam");
        cbGT.addItem("Nữ");
        cbGT.setPreferredSize(new Dimension(200, 50));
        
        Box row6 = Box.createHorizontalBox();
        row6.add(lblrow7 = new JLabel("Trạng thái: "));
        row6.add(cbtrangThai = new JComboBox<String>());
        cbtrangThai.addItem("Đang làm");
        cbtrangThai.addItem("Tạm nghỉ");
        cbtrangThai.setPreferredSize(new Dimension(200, 50));
        
        Box row11 = Box.createHorizontalBox();
        row11.setPreferredSize(new Dimension(200, 50));
        row11.add(lblrow8 = new JLabel("Lương Giờ: "));
        row11.add(txtLuong = new JTextField());
        
        Box row12 = Box.createHorizontalBox();
        row12.add(lblrow9 = new JLabel("Chức Vụ:           "));
        row12.add(cbChucVu = new JComboBox<LoaiNhanVien>());     
        cbChucVu.addItem(new LoaiNhanVien("LNV02", "Nhân Viên"));
        cbChucVu.addItem(new LoaiNhanVien("LNV01", "Người Quản Lý"));
        cbChucVu.setPreferredSize(new Dimension(200, 50));
        
        
        lblrow1.setPreferredSize(lblrow2.getPreferredSize());
        lblrow3.setPreferredSize(lblrow2.getPreferredSize());
        lblrow4.setPreferredSize(lblrow2.getPreferredSize());
        lblrow5.setPreferredSize(lblrow2.getPreferredSize());
        
        lblrow6.setPreferredSize(lblrow2.getPreferredSize());
        lblrow8.setPreferredSize(lblrow2.getPreferredSize());
        lblrow7.setPreferredSize(lblrow2.getPreferredSize());
        
        JPanel row8 = new JPanel();
        row8.setPreferredSize(new Dimension(200, 50));
        JPanel row9 = new JPanel();
        row9.setPreferredSize(new Dimension(200, 50));
        
        
        left.add(row1);
        left.add(Box.createVerticalStrut(10));
        left.add(row2);
        left.add(Box.createVerticalStrut(10));
        left.add(row3);
        left.add(Box.createVerticalStrut(10));
        left.add(row4);
        left.add(Box.createVerticalStrut(10));
        left.add(row5);
        left.add(Box.createVerticalStrut(20));
        
        right.add(row10);
        right.add(Box.createVerticalStrut(20));
        right.add(row6);
        right.add(Box.createVerticalStrut(20));
        right.add(row11);
        right.add(Box.createVerticalStrut(20));
        right.add(row12);
        right.add(Box.createVerticalStrut(20));
        right.add(row8);
        right.add(Box.createVerticalStrut(20));
        right.add(row9);
        right.add(Box.createVerticalStrut(20));
        topCenter.add(left);
        topCenter.add(Box.createHorizontalStrut(30));
        topCenter.add(right);
        
        JPanel chucnang = new JPanel();
        them = editButton("Thêm", "#FFFFFF");
		sua = editButton("Sửa", "#FFFFFF");
		xoaRong = editButton("Làm mới", "#FFFFFF");
		xoa = editButton("Xóa", "#FF3727");
		luu = editButton("Lưu", "#78FF4F");
        
		chucnang.add(them);
		chucnang.add(xoaRong);
		chucnang.add(sua);
		chucnang.add(xoa);
		chucnang.add(luu);
		
		
		
		Box tableBox = Box.createHorizontalBox();
		String[] colTable = {"Mã NV", "Tên NV", "Tuổi", "CCCD", "Email", "Giới Tính", "Trạng Thái", "Lương","Chức Vụ"};
		model = new DefaultTableModel(colTable, 0)
		{
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};		
        table = new JTable(model);
        JScrollPane pane = new EditScrollPane(table);
        table.getTableHeader().setBackground(Color.white);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
        center.add(topCenter);
        center.add(Box.createVerticalStrut(10));
        center.add(chucnang);
        center.add(Box.createVerticalStrut(10));
        center.add(pane);

		txtMaNV.setEditable(false);
		them.addActionListener(this);
		xoa.addActionListener(this);
		xoaRong.addActionListener(this);
		sua.addActionListener(this);
		table.addMouseListener(this);
		
		try {
			txtMaNV.setText(nvd.generateMaNhanVien());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConnectDB.getInstance().connect();
		dayNhanVien();
		sua.setEnabled(false);
        them.addActionListener(this);
        xoaRong.addActionListener(this);
        table.addMouseListener(this);
        
        txtTenNV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkTenNV()) {
					txtTuoi.requestFocus();
				}
				
			}
		});
        
        txtTuoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkTuoi()) {
					txtCCCD.requestFocus();
				}
				
			}
		});
        
        txtCCCD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkCCCD()) {
					txtEmail.requestFocus();
				}
				
			}
		});
        
        txtEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkEmailNV()) {
					txtLuong.requestFocus();
				}
			}
		});
        txtTenNV.requestFocus();
    }
    
    
    
    public JButton editButton(String nameButton, String color) {
    	JButton button = new JButton(nameButton);
    	button.setBorderPainted(false);
    	button.setFont(new Font("Times New Roman", Font.BOLD, 15));
    	button.setBackground(Color.decode(color));
    	button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	button.setFocusPainted(false);
    	return button;
    }
    
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
			model.addRow(row);
    	}
    }
    public void xoaHetBang()
    {
    	model.setRowCount(0);
    }
    public boolean kiemTraTrungCCCD(String maCCCD)
    {
    	for(NhanVien nv : dsnv)
    	{
    		if(nv.getMaCCCD().equals(maCCCD.trim()))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    public void xoaTrang()
    {
    	try {
			txtMaNV.setText(nvd.generateMaNhanVien());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ConnectDB.getInstance().connect();
		txtTenNV.setText("");
		txtTuoi.setText("");
		txtCCCD.setText("");
		txtEmail.setText("");
		cbGT.setSelectedIndex(0);
		cbtrangThai.setSelectedIndex(0);
		txtLuong.setText("");
		cbChucVu.setSelectedIndex(0);
		sua.setEnabled(false);
		them.setEnabled(true);
		this.cccd_temp="";
		
    }
    
    public boolean checkTenNV() {
    	if(txtTenNV.getText().trim().isEmpty()) {
    		JOptionPane.showMessageDialog(this, "Tên nhân viên không được trống");
    		txtTenNV.selectAll();
    		txtTenNV.requestFocus();
    		return false;
    	}
    	return true;
    }
    
    public boolean checkTuoi() {
        if (!txtTuoi.getText().matches("^\\d{2}$")) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là số gồm 2 chữ số!");
            txtTuoi.selectAll();
            txtTuoi.requestFocus();
            return false;
        }
        int tuoi = Integer.parseInt(txtTuoi.getText());
        if (tuoi < 18 || tuoi > 60) {
            JOptionPane.showMessageDialog(this, "Nhân viên lao động phải từ 18 đến 60 tuổi!");
            txtTuoi.selectAll();
            txtTuoi.requestFocus();
            return false;
        }
        return true;
    }

    
    public boolean checkCCCD() {
    	String maCCCD = txtCCCD.getText();
		if(!maCCCD.matches("^[0-9]{12}$"))
		{
			JOptionPane.showMessageDialog(this, "Mã CCCD phải là số và có 12 chữ số!");
			txtCCCD.selectAll();
			txtCCCD.requestFocus();
			return false;
		}
		if(kiemTraTrungCCCD(maCCCD))
		{
			JOptionPane.showMessageDialog(this, "Trùng Mã CCCD!");
			txtCCCD.selectAll();
			txtCCCD.requestFocus();
			return false;
		}
		return true;
    }
    
    public boolean checkEmailNV() {
    	String email = txtEmail.getText().trim();
    	if(!email.matches("^[a-z\\d]+@gmail\\.com$")) {
    		JOptionPane.showMessageDialog(this, "Email không được để trống và phải có dạng xxxx@gmail.com");
    		txtEmail.selectAll();
    		txtEmail.requestFocus();
    		return false;
    	}return true;
    }
    
    public void themNhanVien()
    {
    	try
    	{
    		String maNhanVien = txtMaNV.getText();
			String tenNhanVien = txtTenNV.getText();
			
			int tuoi = 0;
			if(txtTuoi.getText().matches("^[0-9]*$"))
			tuoi = Integer.parseInt(txtTuoi.getText());
			else
			{
				JOptionPane.showMessageDialog(this,"Tuổi là số dương!");
				return;
			}
			
			
			String maCCCD = txtCCCD.getText();
			if(!maCCCD.matches("^[0-9]{12}$"))
			{
				JOptionPane.showMessageDialog(this, "Mã CCCD phải là số và có 12 chữ số!");
				return;
			}
			if(kiemTraTrungCCCD(maCCCD))
			{
				JOptionPane.showMessageDialog(this, "Trùng Mã CCCD!");
				return;
			}
			String email = 	txtEmail.getText();
			
			boolean gioiTinh = cbGT.getSelectedItem().toString().equals("Nam")? true:false;
			String trangThai = cbtrangThai.getSelectedItem().toString();
			float heSoLuong = Float.parseFloat(txtLuong.getText());
			LoaiNhanVien lnv = (LoaiNhanVien) cbChucVu.getSelectedItem();
			NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, tuoi, maCCCD, email, trangThai, gioiTinh, heSoLuong, lnv);
			
			String tenTaiKhoan = "";
			
			tenTaiKhoan = tkd.generateTenTaiKhoan();
			ConnectDB.getInstance().connect();
			if(nvd.themNhanVien(nv) && tkd.themTaiKhoan(new TaiKhoan(tenTaiKhoan,"1234",true,LocalDateTime.now(),nv)))
			{
				JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
				xoaTrang();
				xoaHetBang();
				dayNhanVien();
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Thêm Thất Bại!");
			}
			
    	}catch(Exception e)
    	{
    		
    	}
    }
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(them))
		{
			
			themNhanVien();
		}
		if(e.getSource().equals(xoaRong))
		{
			xoaTrang();
		}
		if(e.getSource().equals(sua))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn sửa thông tin Nhân Viên này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			suaThongTin();
		}
	}

	public void fillFormFromTableRow(int rowIndex) {
	    if (rowIndex < 0 || rowIndex >= table.getRowCount()) {
	        return;
	    }

	    txtMaNV.setText(table.getValueAt(rowIndex, 0).toString());
	    txtTenNV.setText(table.getValueAt(rowIndex, 1).toString());
	    txtTuoi.setText(table.getValueAt(rowIndex, 2).toString());
	    
	    this.cccd_temp=table.getValueAt(rowIndex, 3).toString();
	    
	    txtCCCD.setText(table.getValueAt(rowIndex, 3).toString());
	    txtEmail.setText(table.getValueAt(rowIndex, 4).toString());
	    // Giới tính
	    String gioiTinh = table.getValueAt(rowIndex, 5).toString();
	    cbGT.setSelectedItem(gioiTinh);

	    // Trạng thái
	    String trangThai = table.getValueAt(rowIndex, 6).toString();
	    cbtrangThai.setSelectedItem(trangThai);

	    // Lương
	    txtLuong.setText(table.getValueAt(rowIndex, 7).toString());

	    // Chức vụ
	    String chucVuStr = table.getValueAt(rowIndex, 8).toString();
	    if (chucVuStr.equals("Người Quản Lý"))
	    {
	    	cbChucVu.setSelectedIndex(1);
	    }else
	    {
	    	cbChucVu.setSelectedIndex(0);
	    }
	    sua.setEnabled(true);
	    them.setEnabled(false);
	}
	public void suaThongTin()
	{
		String maNhanVien = txtMaNV.getText();
		String tenNhanVien = txtTenNV.getText();
		int tuoi = Integer.parseInt(txtTuoi.getText());
		String maCCCD = txtCCCD.getText();
		if(!maCCCD.matches("^[0-9]{12}$"))
		{
			JOptionPane.showMessageDialog(this, "Mã CCCD phải là số và có 12 chữ số!");
			return;
		}
		if(!maCCCD.equals(this.cccd_temp))
		if(kiemTraTrungCCCD(maCCCD))
		{
			JOptionPane.showMessageDialog(this, "Trùng Mã CCCD!");
			return;
		}
		String email = 	txtEmail.getText();
		
		boolean gioiTinh = cbGT.getSelectedItem().toString().equals("Nam")? true:false;
		String trangThai = cbtrangThai.getSelectedItem().toString();
		float heSoLuong = Float.parseFloat(txtLuong.getText());
		LoaiNhanVien lnv = (LoaiNhanVien) cbChucVu.getSelectedItem();
		NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, tuoi, maCCCD, email, trangThai, gioiTinh, heSoLuong, lnv);
		if(nvd.suaNhanVien(nv))
		{
			xoaTrang();
			xoaHetBang();
			dayNhanVien();
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Sửa Thất Bại!");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==1)
		{
			int curr = table.getSelectedRow();
			fillFormFromTableRow(curr);
		}
		if(e.getClickCount()==2)
		{
			xoaTrang();
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
