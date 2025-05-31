package Entity;

import java.util.Objects;

public class KhachHang {
	private String soDienThoai;
	private String hoTen;
	private String email;
	private float tongDiemTichLuy;
	private float diemTichLuyHienTai;
	private LoaiKhachHang maLoaiKhachHang;
	
	public KhachHang(String soDienThoai, String hoTen) {
		super();
		this.soDienThoai = soDienThoai;
		this.hoTen = hoTen;
	}
	
	public KhachHang(String soDienThoai) {
		super();
		this.soDienThoai = soDienThoai;
	}

	public KhachHang(String soDienThoai, String hoTen, String email, LoaiKhachHang maLoaiKhachHang) {
		super();
		this.soDienThoai = soDienThoai;
		this.hoTen = hoTen;
		this.email = email;
		this.maLoaiKhachHang = maLoaiKhachHang;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public KhachHang(String soDienThoai, String hoTen, String email, float tongDiemTichLuy, float diemTichLuyHienTai,
			LoaiKhachHang maLoaiKhachHang) {
		super();
		this.soDienThoai = soDienThoai;
		this.hoTen = hoTen;
		this.email = email;
		this.tongDiemTichLuy = tongDiemTichLuy;
		this.diemTichLuyHienTai = diemTichLuyHienTai;
		this.maLoaiKhachHang = maLoaiKhachHang;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public float getTongDiemTichLuy() {
		return tongDiemTichLuy;
	}
	public void setTongDiemTichLuy(float tongDiemTichLuy) {
		this.tongDiemTichLuy = tongDiemTichLuy;
	}
	public float getDiemTichLuyHienTai() {
		return diemTichLuyHienTai;
	}
	public void setDiemTichLuyHienTai(float diemTichLuyHienTai) {
		this.diemTichLuyHienTai = diemTichLuyHienTai;
	}
	public LoaiKhachHang getMaLoaiKhachHang() {
		return maLoaiKhachHang;
	}
	public void setMaLoaiKhachHang(LoaiKhachHang maLoaiKhachHang) {
		this.maLoaiKhachHang = maLoaiKhachHang;
	}

	@Override
	public int hashCode() {
		return Objects.hash(soDienThoai);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(soDienThoai, other.soDienThoai);
	}

	@Override
	public String toString() {
		return soDienThoai+";"+ hoTen;

	}
	
	
}
