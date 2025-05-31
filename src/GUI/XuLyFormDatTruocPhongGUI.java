package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.KhachHang_DAO;
import DAO.PhieuDatBan_DAO;
import DAO.PhieuDatPhong_DAO;
import DAO.Phong_DAO;
import EDIT.BackgroundPanel;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import EDIT.HintTextField;
import Entity.Ban;
import Entity.KhachHang;
import Entity.LoaiKhachHang;
import Entity.PhieuDatBan;
import Entity.PhieuDatPhong;
import Entity.Phong;
import Entity.TaiKhoan;
import FILE.InputOutput;
import EDIT.HintTextArea;

public class XuLyFormDatTruocPhongGUI extends JFrame implements ActionListener{
    public JTextField txtHoTen, txtSDT, txtMaPhieu;
    public HintTextArea txtGhiChu;
    public EditJButton xacNhan, huy;
    public JDateChooser txtNgayDat;
    public JSpinner txtSLNgoi;
    
    private TaiKhoan tk;
    private Phong p;
    
    private PhieuDatPhong_DAO phdpd;
	private KhachHang_DAO khd;
	private JTextField txtTenNhanVien;
	private JTextField txtMaRoomTable;
    private Phong_DAO phd;
    private InputOutput io;
    private KhachHang kh =null;
    
    
    public XuLyFormDatTruocPhongGUI(TaiKhoan tk,Phong p) {
        super("Đặt Trước");
        ConnectDB.getInstance().connect();
        this.tk =tk;
        this.p = p;
        this.tk = tk;
        phdpd = new PhieuDatPhong_DAO();
        khd = new KhachHang_DAO();
        phd = new Phong_DAO();
        io = new InputOutput();
        JPanel mainPanel = new BackgroundPanel("images/healthy.jpg");
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        EditJPanel nen = new EditJPanel(0.4f, Color.black, 0, 0);
        mainPanel.add(nen, BorderLayout.CENTER);

        EditJPanel center = new EditJPanel(0.7f, Color.white, 40, 40);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        nen.setLayout(new GridBagLayout());
        nen.add(center);

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.add(editJLabel("Đơn Đặt Phòng Trước", 30));

        Box form = Box.createVerticalBox();
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtHoTen = editJTextField("Họ và Tên");
        txtSDT = editJTextField("Số Điện Thoại");
        txtSDT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtSDT.getText().isEmpty())
				{
					txtHoTen.setEditable(true);
				}
	    		kh = khd.timKhachHang(txtSDT.getText().trim());
	    		if (kh!=null)
	    		{
	    			txtHoTen.setText(kh.getHoTen());
	    			txtHoTen.setEditable(false);
	    		}
	    		
	    		else
	    		txtHoTen.setText("Không tồn tại!");
				
			}
		});
        txtNgayDat = new JDateChooser();
        txtNgayDat.setDateFormatString("dd/MM/yyyy");
        txtNgayDat.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtNgayDat.setOpaque(false);
        txtNgayDat.setPreferredSize(new Dimension(140, 30));
        txtNgayDat.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));


        JTextField dateEditor = (JTextField) txtNgayDat.getDateEditor().getUiComponent();
        dateEditor.setText("Chọn ngày đặt");
        dateEditor.setForeground(Color.GRAY);
        txtNgayDat.getDateEditor().addPropertyChangeListener("date", e -> {
            dateEditor.setForeground(Color.BLACK);
        });

        txtSLNgoi = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        txtSLNgoi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtSLNgoi.setPreferredSize(new Dimension(50, 30));
        txtSLNgoi.setOpaque(false);
        txtSLNgoi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));

        txtGhiChu = new HintTextArea("Ghi chú");
        txtGhiChu.setPreferredSize(new Dimension(200, 50));
        txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));

        Box row0 = Box.createHorizontalBox();
        row0.add(editJLabel("Phòng: ", 20));
        txtMaRoomTable = new JTextField();
        txtMaRoomTable.setOpaque(false);
        txtMaRoomTable.setBorder(null);
        txtMaRoomTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtMaRoomTable.setPreferredSize(new Dimension(100, 50));
        txtMaRoomTable.setText(p.getMaPhong());
        row0.add(txtMaRoomTable);
        
        Box row05 = Box.createHorizontalBox();
        row05.add(editJLabel("Mã Phiếu Đặt Phòng: ", 20));
        txtMaPhieu = new JTextField();
        txtMaPhieu.setOpaque(false);
        txtMaPhieu.setBorder(null);
        txtMaPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtMaPhieu.setPreferredSize(new Dimension(100, 50));
        try {
			txtMaPhieu.setText(phdpd.generateMaPhieuDatPhong());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ConnectDB.getInstance().connect();
        txtMaPhieu.setEditable(false);
        row05.add(txtMaPhieu);
        
        
        Box row06 = Box.createHorizontalBox();
        row06.add(editJLabel("Tên Nhân Viên: ", 20));
        txtTenNhanVien = new JTextField();
        txtTenNhanVien.setOpaque(false);
        txtTenNhanVien.setBorder(null);
        txtTenNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtTenNhanVien.setPreferredSize(new Dimension(100, 50));
        txtTenNhanVien.setEditable(false);
        txtTenNhanVien.setText(tk.getMaNhanVien().getTenNhanVien());
        row06.add(txtTenNhanVien);
        
        Box row1 = Box.createHorizontalBox();
        row1.add(txtNgayDat);
        row1.add(Box.createHorizontalStrut(10));
        row1.add(txtSLNgoi);

        xacNhan = new EditJButton("Xác Nhận", 20);
        xacNhan.setBackground(new Color(255, 215, 0));

        huy = new EditJButton("Hủy", 20);
        huy.setBackground(Color.red);

        JPanel row2 = new JPanel();
        row2.setOpaque(false);
        row2.add(xacNhan);
        row2.add(huy);
        
        form.add(row0);
        form.add(row05);
        form.add(row06);
        form.add(Box.createVerticalStrut(20));
        form.add(txtHoTen);
        form.add(Box.createVerticalStrut(20));
        form.add(txtSDT);
        form.add(Box.createVerticalStrut(20));
        form.add(row1);
        form.add(Box.createVerticalStrut(20));
        form.add(txtGhiChu);
        form.add(Box.createVerticalStrut(20));
        form.add(row2);

        center.add(title);
        center.add(form);

        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        xacNhan.addActionListener(this);
        huy.addActionListener(this);
    }

    public JLabel editJLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.white);
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }

    public JTextField editJTextField(String moTa) {
        HintTextField textField = new HintTextField(moTa);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        return textField;
    }
    public boolean xuLyDatTruoc()
    {
    	try
    	{
    		String maPhieu = txtMaPhieu.getText();
    		LocalDateTime ngayDatBan = LocalDateTime.now();
            
    		Date selectedDate = txtNgayDat.getDate();
    		if (selectedDate == null) { 
    			JOptionPane.showMessageDialog(this, "Chưa Chọn Ngày Nhận Phòng!");
    			return false;
    				}
    		if (!selectedDate.after(new Date())) { 
    			JOptionPane.showMessageDialog(this, "Ngày Nhận Phải Sau Ngày Hôm Nay!");
    			return false;
    				
    		}
    		if(txtHoTen.getText().isEmpty() && txtSDT.getText().isEmpty())
    		{
    			JOptionPane.showMessageDialog(this, "Chưa Nhập Thông Tin Khách Hàng!");
    			return false;
    		}
    		if(kh==null)
    		{
    			String sdt = txtSDT.getText();
    			if(!sdt.matches("^[0-9]{10}$"))
    			{
    				JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!");
    				return false;
    			}
    			if(khd.timKhachHang(txtSDT.getText().trim())==null)
    			{
    				kh = new KhachHang(sdt,txtHoTen.getText(),"",0,0,new LoaiKhachHang("ML00001","Đồng"));
        			khd.themKhachHang(kh);
    			}
    				
    		}
    		LocalDateTime ngayNhanBan = LocalDateTime.ofInstant(selectedDate.toInstant(), ZoneId.systemDefault());
    		String diaChiQuan = "135 Nam Kỳ khởi nghĩa, phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam";
    		String soDienThoaiQuan = "020930041975";
    		String ghiChu = txtGhiChu.getText();
    		
    		PhieuDatPhong pdb = new PhieuDatPhong(maPhieu, ngayDatBan, ngayNhanBan, ghiChu, diaChiQuan, soDienThoaiQuan,this.p,tk.getMaNhanVien(),kh);
    		if(phdpd.themPhieuDatPhong(pdb))
    		{
    			io.writeToFile(kh,"tempKhachHang/"+p.getMaPhong().trim());
    			phd.setTrangThaiPhong(p.getMaPhong().trim(), "Đã đặt trước");
    			JOptionPane.showMessageDialog(this, "Đặt Trước Thành Công!");
    		}
    	}catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(this, "Đặt Trước Thất Bại!");
    		return false;
    	}
    	
    	return true;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(xacNhan))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn đặt trước phòng này không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
				if(xuLyDatTruoc()) {
					XuLyDatPhongGUI.listBan.removeAll();
					XuLyDatPhongGUI.napPhongTheoKhuVuc(p.getMaKhuVuc().getMaKhuVuc());
					XuLyDatPhongGUI.listBan.revalidate();
					XuLyDatPhongGUI.listBan.repaint();
					this.dispose();
				}
				
				
			}
		}
		if(e.getSource().equals(huy))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn tắt không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			this.dispose();
		}
		
	}

}
