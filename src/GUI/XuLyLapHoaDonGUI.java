package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.KhuVuc_DAO;
import DAO.Phong_DAO;
import EDIT.BackgroundPanel;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.KhuVuc;
import Entity.Phong;
import Entity.TaiKhoan;

public class XuLyLapHoaDonGUI extends JPanel{
	public JLabel nameUser, chucVu, lblBanList, lblMaPhong;
    public JComboBox<String> cacPhong, tinhTrangGMon, choNgoiBan;
    public static JPanel listBan;
    public static TaiKhoan tk;
    

    private static ArrayList<Ban> dsb;
    private static Ban_DAO bd;
    private static Phong_DAO phd;
    
    private static ArrayList<Phong> dsph;
    
    public XuLyLapHoaDonGUI(TaiKhoan tk) {
    	this.setVisible(true);
    	ConnectDB.getInstance().connect();
    	this.tk = tk;
    	bd = new Ban_DAO();
    	phd = new Phong_DAO();
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);

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

        user.add(info);
        user.add(Box.createHorizontalStrut(10));
        user.add(uI);

        row1.add(user, BorderLayout.EAST);

        // Chức năng
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));

        choNgoiBan = editJcombo();
        choNgoiBan.addItem("Số lượng chỗ ngồi của bàn");
        cacPhong = editJcombo();
        cacPhong.addItem("Các mã phòng");
        tinhTrangGMon  = editJcombo();
        tinhTrangGMon.addItem("Các bàn đã có khách");
        tinhTrangGMon.addItem("Các bàn chưa có khách");


        row2.add(Box.createHorizontalStrut(100));
        row2.add(choNgoiBan);
        row2.add(Box.createHorizontalStrut(100));
        row2.add(cacPhong);
        row2.add(Box.createHorizontalStrut(100));
        row2.add(tinhTrangGMon);
        row2.add(Box.createHorizontalStrut(100));

        JPanel lblTable = new JPanel(new GridLayout(1, 2));
        
        lblBanList = editJlabel("Danh Sách Các Bàn Đã Gọi Món", 20);
        

        lblTable.add(lblBanList);
        lblTable.add(new JPanel());

        
        // Danh sách bàn 
        listBan = new JPanel(new GridLayout(0, 3, 10, 10));
        listBan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        
        JScrollPane pane2 = new EditScrollPane(listBan);
        pane2.setPreferredSize(new Dimension(1250, 600));
        pane2.setBorder(null);
        pane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        center.add(Box.createVerticalStrut(10));
        center.add(row1);
        center.add(Box.createVerticalStrut(10));
        center.add(row2);
        center.add(Box.createVerticalStrut(20));
        center.add(lblTable);
        center.add(Box.createVerticalStrut(10));
        center.add(pane2);
        napBanDaGoiMon();
        napPhongDaGoiMon();
        
    }

    public static void napBanDaGoiMon()
    {
    	dsb = bd.timBanDaGoiMon();
    	if(dsb.size()>0)
    	for (Ban ban : dsb) {
        	String roomID = ban.getMaBan();
            String tableID = ban.getTenBan();
            String seats = ban.getSoLuongChoNgoi()+"";
            String status = ban.getTrangThai().trim();
            String imagePath = "images/ban_an.png";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath));
            
        }
    }
    public static void napPhongDaGoiMon()
    {
    	dsph = phd.timPhongDaGoiMon();
    	for (Phong p : dsph) {
        	String roomID = p.getMaPhong();
            String tableID = p.getTenPhong();
            String seats = p.getSoLuongChoNgoi()+"";
            String status = p.getTrangThai().trim();
            String imagePath = "images/phongan.jpg";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath));
                    
        }
    }
    public static Phong timPhong(String maPhong)
    {
    	return dsph.get(dsph.indexOf(new Phong(maPhong)));
    }
    public JComboBox editJcombo() {
        JComboBox<String> combo = new JComboBox();
        combo.setPreferredSize(new Dimension(150, 30));
        combo.setBackground(Color.white);
        return combo;
    }
    
    public JLabel editJlabel(String text, int size) {
    	JLabel nan = new JLabel(text);
    	nan.setFont(new Font("Times New Roman", Font.BOLD, size));
    	return nan;
    }
    public static Ban timBan(String maBan)
    {
    	return dsb.get(dsb.indexOf(new Ban(maBan)));
    }
    public static JPanel ttBan(String roomID, String name, String seats, String status, String image) {
    	EditJPanel form = new EditJPanel(0.6f, Color.white, 40, 40);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        //hiển thị hình ảnh bàn
        EditJPanel formImages = new EditJPanel(0.6f, Color.white, 40, 40);
        ImageIcon images = new ImageIcon(image);
        formImages.add(new JLabel(new ImageIcon(images.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));

        //thông tin bàn
        Box thongTinBan = Box.createVerticalBox();
        
        JPanel row0 = new JPanel();
        row0.add(new JLabel("Mã Phòng/Bàn: " + roomID));

        JPanel row1 = new JPanel();
        row1.add(new JLabel("Tên Phòng/Bàn: " + name));

        JPanel row2 = new JPanel();
        row2.add(new JLabel("Số ghế: " + seats));

        EditJPanel row3 = new EditJPanel(0.8f,Color.YELLOW, 20, 20);
 

        JPanel row4 = new JPanel();
        
        EditJButton btnGoiMon = new EditJButton("Lập Hóa Đơn", 50);
        btnGoiMon.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnGoiMon.setPreferredSize(new Dimension(150, 40));
        btnGoiMon.setBackground(Color.decode("#006400"));
        btnGoiMon.setActionCommand(roomID.trim());
        btnGoiMon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().contains("B"))
				{
					System.out.println(e.getActionCommand());
					new XuLyLapHoaDonTiepTheoGUI(tk,timBan(roomID),null);
				}
				else
				{
					new XuLyLapHoaDonTiepTheoGUI(tk,null,timPhong(roomID));	
				}
			}
		});
        row4.add(btnGoiMon);

        
        thongTinBan.add(row0);
        thongTinBan.add(row1);
        thongTinBan.add(row2);
        thongTinBan.add(row3);
        thongTinBan.add(Box.createVerticalStrut(10));
        thongTinBan.add(row4);

        form.add(formImages);
        form.add(thongTinBan);
        return form;
    }
}
