package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.LoaiMonAn_DAO;
import DAO.MonAn_DAO;
import EDIT.EditScrollPane;
import Entity.LoaiMonAn;
import Entity.MonAn;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CapNhatMonAnGUI extends JPanel implements ActionListener,MouseListener{
    private JTabbedPane tabbedPane;
    private JPanel panelQuanLyMon, panelThemNhieu;
    private JLabel labelImage;
    private JButton buttonThemAnh;
    private JLabel lblTenMon;
    private JTextField txtTenMon;
    private JLabel lblTrangThai;
    private JComboBox<String> cbTrangThai;
    private JLabel lblLoaiMon;
    private JComboBox<LoaiMonAn> cbLoaiMon;
    private JLabel lblGiaTien;
    private JTextField txtGiaTien;
    private JLabel lblMoTa;
    private JTextArea txtMoTa;
    private JScrollPane moTaScrollPane;
    private JButton btnThem, btnXoa, btnSua;
    private JLabel lblTimKiem;
    private JTextField txtTimKiem;
    private JLabel lblTimKiem2;
    private JComboBox<String> cbDanhMuc;
    private JTable table, tableThemNhieu;
    private JScrollPane scrollPane;
    private String imgPath="";
	private DefaultTableModel model, modelThemNhieu;
    private MonAn_DAO mad;
    private LoaiMonAn_DAO lmad;
    private ArrayList<MonAn> dsma;
    private ArrayList<LoaiMonAn> dslma;
	private JLabel lblMaMon;
	private JTextField txtMaMon;
	private JLabel lblDvt;
	private JTextField txtDvt;
	public DecimalFormat format = new DecimalFormat("#, ### VND");
	private JTextField txtTenMonNhieu;
	private JTextField txtMaMonNhieu;
	
    public CapNhatMonAnGUI() {
    	ConnectDB.getInstance().connect();
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 1250, 1200);
        
        mad = new MonAn_DAO();
        lmad = new LoaiMonAn_DAO();
        
        panelQuanLyMon = new JPanel(null);
        panelQuanLyMon.setPreferredSize(new Dimension(1250, 1200));

        // Ảnh
        labelImage = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        labelImage.setFont(new Font("Arial", Font.PLAIN, 18));
        labelImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        labelImage.setBounds(20, 20, 180, 220);
        
        buttonThemAnh = new JButton("Thêm ảnh");
        buttonThemAnh.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonThemAnh.setBounds(40, 250, 140, 40);
        buttonThemAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh món ăn");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fullPath = selectedFile.getAbsolutePath();
                String projectPath = System.getProperty("user.dir"); // Lấy đường dẫn gốc của project
                // Nếu ảnh nằm trong thư mục con của project
                String relativePath = fullPath.replace(projectPath + File.separator, "").replace("\\", "/");
                ImageIcon icon = new ImageIcon(relativePath.trim());
                System.out.println(relativePath);
                // Resize ảnh cho vừa JLabel
                Image scaledImage = icon.getImage().getScaledInstance(
                labelImage.getWidth(), labelImage.getHeight(), Image.SCALE_SMOOTH);
                labelImage.setIcon(new ImageIcon(scaledImage));
                labelImage.setText(""); // Xóa chữ "Chưa có ảnh" nếu có

                // Có thể lưu đường dẫn ảnh để lưu DB nếu cần:
                imgPath = relativePath;
            }
        });

        panelQuanLyMon.add(labelImage);
        panelQuanLyMon.add(buttonThemAnh);

        // Thông tin món
        lblMaMon = new JLabel("Mã món ăn:");
        lblMaMon.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMaMon.setBounds(230, 20, 120, 30);
        panelQuanLyMon.add(lblMaMon);
        
        txtMaMon = new JTextField();
        txtMaMon.setFont(new Font("Arial", Font.PLAIN, 18));
        txtMaMon.setBounds(360, 20, 250, 30);
        panelQuanLyMon.add(txtMaMon);
        
        
        lblTenMon = new JLabel("Tên món ăn:");
        lblTenMon.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTenMon.setBounds(230, 60, 120, 30);
        panelQuanLyMon.add(lblTenMon);

        txtTenMon = new JTextField();
        txtTenMon.setFont(new Font("Arial", Font.PLAIN, 18));
        txtTenMon.setBounds(360, 60, 250, 30);
        panelQuanLyMon.add(txtTenMon);
        
        lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTrangThai.setBounds(230, 100, 120, 30);
        panelQuanLyMon.add(lblTrangThai);

        cbTrangThai = new JComboBox<>(new String[]{"Đang Phục Vụ", "Tạm Ngưng"});
        cbTrangThai.setFont(new Font("Arial", Font.PLAIN, 18));
        cbTrangThai.setBounds(360, 100, 250, 30);
        panelQuanLyMon.add(cbTrangThai);

        lblLoaiMon = new JLabel("Loại món:");
        lblLoaiMon.setFont(new Font("Arial", Font.PLAIN, 18));
        lblLoaiMon.setBounds(230, 140, 120, 30);
        panelQuanLyMon.add(lblLoaiMon);

        cbLoaiMon = new JComboBox<LoaiMonAn>();
        cbLoaiMon.setFont(new Font("Arial", Font.PLAIN, 18));
        cbLoaiMon.setBounds(360, 140, 250, 30);
        panelQuanLyMon.add(cbLoaiMon);

        lblGiaTien = new JLabel("Giá tiền:");
        lblGiaTien.setFont(new Font("Arial", Font.PLAIN, 18));
        lblGiaTien.setBounds(230, 180, 120, 30);
        panelQuanLyMon.add(lblGiaTien);

        txtGiaTien = new JTextField();
        txtGiaTien.setFont(new Font("Arial", Font.PLAIN, 18));
        txtGiaTien.setBounds(360, 180, 250, 30);
        panelQuanLyMon.add(txtGiaTien);
        
        
        lblDvt = new JLabel("Đơn Vị Tính:");
        lblDvt.setFont(new Font("Arial", Font.PLAIN, 18));
        lblDvt.setBounds(230, 220, 120, 30);
        panelQuanLyMon.add(lblDvt);
        
        txtDvt = new JTextField();
        txtDvt.setFont(new Font("Arial", Font.PLAIN, 18));
        txtDvt.setBounds(360, 220, 250, 30);
        panelQuanLyMon.add(txtDvt);
        

        lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMoTa.setBounds(230, 260, 120, 30);
        panelQuanLyMon.add(lblMoTa);

        txtMoTa = new JTextArea();
        txtMoTa.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        
        
        moTaScrollPane = new JScrollPane(txtMoTa);
        moTaScrollPane.setBounds(360, 260, 250, 60);
        panelQuanLyMon.add(moTaScrollPane);

        // Buttons
        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 18));
        btnThem.setBounds(650, 20, 150, 40);
        panelQuanLyMon.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 18));
        btnXoa.setBounds(650, 70, 150, 40);
        panelQuanLyMon.add(btnXoa);

        btnSua = new JButton("Sửa");
        btnSua.setFont(new Font("Arial", Font.BOLD, 18));
        btnSua.setBounds(650, 120, 150, 40);
        panelQuanLyMon.add(btnSua);

        // Tìm kiếm và lọc
        lblTimKiem = new JLabel("Tìm Theo Tên:");
        lblTimKiem.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTimKiem.setBounds(850, 30, 50, 30);
        panelQuanLyMon.add(lblTimKiem);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 18));
        txtTimKiem.setBounds(900, 30, 250, 30);
        txtTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String tenMon = txtTimKiem.getText().trim();
				if(tenMon.length()==0)
				{
					if(table.getRowCount()!=0)
					xoaHetBang();
					napDuLieuLenTable();
				}
				if(tenMon.length()!=0)
				{
					dsma = mad.timMonAnTheoTen(tenMon);
					if(table.getRowCount()!=0)
					xoaHetBang();
					if (dsma.size()!=0)
					{
						for (MonAn mon : dsma) {
				            model.addRow(new Object[]{
				                mon.getMaMonAn(),
				                mon.getTenMonAn(),
				                format.format(mon.getGiaMonAn()),
				                mon.getMoTa(),
				                mon.getDonViTinh(),
				                mon.getHinhAnh(),
				                mon.getTrangThai(),
				                mon.getMaLoaiMonAn().getTenLoaiMonAn()
				            });
				        }
					}
				}
			}
		});
        panelQuanLyMon.add(txtTimKiem);


        lblTimKiem2 = new JLabel("Danh mục:");
        lblTimKiem2.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTimKiem2.setBounds(850, 70, 100, 30);
        panelQuanLyMon.add(lblTimKiem2);

        cbDanhMuc = new JComboBox<>();
        cbDanhMuc.setFont(new Font("Arial", Font.PLAIN, 18));
        cbDanhMuc.setBounds(950, 70, 200, 30);
        panelQuanLyMon.add(cbDanhMuc);
        
        cbDanhMuc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String loaiMon = (String) cbDanhMuc.getSelectedItem();
				if(loaiMon.equals("tất cả"))
				{
					if(table.getRowCount()!=0)
					xoaHetBang();
					napDuLieuLenTable();
					return;
				}
				dsma = mad.timMonAnTheoLoai(loaiMon);
				if(table.getRowCount()!=0)
				xoaHetBang();
				if (dsma.size()!=0)
				{
					for (MonAn mon : dsma) {
			            model.addRow(new Object[]{
			                mon.getMaMonAn(),
			                mon.getTenMonAn(),
			                format.format(mon.getGiaMonAn()),
			                mon.getMoTa(),
			                mon.getDonViTinh(),
			                mon.getHinhAnh(),
			                mon.getTrangThai(),
			                mon.getMaLoaiMonAn().getTenLoaiMonAn()
			            });
			        }
				}
				
			}
						
		});
        // Table
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(40);
        table.setModel(model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Món Ăn", "Tên Món Ăn", "Giá Tiền", "Thông Tin Món", "Đơn vị tính","Hình Ảnh","Trạng Thái","Loại Món"}
                
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
         }});

        scrollPane = new EditScrollPane(table);
        scrollPane.setBounds(20, 360, 1300, 400);
        panelQuanLyMon.add(scrollPane);
        
        //Panel them nhieu mon
        panelThemNhieu = new JPanel(null);
        panelThemNhieu.setPreferredSize(new Dimension(1250, 1200));
        ConnectDB.getInstance().connect();
        JLabel labelImageThemNhieu = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        labelImageThemNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        labelImageThemNhieu.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        labelImageThemNhieu.setBounds(20, 20, 180, 220);
        panelThemNhieu.add(labelImageThemNhieu);

        JButton buttonThemAnhNhieu = new JButton("Thêm ảnh");
        buttonThemAnhNhieu.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonThemAnhNhieu.setBounds(40, 250, 140, 40);
        buttonThemAnhNhieu.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh món ăn");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fullPath = selectedFile.getAbsolutePath();
                String projectPath = System.getProperty("user.dir");
                imgPath = fullPath.replace(projectPath + File.separator, "").replace("\\", "/");
                ImageIcon icon = new ImageIcon(imgPath.trim());
                Image scaledImage = icon.getImage().getScaledInstance(
                        labelImageThemNhieu.getWidth(), labelImageThemNhieu.getHeight(), Image.SCALE_SMOOTH);
                labelImageThemNhieu.setIcon(new ImageIcon(scaledImage));
                labelImageThemNhieu.setText("");
                int selectedRow = tableThemNhieu.getSelectedRow();
                if (selectedRow != -1) {
                    modelThemNhieu.setValueAt(imgPath, selectedRow, 5);
                }
            }
        });
        
        
        panelThemNhieu.add(buttonThemAnhNhieu);


        JLabel lblMaMonNhieu = new JLabel("Mã món ăn:");
        lblMaMonNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMaMonNhieu.setBounds(230, 20, 120, 30);
        panelThemNhieu.add(lblMaMonNhieu);

        txtMaMonNhieu = new JTextField();
        txtMaMonNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        txtMaMonNhieu.setBounds(360, 20, 250, 30);
        txtMaMonNhieu.setEnabled(false); 
        try {
            txtMaMonNhieu.setText(mad.generateMaMonAn());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ConnectDB.getInstance().connect();
        panelThemNhieu.add(txtMaMonNhieu);

        JLabel lblTenMonNhieu = new JLabel("Tên món ăn:");
        lblTenMonNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTenMonNhieu.setBounds(230, 60, 120, 30);
        panelThemNhieu.add(lblTenMonNhieu);

        txtTenMonNhieu = new JTextField();
        txtTenMonNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        txtTenMonNhieu.setBounds(360, 60, 250, 30);
        panelThemNhieu.add(txtTenMonNhieu);

        JLabel lblTrangThaiNhieu = new JLabel("Trạng thái:");
        lblTrangThaiNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTrangThaiNhieu.setBounds(230, 100, 120, 30);
        panelThemNhieu.add(lblTrangThaiNhieu);

        JComboBox<String> cbTrangThaiNhieu = new JComboBox<>(new String[]{"Đang Phục Vụ", "Tạm Ngưng"});
        cbTrangThaiNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        cbTrangThaiNhieu.setBounds(360, 100, 250, 30);
        panelThemNhieu.add(cbTrangThaiNhieu);

        JLabel lblLoaiMonNhieu = new JLabel("Loại món:");
        lblLoaiMonNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblLoaiMonNhieu.setBounds(230, 140, 120, 30);
        panelThemNhieu.add(lblLoaiMonNhieu);

        JComboBox<LoaiMonAn> cbLoaiMonNhieu = new JComboBox<>();
        cbLoaiMonNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        cbLoaiMonNhieu.setBounds(360, 140, 250, 30);
        panelThemNhieu.add(cbLoaiMonNhieu);

        JLabel lblGiaTienNhieu = new JLabel("Giá tiền:");
        lblGiaTienNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblGiaTienNhieu.setBounds(230, 180, 120, 30);
        panelThemNhieu.add(lblGiaTienNhieu);

        JTextField txtGiaTienNhieu = new JTextField();
        txtGiaTienNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        txtGiaTienNhieu.setBounds(360, 180, 250, 30);
        panelThemNhieu.add(txtGiaTienNhieu);

        JLabel lblDvtNhieu = new JLabel("Đơn vị tính:");
        lblDvtNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblDvtNhieu.setBounds(230, 220, 120, 30);
        panelThemNhieu.add(lblDvtNhieu);

        JTextField txtDvtNhieu = new JTextField();
        txtDvtNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        txtDvtNhieu.setBounds(360, 220, 250, 30);
        panelThemNhieu.add(txtDvtNhieu);

        JLabel lblMoTaNhieu = new JLabel("Mô tả:");
        lblMoTaNhieu.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMoTaNhieu.setBounds(230, 260, 120, 30);
        panelThemNhieu.add(lblMoTaNhieu);

        JTextArea txtMoTaNhieu = new JTextArea();
        txtMoTaNhieu.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMoTaNhieu.setLineWrap(true);
        txtMoTaNhieu.setWrapStyleWord(true);
        JScrollPane moTaScrollPaneNhieu = new JScrollPane(txtMoTaNhieu);
        moTaScrollPaneNhieu.setBounds(360, 260, 250, 60);
        panelThemNhieu.add(moTaScrollPaneNhieu);

        
        JButton btnThemTatCa = new JButton("Thêm tất cả");
        btnThemTatCa.setFont(new Font("Arial", Font.BOLD, 18));
        btnThemTatCa.setBounds(650, 20, 150, 40);
        btnThemTatCa.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm tất cả món ăn này không?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                themTatCaMonAn();
            }
        });
        panelThemNhieu.add(btnThemTatCa);

        JButton btnXoaHang = new JButton("Xóa hàng");
        btnXoaHang.setFont(new Font("Arial", Font.BOLD, 18));
        btnXoaHang.setBounds(650, 70, 150, 40);
        btnXoaHang.addActionListener(e -> {
            int selectedRow = tableThemNhieu.getSelectedRow();
            if (selectedRow != -1) {
                modelThemNhieu.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa!");
            }
        });
        panelThemNhieu.add(btnXoaHang);

        // Bảng nhập liệu
        modelThemNhieu = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Mã Món Ăn", "Tên Món Ăn", "Giá Tiền", "Mô Tả", "Đơn Vị Tính", "Hình Ảnh", "Trạng Thái", "Loại Món"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; 
            }
        };
        JTable tableThemNhieu = new JTable(modelThemNhieu);
        tableThemNhieu.setFont(new Font("Arial", Font.PLAIN, 16));
        tableThemNhieu.setRowHeight(40);
        JScrollPane scrollPaneThemNhieu = new JScrollPane(tableThemNhieu);
        scrollPaneThemNhieu.setBounds(20, 360, 1300, 400);
        panelThemNhieu.add(scrollPaneThemNhieu);


        dslma = lmad.getAllLoaiMonAn();
        for (LoaiMonAn lma : dslma) {
            cbLoaiMonNhieu.addItem(lma);
        }


        JButton btnThemHang = new JButton("Thêm hàng");
        btnThemHang.setFont(new Font("Arial", Font.BOLD, 18));
        btnThemHang.setBounds(650, 120, 150, 40);
        btnThemHang.addActionListener(e -> {
            try {
                String maMon = mad.generateMaMonAn();
                modelThemNhieu.addRow(new Object[]{maMon, "", "0", "", "", "", "Đang Phục Vụ", dslma.get(0).getTenLoaiMonAn()});
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        panelThemNhieu.add(btnThemHang);


        tableThemNhieu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableThemNhieu.getSelectedRow();
                if (row != -1) {
                    txtMaMonNhieu.setText(modelThemNhieu.getValueAt(row, 0).toString());
                    txtTenMonNhieu.setText(modelThemNhieu.getValueAt(row, 1).toString());
                    txtGiaTienNhieu.setText(modelThemNhieu.getValueAt(row, 2).toString().replaceAll("[^0-9.]", ""));
                    txtMoTaNhieu.setText(modelThemNhieu.getValueAt(row, 3).toString());
                    txtDvtNhieu.setText(modelThemNhieu.getValueAt(row, 4).toString());
                    String imagePath = modelThemNhieu.getValueAt(row, 5).toString();
                    if (!imagePath.isEmpty()) {
                        try {
                            ImageIcon icon = new ImageIcon(imagePath);
                            Image scaledImage = icon.getImage().getScaledInstance(
                                    labelImageThemNhieu.getWidth(), labelImageThemNhieu.getHeight(), Image.SCALE_SMOOTH);
                            labelImageThemNhieu.setIcon(new ImageIcon(scaledImage));
                            labelImageThemNhieu.setText("");
                        } catch (Exception ex) {
                            labelImageThemNhieu.setIcon(null);
                            labelImageThemNhieu.setText("Không thể hiển thị ảnh");
                        }
                    } else {
                        labelImageThemNhieu.setIcon(null);
                        labelImageThemNhieu.setText("Chưa có ảnh");
                    }
                    cbTrangThaiNhieu.setSelectedItem(modelThemNhieu.getValueAt(row, 6).toString());
                    String loaiMon = modelThemNhieu.getValueAt(row, 7).toString();
                    for (int i = 0; i < cbLoaiMonNhieu.getItemCount(); i++) {
                        if (cbLoaiMonNhieu.getItemAt(i).getTenLoaiMonAn().equals(loaiMon)) {
                            cbLoaiMonNhieu.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
        
        JButton btnChonExcel = new JButton("Chọn file Excel");
        btnChonExcel.setFont(new Font("Arial", Font.BOLD, 18));
        btnChonExcel.setBounds(650, 170, 200, 40);
        btnChonExcel.addActionListener(e -> chooseAndReadExcel());
        panelThemNhieu.add(btnChonExcel);
        
        JButton btnLuuVaoBang = new JButton("Lưu vào bảng");
        btnLuuVaoBang.setFont(new Font("Arial", Font.BOLD, 18));
        btnLuuVaoBang.setBounds(650, 220, 200, 40);
        btnLuuVaoBang.addActionListener(e -> {
            int selectedRow = tableThemNhieu.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    modelThemNhieu.setValueAt(txtMaMonNhieu.getText(), selectedRow, 0);
                    modelThemNhieu.setValueAt(txtTenMonNhieu.getText(), selectedRow, 1);
                    modelThemNhieu.setValueAt(format.format(Double.parseDouble(txtGiaTienNhieu.getText())), selectedRow, 2);
                    modelThemNhieu.setValueAt(txtMoTaNhieu.getText(), selectedRow, 3);
                    modelThemNhieu.setValueAt(txtDvtNhieu.getText(), selectedRow, 4);
                    modelThemNhieu.setValueAt(labelImageThemNhieu.getIcon() != null ? imgPath : "", selectedRow, 5);
                    modelThemNhieu.setValueAt(cbTrangThaiNhieu.getSelectedItem().toString(), selectedRow, 6);
                    modelThemNhieu.setValueAt(((LoaiMonAn) cbLoaiMonNhieu.getSelectedItem()).getTenLoaiMonAn(), selectedRow, 7);
                    JOptionPane.showMessageDialog(this, "Đã cập nhật dữ liệu vào bảng!");
                    xoaTrang();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Giá tiền phải là số hợp lệ!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để cập nhật!");
            }
        });
        panelThemNhieu.add(btnLuuVaoBang);

        tabbedPane.addTab("Thêm món", panelQuanLyMon);
        tabbedPane.addTab("Thêm nhiều món", panelThemNhieu);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        btnSua.setEnabled(false);

        try {
			txtMaMon.setText(mad.generateMaMonAn());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ConnectDB.getInstance().connect();
        txtMaMon.setEnabled(false);
        napDuLieuLenTable();
        napLoaiMonAn();
        btnThem.addActionListener(this);
        table.addMouseListener(this);
        btnSua.addActionListener(this);
    }
    public void resetLabelImage() {
        labelImage.setIcon(null); // Xóa hình
        labelImage.setText("Chưa có ảnh"); // Đặt lại chữ mặc định
    }

    public void napDuLieuLenTable() {
        this.dsma = mad.getAllMonAn();// Gọi DAO trả về danh sách món ăn
        for (MonAn mon : this.dsma) {
            model.addRow(new Object[]{
                mon.getMaMonAn(),
                mon.getTenMonAn(),
                format.format(mon.getGiaMonAn()),
                mon.getMoTa(),
                mon.getDonViTinh(),
                mon.getHinhAnh(),
                mon.getTrangThai(),
                mon.getMaLoaiMonAn().getTenLoaiMonAn()
            });
        }
    }
    public void napLoaiMonAn()
    {
    	dslma = lmad.getAllLoaiMonAn();
    	cbDanhMuc.addItem("tất cả");
    	for(LoaiMonAn lma : dslma)
    	{
    		cbLoaiMon.addItem(lma);
    		cbDanhMuc.addItem(lma.getTenLoaiMonAn());
    	}
    }
    public void xoaTrang()
    {
    	txtTenMon.setText("");
    	txtGiaTien.setText("");
    	txtMoTa.setText("");
    	cbTrangThai.setSelectedIndex(0);
    	cbLoaiMon.setSelectedIndex(0);
    	labelImage.setIcon(null);
    	txtDvt.setText("");
    	labelImage.setText("Chưa có ảnh");
    	imgPath = "";
    	try {
			txtMaMon.setText(mad.generateMaMonAn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ConnectDB.getInstance().connect();
    	btnSua.setEnabled(false);
    	btnThem.setEnabled(true);
    }
    public void xoaHetBang()
    {
    	model.setRowCount(0);
    }
    public void themMonAn() {
        try {
            String maMon = txtMaMon.getText().trim();
            String tenMon = txtTenMon.getText().trim();
            String trangThai = cbTrangThai.getSelectedItem().toString();
            LoaiMonAn loaiMon = (LoaiMonAn) cbLoaiMon.getSelectedItem();
            double giaTien = Double.parseDouble(txtGiaTien.getText().trim());
            String moTa = txtMoTa.getText().trim();
            String hinhAnh = imgPath; // xử lý ảnh nếu có
            String donViTinh = txtDvt.getText();
            MonAn mon = new MonAn(maMon, tenMon, giaTien, moTa,donViTinh, hinhAnh, trangThai, loaiMon);

            ConnectDB.getInstance().connect();
            if (mad.themMonAn(mon)) {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!");
                xoaTrang(); // reset form
                xoaHetBang(); // xóa bảng
                napDuLieuLenTable(); // load lại dữ liệu
            } else {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm món ăn: " + e.getMessage());
        }
    }
	
	public void fillFormFromTableRow_MonAn(int rowIndex) {
	    if (rowIndex < 0 || rowIndex >= table.getRowCount()) {
	        return;
	    }
	    txtMaMon.setText(table.getValueAt(rowIndex, 0).toString());
	    txtTenMon.setText(table.getValueAt(rowIndex, 1).toString());
	    txtGiaTien.setText(table.getValueAt(rowIndex, 2).toString());
	    txtMoTa.setText(table.getValueAt(rowIndex, 3).toString());
	    txtDvt.setText(table.getValueAt(rowIndex, 4).toString());
	    String imagePath = table.getValueAt(rowIndex, 5).toString();
	    imgPath = imagePath; // Lưu lại đường dẫn ảnh để sửa nếu cần

	    try {
	        ImageIcon icon = new ImageIcon(imagePath);
	        Image scaledImage = icon.getImage().getScaledInstance(
	                labelImage.getWidth(), labelImage.getHeight(), Image.SCALE_SMOOTH);
	        labelImage.setIcon(new ImageIcon(scaledImage));
	        labelImage.setText("");
	    } catch (Exception e) {
	        labelImage.setIcon(null);
	        labelImage.setText("Không thể hiển thị ảnh");
	    }

	    // Trạng thái
	    String trangThai = table.getValueAt(rowIndex, 6).toString();
	    cbTrangThai.setSelectedItem(trangThai);

	    // Loại món
	    String loaiMonStr = table.getValueAt(rowIndex, 7).toString();
	    for (int i = 0; i < cbLoaiMon.getItemCount(); i++) {
	        LoaiMonAn loai = cbLoaiMon.getItemAt(i);
	        if (loai.toString().equals(loaiMonStr)) {
	            cbLoaiMon.setSelectedIndex(i);
	            break;
	        }
	    }

	    btnSua.setEnabled(true);
	    btnThem.setEnabled(false);
	}
	

	private void chooseAndReadExcel() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
	    int result = chooser.showOpenDialog(this);

	    if (result == JFileChooser.APPROVE_OPTION) {
	        File file = chooser.getSelectedFile();
	        readExcelToTable(file);
	    }
	}

	private void readExcelToTable(File file) {
	    try (FileInputStream fis = new FileInputStream(file);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheetAt(0);
	        modelThemNhieu.setRowCount(0);
	        String[] expectedColumns = {"Mã Món Ăn", "Tên Món Ăn", "Giá Tiền", "Mô Tả", "Đơn Vị Tính", "Hình Ảnh", "Trạng Thái", "Loại Món"};
	        modelThemNhieu.setColumnIdentifiers(expectedColumns);

	        // Lấy mã món ăn mới nhất từ CSDL
	        String maMonAnCuoi = mad.generateMaMonAn();
	        String prefix = maMonAnCuoi.replaceAll("\\d", "");
	        int soHienTai = Integer.parseInt(maMonAnCuoi.replaceAll("\\D", ""));
	        int maGen = soHienTai - 1;

	        for (Row row : sheet) {
	            if (row.getRowNum() == 0) continue;

	            Object[] rowData = new Object[8];
	            String maMoi = String.format("%s%06d", prefix, ++maGen);
	            rowData[0] = maMoi;

	            rowData[1] = getCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)).toString().trim();
	            rowData[2] = getCellValue(row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
	            rowData[3] = getCellValue(row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)).toString().trim();
	            rowData[4] = getCellValue(row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)).toString().trim();
	            rowData[5] = getCellValue(row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)).toString().trim();
	            rowData[6] = getCellValue(row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)).toString().trim();
	            rowData[7] = getCellValue(row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)).toString().trim();

	            if (rowData[1].toString().isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Tên món ăn ở dòng " + (row.getRowNum() + 1) + " trống!");
	                continue;
	            }

	            modelThemNhieu.addRow(rowData);
	        }

	        JOptionPane.showMessageDialog(this, "Đã tải dữ liệu từ file Excel!");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + ex.getMessage());
	    }
	}


	private Object getCellValue(Cell cell) {
	    return switch (cell.getCellType()) {
	        case STRING -> cell.getStringCellValue();
	        case NUMERIC -> cell.getNumericCellValue();
	        case BOOLEAN -> cell.getBooleanCellValue();
	        case FORMULA -> cell.getCellFormula();
	        default -> "";
	    };
	}
	
	
	public void themTatCaMonAn() {
	    try {
	        boolean success = true;
	        for (int i = 0; i < modelThemNhieu.getRowCount(); i++) {
	            String maMon = modelThemNhieu.getValueAt(i, 0).toString();
	            String tenMon = modelThemNhieu.getValueAt(i, 1).toString();
	            if (tenMon.trim().isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Tên món ăn ở dòng " + (i + 1) + " không được để trống!");
	                success = false;
	                continue;
	            }
	            double giaTien;
	            try {
	                giaTien = Double.parseDouble(modelThemNhieu.getValueAt(i, 2).toString());
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "Giá tiền ở dòng " + (i + 1) + " không hợp lệ!");
	                success = false;
	                continue;
	            }
	            String moTa = modelThemNhieu.getValueAt(i, 3).toString();
	            String donViTinh = modelThemNhieu.getValueAt(i, 4).toString();
	            String hinhAnh = modelThemNhieu.getValueAt(i, 5).toString();
	            String trangThai = modelThemNhieu.getValueAt(i, 6).toString();
	            LoaiMonAn loaiMon = new LoaiMonAn(modelThemNhieu.getValueAt(i, 7).toString(),"");
	            
	            MonAn mon = new MonAn(maMon, tenMon, giaTien, moTa, donViTinh, hinhAnh, trangThai, loaiMon);
	            ConnectDB.getInstance().connect();
	            if (!mad.themMonAn(mon)) {
	                success = false;
	            }
	        }
	        if (success) {
	            JOptionPane.showMessageDialog(this, "Thêm tất cả món ăn thành công!");
	            modelThemNhieu.setRowCount(0); 
	            xoaTrang();
	        } else {
	            JOptionPane.showMessageDialog(this, "Có lỗi khi thêm một số món ăn!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Lỗi khi thêm món ăn: " + e.getMessage());
	    }
        try {
            txtMaMonNhieu.setText(mad.generateMaMonAn());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ConnectDB.getInstance().connect();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnThem))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn thêm Món này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			themMonAn();
		}
		if(e.getSource().equals(btnSua))
		{
			if(JOptionPane.showConfirmDialog(this,"Bạn có muốn Sửa Thông Tin Món này không?","Cảnh Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			suaMonAn();
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==1)
		{
			int curr = table.getSelectedRow();
			fillFormFromTableRow_MonAn(curr);
		}
		if(e.getClickCount()==2)
		{
			xoaTrang();
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
	public void suaMonAn() {
	    String maMonAn = txtMaMon.getText();
	    String tenMonAn = txtTenMon.getText();
	    double giaMonAn = Double.parseDouble(txtGiaTien.getText());
	    String moTa = txtMoTa.getText();
	    String donViTinh = txtDvt.getText();
	    String hinhAnh = imgPath;
	    String trangThai = cbTrangThai.getSelectedItem().toString();
	    LoaiMonAn loai = (LoaiMonAn) cbLoaiMon.getSelectedItem();

	    // Tạo đối tượng món ăn mới từ dữ liệu giao diện
	    MonAn mon = new MonAn(maMonAn, tenMonAn, giaMonAn, moTa, donViTinh, hinhAnh, trangThai, loai);
	    // Gọi DAO để sửa thông tin món ăn
	    if (mad.suaMonAn(mon)) {
	        JOptionPane.showMessageDialog(this, "Cập nhật món ăn thành công!");
	        xoaTrang();         // Làm sạch form
	        xoaHetBang();       // Xóa toàn bộ dữ liệu trong bảng hiện tại
	        napDuLieuLenTable();   // Load lại danh sách món ăn
	    } else {
	        JOptionPane.showMessageDialog(this, "Sửa món ăn thất bại!");
	    }
	}

}