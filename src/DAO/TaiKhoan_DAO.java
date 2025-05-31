package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.LoaiNhanVien;
import Entity.NhanVien;
import Entity.TaiKhoan;

public class TaiKhoan_DAO {
	private ArrayList<TaiKhoan> dstk;
	 public ArrayList<TaiKhoan> getAllTaiKhoan() {
	        ArrayList<TaiKhoan> dstk = new ArrayList<>();
	        try {
	            Connection con = ConnectDB.getInstance().getConnection();
	            CallableStatement stmt = con.prepareCall("{CALL sp_LayTatCaTaiKhoan}");
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                // Thông tin loại nhân viên
	                LoaiNhanVien lnv = new LoaiNhanVien(
	                        rs.getString("MaLoaiNhanVien"),
	                        rs.getString("TenLoaiNhanVien")
	                );

	                // Thông tin nhân viên
	                NhanVien nv = new NhanVien(
	                        rs.getString("MaNhanVien"),
	                        rs.getString("TenNhanVien"),
	                        rs.getInt("Tuoi"),
	                        rs.getString("MaCCCD"),
	                        rs.getString("Email"),
	                        rs.getString("trangThai"),
	                        rs.getBoolean("GioiTinh"),
	                        rs.getFloat("HeSoLuong"),
	                        lnv
	                );

	                // Thông tin tài khoản
	                TaiKhoan tk = new TaiKhoan(
	                        rs.getString("TenTaiKhoan"),
	                        rs.getString("MatKhau"),
	                        rs.getBoolean("TrangThaiTaiKhoan"),
	                        rs.getTimestamp("NgayTaoTaiKhoan").toLocalDateTime(),
	                        nv
	                );

	                dstk.add(tk);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return dstk;
	    }
	public TaiKhoan timTaiKhoan(String tenTaiKhoan)
	{
		TaiKhoan tk = null;
		try
		{
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimKiemTaiKhoan @tenTaiKhoan=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tenTaiKhoan.trim());
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				tk = new TaiKhoan(
						rs.getString(1),rs.getString(2),rs.getBoolean(3),rs.getTimestamp(4).toLocalDateTime(),
						new NhanVien(rs.getString(5),rs.getString(6),new LoaiNhanVien(rs.getString(7),rs.getString(8))));
			}	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return tk;
	}
	public boolean themTaiKhoan(TaiKhoan tk)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try
		{
			stmt = con.prepareStatement("insert into "+"TaiKhoan values(?, ?, ?, ? ,?)");
			stmt.setString(1,tk.getTenTaiKhoan());
			stmt.setString(2,tk.getMatKhau());
			stmt.setBoolean(3,tk.isTrangThai());
			LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
			stmt.setTimestamp(4,timestamp);
			stmt.setString(5,tk.getMaNhanVien().getMaNhanVien());
			n=stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}
	
	private static final String PREFIX = "TK";
	private static final int LENGTH = 5;
	
	public String generateTenTaiKhoan() throws SQLException {
	    String maxTenTK = null;
	    String query = "SELECT MAX(tenTaiKhoan) AS maxTenTK FROM TaiKhoan";
	    
	    try (Connection conn = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {   
	        if (rs.next()) {
	            maxTenTK = rs.getString("maxTenTK");
	        }
	    }
	    
	    // Nếu chưa có tài khoản nào hoặc giá trị là null hoặc rỗng, bắt đầu từ TK00001
	    if (maxTenTK == null || maxTenTK.isEmpty()) {
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }

	    // Lấy phần số từ mã tài khoản hiện tại và tăng lên 1
	    try {
	        int currentNumber = Integer.parseInt(maxTenTK.substring(PREFIX.length()));
	        int newNumber = currentNumber + 1;
	        
	        // Tạo mã tài khoản mới với độ dài cố định
	        return PREFIX + String.format("%0" + LENGTH + "d", newNumber);
	    } catch (NumberFormatException e) {
	        // Xử lý lỗi nếu phần số không hợp lệ, bắt đầu lại từ TK00001
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }
	}
	public boolean suaMatKhau(TaiKhoan tk,String newPass)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try
		{
			stmt = con.prepareStatement("update TaiKhoan set matKhau=? where tenTaiKhoan=?");
			stmt.setString(1,newPass);
			stmt.setString(2,tk.getTenTaiKhoan());
			n=stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}

}
