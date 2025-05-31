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
import javax.swing.JOptionPane;
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

public class XuLyDatBanGUI extends JPanel{
	public JLabel nameUser, chucVu, lblBanList, lblMaPhong;
    public static JComboBox<KhuVuc> cbKhuVuc;
    public static JPanel listBan;
    public static TaiKhoan tk;
    
    private static Ban_DAO bd;
    private KhuVuc_DAO kvd;
    
    
    private static ArrayList<Ban> dsb;
    private ArrayList<KhuVuc> dskv;
    
    public XuLyDatBanGUI(TaiKhoan tk) {
    	setLayout(new BorderLayout());
    	ConnectDB.getInstance().connect();
    	Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        this.tk = tk;
        bd = new Ban_DAO();
        kvd = new KhuVuc_DAO();
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

        cbKhuVuc = editJcombo();
        
        cbKhuVuc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KhuVuc kv = (KhuVuc)cbKhuVuc.getSelectedItem();
				String maKhuVuc = kv.getMaKhuVuc();
				
				listBan.removeAll();
				napBanTheoKhuVuc(maKhuVuc);
				listBan.revalidate();
				listBan.repaint();
				
			}
		});
        
        lblMaPhong = editJlabel("Danh Sách Bàn", 30);

        row2.add(Box.createHorizontalStrut(100));
        row2.add(cbKhuVuc);
        row2.add(Box.createHorizontalStrut(150));
        row2.add(lblMaPhong);
        row2.add(Box.createHorizontalStrut(550));

        JPanel lblTable = new JPanel(new GridLayout(1, 2));
        
        lblBanList = editJlabel("Danh Sách Các Bàn", 20);
        

        lblTable.add(lblBanList);
        lblTable.add(new JPanel());

        
        // Danh sách bàn 
        listBan = new JPanel(new GridLayout(0, 3, 10, 10));
        listBan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        

        JScrollPane pane2 = new EditScrollPane(listBan);
        pane2.setPreferredSize(new Dimension(1250, 700));
        pane2.setBorder(null);
        pane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        center.add(Box.createVerticalStrut(10));
        center.add(row1);
        center.add(Box.createVerticalStrut(10));
        center.add(row2);
        center.add(Box.createVerticalStrut(10));
        center.add(lblTable);
        center.add(Box.createVerticalStrut(10));
        center.add(pane2);
        napBanTheoKhuVuc("KV000001");
        napKhuVuc();

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
    public static JPanel ttBan(String tableID, String seats, String status, String image,String tenBan,String chuThich) {
    	EditJPanel form = new EditJPanel(0.6f, Color.white, 40, 40);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        //hiển thị hình ảnh bàn
        EditJPanel formImages = new EditJPanel(0.6f, Color.white, 40, 40);
        ImageIcon images = new ImageIcon(image);
        formImages.add(new JLabel(new ImageIcon(images.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));

        //thông tin bàn
        Box thongTinBan = Box.createVerticalBox();

        JPanel row1 = new JPanel();
        row1.add(new JLabel(chuThich + tableID));

        JPanel row2 = new JPanel();
        row2.add(new JLabel("Số ghế: " + seats));
        EditJPanel row3 = new EditJPanel(0.8f,Color.GRAY,20, 20);
        row3.setBackground(Color.GRAY);

        JPanel row4 = new JPanel();
        
        EditJButton btnDatBan = new EditJButton("Đặt Ngay", 50);
        btnDatBan.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnDatBan.setPreferredSize(new Dimension(150, 40));
        btnDatBan.setBackground(Color.decode("#006400"));
        btnDatBan.setActionCommand(tableID);
        btnDatBan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String maKhuVuc = layMaKhuVucHienTai();
				if(datBanNgay(tenBan))
				if(chuThich.equals("Mã Bàn: "))
				{
					if(bd.setTrangThaiBan(e.getActionCommand().trim(),"Có khách"))
					{
						thongBao();
						listBan.removeAll();
						napBanTheoKhuVuc(maKhuVuc);
						listBan.revalidate();
						listBan.repaint();
					}
					else
					{
						thongBaoThatBai();
					}
				}
		
				
			}
		});
        //btnDatBan.setActionCommand(tableID);
        
        EditJButton btnDatTruoc = new EditJButton("Đặt Trước", 50);
        btnDatTruoc.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnDatTruoc.setPreferredSize(new Dimension(150, 40));
        btnDatTruoc.setBackground(Color.decode("#006400"));
        
        btnDatTruoc.setActionCommand(tableID);
        btnDatTruoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Ban b = timBan(tableID);
				new XuLyFormDatTruocBanGUI(tk,b);
				
			}
		});
        
        row4.add(btnDatBan);
        row4.add(btnDatTruoc);

        thongTinBan.add(row1);
        thongTinBan.add(row2);
        thongTinBan.add(row3);
        thongTinBan.add(Box.createVerticalStrut(10));
        thongTinBan.add(row4);

        form.add(formImages);
        form.add(thongTinBan);
        return form;
    }
    public static String layMaKhuVucHienTai()
    {
    	KhuVuc kv = (KhuVuc)cbKhuVuc.getSelectedItem();
		return kv.getMaKhuVuc().trim();
    }
    public static boolean datBanNgay(String tenBan)
    {
    	return JOptionPane.showConfirmDialog(null,"Bạn có muốn đặt "+tenBan+" không","Cảnh Báo!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
    }
    public static void thongBao()
    {
    	JOptionPane.showMessageDialog(null,"Đặt Bàn Thành Công !");
    }
    public static void thongBaoThatBai()
    {
    	JOptionPane.showMessageDialog(null,"Đặt Bàn Thất Bại, đã có khách !");
    }
    public static Ban timBan(String maBan)
    {
    	return dsb.get(dsb.indexOf(new Ban(maBan)));
    }

    public static void napBanTheoKhuVuc(String maKhuVuc)
    {
    	dsb = bd.timBanTheoMaKhuVuc(maKhuVuc);
    	if(dsb.size()>0)
    	for (Ban ban: dsb) {
            String tableId = ban.getMaBan().trim();
            String tenBan = ban.getTenBan();
            String seats = ban.getSoLuongChoNgoi()+"";
            String status = ban.getTrangThai().trim();
            String imagePath = "images/ban_an.png";
            listBan.add(ttBan(tableId, seats, status, imagePath,tenBan,"Mã Bàn: "));      
        }
    }
    public void napKhuVuc() {
        dskv = kvd.layToanBoKhuVuc();
        for (KhuVuc kv : dskv) {
            cbKhuVuc.addItem(kv);
        }
    }
    
}
