package Entity;

public class LoaiMonAn {
	private String maLoaiMonAn;
	private String tenLoaiMonAn;
	
	public LoaiMonAn(String maLoaiMonAn, String tenLoaiMonAn) {
		super();
		this.maLoaiMonAn = maLoaiMonAn;
		this.tenLoaiMonAn = tenLoaiMonAn;
	}
	
	public LoaiMonAn() {
		super();
	}
	public String getMaLoaiMonAn() {
		return maLoaiMonAn;
	}
	public void setMaLoaiMonAn(String maLoaiMonAn) {
		this.maLoaiMonAn = maLoaiMonAn;
	}
	public String getTenLoaiMonAn() {
		return tenLoaiMonAn;
	}
	public void setTenLoaiMonAn(String tenLoaiMonAn) {
		this.tenLoaiMonAn = tenLoaiMonAn;
	}

	@Override
	public String toString() {
		return tenLoaiMonAn;
	}
	
}
