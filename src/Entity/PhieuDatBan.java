package Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class PhieuDatBan {
	private String maPhieuDatBan;
	private LocalDateTime ngayDatBan;
	private LocalDateTime ngayNhanBan;
	private String ghiChu;
	
	private String diaChiQuan;
	private String soDienThoaiNhaHang;
	private Ban maBan;
	private NhanVien maNhanVien;
	private KhachHang soDienThoai;
	
	
	public PhieuDatBan(String maPhieuDatBan, LocalDateTime ngayDatBan, LocalDateTime ngayNhanBan, String ghiChu,
			String diaChiQuan, String soDienThoaiNhaHang, Ban maBan, NhanVien maNhanVien, KhachHang soDienThoai) {
		super();
		this.maPhieuDatBan = maPhieuDatBan;
		this.ngayDatBan = ngayDatBan;
		this.ngayNhanBan = ngayNhanBan;
		this.ghiChu = ghiChu;
		this.diaChiQuan = diaChiQuan;
		this.soDienThoaiNhaHang = soDienThoaiNhaHang;
		this.maBan = maBan;
		this.maNhanVien = maNhanVien;
		this.soDienThoai = soDienThoai;
	}
	
	
	public PhieuDatBan(String maPhieuDatBan) {
		super();
		this.maPhieuDatBan = maPhieuDatBan;
	}


	public String getGhiChu() {
		return ghiChu;
	}


	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}


	public String getMaPhieuDatBan() {
		return maPhieuDatBan;
	}
	public LocalDateTime getNgayDatBan() {
		return ngayDatBan;
	}
	public void setNgayDatBan(LocalDateTime ngayDatBan) {
		this.ngayDatBan = ngayDatBan;
	}
	public LocalDateTime getNgayNhanBan() {
		return ngayNhanBan;
	}
	public void setNgayNhanBan(LocalDateTime ngayNhanBan) {
		this.ngayNhanBan = ngayNhanBan;
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
	public Ban getMaBan() {
		return maBan;
	}
	public void setMaBan(Ban maBan) {
		this.maBan = maBan;
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
		return Objects.hash(maPhieuDatBan);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuDatBan other = (PhieuDatBan) obj;
		return Objects.equals(maPhieuDatBan, other.maPhieuDatBan);
	}
	
	
}
