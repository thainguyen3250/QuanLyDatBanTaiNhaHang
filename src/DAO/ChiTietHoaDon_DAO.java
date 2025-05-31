package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.MonAn;

public class ChiTietHoaDon_DAO {
	private ArrayList<ChiTietHoaDon> cthd;
	public boolean themChiTietHoaDon(ChiTietHoaDon cthd) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        String sql = "INSERT INTO ChiTietHoaDon(maHoaDon, maMonAn, soLuong, donGia) VALUES (?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, cthd.getMaHoaDon().getMaHoaDon());
	        stmt.setString(2, cthd.getMaMonAn().getMaMonAn());
	        stmt.setInt(3, cthd.getSoLuong());
	        stmt.setFloat(4, (float) cthd.getDonGia());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return n > 0;
	}
	public ArrayList<ChiTietHoaDon> timCTHD(String maHoaDon) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    ArrayList<ChiTietHoaDon> dscthd= new ArrayList<ChiTietHoaDon>(); 
	    try {
	        String sql = "select  cthd.maHoaDon, ma.maMonAn,ma.tenMonAn, cthd.soLuong, cthd.donGia \r\n"
	        		+ "from ChiTietHoaDon cthd \r\n"
	        		+ "inner join MonAn ma\r\n"
	        		+ "on cthd.maMonAn=ma.maMonAn\r\n"
	        		+ "where maHoaDon =?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1,maHoaDon);
	        ResultSet rs = stmt.executeQuery();
	        while(rs.next())
	        {
	        	dscthd.add(new ChiTietHoaDon(new HoaDon(rs.getString(1)),new MonAn(rs.getString(2),rs.getString(3)),rs.getInt(4),rs.getDouble(5)));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return dscthd;
	}
}
