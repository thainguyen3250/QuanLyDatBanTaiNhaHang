package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import ConnectDB.ConnectDB;
import Entity.HoaDon;
import Entity.LoaiMonAn;
import Entity.MonAn;

public class MonAn_DAO {
	private ArrayList<MonAn> dsma;
	
	public double tongDoanhThuTheoThang =0;
	public int tongMonBan =0;
	public ArrayList<MonAn> getAllMonAn() {
	    dsma = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "sp_LayTatCaMonAn";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maMonAn = rs.getString(1);
	            String tenMonAn = rs.getString(2);
	            double giaMonAn = rs.getDouble(3);
	            String moTa = rs.getString(4);
	            String donViTinh = rs.getString(5);
	            String hinhAnh = rs.getString(6);
	            String trangThai =rs.getString(7);
	            String maLoaiMonAn = rs.getString(8);
	            String tenLoaiMonAn = rs.getString(9);

	            LoaiMonAn loaiMon = new LoaiMonAn(maLoaiMonAn, tenLoaiMonAn);
	            MonAn mon = new MonAn(maMonAn, tenMonAn, giaMonAn, moTa, donViTinh, hinhAnh,trangThai, loaiMon);
	            this.dsma.add(mon);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return this.dsma;
	}
	private static final String PREFIX_MA = "MA";
	private static final int LENGTH_MA = 6;

	public String generateMaMonAn() throws SQLException {
	    String maxMaMonAn = null;
	    String query = "SELECT MAX(maMonAn) AS maxMaMonAn FROM MonAn"; // Tên bảng và cột theo CSDL của bạn

	    try (Connection conn = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        if (rs.next()) {
	            maxMaMonAn = rs.getString("maxMaMonAn");
	        }
	    }

	    if (maxMaMonAn == null || maxMaMonAn.isEmpty()) {
	        return PREFIX_MA + String.format("%0" + LENGTH_MA + "d", 1); // MA000001
	    }

	    try {
	        int currentNumber = Integer.parseInt(maxMaMonAn.substring(PREFIX_MA.length()));
	        int newNumber = currentNumber + 1;

	        return PREFIX_MA + String.format("%0" + LENGTH_MA + "d", newNumber);
	    } catch (NumberFormatException e) {
	        return PREFIX_MA + String.format("%0" + LENGTH_MA + "d", 1);
	    }
	}
	public boolean themMonAn(MonAn monAn) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        stmt = con.prepareStatement("INSERT INTO MonAn(maMonAn,tenMonAn,giaMonAn,moTa,donViTinh,hinhAnh,trangThai,maLoaiMonAn) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

	        stmt.setString(1, monAn.getMaMonAn());
	        stmt.setString(2, monAn.getTenMonAn());
	        stmt.setDouble(3, monAn.getGiaMonAn());
	        stmt.setString(4, monAn.getMoTa());
	        stmt.setString(5, monAn.getDonViTinh());
	        stmt.setString(6, monAn.getHinhAnh());
	        stmt.setString(7, monAn.getTrangThai());
	        stmt.setString(8, monAn.getMaLoaiMonAn().getMaLoaiMonAn()); // Giả sử getMaLoaiMonAn() trả về 1 đối tượng LoaiMonAn

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return n > 0;
	}
	public boolean suaMonAn(MonAn mon) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement(
	            "UPDATE MonAn SET tenMonAn = ?, giaMonAn = ?, moTa = ?, donViTinh = ?, hinhAnh = ?, trangThai = ?, maLoaiMonAn = ? WHERE maMonAn = ?"
	        );
	        stmt.setString(1, mon.getTenMonAn());
	        stmt.setDouble(2, mon.getGiaMonAn());
	        stmt.setString(3, mon.getMoTa());
	        stmt.setString(4, mon.getDonViTinh());
	        stmt.setString(5, mon.getHinhAnh());
	        stmt.setString(6, mon.getTrangThai());
	        stmt.setString(7, mon.getMaLoaiMonAn().getMaLoaiMonAn()); // assuming LoaiMonAn has getMaLoaiMonAn()
	        stmt.setString(8, mon.getMaMonAn());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public Object[][] layTop10MonAnBanChay() {
	    ArrayList<Object[]> ds = new ArrayList<>();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_Top10MonAnBanChayNhat";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String tenMonAn = rs.getString("tenMonAn");
	            String hinhAnh = rs.getString("hinhAnh");
	            int tongSoLuong = rs.getInt("tongSoLuongBan");

	            ds.add(new Object[]{tenMonAn, hinhAnh, tongSoLuong});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public Object[][] layThongTinDoanhThuTheoThangNam(int thang, int nam) {
	    ArrayList<Object[]> ds = new ArrayList<>();
	    this.tongDoanhThuTheoThang=0;
	    this.tongMonBan=0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_DoanhThuTheoThangNam ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setInt(1, thang);
	        stmt.setInt(2, nam);

	        ResultSet rs = stmt.executeQuery();
	        int count =0;
	        while (rs.next()) {
	        	if(count==10)
	        		break;
	            String tenMonAn = rs.getString("TenMonAn");
	            String tenDanhMuc = rs.getString("TenDanhMuc");
	            int soLuongBan = rs.getInt("SoLuongBan");
	            this.tongMonBan+=soLuongBan;
	            double doanhThu = rs.getDouble("DoanhThu");
	            this.tongDoanhThuTheoThang+=doanhThu;
	            ds.add(new Object[]{tenMonAn, tenDanhMuc, soLuongBan, doanhThu});
	            count++;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public ArrayList<MonAn> timMonAnTheoTen(String tenMonan) {
		ArrayList<MonAn> dsma = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "exec sp_timMonAnTheoTen ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, tenMonan);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maMonAn = rs.getString(1);
	            String tenMonAn = rs.getString(2);
	            double giaMonAn = rs.getDouble(3);
	            String moTa = rs.getString(4);
	            String donViTinh = rs.getString(5);
	            String hinhAnh = rs.getString(6);
	            String trangThai =rs.getString(7);
	            String maLoaiMonAn = rs.getString(8);
	            String tenLoaiMonAn = rs.getString(9);

	            LoaiMonAn loaiMon = new LoaiMonAn(maLoaiMonAn, tenLoaiMonAn);
	            MonAn mon = new MonAn(maMonAn, tenMonAn, giaMonAn, moTa, donViTinh, hinhAnh,trangThai, loaiMon);
	            dsma.add(mon);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsma;
	}
	public ArrayList<MonAn> timMonAnTheoLoai(String loaiMonan) {
		ArrayList<MonAn> dsma = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "exec sp_timMonAnTheoLoai ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, loaiMonan);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maMonAn = rs.getString(1);
	            String tenMonAn = rs.getString(2);
	            double giaMonAn = rs.getDouble(3);
	            String moTa = rs.getString(4);
	            String donViTinh = rs.getString(5);
	            String hinhAnh = rs.getString(6);
	            String trangThai =rs.getString(7);
	            String maLoaiMonAn = rs.getString(8);
	            String tenLoaiMonAn = rs.getString(9);

	            LoaiMonAn loaiMon = new LoaiMonAn(maLoaiMonAn, tenLoaiMonAn);
	            MonAn mon = new MonAn(maMonAn, tenMonAn, giaMonAn, moTa, donViTinh, hinhAnh,trangThai, loaiMon);
	            dsma.add(mon);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsma;
	}
	

}
