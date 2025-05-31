package Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HoaDon {
    private String maHoaDon;
    private LocalDateTime ngayLapHoaDon;
    private String diaChiNhaHang;
    private float thueVAT;
    private double tienKhachDua;
    private String soDienThoaiNhaHang;
    
    private double phiDichVu;
    private double tongHoaDonTruocThue;
    private double tongHoaDonSauThue;
    private double tienKhuyenMai;
    private double tongHoaDon;
    private double tienTraKhach;
    private NhanVien maNhanVien;
    private KhachHang soDienThoai;
    private Ban maBan;
    private Phong maPhong;
	public HoaDon(String maHoaDon, LocalDateTime ngayLapHoaDon, String diaChiNhaHang,float thueVAT,double tienKhachDua,
			float phiDichVu, String soDienThoaiNhaHang,double khuyenMai ) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.diaChiNhaHang = diaChiNhaHang;
		this.thueVAT = thueVAT;
		this.tienKhachDua = tienKhachDua;
		this.phiDichVu = phiDichVu;
		this.soDienThoaiNhaHang = soDienThoaiNhaHang;
		this.tienKhuyenMai =khuyenMai;
	}
	
    public HoaDon(String maHoaDon, LocalDateTime ngayLapHoaDon, String diaChiNhaHang, float thueVAT,
			double tienKhachDua, String soDienThoaiNhaHang, double phiDichVu, double tienKhuyenMai, NhanVien maNhanVien,
			KhachHang soDienThoai, Ban maBan, Phong maPhong) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.diaChiNhaHang = diaChiNhaHang;
		this.thueVAT = thueVAT;
		this.tienKhachDua = tienKhachDua;
		this.soDienThoaiNhaHang = soDienThoaiNhaHang;
		this.phiDichVu = phiDichVu;
		this.tienKhuyenMai = tienKhuyenMai;
		this.maNhanVien = maNhanVien;
		this.soDienThoai = soDienThoai;
		this.maBan = maBan;
		this.maPhong = maPhong;
	}
    public HoaDon(String maHoaDon, NhanVien nhanVien, KhachHang khachHang,
            LocalDateTime ngayLap, double tienKhachDua, Ban maBan, Phong maPhong,double phiDichVu) {
			  this.maHoaDon = maHoaDon;
			  this.maNhanVien = nhanVien;
			  this.soDienThoai = khachHang;
			  this.ngayLapHoaDon = ngayLap;
			  this.tienKhachDua = tienKhachDua;
			  this.maBan = maBan;
			  this.maPhong = maPhong;
			  this.phiDichVu= phiDichVu;
}
	public HoaDon(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public LocalDateTime getNgayLapHoaDon() {
		return ngayLapHoaDon;
	}

	public void setNgayLapHoaDon(LocalDateTime ngayLapHoaDon) {
		this.ngayLapHoaDon = ngayLapHoaDon;
	}

	public String getDiaChiNhaHang() {
		return diaChiNhaHang;
	}

	public void setDiaChiNhaHang(String diaChiNhaHang) {
		this.diaChiNhaHang = diaChiNhaHang;
	}

	public float getThueVAT() {
		return thueVAT;
	}

	public void setThueVAT(float thueVAT) {
		this.thueVAT = thueVAT;
	}

	public double getTienKhachDua() {
		return tienKhachDua;
	}

	public void setTienKhachDua(double tienKhachDua) {
		this.tienKhachDua = tienKhachDua;
	}

	public String getSoDienThoaiNhaHang() {
		return soDienThoaiNhaHang;
	}

	public void setSoDienThoaiNhaHang(String soDienThoaiNhaHang) {
		this.soDienThoaiNhaHang = soDienThoaiNhaHang;
	}

	public double getPhiDichVu() {
		return phiDichVu;
	}

	public void setPhiDichVu(double phiDichVu) {
		this.phiDichVu = phiDichVu;
	}

	public double getTienKhuyenMai() {
		return tienKhuyenMai;
	}

	public void setTienKhuyenMai(double tienKhuyenMai) {
		this.tienKhuyenMai = tienKhuyenMai;
	}

	public double getTienTraKhach() {
		return tienTraKhach;
	}

	public void setTienTraKhach(double tienTraKhach) {
		this.tienTraKhach = tienTraKhach;
	}

	public NhanVien getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(NhanVien maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public KhachHang getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(KhachHang soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public Ban getMaBan() {
		return maBan;
	}

	public void setMaBan(Ban maBan) {
		this.maBan = maBan;
	}

	public Phong getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(Phong maPhong) {
		this.maPhong = maPhong;
	}

	public double tinhTongHoaDonTruocThue(ArrayList<ChiTietHoaDon> cthd)
    {
		this.tongHoaDonTruocThue=0;
    	for (ChiTietHoaDon cth: cthd)
    	{
    		this.tongHoaDonTruocThue +=cth.getTongTien();
    	}
    	return this.tongHoaDonTruocThue+=this.phiDichVu;
    }
    public double tinhTongHoaDonSauThue()
    {
    	return this.tongHoaDonSauThue = this.tongHoaDonTruocThue*this.thueVAT+this.tongHoaDonTruocThue;
    }
    public void tinhTienHoaDon()
    {
    	this.tongHoaDon = this.tongHoaDonSauThue +this.phiDichVu - this.tienKhuyenMai;
    }
    public void tinhTienTraKhach()
    {
    	this.tienTraKhach= this.tienKhachDua - this.tongHoaDon;
    }
    
}
