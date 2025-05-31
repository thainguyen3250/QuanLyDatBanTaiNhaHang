package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import ConnectDB.ConnectDB;
import DAO.TaiKhoan_DAO;
import EDIT.BackgroundPanel;
import EDIT.EditJButton;
import EDIT.EditJPanel;
import Entity.TaiKhoan;

public class loginGUI extends JFrame implements ActionListener {
    private JLabel lbltaiKhoan, lblMatKhau, lblTitle;
    private JTextField txtTaiKhoan; 
    private JPasswordField txtMatKhau;
    private JRadioButton showPassword;
    private EditJButton login;
    private TaiKhoan tk;
    private TaiKhoan_DAO daoTK;
    public loginGUI() {
    	ConnectDB.getInstance().connect();
    	daoTK = new TaiKhoan_DAO();
        setTitle("Login");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel mainPanel = new BackgroundPanel("images/nhàHang.jpg");
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        
        EditJPanel nen = new EditJPanel(0.4f, Color.white, 0, 0);
        mainPanel.add(nen);
        
        

        JPanel logoPanel = new BackgroundPanel("images/logo.png");
        logoPanel.setPreferredSize(new Dimension(400, 600));
        logoPanel.setOpaque(false);
        nen.add(logoPanel, BorderLayout.WEST);

        
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        nen.add(rightPanel, BorderLayout.CENTER);

        
        EditJPanel blurPanel = new EditJPanel(0.6f, Color.white, 30, 30);
        blurPanel.setLayout(new BoxLayout(blurPanel, BoxLayout.Y_AXIS));
        blurPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        
        lblTitle = editLabel("Đăng nhập", 35);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        Box row1 = Box.createHorizontalBox();
        row1.add(lbltaiKhoan = editLabel("Tài khoản", 18));
        row1.add(Box.createHorizontalStrut(10));
        row1.add(txtTaiKhoan = editJTextField());

        
        Box row2 = Box.createHorizontalBox();
        row2.add(lblMatKhau = editLabel("Mật khẩu", 18));
        row2.add(Box.createHorizontalStrut(10));
        row2.add(txtMatKhau = editPassword());
        
        Box row3 = Box.createHorizontalBox();
        row3.add(showPassword = editRadio());

        
        login = new EditJButton("Xác nhận", 40);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.addActionListener(this);

        
        blurPanel.add(lblTitle);
        blurPanel.add(Box.createVerticalStrut(20));
        blurPanel.add(row1);
        blurPanel.add(Box.createVerticalStrut(15));
        blurPanel.add(row2);
        blurPanel.add(Box.createVerticalStrut(10));
        blurPanel.add(row3);
        blurPanel.add(Box.createVerticalStrut(15));
        blurPanel.add(login);

        rightPanel.add(blurPanel);
        
        showPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showPassword.isSelected()) {
					txtMatKhau.setEchoChar((char) 0);
				}else {
					txtMatKhau.setEchoChar('●');
				}
			}
		});
        txtMatKhau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dangNhap();
			}
		});
        setVisible(true);
    }

    public static void main(String[] args) {
        new loginGUI();
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
    
    public JRadioButton editRadio() {
    	JRadioButton radio = new JRadioButton("Show password");
    	radio.setOpaque(false);
    	radio.setBackground(Color.decode("#48D1CC"));
    	radio.setFocusPainted(false);
    	radio.setForeground(Color.black);
    	radio.setFont(new Font("Arial", Font.ITALIC, 18));
    	return radio;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
        	dangNhap();
        }
    }
    public void dangNhap()
    {
    	String tenDangNhap = txtTaiKhoan.getText().toUpperCase();
    	char[] matKhau = txtMatKhau.getPassword();
    	String matkhau = new String(matKhau).toLowerCase();
    	tk = daoTK.timTaiKhoan(tenDangNhap);
    	if(tk==null)
    	{
    		JOptionPane.showMessageDialog(this,"Tài Khoản Không Tồn Tại!");
    		txtTaiKhoan.selectAll();
    		txtTaiKhoan.requestFocus();
    		return;
    	}
    	if(!tk.getMatKhau().equals(matkhau))
    	{
    		JOptionPane.showMessageDialog(this,"Sai Mật Khẩu!");
    		txtMatKhau.selectAll();
    		txtMatKhau.requestFocus();
    		return;
    	}
    	new DashboardGUI(tk);
    	this.dispose();
    }
}
