package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ConnectDB.ConnectDB;
import DAO.Ban_DAO;
import DAO.KhuVuc_DAO;
import EDIT.EditScrollPane;
import Entity.Ban;
import Entity.KhuVuc;

public class DanhMucBanAnGUI extends JPanel implements MouseListener{

    private RoundedTextField tableIdField;
    private RoundedTextField tableNameField;
    private RoundedTextField seatsField;
    private RoundedTextField statusField;
    private RoundedTextField areaIdField;
    private RoundedTextField areaNameField;

    private JTable table;
    private DefaultTableModel tableModel;
    private Ban_DAO bd;
    private ArrayList<Ban> dsb;
	private JComboBox<String> filterRoomCombo;
	private ArrayList<KhuVuc> dskv;
	private KhuVuc_DAO kvd;
    
    
    static class RoundedTextField extends JTextField {
        private int padding = 8;
        private Color borderColor = Color.decode("#B0BEC5");
        private Color focusedBorderColor = Color.decode("#42A5F5");

        public RoundedTextField(String text) {
            super(text);
            setOpaque(false);
            setBorder(new RoundedBorder());
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setForeground(Color.decode("#212121"));
            setBackground(Color.WHITE);
            setCaretColor(Color.decode("#42A5F5"));

            addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    borderColor = focusedBorderColor;
                    repaint();
                }

                public void focusLost(java.awt.event.FocusEvent evt) {
                    borderColor = Color.decode("#B0BEC5");
                    repaint();
                }
            });

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!isFocusOwner()) {
                        borderColor = Color.decode("#90CAF9");
                        repaint();
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!isFocusOwner()) {
                        borderColor = Color.decode("#B0BEC5");
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
            super.paintComponent(g2);
            g2.dispose();
        }

        private class RoundedBorder extends AbstractBorder {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(borderColor);
                g2.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(padding, padding, padding, padding);
            }

            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                insets.left = insets.right = padding;
                insets.top = insets.bottom = padding;
                return insets;
            }
        }
    }

    public DanhMucBanAnGUI() {
    	ConnectDB.getInstance().connect();
    	bd = new Ban_DAO();
    	kvd = new KhuVuc_DAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#F5F5F5"));

        JPanel topPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(Color.decode("#E3F2FD"));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        tableIdField = new RoundedTextField("B00039");
        tableNameField = new RoundedTextField("");
        seatsField = new RoundedTextField("");
        statusField = new RoundedTextField("");
        areaIdField = new RoundedTextField("KV00001");
        areaNameField = new RoundedTextField("Khu Vực 1");

        topPanel.add(new JLabel("Mã bàn:")).setFont(labelFont);
        topPanel.add(tableIdField);
        topPanel.add(new JLabel("Tên bàn:")).setFont(labelFont);
        topPanel.add(tableNameField);
        topPanel.add(new JLabel("Số chỗ ngồi:")).setFont(labelFont);
        topPanel.add(seatsField);
        topPanel.add(new JLabel("Trạng thái:")).setFont(labelFont);
        topPanel.add(statusField);
        topPanel.add(new JLabel("Mã Khu vực:")).setFont(labelFont);
        topPanel.add(areaIdField);
        topPanel.add(new JLabel("Tên Khu vực:")).setFont(labelFont);
        topPanel.add(areaNameField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.decode("#E3F2FD"));


        JLabel filterLabel = new JLabel("Lọc bàn:");
        filterLabel.setFont(labelFont);
        buttonPanel.add(filterLabel);

        filterRoomCombo = new JComboBox<String>();
        filterRoomCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterRoomCombo.setBackground(Color.WHITE);
        filterRoomCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        buttonPanel.add(filterRoomCombo);
        filterRoomCombo.addItem("Tất cả");
        filterRoomCombo.addItem("Đã đặt trước");
        filterRoomCombo.addItem("Có khách");
        filterRoomCombo.addItem("Trống");
        filterRoomCombo.addItem("Đã gọi món");
        filterRoomCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String trangThaiPhong = (String) filterRoomCombo.getSelectedItem();
				if(trangThaiPhong.equals("Tất cả"))
				{
					if(table.getRowCount()!=0)
					tableModel.setRowCount(0);
					napBan();
				}
				if(trangThaiPhong.equals("Đã đặt trước"))
				{
					if(table.getRowCount()!=0)
					tableModel.setRowCount(0);
					dsb = bd.timBanDatTruoc();
					for (Ban ban : dsb) {
			        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
			        	String maBan = ban.getMaBan();
			        	String tenBan = ban.getTenBan();
			        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
			        	String trangThai = ban.getTrangThai();
			        	
			        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
			        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
			        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
			        	tableModel.addRow(rows);
			        }
				}
				if(trangThaiPhong.equals("Có khách"))
				{
					if(table.getRowCount()!=0)
					tableModel.setRowCount(0);
						dsb = bd.timBanDangCoKhach();
						for (Ban ban : dsb) {
				        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
				        	String maBan = ban.getMaBan();
				        	String tenBan = ban.getTenBan();
				        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
				        	String trangThai = ban.getTrangThai();
				        	
				        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
				        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
				        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
				        	tableModel.addRow(rows);
				        }
				}
				if(trangThaiPhong.equals("Trống"))
				{
					if(table.getRowCount()!=0)
						tableModel.setRowCount(0);
						dsb = bd.timBanChuaDat();
						for (Ban ban : dsb) {
				        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
				        	String maBan = ban.getMaBan();
				        	String tenBan = ban.getTenBan();
				        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
				        	String trangThai = ban.getTrangThai();
				        	
				        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
				        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
				        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
				        	tableModel.addRow(rows);
				        }
				}
				if(trangThaiPhong.equals("Đã gọi món"))
				{
					if(table.getRowCount()!=0)
						tableModel.setRowCount(0);
						dsb = bd.timBanDaGoiMon();
						for (Ban ban : dsb) {
				        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
				        	String maBan = ban.getMaBan();
				        	String tenBan = ban.getTenBan();
				        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
				        	String trangThai = ban.getTrangThai();
				        	
				        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
				        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
				        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
				        	tableModel.addRow(rows);
				        }
				}
				
			}
		});
        

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.decode("#E3F2FD"));
        northPanel.add(topPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH); 
        table = new JTable(tableModel= new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Mã Khu vực","Tên Khu Vực"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // không cho chỉnh sửa bất kỳ ô nào
            }
        });
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.decode("#CFD8DC"));
        table.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#1976D2"));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(BorderFactory.createLineBorder(Color.decode("#1976D2")));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#E8EAF6"));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new EditScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        add(scrollPane, BorderLayout.CENTER);
        napBan();
        table.addMouseListener(this);
        voHieuHoaTruongNhap();
    }
    public void napBan()
    {
        dsb = bd.getAllBan();
        for (Ban ban : dsb) {
        	//{"Mã Bàn", "Tên Bàn", "Số Chỗ Ngồi", "Trạng Thái","Tên Phòng","Mã Phòng","Tên Khu Vực"}
        	String maBan = ban.getMaBan();
        	String tenBan = ban.getTenBan();
        	String soChoNgoi = ban.getSoLuongChoNgoi()+"";
        	String trangThai = ban.getTrangThai();
        	
        	String maKhuVuc = ban.getMaKhuVuc().getMaKhuVuc();
        	String tenKhuVuc = ban.getMaKhuVuc().getTenKhuVuc();
        	String[] rows = {maBan,tenBan,soChoNgoi,trangThai,maKhuVuc,tenKhuVuc};
        	tableModel.addRow(rows);
        }
    }
    public void voHieuHoaTruongNhap() {
        tableIdField.setEnabled(false);
        tableNameField.setEnabled(false);
        seatsField.setEnabled(false);
        statusField.setEnabled(false);
        areaIdField.setEnabled(false);
        areaNameField.setEnabled(false);
    }
    public void xoaTrang() {
        
        tableIdField.setText("");
        tableNameField.setText("");
        seatsField.setText("");
        statusField.setText("");
        areaIdField.setText("");
        areaNameField.setText("");

        filterRoomCombo.setSelectedIndex(0);


        // Bỏ chọn các dòng trong bảng
        table.clearSelection();
    }
    public void dayLen(int curr) {
        tableIdField.setText(table.getValueAt(curr, 0).toString());
        tableNameField.setText(table.getValueAt(curr, 1).toString());
        seatsField.setText(table.getValueAt(curr, 2).toString());
        statusField.setText(table.getValueAt(curr, 3).toString());

        // Chuyển đổi khu vực từ table -> combo box
        String maKhuVuc = table.getValueAt(curr, 4).toString();
        areaIdField.setText(maKhuVuc);
        areaNameField.setText(table.getValueAt(curr, 5).toString());
        filterRoomCombo.setSelectedItem(areaNameField.getText());

    }
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==1)
		{
			dayLen(table.getSelectedRow());
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
}

