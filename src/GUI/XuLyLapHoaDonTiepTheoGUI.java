package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.FlatteningPathIterator;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.ChiTietHoaDon_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.Phong_DAO;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.MonAn;
import Entity.NhanVien;
import Entity.Phong;
import Entity.TaiKhoan;
import FILE.InputOutput;


public class XuLyLapHoaDonTiepTheoGUI extends JFrame implements ActionListener{
	public JLabel lblTitle, lblNameCty, lblDiaChi, lblSDTCty, lblEmailCty, lblNameCus, lblSDTCus, lblMaBan, lblMaHD, lblNgayTao, lblrow4, lblrow5, lblrow6;
	public JTable table;
	public JTextField txtTongHD, txtTienKhachDua, txtTienTraLai, txtKhachHang, txtSDTKhachHang;
	public EditJButton thanhToan, thoat;
	
	private Ban ban;
	private Phong ph;
	private ArrayList<ChiTietHoaDon> dscthd;
	private InputOutput io;
	private DefaultTableModel model;
	
	private String tenKhachHang = "Khách Vãng Lai";
	private String ma = "";
	
	private KhachHang kh =null;
	
	private Ban_DAO band;
	private Phong_DAO phd;
	
	private KhachHang_DAO khd;
	
	private HoaDon_DAO hdd ;
	
	private ChiTietHoaDon_DAO cthdd;
	
	private String LoaiKhuVuc;
	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private LocalDateTime now;
	private double tongTienTruocThue;
	private double tongTienSauThue;
	private String maHoaDon;
	
	private TaiKhoan tk;
	public DecimalFormat format = new DecimalFormat("#, ### VND");
	
	
	public XuLyLapHoaDonTiepTheoGUI(TaiKhoan tk,Ban b,Phong p) {
		super("Hóa đơn");
		ConnectDB.getInstance().connect();
		//North 
		io = new InputOutput();
		this.ban=b;
		this.ph =p;
		this.tk =tk;
		cthdd = new ChiTietHoaDon_DAO();
		hdd = new HoaDon_DAO();
		khd = new KhachHang_DAO();
		try
		{
			if(ph!=null) {
			dscthd = (ArrayList<ChiTietHoaDon>) io.readFromFile("tempBan/"+ph.getMaPhong());
			phd = new Phong_DAO();
			ma = ph.getMaPhong();
			LoaiKhuVuc = ph.getMaKhuVuc().getLoaiKhuVuc(); 
			}
			else
			{
			 dscthd = (ArrayList<ChiTietHoaDon>) io.readFromFile("tempBan/"+ban.getMaBan());
			 band = new Ban_DAO();
			 ma = ban.getMaBan();
			 LoaiKhuVuc = ban.getMaKhuVuc().getLoaiKhuVuc(); 
			}
		}catch (Exception e2)
		{
			System.out.println("Chưa có file");
		}
		EditJPanel north = new EditJPanel(1f, Color.decode("#303030"), 0, 0);
		north.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//logo
		ImageIcon logo = new ImageIcon("images/logo.png");
		JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //text
        JPanel jptext = new JPanel();
        jptext.setOpaque(false);
        lblTitle = new JLabel("Hóa Đơn");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        lblTitle.setForeground(Color.white);
        jptext.add(lblTitle);
        
        //Dia chi
        Box jpDiaChi = Box.createVerticalBox();
        lblNameCty = editJLabel("<Cty Ba Con GÀ>", "TimesNewRoman-ITALIC-12", "#FFFFFF");
        lblDiaChi = editJLabel("<Địa chỉ>", "135 Nam Kỳ khởi nghĩa, phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam", "#FFFFFF");
        lblSDTCty = editJLabel("<số điện thoại>", "020930041975", "#FFFFFF");
        lblEmailCty = editJLabel("<email>", "TimesNewRoman-ITALIC-12", "#FFFFFF");
        jpDiaChi.add(lblNameCty);
        jpDiaChi.add(lblDiaChi);
        jpDiaChi.add(lblSDTCty);
        jpDiaChi.add(lblEmailCty);
        
        north.add(logoLabel);
        north.add(Box.createHorizontalStrut(60));
        north.add(jptext);
        north.add(Box.createHorizontalStrut(40));
        north.add(jpDiaChi);

		add(north, BorderLayout.NORTH);
		
		maHoaDon = hdd.generateMaHoaDon();
		ConnectDB.getInstance().connect();
		//Center
		Box center = Box.createVerticalBox();
		add(center, BorderLayout.CENTER);
		
		//Thông tin thanh toán
		JPanel row1 = new JPanel(new GridLayout(3, 2, 10, 10));
		row1.setPreferredSize(new Dimension(600, 10));
		lblMaBan = editJLabel("Mã bàn Or Phòng: "+ma, "TimesNewRoman-BOLD-14", "#000000");
		lblNameCus = editJLabel("Hóa đơn gửi: "+tenKhachHang, "TimesNewRoman-BOLD-14", "#000000");
		lblSDTCus = editJLabel("Số điện thoại: ", "TimesNewRoman-BOLD-14", "#000000");
		lblMaHD = editJLabel("Mã hóa đơn: "+maHoaDon, "TimesNewRoman-BOLD-14", "#000000");
		
		now = LocalDateTime.now();
		
		try {
			if(ban!=null)
			kh = (KhachHang) io.readFromFileKh("tempKhachHang/"+ban.getMaBan().trim());
			else
			kh = (KhachHang) io.readFromFileKh("tempKhachHang/"+ph.getMaPhong().trim());	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblNgayTao = editJLabel("Ngày tạo: "+now.format(formatter1), "TimesNewRoman-BOLD-14", "#000000");
		
		Box rowSDTKH = Box.createHorizontalBox();
		txtSDTKhachHang = editJTextField();
		txtSDTKhachHang.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				kh = khd.timKhachHang(txtSDTKhachHang.getText().trim());
	    		if (kh!=null)
	    		{
	    			lblNameCus.setText("Hóa đơn gửi: "+kh.getHoTen());
	    			txtSDTKhachHang.setText(kh.getSoDienThoai());
	    		}
	    		else
	    		lblNameCus.setText("Hóa đơn gửi: Khách vãng lai");
				
			}
		});
		if(kh!=null)
		{
			kh = khd.timKhachHang(kh.getSoDienThoai().trim());
			lblNameCus.setText("Hóa đơn gửi: "+kh.getHoTen());
			txtSDTKhachHang.setText(kh.getSoDienThoai());
			txtSDTKhachHang.setEditable(false);
		}
		
		rowSDTKH.add(lblSDTCus);
		rowSDTKH.add(txtSDTKhachHang);
		
		row1.add(lblMaBan);
		row1.add(lblNameCus);
		row1.add(rowSDTKH);
		row1.add(lblMaHD);
		row1.add(lblNgayTao);
		
		
		//table 
		String[] columnNames = {"Mã Món Ăn","Tên Món Ăn", "Giá Thành", "SL", "Thành Tiền"};
        
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(false);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(64, 64, 64));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new EditScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setViewportView(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
        //Tổng tiền
        JPanel ttTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Box t1 = Box.createVerticalBox();
       
        
        Box row4 = Box.createHorizontalBox();
		row4.add(lblrow4 = new JLabel("Tổng tiền (Đã Tính Thuế 10%): "));
		row4.add(txtTongHD = editJTextField());
		txtTongHD.setEditable(false);
		
		Box row5 = Box.createHorizontalBox();
		row5.add(lblrow5 = new JLabel("Tiền khách đưa: "));
		row5.add(txtTienKhachDua = editJTextField());
		
		txtTienKhachDua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			if(txtTienKhachDua.getText().matches("^[/d]+$"))
			{
				double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
				if(tienKhachDua< tongTienSauThue)
					{
						thongBaoKhongDuTien();
						txtTienKhachDua.selectAll();
						txtTienKhachDua.requestFocus();
						return;
					}
				else
					{
						txtTienTraLai.setText(format.format(tienKhachDua-tongTienSauThue));
					}
				}else {
					thongBaoTienKhongHopLe();
				}
			}
				
		});
		
		Box row6 = Box.createHorizontalBox();
		row6.add(lblrow6 = new JLabel("Tiền trả lại: "));
		row6.add(txtTienTraLai = editJTextField());
		txtTienTraLai.setEnabled(false);
		lblrow4.setPreferredSize(lblrow5.getPreferredSize());
		lblrow6.setPreferredSize(lblrow5.getPreferredSize());
		
		t1.add(row4);
		t1.add(row5);
		t1.add(row6);
		
		ttTien.add(t1);

        //Nút chức năng 
		JPanel south = new JPanel();
		thanhToan = new EditJButton("<html><center>F5<br>In hóa đơn & Lưu</center></html>", 20);
		thanhToan.setPreferredSize(new Dimension(200, 45));
		thanhToan.setBackground(Color.green);
		thoat = new EditJButton("<html><center>ESC<br>Thoát</center></html>", 20);
		thoat.setPreferredSize(new Dimension(200, 45));
		thoat.setBackground(Color.red);
		south.add(thanhToan);
		south.add(Box.createHorizontalStrut(20));
		south.add(thoat);
        
		center.add(Box.createVerticalStrut(10));
        center.add(row1);
        center.add(Box.createVerticalStrut(10));
        center.add(scrollPane);
        center.add(ttTien);
        center.add(south);
        
		
		setSize(600, 820);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tongTienTruocThue = napTable();
		
		tongTienSauThue = tinhTienSauThue(0.1);
		
		txtTongHD.setText(format.format(tongTienSauThue));
		
		// Gán phím F5 
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "thanhToan");
		getRootPane().getActionMap().put("thanhToan", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        thanhToan.doClick();
		    }
		});

		// Gán phím ESC 
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		    .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "thoat");
		getRootPane().getActionMap().put("thoat", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        thoat.doClick();
		    }
		});

		
		thoat.addActionListener(this);
		thanhToan.addActionListener(this);
		
	}
	
	public void thongBaoTienKhongHopLe() {
		JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!");
		txtTienKhachDua.selectAll();
		txtTienKhachDua.requestFocus();
	}
	
	public void thongBaoKhongDuTien()
	{
		JOptionPane.showMessageDialog(this,"Tiền khách đưa ít hơn tổng tiền!");
		txtTienKhachDua.selectAll();
		txtTienKhachDua.requestFocus();
	}
	public JLabel editJLabel(String name, String font, String color) {
	    JLabel lbl = new JLabel(name);
	    lbl.setForeground(Color.decode(color));
	    lbl.setFont(Font.decode(font));
	    return lbl;
	}
	public JTextField editJTextField() {
	    JTextField text = new JTextField();
	    text.setPreferredSize(new Dimension(200, 30));
	    text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    text.setBackground(Color.WHITE);
	    text.setOpaque(false);
	    return text;
	}
	public double napTable()
	{
		double tongTien = 0;
		if(dscthd.size()>0)
		{	
    		for(ChiTietHoaDon cthd : dscthd)
    		{
    			String[] rows = {cthd.getMaMonAn().getMaMonAn(),cthd.getMaMonAn().getTenMonAn(),cthd.getSoLuong()+"",
    					format.format(cthd.getDonGia()),format.format(cthd.getTongTien())};
    			tongTien += cthd.getTongTien();
    			model.addRow(rows);
    		}
    	}
		if(LoaiKhuVuc.equals("VIP"))
			return tongTien+300000;
		return tongTien;
	}
	public double tinhTienSauThue(double vat)
	{
		return tongTienTruocThue*vat+tongTienTruocThue;
	}
	
	
	public boolean thanhToan()
	{
		String diaChi = "135 Nam Kỳ khởi nghĩa, phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam";
		float thueVat = (float) 0.1;
		if(!txtTienKhachDua.getText().matches("^[/d]+$")) {
			JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!");
			txtTienKhachDua.selectAll();
			txtTienKhachDua.requestFocus();
			return false;
		}
		double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
		if(tienKhachDua< tongTienSauThue)
		{
			JOptionPane.showMessageDialog(this,"Tiền khách đưa ít hơn tổng tiền!");
			txtTienKhachDua.selectAll();
			txtTienKhachDua.requestFocus();
			return false;
		}
		float phiDichVu = 0;
		if(LoaiKhuVuc.equals("VIP"))
			phiDichVu=300000;
		String soDienThoaiNhaHang = "020930041975";
		double khuyenMai =0;
		if(this.ban!=null)
		{
			HoaDon hd = new HoaDon(maHoaDon,now,diaChi,thueVat,tienKhachDua,soDienThoaiNhaHang,phiDichVu,khuyenMai,new NhanVien(tk.getMaNhanVien().getMaNhanVien(),tk.getMaNhanVien().getTenNhanVien())
			,kh,ban,null);
			if(hdd.themHoaDon(hd))
			{
				for(ChiTietHoaDon cthd: dscthd)
				{
					cthdd.themChiTietHoaDon(new ChiTietHoaDon(hd,new MonAn(cthd.getMaMonAn().getMaMonAn(),cthd.getMaMonAn().getTenMonAn()),cthd.getSoLuong(),cthd.getDonGia()));
				}
				if (kh!=null)
				{
					khd.congDiemKhachHang(kh,(tongTienSauThue/10));
				}
				band.setTrangThaiBan(ban.getMaBan(),"Trống");
				io.deleteSpecificFile("tempBan/"+ban.getMaBan().trim());
				io.deleteSpecificFile("tempKhachHang/"+ban.getMaBan().trim());
			}
			io.xuatHoaDon(hd,dscthd,"tempHoaDon/"+maHoaDon+".pdf",tongTienTruocThue,tongTienSauThue);
		}
		else
		{
			HoaDon hd = new HoaDon(maHoaDon,now,diaChi,thueVat,tienKhachDua,soDienThoaiNhaHang,phiDichVu,khuyenMai,new NhanVien(tk.getMaNhanVien().getMaNhanVien(),tk.getMaNhanVien().getTenNhanVien())
			,kh,null,ph);
			if(hdd.themHoaDon(hd))
			{
				for(ChiTietHoaDon cthd: dscthd)
				{
					cthdd.themChiTietHoaDon(new ChiTietHoaDon(hd,new MonAn(cthd.getMaMonAn().getMaMonAn(),cthd.getMaMonAn().getTenMonAn()),cthd.getSoLuong(),cthd.getDonGia()));
				}
				if (kh!=null)
				{
					khd.congDiemKhachHang(kh,(tongTienSauThue/10));
				}
				phd.setTrangThaiPhong(ph.getMaPhong(),"Trống");
				io.deleteSpecificFile("tempBan/"+ph.getMaPhong());
				io.deleteSpecificFile("tempKhachHang/"+ph.getMaPhong());
			}
			io.xuatHoaDon(hd,dscthd,"tempHoaDon/"+maHoaDon+".pdf",tongTienTruocThue,tongTienSauThue);
		}
		return true;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(thanhToan))
		{
			if(JOptionPane.showConfirmDialog(null,"Bạn có muốn lập và lưu hóa đơn không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
				if(thanhToan()) {
					XuLyLapHoaDonGUI.listBan.removeAll();
					XuLyLapHoaDonGUI.napBanDaGoiMon();
					XuLyLapHoaDonGUI.napPhongDaGoiMon();
					XuLyLapHoaDonGUI.listBan.revalidate();
					XuLyLapHoaDonGUI.listBan.repaint();
					
					this.dispose();
				}
			}
			
		}if(e.getSource() == thoat) {
			dispose();
		}
		
	}
}