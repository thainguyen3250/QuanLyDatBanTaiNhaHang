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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.LoaiMonAn_DAO;
import DAO.MonAn_DAO;
import DAO.Phong_DAO;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.ChiTietHoaDon;
import Entity.LoaiMonAn;
import Entity.MonAn;
import Entity.Phong;
import Entity.TaiKhoan;
import FILE.InputOutput;

public class XuLyGoiMonPhongGUI extends JPanel implements ActionListener,MouseListener{
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
    
    private Phong phong;
    private InputOutput io;
    private ArrayList<LoaiMonAn> dslma;
    private LoaiMonAn_DAO lmad;
    private Phong_DAO pd;
    public DecimalFormat format = new DecimalFormat("#,### VND");
    
    
    public XuLyGoiMonPhongGUI(TaiKhoan tk,Phong p) {
    	ConnectDB.getInstance().connect();
        setLayout(new BorderLayout());
        this.tk = tk;
        this.phong = p;
        io = new InputOutput();
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        mad = new MonAn_DAO();
        dscthd = new ArrayList<ChiTietHoaDon>();
        lmad = new LoaiMonAn_DAO();
        pd = new Phong_DAO();
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
			dscthd = (ArrayList<ChiTietHoaDon>) io.readFromFile("tempBan/"+phong.getMaPhong().trim());
		}catch (Exception e2)
		{
			System.out.println("Chưa có file");
		}
        user.add(info);
        user.add(Box.createHorizontalStrut(10));
        user.add(uI);

        row1.add(user, BorderLayout.EAST);

        Box row2 = Box.createHorizontalBox();

        // Gọi món
        Box left = Box.createVerticalBox();

        JPanel tacvu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loaiMon = new JComboBox<String>();
        loaiMon.setPreferredSize(new Dimension(250, 30));
        loaiMon.addItem("Tất cả");
        loaiMon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					String loaimonan = (String) loaiMon.getSelectedItem();
					if(loaimonan.equals("Tất cả"))
					{
						listMonAn.removeAll();
						dayMonLen();
						listMonAn.revalidate();
						listMonAn.repaint();
						return;
					}
					dsma = mad.timMonAnTheoLoai(loaimonan);
					listMonAn.removeAll();
					if (dsma.size()!=0)
					{
						for (MonAn ma : dsma) {
				            String ten = ma.getTenMonAn();
				            String image = ma.getHinhAnh().trim();
				            String gia = ma.getGiaMonAn()+"";
				            String maMonAn = ma.getMaMonAn();
				            listMonAn.add(ttGoiMon(ten, image, gia, maMonAn));
				        }
					}
					listMonAn.revalidate();
					listMonAn.repaint();
					
				}
				
		});
        txtTim = new JTextField(20);
        txtTim.setPreferredSize(new Dimension(200, 30));
        txtTim.setBorder(null);
        txtTim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tenMon = txtTim.getText().trim();
				if(tenMon.length()==0)
				{
					listMonAn.removeAll();
					dayMonLen();
					listMonAn.revalidate();
					listMonAn.repaint();
					return;
				}
				if(tenMon.length()!=0)
				{
					dsma = mad.timMonAnTheoTen(tenMon);
					if (dsma.size()!=0)
					{
						listMonAn.removeAll();
						for (MonAn ma : dsma) {
				            String ten = ma.getTenMonAn();
				            String image = ma.getHinhAnh().trim();
				            String gia = ma.getGiaMonAn()+"";
				            String maMonAn = ma.getMaMonAn();
				            listMonAn.add(ttGoiMon(ten, image, gia, maMonAn));
				        }
						listMonAn.revalidate();
						listMonAn.repaint();
						return;
					}
					else
					{
						listMonAn.removeAll();
						listMonAn.revalidate();
						listMonAn.repaint();
					}
				}
			}
		});
        tacvu.add(loaiMon);
        tacvu.add(Box.createHorizontalStrut(80));
        tacvu.add(new JLabel("Tìm kiếm món ăn: "));
        tacvu.add(txtTim);
        

        // Danh sách món ăn
        listMonAn = new JPanel(new GridLayout(0, 3, 10, 10));
        listMonAn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Tạo mẫu món ăn
        

        JScrollPane pane1 = new EditScrollPane(listMonAn);
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
        lblMaBan = new JLabel("Mã Phòng: "+phong.getMaPhong()+", "+phong.getMaKhuVuc().getTenKhuVuc());
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
        btnLamMoi = new EditJButton("<html><center>F4<br>Làm mới</center></html>", 30);
        btnXacNhan = new EditJButton("<html><center>F5<br>Xác nhận gọi món</center></html>", 30);
        
        btnXoa.setBackground(Color.red);
        btnLamMoi.setBackground(Color.decode("#eab676"));
        btnXacNhan.setBackground(Color.green);

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
        
		// Gán phím F4 
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "btnLamMoi");
		this.getActionMap().put("btnLamMoi", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	btnLamMoi.doClick();
		    }
		});

		// Gán phím F5 
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "btnXacNhan");
		this.getActionMap().put("btnXacNhan", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	btnXacNhan.doClick();
		    }
		});
        
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        tableGoiMon.addMouseListener(this);
        btnXacNhan.addActionListener(this);
        napLoaiMonAn();
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
				luuFile("tempBan/"+phong.getMaPhong());
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
    					format.format(cthd.getDonGia()),format.format(cthd.getTongTien())};
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
    	listMonAn.removeAll();
    	dayMonLen();
    	txtTim.setText("");
    	model.setRowCount(0);
    	dayHetVaoDanhSach();
    	luuFile("tempban/"+phong.getMaPhong());
    }
    public void xoaDong(int curr)
    {
    	model.removeRow(curr);
    	dayHetVaoDanhSach();
    	luuFile("tempban/"+phong.getMaPhong());
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
		if(e.getSource().equals(btnXacNhan))
		{
			if(xacNhanGoiMon())
			{
				xuLyGoiMon();
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
	public boolean xacNhanGoiMon()
    {
    	return JOptionPane.showConfirmDialog(this,"Bạn có muốn gọi món không?","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
    }
	public void datLaiIndex()
	{
		for (int i=0;i<tableGoiMon.getRowCount();i++)
		{
			tableGoiMon.setValueAt(i+1, i, 0);
		}
	}
	public void napLoaiMonAn()
	{
		dslma= lmad.getAllLoaiMonAn();
		for(LoaiMonAn lma : dslma)
		{
			loaiMon.addItem(lma.getTenLoaiMonAn());
		}
	}
	public void xuLyGoiMon()
	{
		if(tableGoiMon.getRowCount()>0)
		{
			if(pd.setTrangThaiPhong(phong.getMaPhong(),"Đã gọi món"))
			{
				JOptionPane.showMessageDialog(this,"Gọi Món Thành Công");
				DashboardGUI.center.removeAll();
	        	XuLyChonBanGUI dbGUI = new XuLyChonBanGUI(tk);
	        	DashboardGUI.center.add(dbGUI, BorderLayout.CENTER);
	        	DashboardGUI.center.revalidate();
	        	DashboardGUI.center.repaint();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Bàn chưa gọi món!");
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