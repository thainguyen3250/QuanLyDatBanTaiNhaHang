package GUI;

import javax.swing.*;

import ConnectDB.ConnectDB;
import EDIT.EditJPanel;
import EDIT.EditScrollPane;
import Entity.TaiKhoan;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class DashboardGUI extends JFrame {
    public static JPanel center;
    private final String[] icons = {
            "icons/icons8-home-page.png",
            "icons/icons8-category.png",
            "icons/icons8-update.png",
            "icons/icons8-task-completed.png",
            "icons/icons8-statistics.png",
            "icons/icons8-chevron-up.png",
            "icons/icons8-chevron-down.png",
            "icons/icons8-search.png",
            "icons/icons8-acount.png",
            "icons/icons8-exit.png"
            
        };
	public TaiKhoan tk;
	private JPanel mn1;
	private JPanel mn2;
	private JPanel mn3;
	private JPanel mn4;
    
    public DashboardGUI(TaiKhoan tk) {
    	
    	ConnectDB.getInstance().connect();
		this.tk=tk;
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Sidebar 
        JPanel sidebarContent = new JPanel();
        sidebarContent.setLayout(new BoxLayout(sidebarContent, BoxLayout.Y_AXIS));
        sidebarContent.setBackground(Color.decode("#76D7C4"));

        // Logo
        EditJPanel nen = new EditJPanel(0.7f, Color.decode("#D5F5E3"), 120, 120);
        nen.setLayout(new BoxLayout(nen, BoxLayout.X_AXIS));
        ImageIcon logo = new ImageIcon("images/logo.png");
        JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nen.add(logoLabel);
        sidebarContent.add(Box.createVerticalStrut(10));
        sidebarContent.add(nen);
        sidebarContent.add(Box.createVerticalStrut(20));

        // Menu items
        Box row1 = Box.createHorizontalBox();
        Box row2 = Box.createHorizontalBox();
        Box row3 = Box.createHorizontalBox();
        Box row4 = Box.createHorizontalBox();
        Box row5 = Box.createHorizontalBox();
        Box row6 = Box.createHorizontalBox();
        Box row7 = Box.createHorizontalBox();
        Box row8 = Box.createHorizontalBox();
        
        row1.add(createMenuButton("Trang Chủ", icons[0], e -> {
            center.removeAll();
            center.add(new TrangChuGUI(tk), BorderLayout.CENTER);
            center.revalidate();
            center.repaint();
        }));
        row2.add(mn1 = createDropdownMenu("Danh Mục", icons[1], new String[]{"Bàn Ăn", "Phòng Ăn", "Món Ăn", "Nhân Viên", "Khách Hàng", "Hóa Đơn"}));
        if (tk.getMaNhanVien().getMaLoaiNhanVien().getTenLoai().equals("Nhân viên"))
        {
        	row3.add(mn2 = createDropdownMenu("Cập Nhật", icons[2], new String[]{"Khách Hàng"}));
        }
        else
        {
        	row3.add(mn2 = createDropdownMenu("Cập Nhật", icons[2], new String[]{"Bàn Ăn", "Phòng Ăn", "Món Ăn", "Nhân Viên", "Khách Hàng"}));
        }
        
        row4.add(mn3 = createDropdownMenu("Xử Lý Tác Vụ", icons[3], new String[]{"Đặt Bàn", "Đặt Phòng", "Gọi Món", "Lập Hóa Đơn"}));
        row5.add(mn4 = createDropdownMenu("Thống Kê", icons[4], new String[]{"Món Ăn Bán Chạy", "Doanh Thu", "Khách Hàng"}));
//        row6.add(createDropdownMenu("Tìm Kiếm", icons[7], new String[]{"Bàn Ăn", "Phòng Ăn", "Món Ăn", "Nhân Viên", "Khách Hàng"}));
        
        row7.add(createMenuButton("Tài Khoản", icons[8], e -> {
        	center.removeAll();
            center.add(new TaiKhoanGUI(tk,this), BorderLayout.CENTER);
            center.revalidate();
            center.repaint();
        }));
        
        
        row8.add(createMenuButton("Thoát", icons[9], e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new loginGUI();
            }
        }));
        
        sidebarContent.add(row1);
        sidebarContent.add(row2);
        sidebarContent.add(row3);
        sidebarContent.add(row4);
        sidebarContent.add(row5);
        sidebarContent.add(row6);
        sidebarContent.add(Box.createVerticalStrut(30));
        sidebarContent.add(row7);
        sidebarContent.add(Box.createVerticalGlue());
        sidebarContent.add(row8);

        // ScrollPane chứa sidebar
        JScrollPane scrollPane = new EditScrollPane(sidebarContent);
        scrollPane.setPreferredSize(new Dimension(250, 600));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        add(scrollPane, BorderLayout.WEST);

        // Center panel
        center = new JPanel(new BorderLayout());
        add(center, BorderLayout.CENTER);
        center.add(new TrangChuGUI(tk), BorderLayout.CENTER);
    }

    private JButton createMenuButton(String name, String iconPath, ActionListener action) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton button = new JButton(name, new ImageIcon(scaled));
        styleButton(button);
        button.addActionListener(action);
        return button;
    }


    private JPanel createDropdownMenu(String title, String iconPath, String[] subItems) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(40, 10, 0, 10));
        container.setOpaque(false);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
       
        ImageIcon chevronUp = new ImageIcon(icons[5]);
        Image scaledChevronUp = chevronUp.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
        ImageIcon chevronDown = new ImageIcon(icons[6]);
        Image scaledChevronDown = chevronDown.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
        JButton mainButton = new JButton("");
        styleButton(mainButton);
        

        JPanel buttonContent = new JPanel(new BorderLayout());
        buttonContent.setOpaque(false);
        

        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        buttonContent.add(iconLabel, BorderLayout.WEST);
        

        JLabel textLabel = new JLabel(title);
        textLabel.setForeground(Color.decode("#34495E"));
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        buttonContent.add(textLabel, BorderLayout.CENTER);
        

        JLabel chevronLabel = new JLabel(new ImageIcon(scaledChevronUp));
        buttonContent.add(chevronLabel, BorderLayout.EAST);
        
        mainButton.setLayout(new BorderLayout());
        mainButton.add(buttonContent);
        mainButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setVisible(false);
        for (String item : subItems) {
            JButton subBtn = new JButton("   • " + item);
            styleSubButton(subBtn);
            
            subBtn.addActionListener(e -> {
                center.removeAll();

                switch (title) {
                    case "Cập Nhật":
                        switch (item) {
                            case "Món Ăn":
                                center.add(new CapNhatMonAnGUI(), BorderLayout.CENTER);
                                break;
                            case "Bàn Ăn":
                                center.add(new CapNhatBanAnGUI(), BorderLayout.CENTER);
                                break;
                            case "Phòng Ăn":
                                center.add(new CapNhatPhongGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Nhân Viên":
                                center.add(new CapNhatNhanVienGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Khách Hàng":
							try {
								center.add(new CapNhatKhachHangGUI(tk), BorderLayout.CENTER);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                                break;
                        }
                        break;

                    case "Danh Mục":
                        switch (item) {
                            case "Món Ăn":
                                center.add(new DanhMucMonAnGUI(), BorderLayout.CENTER);
                                 break;
                            case "Khách Hàng":
                            	 center.add(new DanhMucKhachHangGUI(), BorderLayout.CENTER);
                                 break;
                            case "Nhân Viên":
                           	 center.add(new DanhMucNhanVienGUI(), BorderLayout.CENTER);
                                break;
                            case "Bàn Ăn":
                              	 center.add(new DanhMucBanAnGUI(), BorderLayout.CENTER);
                                 break;
                            case "Phòng Ăn":
                              	 center.add(new DanhMucPhongAnGUI(), BorderLayout.CENTER);
                                  break;
                            case "Hóa Đơn":
							try {
								center.add(new DanhMucHoaDonGUI(tk), BorderLayout.CENTER);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                                 break;
                        }
                        break;

                    case "Xử Lý Tác Vụ":
                        switch (item) {
                            case "Đặt Bàn":
                                center.add(new XuLyDatBanGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Đặt Phòng":
                                center.add(new XuLyDatPhongGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Gọi Món":
                                center.add(new XuLyChonBanGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Lập Hóa Đơn":
                                center.add(new XuLyLapHoaDonGUI(tk), BorderLayout.CENTER);
                                break;
                        }
                        break;

                    case "Thống Kê":
                        switch (item) {
                            case "Doanh Thu":
                            	center.add(new ThongKeDoanhThuGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Khách Hàng":
                            	center.add(new ThongKeTopKhachHangGUI(tk), BorderLayout.CENTER);
                                break;
                            case "Món Ăn Bán Chạy":
                            	center.add(new ThongKeTopMonAnGUI(tk), BorderLayout.CENTER);
                                break;
                        }
                        break;
                }

                center.revalidate();
                center.repaint();
            });
            
            subPanel.add(subBtn);
        }

        mainButton.addActionListener(e -> {
            boolean isVisible = subPanel.isVisible();
            subPanel.setVisible(!isVisible);
            

            if (isVisible) {

                chevronLabel.setIcon(new ImageIcon(scaledChevronUp));
            } else {

                chevronLabel.setIcon(new ImageIcon(scaledChevronDown));
            }
            
            subPanel.revalidate();
            subPanel.repaint();
        });

        container.add(mainButton);
        container.add(subPanel);
        return container;
    }
    public void disposeed()
    {
    	dispose();
    }

    private void styleButton(JButton button) {
        button.setMaximumSize(new Dimension(220, 40));
        button.setPreferredSize(new Dimension(220, 40));
        button.setMinimumSize(new Dimension(220, 40));
        button.setBackground(Color.decode("#76D7C4"));
        button.setForeground(Color.decode("#34495E"));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setMargin(new Insets(5, 15, 5, 5));
    }

    private void styleSubButton(JButton button) {
        button.setMaximumSize(new Dimension(220, 35));
        button.setPreferredSize(new Dimension(220, 35));
        button.setMinimumSize(new Dimension(220, 35));
        button.setBackground(Color.decode("#D1F2EB"));
        button.setForeground(Color.decode("#34495E"));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setMargin(new Insets(5, 30, 5, 5));
    }

}