package GUI;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import javax.swing.*;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.HoaDon_DAO;
import DAO.MonAn_DAO;
import DAO.Phong_DAO;
import EDIT.EditJPanel;
import Entity.TaiKhoan;

public class TrangChuGUI extends JPanel {
    public JLabel nameUser, chucVu;
    public String doanhThuToday, slBanTrong, slBanDatTruoc;
    public String[] images = {
        "images/monAn/pho.jpg", "images/monAn/salmon.jpg", "images/monAn/chicken.jpg", "images/monAn/goi_ga.jpg", "images/monAn/beaf_steak.jpg"
    };
    public DecimalFormat format = new DecimalFormat("#,### VND");
    double tongDoanhThu=0;
    private TaiKhoan tk ;
    
    private HoaDon_DAO hdd;
    private Ban_DAO band;
    private Phong_DAO phd;
    private MonAn_DAO mad;
    
    
    public TrangChuGUI(TaiKhoan tk) {
        setLayout(new BorderLayout());
        ConnectDB.getInstance().connect();
        this.tk = tk;
        // NORTH
        this.hdd = new HoaDon_DAO();
        this.mad = new MonAn_DAO();
        this.band = new Ban_DAO();
        this.phd = new Phong_DAO();
        EditJPanel north = new EditJPanel(0.8f, Color.decode("#76D7C4"), 40, 40);
        north.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(north, BorderLayout.NORTH);

        // Logo
        EditJPanel nen = new EditJPanel(0.7f, Color.decode("#D5F5E3"), 120, 120);
        nen.setLayout(new BoxLayout(nen, BoxLayout.X_AXIS));
        ImageIcon logo = new ImageIcon("images/logo.png");
        JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nen.add(logoLabel);

        // Thông tin nhân viên
        Box boxInfo = Box.createHorizontalBox();
        Box info = Box.createVerticalBox();
        info.add(nameUser = new JLabel(tk.getMaNhanVien().getTenNhanVien()));
        info.add(chucVu = new JLabel(tk.getMaNhanVien().getMaLoaiNhanVien().getTenLoai()));

        ImageIcon userIcon = new ImageIcon("icons/icons8-user.png");
        JLabel avatar = new JLabel(new ImageIcon(userIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));

        boxInfo.add(info);
        boxInfo.add(Box.createHorizontalStrut(10));
        boxInfo.add(avatar);

        north.add(nen);
        north.add(Box.createHorizontalStrut(100));
        north.add(editJLabel("Trang Chủ", 50));
        north.add(Box.createHorizontalStrut(600));
        north.add(boxInfo);

        // CENTER
        Box center = Box.createVerticalBox();
        center.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(center, BorderLayout.CENTER);

        JPanel row1 = new JPanel(new GridLayout(1, 4, 30, 0));
        EditJPanel boxDoanhThu = new EditJPanel(0.9f, Color.decode("#D5F5E3"), 20, 20);
        boxDoanhThu.setLayout(new BoxLayout(boxDoanhThu, BoxLayout.Y_AXIS));
        boxDoanhThu.add(editJLabel("Doanh Thu Hôm Nay", 20));
        napDoanhThuTheoNgay();
        boxDoanhThu.add(editJLabel(format.format(tongDoanhThu), 30));

        EditJPanel boxBanTrong = new EditJPanel(0.9f, Color.decode("#D5F5E3"), 20, 20);
        boxBanTrong.setLayout(new BoxLayout(boxBanTrong, BoxLayout.Y_AXIS));
        boxBanTrong.add(editJLabel("Bàn Trống", 20));
        boxBanTrong.add(editJLabel(band.getTongSoBanTrong()+"", 30));

        
        EditJPanel boxPhongTrong = new EditJPanel(0.9f, Color.decode("#D5F5E3"), 20, 20);
        boxPhongTrong.setLayout(new BoxLayout(boxPhongTrong, BoxLayout.Y_AXIS));
        boxPhongTrong.add(editJLabel("Phòng Trống", 20));
        boxPhongTrong.add(editJLabel(phd.getTongSoPhongTrong()+"", 30));
        
        EditJPanel boxBanDatTruoc = new EditJPanel(0.9f, Color.decode("#D5F5E3"), 20, 20);
        boxBanDatTruoc.setLayout(new BoxLayout(boxBanDatTruoc, BoxLayout.Y_AXIS));
        boxBanDatTruoc.add(editJLabel("Bàn Đặt Trước", 20));
        boxBanDatTruoc.add(editJLabel(band.getTongSoBanDatTruoc()+"", 30));

        row1.add(boxDoanhThu);
        row1.add(boxBanTrong);
        row1.add(boxPhongTrong);
        row1.add(boxBanDatTruoc);

        // Biểu đồ doanh thu tuần
        JPanel row2 = new JPanel(new BorderLayout());
        row2.add(editJLabel("Doanh Thu 7 Ngày Trước", 25), BorderLayout.NORTH);
        row2.add(taoBieuDoBar(), BorderLayout.CENTER);

        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.Y_AXIS));
        row3.setBackground(Color.decode("#D5F5E3"));
        
        
        // Top món ăn
        JPanel listMonAn = new JPanel(new GridLayout(1, 5, 10, 10));
        Object[][] top5MonAn = mad.layTop10MonAnBanChay();
        int count =0;
    	for (Object[] top5: top5MonAn)
    	{
    		if(count ==5)
    		{
    			break;
    		}
    		String tenMonAn = (String) top5[0];
    		String hinhAnh = (String) top5[1];
    		int soLuong = (int) top5[2];
    		listMonAn.add(monAn(hinhAnh, tenMonAn, soLuong));
    		count+=1;
    	}


        row3.add(editJLabel("Top Món Ăn Bán Chạy", 20));
        row3.add(listMonAn);
        
        center.add(Box.createVerticalStrut(10));
        center.add(row1);
        center.add(Box.createVerticalStrut(10));
        center.add(row2);
        center.add(Box.createVerticalStrut(10));
        center.add(row3);
        center.add(Box.createVerticalStrut(10));

    }


    public JLabel editJLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.decode("#2C3E50"));
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }

    public JPanel taoBieuDoBar() {
        JPanel chartPanel = new JPanel(new BorderLayout()) {
        	@Override
        	protected void paintComponent(Graphics g) {
        	    super.paintComponent(g);
        	    Graphics2D g2 = (Graphics2D) g.create();
        	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        	    int width = getWidth();
        	    int height = getHeight();
        	    int paddingLeft = 60;
        	    int paddingBottom = 30;
        	    int usableHeight = height - paddingBottom;
        	    int spacing = 80;

        	    Object[][] data = hdd.layDoanhThu7NgayGanNhat();

        	    int maxValue = 0;
        	    for (Object[] point : data) {
        	        maxValue = Math.max(maxValue, (int)point[1]);
        	    }

        	    int step = 2_000_000;
        	    int roundedMax = ((maxValue + step - 1) / step) * step + step;

        	    int barCount = data.length;
        	    int barWidth = 100;

        	    g2.setColor(Color.decode("#2C3E50"));
        	    g2.setFont(new Font("SansSerif", Font.PLAIN, 11));

        	    // Trục Y
        	    for (int i = 0; i <= roundedMax; i += step) {
        	        int y = usableHeight - (int) ((i / (double) roundedMax) * usableHeight);
        	        String label = (i / 1_000_000) + " triệu VND";
        	        g2.drawString(label, 5, y + 5);
        	        g2.setColor(new Color(255, 255, 255, 40));
        	        g2.drawLine(paddingLeft - 10, y, width, y);
        	        g2.setColor(Color.decode("#2C3E50"));
        	    }

        	    // Cột và nhãn trục X
        	    for (int i = 0; i < barCount; i++) {
        	        int value = (int) data[i][1];
        	        int barHeight = (int) ((value / (double) roundedMax) * usableHeight);
        	        int x = paddingLeft + spacing + i * (barWidth + spacing);
        	        int y = usableHeight - barHeight;

        	        g2.setPaint(new GradientPaint(x, y, Color.decode("#FAD7A0"), x, y + barHeight, Color.decode("#F39C12")));
        	        g2.fillRect(x, y, barWidth, barHeight);

        	        g2.setColor(Color.decode("#2C3E50"));
        	        g2.drawRect(x, y, barWidth, barHeight);

        	        // Vẽ nhãn bên dưới
        	        String label = (String) data[i][0];
        	        FontMetrics fm = g2.getFontMetrics();
        	        int labelWidth = fm.stringWidth(label);
        	        g2.drawString(label, x + (barWidth - labelWidth) / 2, height - 10);
        	    }

        	    g2.dispose();
        	}

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 150); 
            }
        };

        chartPanel.setBackground(Color.decode("#F9F9F9"));
        return chartPanel;
    }
    public void napDoanhThuTheoNgay()
    {
    	LocalDateTime homnay = LocalDateTime.now();
    	tongDoanhThu = hdd.getDoanhThuTheoNgay(homnay);
    }

    public JPanel monAn(String images, String tenMon, int orders) {
        EditJPanel form = new EditJPanel(1f, Color.decode("#FBFCFC"), 50, 50);
        
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        EditJPanel formImages = new EditJPanel(0.6f, Color.white, 0, 0);
        ImageIcon image = new ImageIcon(images);
        formImages.add(new JLabel(new ImageIcon(image.getImage().getScaledInstance(80, 50, Image.SCALE_SMOOTH))));

        Box ttMonAn = Box.createVerticalBox();
        JLabel lblTen = new JLabel(tenMon);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblOrders = new JLabel("Orders: " + orders);
        lblOrders.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblOrders.setAlignmentX(Component.CENTER_ALIGNMENT);
        ttMonAn.add(lblTen);
        ttMonAn.add(lblOrders);

        form.add(formImages);
        form.add(ttMonAn);
        return form;
    }
}
