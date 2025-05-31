package Entity;

import java.util.Objects;

public class KhuVuc {
	private String maKhuVuc;
	private String tenKhuVuc;
	private String loaiKhuVuc;
	private int soLuongPhong;
	public KhuVuc(String maKhuVuc) {
		super();
		this.maKhuVuc = maKhuVuc;
	}
	public KhuVuc(String maKhuVuc, String tenKhuVuc) {
		super();
		this.maKhuVuc = maKhuVuc;
		this.tenKhuVuc = tenKhuVuc;
	}
	public KhuVuc(String maKhuVuc, String tenKhuVuc, String loaiKhuVuc) {
		super();
		this.maKhuVuc = maKhuVuc;
		this.tenKhuVuc = tenKhuVuc;
		this.loaiKhuVuc = loaiKhuVuc;
	}
	
	public KhuVuc(String maKhuVuc, String tenKhuVuc, String loaiKhuVuc, int soLuongPhong) {
		super();
		this.maKhuVuc = maKhuVuc;
		this.tenKhuVuc = tenKhuVuc;
		this.loaiKhuVuc = loaiKhuVuc;
		this.soLuongPhong = soLuongPhong;
	}
	
	public int getSoLuongPhong() {
		return soLuongPhong;
	}
	public void setSoLuongPhong(int soLuongPhong) {
		this.soLuongPhong = soLuongPhong;
	}
	public String getMaKhuVuc() {
		return maKhuVuc;
	}
	public void setMaKhuVuc(String maKhuVuc) {
		this.maKhuVuc = maKhuVuc;
	}
	public String getTenKhuVuc() {
		return tenKhuVuc;
	}
	public void setTenKhuVuc(String tenKhuVuc) {
		this.tenKhuVuc = tenKhuVuc;
	}
	public String getLoaiKhuVuc() {
		return loaiKhuVuc;
	}
	public void setLoaiKhuVuc(String loaiKhuVuc) {
		this.loaiKhuVuc = loaiKhuVuc;
	}
	@Override
	public String toString() {
		return tenKhuVuc;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKhuVuc);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuVuc other = (KhuVuc) obj;
		return Objects.equals(maKhuVuc, other.maKhuVuc);
	}
	
}
