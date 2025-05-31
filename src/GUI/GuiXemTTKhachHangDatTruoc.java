package GUI;
import javax.swing.*;

import EDIT.EditJButton;
import Entity.KhachHang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiXemTTKhachHangDatTruoc extends JFrame implements ActionListener{
	public EditJButton xacnhan;
    public GuiXemTTKhachHangDatTruoc(KhachHang kh,String tenPhong) {
        
        setTitle("Thông tin khách hàng");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel phongLBL = new JLabel("Mã Phòng/Bàn: "+tenPhong);
        JLabel nameLabel = new JLabel("Tên khách hàng: "+kh.getHoTen());
        JLabel phoneLabel = new JLabel("Số điện thoại: "+kh.getSoDienThoai());
        phongLBL.setFont(new Font("Arial", Font.PLAIN, 21));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 21));
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 21));
        panel.add(phongLBL);
        panel.add(nameLabel);
        panel.add(phoneLabel);
        
        xacnhan = new EditJButton("Xác nhận", 40);
        xacnhan.setBackground(Color.green);
        JPanel b1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        b1.add(xacnhan);
        
        xacnhan.addActionListener(this);
        
        add(panel, BorderLayout.CENTER);
        add(b1, BorderLayout.SOUTH);
        setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == xacnhan) {
			dispose();
		}
	}

}

