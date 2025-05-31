package Entity;

import java.time.LocalDateTime;

public class TaiKhoan {
	private String tenTaiKhoan;
	private String matKhau;
	private boolean trangThai;
	private LocalDateTime ngayTao;
	private NhanVien maNhanVien;
	
	public TaiKhoan(String tenTaiKhoan, String matKhau, boolean trangThai, LocalDateTime ngayTao, NhanVien maNhanVien) {
		super();
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
		this.trangThai = trangThai;
		this.ngayTao = ngayTao;
		this.maNhanVien = maNhanVien;
	}
	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}
	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public LocalDateTime getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(LocalDateTime ngayTao) {
		this.ngayTao = ngayTao;
	}
	public NhanVien getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(NhanVien maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	
}
