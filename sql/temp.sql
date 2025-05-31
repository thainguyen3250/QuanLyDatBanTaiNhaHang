create database quanlynhahang

use quanlynhahang

-- Bảng Loại Nhân Viên
CREATE TABLE LoaiNhanVien (
    maLoaiNhanVien NVARCHAR(10) PRIMARY KEY NOT NULL,
    tenLoai NVARCHAR(20)
);

-- Bảng Nhân Viên
CREATE TABLE NhanVien (
    maNhanVien NVARCHAR(20) PRIMARY KEY NOT NULL,
    tenNhanVien NVARCHAR(50),
    tuoi INT,
    maCCCD NVARCHAR(20) UNIQUE,
    email NVARCHAR(50),
	gioiTinh BIT,  
	trangThai Nvarchar(20),
    heSoLuong FLOAT,
    maLoaiNhanVien NVARCHAR(10),
    CONSTRAINT fk_loainv_nhanvien
        FOREIGN KEY (maLoaiNhanVien)
        REFERENCES LoaiNhanVien(maLoaiNhanVien)
);

-- Bảng Tài Khoản
CREATE TABLE TaiKhoan (
    tenTaiKhoan NVARCHAR(20) PRIMARY KEY NOT NULL,
    matKhau NVARCHAR(20),
    trangThaiTaiKhoan BIT,
    ngayTaoTaiKhoan DATETIME,
    maNhanVien NVARCHAR(20),
    CONSTRAINT fk_tk_nv
        FOREIGN KEY (maNhanVien)
        REFERENCES NhanVien(maNhanVien)
        ON DELETE CASCADE
);

/**/
create table LoaiKhachHang(
maLoaiKhachHang nvarchar(20) primary key not null,
tenLoai nvarchar(20),
)
/**/
create table KhachHang(
soDienThoai nvarchar(10) primary key not null,
hoTen Nvarchar(50),
email Nvarchar(50),
tongDiemTichLuy float,
diemTichLuyHienTai float,
maLoaiKhachHang nvarchar(20),
CONSTRAINT fk_tk_kh
FOREIGN KEY (maLoaiKhachHang)
REFERENCES LoaiKhachHang(maLoaiKhachHang) on delete cascade,
)

create table LoaiMonAn(
maLoaiMonAn nvarchar(20) primary key not null,
tenLoaiMonAn nvarchar(30),
)
/*món ăn*/
create table MonAn(
maMonAn nvarchar(20) primary key not null,
tenMonAn nvarchar(20),
giaMonAn float,
moTa nvarchar(500),
donViTinh nvarchar(20),
hinhAnh nvarchar(100),
trangThai nvarchar(20),
maLoaiMonAn nvarchar(20),
constraint fk_monan_lma
foreign key (maLoaiMonAn)
references LoaiMonAn(maLoaiMonAn)
)


create table KhuVuc(
maKhuVuc nvarchar(20) primary key not null,
tenKhuVuc nvarchar(20),
loaiKhuVuc nvarchar(20),
soLuongPhong int
)

create table Phong(
maPhong nvarchar(20) primary key not null,
tenPhong nvarchar(20),
soLuongChoNgoi int,
trangThai nvarchar(20),
maKhuVuc nvarchar(20),
CONSTRAINT fk_khuvuc_phong
FOREIGN KEY (maKhuVuc)
REFERENCES KhuVuc(maKhuVuc),
)

create table Ban(
maBan nvarchar(20) primary key not null,
tenBan nvarchar(20),
soLuongChoNgoi int,
trangThai nvarchar(20),
maKhuVuc nvarchar(20),
CONSTRAINT fk_khuvuc_ban
FOREIGN KEY (maKhuVuc)
REFERENCES KhuVuc(maKhuVuc) on delete cascade,
)


create table PhieuDatPhong(
maPhieuDatPhong nvarchar(20) primary key not null,
ngayDatPhong datetime,
ngayNhanPhong datetime,
diaChiNhaHang nvarchar(200),
soDienThoaiNhaHang nvarchar(50),
ghiChu nvarchar(100),
/**/
/**/
maNhanVien nvarchar(20),
soDienThoai nvarchar(10),
maPhong nvarchar(20),
/**/
constraint fk_nv_phieudatphong 
foreign key (maNhanVien)
references NhanVien(maNhanVien),

constraint fk_kh_phieudatphong 
foreign key (soDienThoai)
references KhachHang(soDienThoai),

constraint fk_phong_phieudatphong 
foreign key (maPhong)
references Phong(maPhong),
)




create table PhieuDatBan(
maPhieuDatBan nvarchar(20) primary key not null,
ngayDatBan datetime,
ngayNhanBan datetime,
diaChiQuan nvarchar(200),
soDienThoaiNhaHang nvarchar(50),
ghiChu nvarchar(100),
/**/
/**/
maNhanVien nvarchar(20),
soDienThoai nvarchar(10),
maBan nvarchar(20),
/**/
constraint fk_nv_phieudat 
foreign key (maNhanVien)
references NhanVien(maNhanVien),

constraint fk_kh_phieudat 
foreign key (soDienThoai)
references KhachHang(soDienThoai),

constraint fk_ban_phieudat 
foreign key (maBan)
references Ban(maBan),
)


create table HoaDon(
maHoaDon nvarchar(20) primary key not null,
ngayLapHoaDon datetime,
diaChiNhaHang nvarchar(200),
thueVAT float,
tienKhachDua float,
phiDichVu float,
soDienThoaiNhaHang nvarchar(13),

/**/
maNhanVien nvarchar(20),
soDienThoai nvarchar(10),
maBan nvarchar(20),
maPhong nvarchar(20),

CONSTRAINT fk_nv_hd
FOREIGN KEY (maNhanVien)
REFERENCES NhanVien(maNhanVien) on delete cascade,

CONSTRAINT fk_kh_hd
FOREIGN KEY (soDienThoai)
REFERENCES KhachHang(soDienThoai) on delete cascade,

CONSTRAINT fk_ban_hd
FOREIGN KEY (maBan)
REFERENCES Ban(maBan) on delete cascade,

CONSTRAINT fk_phong_hd
FOREIGN KEY (maPhong)
REFERENCES Phong(maPhong) on delete cascade,
)

create table ChiTietHoaDon(
maHoaDon nvarchar(20),
maMonAn nvarchar(20),
soLuong int,
donGia float,
/**/
CONSTRAINT fk_monan_cthd
FOREIGN KEY (maMonAn)
REFERENCES MonAn(maMonAn) on delete cascade,
 
CONSTRAINT fk_hoadon_cthd
FOREIGN KEY (maHoaDon)
REFERENCES HoaDon(maHoaDon) on delete cascade,

constraint pk_cthd Primary Key (maHoaDon,maMonAn)
)

/*-----------------------------------PROCEDURE-------------------------------------------------*/

CREATE PROCEDURE sp_Top10MonAnBanChayNhat
AS
BEGIN
    SELECT TOP 10 
        MA.tenMonAn,
		MA.hinhAnh,
        SUM(CT.soLuong) AS tongSoLuongBan
    FROM ChiTietHoaDon CT
    JOIN MonAn MA ON CT.maMonAn = MA.maMonAn
    JOIN LoaiMonAn LMA ON MA.maLoaiMonAn = LMA.maLoaiMonAn
    GROUP BY MA.tenMonAn, MA.giaMonAn,MA.hinhAnh
    ORDER BY tongSoLuongBan DESC
END



CREATE PROCEDURE sp_LayTatCaMonAn
AS
BEGIN
    SELECT 
        maMonAn,
        tenMonAn,
        giaMonAn,
        moTa,
        donViTinh,
        hinhAnh,
		trangThai,
        MonAn.maLoaiMonAn,
        tenLoaiMonAn
    FROM 
        MonAn
    INNER JOIN 
        LoaiMonAn ON MonAn.maLoaiMonAn = LoaiMonAn.maLoaiMonAn;
END





CREATE PROCEDURE sp_TimKiemTaiKhoan
    @tenTaiKhoan NVARCHAR(20)
AS
BEGIN
    SELECT 
        tk.tenTaiKhoan,
        tk.matKhau,
        tk.trangThaiTaiKhoan,
        tk.ngayTaoTaiKhoan,
        
        nv.maNhanVien,
        nv.tenNhanVien,
        
        lnv.maLoaiNhanVien,
        lnv.tenLoai
    FROM 
        TaiKhoan tk
    LEFT JOIN 
        NhanVien nv ON tk.maNhanVien = nv.maNhanVien
    LEFT JOIN 
        LoaiNhanVien lnv ON nv.maLoaiNhanVien = lnv.maLoaiNhanVien
    WHERE 
        tk.tenTaiKhoan = @tenTaiKhoan;
END;



 
CREATE PROCEDURE sp_TimPhongTheoKhuVuc
    @maKhuVuc NVARCHAR(20)
AS
BEGIN
SELECT 
	p.maPhong,
	p.tenPhong,
	p.soLuongChoNgoi,
	p.trangThai,
	p.maKhuVuc,
	kv.tenKhuVuc,
	kv.loaiKhuVuc
	From Phong p
	inner join KhuVuc kv on p.maKhuVuc = kv.maKhuVuc
	where p.maKhuVuc=@maKhuVuc and p.trangThai=N'Trống';
END;



CREATE PROCEDURE sp_LayTatCaTaiKhoan
AS
BEGIN
    SELECT 
        tk.tenTaiKhoan AS TenTaiKhoan,
        tk.matKhau AS MatKhau,
        tk.trangThaiTaiKhoan AS TrangThaiTaiKhoan,
        tk.ngayTaoTaiKhoan AS NgayTaoTaiKhoan,

        nv.maNhanVien AS MaNhanVien,
        nv.tenNhanVien AS TenNhanVien,
        nv.tuoi AS Tuoi,
        nv.maCCCD AS MaCCCD,
        nv.email AS Email,
		nv.trangThai as trangThai,
        nv.gioiTinh AS GioiTinh,
        nv.heSoLuong AS HeSoLuong,

        lnv.maLoaiNhanVien AS MaLoaiNhanVien,
        lnv.tenLoai AS TenLoaiNhanVien

    FROM TaiKhoan tk
    INNER JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien
    INNER JOIN LoaiNhanVien lnv ON nv.maLoaiNhanVien = lnv.maLoaiNhanVien;
END;


CREATE PROCEDURE sp_LayHetPhong
AS
BEGIN
SELECT 
	p.maPhong,
	p.tenPhong,
	p.soLuongChoNgoi,
	p.trangThai,
	p.maKhuVuc,
	kv.tenKhuVuc,
	kv.loaiKhuVuc
	From Phong p
	inner join KhuVuc kv on p.maKhuVuc = kv.maKhuVuc
END;

CREATE PROCEDURE sp_LayHetBan
AS
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,

	b.maKhuVuc,
	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
END;



CREATE PROCEDURE sp_TimBanTheoMaKhuVuc
    @maKhuVuc NVARCHAR(20)
AS
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,
	b.maKhuVuc,
	
	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
	where b.maKhuVuc=@maKhuVuc and b.trangThai=N'Trống';
END;



CREATE PROCEDURE sp_TimBanDaDat
AS
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,

	b.maKhuVuc,

	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
	where b.trangThai=N'Có khách';
END;


CREATE PROCEDURE sp_TimPhongDaDat
AS
BEGIN
SELECT 
	p.maPhong,
	p.tenPhong,
	p.soLuongChoNgoi,
	p.trangThai,

	p.maKhuVuc,
	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Phong p
	inner join KhuVuc kv on p.maKhuVuc=kv.maKhuVuc
	where p.trangThai=N'Có khách';
END;


CREATE PROCEDURE sp_TimBanChuaDat
AS
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,
	b.maKhuVuc,

	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
	where b.trangThai=N'Trống';
END;

CREATE PROCEDURE sp_TimBanDaGoiMon
AS
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,
	b.maKhuVuc,

	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
	where b.trangThai=N'Đã gọi món';
END;



CREATE PROCEDURE sp_TimPhongTheoTrangThai
	@trangThai NVARCHAR(20)
AS
BEGIN
SELECT 
	p.maPhong,
	p.tenPhong,
	p.soLuongChoNgoi,
	p.trangThai,

	p.maKhuVuc,
	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Phong p
	inner join KhuVuc kv on p.maKhuVuc=kv.maKhuVuc
	where p.trangThai=@trangThai;
END;



CREATE PROCEDURE sp_TimBanTheoTrangThai
	@trangThai NVARCHAR(20)
AS
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,

	b.maKhuVuc,
	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
	where b.trangThai=@trangThai;
END;


CREATE PROCEDURE TinhTongHoaDonTrongNgay
    @ngay DATE
AS
BEGIN
    SELECT SUM((cthd.donGia * cthd.soLuong)+hd.phiDichVu+(cthd.donGia * cthd.soLuong)*hd.thueVAT)
    FROM HoaDon hd
	inner join ChiTietHoaDon cthd on hd.maHoaDon= cthd.maHoaDon
    WHERE CONVERT(DATE, hd.ngayLapHoaDon) = @ngay;
END;




CREATE PROCEDURE sp_TongSoBanTrong
AS
BEGIN
SELECT 
	count(b.maBan)
	From Ban b
	where b.trangThai=N'Trống';
END;


CREATE PROCEDURE sp_TongSoBanDatTruoc
AS
BEGIN
SELECT 
	count(b.maBan)
	From Ban b
	where b.trangThai=N'Đã đặt trước';
END;

CREATE PROCEDURE sp_TongSoPhongTrong
AS
BEGIN
SELECT 
	count(p.maPhong)
	From Phong p
	where p.trangThai=N'Trống';
END;



CREATE PROCEDURE sp_Top10KhachHangTheoDoanhThu
    @thang INT,
    @nam INT
AS
BEGIN
    SELECT TOP 10 
        kh.soDienThoai,
        kh.hoTen,
        SUM(hd.tienKhachDua) AS tongDoanhThu
    FROM HoaDon hd
    JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
    WHERE MONTH(hd.ngayLapHoaDon) = @thang AND YEAR(hd.ngayLapHoaDon) = @nam
    GROUP BY kh.soDienThoai, kh.hoTen
    ORDER BY tongDoanhThu DESC
END


CREATE PROCEDURE sp_TongDoanhThuTop10KhachHang
    @thang INT,
    @nam INT
AS
BEGIN
    SELECT SUM(tongDoanhThu) AS tongDoanhThuTop10
    FROM (
        SELECT TOP 10 
            kh.soDienThoai,
            SUM(hd.tienKhachDua) AS tongDoanhThu
        FROM HoaDon hd
        JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
        WHERE MONTH(hd.ngayLapHoaDon) = @thang AND YEAR(hd.ngayLapHoaDon) = @nam
        GROUP BY kh.soDienThoai
        ORDER BY tongDoanhThu DESC
    ) AS Top10
END


CREATE PROCEDURE sp_DemSoLuotLoaiKhachHangTheoThangNam
    @thang INT,
    @nam INT
AS
BEGIN
    SELECT 
        lkh.maLoaiKhachHang,
        lkh.tenLoai,
        COUNT(*) AS soLuotXuatHien
    FROM HoaDon hd
    JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
    JOIN LoaiKhachHang lkh ON kh.maLoaiKhachHang = lkh.maLoaiKhachHang
    WHERE MONTH(hd.ngayLapHoaDon) = @thang AND YEAR(hd.ngayLapHoaDon) = @nam
    GROUP BY lkh.maLoaiKhachHang, lkh.tenLoai
    ORDER BY soLuotXuatHien DESC
END


CREATE PROCEDURE sp_DoanhThu7NgayGanNhat
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        CAST(H.ngayLapHoaDon AS DATE) AS Ngay,
        SUM(CT.soLuong * CT.donGia) + SUM(H.phiDichVu) + SUM(H.thueVAT) AS TongDoanhThu
    FROM 
        HoaDon H
        INNER JOIN ChiTietHoaDon CT ON H.maHoaDon = CT.maHoaDon
    WHERE 
        H.ngayLapHoaDon >= CAST(DATEADD(DAY, -6, GETDATE()) AS DATE)
        AND H.ngayLapHoaDon < CAST(DATEADD(DAY, 1, GETDATE()) AS DATE)
    GROUP BY 
        CAST(H.ngayLapHoaDon AS DATE)
    ORDER BY 
        Ngay;
END;


CREATE PROCEDURE sp_DoanhThuTheoThangNam
    @Thang INT,
    @Nam INT
AS
BEGIN
    SELECT 
        ma.tenMonAn AS TenMonAn,
        lma.tenLoaiMonAn AS TenDanhMuc,
        SUM(cthd.soLuong) AS SoLuongBan,
        SUM(cthd.soLuong * cthd.donGia) AS DoanhThu
    FROM 
        ChiTietHoaDon cthd
    INNER JOIN 
        MonAn ma ON cthd.maMonAn = ma.maMonAn
    INNER JOIN 
        LoaiMonAn lma ON ma.maLoaiMonAn = lma.maLoaiMonAn
    INNER JOIN 
        HoaDon hd ON cthd.maHoaDon = hd.maHoaDon
    WHERE 
        MONTH(hd.ngayLapHoaDon) = @Thang
        AND YEAR(hd.ngayLapHoaDon) = @Nam
    GROUP BY 
        ma.tenMonAn, lma.tenLoaiMonAn
    ORDER BY 
        SoLuongBan DESC
END


CREATE PROCEDURE sp_TongDoanhThuTheoNgay
    @startDate DATE,
    @endDate DATE
AS
BEGIN
    SELECT 
        CAST(HD.ngayLapHoaDon AS DATE) AS Ngay,
        SUM(CTHD.soLuong * CTHD.donGia+hd.phiDichVu) AS DoanhThu
    FROM 
        HoaDon HD
    JOIN 
        ChiTietHoaDon CTHD ON HD.maHoaDon = CTHD.maHoaDon
    WHERE 
        HD.ngayLapHoaDon BETWEEN @startDate AND @endDate
    GROUP BY 
        CAST(HD.ngayLapHoaDon AS DATE)
    ORDER BY 
        Ngay
END

CREATE PROCEDURE sp_Top5LoaiMonAn_BanRa_TheoThoiGian
    @tuNgay DATE,
    @denNgay DATE
AS
BEGIN
    -- Bảng tổng số lượng theo loại món ăn
    WITH TongSoLuong AS (
        SELECT 
            lma.tenLoaiMonAn,
            SUM(cthd.soLuong) AS soLuongBan
        FROM 
            ChiTietHoaDon cthd
        JOIN 
            HoaDon hd ON cthd.maHoaDon = hd.maHoaDon
        JOIN 
            MonAn ma ON cthd.maMonAn = ma.maMonAn
        JOIN 
            LoaiMonAn lma ON ma.maLoaiMonAn = lma.maLoaiMonAn
        WHERE 
            hd.ngayLapHoaDon BETWEEN @tuNgay AND @denNgay
        GROUP BY 
            lma.tenLoaiMonAn
    ),
    -- Gắn thứ hạng
    XepHang AS (
        SELECT *,
               ROW_NUMBER() OVER (ORDER BY soLuongBan DESC) AS hang
        FROM TongSoLuong
    )

    -- Kết quả tối đa 5 dòng
    SELECT tenLoaiMonAn, soLuongBan
    FROM XepHang
    WHERE hang <= 4

    UNION ALL

    SELECT 
        N'Khác' AS tenLoaiMonAn,
        SUM(soLuongBan) AS soLuongBan
    FROM XepHang
    WHERE hang > 4
END



CREATE PROCEDURE sp_TongDoanhThuTheoThang
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    SELECT 
        FORMAT(hd.ngayLapHoaDon, 'MM/yyyy') AS Thang,
        SUM(cthd.soLuong * cthd.donGia+hd.phiDichVu) AS DoanhThu
    FROM 
        HoaDon hd
        JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon
    WHERE 
        hd.ngayLapHoaDon BETWEEN @TuNgay AND @DenNgay
    GROUP BY 
        FORMAT(hd.ngayLapHoaDon, 'MM/yyyy')
    ORDER BY 
        MIN(hd.ngayLapHoaDon)
END


CREATE PROCEDURE sp_TongDoanhThuTheoNam
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    SELECT 
        YEAR(hd.ngayLapHoaDon) AS Nam,
        SUM(cthd.soLuong * cthd.donGia+hd.phiDichVu) AS DoanhThu
    FROM 
        HoaDon hd
        JOIN ChiTietHoaDon cthd ON hd.maHoaDon = cthd.maHoaDon
    WHERE 
        hd.ngayLapHoaDon BETWEEN @TuNgay AND @DenNgay
    GROUP BY 
        YEAR(hd.ngayLapHoaDon)
    ORDER BY 
        Nam
END

CREATE PROCEDURE sp_DemSoLuongHoaDonTheoKhoangThoiGian
    @startDate DATETIME,
    @endDate DATETIME
AS
BEGIN
    SELECT COUNT(*) AS soLuongHoaDon
    FROM HoaDon
    WHERE ngayLapHoaDon BETWEEN @startDate AND @endDate;
END


/*-------------------------------------------------món ăn------------------------------------------------*/

CREATE PROCEDURE sp_timMonAnTheoTen
    @tenMonAn NVARCHAR(20)
AS
BEGIN
    SELECT 
        maMonAn,
        tenMonAn,
        giaMonAn,
        moTa,
        donViTinh,
        hinhAnh,
        trangThai,
        MonAn.maLoaiMonAn,
        tenLoaiMonAn
    FROM 
        MonAn
    INNER JOIN 
        LoaiMonAn ON MonAn.maLoaiMonAn = LoaiMonAn.maLoaiMonAn
    WHERE 
        MonAn.tenMonAn LIKE N'%' + @tenMonAn + N'%'
END


Create procedure sp_timMonAnTheoLoai
	@loaiMonAn NVARCHAR(20)
AS
BEGIN
    SELECT 
        maMonAn,
        tenMonAn,
        giaMonAn,
        moTa,
        donViTinh,
        hinhAnh,
        trangThai,
        MonAn.maLoaiMonAn,
        tenLoaiMonAn
    FROM 
        MonAn
    INNER JOIN 
        LoaiMonAn ON MonAn.maLoaiMonAn = LoaiMonAn.maLoaiMonAn
    WHERE 
        LoaiMonAn.tenLoaiMonAn LIKE N'%' + @loaiMonAn + N'%'
End;



CREATE PROCEDURE sp_timBanTheoSoCho
    @soChoNhoNhat INT,
    @soChoLonNhat INT
as
BEGIN
SELECT 
	b.maBan,
	b.tenBan,
	b.soLuongChoNgoi,
	b.trangThai,
	b.maKhuVuc,

	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Ban b
	inner join KhuVuc kv on b.maKhuVuc=kv.maKhuVuc
    WHERE 
        b.soLuongChoNgoi BETWEEN @soChoNhoNhat AND @soChoLonNhat
END


CREATE PROCEDURE sp_timPhongTheoSoCho
    @soChoNhoNhat INT,
    @soChoLonNhat INT
as
BEGIN
SELECT 
	p.maPhong,
	p.tenPhong,
	p.soLuongChoNgoi,
	p.trangThai,
	p.maKhuVuc,

	kv.tenKhuVuc,
	kv.loaiKhuVuc,
	kv.soLuongPhong
	From Phong p
	inner join KhuVuc kv on p.maKhuVuc=kv.maKhuVuc
    WHERE 
        p.soLuongChoNgoi BETWEEN @soChoNhoNhat AND @soChoLonNhat
END

/*-------------------------------------------------Phong------------------------------------------------*/

CREATE PROCEDURE sp_LayThongTinHoaDon
AS
BEGIN
    SELECT 
        hd.maHoaDon,
        nv.tenNhanVien,
        kh.hoTen,
        hd.ngayLapHoaDon,
        SoTien = ISNULL(TongTien * 1.1, 0)+hd.phiDichVu
    FROM HoaDon hd
    LEFT JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien
    LEFT JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
    OUTER APPLY (
        SELECT SUM(ct.soLuong * ct.donGia) AS TongTien
        FROM ChiTietHoaDon ct
        WHERE ct.maHoaDon = hd.maHoaDon
    ) AS ct_tong
END

CREATE PROCEDURE sp_LayThongTinHoaDonTheoMaHoaDon
	@maHoaDon nvarchar(20)
AS
BEGIN
    SELECT 
        hd.maHoaDon,
        nv.tenNhanVien,
        kh.hoTen,
        hd.ngayLapHoaDon,
        SoTien = ISNULL(TongTien * 1.1, 0)+hd.phiDichVu
    FROM HoaDon hd
    LEFT JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien
    LEFT JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
    OUTER APPLY (
        SELECT SUM(ct.soLuong * ct.donGia) AS TongTien
        FROM ChiTietHoaDon ct
        WHERE ct.maHoaDon = hd.maHoaDon
    ) AS ct_tong
	where hd.maHoaDon=@maHoaDon
END

CREATE PROCEDURE sp_LayThongTinHoaDonTheoNgay
    @ngay DATE
AS
BEGIN
    SELECT 
        hd.maHoaDon,
        nv.tenNhanVien,
        kh.hoTen,
        hd.ngayLapHoaDon,
        SoTien = ISNULL(TongTien * 1.1, 0) + ISNULL(hd.phiDichVu, 0)
    FROM HoaDon hd
    LEFT JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien
    LEFT JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
    OUTER APPLY (
        SELECT SUM(ct.soLuong * ct.donGia) AS TongTien
        FROM ChiTietHoaDon ct
        WHERE ct.maHoaDon = hd.maHoaDon
    ) AS ct_tong
    WHERE CAST(hd.ngayLapHoaDon AS DATE) = @ngay
END

CREATE PROCEDURE sp_LayThongTinHoaDonTheoMaHoaDon1
    @maHoaDon nvarchar(20)
AS
BEGIN
    SELECT 
        hd.maHoaDon,
        
        -- Thông tin nhân viên
        nv.maNhanVien,
        nv.tenNhanVien,
        
        -- Thông tin khách hàng
        kh.soDienThoai AS soDienThoaiKhach,
        kh.hoTen AS tenKhachHang,
        
        -- Thông tin hóa đơn
        hd.ngayLapHoaDon,
        hd.tienKhachDua,
        hd.maBan,
        hd.maPhong,

		hd.phiDichVu
    FROM HoaDon hd
    LEFT JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien
    LEFT JOIN KhachHang kh ON hd.soDienThoai = kh.soDienThoai
    WHERE hd.maHoaDon = @maHoaDon
END

/*----------------------------------------------DATA-----------------------------------------------------*/

INSERT INTO LoaiNhanVien (maLoaiNhanVien, tenLoai)  
VALUES ('LNV01', N'Người Quản Lý'),  
       ('LNV02', N'Nhân viên');


INSERT INTO NhanVien (maNhanVien, tenNhanVien, tuoi, maCCCD, email, gioiTinh,trangThai, heSoLuong, maLoaiNhanVien)  
VALUES 
('NV0001', N'Hà Mạnh Tường', 20, '012345678001', 'tuongha@example.com', 1,N'Đang làm', 5.0, 'LNV01'),
('NV0002', N'Trần Thị B', 40, '012345678002', 'b.tran@example.com', 0,N'Đang làm', 3.2, 'LNV01'),
('NV0003', N'Phạm Văn C', 38, '012345678003', 'c.pham@example.com', 1,N'Đang làm',3.1, 'LNV01'),
('NV0004', N'Lê Thị D', 36, '012345678004', 'd.le@example.com', 0,N'Đang làm',3.4, 'LNV01'),
('NV0005', N'Hoàng Văn E', 42, '012345678005', 'e.hoang@example.com',1,N'Đang làm',3.3, 'LNV01'),
('NV0006', N'Đỗ Thị F', 37, '012345678006', 'f.do@example.com', 0,N'Đang làm',3.0, 'LNV01'),
('NV0007', N'Vũ Văn G', 39, '012345678007', 'g.vu@example.com', 1,N'Đang làm',3.5, 'LNV01'),
('NV0008', N'Ngô Thị H', 41, '012345678008', 'h.ngo@example.com', 0,N'Đang làm',3.1, 'LNV01'),
('NV0009', N'Huỳnh Văn I', 43, '012345678009', 'i.huynh@example.com', 1,N'Đang làm',3.2, 'LNV01'),
('NV0010', N'Dương Thị J', 36, '012345678010', 'j.duong@example.com', 0,N'Đang làm',3.3, 'LNV01');

INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, trangThaiTaiKhoan, ngayTaoTaiKhoan, maNhanVien)
VALUES 
('TK00001', '1234', 1, GETDATE(), 'NV0001'),
('TK00002', '1234', 1, GETDATE(), 'NV0002'),
('TK00003', '1234', 1, GETDATE(), 'NV0003'),
('TK00004', '1234', 1, GETDATE(), 'NV0004'),
('TK00005', '1234', 1, GETDATE(), 'NV0005'),
('TK00006', '1234', 1, GETDATE(), 'NV0006'),
('TK00007', '1234', 1, GETDATE(), 'NV0007'),
('TK00008', '1234', 1, GETDATE(), 'NV0008'),
('TK00009', '1234', 1, GETDATE(), 'NV0009'),
('TK00010', '1234', 1, GETDATE(), 'NV0010');

INSERT INTO NhanVien (maNhanVien, tenNhanVien, tuoi, maCCCD, email, gioiTinh,trangThai,heSoLuong, maLoaiNhanVien)  
VALUES 
('NV0011', N'Lê Văn K', 28, '012345678011', 'k.le@example.com', 1,N'Đang làm',2.0, 'LNV02'),
('NV0012', N'Phan Thị L', 27, '012345678012', 'l.phan@example.com', 0,N'Đang làm',2.1, 'LNV02'),
('NV0013', N'Trịnh Văn M', 29, '012345678013', 'm.trinh@example.com', 1,N'Đang làm',2.2, 'LNV02'),
('NV0014', N'Bùi Thị N', 30, '012345678014', 'n.bui@example.com', 0,N'Đang làm',2.0, 'LNV02'),
('NV0015', N'Tạ Văn O', 31, '012345678015', 'o.ta@example.com', 1,N'Đang làm',2.3, 'LNV02'),
('NV0016', N'Hồ Thị P', 26, '012345678016', 'p.ho@example.com', 0,N'Đang làm',2.1, 'LNV02'),
('NV0017', N'Tống Văn Q', 33, '012345678017', 'q.tong@example.com', 1,N'Đang làm',2.4, 'LNV02'),
('NV0018', N'Đặng Thị R', 32, '012345678018', 'r.dang@example.com', 0,N'Đang làm',2.2, 'LNV02'),
('NV0019', N'Cao Văn S', 29, '012345678019', 's.cao@example.com', 1,N'Đang làm',2.1, 'LNV02'),
('NV0020', N'Thái Thị T', 28, '012345678020', 't.thai@example.com', 0,N'Đang làm',2.3, 'LNV02');

INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, trangThaiTaiKhoan, ngayTaoTaiKhoan, maNhanVien)
VALUES 
('TK00011', '1234', 1, GETDATE(), 'NV0011'),
('TK00012', '1234', 1, GETDATE(), 'NV0012'),
('TK00013', '1234', 1, GETDATE(), 'NV0013'),
('TK00014', '1234', 1, GETDATE(), 'NV0014'),
('TK00015', '1234', 1, GETDATE(), 'NV0015'),
('TK00016', '1234', 1, GETDATE(), 'NV0016'),
('TK00017', '1234', 1, GETDATE(), 'NV0017'),
('TK00018', '1234', 1, GETDATE(), 'NV0018'),
('TK00019', '1234', 1, GETDATE(), 'NV0019'),
('TK00020', '1234', 1, GETDATE(), 'NV0020');



INSERT INTO LoaiKhachHang(maLoaiKhachHang, tenLoai)  
VALUES 
    ('ML00001', N'Đồng'),
    ('ML00002', N'Bạc'),
    ('ML00003', N'Vàng'),
    ('ML00004', N'Kim Cương');


INSERT INTO KhachHang(soDienThoai, hoTen, email, tongDiemTichLuy, diemTichLuyHienTai, maLoaiKhachHang) VALUES
    ('0382740501', N'Nguyễn Văn An', 'annguyen5501@gmail.com', 0, 0, 'ML00001'),
    ('0382740502', N'Trần Thị Bình', 'binhtran5502@gmail.com', 100, 50, 'ML00002'),
    ('0382740503', N'Lê Hoàng Cường', 'cuongle5503@gmail.com', 300, 150, 'ML00003'),
    ('0382740504', N'Phạm Minh Đức', 'ducpham5504@gmail.com', 500, 200, 'ML00004'),
    ('0382740505', N'Hoàng Thị Duyên', 'duyenhoang5505@gmail.com', 0, 0, 'ML00001'),
    ('0382740506', N'Võ Văn Hùng', 'hungvo5506@gmail.com', 120, 60, 'ML00002'),
    ('0382740507', N'Đặng Quốc Khánh', 'khanhdang5507@gmail.com', 350, 175, 'ML00003'),
    ('0382740508', N'Bùi Thị Linh', 'linhbui5508@gmail.com', 600, 250, 'ML00004'),
    ('0382740509', N'Ngô Văn Nam', 'namngo5509@gmail.com', 0, 0, 'ML00001'),
    ('0382740510', N'Huỳnh Thị Mai', 'maihuynh5510@gmail.com', 150, 75, 'ML00002'),
    ('0382740511', N'Phan Văn Phúc', 'phucphan5511@gmail.com', 400, 200, 'ML00003'),
    ('0382740512', N'Trương Thị Thảo', 'thaotruong5512@gmail.com', 0, 0, 'ML00001'),
    ('0382740513', N'Đỗ Văn Tâm', 'tamdo5513@gmail.com', 180, 90, 'ML00002'),
    ('0382740514', N'Lý Minh Triết', 'trietly5514@gmail.com', 450, 225, 'ML00003'),
    ('0382740515', N'Nguyễn Thị Ngọc', 'ngocnguyen5515@gmail.com', 700, 300, 'ML00004');




-- Thêm dữ liệu vào bảng KhuVuc
INSERT INTO KhuVuc(maKhuVuc, tenKhuVuc, loaiKhuVuc, soLuongPhong) VALUES
('KV000001', N'Khu Vực 1', 'Thường', 12),
('KV000002', N'Khu Vực 2', 'VIP', 12),
('KV000003', N'Khu Vực 3', 'Thường', 12),
('KV000004', N'Khu Vực 4', 'VIP', 12),
('KV000005', N'Khu Vực 5', 'Thường', 12),
('KV000006', N'Khu Vực 6', 'VIP', 12),
('KV000007', N'Khu Vực 7', 'Thường', 12),
('KV000008', N'Khu Vực 8', 'VIP', 12),
('KV000009', N'Khu Vực 9', 'Thường', 12),
('KV000010', N'Khu Vực 10', 'VIP', 12);

-- Thêm dữ liệu vào bảng Phong
INSERT INTO Phong(maPhong, tenPhong, soLuongChoNgoi, trangThai, maKhuVuc) VALUES
('P00000001', N'Phòng 1', 12, N'Trống', 'KV000001'),
('P00000002', N'Phòng 2', 12, N'Trống', 'KV000001'),
('P00000003', N'Phòng 3', 12, N'Trống', 'KV000001'),
('P00000004', N'Phòng 4', 12, N'Trống', 'KV000001'),
('P00000005', N'Phòng 5', 12, N'Trống', 'KV000001'),
('P00000006', N'Phòng 6', 12, N'Trống', 'KV000001'),
('P00000007', N'Phòng 7', 12, N'Trống', 'KV000001'),
('P00000008', N'Phòng 8', 12, N'Trống', 'KV000001'),
('P00000009', N'Phòng 9', 12, N'Trống', 'KV000002'),
('P00000010', N'Phòng 10', 12, N'Trống', 'KV000002'),
('P00000011', N'Phòng 11', 12, N'Trống', 'KV000002'),
('P00000012', N'Phòng 12', 12, N'Trống', 'KV000002'),
('P00000013', N'Phòng 13', 12, N'Trống', 'KV000002'),
('P00000014', N'Phòng 14', 12, N'Trống', 'KV000002'),
('P00000015', N'Phòng 15', 12, N'Trống', 'KV000002'),
('P00000016', N'Phòng 16', 12, N'Trống', 'KV000002'),
('P00000017', N'Phòng 17', 12, N'Trống', 'KV000003'),
('P00000018', N'Phòng 18', 12, N'Trống', 'KV000003'),
('P00000019', N'Phòng 19', 12, N'Trống', 'KV000003'),
('P00000020', N'Phòng 20', 12, N'Trống', 'KV000003'),
('P00000021', N'Phòng 21', 12, N'Trống', 'KV000003'),
('P00000022', N'Phòng 22', 12, N'Trống', 'KV000003'),
('P00000023', N'Phòng 23', 12, N'Trống', 'KV000003'),
('P00000024', N'Phòng 24', 12, N'Trống', 'KV000003'),
('P00000025', N'Phòng 25', 12, N'Trống', 'KV000004'),
('P00000026', N'Phòng 26', 12, N'Trống', 'KV000004'),
('P00000027', N'Phòng 27', 12, N'Trống', 'KV000004'),
('P00000028', N'Phòng 28', 12, N'Trống', 'KV000004'),
('P00000029', N'Phòng 29', 12, N'Trống', 'KV000004'),
('P00000030', N'Phòng 30', 12, N'Trống', 'KV000004'),
('P00000031', N'Phòng 31', 12, N'Trống', 'KV000004'),
('P00000032', N'Phòng 32', 12, N'Trống', 'KV000004'),
('P00000033', N'Phòng 33', 12, N'Trống', 'KV000005'),
('P00000034', N'Phòng 34', 12, N'Trống', 'KV000005'),
('P00000035', N'Phòng 35', 12, N'Trống', 'KV000005'),
('P00000036', N'Phòng 36', 12, N'Trống', 'KV000005'),
('P00000037', N'Phòng 37', 12, N'Trống', 'KV000005'),
('P00000038', N'Phòng 38', 12, N'Trống', 'KV000005'),
('P00000039', N'Phòng 39', 12, N'Trống', 'KV000005'),
('P00000040', N'Phòng 40', 12, N'Trống', 'KV000005'),
('P00000041', N'Phòng 41', 12, N'Trống', 'KV000006'),
('P00000042', N'Phòng 42', 12, N'Trống', 'KV000006'),
('P00000043', N'Phòng 43', 12, N'Trống', 'KV000006'),
('P00000044', N'Phòng 44', 12, N'Trống', 'KV000006'),
('P00000045', N'Phòng 45', 12, N'Trống', 'KV000006'),
('P00000046', N'Phòng 46', 12, N'Trống', 'KV000006'),
('P00000047', N'Phòng 47', 12, N'Trống', 'KV000006'),
('P00000048', N'Phòng 48', 12, N'Trống', 'KV000006'),
('P00000049', N'Phòng 49', 12, N'Trống', 'KV000007'),
('P00000050', N'Phòng 50', 12, N'Trống', 'KV000007'),
('P00000051', N'Phòng 51', 12, N'Trống', 'KV000007'),
('P00000052', N'Phòng 52', 12, N'Trống', 'KV000007'),
('P00000053', N'Phòng 53', 12, N'Trống', 'KV000007'),
('P00000054', N'Phòng 54', 12, N'Trống', 'KV000007'),
('P00000055', N'Phòng 55', 12, N'Trống', 'KV000007'),
('P00000056', N'Phòng 56', 12, N'Trống', 'KV000007'),
('P00000057', N'Phòng 57', 12, N'Trống', 'KV000008'),
('P00000058', N'Phòng 58', 12, N'Trống', 'KV000008'),
('P00000059', N'Phòng 59', 12, N'Trống', 'KV000008'),
('P00000060', N'Phòng 60', 12, N'Trống', 'KV000008'),
('P00000061', N'Phòng 61', 12, N'Trống', 'KV000008'),
('P00000062', N'Phòng 62', 12, N'Trống', 'KV000008'),
('P00000063', N'Phòng 63', 12, N'Trống', 'KV000008'),
('P00000064', N'Phòng 64', 12, N'Trống', 'KV000008'),
('P00000065', N'Phòng 65', 12, N'Trống', 'KV000009'),
('P00000066', N'Phòng 66', 12, N'Trống', 'KV000009'),
('P00000067', N'Phòng 67', 12, N'Trống', 'KV000009'),
('P00000068', N'Phòng 68', 12, N'Trống', 'KV000009'),
('P00000069', N'Phòng 69', 12, N'Trống', 'KV000009'),
('P00000070', N'Phòng 70', 12, N'Trống', 'KV000009'),
('P00000071', N'Phòng 71', 12, N'Trống', 'KV000009'),
('P00000072', N'Phòng 72', 12, N'Trống', 'KV000009'),
('P00000073', N'Phòng 73', 12, N'Trống', 'KV000010'),
('P00000074', N'Phòng 74', 12, N'Trống', 'KV000010'),
('P00000075', N'Phòng 75', 12, N'Trống', 'KV000010'),
('P00000076', N'Phòng 76', 12, N'Trống', 'KV000010'),
('P00000077', N'Phòng 77', 12, N'Trống', 'KV000010'),
('P00000078', N'Phòng 78', 12, N'Trống', 'KV000010'),
('P00000079', N'Phòng 79', 12, N'Trống', 'KV000010'),
('P00000080', N'Phòng 80', 12, N'Trống', 'KV000010');

-- Thêm dữ liệu vào bảng Ban
INSERT INTO Ban(maBan, tenBan, soLuongChoNgoi, trangThai, maKhuVuc) VALUES
('B000001', N'Bàn 1', 9, N'Trống', 'KV000001'),
('B000002', N'Bàn 2', 7, N'Trống', 'KV000001'),
('B000003', N'Bàn 3', 7, N'Trống', 'KV000001'),
('B000004', N'Bàn 4', 9, N'Trống', 'KV000001'),
('B000005', N'Bàn 5', 10, N'Trống', 'KV000001'),
('B000006', N'Bàn 6', 10, N'Trống', 'KV000001'),
('B000007', N'Bàn 7', 7, N'Trống', 'KV000001'),
('B000008', N'Bàn 8', 2, N'Trống', 'KV000001'),
('B000009', N'Bàn 9', 12, N'Trống', 'KV000002'),
('B000010', N'Bàn 10', 6, N'Trống', 'KV000002'),
('B000011', N'Bàn 11', 2, N'Trống', 'KV000002'),
('B000012', N'Bàn 12', 10, N'Trống', 'KV000002'),
('B000013', N'Bàn 13', 12, N'Trống', 'KV000002'),
('B000014', N'Bàn 14', 8, N'Trống', 'KV000002'),
('B000015', N'Bàn 15', 12, N'Trống', 'KV000002'),
('B000016', N'Bàn 16', 4, N'Trống', 'KV000002'),
('B000017', N'Bàn 17', 7, N'Trống', 'KV000003'),
('B000018', N'Bàn 18', 6, N'Trống', 'KV000003'),
('B000019', N'Bàn 19', 4, N'Trống', 'KV000003'),
('B000020', N'Bàn 20', 9, N'Trống', 'KV000003'),
('B000021', N'Bàn 21', 10, N'Trống', 'KV000003'),
('B000022', N'Bàn 22', 6, N'Trống', 'KV000003'),
('B000023', N'Bàn 23', 2, N'Trống', 'KV000003'),
('B000024', N'Bàn 24', 12, N'Trống', 'KV000003'),
('B000025', N'Bàn 25', 4, N'Trống', 'KV000004'),
('B000026', N'Bàn 26', 7, N'Trống', 'KV000004'),
('B000027', N'Bàn 27', 12, N'Trống', 'KV000004'),
('B000028', N'Bàn 28', 9, N'Trống', 'KV000004'),
('B000029', N'Bàn 29', 4, N'Trống', 'KV000004'),
('B000030', N'Bàn 30', 6, N'Trống', 'KV000004'),
('B000031', N'Bàn 31', 6, N'Trống', 'KV000004'),
('B000032', N'Bàn 32', 10, N'Trống', 'KV000004'),
('B000033', N'Bàn 33', 2, N'Trống', 'KV000005'),
('B000034', N'Bàn 34', 8, N'Trống', 'KV000005'),
('B000035', N'Bàn 35', 4, N'Trống', 'KV000005'),
('B000036', N'Bàn 36', 2, N'Trống', 'KV000005'),
('B000037', N'Bàn 37', 4, N'Trống', 'KV000005'),
('B000038', N'Bàn 38', 12, N'Trống', 'KV000005'),
('B000039', N'Bàn 39', 4, N'Trống', 'KV000005'),
('B000040', N'Bàn 40', 9, N'Trống', 'KV000005'),
('B000041', N'Bàn 41', 6, N'Trống', 'KV000006'),
('B000042', N'Bàn 42', 9, N'Trống', 'KV000006'),
('B000043', N'Bàn 43', 10, N'Trống', 'KV000006'),
('B000044', N'Bàn 44', 12, N'Trống', 'KV000006'),
('B000045', N'Bàn 45', 10, N'Trống', 'KV000006'),
('B000046', N'Bàn 46', 12, N'Trống', 'KV000006'),
('B000047', N'Bàn 47', 6, N'Trống', 'KV000006'),
('B000048', N'Bàn 48', 8, N'Trống', 'KV000006'),
('B000049', N'Bàn 49', 6, N'Trống', 'KV000007'),
('B000050', N'Bàn 50', 4, N'Trống', 'KV000007'),
('B000051', N'Bàn 51', 9, N'Trống', 'KV000007'),
('B000052', N'Bàn 52', 7, N'Trống', 'KV000007'),
('B000053', N'Bàn 53', 4, N'Trống', 'KV000007'),
('B000054', N'Bàn 54', 8, N'Trống', 'KV000007'),
('B000055', N'Bàn 55', 8, N'Trống', 'KV000007'),
('B000056', N'Bàn 56', 4, N'Trống', 'KV000007'),
('B000057', N'Bàn 57', 7, N'Có khách', 'KV000008'),
('B000058', N'Bàn 58', 12, N'Có khách', 'KV000008'),
('B000059', N'Bàn 59', 4, N'Trống', 'KV000008'),
('B000060', N'Bàn 60', 12, N'Trống', 'KV000008'),
('B000061', N'Bàn 61', 10, N'Trống', 'KV000008'),
('B000062', N'Bàn 62', 2, N'Có khách', 'KV000008'),
('B000063', N'Bàn 63', 4, N'Có khách', 'KV000008'),
('B000064', N'Bàn 64', 4, N'Trống', 'KV000008'),
('B000065', N'Bàn 65', 10, N'Trống', 'KV000009'),
('B000066', N'Bàn 66', 6, N'Trống', 'KV000009'),
('B000067', N'Bàn 67', 7, N'Trống', 'KV000009'),
('B000068', N'Bàn 68', 2, N'Trống', 'KV000009'),
('B000069', N'Bàn 69', 8, N'Trống', 'KV000009'),
('B000070', N'Bàn 70', 12, N'Trống', 'KV000009'),
('B000071', N'Bàn 71', 9, N'Trống', 'KV000009'),
('B000072', N'Bàn 72', 7, N'Trống', 'KV000009'),
('B000073', N'Bàn 73', 4, N'Trống', 'KV000010'),
('B000074', N'Bàn 74', 4, N'Có khách', 'KV000010'),
('B000075', N'Bàn 75', 10, N'Trống', 'KV000010'),
('B000076', N'Bàn 76', 4, N'Có khách', 'KV000010'),
('B000077', N'Bàn 77', 2, N'Trống', 'KV000010'),
('B000078', N'Bàn 78', 7, N'Có khách', 'KV000010'),
('B000079', N'Bàn 79', 4, N'Trống', 'KV000010'),
('B000080', N'Bàn 80', 2, N'Trống', 'KV000010');

insert into LoaiMonAn(maLoaiMonAn,tenLoaiMonAn) values
('LMA0001',N'Lẩu'),
('LMA0002',N'Nước'),
('LMA0003',N'Món Chiên'),
('LMA0004',N'Món Cay'),
('LMA0005',N'Món Hầm'),
('LMA0006',N'Món Cuốn'),
('LMA0007',N'Món Tráng Miệng'),
('LMA0008',N'Trái Cây'),
('LMA0009',N'Món Xào'),
('LMA0010',N'Rau Xào'),
('LMA0011',N'Món Chấm'),
('LMA0012',N'Món Khô'),
('LMA0013',N'Món nước');

INSERT INTO MonAn(maMonAn, tenMonAn, giaMonAn, moTa, donViTinh, hinhAnh, trangThai, maLoaiMonAn) VALUES
('MA000001', N'Lẩu Nhật Bản', 200000, N'một món thích hợp cho mùa đông', N'Phần', 'images/monAn/launhatban.jpg', N'Đang Phục Vụ', 'LMA0001'),
('MA000002', N'Bún Nước Lèo', 40000, N'một món rất ngon của người miền tây', N'Tô', 'images/monAn/bunnuocleo.jpg', N'Đang Phục Vụ', 'LMA0013'),
('MA000003', N'Cá Hấp', 300000, N'Cá Thiên Nhiên', N'Dĩa', 'images/monAn/cahap.jpg', N'Đang Phục Vụ', 'LMA0005'),
('MA000004', N'Cháo Thịt', 20000, N'một món thích hợp cho trẻ nhỏ', N'Tô', 'images/monAn/chaothit.jpg', N'Đang Phục Vụ', 'LMA0005'),
('MA000005', N'Cơm Chiên', 200000, N'một món bán rất chạy', N'Dĩa', 'images/monAn/comchien.jpg', N'Đang Phục Vụ', 'LMA0003'),
('MA000006', N'Gà Hấp', 350000, N'Gà Thả vườn đảm bảo thịt dai', N'Con', 'images/monAn/gahap.jpg', N'Đang Phục Vụ', 'LMA0005'),
('MA000007', N'Lẩu Cua Đồng', 250000, N'một món đặc sản của nước ta', N'Phần', 'images/monAn/laucuadong.jpg', N'Đang Phục Vụ', 'LMA0001'),
('MA000008', N'Mì Quảng', 40000, N'một món rất ngon', N'Tô', 'images/monAn/miquang.jpg', N'Đang Phục Vụ', 'LMA0013'),
('MA000009', N'Mì Xào Bò', 200000, N'một món cũng ngon không kém', N'Tô', 'images/monAn/mixaobo.jpg', N'Đang Phục Vụ', 'LMA0009'),
('MA000010', N'Rau Muống Xào Tỏi', 200000, N'một món dân dã', N'Dĩa', 'images/monAn/rauxao.jpg', N'Đang Phục Vụ', 'LMA0010'),
('MA000011', N'Bánh Xèo', 150000, N'Bánh xèo giòn, nhân tôm, thịt, giá, chấm nước mắm', N'Cái', 'images/monAn/banh_xeo.jpg', N'Đang Phục Vụ', 'LMA0003'),
('MA000012', N'Gà Chiên Mắm', 180000, N'Cánh gà chiên giòn thấm nước mắm thơm, đậm đà', N'Dĩa', 'images/monAn/ga_chien_mam.jpg', N'Đang Phục Vụ', 'LMA0003'),
('MA000013', N'Ếch Xào Sả Ớt', 220000, N'Ếch xào sả ớt cay nồng, thơm, kích thích vị giác', N'Dĩa', 'images/monAn/ech_xao.jpg', N'Đang Phục Vụ', 'LMA0004'),
('MA000014', N'Cá Kho Tộ', 250000, N'Cá kho đậm đà với ớt, tiêu, ăn kèm cơm trắng', N'Niêu', 'images/monAn/ca_kho_to.jpg', N'Đang Phục Vụ', 'LMA0005'),
('MA000015', N'Gỏi Cuốn Tôm Thịt', 100000, N'Gỏi cuốn tôm, thịt, rau sống, chấm nước mắm', N'Phần', 'images/monAn/goi_cuon.jpg', N'Đang Phục Vụ', 'LMA0006'),
('MA000016', N'Phở Cuốn', 120000, N'Phở cuốn thịt bò xào, rau thơm, dưa chuột', N'Phần', 'images/monAn/pho_cuon.jpg', N'Đang Phục Vụ', 'LMA0006'),
('MA000017', N'Chè Ba Màu', 30000, N'Chè ngọt mát, đậu đỏ, đậu xanh, thạch dừa', N'Ly', 'images/monAn/che.jpg', N'Đang Phục Vụ', 'LMA0007'),
('MA000018', N'Bánh Da Lợn', 50000, N'Bánh dẻo, đậu xanh, lá dứa, ngọt thanh', N'Phần', 'images/monAn/banh_da_lon.jpg', N'Đang Phục Vụ', 'LMA0007'),
('MA000019', N'Dĩa Trái Cây Tươi', 80000, N'Trái cây tươi: xoài, dứa, dưa hấu, thanh long', N'Dĩa', 'images/monAn/dia_trai_cay.jpg', N'Đang Phục Vụ', 'LMA0008'),
('MA000020', N'Sinh Tố Xoài', 45000, N'Sinh tố xoài thơm ngon, giải nhiệt', N'Ly', 'images/monAn/sinh_to_xoai.jpg', N'Đang Phục Vụ', 'LMA0008'),
('MA000021', N'Sườn Nướng BBQ Cay', 300000, N'Sườn nướng sốt BBQ cay nhẹ, mềm, đậm vị', N'Phần', 'images/monAn/suon_bbq.jpg', N'Đang Phục Vụ', 'LMA0004'),
('MA000022', N'Wrap Gà Nướng', 150000, N'Wrap gà nướng, rau diếp, cà chua, sốt Caesar', N'Cái', 'images/monAn/wrap.jpg', N'Đang Phục Vụ', 'LMA0006'),
('MA000023', N'Tiramisu', 80000, N'Tiramisu Ý, kem mascarpone, cà phê, cacao', N'Miếng', 'images/monAn/tiramisu.jpg', N'Đang Phục Vụ', 'LMA0007'),
('MA000024', N'Panna Cotta', 70000, N'Kem sữa Ý mềm mịn, phủ sốt dâu tây ngọt ngào', N'Ly', 'images/monAn/panna.jpg', N'Đang Phục Vụ', 'LMA0007'),
('MA000025', N'Salat Trái Cây', 90000, N'Hỗn hợp dâu tây, táo, nho, cam, ăn kèm sữa chua', N'Dĩa', 'images/monAn/salat.jpg', N'Đang Phục Vụ', 'LMA0008');

-- Thêm dữ liệu vào bảng HoaDon
INSERT INTO HoaDon(maHoaDon, ngayLapHoaDon, diaChiNhaHang, thueVAT, tienKhachDua, phiDichVu, soDienThoaiNhaHang, maNhanVien, soDienThoai, maBan, maPhong) VALUES
('HD000001', '2022-01-22 21:19:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2047000.0, 0.05, '020930041975', 'NV0006', '0382740515', 'B000065', 'P00000010'),
('HD000002', '2022-08-03 11:52:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 287500.0, 0.05, '020930041975', 'NV0003', '0382740504', 'B000031', NULL),
('HD000003', '2022-01-16 12:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 552000.0, 0.05, '020930041975', 'NV0015', '0382740504', 'B000039', NULL),
('HD000004', '2022-09-01 21:22:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 759000.0, 0.05, '020930041975', 'NV0015', '0382740513', 'B000074', NULL),
('HD000005', '2022-07-26 15:37:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 46000.0, 0.05, '020930041975', 'NV0003', '0382740512', 'B000011', NULL),
('HD000006', '2022-02-27 18:54:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 805000.0, 0.05, '020930041975', 'NV0008', '0382740503', 'B000066', 'P00000072'),
('HD000007', '2022-09-03 21:25:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 862500.0, 0.05, '020930041975', 'NV0012', '0382740509', 'B000030', NULL),
('HD000008', '2022-02-21 15:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1334000.0, 0.05, '020930041975', 'NV0004', '0382740512', 'B000047', 'P00000051'),
('HD000009', '2022-08-04 14:38:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 368000.0, 0.05, '020930041975', 'NV0002', '0382740511', 'B000057', 'P00000002'),
('HD000010', '2022-07-14 14:52:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1219000.0, 0.05, '020930041975', 'NV0008', '0382740504', 'B000026', NULL),
('HD000011', '2022-11-29 16:17:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1759500.0, 0.05, '020930041975', 'NV0020', '0382740515', 'B000008', NULL),
('HD000012', '2022-01-22 20:34:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1598500.0, 0.05, '020930041975', 'NV0003', '0382740513', 'B000002', 'P00000048'),
('HD000013', '2022-08-15 09:41:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1299500.0, 0.05, '020930041975', 'NV0004', '0382740502', 'B000039', 'P00000019'),
('HD000014', '2022-07-05 12:43:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 414000.0, 0.05, '020930041975', 'NV0013', '0382740513', 'B000035', NULL),
('HD000015', '2022-01-07 12:02:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 299000.0, 0.05, '020930041975', 'NV0011', '0382740506', 'B000019', 'P00000061'),
('HD000016', '2022-02-17 08:17:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 690000.0, 0.05, '020930041975', 'NV0019', '0382740513', 'B000035', 'P00000080'),
('HD000017', '2022-01-04 16:50:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 782000.0, 0.05, '020930041975', 'NV0014', '0382740506', 'B000029', 'P00000017'),
('HD000018', '2022-07-19 19:55:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 575000.0, 0.05, '020930041975', 'NV0012', '0382740512', 'B000034', NULL),
('HD000019', '2022-01-29 19:13:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1932000.0, 0.05, '020930041975', 'NV0015', '0382740507', 'B000071', NULL),
('HD000020', '2022-09-23 19:19:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 770500.0, 0.05, '020930041975', 'NV0009', '0382740504', 'B000006', 'P00000073'),
('HD000021', '2022-08-10 08:06:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1104000.0, 0.05, '020930041975', 'NV0013', '0382740515', 'B000014', 'P00000040'),
('HD000022', '2022-02-12 22:00:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 529000.0, 0.05, '020930041975', 'NV0015', '0382740505', 'B000049', 'P00000050'),
('HD000023', '2022-07-05 17:21:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 414000.0, 0.05, '020930041975', 'NV0017', '0382740515', 'B000059', NULL),
('HD000024', '2022-01-16 10:40:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1242000.0, 0.05, '020930041975', 'NV0008', '0382740514', 'B000033', NULL),
('HD000025', '2022-09-02 20:49:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1104000.0, 0.05, '020930041975', 'NV0010', '0382740501', 'B000071', 'P00000024'),
('HD000026', '2022-02-19 15:07:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 644000.0, 0.05, '020930041975', 'NV0006', '0382740504', 'B000037', 'P00000072'),
('HD000027', '2022-02-04 10:32:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 851000.0, 0.05, '020930041975', 'NV0018', '0382740510', 'B000047', NULL),
('HD000028', '2022-01-11 17:25:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 425500.0, 0.05, '020930041975', 'NV0006', '0382740501', 'B000028', 'P00000013'),
('HD000029', '2022-02-24 17:24:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 609500.0, 0.05, '020930041975', 'NV0004', '0382740503', 'B000016', NULL),
('HD000030', '2022-08-03 12:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 885500.0, 0.05, '020930041975', 'NV0003', '0382740513', 'B000010', NULL),
('HD000031', '2022-08-23 10:00:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 874000.0, 0.05, '020930041975', 'NV0016', '0382740503', 'B000064', NULL),
('HD000032', '2022-11-04 08:13:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 684250.0, 0.05, '020930041975', 'NV0013', '0382740507', 'B000022', NULL),
('HD000033', '2022-09-07 12:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 69000.0, 0.05, '020930041975', 'NV0016', '0382740504', 'B000017', NULL),
('HD000034', '2022-08-24 08:16:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 115000.0, 0.05, '020930041975', 'NV0008', '0382740513', 'B000050', NULL),
('HD000035', '2022-08-24 09:34:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1851500.0, 0.05, '020930041975', 'NV0009', '0382740515', 'B000006', 'P00000044'),
('HD000036', '2022-01-01 09:02:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2116000.0, 0.05, '020930041975', 'NV0010', '0382740515', 'B000065', 'P00000047'),
('HD000037', '2022-09-22 19:31:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 494500.0, 0.05, '020930041975', 'NV0009', '0382740502', 'B000050', 'P00000056'),
('HD000038', '2022-07-14 09:54:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 442750.0, 0.05, '020930041975', 'NV0019', '0382740513', 'B000027', NULL),
('HD000039', '2022-08-06 09:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1840000.0, 0.05, '020930041975', 'NV0012', '0382740515', 'B000074', 'P00000033'),
('HD000040', '2022-11-14 20:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 92000.0, 0.05, '020930041975', 'NV0010', '0382740506', 'B000056', NULL),
('HD000041', '2022-01-26 09:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 517500.0, 0.05, '020930041975', 'NV0017', '0382740502', 'B000046', 'P00000007'),
('HD000042', '2022-08-06 12:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1035000.0, 0.05, '020930041975', 'NV0017', '0382740512', 'B000070', 'P00000022'),
('HD000043', '2022-01-24 21:26:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1288000.0, 0.05, '020930041975', 'NV0007', '0382740503', 'B000075', NULL),
('HD000044', '2022-09-06 09:41:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 920000.0, 0.05, '020930041975', 'NV0020', '0382740505', 'B000032', 'P00000062'),
('HD000045', '2022-07-09 14:24:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 690000.0, 0.05, '020930041975', 'NV0014', '0382740507', 'B000011', 'P00000053'),
('HD000046', '2022-08-23 08:41:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1144250.0, 0.05, '020930041975', 'NV0013', '0382740515', 'B000079', NULL),
('HD000047', '2022-08-05 12:54:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 747500.0, 0.05, '020930041975', 'NV0014', '0382740509', 'B000045', 'P00000035'),
('HD000048', '2022-01-13 14:06:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1897500.0, 0.05, '020930041975', 'NV0002', '0382740507', 'B000053', NULL),
('HD000049', '2022-11-24 15:54:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 115000.0, 0.05, '020930041975', 'NV0004', '0382740515', 'B000067', NULL),
('HD000050', '2022-01-25 13:48:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 874000.0, 0.05, '020930041975', 'NV0010', '0382740507', 'B000008', NULL),
('HD000051', '2022-02-08 17:31:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 805000.0, 0.05, '020930041975', 'NV0019', '0382740506', 'B000019', NULL),
('HD000052', '2022-01-10 16:17:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1035000.0, 0.05, '020930041975', 'NV0020', '0382740513', 'B000031', NULL),
('HD000053', '2022-07-15 10:31:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2334500.0, 0.05, '020930041975', 'NV0020', '0382740509', 'B000017', NULL),
('HD000054', '2022-09-01 13:30:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1805500.0, 0.05, '020930041975', 'NV0007', '0382740507', 'B000046', 'P00000050'),
('HD000055', '2022-01-01 10:49:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1288000.0, 0.05, '020930041975', 'NV0016', '0382740503', 'B000034', 'P00000003'),
('HD000056', '2022-01-22 13:20:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 92000.0, 0.05, '020930041975', 'NV0009', '0382740514', 'B000077', NULL),
('HD000057', '2022-02-03 21:56:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1552500.0, 0.05, '020930041975', 'NV0019', '0382740507', 'B000033', 'P00000035'),
('HD000058', '2022-02-04 17:35:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 954500.0, 0.05, '020930041975', 'NV0012', '0382740504', 'B000077', NULL),
('HD000059', '2022-01-17 08:23:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1000500.0, 0.05, '020930041975', 'NV0005', '0382740506', 'B000028', 'P00000075'),
('HD000060', '2022-08-19 21:59:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 839500.0, 0.05, '020930041975', 'NV0014', '0382740504', 'B000031', NULL),
('HD000061', '2023-05-03 15:38:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 759000.0, 0.05, '020930041975', 'NV0007', '0382740504', 'B000025', 'P00000027'),
('HD000062', '2023-07-15 11:01:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 931500.0, 0.05, '020930041975', 'NV0019', '0382740501', 'B000077', 'P00000050'),
('HD000063', '2023-04-18 15:06:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1529500.0, 0.05, '020930041975', 'NV0020', '0382740508', 'B000059', NULL),
('HD000064', '2023-04-03 16:48:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2357500.0, 0.05, '020930041975', 'NV0012', '0382740501', 'B000064', 'P00000030'),
('HD000065', '2023-02-08 18:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1575500.0, 0.05, '020930041975', 'NV0017', '0382740501', 'B000001', 'P00000037'),
('HD000066', '2023-04-09 12:39:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 23000.0, 0.05, '020930041975', 'NV0019', '0382740514', 'B000038', 'P00000011'),
('HD000067', '2023-07-26 14:37:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1035000.0, 0.05, '020930041975', 'NV0008', '0382740511', 'B000029', 'P00000046'),
('HD000068', '2023-07-20 08:02:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1253500.0, 0.05, '020930041975', 'NV0005', '0382740513', 'B000029', 'P00000009'),
('HD000069', '2023-04-28 21:01:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1840000.0, 0.05, '020930041975', 'NV0016', '0382740511', 'B000033', NULL),
('HD000070', '2023-03-03 14:48:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 345000.0, 0.05, '020930041975', 'NV0013', '0382740511', 'B000031', 'P00000053'),
('HD000071', '2023-10-28 14:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1449000.0, 0.05, '020930041975', 'NV0018', '0382740511', 'B000069', NULL),
('HD000072', '2023-02-19 09:16:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1242000.0, 0.05, '020930041975', 'NV0019', '0382740505', 'B000071', NULL),
('HD000073', '2023-10-29 08:39:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1012000.0, 0.05, '020930041975', 'NV0005', '0382740509', 'B000048', NULL),
('HD000074', '2023-03-20 10:46:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1575500.0, 0.05, '020930041975', 'NV0014', '0382740510', 'B000014', 'P00000023'),
('HD000075', '2023-03-06 16:12:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1058000.0, 0.05, '020930041975', 'NV0016', '0382740514', 'B000010', NULL),
('HD000076', '2023-03-03 19:21:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 460000.0, 0.05, '020930041975', 'NV0003', '0382740509', 'B000013', 'P00000018'),
('HD000077', '2023-02-17 17:26:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 701500.0, 0.05, '020930041975', 'NV0019', '0382740514', 'B000013', 'P00000019'),
('HD000078', '2023-07-09 13:07:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 586500.0, 0.05, '020930041975', 'NV0006', '0382740501', 'B000055', NULL),
('HD000079', '2023-07-13 18:26:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1518000.0, 0.05, '020930041975', 'NV0001', '0382740512', 'B000001', 'P00000076'),
('HD000080', '2023-04-22 19:31:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 230000.0, 0.05, '020930041975', 'NV0008', '0382740509', 'B000021', NULL),
('HD000081', '2023-04-29 17:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2806000.0, 0.05, '020930041975', 'NV0007', '0382740502', 'B000054', 'P00000038'),
('HD000082', '2023-05-11 13:24:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 563500.0, 0.05, '020930041975', 'NV0019', '0382740503', 'B000074', 'P00000075'),
('HD000083', '2023-05-11 10:18:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1230500.0, 0.05, '020930041975', 'NV0006', '0382740502', 'B000029', NULL),
('HD000084', '2023-03-06 11:33:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1966500.0, 0.05, '020930041975', 'NV0007', '0382740501', 'B000047', 'P00000014'),
('HD000085', '2023-05-11 15:26:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 69000.0, 0.05, '020930041975', 'NV0018', '0382740507', 'B000079', 'P00000009'),
('HD000086', '2023-07-29 15:18:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 460000.0, 0.05, '020930041975', 'NV0005', '0382740507', 'B000026', 'P00000041'),
('HD000087', '2023-04-10 08:37:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 69000.0, 0.05, '020930041975', 'NV0008', '0382740508', 'B000069', 'P00000059'),
('HD000088', '2023-04-02 10:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2932500.0, 0.05, '020930041975', 'NV0015', '0382740501', 'B000038', NULL),
('HD000089', '2023-07-02 15:27:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1748000.0, 0.05, '020930041975', 'NV0005', '0382740514', 'B000015', NULL),
('HD000090', '2023-04-04 17:45:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1311000.0, 0.05, '020930041975', 'NV0007', '0382740507', 'B000027', 'P00000024'),
('HD000091', '2023-03-19 18:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 690000.0, 0.05, '020930041975', 'NV0018', '0382740515', 'B000020', 'P00000030'),
('HD000092', '2023-07-26 13:42:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 690000.0, 0.05, '020930041975', 'NV0018', '0382740509', 'B000059', NULL),
('HD000093', '2023-04-16 18:37:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1679000.0, 0.05, '020930041975', 'NV0018', '0382740508', 'B000007', 'P00000014'),
('HD000094', '2023-04-12 13:23:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 195500.0, 0.05, '020930041975', 'NV0016', '0382740511', 'B000003', 'P00000067'),
('HD000095', '2023-10-09 21:52:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 115000.0, 0.05, '020930041975', 'NV0002', '0382740501', 'B000005', NULL),
('HD000096', '2023-05-09 19:39:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1966500.0, 0.05, '020930041975', 'NV0011', '0382740512', 'B000063', NULL),
('HD000097', '2023-10-27 09:14:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 805000.0, 0.05, '020930041975', 'NV0012', '0382740515', 'B000073', 'P00000079'),
('HD000098', '2023-07-18 12:01:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1546750.0, 0.05, '020930041975', 'NV0009', '0382740507', 'B000016', NULL),
('HD000099', '2023-02-10 20:32:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 897000.0, 0.05, '020930041975', 'NV0006', '0382740505', 'B000062', 'P00000013'),
('HD000100', '2023-04-19 17:12:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 977500.0, 0.05, '020930041975', 'NV0008', '0382740511', 'B000030', NULL),
('HD000101', '2023-05-29 20:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1621500.0, 0.05, '020930041975', 'NV0008', '0382740503', 'B000032', NULL),
('HD000102', '2023-07-10 09:01:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2070000.0, 0.05, '020930041975', 'NV0019', '0382740508', 'B000077', NULL),
('HD000103', '2023-02-10 15:06:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1178750.0, 0.05, '020930041975', 'NV0013', '0382740514', 'B000062', NULL),
('HD000104', '2023-02-27 10:07:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 713000.0, 0.05, '020930041975', 'NV0018', '0382740502', 'B000020', NULL),
('HD000105', '2023-10-21 13:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2242500.0, 0.05, '020930041975', 'NV0017', '0382740505', 'B000037', NULL),
('HD000106', '2023-04-08 19:47:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1000500.0, 0.05, '020930041975', 'NV0005', '0382740515', 'B000052', 'P00000057'),
('HD000107', '2023-10-23 09:27:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 345000.0, 0.05, '020930041975', 'NV0009', '0382740501', 'B000075', NULL),
('HD000108', '2023-04-14 17:39:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 23000.0, 0.05, '020930041975', 'NV0011', '0382740507', 'B000038', 'P00000026'),
('HD000109', '2023-05-12 22:05:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 517500.0, 0.05, '020930041975', 'NV0016', '0382740508', 'B000022', 'P00000030'),
('HD000110', '2023-03-05 13:10:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 908500.0, 0.05, '020930041975', 'NV0019', '0382740505', 'B000023', 'P00000042'),
('HD000111', '2023-04-12 14:33:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1196000.0, 0.05, '020930041975', 'NV0003', '0382740508', 'B000005', NULL),
('HD000112', '2023-07-19 19:25:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 621000.0, 0.05, '020930041975', 'NV0002', '0382740504', 'B000074', 'P00000049'),
('HD000113', '2023-02-08 09:08:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1483500.0, 0.05, '020930041975', 'NV0020', '0382740511', 'B000061', NULL),
('HD000114', '2023-03-14 10:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 379500.0, 0.05, '020930041975', 'NV0012', '0382740515', 'B000024', 'P00000065'),
('HD000115', '2023-10-23 20:05:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 368000.0, 0.05, '020930041975', 'NV0015', '0382740504', 'B000010', 'P00000073'),
('HD000116', '2023-04-04 20:14:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 885500.0, 0.05, '020930041975', 'NV0007', '0382740506', 'B000066', 'P00000015'),
('HD000117', '2023-05-02 17:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 621000.0, 0.05, '020930041975', 'NV0011', '0382740512', 'B000079', 'P00000055'),
('HD000118', '2023-02-18 10:59:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 690000.0, 0.05, '020930041975', 'NV0007', '0382740504', 'B000027', 'P00000062'),
('HD000119', '2023-05-04 14:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1380000.0, 0.05, '020930041975', 'NV0005', '0382740503', 'B000010', 'P00000059'),
('HD000120', '2023-10-19 09:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 575000.0, 0.05, '020930041975', 'NV0011', '0382740515', 'B000071', 'P00000052'),
('HD000121', '2024-03-16 22:59:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 172500.0, 0.05, '020930041975', 'NV0017', '0382740505', 'B000072', NULL),
('HD000122', '2024-12-30 08:19:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 937250.0, 0.05, '020930041975', 'NV0019', '0382740511', 'B000057', 'P00000056'),
('HD000123', '2024-02-14 19:11:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2242500.0, 0.05, '020930041975', 'NV0012', '0382740501', 'B000004', 'P00000076'),
('HD000124', '2024-01-04 17:12:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1863000.0, 0.05, '020930041975', 'NV0008', '0382740505', 'B000039', NULL),
('HD000125', '2024-01-04 19:41:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 805000.0, 0.05, '020930041975', 'NV0010', '0382740502', 'B000042', 'P00000028'),
('HD000126', '2024-03-15 21:19:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 552000.0, 0.05, '020930041975', 'NV0017', '0382740505', 'B000068', 'P00000077'),
('HD000127', '2024-03-08 12:11:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1345500.0, 0.05, '020930041975', 'NV0006', '0382740505', 'B000005', NULL),
('HD000128', '2024-01-01 18:30:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 736000.0, 0.05, '020930041975', 'NV0017', '0382740507', 'B000007', NULL),
('HD000129', '2024-06-17 17:27:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 138000.0, 0.05, '020930041975', 'NV0018', '0382740505', 'B000037', NULL),
('HD000130', '2024-02-15 09:48:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1150000.0, 0.05, '020930041975', 'NV0013', '0382740515', 'B000060', 'P00000038'),
('HD000131', '2024-12-19 11:49:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 402500.0, 0.05, '020930041975', 'NV0005', '0382740512', 'B000048', 'P00000014'),
('HD000132', '2024-05-02 19:54:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 138000.0, 0.05, '020930041975', 'NV0011', '0382740508', 'B000013', 'P00000075'),
('HD000133', '2024-06-17 15:46:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 218500.0, 0.05, '020930041975', 'NV0013', '0382740506', 'B000022', NULL),
('HD000134', '2024-03-18 20:41:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 276000.0, 0.05, '020930041975', 'NV0007', '0382740511', 'B000004', NULL),
('HD000135', '2024-05-05 19:41:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 736000.0, 0.05, '020930041975', 'NV0019', '0382740510', 'B000033', 'P00000048'),
('HD000136', '2024-01-23 08:39:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 333500.0, 0.05, '020930041975', 'NV0015', '0382740507', 'B000021', NULL),
('HD000137', '2024-02-18 22:12:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 287500.0, 0.05, '020930041975', 'NV0001', '0382740514', 'B000043', NULL),
('HD000138', '2024-03-28 16:32:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 563500.0, 0.05, '020930041975', 'NV0020', '0382740514', 'B000059', NULL),
('HD000139', '2024-03-28 16:35:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 552000.0, 0.05, '020930041975', 'NV0003', '0382740513', 'B000063', NULL),
('HD000140', '2024-02-26 17:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1598500.0, 0.05, '020930041975', 'NV0017', '0382740501', 'B000041', 'P00000053'),
('HD000141', '2024-02-08 17:33:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 644000.0, 0.05, '020930041975', 'NV0004', '0382740504', 'B000014', 'P00000019'),
('HD000142', '2024-03-27 10:50:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1633000.0, 0.05, '020930041975', 'NV0020', '0382740509', 'B000076', NULL),
('HD000143', '2024-06-08 09:34:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 172500.0, 0.05, '020930041975', 'NV0015', '0382740508', 'B000010', 'P00000047'),
('HD000144', '2024-02-01 16:53:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1069500.0, 0.05, '020930041975', 'NV0005', '0382740512', 'B000070', 'P00000027'),
('HD000145', '2024-03-22 19:48:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 276000.0, 0.05, '020930041975', 'NV0010', '0382740515', 'B000075', NULL),
('HD000146', '2024-12-10 16:11:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 299000.0, 0.05, '020930041975', 'NV0014', '0382740506', 'B000028', NULL),
('HD000147', '2024-01-04 13:42:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1495000.0, 0.05, '020930041975', 'NV0013', '0382740506', 'B000070', NULL),
('HD000148', '2024-01-23 21:19:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 667000.0, 0.05, '020930041975', 'NV0006', '0382740508', 'B000076', 'P00000011'),
('HD000149', '2024-06-02 13:15:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 931500.0, 0.05, '020930041975', 'NV0013', '0382740507', 'B000067', 'P00000055'),
('HD000150', '2024-06-24 13:26:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1633000.0, 0.05, '020930041975', 'NV0009', '0382740515', 'B000005', 'P00000015'),
('HD000151', '2024-05-05 21:06:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1621500.0, 0.05, '020930041975', 'NV0003', '0382740501', 'B000020', 'P00000065'),
('HD000152', '2024-02-10 19:10:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2093000.0, 0.05, '020930041975', 'NV0008', '0382740504', 'B000003', 'P00000054'),
('HD000153', '2024-02-10 15:02:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 115000.0, 0.05, '020930041975', 'NV0016', '0382740514', 'B000017', 'P00000055'),
('HD000154', '2024-03-16 09:17:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 690000.0, 0.05, '020930041975', 'NV0003', '0382740502', 'B000006', NULL),
('HD000155', '2024-06-29 19:07:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1173000.0, 0.05, '020930041975', 'NV0005', '0382740504', 'B000057', NULL),
('HD000156', '2024-03-04 20:16:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1587000.0, 0.05, '020930041975', 'NV0020', '0382740513', 'B000002', NULL),
('HD000157', '2024-12-03 12:34:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1012000.0, 0.05, '020930041975', 'NV0019', '0382740514', 'B000039', NULL),
('HD000158', '2024-12-29 20:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1035000.0, 0.05, '020930041975', 'NV0013', '0382740504', 'B000003', NULL),
('HD000159', '2024-12-09 22:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 632500.0, 0.05, '020930041975', 'NV0011', '0382740501', 'B000042', 'P00000002'),
('HD000160', '2024-05-21 12:57:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 897000.0, 0.05, '020930041975', 'NV0016', '0382740511', 'B000038', 'P00000009'),
('HD000161', '2024-05-13 16:46:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 977500.0, 0.05, '020930041975', 'NV0010', '0382740510', 'B000075', NULL),
('HD000162', '2024-06-22 20:31:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1276500.0, 0.05, '020930041975', 'NV0019', '0382740514', 'B000066', 'P00000020'),
('HD000163', '2024-06-04 21:39:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 943000.0, 0.05, '020930041975', 'NV0018', '0382740508', 'B000072', 'P00000026'),
('HD000164', '2024-12-01 18:43:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2932500.0, 0.05, '020930041975', 'NV0020', '0382740513', 'B000042', 'P00000005'),
('HD000165', '2024-06-24 17:22:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 103500.0, 0.05, '020930041975', 'NV0016', '0382740503', 'B000031', NULL),
('HD000166', '2024-03-28 13:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 230000.0, 0.05, '020930041975', 'NV0004', '0382740506', 'B000033', 'P00000073'),
('HD000167', '2024-01-29 22:58:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1610000.0, 0.05, '020930041975', 'NV0013', '0382740501', 'B000068', 'P00000014'),
('HD000168', '2024-06-30 10:51:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 966000.0, 0.05, '020930041975', 'NV0009', '0382740503', 'B000025', 'P00000073'),
('HD000169', '2024-06-30 08:09:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 23000.0, 0.05, '020930041975', 'NV0011', '0382740508', 'B000003', 'P00000014'),
('HD000170', '2024-05-20 09:09:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2478250.0, 0.05, '020930041975', 'NV0011', '0382740501', 'B000059', NULL),
('HD000171', '2024-06-22 20:58:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 2185000.0, 0.05, '020930041975', 'NV0002', '0382740511', 'B000070', 'P00000049'),
('HD000172', '2024-01-22 21:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 92000.0, 0.05, '020930041975', 'NV0017', '0382740514', 'B000014', NULL),
('HD000173', '2024-06-02 16:28:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 138000.0, 0.05, '020930041975', 'NV0007', '0382740510', 'B000072', NULL),
('HD000174', '2024-03-21 21:44:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1081000.0, 0.05, '020930041975', 'NV0002', '0382740511', 'B000066', 'P00000073'),
('HD000175', '2024-03-06 14:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 741750.0, 0.05, '020930041975', 'NV0002', '0382740508', 'B000049', 'P00000004'),
('HD000176', '2024-02-13 16:25:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 310500.0, 0.05, '020930041975', 'NV0018', '0382740508', 'B000064', NULL),
('HD000177', '2024-03-09 17:56:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 345000.0, 0.05, '020930041975', 'NV0015', '0382740508', 'B000051', NULL),
('HD000178', '2024-06-25 18:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1426000.0, 0.05, '020930041975', 'NV0002', '0382740511', 'B000029', NULL),
('HD000179', '2024-12-31 19:16:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 345000.0, 0.05, '020930041975', 'NV0020', '0382740507', 'B000066', NULL),
('HD000180', '2024-02-11 18:56:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1782500.0, 0.05, '020930041975', 'NV0019', '0382740513', 'B000050', NULL),
('HD000181', '2025-05-06 14:36:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 741750.0, 0.05, '020930041975', 'NV0002', '0382740508', 'B000049', 'P00000004'),
('HD000182', '2025-05-13 16:25:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 310500.0, 0.05, '020930041975', 'NV0018', '0382740508', 'B000064', NULL),
('HD000183', '2025-03-09 17:56:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 345000.0, 0.05, '020930041975', 'NV0015', '0382740508', 'B000051', NULL),
('HD000184', '2025-04-25 18:29:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1426000.0, 0.05, '020930041975', 'NV0002', '0382740511', 'B000029', NULL),
('HD000185', '2025-01-31 19:16:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 345000.0, 0.05, '020930041975', 'NV0020', '0382740507', 'B000066', NULL),
('HD000186', '2025-02-11 18:56:00', N'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, Thành phố Hồ Chí Minh, Việt Nam', 0.1, 1782500.0, 0.05, '020930041975', 'NV0019', '0382740513', 'B000050', NULL);

-- Thêm dữ liệu vào bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon(maHoaDon, maMonAn, soLuong, donGia) VALUES
('HD000001', 'MA000014', 3, 250000),
('HD000001', 'MA000025', 2, 90000),
('HD000001', 'MA000021', 2, 300000),
('HD000001', 'MA000005', 1, 200000),
('HD000001', 'MA000018', 1, 50000),
('HD000002', 'MA000007', 1, 250000),
('HD000003', 'MA000012', 2, 180000),
('HD000003', 'MA000016', 1, 120000),
('HD000004', 'MA000021', 1, 300000),
('HD000004', 'MA000016', 3, 120000),
('HD000005', 'MA000002', 1, 40000),
('HD000006', 'MA000018', 2, 50000),
('HD000006', 'MA000001', 3, 200000),
('HD000007', 'MA000007', 3, 250000),
('HD000008', 'MA000002', 2, 40000),
('HD000008', 'MA000012', 3, 180000),
('HD000008', 'MA000025', 1, 90000),
('HD000008', 'MA000011', 3, 150000),
('HD000009', 'MA000019', 3, 80000),
('HD000009', 'MA000002', 2, 40000),
('HD000010', 'MA000025', 2, 90000),
('HD000010', 'MA000007', 3, 250000),
('HD000010', 'MA000002', 2, 40000),
('HD000010', 'MA000018', 1, 50000),
('HD000011', 'MA000010', 2, 200000),
('HD000011', 'MA000002', 2, 40000),
('HD000011', 'MA000011', 3, 150000),
('HD000011', 'MA000001', 3, 200000),
('HD000012', 'MA000016', 2, 120000),
('HD000012', 'MA000018', 3, 50000),
('HD000012', 'MA000001', 2, 200000),
('HD000012', 'MA000010', 3, 200000),
('HD000013', 'MA000022', 3, 150000),
('HD000013', 'MA000016', 2, 120000),
('HD000013', 'MA000013', 2, 220000),
('HD000014', 'MA000004', 3, 20000),
('HD000014', 'MA000015', 3, 100000),
('HD000015', 'MA000017', 2, 30000),
('HD000015', 'MA000010', 1, 200000),
('HD000016', 'MA000005', 3, 200000),
('HD000017', 'MA000003', 2, 300000),
('HD000017', 'MA000008', 2, 40000),
('HD000018', 'MA000017', 3, 30000),
('HD000018', 'MA000006', 1, 350000),
('HD000018', 'MA000004', 3, 20000),
('HD000019', 'MA000002', 2, 40000),
('HD000019', 'MA000003', 3, 300000),
('HD000019', 'MA000006', 2, 350000),
('HD000020', 'MA000022', 1, 150000),
('HD000020', 'MA000024', 1, 70000),
('HD000020', 'MA000011', 3, 150000),
('HD000021', 'MA000014', 3, 250000),
('HD000021', 'MA000024', 3, 70000),
('HD000022', 'MA000018', 2, 50000),
('HD000022', 'MA000016', 3, 120000),
('HD000023', 'MA000003', 1, 300000),
('HD000023', 'MA000017', 2, 30000),
('HD000024', 'MA000009', 3, 200000),
('HD000024', 'MA000022', 2, 150000),
('HD000024', 'MA000025', 2, 90000),
('HD000025', 'MA000003', 1, 300000),
('HD000025', 'MA000022', 2, 150000),
('HD000025', 'MA000012', 2, 180000),
('HD000026', 'MA000004', 2, 20000),
('HD000026', 'MA000010', 1, 200000),
('HD000026', 'MA000008', 2, 40000),
('HD000026', 'MA000019', 3, 80000),
('HD000027', 'MA000001', 2, 200000),
('HD000027', 'MA000017', 2, 30000),
('HD000027', 'MA000008', 1, 40000),
('HD000027', 'MA000015', 1, 100000),
('HD000027', 'MA000024', 2, 70000),
('HD000028', 'MA000024', 1, 70000),
('HD000028', 'MA000011', 2, 150000),
('HD000029', 'MA000023', 3, 80000),
('HD000029', 'MA000010', 1, 200000),
('HD000029', 'MA000025', 1, 90000),
('HD000030', 'MA000005', 2, 200000),
('HD000030', 'MA000002', 3, 40000),
('HD000030', 'MA000018', 1, 50000),
('HD000030', 'MA000010', 1, 200000),
('HD000031', 'MA000018', 1, 50000),
('HD000031', 'MA000024', 3, 70000),
('HD000031', 'MA000008', 3, 40000),
('HD000031', 'MA000011', 2, 150000),
('HD000031', 'MA000002', 2, 40000),
('HD000032', 'MA000019', 2, 80000),
('HD000032', 'MA000020', 3, 45000),
('HD000032', 'MA000001', 1, 200000),
('HD000032', 'MA000018', 2, 50000),
('HD000033', 'MA000004', 3, 20000),
('HD000034', 'MA000004', 1, 20000),
('HD000034', 'MA000008', 2, 40000),
('HD000035', 'MA000006', 2, 350000),
('HD000035', 'MA000022', 1, 150000),
('HD000035', 'MA000005', 3, 200000),
('HD000035', 'MA000008', 3, 40000),
('HD000035', 'MA000004', 2, 20000),
('HD000036', 'MA000012', 3, 180000),
('HD000036', 'MA000010', 1, 200000),
('HD000036', 'MA000009', 3, 200000),
('HD000036', 'MA000001', 1, 200000),
('HD000036', 'MA000003', 1, 300000),
('HD000037', 'MA000008', 3, 40000),
('HD000037', 'MA000024', 1, 70000),
('HD000037', 'MA000011', 1, 150000),
('HD000037', 'MA000017', 3, 30000),
('HD000038', 'MA000016', 2, 120000),
('HD000038', 'MA000020', 1, 45000),
('HD000038', 'MA000015', 1, 100000),
('HD000039', 'MA000010', 2, 200000),
('HD000039', 'MA000011', 3, 150000),
('HD000039', 'MA000023', 2, 80000),
('HD000039', 'MA000017', 3, 30000),
('HD000039', 'MA000014', 2, 250000),
('HD000040', 'MA000008', 2, 40000),
('HD000041', 'MA000023', 1, 80000),
('HD000041', 'MA000025', 3, 90000),
('HD000041', 'MA000015', 1, 100000),
('HD000042', 'MA000003', 3, 300000),
('HD000043', 'MA000025', 1, 90000),
('HD000043', 'MA000004', 3, 20000),
('HD000043', 'MA000007', 3, 250000),
('HD000043', 'MA000013', 1, 220000),
('HD000044', 'MA000014', 2, 250000),
('HD000044', 'MA000021', 1, 300000),
('HD000045', 'MA000005', 3, 200000),
('HD000046', 'MA000013', 3, 220000),
('HD000046', 'MA000015', 2, 100000),
('HD000046', 'MA000020', 3, 45000),
('HD000047', 'MA000015', 2, 100000),
('HD000047', 'MA000012', 1, 180000),
('HD000047', 'MA000025', 3, 90000),
('HD000048', 'MA000011', 1, 150000),
('HD000048', 'MA000014', 3, 250000),
('HD000048', 'MA000020', 2, 45000),
('HD000048', 'MA000013', 3, 220000),
('HD000049', 'MA000015', 1, 100000),
('HD000050', 'MA000001', 2, 200000),
('HD000050', 'MA000002', 3, 40000),
('HD000050', 'MA000023', 3, 80000),
('HD000051', 'MA000006', 2, 350000),
('HD000052', 'MA000003', 3, 300000),
('HD000053', 'MA000006', 2, 350000),
('HD000053', 'MA000011', 1, 150000),
('HD000053', 'MA000010', 1, 200000),
('HD000053', 'MA000002', 2, 40000),
('HD000053', 'MA000021', 3, 300000),
('HD000054', 'MA000016', 1, 120000),
('HD000054', 'MA000006', 3, 350000),
('HD000054', 'MA000009', 2, 200000),
('HD000055', 'MA000013', 1, 220000),
('HD000055', 'MA000021', 3, 300000),
('HD000056', 'MA000019', 1, 80000),
('HD000057', 'MA000003', 3, 300000),
('HD000057', 'MA000011', 3, 150000),
('HD000058', 'MA000014', 3, 250000),
('HD000058', 'MA000008', 2, 40000),
('HD000059', 'MA000023', 1, 80000),
('HD000059', 'MA000002', 1, 40000),
('HD000059', 'MA000018', 3, 50000),
('HD000059', 'MA000010', 3, 200000),
('HD000060', 'MA000013', 3, 220000),
('HD000060', 'MA000024', 1, 70000),
('HD000061', 'MA000002', 3, 40000),
('HD000061', 'MA000007', 1, 250000),
('HD000061', 'MA000025', 1, 90000),
('HD000061', 'MA000010', 1, 200000),
('HD000062', 'MA000003', 1, 300000),
('HD000062', 'MA000007', 1, 250000),
('HD000062', 'MA000012', 1, 180000),
('HD000062', 'MA000019', 1, 80000),
('HD000063', 'MA000017', 1, 30000),
('HD000063', 'MA000007', 1, 250000),
('HD000063', 'MA000006', 3, 350000),
('HD000064', 'MA000023', 3, 80000),
('HD000064', 'MA000016', 3, 120000),
('HD000064', 'MA000011', 3, 150000),
('HD000064', 'MA000014', 1, 250000),
('HD000064', 'MA000007', 3, 250000),
('HD000065', 'MA000008', 3, 40000),
('HD000065', 'MA000022', 2, 150000),
('HD000065', 'MA000015', 3, 100000),
('HD000065', 'MA000005', 2, 200000),
('HD000065', 'MA000014', 1, 250000),
('HD000066', 'MA000004', 1, 20000),
('HD000067', 'MA000015', 3, 100000),
('HD000067', 'MA000008', 1, 40000),
('HD000067', 'MA000014', 2, 250000),
('HD000067', 'MA000017', 2, 30000),
('HD000068', 'MA000005', 1, 200000),
('HD000068', 'MA000021', 1, 300000),
('HD000068', 'MA000007', 2, 250000),
('HD000068', 'MA000025', 1, 90000),
('HD000069', 'MA000013', 2, 220000),
('HD000069', 'MA000020', 2, 45000),
('HD000069', 'MA000010', 1, 200000),
('HD000069', 'MA000001', 3, 200000),
('HD000069', 'MA000025', 3, 90000),
('HD000070', 'MA000023', 3, 80000),
('HD000070', 'MA000017', 2, 30000),
('HD000071', 'MA000012', 3, 180000),
('HD000071', 'MA000003', 2, 300000),
('HD000071', 'MA000023', 1, 80000),
('HD000071', 'MA000004', 2, 20000),
('HD000072', 'MA000001', 3, 200000),
('HD000072', 'MA000004', 2, 20000),
('HD000072', 'MA000013', 2, 220000),
('HD000073', 'MA000007', 2, 250000),
('HD000073', 'MA000003', 1, 300000),
('HD000073', 'MA000008', 2, 40000),
('HD000074', 'MA000011', 1, 150000),
('HD000074', 'MA000013', 2, 220000),
('HD000074', 'MA000006', 1, 350000),
('HD000074', 'MA000019', 2, 80000),
('HD000074', 'MA000025', 3, 90000),
('HD000075', 'MA000016', 2, 120000),
('HD000075', 'MA000019', 1, 80000),
('HD000075', 'MA000003', 2, 300000),
('HD000076', 'MA000009', 1, 200000),
('HD000076', 'MA000019', 2, 80000),
('HD000076', 'MA000008', 1, 40000),
('HD000077', 'MA000025', 3, 90000),
('HD000077', 'MA000018', 2, 50000),
('HD000077', 'MA000016', 2, 120000),
('HD000078', 'MA000024', 3, 70000),
('HD000078', 'MA000021', 1, 300000),
('HD000079', 'MA000013', 3, 220000),
('HD000079', 'MA000017', 2, 30000),
('HD000079', 'MA000003', 2, 300000),
('HD000080', 'MA000005', 1, 200000),
('HD000081', 'MA000008', 3, 40000),
('HD000081', 'MA000005', 3, 200000),
('HD000081', 'MA000007', 3, 250000),
('HD000081', 'MA000025', 3, 90000),
('HD000081', 'MA000006', 2, 350000),
('HD000082', 'MA000011', 1, 150000),
('HD000082', 'MA000003', 1, 300000),
('HD000082', 'MA000004', 2, 20000),
('HD000083', 'MA000014', 1, 250000),
('HD000083', 'MA000013', 3, 220000),
('HD000083', 'MA000018', 2, 50000),
('HD000083', 'MA000004', 3, 20000),
('HD000084', 'MA000007', 3, 250000),
('HD000084', 'MA000009', 3, 200000),
('HD000084', 'MA000012', 2, 180000),
('HD000085', 'MA000004', 3, 20000),
('HD000086', 'MA000010', 2, 200000),
('HD000087', 'MA000017', 2, 30000),
('HD000088', 'MA000007', 2, 250000),
('HD000088', 'MA000015', 3, 100000),
('HD000088', 'MA000010', 3, 200000),
('HD000088', 'MA000009', 2, 200000),
('HD000088', 'MA000014', 3, 250000),
('HD000089', 'MA000024', 1, 70000),
('HD000089', 'MA000007', 3, 250000),
('HD000089', 'MA000006', 2, 350000),
('HD000090', 'MA000014', 2, 250000),
('HD000090', 'MA000024', 3, 70000),
('HD000090', 'MA000008', 1, 40000),
('HD000090', 'MA000017', 3, 30000),
('HD000090', 'MA000003', 1, 300000),
('HD000091', 'MA000001', 3, 200000),
('HD000092', 'MA000013', 1, 220000),
('HD000092', 'MA000008', 1, 40000),
('HD000092', 'MA000011', 2, 150000),
('HD000092', 'MA000002', 1, 40000),
('HD000093', 'MA000023', 3, 80000),
('HD000093', 'MA000007', 3, 250000),
('HD000093', 'MA000001', 2, 200000),
('HD000093', 'MA000024', 1, 70000),
('HD000094', 'MA000020', 2, 45000),
('HD000094', 'MA000008', 2, 40000),
('HD000095', 'MA000015', 1, 100000),
('HD000096', 'MA000009', 3, 200000),
('HD000096', 'MA000023', 3, 80000),
('HD000096', 'MA000007', 3, 250000),
('HD000096', 'MA000002', 3, 40000),
('HD000097', 'MA000014', 1, 250000),
('HD000097', 'MA000011', 3, 150000),
('HD000098', 'MA000005', 3, 200000),
('HD000098', 'MA000020', 1, 45000),
('HD000098', 'MA000006', 2, 350000),
('HD000099', 'MA000018', 2, 50000),
('HD000099', 'MA000015', 2, 100000),
('HD000099', 'MA000001', 2, 200000),
('HD000099', 'MA000023', 1, 80000),
('HD000100', 'MA000025', 2, 90000),
('HD000100', 'MA000012', 1, 180000),
('HD000100', 'MA000001', 1, 200000),
('HD000100', 'MA000004', 2, 20000),
('HD000100', 'MA000014', 1, 250000),
('HD000101', 'MA000008', 2, 40000),
('HD000101', 'MA000001', 3, 200000),
('HD000101', 'MA000020', 2, 45000),
('HD000101', 'MA000010', 2, 200000),
('HD000101', 'MA000016', 2, 120000),
('HD000102', 'MA000006', 3, 350000),
('HD000102', 'MA000014', 3, 250000),
('HD000103', 'MA000016', 2, 120000),
('HD000103', 'MA000024', 3, 70000),
('HD000103', 'MA000005', 1, 200000),
('HD000103', 'MA000020', 3, 45000),
('HD000103', 'MA000019', 3, 80000),
('HD000104', 'MA000023', 1, 80000),
('HD000104', 'MA000012', 3, 180000),
('HD000105', 'MA000006', 3, 350000),
('HD000105', 'MA000015', 3, 100000),
('HD000105', 'MA000001', 3, 200000),
('HD000106', 'MA000004', 3, 20000),
('HD000106', 'MA000016', 3, 120000),
('HD000106', 'MA000011', 3, 150000),
('HD000107', 'MA000015', 3, 100000),
('HD000108', 'MA000004', 1, 20000),
('HD000109', 'MA000022', 3, 150000),
('HD000110', 'MA000010', 2, 200000),
('HD000110', 'MA000022', 1, 150000),
('HD000110', 'MA000011', 1, 150000),
('HD000110', 'MA000025', 1, 90000),
('HD000111', 'MA000010', 3, 200000),
('HD000111', 'MA000006', 1, 350000),
('HD000111', 'MA000020', 2, 45000),
('HD000112', 'MA000016', 2, 120000),
('HD000112', 'MA000011', 2, 150000),
('HD000113', 'MA000016', 3, 120000),
('HD000113', 'MA000025', 2, 90000),
('HD000113', 'MA000015', 1, 100000),
('HD000113', 'MA000011', 3, 150000),
('HD000113', 'MA000001', 1, 200000),
('HD000114', 'MA000018', 2, 50000),
('HD000114', 'MA000011', 1, 150000),
('HD000114', 'MA000023', 1, 80000),
('HD000115', 'MA000002', 3, 40000),
('HD000115', 'MA000009', 1, 200000),
('HD000116', 'MA000025', 3, 90000),
('HD000116', 'MA000007', 2, 250000),
('HD000117', 'MA000012', 3, 180000),
('HD000118', 'MA000021', 2, 300000),
('HD000119', 'MA000014', 2, 250000),
('HD000119', 'MA000023', 3, 80000),
('HD000119', 'MA000012', 2, 180000),
('HD000119', 'MA000015', 1, 100000),
('HD000120', 'MA000014', 2, 250000),
('HD000121', 'MA000018', 3, 50000),
('HD000122', 'MA000020', 3, 45000),
('HD000122', 'MA000002', 3, 40000),
('HD000122', 'MA000024', 2, 70000),
('HD000122', 'MA000012', 2, 180000),
('HD000122', 'MA000004', 3, 20000),
('HD000123', 'MA000022', 2, 150000),
('HD000123', 'MA000005', 2, 200000),
('HD000123', 'MA000011', 2, 150000),
('HD000123', 'MA000015', 2, 100000),
('HD000123', 'MA000007', 3, 250000),
('HD000124', 'MA000006', 3, 350000),
('HD000124', 'MA000023', 2, 80000),
('HD000124', 'MA000020', 2, 45000),
('HD000124', 'MA000009', 1, 200000),
('HD000124', 'MA000002', 3, 40000),
('HD000125', 'MA000021', 1, 300000),
('HD000125', 'MA000016', 3, 120000),
('HD000125', 'MA000004', 2, 20000),
('HD000126', 'MA000012', 1, 180000),
('HD000126', 'MA000015', 3, 100000),
('HD000127', 'MA000003', 1, 300000),
('HD000127', 'MA000002', 2, 40000),
('HD000127', 'MA000014', 2, 250000),
('HD000127', 'MA000017', 3, 30000),
('HD000127', 'MA000015', 2, 100000),
('HD000128', 'MA000014', 2, 250000),
('HD000128', 'MA000024', 2, 70000),
('HD000129', 'MA000002', 3, 40000),
('HD000130', 'MA000001', 3, 200000),
('HD000130', 'MA000010', 1, 200000),
('HD000130', 'MA000015', 2, 100000),
('HD000131', 'MA000006', 1, 350000),
('HD000132', 'MA000016', 1, 120000),
('HD000133', 'MA000004', 2, 20000),
('HD000133', 'MA000024', 1, 70000),
('HD000133', 'MA000008', 2, 40000),
('HD000134', 'MA000016', 2, 120000),
('HD000135', 'MA000005', 3, 200000),
('HD000135', 'MA000008', 1, 40000),
('HD000136', 'MA000008', 1, 40000),
('HD000136', 'MA000017', 3, 30000),
('HD000136', 'MA000023', 2, 80000),
('HD000137', 'MA000014', 1, 250000),
('HD000138', 'MA000018', 1, 50000),
('HD000138', 'MA000013', 2, 220000),
('HD000139', 'MA000002', 2, 40000),
('HD000139', 'MA000010', 2, 200000),
('HD000140', 'MA000003', 3, 300000),
('HD000140', 'MA000024', 3, 70000),
('HD000140', 'MA000012', 1, 180000),
('HD000140', 'MA000004', 1, 20000),
('HD000140', 'MA000019', 1, 80000),
('HD000141', 'MA000017', 2, 30000),
('HD000141', 'MA000015', 1, 100000),
('HD000141', 'MA000001', 2, 200000),
('HD000142', 'MA000011', 3, 150000),
('HD000142', 'MA000010', 2, 200000),
('HD000142', 'MA000004', 3, 20000),
('HD000142', 'MA000023', 2, 80000),
('HD000142', 'MA000006', 1, 350000),
('HD000143', 'MA000020', 2, 45000),
('HD000143', 'MA000004', 3, 20000),
('HD000144', 'MA000011', 2, 150000),
('HD000144', 'MA000016', 3, 120000),
('HD000144', 'MA000025', 3, 90000),
('HD000145', 'MA000002', 1, 40000),
('HD000145', 'MA000009', 1, 200000),
('HD000146', 'MA000002', 3, 40000),
('HD000146', 'MA000024', 2, 70000),
('HD000147', 'MA000009', 2, 200000),
('HD000147', 'MA000003', 3, 300000),
('HD000148', 'MA000001', 1, 200000),
('HD000148', 'MA000024', 2, 70000),
('HD000148', 'MA000023', 3, 80000),
('HD000149', 'MA000012', 2, 180000),
('HD000149', 'MA000022', 3, 150000),
('HD000150', 'MA000005', 2, 200000),
('HD000150', 'MA000025', 2, 90000),
('HD000150', 'MA000009', 2, 200000),
('HD000150', 'MA000013', 2, 220000),
('HD000151', 'MA000011', 3, 150000),
('HD000151', 'MA000012', 2, 180000),
('HD000151', 'MA000003', 2, 300000),
('HD000152', 'MA000016', 2, 120000),
('HD000152', 'MA000002', 2, 40000),
('HD000152', 'MA000006', 2, 350000),
('HD000152', 'MA000001', 2, 200000),
('HD000152', 'MA000010', 2, 200000),
('HD000153', 'MA000017', 1, 30000),
('HD000153', 'MA000024', 1, 70000),
('HD000154', 'MA000009', 3, 200000),
('HD000155', 'MA000016', 2, 120000),
('HD000155', 'MA000025', 2, 90000),
('HD000155', 'MA000003', 2, 300000),
('HD000156', 'MA000021', 2, 300000),
('HD000156', 'MA000002', 3, 40000),
('HD000156', 'MA000013', 3, 220000),
('HD000157', 'MA000015', 2, 100000),
('HD000157', 'MA000006', 1, 350000),
('HD000157', 'MA000003', 1, 300000),
('HD000157', 'MA000017', 1, 30000),
('HD000158', 'MA000021', 3, 300000),
('HD000159', 'MA000025', 3, 90000),
('HD000159', 'MA000023', 1, 80000),
('HD000159', 'MA000015', 2, 100000),
('HD000160', 'MA000023', 1, 80000),
('HD000160', 'MA000014', 2, 250000),
('HD000160', 'MA000010', 1, 200000),
('HD000161', 'MA000006', 1, 350000),
('HD000161', 'MA000014', 2, 250000),
('HD000162', 'MA000012', 2, 180000),
('HD000162', 'MA000014', 3, 250000),
('HD000163', 'MA000015', 1, 100000),
('HD000163', 'MA000013', 2, 220000),
('HD000163', 'MA000017', 1, 30000),
('HD000163', 'MA000007', 1, 250000),
('HD000164', 'MA000006', 2, 350000),
('HD000164', 'MA000021', 1, 300000),
('HD000164', 'MA000007', 2, 250000),
('HD000164', 'MA000011', 3, 150000),
('HD000164', 'MA000009', 3, 200000),
('HD000165', 'MA000020', 2, 45000),
('HD000166', 'MA000005', 1, 200000),
('HD000167', 'MA000012', 2, 180000),
('HD000167', 'MA000025', 1, 90000),
('HD000167', 'MA000011', 3, 150000),
('HD000167', 'MA000007', 2, 250000),
('HD000168', 'MA000020', 2, 45000),
('HD000168', 'MA000014', 3, 250000),
('HD000169', 'MA000004', 1, 20000),
('HD000170', 'MA000020', 1, 45000),
('HD000170', 'MA000017', 2, 30000),
('HD000170', 'MA000007', 3, 250000),
('HD000170', 'MA000021', 3, 300000),
('HD000170', 'MA000010', 2, 200000),
('HD000171', 'MA000005', 1, 200000),
('HD000171', 'MA000009', 1, 200000),
('HD000171', 'MA000003', 2, 300000),
('HD000171', 'MA000007', 3, 250000),
('HD000171', 'MA000018', 3, 50000),
('HD000172', 'MA000023', 1, 80000),
('HD000173', 'MA000016', 1, 120000),
('HD000174', 'MA000016', 2, 120000),
('HD000174', 'MA000004', 3, 20000),
('HD000174', 'MA000002', 1, 40000),
('HD000174', 'MA000005', 1, 200000),
('HD000174', 'MA000009', 2, 200000),
('HD000175', 'MA000001', 1, 200000),
('HD000175', 'MA000005', 2, 200000),
('HD000175', 'MA000020', 1, 45000),
('HD000176', 'MA000025', 3, 90000),
('HD000177', 'MA000021', 1, 300000),
('HD000178', 'MA000014', 3, 250000),
('HD000178', 'MA000011', 3, 150000),
('HD000178', 'MA000002', 1, 40000),
('HD000179', 'MA000011', 2, 150000),
('HD000180', 'MA000022', 3, 150000),
('HD000180', 'MA000009', 1, 200000),
('HD000180', 'MA000019', 2, 80000),
('HD000180', 'MA000011', 2, 150000),
('HD000180', 'MA000013', 2, 220000),
('HD000181', 'MA000001', 1, 200000),
('HD000181', 'MA000005', 2, 200000),
('HD000181', 'MA000020', 1, 45000),
('HD000182', 'MA000025', 3, 90000),
('HD000183', 'MA000021', 1, 300000),
('HD000184', 'MA000014', 3, 250000),
('HD000184', 'MA000011', 3, 150000),
('HD000184', 'MA000002', 1, 40000),
('HD000185', 'MA000011', 2, 150000),
('HD000186', 'MA000022', 3, 150000),
('HD000186', 'MA000009', 1, 200000),
('HD000186', 'MA000019', 2, 80000),
('HD000186', 'MA000011', 2, 150000),
('HD000186', 'MA000013', 2, 220000);






