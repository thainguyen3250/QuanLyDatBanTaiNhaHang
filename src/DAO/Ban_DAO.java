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
import Entity.Ban;
import Entity.KhuVuc;
import Entity.Phong;

public class Ban_DAO {
	public ArrayList<Ban> dsb;
	public ArrayList<Ban> getAllBan()
	{
		try
		{
			dsb = new ArrayList<Ban>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_LayHetBan";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{				
				dsb.add(new Ban(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return this.dsb;
	}
	public ArrayList<Ban> timBanTheoMaKhuVuc(String maKhuVuc)
	{
		ArrayList<Ban> dsb = null;
		try
		{
			dsb = new ArrayList<Ban>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimBanTheoMaKhuVuc @maKhuVuc=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,maKhuVuc.trim());
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsb.add(new Ban(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsb;
	}
	public boolean setTrangThaiBan(String maban,String trangThai)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Ban set trangThai=? where maBan=?");		
			stmt.setString(1,trangThai.trim());
			stmt.setString(2,maban);
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return n > 0; 
	}
	public ArrayList<Ban> timBanDangCoKhach() {
	    ArrayList<Ban> dsb = new ArrayList<>();
	    Connection con = null;
	    CallableStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "{call sp_TimBanDaDat}";
	        stmt = con.prepareCall(sql);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maBan = rs.getString("maBan");
	            String tenBan = rs.getString("tenBan");
	            int soLuongChoNgoi = rs.getInt("soLuongChoNgoi");
	            String trangThai = rs.getString("trangThai");
	            String maKhuVuc = rs.getString("maKhuVuc");
	            String tenKhuVuc = rs.getString("tenKhuVuc");
	            String loaiKhuVuc = rs.getString("loaiKhuVuc");
	            int soLuongPhong = rs.getInt("soLuongPhong");

	            // Tạo đối tượng khu vực
	            KhuVuc kv = new KhuVuc(maKhuVuc, tenKhuVuc, loaiKhuVuc, soLuongPhong);


	            // Tạo đối tượng bàn có chứa phòng
	            Ban b = new Ban(maBan, tenBan, soLuongChoNgoi, trangThai, kv);

	            dsb.add(b);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return dsb;
	}

	public ArrayList<Ban> timBanChuaDat()
	{
		ArrayList<Ban> dsb = new ArrayList<>();
	    Connection con = null;
	    CallableStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "{call sp_TimBanChuaDat}";
	        stmt = con.prepareCall(sql);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	        	String maBan = rs.getString("maBan");
	            String tenBan = rs.getString("tenBan");
	            int soLuongChoNgoi = rs.getInt("soLuongChoNgoi");
	            String trangThai = rs.getString("trangThai");
	            String maKhuVuc = rs.getString("maKhuVuc");
	            String tenKhuVuc = rs.getString("tenKhuVuc");
	            String loaiKhuVuc = rs.getString("loaiKhuVuc");
	            int soLuongPhong = rs.getInt("soLuongPhong");

	            // Tạo đối tượng khu vực
	            KhuVuc kv = new KhuVuc(maKhuVuc, tenKhuVuc, loaiKhuVuc, soLuongPhong);


	            // Tạo đối tượng bàn có chứa phòng
	            Ban b = new Ban(maBan, tenBan, soLuongChoNgoi, trangThai, kv);
	            dsb.add(b);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return dsb;
	}
	public ArrayList<Ban> timBanDatTruoc()
	{
		ArrayList<Ban> dsb = new ArrayList<>();
	    Connection con = null;
	    CallableStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "sp_TimBanTheoTrangThai @trangThai=?";
	        stmt = con.prepareCall(sql);
	        stmt.setString(1,"Đã đặt trước");
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	        	String maBan = rs.getString("maBan");
	            String tenBan = rs.getString("tenBan");
	            int soLuongChoNgoi = rs.getInt("soLuongChoNgoi");
	            String trangThai = rs.getString("trangThai");
	            String maKhuVuc = rs.getString("maKhuVuc");
	            String tenKhuVuc = rs.getString("tenKhuVuc");
	            String loaiKhuVuc = rs.getString("loaiKhuVuc");
	            int soLuongPhong = rs.getInt("soLuongPhong");

	            // Tạo đối tượng khu vực
	            KhuVuc kv = new KhuVuc(maKhuVuc, tenKhuVuc, loaiKhuVuc, soLuongPhong);


	            // Tạo đối tượng bàn có chứa phòng
	            Ban b = new Ban(maBan, tenBan, soLuongChoNgoi, trangThai, kv);
	            dsb.add(b);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return dsb;
	}
	public ArrayList<Ban> timBanDaGoiMon()
	{
		ArrayList<Ban> dsb = new ArrayList<>();
	    Connection con = null;
	    CallableStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "{call sp_TimBanDaGoiMon}";
	        stmt = con.prepareCall(sql);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maBan = rs.getString("maBan");
	            String tenBan = rs.getString("tenBan");
	            int soLuongChoNgoi = rs.getInt("soLuongChoNgoi");
	            String trangThai = rs.getString("trangThai");
	            String maKhuVuc = rs.getString("maKhuVuc");
	            String tenKhuVuc = rs.getString("tenKhuVuc");
	            String loaiKhuVuc = rs.getString("loaiKhuVuc");
	            int soLuongPhong = rs.getInt("soLuongPhong");

	            // Tạo đối tượng khu vực
	            KhuVuc kv = new KhuVuc(maKhuVuc, tenKhuVuc, loaiKhuVuc, soLuongPhong);


	            // Tạo đối tượng bàn có chứa phòng
	            Ban b = new Ban(maBan, tenBan, soLuongChoNgoi, trangThai, kv);

	            dsb.add(b);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return dsb;
	}
	private static final String PREFIX = "B";
	private static final int LENGTH = 6;

	public String generateMaBan() throws SQLException {
	    String maxMaBan = null;
	    String query = "SELECT MAX(maBan) AS maxMaBan FROM Ban"; // Cập nhật tên bảng và cột phù hợp với CSDL

	    try (Connection conn = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        if (rs.next()) {
	            maxMaBan = rs.getString("maxMaBan");
	        }
	    }

	    // Nếu chưa có bàn ăn nào hoặc giá trị là null/rỗng, bắt đầu từ B001
	    if (maxMaBan == null || maxMaBan.isEmpty()) {
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }

	    // Lấy phần số từ mã bàn hiện tại và tăng lên 1
	    try {
	        int currentNumber = Integer.parseInt(maxMaBan.substring(PREFIX.length()));
	        int newNumber = currentNumber + 1;

	        // Tạo mã bàn mới với độ dài cố định
	        return PREFIX + String.format("%0" + LENGTH + "d", newNumber);
	    } catch (NumberFormatException e) {
	        // Nếu có lỗi, bắt đầu lại từ B001
	        return PREFIX + String.format("%0" + LENGTH + "d", 1);
	    }
	}
	public boolean themBan(Ban ban)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try
		{
			stmt = con.prepareStatement("insert into "+"Ban values(?, ?, ?, ?, ?)");
			stmt.setString(1,ban.getMaBan());
			stmt.setString(2,ban.getTenBan());
			stmt.setInt(3,ban.getSoLuongChoNgoi());
			stmt.setString(4,ban.getTrangThai());
			stmt.setString(5,ban.getMaKhuVuc().getMaKhuVuc());
			n=stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}
	public boolean capNhatBan(Ban ban)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Ban set tenBan=?,soLuongChoNgoi=?,maKhuVuc=? where maBan=?");		
			stmt.setString(1,ban.getTenBan());
			stmt.setInt(2,ban.getSoLuongChoNgoi());
			stmt.setString(3,ban.getMaKhuVuc().getMaKhuVuc());
			stmt.setString(4,ban.getMaBan());
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return n > 0; 
	}
	public int getTongSoBanTrong()
	{
		int banTrong=0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "sp_TongSoBanTrong";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()==false)
			{
				return banTrong;
			}
			else
			{
				banTrong =rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return banTrong;
	}
	public int getTongSoBanDatTruoc()
	{
		int banTrong=0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "sp_TongSoBanDatTruoc";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()==false)
			{
				return banTrong;
			}
			else
			{
				banTrong =rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return banTrong;
	}
	public ArrayList<Ban> timBanTheoChoNgoi(int min, int max)
	{
		ArrayList<Ban> dsb = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "sp_timBanTheoSoCho ?,?";
	        stmt = con.prepareStatement(sql);
			stmt.setInt(1,min);
			stmt.setInt(2,max);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maBan = rs.getString("maBan");
	            String tenBan = rs.getString("tenBan");
	            int soLuongChoNgoi = rs.getInt("soLuongChoNgoi");
	            String trangThai = rs.getString("trangThai");
	            String maKhuVuc = rs.getString("maKhuVuc");
	            String tenKhuVuc = rs.getString("tenKhuVuc");
	            String loaiKhuVuc = rs.getString("loaiKhuVuc");
	            int soLuongPhong = rs.getInt("soLuongPhong");

	            // Tạo đối tượng khu vực
	            KhuVuc kv = new KhuVuc(maKhuVuc, tenKhuVuc, loaiKhuVuc, soLuongPhong);


	            // Tạo đối tượng bàn có chứa phòng
	            Ban b = new Ban(maBan, tenBan, soLuongChoNgoi, trangThai, kv);

	            dsb.add(b);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return dsb;
	}
}
