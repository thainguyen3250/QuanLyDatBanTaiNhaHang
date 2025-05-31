package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DAO.TaiKhoan_DAO;
import EDIT.BackgroundPanel;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import Entity.TaiKhoan;

public class TaiKhoanGUI extends JPanel implements ActionListener{
	public JTextField txtTaiKhoan, txtPassCu, txtPassMoi, txtPassMoiAgain;
	public JLabel lblTaiKhoan, lblPassCu, lblPassMoi, lblPassMoiAgain;
	public EditJButton reset, btnXacNhan;
	private TaiKhoan tk;
	private TaiKhoan_DAO tkd;
	private JFrame jf;
	public TaiKhoanGUI(TaiKhoan tk,JFrame jf) {
        setLayout(new BorderLayout());
        this.tk=tk;
        this.jf = jf;
        JPanel mainPanel = new BackgroundPanel("images/nhàHang.jpg");
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        tkd = new TaiKhoan_DAO();
        EditJPanel nen = new EditJPanel(0.4f, Color.white, 0, 0);
        nen.setLayout(new GridBagLayout()); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
   
        mainPanel.add(nen);
        
        EditJPanel blurPanel = new EditJPanel(0.6f, Color.white, 30, 30);
        blurPanel.setLayout(new BoxLayout(blurPanel, BoxLayout.Y_AXIS));
        blurPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));
        nen.add(blurPanel, gbc);
        
        ImageIcon logo = new ImageIcon("images/icons8-account.png");
        JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        JPanel row0 = new JPanel();
        row0.setOpaque(false);
        row0.add(logoLabel);
        
        Box row1 = Box.createHorizontalBox();
        row1.add(lblTaiKhoan = editLabel("Tài khoản", 18));
        row1.add(Box.createHorizontalStrut(10));
        row1.add(txtTaiKhoan = editJTextField());
        txtTaiKhoan.setText(tk.getTenTaiKhoan());
        txtTaiKhoan.setEditable(false);
        
        Box row2 = Box.createHorizontalBox();
        row2.add(lblPassCu = editLabel("Mật khẩu cũ", 18));
        row2.add(Box.createHorizontalStrut(10));
        row2.add(txtPassCu = editJTextField());
        
        Box row3 = Box.createHorizontalBox();
        row3.add(lblPassMoi = editLabel("Mật khẩu mới", 18));
        row3.add(Box.createHorizontalStrut(10));
        row3.add(txtPassMoi = editPassword());
        
        Box row4 = Box.createHorizontalBox();
        row4.add(lblPassMoiAgain = editLabel("Nhập lại mật khẩu", 18));
        row4.add(Box.createHorizontalStrut(10));
        row4.add(txtPassMoiAgain = editPassword());
        
        lblTaiKhoan.setPreferredSize(lblPassMoiAgain.getPreferredSize());
        lblPassCu.setPreferredSize(lblPassMoiAgain.getPreferredSize());
        lblPassMoi.setPreferredSize(lblPassMoiAgain.getPreferredSize());
        
        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row5.setOpaque(false);
        btnXacNhan = new EditJButton("Đổi Mật Khẩu", 40);
        btnXacNhan.setBackground(Color.green);
        
        btnXacNhan.addActionListener(this);
        
        txtPassCu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPassCu();
			}
		});
        
        txtPassMoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkDoDaiPass();
			}
		});
        
        txtPassMoiAgain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPassMoiAgain();
			}
		});
        
        row5.add(Box.createHorizontalStrut(15));
        row5.add(btnXacNhan);
        
        blurPanel.add(row0);
        blurPanel.add(row1);
        blurPanel.add(Box.createVerticalStrut(10));
        blurPanel.add(row2);
        blurPanel.add(Box.createVerticalStrut(10));
        blurPanel.add(row3);
        blurPanel.add(Box.createVerticalStrut(10));
        blurPanel.add(row4);
        blurPanel.add(Box.createVerticalStrut(20));
        blurPanel.add(row5);
	}
	
	public void checkPassCu() {
		if(!txtPassCu.getText().equals(tk.getMatKhau())) {
			JOptionPane.showMessageDialog(this,"Nhập sai mật khẩu cũ!");
			txtPassCu.selectAll();
			txtPassCu.requestFocus();
		}else {
			txtPassMoi.removeAll();
			txtPassMoi.requestFocus();
		}
	}
	
	public void checkDoDaiPass() {
		if(txtPassMoi.getText().matches("^.{0,6}$"))
		{
			JOptionPane.showMessageDialog(this,"Mật Khẩu Quá ngắn, yêu cầu trên 6 kí tự!");
			txtPassMoi.selectAll();
			txtPassMoi.requestFocus();
		}else {
			txtPassMoiAgain.removeAll();
			txtPassMoiAgain.requestFocus();
		}
	}
	
	public void checkPassMoiAgain() {
		if(!txtPassMoi.getText().equals(txtPassMoiAgain.getText()))
		{
			JOptionPane.showMessageDialog(this,"Mật Khẩu Nhập Lại Không Khớp");
			txtPassMoiAgain.selectAll();
			txtPassMoiAgain.requestFocus();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnXacNhan) {
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn đổi mật khẩu không!","Cảnh báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
				String passCu = txtPassCu.getText();
				String passMoi = txtPassMoi.getText();
				String passMoiAgain = txtPassMoiAgain.getText();
				if(!passCu.equals(tk.getMatKhau()))
				{
					JOptionPane.showMessageDialog(this,"Nhập sai mật khẩu cũ!");
					return;
				}
				if(!passMoi.equals(passMoiAgain))
				{
					JOptionPane.showMessageDialog(this,"Mật Khẩu Nhập Lại Không Khớp");
					return;
				}
				if(passMoi.matches("^.{0,6}$"))
				{
					JOptionPane.showMessageDialog(this,"Mật Khẩu Quá ngắn, yêu cầu trên 6 kí tự!");
					return;
				}
				if(tkd.suaMatKhau(tk, passMoi))
				{
					JOptionPane.showMessageDialog(this,"Sửa Thành Công!");
					this.jf.dispose();
					new loginGUI();
					return;
				}
				else
				{
					JOptionPane.showMessageDialog(this,"Sửa Thất Bại!");

				}
			}
			
		}
	}
	
	
	public JPasswordField editPassword() {
        JPasswordField password = new JPasswordField();
        password.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        password.setPreferredSize(new Dimension(250, 35));
        return password;
    }

    
    public JLabel editLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, size));
        label.setForeground(Color.black);
        return label;
    }

    
    public JTextField editJTextField() {
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        textField.setPreferredSize(new Dimension(250, 35));
        return textField;
    }
    

}
