package Entity;

import java.util.Objects;

public class MonAn {
	private String maMonAn;
	private String tenMonAn;
	private double giaMonAn;
	private String moTa;
	private String donViTinh;
	private String hinhAnh;
	private String trangThai;
	public MonAn(String maMonAn, String tenMonAn, double giaMonAn, String moTa, String donViTinh, String hinhAnh,
			String trangThai, LoaiMonAn maLoaiMonAn) {
		super();
		this.maMonAn = maMonAn;
		this.tenMonAn = tenMonAn;
		this.giaMonAn = giaMonAn;
		this.moTa = moTa;
		this.donViTinh = donViTinh;
		this.hinhAnh = hinhAnh;
		this.trangThai = trangThai;
		this.maLoaiMonAn = maLoaiMonAn;
	}

	private LoaiMonAn maLoaiMonAn;

	public MonAn() {
		super();
	}
	public MonAn(String maMonAn) {
		super();
		this.maMonAn = maMonAn;
	}
	
	public MonAn(String maMonAn, String tenMonAn) {
		super();
		this.maMonAn = maMonAn;
		this.tenMonAn = tenMonAn;
	}
	public MonAn(String maMonAn, String tenMonAn, double giaMonAn, String moTa, String donViTinh, String hinhAnh,
			LoaiMonAn maLoaiMonAn) {
		super();
		this.maMonAn = maMonAn;
		this.tenMonAn = tenMonAn;
		this.giaMonAn = giaMonAn;
		this.moTa = moTa;
		this.donViTinh = donViTinh;
		this.hinhAnh = hinhAnh;
		this.maLoaiMonAn = maLoaiMonAn;
	}
	public String getMaMonAn() {
		return maMonAn;
	}
	public String getTenMonAn() {
		return tenMonAn;
	}
	public void setTenMonAn(String tenMonAn) {
		this.tenMonAn = tenMonAn;
	}
	public double getGiaMonAn() {
		return giaMonAn;
	}
	public void setGiaMonAn(double giaMonAn) {
		this.giaMonAn = giaMonAn;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public LoaiMonAn getMaLoaiMonAn() {
		return maLoaiMonAn;
	}
	public void setMaLoaiMonAn(LoaiMonAn maLoaiMonAn) {
		this.maLoaiMonAn = maLoaiMonAn;
	}
	
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maMonAn);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonAn other = (MonAn) obj;
		return Objects.equals(maMonAn, other.maMonAn);
	}
	
}
