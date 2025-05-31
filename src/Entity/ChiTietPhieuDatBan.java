package Entity;

public class ChiTietPhieuDatBan {
	private PhieuDatBan maPhieuDatBan;
	private Ban maBan;
	private MonAn maMonAn;
	private Phong maPhong;
	private double tienDatCoc;
	private int soLuong;
	private String loaiDichVu;
	private double tongTien;
	public ChiTietPhieuDatBan(PhieuDatBan maPhieuDatBan, Ban maBan) {
		super();
		this.maPhieuDatBan = maPhieuDatBan;
		this.maBan = maBan;
		this.soLuong = 1;
		this.tienDatCoc =300000;
		this.tongTien= 300000;
		this.loaiDichVu="Đặt Bàn";
	}
	public ChiTietPhieuDatBan(PhieuDatBan maPhieuDatBan, Phong maPhong) {
		super();
		this.maPhieuDatBan = maPhieuDatBan;
		this.maPhong = maPhong;
		this.soLuong = 1;
		this.tienDatCoc =600000;
		this.tongTien= 600000;
		this.loaiDichVu="Đặt Phòng";
	}
	public ChiTietPhieuDatBan(PhieuDatBan maPhieuDatBan, MonAn maMonAn,int soLuong) {
		super();
		this.maPhieuDatBan = maPhieuDatBan;
		this.maMonAn = maMonAn;
		this.soLuong = soLuong;
		this.tienDatCoc = maMonAn.getGiaMonAn()*0.2;
		this.tongTien=this.soLuong*this.tongTien;
		this.loaiDichVu="Đặt Món";
	}
	public double getTienDatCoc() {
		return tienDatCoc;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public String getLoaiDichVu() {
		return loaiDichVu;
	}
	public double getTongTien() {
		return tongTien;
	}
	
}
