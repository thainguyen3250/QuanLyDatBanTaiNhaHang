package Entity;

import java.util.Objects;

public class LoaiKhachHang {
	private String maLoaiKhachHang;
	private String tenLoai;
	
	public LoaiKhachHang(String maLoaiKhachHang, String tenLoai) {
		super();
		this.maLoaiKhachHang = maLoaiKhachHang;
		this.tenLoai = tenLoai;
	}
	
	public LoaiKhachHang(String maLoaiKhachHang) {
		super();
		this.maLoaiKhachHang = maLoaiKhachHang;
	}

	public LoaiKhachHang() {
		super();
	}
	public String getMaLoaiKhachHang() {
		return maLoaiKhachHang;
	}
	public String getTenLoai() {
		return tenLoai;
	}
	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maLoaiKhachHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiKhachHang other = (LoaiKhachHang) obj;
		return Objects.equals(maLoaiKhachHang, other.maLoaiKhachHang);
	}
	
}
