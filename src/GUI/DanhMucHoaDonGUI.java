package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import ConnectDB.ConnectDB;
import DAO.ChiTietHoaDon_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.TaiKhoan;
import FILE.InputOutput;

public class DanhMucHoaDonGUI extends JPanel implements MouseListener{
	public JLabel nameUser, chucVu;
	public JTextField txtTimTheoMaHD;
	public JDateChooser txtNgay;
	public JTable table;
	public DefaultTableModel model;
	public EditJButton btnChiTietHD, btnInHoaDon, btnReset;
	private TaiKhoan tk;
	private KhachHang_DAO khd;
	private HoaDon_DAO hdd;
	private JButton btnTim;
	private InputOutput io;
	private ChiTietHoaDon_DAO cthdd;
	public DanhMucHoaDonGUI(TaiKhoan tk)  throws SQLException {
        setLayout(new BorderLayout());
        
        ConnectDB.getInstance().connect();
    	khd = new KhachHang_DAO();
    	this.tk = tk;
        hdd = new HoaDon_DAO();
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        cthdd = new ChiTietHoaDon_DAO();
        // Thông tin nhân viên
        EditJPanel row1 = new EditJPanel(0.8f, Color.white, 50, 50);
        row1.setLayout(new BorderLayout());
        io = new InputOutput();
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
        row1.add(user, BorderLayout.EAST);
        
        
        JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title.add(editJlabel("Quản Lý Hóa Đơn", 40));
        
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(editJlabel("Tìm kiếm theo mã hóa đơn", 20));
        row2.add(Box.createHorizontalStrut(5));
        row2.add(txtTimTheoMaHD = new JTextField());
        
        txtTimTheoMaHD.setPreferredSize(new Dimension(250, 30));
        txtTimTheoMaHD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtNgay.setDate(null);
				String maHD = txtTimTheoMaHD.getText();
				if(maHD.isEmpty())
				{
					if(table.getRowCount()>0)
					model.setRowCount(0);
					dayLenTable();
					return;
				}
				else
				{
					if(table.getRowCount()>0)
					model.setRowCount(0);
					String[][] obj = hdd.timHoaDonTheoMa(maHD);
					for (String[] row: obj)
					{
						model.addRow(row);
					}
					return;
				}
				
			}
		});
        
        row2.add(Box.createHorizontalStrut(20));
        row2.add(editJlabel("Tìm kiếm theo ngày", 20));
        row2.add(Box.createHorizontalStrut(10));
        row2.add(txtNgay = new JDateChooser());
        row2.add(btnTim =new JButton(" Tìm "));
        btnTim.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtNgay.getDate() == null)
				{
					thongBaoLoi();
					return;
				}
				Date ngay = txtNgay.getDate();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String ngaynhap = formatter.format(ngay);
				if(table.getRowCount()>0)
				model.setRowCount(0);
				String[][] obj = hdd.timHoaDonTheoNgay(ngaynhap);
				for (String[] row: obj)
				{
					model.addRow(row);
				}
				return;
				
			}
		});
        txtNgay.setPreferredSize(new Dimension(250, 30));
        txtNgay.setDateFormatString("dd/MM/yyyy");
        txtNgay.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        
        Box row3 = Box.createHorizontalBox();
        String[] colTable = {"Mã HĐ", "Tên Nhân Viên", "Khách hàng", "Ngày tạo", "Số tiền"};
        model = new DefaultTableModel(colTable, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // không cho phép chỉnh sửa ô nào cả
            }
        };

        
		table = new JTable(model);
        JScrollPane pane = new EditScrollPane(table);
        pane.setPreferredSize(new Dimension(1250, 500));
        table.getTableHeader().setBackground(Color.white);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		row3.add(pane);
       
		
		JPanel row4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		row4.setPreferredSize(new Dimension(1250, 80));
		btnChiTietHD = new EditJButton("Xem chi tiết", 30);
		btnInHoaDon = new EditJButton("In hóa đơn", 30);
		btnReset = new EditJButton("Làm mới", 30);
		
		
		
		
		btnChiTietHD.setBackground(Color.decode("#76b5c5"));
		btnInHoaDon.setBackground(Color.green);
		btnReset.setBackground(Color.decode("#eab676"));
		
		btnInHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int curr = table.getSelectedRow();
				if(curr!=-1)
				{
					xuatHoaDonThanhPDF(curr);
				}else
				{
					thongBaoLoiHoaDon();
				}

			}
		});
		
		btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtTimTheoMaHD.setText("");
				model.setRowCount(0);
				dayLenTable();	
			}
		});
		
		row4.add(btnChiTietHD);
		row4.add(Box.createHorizontalStrut(40));
		row4.add(Box.createHorizontalStrut(40));
		row4.add(btnInHoaDon);
		
        center.add(row1);
        center.add(Box.createVerticalStrut(20));
        center.add(title);
        center.add(Box.createVerticalStrut(20));
        center.add(row2);
        center.add(Box.createVerticalStrut(20));
        center.add(row3);
        center.add(Box.createVerticalStrut(20));
        center.add(row4);
        dayLenTable();
        table.addMouseListener(this);
	}
	public void thongBaoLoi()
	{
		JOptionPane.showMessageDialog(this,"Chưa Chọn Ngày!");
	}
	public void thongBaoLoiHoaDon()
	{
		JOptionPane.showMessageDialog(this,"Chưa Chọn Dòng!");
	}
	public void dayLenTable()
	{
		String[][] obj = hdd.getThongTinHoaDon();
		for (String[] row: obj)
		{
			model.addRow(row);
		}
	}
	public JLabel editJlabel(String text, int size) {
    	JLabel nan = new JLabel(text);
    	nan.setFont(new Font("Times New Roman", Font.BOLD, size));
    	return nan;
    }
	public boolean xuatHoaDonThanhPDF(int curr)
	{
		
		try
		{
			
			String maHoaDon = (String) table.getValueAt(curr, 0);
			if(JOptionPane.showConfirmDialog(this,"Bạn Muốn Xuất Hóa Đơn Này Không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
				HoaDon hd = hdd.timHoaDonTheoMa1(maHoaDon);
				ArrayList<ChiTietHoaDon> dscthd = cthdd.timCTHD(maHoaDon);
				double tongTienTruocThue = hd.tinhTongHoaDonTruocThue(dscthd);
				double tongTienSauThue = hd.tinhTongHoaDonSauThue();
				String dir ="tempHoaDon/hoaDonXuat_"+maHoaDon+".pdf";
				io.xuatHoaDon(hd, dscthd, dir, tongTienTruocThue, tongTienSauThue);
				JOptionPane.showMessageDialog(this,"Xuất Thành Công! ");
			}	
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(this,"Lỗi!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2)
		{
			table.clearSelection();
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
