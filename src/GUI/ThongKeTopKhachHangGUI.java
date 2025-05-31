package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import ConnectDB.ConnectDB;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import EDIT.BackgroundPanel;
import EDIT.BarChartPanel;
import EDIT.EditJPanel;
import Entity.TaiKhoan;

public class ThongKeTopKhachHangGUI extends JPanel {
	public JLabel nameUser, chucVu;
    public JMonthChooser txtThang;
    public JYearChooser txtNam;
    private  TaiKhoan tk;
	private EditJPanel b1;
	private EditJPanel b2;
	private EditJPanel b3;
	private EditJPanel b4;
	private ArrayList<String> loaiRank;
	private ArrayList<Integer> phanTram;
	
	private HoaDon_DAO hdd;
	private KhachHang_DAO khd;
	private JPanel left;
	private JPanel right;
	private Box boxTop23;
	private EditJPanel boxTopCus;
	public DecimalFormat format = new DecimalFormat("#, ### VND");
	
	public ThongKeTopKhachHangGUI(TaiKhoan tk) {
		setLayout(new BorderLayout());
		//north
		ConnectDB.getInstance().connect();
		this.tk =tk;
		hdd = new HoaDon_DAO();
		khd = new KhachHang_DAO();
		EditJPanel north = new EditJPanel(0.9f, Color.decode("#76D7C4"), 30, 30);
        north.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(north, BorderLayout.NORTH);

        ImageIcon userIcon = new ImageIcon("icons/icons8-user.png");
        JLabel uI = new JLabel(new ImageIcon(userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        Box info = Box.createVerticalBox();
        info.add(nameUser = new JLabel(tk.getMaNhanVien().getTenNhanVien()));
        info.add(chucVu = new JLabel(tk.getMaNhanVien().getMaLoaiNhanVien().getTenLoai()));

        north.add(info);
        north.add(uI);
        north.add(Box.createHorizontalStrut(20));
        
        //Center
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        
        // Row 1
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        txtThang = new JMonthChooser();
        txtThang.setPreferredSize(new Dimension(200, 30));
        txtThang.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        txtNam = new JYearChooser();
        txtNam.setPreferredSize(new Dimension(100, 30));
        txtNam.setFont(new Font("Segoe UI", Font.ITALIC, 20));

        row1.add(editJLabel("Thống kê theo tháng: ", 20));
        row1.add(Box.createHorizontalStrut(10));
        row1.add(txtThang);
        row1.add(Box.createHorizontalStrut(100));
        row1.add(editJLabel("Thống kê theo năm: ", 20));
        row1.add(Box.createHorizontalStrut(10));
        row1.add(txtNam);
        
        txtThang.addPropertyChangeListener("month", (PropertyChangeListener) new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                int thang = txtThang.getMonth() + 1; 
                int nam = txtNam.getYear();
                
                int soLuongKhachQuen = khd.tinhSoKhachQuen(thang, nam);
                int soKhachVangLai = khd.tinhSoKhachVangLai(thang, nam);
                
                double tongDoanhThuTop10 = khd.tinhTongDoanhThuTop10KhachHang(thang, nam);
                
                Object[][] top10KhachHang =khd.layTop10KhachHangTheoDoanhThu(thang, nam);
                
                
                
                left.removeAll();
                left.add(editJLabel("Doanh Thu Top 10 Khách Hàng", 25));
                left.add(taoBieuDoBar(top10KhachHang));
                
                if(top10KhachHang.length>0)
                {
                	boxTop23.removeAll();
                    boxTopCus.removeAll();
                    boxTop23.add(createTopBox("icons/icons8-number-two.png", top10KhachHang[1][0]+""));
                    boxTop23.add(Box.createHorizontalStrut(20));
                    boxTop23.add(createTopBox("icons/icons8-number-three.png",top10KhachHang[2][0]+""));
                    
                    boxTopCus.add(createTopBox("icons/icons8-number-one.png", top10KhachHang[0][0]+""));
                    boxTopCus.add(boxTop23);
                }
                
                
                loaiRank.removeAll(loaiRank);
                phanTram.removeAll(phanTram);
                Object[][] danhsachma = khd.demSoLuotLoaiKhachHangTheoThangNam(thang, nam);

                for(Object[] dong: danhsachma)
                {
                	loaiRank.add((String) dong[0]);
                	phanTram.add((int) dong[1]);
                }
                right.removeAll();
                JPanel pieChart = taoBDTronTheoNgay(loaiRank, phanTram);
                right.add(pieChart);
                right.add(boxTopCus);
                b1.removeAll();
                b1.add(editJLabel("Tổng Khách Hàng", 20));
                b1.add(editJLabel(soLuongKhachQuen+soKhachVangLai+"", 30));
                b2.removeAll();
                b2.add(editJLabel("Khách Quen", 20));
                b2.add(editJLabel(soLuongKhachQuen+"",20));
                b3.removeAll();
                b3.add(editJLabel("Khách Vãng Lai", 20));
                b3.add(editJLabel(soKhachVangLai+"",20));
                b4.removeAll();
                b4.add(editJLabel("Doanh Thu Top 10", 20));
                b4.add(editJLabel(format.format(tongDoanhThuTop10),20));
            }
        });
        // Row 2
        JPanel row2 = new JPanel(new GridLayout(1, 5, 10, 0));
        row2.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        row2.add(b1=createBox("Tổng Khách Hàng", "")); // truyền tổng khách hàng 
        row2.add(b2=createBox("Khách Quen", ""));  //truyền tổng khách quen
        row2.add(b3=createBox("Khách Vãng Lai", "")); //truyền tổng khách vãng lai 
        row2.add(b4=createBox("Doanh Thu Từ Top 10", "")); //truyền tổng doanh thu từ top 10 khách hàng
        
        // Row 3
        JPanel row3 = new JPanel();
        row3.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));

        left = new JPanel();
        left.setLayout(new BorderLayout());
        left.setPreferredSize(new Dimension(900, 600));
        left.add(editJLabel("Doanh Thu Top 10 Khách Hàng", 25));
        left.add(taoBieuDoBar(new Object[][] {}));
        
        
        right = new JPanel();
        right.setLayout(new GridLayout(2, 1, 0, 20));
        right.setPreferredSize(new Dimension(350, 600));
        //data mau
        loaiRank = new ArrayList<String>();
        phanTram = new ArrayList<Integer>();
        
        JPanel pieChart = taoBDTronTheoNgay(loaiRank, phanTram);
        
        //Box top 3 khach hang
        boxTopCus = new EditJPanel(1f, Color.white, 30, 30);
        boxTopCus.setLayout(new BoxLayout(boxTopCus, BoxLayout.Y_AXIS));
        
        boxTop23 = Box.createHorizontalBox();
        boxTop23.add(createTopBox("icons/icons8-number-two.png", ""));
        boxTop23.add(Box.createHorizontalStrut(20));
        boxTop23.add(createTopBox("icons/icons8-number-three.png", ""));
        
        boxTopCus.add(createTopBox("icons/icons8-number-one.png", ""));
        boxTopCus.add(boxTop23);
        
        right.add(pieChart);
        right.add(boxTopCus);
        
        row3.add(left);
        row3.add(Box.createHorizontalStrut(10));
        row3.add(right);
        
        
        center.add(row1);
        center.add(row2);
        center.add(row3);
        
	}

	
	public JLabel editJLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.decode("#2C3E50"));
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }
	
	public EditJPanel createBox(String title, String value) {
        EditJPanel box = new EditJPanel(0.9f, Color.decode("#D5F5E3"), 30, 30);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.add(editJLabel(title, 20));
        box.add(editJLabel(value, 30));
        return box;
    }
	
	public JPanel taoBieuDoBar(Object[][] data1) {
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
        	    int spacing = 32;

        	    Object[][] data = data1;

        	    int maxValue = 0;
        	    if(data!=null)
        	    for (Object[] point : data) {
        	        maxValue = Math.max(maxValue, (int) point[1]);
        	    }

        	    int step = 2_000_000;
        	    int roundedMax = ((maxValue + step - 1) / step) * step + step;

        	    int barCount = data.length;
        	    int barWidth = 45;

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

        	        g2.setPaint(new GradientPaint(x, y, Color.decode("#64a3f8"), x, y + barHeight, Color.decode("#bbdcfb")));
        	        g2.fillRect(x, y, barWidth, barHeight);

        	        g2.setColor(Color.decode("#2C3E50"));
        	        g2.drawRect(x, y, barWidth, barHeight);

        	        // Vẽ nhãn bên dưới (xoay)
        	        String label = (String) data[i][0];
        	        FontMetrics fm = g2.getFontMetrics();
        	        int labelX = x + barWidth / 2;
        	        int labelY = height - 10;

        	        AffineTransform original = g2.getTransform();
        	        g2.translate(labelX, labelY);
        	        g2.rotate(-Math.PI / 9);
        	        g2.drawString(label, -fm.stringWidth(label) / 2, 0);
        	        g2.setTransform(original);

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
	
	public JPanel taoBDTronTheoNgay(List<String> labels, ArrayList<Integer> phanTram2) {
	    DefaultPieDataset dataset = new DefaultPieDataset();

	    for (int i = 0; i < labels.size(); i++) {
	        dataset.setValue(labels.get(i), phanTram2.get(i));
	    }

	    JFreeChart chart = ChartFactory.createPieChart(
	            "Tỷ lệ khách hàng ghé thăm", dataset, true, true, false);

	    PiePlot plot = (PiePlot) chart.getPlot();
	    plot.setBackgroundPaint(Color.WHITE);
	    plot.setOutlineVisible(false);
	    plot.setShadowPaint(null);
	    plot.setLabelGenerator(null);
	    chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 14));
	    chart.getLegend().setBorder(0, 0, 0, 0);

	    
	    Color[] colors = {
	        Color.decode("#B9F2FF"),  
	        Color.decode("#FFD700"),  
	        Color.decode("#C0C0C0"),  
	        Color.decode("#B87333"),  
	        Color.decode("#76D7C4"),  
	    };

	    for (int i = 0; i < labels.size(); i++) {
	        plot.setSectionPaint(labels.get(i), colors[i % colors.length]);
	    }

	    return new ChartPanel(chart);
	}
	
	public JPanel createTopBox(String icon, String text) {
	    JPanel box = new JPanel();
	    box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
	    box.setOpaque(false);
	    box.setAlignmentX(CENTER_ALIGNMENT);

	    ImageIcon imageRank = new ImageIcon(icon);
	    JLabel iconLabel = new JLabel(new ImageIcon(imageRank.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	    iconLabel.setAlignmentX(CENTER_ALIGNMENT);

	    JLabel nameLabel = editJLabel(text, 20);
	    nameLabel.setAlignmentX(CENTER_ALIGNMENT);

	    box.add(iconLabel);
	    box.add(Box.createVerticalStrut(5));
	    box.add(nameLabel);

	    return box;
	}

}

