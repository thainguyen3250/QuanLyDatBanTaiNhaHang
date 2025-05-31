package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.KhachHang;
import Entity.KhuVuc;
import Entity.LoaiNhanVien;
import Entity.NhanVien;
import Entity.Phong;

public class Phong_DAO {
	private ArrayList<Phong> dsp=null;
	public ArrayList<Phong> getAllPhong()
	{
		try
		{
			this.dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_LayHetPhong";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return this.dsp;
	}
	
	public ArrayList<Phong> timPhongTheoMaKhuVuc(String maKhuVuc)
	{
		ArrayList<Phong> dsp = null;
		try
		{
			dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimPhongTheoKhuVuc @maKhuVuc=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,maKhuVuc.trim());
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsp;
	}
	public ArrayList<Phong> timPhongDangCoKhach()
	{
		ArrayList<Phong> dsp = null;
		try
		{
			dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimPhongDaDat";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsp;
	}
	public ArrayList<Phong> timPhongChuaDat()
	{
		ArrayList<Phong> dsp = null;
		try
		{
			dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimPhongTheoTrangThai @trangThai=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"Trống");
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsp;
	}
	public ArrayList<Phong> timPhongDatTruoc()
	{
		ArrayList<Phong> dsp = null;
		try
		{
			dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimPhongTheoTrangThai @trangThai=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"Đã đặt trước");
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsp;
	}
	public ArrayList<Phong> timPhongDaGoiMon()
	{
		ArrayList<Phong> dsp = null;
		try
		{
			dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_TimPhongTheoTrangThai @trangThai=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"Đã gọi món");
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsp;
	}
	public boolean themPhong(Phong ph)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try
		{
			stmt = con.prepareStatement("insert into "+"Phong values(?, ?, ?, ?,?)");
			stmt.setString(1,ph.getMaPhong());
			stmt.setString(2,ph.getTenPhong());
			stmt.setInt(3,ph.getSoLuongChoNgoi());
			stmt.setString(4,ph.getTrangThai());
			stmt.setString(5,ph.getMaKhuVuc().getMaKhuVuc());
			n=stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}
	public boolean setTrangThaiPhong(String maPhong,String trangThai)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Phong set trangThai=? where maPhong=?");		
			stmt.setString(1,trangThai.trim());
			stmt.setString(2,maPhong.trim());
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return n > 0; 
	}
	public boolean suaPhong(Phong ph)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Phong set tenPhong=?,soLuongChoNgoi=?,maKhuVuc=? where maPhong=?");		
			stmt.setString(1,ph.getTenPhong());
			stmt.setInt(2,ph.getSoLuongChoNgoi());
			stmt.setString(3,ph.getMaKhuVuc().getMaKhuVuc());
			stmt.setString(4,ph.getMaPhong());
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return n > 0; 
	}
	private static final String PREFIX_PHONG = "P";
	private static final int LENGTH_PHONG = 8;

	public String generateMaPhong() throws SQLException {
	    String maxMaPhong = null;
	    String query = "SELECT MAX(maPhong) AS maxMaPhong FROM Phong";
	    
	    try (Connection conn = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {   
	        if (rs.next()) {
	            maxMaPhong = rs.getString("maxMaPhong");
	        }
	    }
	    
	    // Nếu chưa có phòng nào hoặc giá trị là null hoặc rỗng, bắt đầu từ P00000001
	    if (maxMaPhong == null || maxMaPhong.isEmpty()) {
	        return PREFIX_PHONG + String.format("%0" + LENGTH_PHONG + "d", 1);
	    }

	    // Lấy phần số từ mã phòng hiện tại và tăng lên 1
	    try {
	        int currentNumber = Integer.parseInt(maxMaPhong.substring(PREFIX_PHONG.length()));
	        int newNumber = currentNumber + 1;
	        
	        // Tạo mã phòng mới với độ dài cố định
	        return PREFIX_PHONG + String.format("%0" + LENGTH_PHONG + "d", newNumber);
	    } catch (NumberFormatException e) {
	        // Nếu mã hiện tại sai định dạng, bắt đầu lại từ P00000001
	        return PREFIX_PHONG + String.format("%0" + LENGTH_PHONG + "d", 1);
	    }
	}
	public int getTongSoPhongTrong()
	{
		int phongTrong=0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "sp_TongSoPhongTrong";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()==false)
			{
				return phongTrong;
			}
			else
			{
				phongTrong =rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return phongTrong;
	}
	public ArrayList<Phong> timPhongTheoChoNgoi(int min, int max)
	{
		ArrayList<Phong> dsp = null;
		try
		{
			dsp = new ArrayList<Phong>();
			Connection con = ConnectDB.getInstance().getConnection();
			String sql ="sp_timPhongTheoSoCho ?,?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1,min);
			stmt.setInt(2,max);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{				
				dsp.add(new Phong(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4),new KhuVuc(rs.getString(5),rs.getString(6),rs.getString(7))));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return dsp;
	}
}
