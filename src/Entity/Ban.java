package Entity;

import java.util.Objects;

public class Ban {
	private String maBan;
	private String tenBan;
	private int soLuongChoNgoi;
	private String trangThai;
	private	KhuVuc maKhuVuc;
	
	public Ban(String maBan) {
		super();
		this.maBan = maBan;
	}
	public Ban(String maBan, String tenBan, int soLuongChoNgoi, String trangThai, KhuVuc maKhuVuc) {
		super();
		this.maBan = maBan;
		this.tenBan = tenBan;
		this.soLuongChoNgoi = soLuongChoNgoi;
		this.trangThai = trangThai;
		this.maKhuVuc = maKhuVuc;
	}
	public String getMaBan() {
		return maBan;
	}
	public String getTenBan() {
		return tenBan;
	}
	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}
	public int getSoLuongChoNgoi() {
		return soLuongChoNgoi;
	}
	public void setSoLuongChoNgoi(int soLuongChoNgoi) {
		this.soLuongChoNgoi = soLuongChoNgoi;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	
	public KhuVuc getMaKhuVuc() {
		return maKhuVuc;
	}
	public void setMaKhuVuc(KhuVuc maKhuVuc) {
		this.maKhuVuc = maKhuVuc;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maBan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ban other = (Ban) obj;
		return Objects.equals(maBan, other.maBan);
	}
	
}
