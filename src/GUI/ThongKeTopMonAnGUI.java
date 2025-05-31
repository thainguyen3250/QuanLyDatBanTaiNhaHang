package GUI;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import ConnectDB.ConnectDB;
import DAO.MonAn_DAO;
import EDIT.BarChartPanel;
import EDIT.CustomScrollBarUI;
import EDIT.EditJPanel;
import Entity.TaiKhoan;
import java.math.BigDecimal;
public class ThongKeTopMonAnGUI extends JPanel {
    public JLabel nameUser, chucVu;
    public JMonthChooser txtThang;
    public JYearChooser txtNam;
    public JTable table;
    public TaiKhoan tk;

    public MonAn_DAO mad;
	private ArrayList<String> tenMon;
	private ArrayList<Integer> soLuong;
	private BarChartPanel chart;
	private EditJPanel b1;
	private EditJPanel b2;
	private DefaultTableModel model;
	NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	public DecimalFormat format = new DecimalFormat("#, ### VND");
	
    public ThongKeTopMonAnGUI(TaiKhoan tk) {
        setLayout(new BorderLayout());
        ConnectDB.getInstance().connect();
		this.tk = tk;
		mad = new MonAn_DAO();
        // North
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

        // Center
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

        // Row 2
        JPanel row2 = new JPanel(new GridLayout(1, 4, 50, 0));
        row2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        row2.add(b1 =createBox("Tổng Món Bán Ra",""));
        row2.add(b2 =createBox("Tổng Doanh Thu","")); 
        // Row 3
        JPanel row3 = new JPanel(new GridLayout(1, 2, 20, 0));
        row3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Líst data mẫu
        tenMon = new ArrayList<String>();
        soLuong = new ArrayList<Integer>();
        
        txtThang.addPropertyChangeListener("month", (PropertyChangeListener) new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                int thang = txtThang.getMonth() + 1; 
                tenMon.removeAll(tenMon);
                soLuong.removeAll(soLuong);
                Object[][] danhsachma = mad.layThongTinDoanhThuTheoThangNam(thang, txtNam.getYear());
                model.setRowCount(0);
                for(Object[] dong: danhsachma)
                {
                	tenMon.add((String) dong[0]);
                	soLuong.add((int) dong[2]);
                	model.addRow(dong);
                }
                chart.setData(tenMon, soLuong);
                b1.removeAll();
                b1.add(editJLabel("Tổng Món Bán Ra", 20));
                b1.add(editJLabel(mad.tongMonBan+"", 30));
                b2.removeAll();
                b2.add(editJLabel("Tổng Doanh Thu", 20));
                b2.add(editJLabel(format.format(mad.tongDoanhThuTheoThang), 30));
            }
        });
        
        chart = new BarChartPanel(tenMon, soLuong);
        chart.setPreferredSize(new Dimension(600, 400));


        String[] col = {"Tên Món", "Danh Mục", "Số Lượng", "Doanh Thu"};
        model = new DefaultTableModel(col, 0);
        table = new JTable(model);
        table.getTableHeader().setBackground(Color.white);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
        table.setBackground(Color.white);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        row3.add(chart);
        row3.add(scrollPane);


        center.add(Box.createVerticalStrut(10));
        center.add(row1);
        center.add(Box.createVerticalStrut(10));
        center.add(row2);
        center.add(Box.createVerticalStrut(10));
        center.add(row3);

        
    }

    public JLabel editJLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.decode("#2C3E50"));
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }

    private EditJPanel createBox(String title, String value) {
        EditJPanel box = new EditJPanel(0.9f, Color.decode("#D5F5E3"), 30, 30);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.add(editJLabel(title, 20));
        box.add(editJLabel(value, 30));
        return box;
    }

    
}
