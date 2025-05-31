package Entity;

import java.util.Objects;

public class Phong {
	private String maPhong;
	private String tenPhong;
	private int soLuongChoNgoi;
	private String trangThai;
	private KhuVuc maKhuVuc;

	public Phong(String maPhong, String tenPhong, int soLuongChoNgoi, String trangThai, KhuVuc maKhuVuc) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.soLuongChoNgoi = soLuongChoNgoi;
		this.trangThai = trangThai;
		this.maKhuVuc = maKhuVuc;
	}

	public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}


	public String getMaPhong() {
		return maPhong;
	}



	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getTenPhong() {
		return tenPhong;
	}


	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}


	public int getSoLuongChoNgoi() {
		return soLuongChoNgoi;
	}


	public void setSoLuongChoNgoi(int soLuongChoNgoi) {
		this.soLuongChoNgoi = soLuongChoNgoi;
	}


	public KhuVuc getMaKhuVuc() {
		return maKhuVuc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhong);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		return Objects.equals(maPhong, other.maPhong);
	}
	@Override
	public String toString() {
		return tenPhong;
	}
	

	
}
