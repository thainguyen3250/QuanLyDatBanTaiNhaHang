

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
import DAO.KhuVuc_DAO;
import DAO.Phong_DAO;
import EDIT.EditScrollPane;
import Entity.KhuVuc;
import Entity.Phong;

public class DanhMucPhongAnGUI extends JPanel implements MouseListener{

    private ArrayList<Phong> dsp;
    private Phong_DAO phd;
    private ArrayList<KhuVuc> dskv;
    private KhuVuc_DAO kvd;

    // Toàn cục cho các thành phần giao diện
    private RoundedTextField roomIdField, roomNameField, seatsField, statusField;
    private JComboBox<String> areaCombo, filterRoomCombo;
    private JTable table;
    private DefaultTableModel tableModel;

    // Custom JTextField with rounded corners
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

    public DanhMucPhongAnGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.decode("#F5F5F5"));
        ConnectDB.getInstance().connect();
        phd = new Phong_DAO();
        kvd = new KhuVuc_DAO();
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(Color.decode("#E3F2FD"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Mã phòng:") {{ setFont(labelFont); }}, gbc);

        gbc.gridx = 1;
        roomIdField = new RoundedTextField("P00000");
        roomIdField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(roomIdField, gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Tên phòng:") {{ setFont(labelFont); }}, gbc);

        gbc.gridx = 3;
        roomNameField = new RoundedTextField("");
        roomNameField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(roomNameField, gbc);

        gbc.gridx = 4;
        topPanel.add(new JLabel("Số lượng chỗ:") {{ setFont(labelFont); }}, gbc);

        gbc.gridx = 5;
        seatsField = new RoundedTextField("");
        seatsField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(seatsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(new JLabel("Khu vực:") {{ setFont(labelFont); }}, gbc);

        gbc.gridx = 1;
        areaCombo = new JComboBox<String>();
        areaCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaCombo.setBackground(Color.WHITE);
        areaCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
        areaCombo.setPreferredSize(new Dimension(150, 35));
        topPanel.add(areaCombo, gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Trạng Thái:") {{ setFont(labelFont); }}, gbc);

        gbc.gridx = 3;
        statusField = new RoundedTextField("");
        statusField.setPreferredSize(new Dimension(150, 35));
        topPanel.add(statusField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.decode("#E3F2FD"));

        

        buttonPanel.add(new JLabel("Lọc phòng:") {{ setFont(labelFont); }});

        filterRoomCombo = new JComboBox<>();
        filterRoomCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterRoomCombo.setBackground(Color.WHITE);
        filterRoomCombo.setBorder(BorderFactory.createLineBorder(Color.decode("#B0BEC5"), 1));
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
					napPhong();
				}
				if(trangThaiPhong.equals("Đã đặt trước"))
				{
					if(table.getRowCount()!=0)
						tableModel.setRowCount(0);
					dsp = phd.timPhongDatTruoc();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		tableModel.addRow(rows);
			    	}
				}
				if(trangThaiPhong.equals("Có khách"))
				{
					if(table.getRowCount()!=0)
						tableModel.setRowCount(0);
					dsp = phd.timPhongDangCoKhach();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		tableModel.addRow(rows);
			    	}
				}
				if(trangThaiPhong.equals("Trống"))
				{
					if(table.getRowCount()!=0)
						tableModel.setRowCount(0);
					dsp = phd.timPhongChuaDat();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		tableModel.addRow(rows);
			    	}
				}
				if(trangThaiPhong.equals("Đã gọi món"))
				{
					if(table.getRowCount()!=0)
						tableModel.setRowCount(0);
					dsp = phd.timPhongDaGoiMon();
					for(Phong p : dsp)
			    	{
			    		String[] rows = {p.getMaPhong(),p.getTenPhong(),p.getSoLuongChoNgoi()+"",p.getTrangThai(),p.getMaKhuVuc().getMaKhuVuc(),p.getMaKhuVuc().getTenKhuVuc()};
			    		tableModel.addRow(rows);
			    	}
				}
				
			}
		});
        buttonPanel.add(filterRoomCombo);

       

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.decode("#E3F2FD"));
        northPanel.add(topPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã Phòng", "Tên Phòng", "Số Lượng Chỗ", "Trạng Thái", "Mã Khu Vực", "Tên Khu Vực"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
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
        napPhong();
        table.addMouseListener(this);
    }

    public void napPhong() {
        dsp = phd.getAllPhong();
        tableModel.setRowCount(0); // Clear old data
        for (Phong p : dsp) {
            String[] rows = {
                p.getMaPhong(),
                p.getTenPhong(),
                String.valueOf(p.getSoLuongChoNgoi()),
                p.getTrangThai(),
                p.getMaKhuVuc().getMaKhuVuc(),
                p.getMaKhuVuc().getTenKhuVuc()
            };
            tableModel.addRow(rows);
        }
    }
    public void napDuLieuTuBang(int row) {
        roomIdField.setText(table.getValueAt(row, 0).toString());
        roomNameField.setText(table.getValueAt(row, 1).toString());
        seatsField.setText(table.getValueAt(row, 2).toString());
        statusField.setText(table.getValueAt(row, 3).toString());

        String maKhuVuc = table.getValueAt(row, 5).toString();
        
        areaCombo.setSelectedItem(maKhuVuc);
    }
    public void xoaTrang()
    {
    	roomIdField.setText("");
        roomNameField.setText("");
        seatsField.setText("");
        statusField.setText("");
        areaCombo.setSelectedIndex(0);
    }
	@Override
	public void mouseClicked(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            if (e.getClickCount() == 2) {
                // Khi nhấp 2 lần, xóa trắng
                xoaTrang();
            } else if (e.getClickCount() == 1) {
                // Khi nhấp 1 lần, nạp dữ liệu
                napDuLieuTuBang(selectedRow);
            }
     }}
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
