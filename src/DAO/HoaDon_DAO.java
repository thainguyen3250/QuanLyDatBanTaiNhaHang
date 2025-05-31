package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import ConnectDB.ConnectDB;
import Entity.Ban;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.Phong;

public class HoaDon_DAO {
	ArrayList<HoaDon> dshd;
	public double tongDoanhThu =0;
	public int tongHoaDon =0;
	public boolean themHoaDon(HoaDon hd) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;

	    try {
	        String sql = "INSERT INTO HoaDon(maHoaDon, ngayLapHoaDon, diaChiNhaHang, thueVAT, tienKhachDua, phiDichVu, soDienThoaiNhaHang, maNhanVien, soDienThoai, maBan,maPhong) "
	                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, hd.getMaHoaDon());
            Timestamp timestamp = Timestamp.valueOf(hd.getNgayLapHoaDon());
	        stmt.setTimestamp(2,timestamp);
	        stmt.setString(3, hd.getDiaChiNhaHang());
	        stmt.setFloat(4, hd.getThueVAT());
	        stmt.setFloat(5, (float) hd.getTienKhachDua());
	        stmt.setFloat(6, (float) hd.getPhiDichVu());
	        stmt.setString(7, hd.getSoDienThoaiNhaHang());
	        stmt.setString(8, hd.getMaNhanVien().getMaNhanVien());
	        if (hd.getSoDienThoai()==null)
	        {
	        	stmt.setNull(9, Types.NVARCHAR);
	        }
	        else
	        {
	        	stmt.setString(9, hd.getSoDienThoai().getSoDienThoai());
	        }
	        if (hd.getMaBan()==null)
	        {
	        	stmt.setNull(10, Types.NVARCHAR);
	        }
	        else
	        {
	        	stmt.setString(10, hd.getMaBan().getMaBan());
	        }
	        if (hd.getMaPhong()==null)
	        {
	        	stmt.setNull(11, Types.NVARCHAR);
	        }
	        else
	        {
	        	stmt.setString(11, hd.getMaPhong().getMaPhong());
	        }     
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
	public String generateMaHoaDon() {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    String newMa = "HD0000001"; // Mã mặc định nếu bảng trống
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT TOP 1 maHoaDon FROM HoaDon ORDER BY maHoaDon DESC";
	        stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);

	        if (rs.next()) {
	            String lastMa = rs.getString("maHoaDon"); // e.g. HD0000025
	            int number = Integer.parseInt(lastMa.substring(2)); // lấy phần số
	            number++; // tăng lên 1
	            newMa = String.format("HD%07d", number); // format lại: HD0000026
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

	    return newMa;
	}
	public int getDoanhThuTheoNgay(LocalDateTime homnay)
	{
		int tongTien=0;
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = "TinhTongHoaDonTrongNgay @ngay=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setTimestamp(1, Timestamp.valueOf(homnay));
			ResultSet rs = stmt.executeQuery();
			if(rs.next()==false)
			{
				return tongTien;
			}
			else
			{
				tongTien  = (int) Math.round(rs.getDouble(1));;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return tongTien;
	}
	public Object[][] layDoanhThu7NgayGanNhat() {
	    ArrayList<Object[]> ds = new ArrayList<>();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "sp_DoanhThu7NgayGanNhat";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Date ngay = rs.getDate("Ngay");
	            int doanhThuInt = (int) Math.round(rs.getDouble("TongDoanhThu"));
	            // Chuyển ngày thành T2, T3, ...
	            String thu = getThuVietnamese(ngay.toLocalDate());

	            ds.add(new Object[]{thu, doanhThuInt});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public Object[][] layDoanhThuTheoNgay(java.util.Date ngayBD, java.util.Date ngayKetThuc) {
	    ArrayList<Object[]> ds = new ArrayList<>();
	    tongDoanhThu =0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_TongDoanhThuTheoNgay ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);

	        // Set tham số đầu vào cho procedure
	        stmt.setDate(1, new java.sql.Date(ngayBD.getTime()));
	        stmt.setDate(2, new java.sql.Date(ngayKetThuc.getTime()));

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Date ngay = rs.getDate("Ngay");
	            double doanhThu = rs.getDouble("DoanhThu");
	            tongDoanhThu+=doanhThu;
	            ds.add(new Object[]{ngay, doanhThu});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public Object[][] layDoanhThuTheoThang(java.util.Date tuNgay, java.util.Date denNgay) {
	    ArrayList<Object[]> ds = new ArrayList<>();
	    tongDoanhThu =0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_TongDoanhThuTheoThang ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
	        stmt.setDate(2, new java.sql.Date(denNgay.getTime()));

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String thang = rs.getString("Thang"); // Ví dụ: "04/2025"
	            double doanhThu = rs.getDouble("DoanhThu");
	            tongDoanhThu+=doanhThu;
	            ds.add(new Object[]{thang, doanhThu});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public Object[][] layDoanhThuTheoNam(java.util.Date tuNgay, java.util.Date denNgay) {
	    ArrayList<Object[]> ds = new ArrayList<>();
	    tongDoanhThu =0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_TongDoanhThuTheoNam ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);

	        stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
	        stmt.setDate(2, new java.sql.Date(denNgay.getTime()));

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            int nam = rs.getInt("Nam");
	            
	            double doanhThu = rs.getDouble("DoanhThu");
	            tongDoanhThu+=doanhThu;
	            ds.add(new Object[]{nam, doanhThu});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}
	public int demSoLuongHoaDonTheoKhoang(java.util.Date startDate, java.util.Date endDate) {
		tongHoaDon = 0;

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_DemSoLuongHoaDonTheoKhoangThoiGian ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        
	        stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
	        stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
	        
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	tongHoaDon = rs.getInt("soLuongHoaDon");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return tongHoaDon;
	}
	public Object[][] layTop5LoaiMonAnTheoThoiGian(java.util.Date tuNgay, java.util.Date denNgay) {
	    ArrayList<Object[]> ds = new ArrayList<>();

	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "EXEC sp_Top5LoaiMonAn_BanRa_TheoThoiGian ?, ?";
	        PreparedStatement stmt = con.prepareStatement(sql);

	        // Set tham số ngày
	        stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
	        stmt.setDate(2, new java.sql.Date(denNgay.getTime()));

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String tenLoai = rs.getString("tenLoaiMonAn");
	            int soLuongBan = rs.getInt("soLuongBan");

	            ds.add(new Object[]{tenLoai, soLuongBan});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new Object[0][0]);
	}

	private String getThuVietnamese(LocalDate date) {
	    DayOfWeek day = date.getDayOfWeek();
	    switch (day) {
	        case MONDAY: return "T2";
	        case TUESDAY: return "T3";
	        case WEDNESDAY: return "T4";
	        case THURSDAY: return "T5";
	        case FRIDAY: return "T6";
	        case SATURDAY: return "T7";
	        case SUNDAY: return "CN";
	        default: return "";
	    }
	    }
	public String[][] getThongTinHoaDon() {
		
		ArrayList<String[]> ds = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "sp_LayThongTinHoaDon";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maHoaDon = rs.getString(1);
	            String tenNhanVien = rs.getString(2);
	            String tenKhachHang = "Khách Vãng Lai";
	            if(rs.getString(3)!=null)
	            {
	            	tenKhachHang = rs.getString(3);
	            }
	            Timestamp timestamp = rs.getTimestamp("ngayLapHoaDon");  // ví dụ lấy từ ResultSet
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String ngayTao = timestamp.toLocalDateTime().format(formatter);
	            
	            NumberFormat formatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	            String tongTien = formatTien.format(rs.getDouble(5));
	            ds.add(new String[] {maHoaDon,tenNhanVien,tenKhachHang,ngayTao,tongTien});
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ds.toArray(new String[0][0]);
	}
	public String[][] timHoaDonTheoMa(String mahd) {
		
		ArrayList<String[]> ds = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "exec sp_LayThongTinHoaDonTheoMaHoaDon ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, mahd);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maHoaDon = rs.getString(1);
	            String tenNhanVien = rs.getString(2);
	            String tenKhachHang = "Khách Vãng Lai";
	            if(rs.getString(3)!=null)
	            {
	            	tenKhachHang = rs.getString(3);
	            }
	            Timestamp timestamp = rs.getTimestamp("ngayLapHoaDon");
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String ngayTao = timestamp.toLocalDateTime().format(formatter);
	            
	            NumberFormat formatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	            String tongTien = formatTien.format(rs.getDouble(5));
	            ds.add(new String[] {maHoaDon,tenNhanVien,tenKhachHang,ngayTao,tongTien});
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ds.toArray(new String[0][0]);
	}
	public String[][] timHoaDonTheoNgay(String ngay) {

	    ArrayList<String[]> ds = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "exec sp_LayThongTinHoaDonTheoNgay ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, ngay); // định dạng: yyyy-MM-dd

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String maHoaDon = rs.getString(1);
	            String tenNhanVien = rs.getString(2);
	            String tenKhachHang = "Khách Vãng Lai";
	            if (rs.getString(3) != null) {
	                tenKhachHang = rs.getString(3);
	            }

	            Timestamp timestamp = rs.getTimestamp("ngayLapHoaDon");
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String ngayTao = timestamp.toLocalDateTime().format(formatter);

	            NumberFormat formatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	            String tongTien = formatTien.format(rs.getDouble(5));

	            ds.add(new String[]{maHoaDon, tenNhanVien, tenKhachHang, ngayTao, tongTien});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ds.toArray(new String[0][0]);
	}
	public HoaDon timHoaDonTheoMa1(String maHoaDon) {
	    HoaDon hoaDon = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "exec sp_LayThongTinHoaDonTheoMaHoaDon1 ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHoaDon);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String maHD = rs.getString("maHoaDon");

	            
	            String maNV = rs.getString("maNhanVien");
	            String tenNV = rs.getString("tenNhanVien");
	            NhanVien nv = new NhanVien(maNV, tenNV); // Giả sử constructor này tồn tại

	            KhachHang kh =null;
	            if(rs.getString("soDienThoaiKhach")!=null)
	            {
	            	String sdtKH = rs.getString("soDienThoaiKhach");
		            String tenKH = rs.getString("tenKhachHang");
		            kh = new KhachHang(sdtKH, tenKH);
	            }
	             // Giả sử constructor này tồn tại

	            Timestamp ts = rs.getTimestamp("ngayLapHoaDon");
	            LocalDateTime ngayLap = ts.toLocalDateTime();

	            double tienKhachDua = rs.getDouble("tienKhachDua");
	            Ban maBan = new Ban(rs.getString("maBan"));
	            Phong maPhong = new  Phong(rs.getString("maPhong"));
	            
	            double phiDichVu = rs.getDouble("phiDichVu");
	            
	            hoaDon = new HoaDon(maHD, nv, kh, ngayLap, tienKhachDua, maBan, maPhong,phiDichVu);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return hoaDon;
	}

}
