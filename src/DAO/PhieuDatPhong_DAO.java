package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.Ban;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.PhieuDatBan;
import Entity.PhieuDatPhong;
import Entity.Phong;

public class PhieuDatPhong_DAO {
	private ArrayList<PhieuDatPhong> dspdp;
	public ArrayList<PhieuDatPhong> getAllPhieuDatPhong() {
	    dspdp = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM PhieuDatPhong";
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while (rs.next()) {
	            String maPhieu = rs.getString("maPhieuDatPhong");
	            LocalDateTime ngayDat = rs.getTimestamp("ngayDatPhong").toLocalDateTime();
	            LocalDateTime ngayNhan = rs.getTimestamp("ngayNhanPhong").toLocalDateTime();
	            String diaChi = rs.getString("diaChiNhaHang");
	            String sdtNhaHang = rs.getString("soDienThoaiNhaHang");
	            String ghiChu = rs.getString("ghiChu");
	            String maNhanVien = rs.getString("maNhanVien");
	            String sdtKhach = rs.getString("soDienThoai");
	            String maPhong = rs.getString("maPhong");

	            // Giả sử bạn có các constructor tương ứng
	            NhanVien nv = new NhanVien(maNhanVien);  // hoặc truy vấn để lấy đầy đủ info nếu cần
	            KhachHang kh = new KhachHang(sdtKhach);
	            Phong p = new Phong(maPhong);
	            
	            PhieuDatPhong pdp = new PhieuDatPhong(maPhieu, ngayDat, ngayNhan, ghiChu, diaChi, sdtNhaHang,p,nv,kh);
	            this.dspdp.add(pdp);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return this.dspdp;
	}
	public boolean themPhieuDatPhong(PhieuDatPhong phieu) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        String sql = "INSERT INTO PhieuDatPhong (maPhieuDatPhong, ngayDatPhong, ngayNhanPhong, diaChiNhaHang, soDienThoaiNhaHang, ghiChu, maNhanVien, soDienThoai, maPhong) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);

	        stmt.setString(1, phieu.getMaPhieuDatPhong());
	        stmt.setTimestamp(2, Timestamp.valueOf(phieu.getNgayDatPhong()));
	        stmt.setTimestamp(3, Timestamp.valueOf(phieu.getNgayNhanPhong()));
	        stmt.setString(4, phieu.getDiaChiQuan());
	        stmt.setString(5, phieu.getSoDienThoaiNhaHang());
	        stmt.setString(6, phieu.getGhiChu());
	        stmt.setString(7, phieu.getMaNhanVien().getMaNhanVien());
	        if(phieu.getSoDienThoai()==null)
	        {
	        	stmt.setNull(8, java.sql.Types.NVARCHAR);
	        }else
	        {
	        	stmt.setString(8, phieu.getSoDienThoai().getSoDienThoai());
	        }
	        
	        stmt.setString(9, phieu.getMaPhong().getMaPhong());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return n > 0;
	}
	private static final String PREFIX = "PDP";
	private static final int LENGTH = 6;

	public String generateMaPhieuDatPhong() throws SQLException {
	    String maxMaPhieu = null;
	    // Câu truy vấn lấy mã phiếu đặt lớn nhất hiện có
	    String query = "SELECT MAX(maPhieuDatPhong) AS maxMaPhieu FROM PhieuDatPhong";
	    
	    try (Connection conn = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        if (rs.next()) {
	            maxMaPhieu = rs.getString("maxMaPhieu");
	        }
	    }
	    
	    // Nếu chưa có phiếu đặt nào hoặc giá trị null/rỗng, bắt đầu từ PDB000001
	    if (maxMaPhieu == null || maxMaPhieu.isEmpty()) {
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }
	    
	    try {
	        // Lấy phần số từ mã phiếu đặt hiện tại và tăng lên 1
	        int currentNumber = Integer.parseInt(maxMaPhieu.substring(PREFIX.length()));
	        int newNumber = currentNumber + 1;
	        return PREFIX + String.format("%0" + LENGTH + "d", newNumber);
	    } catch (NumberFormatException e) {
	        // Nếu có lỗi trong việc parse, bắt đầu lại từ PDB000001
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }
	}


}
