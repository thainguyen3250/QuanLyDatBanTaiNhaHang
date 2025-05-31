package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.toedter.calendar.JDateChooser;

import ConnectDB.ConnectDB;
import DAO.HoaDon_DAO;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import Entity.TaiKhoan;

public class ThongKeDoanhThuGUI extends JPanel implements ActionListener{
	public JLabel nameUser, chucVu;
	public JButton btnNgay, btnThang, btnNam;
	public JDateChooser txtNgayBD, txtNgayKT;
	
	private TaiKhoan tk;
	
	private HoaDon_DAO hdd;
	private ArrayList<String> ngay;
	private ArrayList<Double> doanhThu;
	private Box left;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private EditJPanel b1;
	private EditJPanel b2;
	private EditJPanel b3;
	private ArrayList<String> loaiMon;
	private ArrayList<Integer> phanTram;
	private Box right;
	public DecimalFormat format = new DecimalFormat("#, ### VND");
	
	public ThongKeDoanhThuGUI(TaiKhoan tk) {
		setLayout(new BorderLayout());
		// North
		ConnectDB.getInstance().connect();
		this.tk = tk;
        EditJPanel north = new EditJPanel(0.9f, Color.decode("#76D7C4"), 30, 30);
        north.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(north, BorderLayout.NORTH);
        hdd = new HoaDon_DAO();
        ImageIcon userIcon = new ImageIcon("icons/icons8-user.png");
        JLabel uI = new JLabel(new ImageIcon(userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        Box info = Box.createVerticalBox();
        info.add(nameUser = new JLabel(tk.getMaNhanVien().getTenNhanVien()));
        info.add(chucVu = new JLabel(tk.getMaNhanVien().getMaLoaiNhanVien().getTenLoai()));

        north.add(info);
        north.add(uI);
        north.add(Box.createHorizontalStrut(20));
        
        //center 
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        
        //row1
        JPanel row1 = new JPanel();
        row1.setPreferredSize(new Dimension(1250, 100));
        
        btnNgay = editJButton("Ngày");
       
        btnThang = editJButton("Tháng");
        
        btnNam = editJButton("Năm");
        
        txtNgayBD = editJDateChooser();
        txtNgayKT = editJDateChooser();
        
        row1.add(editJLabel("Thống kê theo ", 20));
        row1.add(Box.createHorizontalStrut(5));
        row1.add(btnNgay);
        row1.add(Box.createHorizontalStrut(5));
        row1.add(btnThang);
        row1.add(Box.createHorizontalStrut(5));
        row1.add(btnNam);
        row1.add(Box.createHorizontalStrut(5));
        row1.add(editJLabel("Từ ngày ", 20));
        row1.add(txtNgayBD);
        row1.add(Box.createHorizontalStrut(5));
        row1.add(editJLabel("Đến ngày ", 20));
        row1.add(txtNgayKT);
        
        
        //row2
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row2.add(b1 = createBox("Tổng Doanh Thu", ""));	// truyền tổng doanh thu value là biến thứ 2
        row2.add(Box.createHorizontalStrut(50));
        row2.add(b2 = createBox("Trung Bình Mỗi Hóa Đơn", "")); 	//truyền trung bình hóa đơn
        row2.add(Box.createHorizontalStrut(50));
        row2.add(b3 = createBox("Tổng Số Lượng Hóa Đơn", "")); 	//truyền tổng số lượng hóa đơn
        
        //row3 
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        row3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        left = Box.createHorizontalBox();// Biểu đồ đường
        left.setPreferredSize(new Dimension(800, 700));
        //data Mau
        ngay = new ArrayList<String>();
        doanhThu = new ArrayList<Double>();

        JPanel chartPanel = taoBDDuongTheoNgay(ngay, doanhThu);
       
        left.add(chartPanel);
        
        right = Box.createHorizontalBox(); //Biểu đồ tròn
        right.setPreferredSize(new Dimension(450, 700));
        
        // data mau 
        loaiMon = new ArrayList<String>();
        phanTram = new ArrayList<Integer>();

        JPanel piePanel = taoBDTronTheoNgay(loaiMon, phanTram);
        right.add(piePanel);
        
        row3.add(left);
        row3.add(Box.createHorizontalStrut(10));
        row3.add(right);

        
        center.add(row1);
        center.add(row2);
        center.add(row3);
        btnNgay.addActionListener(this);
        btnThang.addActionListener(this);
        btnNam.addActionListener(this);

	}
	public void chinhSuaBox(double tongDoanhThu,int tongHoaDon)
	{
		b1.removeAll();
		b2.removeAll();
		b3.removeAll();
		double trungBinh = tongHoaDon==0? 0:tongDoanhThu/tongHoaDon;
		b1.add(editJLabel("Tổng Doanh Thu", 20));
		b1.add(editJLabel(format.format(tongDoanhThu), 30));
		
		b2.add(editJLabel("Trung Bình Mỗi Hóa Đơn", 20));
		b2.add(editJLabel(format.format(trungBinh), 30));
		
		b3.add(editJLabel("Tổng Số Lượng Hóa Đơn", 20));
		b3.add(editJLabel(tongHoaDon+"", 30));
	}
	
	public JLabel editJLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.decode("#2C3E50"));
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }
	
	public JButton editJButton(String text) {
		EditJButton button = new EditJButton(text, 20);
		button.setPreferredSize(new Dimension(100, 30));
		return button;
	}
	
	public JDateChooser editJDateChooser() {
		JDateChooser boxDate = new JDateChooser();
		boxDate.setDateFormatString("dd/MM/yyyy");
		boxDate.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		boxDate.setOpaque(false);
		boxDate.setPreferredSize(new Dimension(200, 30));
		return boxDate;
	}
	
	public EditJPanel createBox(String title, String value) {
        EditJPanel box = new EditJPanel(1f, Color.decode("#D5F5E3"), 30, 30);
        box.setPreferredSize(new Dimension(350, 150));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.add(editJLabel(title, 20));
        box.add(editJLabel(value, 30));
        return box;
    }
	
	public JPanel taoBDDuongTheoNgay(List<String> labels, List<Double> values) {
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    for (int i = 0; i < Math.min(labels.size(), values.size()); i++) {
	        dataset.addValue(values.get(i), "Doanh thu", labels.get(i));
	    }

	    JFreeChart chart = ChartFactory.createLineChart(
	            "Doanh thu theo ngày",
	            "Ngày",
	            "Doanh thu (VNĐ)",
	            dataset,
	            PlotOrientation.VERTICAL,
	            false, true, false
	    );

	    
	    chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));
	    CategoryPlot plot = chart.getCategoryPlot();
	    plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 14));
	    plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 14));
	    plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
	    plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

	    plot.setBackgroundPaint(Color.WHITE);
	    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

	    LineAndShapeRenderer renderer = new LineAndShapeRenderer();
	    renderer.setSeriesPaint(0, new Color(0, 200, 83)); 
	    renderer.setSeriesStroke(0, new BasicStroke(2.5f));
	    renderer.setSeriesShapesVisible(0, true);
	    plot.setRenderer(renderer);

	    return new ChartPanel(chart);
	}
	
	public JPanel taoBDTronTheoNgay(List<String> labels, List<Integer> values) {
	    DefaultPieDataset dataset = new DefaultPieDataset();

	    for (int i = 0; i < labels.size(); i++) {
	        dataset.setValue(labels.get(i), values.get(i));
	    }

	    JFreeChart chart = ChartFactory.createPieChart(
	            "Tỷ lệ món ăn bán ra", dataset, true, true, false);

	    PiePlot plot = (PiePlot) chart.getPlot();
	    plot.setBackgroundPaint(Color.WHITE);
	    plot.setOutlineVisible(false);
	    plot.setShadowPaint(null);
	    plot.setLabelGenerator(null);
	    chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 14));
	    chart.getLegend().setBorder(0, 0, 0, 0);

	    
	    Color[] colors = {
	        new Color(76, 175, 80),   
	        new Color(33, 150, 243),  
	        new Color(244, 67, 54),   
	        new Color(255, 193, 7),   
	        new Color(156, 39, 176),  
	        new Color(121, 85, 72),   
	        new Color(0, 188, 212)   
	    };

	    for (int i = 0; i < labels.size(); i++) {
	        plot.setSectionPaint(labels.get(i), colors[i % colors.length]);
	    }

	    return new ChartPanel(chart);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnNgay))
		{
			Date ngayBD = txtNgayBD.getDate();
			Date ngayKetThuc = txtNgayKT.getDate();
			ngay.removeAll(ngay);
			doanhThu.removeAll(doanhThu);
			loaiMon.removeAll(loaiMon);
        	phanTram.removeAll(phanTram);
			
			Object[][] danhsachhd = hdd.layDoanhThuTheoNgay(ngayBD, ngayKetThuc);
			Object[][] danhsachloaimon = hdd.layTop5LoaiMonAnTheoThoiGian(ngayBD, ngayKetThuc);
			chinhSuaBox(hdd.tongDoanhThu,hdd.demSoLuongHoaDonTheoKhoang(ngayBD,ngayKetThuc));
			for(Object[] dong: danhsachhd)
            {
            	ngay.add(sdf.format(dong[0]));
            	doanhThu.add((double) dong[1]);
            	left.removeAll();
            }
        	JPanel chartPanel = taoBDDuongTheoNgay(ngay, doanhThu);
            left.add(chartPanel);
			for(Object[] dong: danhsachloaimon)
            {
            	loaiMon.add((String) dong[0]);
            	phanTram.add((int) dong[1]);
            	right.removeAll();
            }
        	JPanel piePanel = taoBDTronTheoNgay(loaiMon, phanTram);
            right.add(piePanel);
		}
		if(e.getSource().equals(btnThang))
		{
			Date ngayBD = txtNgayBD.getDate();
			Date ngayKetThuc = txtNgayKT.getDate();
			ngay.removeAll(ngay);
			doanhThu.removeAll(doanhThu);
			loaiMon.removeAll(loaiMon);
        	phanTram.removeAll(phanTram);
			
			Object[][] danhsachhd = hdd.layDoanhThuTheoThang(ngayBD, ngayKetThuc);
			Object[][] danhsachloaimon = hdd.layTop5LoaiMonAnTheoThoiGian(ngayBD, ngayKetThuc);
			chinhSuaBox(hdd.tongDoanhThu,hdd.demSoLuongHoaDonTheoKhoang(ngayBD,ngayKetThuc));
			for(Object[] dong: danhsachhd)
            {
            	ngay.add((String) dong[0]);
            	doanhThu.add((double) dong[1]);
            	left.removeAll();
            }
        	JPanel chartPanel = taoBDDuongTheoNgay(ngay, doanhThu);
            left.add(chartPanel);
			for(Object[] dong: danhsachloaimon)
            {
            	loaiMon.add((String) dong[0]);
            	phanTram.add((int) dong[1]);
            	right.removeAll();
            }
        	JPanel piePanel = taoBDTronTheoNgay(loaiMon, phanTram);
            right.add(piePanel);
		}
		if(e.getSource().equals(btnNam))
		{
			Date ngayBD = txtNgayBD.getDate();
			Date ngayKetThuc = txtNgayKT.getDate();
			ngay.removeAll(ngay);
			doanhThu.removeAll(doanhThu);
			loaiMon.removeAll(loaiMon);
        	phanTram.removeAll(phanTram);
			
			Object[][] danhsachhd = hdd.layDoanhThuTheoNam(ngayBD, ngayKetThuc);
			Object[][] danhsachloaimon = hdd.layTop5LoaiMonAnTheoThoiGian(ngayBD, ngayKetThuc);
			chinhSuaBox(hdd.tongDoanhThu,hdd.demSoLuongHoaDonTheoKhoang(ngayBD,ngayKetThuc));
			for(Object[] dong: danhsachhd)
            {
            	ngay.add((String) dong[0]);
            	doanhThu.add((double) dong[1]);
            	left.removeAll();
            }
        	JPanel chartPanel = taoBDDuongTheoNgay(ngay, doanhThu);
            left.add(chartPanel);
			for(Object[] dong: danhsachloaimon)
            {
            	loaiMon.add((String) dong[0]);
            	phanTram.add((int) dong[1]);
            	right.removeAll();
            }
        	JPanel piePanel = taoBDTronTheoNgay(loaiMon, phanTram);
            right.add(piePanel);
		}
		
	}
}
