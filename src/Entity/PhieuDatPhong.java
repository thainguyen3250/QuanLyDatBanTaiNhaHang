package Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class PhieuDatPhong {
	private String maPhieuDatPhong;
	private LocalDateTime ngayDatPhong;
	private LocalDateTime ngayNhanPhong;
	private String ghiChu;
	
	private String diaChiQuan;
	private String soDienThoaiNhaHang;
	private Phong maPhong;
	private NhanVien maNhanVien;
	private KhachHang soDienThoai;
	public PhieuDatPhong(String maPhieuDatPhong, LocalDateTime ngayDatPhong, LocalDateTime ngayNhanPhong, String ghiChu,
			String diaChiQuan, String soDienThoaiNhaHang, Phong maPhong, NhanVien maNhanVien, KhachHang soDienThoai) {
		super();
		this.maPhieuDatPhong = maPhieuDatPhong;
		this.ngayDatPhong = ngayDatPhong;
		this.ngayNhanPhong = ngayNhanPhong;
		this.ghiChu = ghiChu;
		this.diaChiQuan = diaChiQuan;
		this.soDienThoaiNhaHang = soDienThoaiNhaHang;
		this.maPhong = maPhong;
		this.maNhanVien = maNhanVien;
		this.soDienThoai = soDienThoai;
	}
	public String getMaPhieuDatPhong() {
		return maPhieuDatPhong;
	}
	public LocalDateTime getNgayDatPhong() {
		return ngayDatPhong;
	}
	public void setNgayDatPhong(LocalDateTime ngayDatPhong) {
		this.ngayDatPhong = ngayDatPhong;
	}
	public LocalDateTime getNgayNhanPhong() {
		return ngayNhanPhong;
	}
	public void setNgayNhanPhong(LocalDateTime ngayNhanPhong) {
		this.ngayNhanPhong = ngayNhanPhong;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getDiaChiQuan() {
		return diaChiQuan;
	}
	public void setDiaChiQuan(String diaChiQuan) {
		this.diaChiQuan = diaChiQuan;
	}
	public String getSoDienThoaiNhaHang() {
		return soDienThoaiNhaHang;
	}
	public void setSoDienThoaiNhaHang(String soDienThoaiNhaHang) {
		this.soDienThoaiNhaHang = soDienThoaiNhaHang;
	}
	public Phong getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(Phong maPhong) {
		this.maPhong = maPhong;
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
	@Override
	public int hashCode() {
		return Objects.hash(maPhieuDatPhong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuDatPhong other = (PhieuDatPhong) obj;
		return Objects.equals(maPhieuDatPhong, other.maPhieuDatPhong);
	}
	
}
