package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.KhuVuc_DAO;
import DAO.Phong_DAO;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.KhuVuc;
import Entity.Phong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CapNhatBanAnGUI extends JPanel implements ActionListener,MouseListener{
    // Biến toàn cục
    public JTextField txtMaBan;
    public JTextField txtTenBan;
    public JTextField txtSoChoNgoi;
    public JComboBox<KhuVuc> cbKhuVuc;
    public JButton btnThem;
    public JButton btnSua;
    public JTextField txtTimKiem;
    public JComboBox<String> cbLocPhong;
    public JTable table;

    
    private ArrayList<KhuVuc> dskv;
    private KhuVuc_DAO kvd;
    private ArrayList<Ban> dsb;
    private Ban_DAO bd;
	private DefaultTableModel model;
	private JTextField txtTenKhuVuc;
	private JTextField txtTrangThai;
    
    public CapNhatBanAnGUI() {
    	ConnectDB.getInstance().connect();
        kvd = new KhuVuc_DAO();
        bd = new Ban_DAO();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1250, 700));

        // ===== Panel trên cùng: nhập liệu + chức năng =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1250, 300));

        // Vùng nhập liệu bên trái
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin bàn ăn"));
        formPanel.setPreferredSize(new Dimension(600, 300));
        formPanel.setFont(new Font("Arial", Font.PLAIN, 20));

        txtMaBan = new JTextField();
        txtTenBan = new JTextField();
        txtSoChoNgoi = new JTextField();
        txtTrangThai = new JTextField();
        cbKhuVuc = new JComboBox<KhuVuc>();
        
        
        formPanel.add(createLabeledPanel("Mã bàn:       ", txtMaBan));
        formPanel.add(createLabeledPanel("Tên bàn:      ", txtTenBan));
        formPanel.add(createLabeledPanel("Số chỗ ngồi: ", txtSoChoNgoi));
        formPanel.add(createLabeledPanel("Trạng thái:   ", txtTrangThai));
     // Panel chứa mã phòng + tên khu vực kế bên
        JPanel panelPhongKhuVuc = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel maPhongPanel = createLabeledPanel("Mã Khu Vực:   ",  cbKhuVuc);
        txtTenKhuVuc = new JTextField();
        txtTenKhuVuc.setEditable(false); // không cho người dùng sửa
        JPanel tenKhuVucPanel = createLabeledPanel("Tên khu vực:  ", txtTenKhuVuc);

        panelPhongKhuVuc.add(maPhongPanel);
        panelPhongKhuVuc.add(tenKhuVucPanel);

        formPanel.add(panelPhongKhuVuc);

        // Vùng nút chức năng + tìm kiếm bên phải
        JPanel controlPanel = new JPanel(null);
        controlPanel.setPreferredSize(new Dimension(600, 300));

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 18));
        btnThem.setBounds(50, 30, 150, 40);
        controlPanel.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 18));
        btnSua.setBounds(220, 30, 150, 40);
        controlPanel.add(btnSua);

        JLabel lblTim = new JLabel("Tìm kiếm:");
        lblTim.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTim.setBounds(50, 100, 100, 30);
        controlPanel.add(lblTim);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 18));
        txtTimKiem.setBounds(150, 100, 250, 30);
        controlPanel.add(txtTimKiem);

        JLabel lblLocPhong = new JLabel("Lọc bàn:");
        lblLocPhong.setFont(new Font("Arial", Font.PLAIN, 18));
        lblLocPhong.setBounds(50, 150, 100, 30);
        controlPanel.add(lblLocPhong);

        cbLocPhong = new JComboBox<>(new String[]{"Tất cả", "Đã đặt trước", "Có khách", "Trống","Đã gọi món"});
        cbLocPhong.setFont(new Font("Arial", Font.PLAIN, 18));
        cbLocPhong.setBounds(150, 150, 250, 30);
        cbLocPhong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String trangThaiPhong = (String) cbLocPhong.getSelectedItem();
				if(trangThaiPhong.equals("Tất cả"))
				{
					if(table.getRowCount()!=0)
					xoaHetDongTrongTable();
					napBan();
				}
				if(trangThaiPhong.equals("Đã đặt trước"))
				{
					if(table.getRowCount()!=0)
					xoaHetDongTrongTable();
					dsb = bd.timBanDatTruoc();
					for (Ban ban : dsb) {
			        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
			        	String maBan = ban.getMaBan();
			        	String tenBan = ban.getTenBan();
			        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
			        	String trangThai = ban.getTrangThai();
			        	
			        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
			        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
			        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
			        	model.addRow(rows);
			        }
				}
				if(trangThaiPhong.equals("Có khách"))
				{
					if(table.getRowCount()!=0)
						xoaHetDongTrongTable();
						dsb = bd.timBanDangCoKhach();
						for (Ban ban : dsb) {
				        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
				        	String maBan = ban.getMaBan();
				        	String tenBan = ban.getTenBan();
				        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
				        	String trangThai = ban.getTrangThai();
				        	
				        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
				        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
				        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
				        	model.addRow(rows);
				        }
				}
				if(trangThaiPhong.equals("Trống"))
				{
					if(table.getRowCount()!=0)
						xoaHetDongTrongTable();
						dsb = bd.timBanChuaDat();
						for (Ban ban : dsb) {
				        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
				        	String maBan = ban.getMaBan();
				        	String tenBan = ban.getTenBan();
				        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
				        	String trangThai = ban.getTrangThai();
				        	
				        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
				        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
				        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
				        	model.addRow(rows);
				        }
				}
				if(trangThaiPhong.equals("Đã gọi món"))
				{
					if(table.getRowCount()!=0)
						xoaHetDongTrongTable();
						dsb = bd.timBanDaGoiMon();
						for (Ban ban : dsb) {
				        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
				        	String maBan = ban.getMaBan();
				        	String tenBan = ban.getTenBan();
				        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
				        	String trangThai = ban.getTrangThai();
				        	
				        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
				        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
				        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
				        	model.addRow(rows);
				        }
				}
				
			}
		});
        controlPanel.add(cbLocPhong);

        topPanel.add(formPanel, BorderLayout.WEST);
        topPanel.add(controlPanel, BorderLayout.CENTER);

        // ===== Bảng không cho chỉnh sửa =====
        table = new JTable(model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Mã Khu vực","Tên Khu Vực"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        });

        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scrollPane = new EditScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 350));

        // ===== Thêm vào giao diện chính =====
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        txtMaBan.setEditable(false);
        try {
			txtMaBan.setText(bd.generateMaBan());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        cbKhuVuc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KhuVuc kv = (KhuVuc) cbKhuVuc.getSelectedItem();
				setTextKV(kv.getTenKhuVuc());			
			}
		});
        txtTrangThai.setEditable(false);
        setTextKV("Khu Vực 1");
        ConnectDB.getInstance().connect();
        napKhuVuc();
        napBan();
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        table.addMouseListener(this);
        
    }
    private JPanel createLabeledPanel(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(lbl, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    public void setTextKV(String txt)
    {
    	txtTenKhuVuc.setText(txt);
    }
    public void napKhuVuc()
    {
    	dskv =kvd.layToanBoKhuVuc();
    	for (KhuVuc kv :dskv)
    	{
    		cbKhuVuc.addItem(kv);
    	}
    }
    public void napBan()
    {
        dsb = bd.getAllBan();
        for (Ban ban : dsb) {
        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
        	String maBan = ban.getMaBan();
        	String tenBan = ban.getTenBan();
        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
        	String trangThai = ban.getTrangThai();
        	
        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
        	model.addRow(rows);
        }
    }
    public void themBan()
    {
    	try
    	{
    		String maBan = txtMaBan.getText();
        	String tenBan = txtTenBan.getText();
        	
        	int soChoNgoi =0;
        	if(txtSoChoNgoi.getText().matches("^[0-9]*$"))
        	soChoNgoi = Integer.parseInt(txtSoChoNgoi.getText());
        	else
        	{
        		JOptionPane.showMessageDialog(this,"Chỗ ngồi là số và không âm!");
        		return;
        	}
        	String trangThai = "Trống";
        	
        	KhuVuc kv = (KhuVuc) cbKhuVuc.getSelectedItem();
        	
        	Ban ban = new Ban(maBan,tenBan,soChoNgoi,trangThai,kv);
        	if(bd.themBan(ban))
        	{
    			JOptionPane.showMessageDialog(this,"Thêm Thành Công");
        		xoaTrang();
        		xoaHetDongTrongTable();
        		napBan();
        		return;
        	}
    	}catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(this,"Thêm Thất Bại");
    	}
    }
    public void xoaTrang()
    {
    	try {
			txtMaBan.setText(bd.generateMaBan());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ConnectDB.getInstance().connect();
    	txtTenBan.setText("");
    	txtSoChoNgoi.setText("");
    	cbKhuVuc.setSelectedIndex(0);
    	btnThem.setEnabled(true);
    	btnSua.setEnabled(false);
    	table.clearSelection();

    	
    }
    public void dayLen(int curr)
    {
    	txtMaBan.setText(table.getValueAt(curr, 0).toString());
    	txtTenBan.setText(table.getValueAt(curr, 1).toString());
    	txtSoChoNgoi.setText(table.getValueAt(curr, 2).toString());
    	txtTrangThai.setText(table.getValueAt(curr, 3).toString());
    	cbKhuVuc.setSelectedItem(new Phong(table.getValueAt(curr, 4).toString()));
    	txtTenKhuVuc.setText(table.getValueAt(curr, 5).toString());
    	btnSua.setEnabled(true);
    	btnThem.setEnabled(false);
    }
    public void xoaHetDongTrongTable() {
        model.setRowCount(0); // Xóa toàn bộ dữ liệu trong bảng
    }

    public void capNhatBan()
    {
    	try
    	{
    		String maBan = txtMaBan.getText();
        	String tenBan = txtTenBan.getText();
        	int soChoNgoi = Integer.parseInt(txtSoChoNgoi.getText());
        	String trangThai = "Trống";
        	KhuVuc kv = (KhuVuc) cbKhuVuc.getSelectedItem();
        	Ban ban = new Ban(maBan,tenBan,soChoNgoi,trangThai,kv);
        	if(bd.capNhatBan(ban))
        	{
    			JOptionPane.showMessageDialog(this,"Sửa Thành Công");
        		xoaTrang();
        		xoaHetDongTrongTable();
        		napBan();
        		return;
        	}
    	}catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(this,"Sửa Thất Bại");
    	}
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnThem))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn thêm bàn này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			themBan();
		}
		if(e.getSource().equals(btnSua))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn sửa thông tin bàn này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			capNhatBan();
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int curr = table.getSelectedRow();
		if (e.getClickCount()==1)
		{
			dayLen(curr);
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

