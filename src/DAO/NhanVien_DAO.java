package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.LoaiNhanVien;
import Entity.NhanVien;

public class NhanVien_DAO {
	private ArrayList<NhanVien> dsnv;
	public ArrayList<NhanVien> getAllNhanVien()
	{
		try
		{
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="Select * from NhanVien";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			dsnv = new ArrayList<NhanVien>();
			while(rs.next())
			{				
				String maNhanVien = rs.getString(1);
				String tenNhanVien = rs.getString(2);
				int tuoi = rs.getInt(3);
				String maCCCD = rs.getString(4);
				String email = rs.getString(5);	
				boolean gioiTinh = rs.getBoolean(6);
				String trangThai = rs.getString(7);
				float heSoLuong = rs.getFloat(8);
				String maChucVu = rs.getString(9);
				this.dsnv.add(new NhanVien(maNhanVien, tenNhanVien, tuoi, maCCCD, email,trangThai, gioiTinh, heSoLuong, new LoaiNhanVien(maChucVu)));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return this.dsnv;
	}
//	public NhanVien timNhanVien(String maNhanVien)
//	{
//		
//	}
	public boolean themNhanVien(NhanVien nv)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try
		{
			stmt = con.prepareStatement("insert into "+"NhanVien values(?, ?, ?, ?,?,?,?,?,?)");
			stmt.setString(1,nv.getMaNhanVien());
			stmt.setString(2,nv.getTenNhanVien());
			stmt.setInt(3,nv.getTuoi());
			stmt.setString(4,nv.getMaCCCD());
			stmt.setString(5,nv.getEmail());
			stmt.setBoolean(6,nv.isGioiTinh());
			stmt.setString(7,nv.getTrangThai());
			stmt.setFloat(8,nv.getHeSoLuong());
			stmt.setString(9,nv.getMaLoaiNhanVien().getMaLoaiNhanVien());
			n=stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}
	private static final String PREFIX = "NV";
	private static final int LENGTH = 4;

	public String generateMaNhanVien() throws SQLException {
	    String maxMaNV = null;
	    String query = "SELECT MAX(maNhanVien) AS maxMaNV FROM NhanVien";
	    
	    try (Connection conn = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {   
	        if (rs.next()) {
	            maxMaNV = rs.getString("maxMaNV");
	        }
	    }
	    
	    // Nếu chưa có nhân viên nào hoặc giá trị là null hoặc rỗng, bắt đầu từ NV0001
	    if (maxMaNV == null || maxMaNV.isEmpty()) {
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }

	    // Lấy phần số từ mã nhân viên hiện tại và tăng lên 1
	    try {
	        int currentNumber = Integer.parseInt(maxMaNV.substring(PREFIX.length()));
	        int newNumber = currentNumber + 1;
	        
	        // Tạo mã nhân viên mới với độ dài cố định
	        return PREFIX + String.format("%0" + LENGTH + "d", newNumber);
	    } catch (NumberFormatException e) {
	        // Xử lý lỗi nếu phần số không hợp lệ, bắt đầu lại từ NV0001
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }
	}
	public boolean suaNhanVien(NhanVien nv) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        String sql = "UPDATE NhanVien SET tenNhanVien=?, tuoi=?, maCCCD=?, email=?, gioiTinh=?, trangThai=?, heSoLuong=?, maLoaiNhanVien=? WHERE maNhanVien=?";
	        stmt = con.prepareStatement(sql);

	        stmt.setString(1, nv.getTenNhanVien());
	        stmt.setInt(2, nv.getTuoi());
	        stmt.setString(3, nv.getMaCCCD());
	        stmt.setString(4, nv.getEmail());
	        stmt.setBoolean(5, nv.isGioiTinh()); // true = nam, false = nữ
	        stmt.setString(6, nv.getTrangThai());
	        stmt.setFloat(7, nv.getHeSoLuong());
	        stmt.setString(8, nv.getMaLoaiNhanVien().getMaLoaiNhanVien());
	        stmt.setString(9, nv.getMaNhanVien());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return n > 0;
	}
	public ArrayList<NhanVien> getAllNhanVienTheoTen(String tennhanVien)
	{
		try
		{
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="Select * from NhanVien where tenNhanVien LIKE N'%' + ? + N'%'";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,tennhanVien);
			ResultSet rs = stmt.executeQuery();
			dsnv = new ArrayList<NhanVien>();
			while(rs.next())
			{				
				String maNhanVien = rs.getString(1);
				String tenNhanVien = rs.getString(2);
				int tuoi = rs.getInt(3);
				String maCCCD = rs.getString(4);
				String email = rs.getString(5);	
				boolean gioiTinh = rs.getBoolean(6);
				String trangThai = rs.getString(7);
				float heSoLuong = rs.getFloat(8);
				String maChucVu = rs.getString(9);
				this.dsnv.add(new NhanVien(maNhanVien, tenNhanVien, tuoi, maCCCD, email,trangThai, gioiTinh, heSoLuong, new LoaiNhanVien(maChucVu)));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsnv;
	}
	public ArrayList<NhanVien> getAllNhanVienTheoLoai(String loaiNhanVien)
	{
		try
		{
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="Select * from NhanVien where maLoaiNhanVien=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,loaiNhanVien);
			ResultSet rs = stmt.executeQuery();
			dsnv = new ArrayList<NhanVien>();
			while(rs.next())
			{				
				String maNhanVien = rs.getString(1);
				String tenNhanVien = rs.getString(2);
				int tuoi = rs.getInt(3);
				String maCCCD = rs.getString(4);
				String email = rs.getString(5);	
				boolean gioiTinh = rs.getBoolean(6);
				String trangThai = rs.getString(7);
				float heSoLuong = rs.getFloat(8);
				String maChucVu = rs.getString(9);
				this.dsnv.add(new NhanVien(maNhanVien, tenNhanVien, tuoi, maCCCD, email,trangThai, gioiTinh, heSoLuong, new LoaiNhanVien(maChucVu)));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsnv;
	}
}
