package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.KhuVuc_DAO;
import DAO.Phong_DAO;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.KhuVuc;
import Entity.Phong;
import Entity.TaiKhoan;
//
public class CapNhatPhongGUI extends JPanel implements ActionListener,MouseListener{
    public JTable table;
    public DefaultTableModel model;

    // Biến toàn cục - Thành phần giao diện
    public JTextField txtMaPhong;
    public JTextField txtTenPhong;
    public JTextField txtSoLuongBan;
    public JTextField txtTimKiem;

    public JComboBox<KhuVuc> cbKhuVuc;
    public JComboBox<String> cbLocPhong;

    public JLabel lblMaPhong;
    public JLabel lblTenPhong;
    public JLabel lblKhuVuc;
    public JLabel lblTim;
    public JLabel lblLocPhong;

    // Nút chức năng
    public JButton btnThem;
    public JButton btnSua;
    public JButton btnXoa;

    private ArrayList<Phong> dsp;
    private Phong_DAO phd;
    private ArrayList<KhuVuc> dskv;
    private KhuVuc_DAO kvd;
	private JLabel lblSoLuongBan;
	private JLabel lblTrangThai;
	private JTextField txtTrangThai;

    public CapNhatPhongGUI(TaiKhoan tk) {
        ConnectDB.getInstance().connect();
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(1250, 700));

        phd = new Phong_DAO();
        kvd = new KhuVuc_DAO();

        // Mã phòng
        lblMaPhong = new JLabel("Mã phòng:");
        lblMaPhong.setFont(new Font("Arial", Font.PLAIN, 20));
        lblMaPhong.setBounds(50, 30, 150, 40);
        panel.add(lblMaPhong);

        txtMaPhong = new JTextField();
        txtMaPhong.setFont(new Font("Arial", Font.PLAIN, 20));
        txtMaPhong.setBounds(200, 30, 250, 40);
        panel.add(txtMaPhong);

        txtMaPhong.setEnabled(false);
        try {
			txtMaPhong.setText(phd.generateMaPhong());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ConnectDB.getInstance().connect();
        // Tên phòng
        lblTenPhong = new JLabel("Tên phòng:");
        lblTenPhong.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTenPhong.setBounds(500, 30, 150, 40);
        panel.add(lblTenPhong);

        txtTenPhong = new JTextField();
        txtTenPhong.setFont(new Font("Arial", Font.PLAIN, 20));
        txtTenPhong.setBounds(650, 30, 250, 40);
        panel.add(txtTenPhong);

        // Số lượng phòng
        lblSoLuongBan = new JLabel("Số lượng chỗ:");
        lblSoLuongBan.setFont(new Font("Arial", Font.PLAIN, 20));
        lblSoLuongBan.setBounds(50, 90, 170, 40);
        panel.add(lblSoLuongBan);

        txtSoLuongBan = new JTextField();
        txtSoLuongBan.setFont(new Font("Arial", Font.PLAIN, 20));
        txtSoLuongBan.setBounds(200, 90, 250, 40);
        panel.add(txtSoLuongBan);
        
        
        lblTrangThai = new JLabel("Trạng Thái:");
        lblTrangThai.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTrangThai.setBounds(50, 150, 150, 40);
        panel.add(lblTrangThai);
        
        txtTrangThai = new JTextField();
        txtTrangThai.setFont(new Font("Arial", Font.PLAIN, 20));
        txtTrangThai.setBounds(200, 150, 250, 40);
        panel.add(txtTrangThai);

        
        // Khu vực
        lblKhuVuc = new JLabel("Khu vực:");
        lblKhuVuc.setFont(new Font("Arial", Font.PLAIN, 20));
        lblKhuVuc.setBounds(500, 90, 150, 40);
        panel.add(lblKhuVuc);

        cbKhuVuc = new JComboBox<>();
        cbKhuVuc.setFont(new Font("Arial", Font.PLAIN, 20));
        cbKhuVuc.setBounds(650, 90, 250, 40);
        panel.add(cbKhuVuc);

        // Nút chức năng
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 20));
        btnThem.setBounds(950, 30, 150, 50);
        panel.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 20));
        btnSua.setBounds(950, 100, 150, 50);
        panel.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 20));
        btnXoa.setBounds(950, 170, 150, 50);
        panel.add(btnXoa);

        // Tìm kiếm
        lblTim = new JLabel("Tìm kiếm:");
        lblTim.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTim.setBounds(950, 240, 100, 30);
        panel.add(lblTim);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 20));
        txtTimKiem.setBounds(1050, 240, 150, 30);
        panel.add(txtTimKiem);

        // Lọc phòng
        lblLocPhong = new JLabel("Lọc phòng:");
        lblLocPhong.setFont(new Font("Arial", Font.PLAIN, 20));
        lblLocPhong.setBounds(950, 280, 100, 30);
        panel.add(lblLocPhong);

        cbLocPhong = new JComboBox<>(new String[]{"Tất cả", "Đã đặt trước", "Có khách", "Trống","Đã gọi món"});
        cbLocPhong.setFont(new Font("Arial", Font.PLAIN, 20));
        cbLocPhong.setBounds(1050, 280, 150, 30);
        cbLocPhong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String trangThaiPhong = (String) cbLocPhong.getSelectedItem();
				if(trangThaiPhong.equals("Tất cả"))
				{
					if(table.getRowCount()!=0)
					xoaHetDongTrongTable();
					napPhong();
				}
				if(trangThaiPhong.equals("Đã đặt trước"))
				{
					if(table.getRowCount()!=0)
					xoaHetDongTrongTable();
					dsp = phd.timPhongDatTruoc();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		model.addRow(rows);
			    	}
				}
				if(trangThaiPhong.equals("Có khách"))
				{
					if(table.getRowCount()!=0)
						xoaHetDongTrongTable();
					dsp = phd.timPhongDangCoKhach();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		model.addRow(rows);
			    	}
				}
				if(trangThaiPhong.equals("Trống"))
				{
					if(table.getRowCount()!=0)
						xoaHetDongTrongTable();
					dsp = phd.timPhongChuaDat();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		model.addRow(rows);
			    	}
				}
				if(trangThaiPhong.equals("Đã gọi món"))
				{
					if(table.getRowCount()!=0)
						xoaHetDongTrongTable();
					dsp = phd.timPhongDaGoiMon();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		model.addRow(rows);
			    	}
				}
				
			}
		});
        panel.add(cbLocPhong);

        // Bảng hiển thị
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(28);
        String[] colTable= {"Mã Phòng", "Tên Phòng", "Số Lượng Chỗ","Trạng Thái","Mã Khu Vực","Tên Khu Vực"};
        table.setModel(model = new DefaultTableModel(colTable, 0)
        {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		});

        JScrollPane scrollPane = new EditScrollPane(table);
        scrollPane.setBounds(50, 330, 1200, 500);
        panel.add(scrollPane);
        txtTrangThai.setEditable(false);
        // Đặt panel chính
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        btnSua.setEnabled(false);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        napKhuVuc();
        napPhong();
        table.addMouseListener(this);
    }

    public void napKhuVuc() {
        dskv = kvd.layToanBoKhuVuc();
        for (KhuVuc kv : dskv) {
            cbKhuVuc.addItem(kv);
        }
    }
    public void napPhong()
    {
    	dsp = phd.getAllPhong();
    	for(Phong p : dsp)
    	{
    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
    		model.addRow(rows);
    	}
    }
    public void themPhong()
    {
    	try
    	{
    		String maPhong = txtMaPhong.getText();
    		String tenPhong = txtTenPhong.getText();
    		int soLuong =0;
    		if(txtSoLuongBan.getText().matches("^[0-9]*$"))
    		soLuong = Integer.parseInt(txtSoLuongBan.getText());
    		else
    		{
    			JOptionPane.showMessageDialog(this,"Số lượng phòng là số và không âm!");
    			return;
    		}
    		KhuVuc kv = (KhuVuc) cbKhuVuc.getSelectedItem();
    		Phong ph = new Phong(maPhong, tenPhong, soLuong,"Trống", kv);
    		if(phd.themPhong(ph))
    		{
    			JOptionPane.showConfirmDialog(this,"Thêm Thành Công!");
    			dsp.add(ph);
    			String[] rows = {ph.getMaPhong(),ph.getTenPhong(),ph.getSoLuongChoNgoi()+"",ph.getMaKhuVuc().getTenKhuVuc(),ph.getMaKhuVuc().getMaKhuVuc()};
        		model.addRow(rows);
        		xoaTrang();
        		return;
    		}
    		else
    		{
    			JOptionPane.showConfirmDialog(this,"Thêm Thất Bại!");
    		}
    	}catch(Exception e)
    	{
    		JOptionPane.showConfirmDialog(this,"Lỗi");
    	}
    }
    public void xoaTrang()
    {
    	try {
			txtMaPhong.setText(phd.generateMaPhong());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ConnectDB.getInstance().connect();
    	txtTenPhong.setText("");
    	txtSoLuongBan.setText("");
    	txtTrangThai.setText("");
    	cbKhuVuc.setSelectedIndex(0);
    	btnThem.setEnabled(true);
    	btnSua.setEnabled(false);
    	table.clearSelection();

    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnThem))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn thêm phòng này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			themPhong();
		}
		if(e.getSource().equals(btnSua))
		{
			int curr = table.getSelectedRow();
			if(curr!=-1)
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn sửa không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			suaPhong(curr);
			else
			{
				return;
			}
		}
		
	}
	public void clickNapBan(int curr)
	{
		txtMaPhong.setText(table.getValueAt(curr, 0).toString());
    	txtTenPhong.setText(table.getValueAt(curr, 1).toString());
    	txtSoLuongBan.setText(table.getValueAt(curr, 2).toString());
    	txtTrangThai.setText(table.getValueAt(curr, 3).toString());
    	cbKhuVuc.setSelectedItem(new KhuVuc(table.getValueAt(curr, 4).toString()));
    	btnThem.setEnabled(false);
    	btnSua.setEnabled(true);
	}
	
	public void xoaHetDongTrongTable() {
	    model.setRowCount(0); // Xóa toàn bộ dữ liệu trong bảng
	}


	public void suaPhong(int curr)
	{
		try
    	{
    		String maPhong = txtMaPhong.getText();
    		String tenPhong = txtTenPhong.getText();
    		String trangThai = txtTrangThai.getText();
    		int soLuong = Integer.parseInt(txtSoLuongBan.getText());
    		KhuVuc kv = (KhuVuc) cbKhuVuc.getSelectedItem();
    		Phong ph = new Phong(maPhong, tenPhong, soLuong,trangThai, kv);
    		if(phd.suaPhong(ph))
    		{
    			JOptionPane.showConfirmDialog(this,"Sửa Thành Công");
    			xoaTrang();
    			xoaHetDongTrongTable();
    			napPhong();
        		return;
    		}
    		else
    		{
    			JOptionPane.showConfirmDialog(this,"Sửa Thất Bại!");
    		}
    	}catch(Exception e)
    	{
    		JOptionPane.showConfirmDialog(this,"Lỗi");
    	}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int curr = table.getSelectedRow();
		if (e.getClickCount()==1)
		{
			clickNapBan(curr);
		}
		if (e.getClickCount()==2)
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


