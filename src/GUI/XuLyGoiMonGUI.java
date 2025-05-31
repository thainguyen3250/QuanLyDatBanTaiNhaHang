package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.MonAn_DAO;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.ChiTietHoaDon;
import Entity.MonAn;
import Entity.TaiKhoan;
import FILE.InputOutput;

public class XuLyGoiMonGUI extends JPanel implements ActionListener,MouseListener{
    public JLabel nameUser, chucVu, lblMaBan;
    public JComboBox<String> loaiMon;
    public JPanel listMonAn;
    public JTable tableGoiMon;
    public JTextField txtTim;
    public DefaultTableModel model;
    public JButton btnXoa, btnXacNhan, btnLamMoi;
    private TaiKhoan tk;
    
    private int row_count=1;
    private ArrayList<MonAn> dsma;
    private MonAn_DAO mad;
    
    private ArrayList<ChiTietHoaDon> dscthd;
    
    private boolean xoa_state = false;
    
    private Ban ban;
    private InputOutput io;
    
    
    public XuLyGoiMonGUI(TaiKhoan tk,Ban b) {
    	ConnectDB.getInstance().connect();
        setLayout(new BorderLayout());
        this.tk = tk;
        this.ban = b;
        io = new InputOutput();
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        mad = new MonAn_DAO();
        dscthd = new ArrayList<ChiTietHoaDon>();
        // Thông tin nhân viên
        EditJPanel row1 = new EditJPanel(0.8f, Color.white, 50, 50);
        row1.setLayout(new BorderLayout());

        EditJPanel user = new EditJPanel(0.8f, Color.white, 40, 40);
        user.setLayout(new BoxLayout(user, BoxLayout.X_AXIS));
        user.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 50));
        user.setBackground(Color.white);

        ImageIcon userIcon = new ImageIcon("icons/icons8-user.png");
        JLabel uI = new JLabel(new ImageIcon(userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        Box info = Box.createVerticalBox();
        info.add(nameUser = new JLabel(tk.getMaNhanVien().getTenNhanVien()));
        info.add(chucVu = new JLabel(tk.getMaNhanVien().getMaLoaiNhanVien().getTenLoai()));

        try
		{
			dscthd = (ArrayList<ChiTietHoaDon>) io.readFromFile("tempBan/"+ban.getMaBan().trim());
		}catch (Exception e2)
		{
			System.out.println("Chưa có file");
		}
        user.add(info);
        user.add(uI);

        row1.add(user, BorderLayout.EAST);

        Box row2 = Box.createHorizontalBox();

        // Gọi món
        Box left = Box.createVerticalBox();

        JPanel tacvu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loaiMon = new JComboBox<String>();
        loaiMon.setPreferredSize(new Dimension(250, 30));
        loaiMon.addItem("Loại món ăn");
        txtTim = new JTextField(20);
        txtTim.setPreferredSize(new Dimension(200, 30));
        txtTim.setBorder(null);

        tacvu.add(loaiMon);
        tacvu.add(Box.createHorizontalStrut(80));
        tacvu.add(new JLabel("Tìm kiếm món ăn: "));
        tacvu.add(txtTim);
        

        // Danh sách món ăn
        listMonAn = new JPanel(new GridLayout(0, 3, 10, 10));
        listMonAn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Tạo mẫu món ăn
        

        JScrollPane pane1 = new JScrollPane(listMonAn);
        pane1.setPreferredSize(new Dimension(800, 600));
        pane1.setBorder(null);
        pane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        left.add(tacvu);
        left.add(Box.createVerticalStrut(10));
        left.add(pane1);

        // Table món ăn
        Box right = Box.createVerticalBox();

        JPanel boxChuaMaBan = new JPanel();
        lblMaBan.setFont(new Font("Times New Roman", Font.BOLD, 24));
        boxChuaMaBan.add(lblMaBan);

        String[] colNames = {"STT","Mã Món Ăn","Tên Món Ăn", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        model = new DefaultTableModel(colNames, 0)
        {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};	
        tableGoiMon = new JTable(model);
        JScrollPane pane = new EditScrollPane(tableGoiMon);
        pane.setPreferredSize(new Dimension(500,600));
        tableGoiMon.getTableHeader().setBackground(Color.white);
        tableGoiMon.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
        tableGoiMon.setRowHeight(25);
        tableGoiMon.setAutoCreateRowSorter(true);
        tableGoiMon.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        right.add(boxChuaMaBan);
        right.add(Box.createVerticalStrut(10));
        right.add(pane);

        row2.add(left);
        row2.add(Box.createHorizontalStrut(10));
        row2.add(right);
        
        //Các chức năng
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnXoa = new EditJButton("Xóa Món", 30);
        btnLamMoi = new EditJButton("Làm Mới", 30);
        btnXacNhan = new EditJButton("Xác Nhận", 30);

        row3.add(btnXoa);
        row3.add(Box.createHorizontalStrut(50));
        row3.add(btnLamMoi);
        row3.add(Box.createHorizontalStrut(50));
        row3.add(btnXacNhan);


        center.add(Box.createVerticalStrut(10));
        center.add(row1);
        center.add(Box.createVerticalStrut(10));
        center.add(row2);
        center.add(Box.createVerticalStrut(20));
        center.add(row3);
        center.add(Box.createVerticalStrut(10));
        btnXoa.setEnabled(xoa_state);
        dayMonLen();
        napLenTable();
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tableGoiMon.addMouseListener(this);
    }
    
    public JPanel ttGoiMon(String tenMon, String image, String gia,String maMonAn) {
        EditJPanel form = new EditJPanel(0.6f, Color.white, 30, 30);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // Hình ảnh món ăn
        EditJPanel formImages = new EditJPanel(0.6f, Color.white, 40, 40);
        ImageIcon images = new ImageIcon(image);
        formImages.add(new JLabel(new ImageIcon(images.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));

        // Thông tin món ăn
        Box ttMonAn = Box.createVerticalBox();

        JPanel row0 = new JPanel();
        row0.add(new JLabel("Tên món: " + tenMon));

        JPanel row1 = new JPanel();
        row1.add(new JLabel("Giá: " + gia));


        JPanel row2 = new JPanel();
        row2.add(new JLabel("Số lượng: "));
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1); 
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(60, 30));
        row2.add(spinner);

        // Nút "Thêm"
        JPanel row4 = new JPanel();
        EditJButton btnGoiMon = new EditJButton("Thêm", 30);
        btnGoiMon.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnGoiMon.setPreferredSize(new Dimension(150, 40));
        btnGoiMon.setBackground(Color.decode("#006400"));
        btnGoiMon.setActionCommand(maMonAn);
        btnGoiMon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int soLuong = (int) spinner.getValue();
				double tongTien = soLuong*Double.parseDouble(gia);
				String[] rows = {row_count+"",maMonAn,tenMon,soLuong+"",gia+"",tongTien+""};
				dayLenModel(rows,maMonAn.trim());
				spinner.setValue(1);
				luuFile("tempBan/"+ban.getMaBan());
			}
		});
        row4.add(btnGoiMon);


        ttMonAn.add(row0);
        ttMonAn.add(row1);
        ttMonAn.add(row2);
        ttMonAn.add(Box.createVerticalStrut(10));
        ttMonAn.add(row4);

        form.add(formImages);
        form.add(ttMonAn);
        return form;
    }
    public void napLenTable()
    {
    	if(dscthd.size()>0)
    	{
    		for(ChiTietHoaDon cthd : dscthd)
    		{
    			String[] rows = {row_count+"",cthd.getMaMonAn().getMaMonAn(),cthd.getMaMonAn().getTenMonAn(),cthd.getSoLuong()+"",
    					cthd.getDonGia()+"",cthd.getTongTien()+""};
    			model.addRow(rows);
    			row_count +=1;
    		}
    	}
    }
    public void luuFile(String path)
    {
    	try {
			io.writeToFile(dscthd, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void dayHetVaoDanhSach()
    {
    	dscthd.removeAll(dscthd);
    	for (int i=0;i<tableGoiMon.getRowCount();i++)
		{
    		String maMonAn = tableGoiMon.getValueAt(i, 1).toString();
    		String tenMonAn = tableGoiMon.getValueAt(i, 2).toString();
    		int soLuong = Integer.parseInt(tableGoiMon.getValueAt(i, 3).toString());
    		double donGia =	Double.parseDouble(tableGoiMon.getValueAt(i, 4).toString());
    		dscthd.add(new ChiTietHoaDon(new MonAn(maMonAn,tenMonAn), soLuong, donGia));
		}
    }
    public void dayLenModel(String[] rows,String ma)
    {
    	for (int i=0;i<tableGoiMon.getRowCount();i++)
		{
			if(tableGoiMon.getValueAt(i, 1).toString().equals(ma))
			{
				int old_val = Integer.parseInt(tableGoiMon.getValueAt(i, 3).toString());
				int new_val = Integer.parseInt(rows[3])+old_val;
				double new_total = new_val * Double.parseDouble(tableGoiMon.getValueAt(i, 4).toString());
				tableGoiMon.setValueAt(new_val+"",i, 3);
				tableGoiMon.setValueAt(new_total+"",i, 5);
				return;
			}
		}
    	row_count +=1;
    	model.addRow(rows);
    	dayHetVaoDanhSach();
    }
    public void dayMonLen()
    {
    	dsma = mad.getAllMonAn();
    	for (MonAn ma : dsma) {
            String tenMon = ma.getTenMonAn();
            String image = ma.getHinhAnh().trim();
            String gia = ma.getGiaMonAn()+"";
            String maMonAn = ma.getMaMonAn();
            listMonAn.add(ttGoiMon(tenMon, image, gia, maMonAn));
        }
    }
    public void lamMoi()
    {
    	model.setRowCount(0);
    	dayHetVaoDanhSach();
    	luuFile("tempban/"+ban.getMaBan());
    }
    public void xoaDong(int curr)
    {
    	model.removeRow(curr);
    	dayHetVaoDanhSach();
    	luuFile("tempban/"+ban.getMaBan());
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnLamMoi))
		{
			if(xacNhanLamMoi())
			{
				lamMoi();
				row_count=1;
			}

		}
		if(e.getSource().equals(btnXoa))
		{
			if(xacNhanXoa())
			{
				int curr = tableGoiMon.getSelectedRow();
				xoaDong(curr);
				datLaiIndex();
				row_count-=1;
				
			}	
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==1)
		{
			btnXoa.setEnabled(true);
		}
		if(e.getClickCount()==2)
		{
			btnXoa.setEnabled(false);
			tableGoiMon.clearSelection();
		}
		
	}
	public boolean xacNhanXoa()
    {
    	return JOptionPane.showConfirmDialog(this,"Bạn có muốn xóa món này không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
    }
	public boolean xacNhanLamMoi()
    {
    	return JOptionPane.showConfirmDialog(this,"Bạn có muốn làm mới không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
    }
	public void datLaiIndex()
	{
		for (int i=0;i<tableGoiMon.getRowCount();i++)
		{
			tableGoiMon.setValueAt(i+1, i, 0);
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