package Entity;

public class ChiTietHoaDon {
	private HoaDon maHoaDon;
	private MonAn maMonAn;
	private int soLuong;
	private double donGia;
	private double tongTien;
	public ChiTietHoaDon(HoaDon maHoaDon, MonAn maMonAn, int soLuong, double donGia) {
		super();
		this.maHoaDon = maHoaDon;
		this.maMonAn = maMonAn;
		this.soLuong = soLuong;
		this.donGia = donGia;
		tinhTongTien();
	}
	
	public ChiTietHoaDon(MonAn maMonAn, int soLuong, double donGia ) {
		super();
		this.maMonAn = maMonAn;
		this.soLuong = soLuong;
		this.donGia = donGia;
		tinhTongTien();
	}
	
	@Override
	public String toString() {
		return maMonAn.getMaMonAn() + ";" +maMonAn.getTenMonAn()+";"+ soLuong + ";"
				+ donGia + ";" + tongTien + "\n";
	}
	
	public HoaDon getMaHoaDon() {
		return maHoaDon;
	}


	public void tinhTongTien()
	{
		this.tongTien=this.soLuong*this.donGia;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	public MonAn getMaMonAn() {
		return maMonAn;
	}

}
