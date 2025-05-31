package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.KhachHang;
import Entity.LoaiKhachHang;

public class KhachHang_DAO {
	public ArrayList<KhachHang> dskh ;
	public ArrayList<KhachHang> getALLKhachHang() throws SQLException
	{
		dskh = new ArrayList<KhachHang>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("Select * from KhachHang");
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			String soDienThoai = rs.getString(1);
			String hoTen = rs.getString(2);
			String email = rs.getString(3);
			float tongDiemTichLuy = rs.getFloat(4);
			float diemTichLuyHienTai = rs.getFloat(5);
			String maLoaiKhachHang = rs.getString(6);
			String tenLoai = "Đồng";
			if(maLoaiKhachHang.equals("ML00002"))
			{
				tenLoai = "Bạc";
			}
			if(maLoaiKhachHang.equals("ML00003"))
			{
				tenLoai = "Vàng";
			}
			if(maLoaiKhachHang.equals("ML00004"))
			{
				tenLoai = "Kim Cương";
			}
			dskh.add(new KhachHang(soDienThoai,hoTen,email,tongDiemTichLuy,diemTichLuyHienTai,new LoaiKhachHang(maLoaiKhachHang,tenLoai)));
		}
		return this.dskh;
	}
	public KhachHang timKhachHang(String soDienThoai)
	{
		KhachHang kh = null;
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from KhachHang where soDienThoai=?");	
			stmt.setString(1, soDienThoai);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				String sdt = rs.getString(1);
				String hoTen = rs.getString(2);
				String email = rs.getString(3);
				float tongDiemTichLuy = rs.getFloat(4);
				float diemTichLuyHienTai = rs.getFloat(5);
				String maLoaiKhachHang = rs.getString(6);
				String tenLoai = "Đồng";
				if(maLoaiKhachHang.equals("ML00002"))
				{
					tenLoai = "Bạc";
				}
				if(maLoaiKhachHang.equals("ML00003"))
				{
					tenLoai = "Vàng";
				}
				if(maLoaiKhachHang.equals("ML00004"))
				{
					tenLoai = "Kim Cương";
				}
				kh = new KhachHang(sdt,hoTen,email,tongDiemTichLuy,diemTichLuyHienTai,new LoaiKhachHang(maLoaiKhachHang,tenLoai));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kh;
	}
	public ArrayList<KhachHang> timKhachHangTheoLoai(String loai)
	{
		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from KhachHang where maLoaiKhachHang=?");	
			stmt.setString(1, loai);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				String sdt = rs.getString(1);
				String hoTen = rs.getString(2);
				String email = rs.getString(3);
				float tongDiemTichLuy = rs.getFloat(4);
				float diemTichLuyHienTai = rs.getFloat(5);
				String maLoaiKhachHang = rs.getString(6);
				String tenLoai = "Đồng";
				if(maLoaiKhachHang.equals("ML00002"))
				{
					tenLoai = "Bạc";
				}
				if(maLoaiKhachHang.equals("ML00003"))
				{
					tenLoai = "Vàng";
				}
				if(maLoaiKhachHang.equals("ML00004"))
				{
					tenLoai = "Kim Cương";
				}
				dskh.add( new KhachHang(sdt,hoTen,email,tongDiemTichLuy,diemTichLuyHienTai,new LoaiKhachHang(maLoaiKhachHang,tenLoai)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dskh;
	}
	public boolean themKhachHang(KhachHang kh)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try
		{
			stmt = con.prepareStatement("insert into "+"KhachHang values(?, ?, ?, ?, ?, ?)");
			stmt.setString(1,kh.getSoDienThoai());
			stmt.setString(2,kh.getHoTen());
			stmt.setString(3,kh.getEmail());
			stmt.setFloat(4,kh.getTongDiemTichLuy());
			stmt.setFloat(5,kh.getDiemTichLuyHienTai());
			stmt.setString(6,kh.getMaLoaiKhachHang().getMaLoaiKhachHang());
			n=stmt.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return n>0;
	}
	public boolean suaKhachHang(KhachHang kh)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update KhachHang set hoTen=?,email=? where soDienThoai=?");		
			stmt.setString(1,kh.getHoTen());
			stmt.setString(2,kh.getEmail());
			stmt.setString(3,kh.getSoDienThoai());
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return n > 0; 
	}
	public boolean congDiemKhachHang(KhachHang kh,double diem)
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update KhachHang set diemTichLuyHienTai+=?,tongDiemTichLuy+=?,maLoaiKhachHang=? where soDienThoai=?");		
			stmt.setDouble(1,diem);
			stmt.setDouble(2,diem);
			
			float tongHienTai = (float) (kh.getTongDiemTichLuy()+diem);
			
			String loaikh ="ML00001";
			if(tongHienTai>=200000 && tongHienTai<=800000)
			{
				loaikh="ML00002";
			}
			if(tongHienTai>800000 && tongHienTai<=1500000)
			{
				loaikh="ML00003";
			}
			if(tongHienTai>1500000)
			{
				loaikh="ML00004";
			}
			stmt.setString(3,loaikh);
			
			stmt.setString(4,kh.getSoDienThoai());
			n = stmt.executeUpdate();						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return n > 0; 
	}
	public int tinhSoKhachVangLai(int thang, int nam) {
	    int tongKhachVangLai = 0;

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT COUNT(*) AS tongVangLai " +
	                     "FROM HoaDon " +
	                     "WHERE MONTH(ngayLapHoaDon) = ? AND YEAR(ngayLapHoaDon) = ? " +
	                     "AND soDienThoai IS NULL";
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            tongKhachVangLai = rs.getInt("tongVangLai");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tongKhachVangLai;
	}

	public int tinhSoKhachQuen(int thang, int nam) {
	    int tongKhachVangLai = 0;

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT COUNT(*) AS tongVangLai " +
	                     "FROM HoaDon " +
	                     "WHERE MONTH(ngayLapHoaDon) = ? AND YEAR(ngayLapHoaDon) = ? " +
	                     "AND soDienThoai IS NOT NULL";
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            tongKhachVangLai = rs.getInt("tongVangLai");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tongKhachVangLai;
	}
	public Object[][] layTop10KhachHangTheoDoanhThu(int thang, int nam) {
	    ArrayList<Object[]> ds = new ArrayList<>();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_Top10KhachHangTheoDoanhThu ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String hoTen = rs.getString("hoTen");
	            int tongDoanhThu = (int) Math.round(rs.getDouble("tongDoanhThu"));

	            ds.add(new Object[]{hoTen, tongDoanhThu});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public double tinhTongDoanhThuTop10KhachHang(int thang, int nam) {
	    double tongDoanhThu = 0;

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_TongDoanhThuTop10KhachHang ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            tongDoanhThu = rs.getDouble("tongDoanhThuTop10");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tongDoanhThu;
	}

	public Object[][] demSoLuotLoaiKhachHangTheoThangNam(int thang, int nam) {
	    ArrayList<Object[]> ds = new ArrayList<>();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_DemSoLuotLoaiKhachHangTheoThangNam ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String tenLoai = rs.getString("tenLoai");
	            int soLuot = rs.getInt("soLuotXuatHien");
	            ds.add(new Object[]{tenLoai, soLuot});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}

}
