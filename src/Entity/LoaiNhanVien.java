package Entity;

import java.util.Objects;

public class LoaiNhanVien {
	private String maLoaiNhanVien;
	private String tenLoai;
	public LoaiNhanVien(String maLoaiNhanVien, String tenLoai) {
		super();
		this.maLoaiNhanVien = maLoaiNhanVien;
		this.tenLoai = tenLoai;
	}
	public LoaiNhanVien(String maLoaiNhanVien) {
		super();
		this.maLoaiNhanVien = maLoaiNhanVien;
	}
	public String getMaLoaiNhanVien() {
		return maLoaiNhanVien;
	}
	public void setMaLoaiNhanVien(String maLoaiNhanVien) {
		this.maLoaiNhanVien = maLoaiNhanVien;
	}
	public String getTenLoai() {
		return tenLoai;
	}
	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maLoaiNhanVien);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiNhanVien other = (LoaiNhanVien) obj;
		return Objects.equals(maLoaiNhanVien, other.maLoaiNhanVien);
	}
	@Override
	public String toString() {
		return tenLoai;
	}
	
}
