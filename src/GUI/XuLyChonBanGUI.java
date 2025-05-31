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
import Entity.KhachHang;
import Entity.KhuVuc;
import Entity.Phong;
import Entity.TaiKhoan;
import FILE.InputOutput;

public class XuLyChonBanGUI extends JPanel{
	public JLabel nameUser, chucVu, lblBanList, lblMaPhong;
    public JComboBox<String>  tinhTrangGMon, choNgoiBan;
    public JPanel listBan;
    public TaiKhoan tk;
    

    private ArrayList<Ban> dsb;
    private ArrayList<Phong> dsph;
    private ArrayList<KhuVuc> dskv;
    private Ban_DAO bd;
    private Phong_DAO pd;
    private InputOutput io;
    
    public XuLyChonBanGUI(TaiKhoan tk) {
    	this.setVisible(true);
    	ConnectDB.getInstance().connect();
    	this.tk = tk;
    	bd = new Ban_DAO();
    	pd = new Phong_DAO();
        Box center = Box.createVerticalBox();
        add(center, BorderLayout.CENTER);
        io = new InputOutput();
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
        user.add(uI);

        row1.add(user, BorderLayout.EAST);

        // Chức năng
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));

        choNgoiBan = editJcombo();
        choNgoiBan.addItem("Tất cả");
        choNgoiBan.addItem("Từ 1 đến 4 chỗ");
        choNgoiBan.addItem("Từ 5 đến 8 chỗ");
        choNgoiBan.addItem("Từ 8 đến 16 chỗ");
        choNgoiBan.addItem("Từ 17 chỗ trở lên");
        
        choNgoiBan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String soLuongChoNgoi = (String) choNgoiBan.getSelectedItem();
				if(soLuongChoNgoi.equals("Tất cả"))
				{	
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã có khách"))
					{
						listBan.removeAll();
						napBanCoKhach();
						napPhongCoKhach();
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã gọi món"))
					{
						listBan.removeAll();
						napBanDaGoiMon();
						napPhongDaGoiMon();
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					else
					{
						listBan.removeAll();
						napBanDaDatTruoc();
						napPhongDaDatTruoc();
						listBan.revalidate();
						listBan.repaint();
					}
					
				}
				if (soLuongChoNgoi.equals("Từ 1 đến 4 chỗ"))
				{
					dsb = bd.timBanTheoChoNgoi(1,4);
					dsph = pd.timPhongTheoChoNgoi(1,4);
					listBan.removeAll();
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã có khách"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Có khách"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Có khách"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã gọi món"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã gọi món"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã gọi món"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					else
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã đặt trước"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã đặt trước"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
				}
				if (soLuongChoNgoi.equals("Từ 5 đến 8 chỗ"))
				{
					dsb = bd.timBanTheoChoNgoi(5,8);
					dsph = pd.timPhongTheoChoNgoi(5,8);
					listBan.removeAll();
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã có khách"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Có khách"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Có khách"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã gọi món"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã gọi món"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã gọi món"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					else
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã đặt trước"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã đặt trước"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
				}
				if (soLuongChoNgoi.equals("Từ 8 đến 16 chỗ"))
				{
					dsb = bd.timBanTheoChoNgoi(8,16);
					dsph = pd.timPhongTheoChoNgoi(8,16);
					listBan.removeAll();
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã có khách"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Có khách"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Có khách"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã gọi món"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã gọi món"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã gọi món"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					else
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã đặt trước"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã đặt trước"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
				}
				if (soLuongChoNgoi.equals("Từ 17 chỗ trở lên"))
				{
					dsb = bd.timBanTheoChoNgoi(17,99);
					dsph = pd.timPhongTheoChoNgoi(17,99);
					listBan.removeAll();
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã có khách"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Có khách"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Có khách"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã gọi món"))
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã gọi món"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã gọi món"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
					else
					{
						if(dsb.size()>0)
					    	for (Ban ban : dsb) {
					    		if(!ban.getTrangThai().trim().equals("Đã đặt trước"))
					    			continue;
					        	String roomID = ban.getMaBan();
					            String tableID = ban.getTenBan();
					            String seats = ban.getSoLuongChoNgoi()+"";
					            String status = ban.getTrangThai().trim();
					            String imagePath = "images/ban_an.png";
					            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
					            
					        }
						if(dsph.size()>0)
				    	for (Phong p : dsph) {
				    		if(!p.getTrangThai().trim().equals("Đã đặt trước"))
				    			continue;
				        	String roomID = p.getMaPhong();
				            String tableID = p.getTenPhong();
				            String seats = p.getSoLuongChoNgoi()+"";
				            String status = p.getTrangThai().trim();
				            String imagePath = "images/phongan.jpg";
				            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
				                    
				        }
						listBan.revalidate();
						listBan.repaint();
						return;
					}
				}
			}
		});
//        
        tinhTrangGMon  = editJcombo();
        tinhTrangGMon.addItem("Các phòng/bàn đã có khách");
        tinhTrangGMon.addItem("Các phòng/bàn đã gọi món");
        tinhTrangGMon.addItem("Các phòng/bàn đã đặt trước");
        tinhTrangGMon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã có khách"))
				{
					listBan.removeAll();
					napBanCoKhach();
					napPhongCoKhach();
					listBan.revalidate();
					listBan.repaint();
					choNgoiBan.setSelectedIndex(0);
					return;
				}
				if(tinhTrangGMon.getSelectedItem().equals("Các phòng/bàn đã gọi món"))
				{
					listBan.removeAll();
					napBanDaGoiMon();
					napPhongDaGoiMon();
					listBan.revalidate();
					listBan.repaint();
					choNgoiBan.setSelectedIndex(0);
					return;
				}
				else
				{
					listBan.removeAll();
					napBanDaDatTruoc();
					napPhongDaDatTruoc();
					listBan.revalidate();
					listBan.repaint();
					choNgoiBan.setSelectedIndex(0);
				}
				
			}
		});

        row2.add(Box.createHorizontalStrut(100));
        row2.add(choNgoiBan);
        row2.add(Box.createHorizontalStrut(100));
        row2.add(tinhTrangGMon);
        row2.add(Box.createHorizontalStrut(100));

        JPanel lblTable = new JPanel(new GridLayout(1, 2));
        
        lblBanList = editJlabel("Danh Sách Các Phòng và Bàn", 20);
        

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
        napBanCoKhach();
        napPhongCoKhach();
        
    }
    
    public void napBanDaGoiMon()
    {
    	dsb = bd.timBanDaGoiMon();
    	if(dsb.size()>0)
    	for (Ban ban : dsb) {
        	String roomID = ban.getMaBan();
            String tableID = ban.getTenBan();
            String seats = ban.getSoLuongChoNgoi()+"";
            String status = ban.getTrangThai().trim();
            String imagePath = "images/ban_an.png";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
            
        }
    }
    public void napPhongDaGoiMon()
    {
    	dsph = pd.timPhongDaGoiMon();
    	for (Phong p : dsph) {
        	String roomID = p.getMaPhong();
            String tableID = p.getTenPhong();
            String seats = p.getSoLuongChoNgoi()+"";
            String status = p.getTrangThai().trim();
            String imagePath = "images/phongan.jpg";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
                    
        }
    }
    public void napBanCoKhach()
    {
    	dsb = bd.timBanDangCoKhach();
    	for (Ban ban : dsb) {
        	String roomID = ban.getMaBan();
            String tableID = ban.getTenBan();
            String seats = ban.getSoLuongChoNgoi()+"";
            String status = ban.getTrangThai().trim();
            String imagePath = "images/ban_an.png";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
            
        }
    }
    public void napPhongCoKhach()
    {
    	dsph = pd.timPhongDangCoKhach();
    	for (Phong p : dsph) {
        	String roomID = p.getMaPhong();
            String tableID = p.getTenPhong();
            String seats = p.getSoLuongChoNgoi()+"";
            String status = p.getTrangThai().trim();
            String imagePath = "images/phongan.jpg";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
                    
        }
    }
    public Ban timBanTheoMa(String maBan)
    {
    	return dsb.get(dsb.indexOf(new Ban(maBan)));
    }
    public Phong timPhongTheoMa(String maPhong)
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
    
    public JPanel ttBan(String roomID, String ten, String seats, String status, String image,String chuThich1) {
    	EditJPanel form = new EditJPanel(0.6f, Color.white, 40, 40);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        //hiển thị hình ảnh bàn
        EditJPanel formImages = new EditJPanel(0.6f, Color.white, 40, 40);
        ImageIcon images = new ImageIcon(image);
        formImages.add(new JLabel(new ImageIcon(images.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));

        //thông tin bàn
        Box thongTinBan = Box.createVerticalBox();
        
        JPanel row0 = new JPanel();
        row0.add(new JLabel(chuThich1 + roomID));

        JPanel row1 = new JPanel();
        row1.add(new JLabel(ten));

        JPanel row2 = new JPanel();
        row2.add(new JLabel("Số Chỗ Ngồi: " + seats));

        EditJPanel row3 = new EditJPanel(0.8f,status.equals("Có khách") ? Color.GREEN: status.equals("Đã đặt trước")? Color.RED:Color.yellow, 20, 20);
 

        JPanel row4 = new JPanel();
        
        EditJButton btnGoiMon = new EditJButton("Gọi món", 50);
        if(status.equals("Trống"))
        btnGoiMon.setEnabled(false);	
        btnGoiMon.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnGoiMon.setPreferredSize(new Dimension(150, 40));
        btnGoiMon.setBackground(Color.decode("#006400"));
        btnGoiMon.setActionCommand(roomID);
        btnGoiMon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chuThich1.equals("Mã Bàn: "))
				{
					DashboardGUI.center.removeAll();
					String maBan = e.getActionCommand().trim();
					Ban b = timBanTheoMa(maBan);
					DashboardGUI.center.add(new XuLyGoiMonBanGUI(tk, b));
					DashboardGUI.center.revalidate();
					DashboardGUI.center.repaint();
				}
				else
				{
					DashboardGUI.center.removeAll();
					String maPhong = e.getActionCommand().trim();
					Phong p = timPhongTheoMa(maPhong);
					DashboardGUI.center.add(new XuLyGoiMonPhongGUI(tk, p));
					DashboardGUI.center.revalidate();
					DashboardGUI.center.repaint();
				}
				
			}
		});
        row4.add(btnGoiMon);
        if(status.equals("Đã đặt trước"))
        {
            EditJButton btnTTKhach = new EditJButton("Xem Khách", 50);
            btnTTKhach.setFont(new Font("Times New Roman", Font.BOLD, 18));
            btnTTKhach.setPreferredSize(new Dimension(150, 40));
            btnTTKhach.setBackground(Color.decode("#006400"));
            row4.add(btnTTKhach);
            btnTTKhach.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String maPhong = btnGoiMon.getActionCommand().trim();
						KhachHang kh = (KhachHang) io.readFromFileKh("tempKhachHang/"+maPhong);
						new GuiXemTTKhachHangDatTruoc(kh,maPhong);
					} catch (Exception e1) {
						
						thongBaoLoi();
					}
					
				}
			});
        }

        
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
    public void napBanDaDatTruoc()
    {
    	dsb = bd.timBanDatTruoc();
    	for (Ban ban : dsb) {
        	String roomID = ban.getMaBan();
            String tableID = ban.getTenBan();
            String seats = ban.getSoLuongChoNgoi()+"";
            String status = ban.getTrangThai().trim();
            String imagePath = "images/ban_an.png";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Bàn: "));
            
        }
    }
    public void thongBaoLoi()
    {
    	JOptionPane.showMessageDialog(this,"Lỗi Khách Hàng!");
    }
    public void napPhongDaDatTruoc()
    {
    	dsph = pd.timPhongDatTruoc();
    	for (Phong p : dsph) {
        	String roomID = p.getMaPhong();
            String tableID = p.getTenPhong();
            String seats = p.getSoLuongChoNgoi()+"";
            String status = p.getTrangThai().trim();
            String imagePath = "images/phongan.jpg";
            listBan.add(ttBan(roomID, tableID, seats, status, imagePath,"Mã Phòng: "));
                    
        }
    }
}
    
