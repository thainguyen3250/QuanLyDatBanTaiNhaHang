package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.KhachHang_DAO;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.KhachHang;
import Entity.LoaiKhachHang;
import Entity.TaiKhoan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CapNhatKhachHangGUI extends JPanel implements ActionListener,MouseListener{
    public JTextField txtTenKH, txtSDT, txtEmail, txtDiemTL;
    public JButton them, sua, xoaRong, xoa, luu;
    public JLabel lblrow2, lblrow3, lblrow4, lblrow5, chucVu, nameUser; 
    private JTable table;
    private DefaultTableModel model;
	private JTextField txtHang;
	private TaiKhoan tk;
	private KhachHang_DAO khd;
	private ArrayList<KhachHang> dskh;
    public CapNhatKhachHangGUI(TaiKhoan tk) throws SQLException {
    	ConnectDB.getInstance().connect();
    	khd = new KhachHang_DAO();
    	dskh = khd.getALLKhachHang();
    	this.tk = tk;
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(1250, 700));
    	
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
        
        Box row2 = Box.createHorizontalBox();
        row2.add(lblrow2 = new JLabel("Số Điện Thoại Khách Hàng: "));
        row2.add(txtSDT = new JTextField(10));
        txtSDT.requestFocus();
        
        Box row3 = Box.createHorizontalBox();
        row3.add(lblrow3 = new JLabel("Tên Khách Hàng: "));
        row3.add(txtTenKH = new JTextField(10));
        
        Box row4 = Box.createHorizontalBox();
        row4.add(lblrow4 = new JLabel("Email khách hàng: "));
        row4.add(txtEmail = new JTextField(10));
        
        Box row5 = Box.createHorizontalBox();
        row5.add(lblrow5 = new JLabel("Điểm tích lũy hiện tại: "));
        row5.add(txtDiemTL = new JTextField(10));
        txtDiemTL.setEnabled(false);
        row5.add(Box.createHorizontalStrut(40));
        row5.add(new JLabel("Xếp hạng: "));
        row5.add(txtHang = new JTextField(10));
        txtHang.setEditable(false);
        row5.add(Box.createHorizontalStrut(40));
        
        lblrow3.setPreferredSize(lblrow2.getPreferredSize());
        
        lblrow4.setPreferredSize(lblrow5.getPreferredSize());
        
        Box left = Box.createVerticalBox();
        Box right = Box.createVerticalBox();
        
        left.add(row2);
        left.add(Box.createVerticalStrut(10));
        left.add(row3);
        left.add(Box.createVerticalStrut(10));
    	
        
        right.add(row4);
        right.add(Box.createVerticalStrut(10));
        right.add(row5);
        right.add(Box.createVerticalStrut(10));
        
        txtSDT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkSDTKH()) {
					txtTenKH.requestFocus();
				}
				
			}
		});
        
        txtTenKH.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkTenKH()) {
					txtEmail.requestFocus();
				}
			}
		});
        
        txtEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkEmailKH();
				
			}
		});
        
        topCenter.add(left);
        topCenter.add(Box.createHorizontalStrut(30));
        topCenter.add(right);
        
        JPanel chucNang = new JPanel();
        them = editButton("Thêm", "#FFFFFF");
        xoaRong = editButton("Làm mới", "#FFFFFF");
        sua = editButton("Sửa", "#FFFFFF");
        xoa = editButton("Xóa", "#FF3727");
        luu = editButton("Lưu", "#78FF4F");
        
        chucNang.add(them);
        chucNang.add(xoaRong);
        chucNang.add(sua);
        chucNang.add(xoa);
        chucNang.add(luu);
        
        Box boxTable = Box.createHorizontalBox();
        String[] colTable = {"Số Điện Thoại", "Tên Khách Hàng", "Email", "Điểm Tích Lũy", "Xếp Hạng"};
        model = new DefaultTableModel(colTable, 0)
        {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};		
        table = new JTable(model);
        JScrollPane pane = new EditScrollPane(table);
        pane.setPreferredSize(new Dimension(1250,550));
        table.getTableHeader().setBackground(Color.white);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		boxTable.add(pane);
		
		center.add(Box.createVerticalStrut(20));
		center.add(topCenter);
		center.add(Box.createVerticalStrut(20));
		center.add(chucNang);
		center.add(Box.createVerticalStrut(20));
		center.add(boxTable);
		sua.setEnabled(false);
		them.addActionListener(this);
		xoaRong.addActionListener(this);
		table.addMouseListener(this);
		sua.addActionListener(this);
		uploadTable();
	}
    
    public boolean checkSDTKH() {
    	String sdt = txtSDT.getText();
    	if(!sdt.matches("^[\\d]{10}$")) {
    		JOptionPane.showMessageDialog(this, "Số điện thoại bao gồm 10 chữ số và không rỗng!");
    		txtSDT.selectAll();
    		txtSDT.requestFocus();
    		return false;
    	}return true;
    }
    
    public boolean checkTenKH() {
    	String name = txtTenKH.getText().trim();
    	if(name.isEmpty()) {
    		JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
    		txtTenKH.selectAll();
    		txtTenKH.requestFocus();
    		return false;
    	}return true;
    }
    
    public boolean checkEmailKH() {
    	String email = txtEmail.getText().trim();
    	if(!email.matches("^[a-z\\d]+@gmail\\.com$")) {
    		JOptionPane.showMessageDialog(this, "Email không được để trống và phải có dạng xxxx@gmail.com");
    		txtEmail.selectAll();
    		txtEmail.requestFocus();
    		return false;
    	}return true;
    }
    
    public JButton editButton(String namebutton, String color) {
    	JButton button = new JButton(namebutton);
    	button.setBorderPainted(false);
    	button.setFont(new Font("Times New Roman", Font.BOLD, 15));
    	button.setBackground(Color.decode(color));
    	button.setFocusPainted(false);
    	return button;
    }
    public void uploadTable()
    {
    	if(dskh!=null)
    	for (KhachHang kh:dskh)
    	{
    		String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
    		model.addRow(row);
    	}
    }
    public boolean kiemTraTonTai(String soDienThoai)
    {
    	return dskh.contains(new KhachHang(soDienThoai));
    }
    public boolean themKhachHang()
    {
    	String soDienThoai = txtSDT.getText().trim();
    	if(!soDienThoai.matches("^[0-9]{10}$"))
    	{
    		JOptionPane.showMessageDialog(this, "Số điện thoại bao gồm 10 chữ số và không rỗng!");
    		txtSDT.requestFocus();
    		return false;
    	}
    	if(kiemTraTonTai(soDienThoai))
    	{
    		JOptionPane.showMessageDialog(this, "Trùng Số Điện Thoại");
    		txtSDT.requestFocus();
    		return false;
    	}
    	
    	String tenKhachHang = txtTenKH.getText();
    	String email =txtEmail.getText();
    	KhachHang kh = new KhachHang(soDienThoai,tenKhachHang,email,0,0,new LoaiKhachHang("ML00001","Đồng"));
    	dskh.add(kh);
    	String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
		model.addRow(row);
		xoaTrang();
    	return khd.themKhachHang(kh);
    }
    public void xoaTrang()
    {
    	txtSDT.setEditable(true);
    	txtSDT.setText("");
    	txtDiemTL.setText("");
    	txtEmail.setText("");
    	txtTenKH.setText("");
    	txtHang.setText("");
    	sua.setEnabled(false);
    	them.setEnabled(true);
    	table.clearSelection();
    }
    public void dayDuLieuLenTren(int curr_row)
    {//"Số Điện Thoại", "Tên Khách Hàng", "Email", "Điểm Tích Lũy", "Xếp Hạng"
    	if(curr_row!=-1)
    	{
    		sua.setEnabled(true);
    		txtSDT.setEditable(false);
    		txtSDT.setText(table.getValueAt(curr_row, 0).toString());
        	txtDiemTL.setText(table.getValueAt(curr_row, 3).toString());
        	txtEmail.setText(table.getValueAt(curr_row, 2).toString());
        	txtTenKH.setText(table.getValueAt(curr_row, 1).toString());
        	txtHang.setText(table.getValueAt(curr_row, 4).toString());
        	them.setEnabled(false);
    		
    	}else
    	{
    		JOptionPane.showMessageDialog(this, "Chưa chọn dòng!");
    	}
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		int curr = table.getSelectedRow();
		if (e.getClickCount()==1)
		{
			dayDuLieuLenTren(curr);
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
	public boolean suaKhachHang()
	{
		int curr = table.getSelectedRow();
		if(curr!=-1)
		{
			String soDienThoai = txtSDT.getText().trim();
	    	if(!soDienThoai.matches("^[0-9]{10}$"))
	    	{
	    		JOptionPane.showMessageDialog(this, "Số điện thoại bao gồm 10 chữ số và không rỗng!");
	    		txtSDT.requestFocus();
	    		return false;
	    	}
	    	String tenKhachHang = txtTenKH.getText();
	    	String email =txtEmail.getText();
	    	KhachHang kh = dskh.get(dskh.indexOf(new KhachHang(soDienThoai)));
	    	kh.setHoTen(tenKhachHang);
	    	kh.setEmail(email);
	    	String[] row = {kh.getSoDienThoai(),kh.getHoTen(),kh.getEmail(),kh.getDiemTichLuyHienTai()+"",kh.getMaLoaiKhachHang().getTenLoai()};
			model.setValueAt(tenKhachHang, curr, 1);
			model.setValueAt(email, curr, 2);
	    	return khd.suaKhachHang(kh);
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Bạn chưa chọn hàng!");
		}
		return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource().equals(them))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn thêm khách hàng này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			themKhachHang();
		}
		if(e.getSource().equals(xoaRong))
		{
			xoaTrang();
		}
		if(e.getSource().equals(sua))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn sửa thông tin khách hàng này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			suaKhachHang();
		}
	}
}