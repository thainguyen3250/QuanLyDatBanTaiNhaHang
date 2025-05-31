package Entity;

import java.time.LocalDateTime;


public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private int tuoi;
    private String maCCCD;
    private String email;
    private String trangThai;
    private boolean gioiTinh;
    private float heSoLuong;
    private LoaiNhanVien loaiNhanVien;
   
    // Constructor đầy đủ
    
    public NhanVien(String maNhanVien, String tenNhanVien, int tuoi, String maCCCD, String email, String trangThai,
			boolean gioiTinh, float heSoLuong, LoaiNhanVien loaiNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.tuoi = tuoi;
		this.maCCCD = maCCCD;
		this.email = email;
		this.trangThai = trangThai;
		this.gioiTinh = gioiTinh;
		this.heSoLuong = heSoLuong;
		this.loaiNhanVien = loaiNhanVien;
	}

	public NhanVien(String maNhanVien, String tenNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
	}
    
	public NhanVien(String maNhanVien, String tenNhanVien, LoaiNhanVien loaiNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.loaiNhanVien = loaiNhanVien;
	}

	// Constructor rút gọn (tùy theo use-case)
    public NhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    // Getters & Setters
    public String getMaNhanVien() {
        return maNhanVien;
    }


    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getMaCCCD() {
        return maCCCD;
    }

    public void setMaCCCD(String maCCCD) {
        this.maCCCD = maCCCD;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public float getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(float heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public LoaiNhanVien getMaLoaiNhanVien() {
        return loaiNhanVien;
    }

    public void setMaLoaiNhanVien(LoaiNhanVien loaiNhanVien) {
        this.loaiNhanVien = loaiNhanVien;
    }
    
    public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	@Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", tuoi=" + tuoi +
                ", maCCCD='" + maCCCD + '\'' +
                ", email='" + email + '\'' +
                ", gioiTinh=" + (gioiTinh ? "Nam" : "Nữ") +
                ", heSoLuong=" + heSoLuong +
                ", loaiNhanVien=" + loaiNhanVien +
                '}';
    }
}

