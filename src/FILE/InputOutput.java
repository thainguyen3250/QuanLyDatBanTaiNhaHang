package FILE;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.MonAn;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class InputOutput {
	float yPosition = 680; 
	float bottomMargin = 50;
	float lineHeight = 22f;
	public void writeToFile(ArrayList<ChiTietHoaDon> dscthd,String filename)throws Exception
	{
		PrintWriter pw = new PrintWriter(filename,"UTF-8");
		for(ChiTietHoaDon cthd : dscthd)
		{
			pw.print(cthd);
		}
		pw.flush();
		pw.close();
	}
	public void writeToFile(KhachHang kh,String filename)throws Exception
	{
		PrintWriter pw = new PrintWriter(filename,"UTF-8");
		pw.print(kh);
		pw.flush();
		pw.close();
	}
	public Object readFromFileKh(String filename)throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
		String line;
		KhachHang kh = null;
		while((line=br.readLine())!=null)
		{
			String[] info=line.split(";");
			kh = new KhachHang(info[0],info[1]);
		}
		br.close();
		return kh;
		
	}
	public Object readFromFile(String filename)throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
		String line;
		ArrayList<ChiTietHoaDon> dscthd = new ArrayList<ChiTietHoaDon>();
		while((line=br.readLine())!=null)
		{
			String[] info=line.split(";");
			dscthd.add(new ChiTietHoaDon(new MonAn(info[0],info[1]),Integer.parseInt(info[2]),Double.parseDouble(info[3])));
		}
		br.close();
		return dscthd;
		
	}
	public boolean deleteSpecificFile(String fileName) {
	    File file = new File(fileName);
	    if (file.exists() && file.isFile()) {
	        return file.delete();
	    }
	    return false;
	}

	public void xuatHoaDon(HoaDon hd, ArrayList<ChiTietHoaDon> dscthd, String tenFile, double tongTienTruocThue, double tongTienSauThue) {
		try (PDDocument document = new PDDocument()) {
		    PDType0Font font = PDType0Font.load(document, new File("thuvien/ARIAL.TTF"));
		    PDRectangle customSize = new PDRectangle(350, 700);
		    PDPage page = new PDPage(customSize);
		    document.addPage(page);

		    PDPageContentStream content = new PDPageContentStream(document, page);
		    content.beginText();
		    content.setFont(font, 14);
		    content.setLeading(22f);
		    float yPosition = 680; // Initialize yPosition
		    float bottomMargin = 50; // Define bottom margin
		    float lineHeight = 22f; // Line height matching setLeading
		    content.newLineAtOffset(20, yPosition);

		    // ===== Tiêu đề =====
		    content.setFont(font, 20);
		    content.showText("              CHILL POT");
		    content.newLine();
		    yPosition -= lineHeight;
		    content.setFont(font, 16);
		    content.showText("         HÓA ĐƠN THANH TOÁN");
		    content.newLine();
		    yPosition -= lineHeight;
		    content.setFont(font, 13);
		    content.showText("Địa Chỉ: 135 Nam Kỳ Khởi Nghĩa, P.Bến Thành,");
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText("Q.1, TP.HCM");
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText("Số Điện thoại: 0209 300 41975");
		    content.newLine();
		    yPosition -= lineHeight;
		    content.newLine();
		    yPosition -= lineHeight;

		    // ===== Thông tin hóa đơn =====
		    content.setFont(font, 12);
		    content.showText("Mã hóa đơn: " + hd.getMaHoaDon());
		    content.newLine();
		    yPosition -= lineHeight;
		    if (hd.getSoDienThoai() == null) {
		        content.showText("Khách hàng: Khách vãng lai");
		        content.newLine();
		        yPosition -= lineHeight;
		    } else {
		        content.showText("Khách hàng: " + hd.getSoDienThoai().getHoTen());
		        content.newLine();
		        yPosition -= lineHeight;
		        content.showText("SĐT: " + hd.getSoDienThoai().getSoDienThoai());
		        content.newLine();
		        yPosition -= lineHeight;
		    }
		    if (hd.getMaBan() != null) {
		        content.showText("Bàn: " + hd.getMaBan().getMaBan());
		    } else {
		        content.showText("Phòng: " + hd.getMaPhong().getMaPhong());
		    }
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText("Ngày Lập Hóa Đơn: " + hd.getNgayLapHoaDon().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText("Nhân viên: " + hd.getMaNhanVien().getTenNhanVien());
		    content.newLine();
		    yPosition -= lineHeight;

		    content.newLine();
		    yPosition -= lineHeight;

		    // Check for page break before adding the table header
		    if (yPosition < bottomMargin) {
		        content.endText();
		        content.close();
		        PDPage newPage = new PDPage(customSize);
		        document.addPage(newPage);
		        content = new PDPageContentStream(document, newPage);
		        content.beginText();
		        content.setFont(font, 12);
		        content.setLeading(lineHeight);
		        yPosition = 680;
		        content.newLineAtOffset(20, yPosition);
		    }

		    content.showText("-".repeat(80));
		    content.newLine();
		    yPosition -= lineHeight;

		    String header = String.format("%-25s %-10s %-15s %-15s", "Tên món", "SL", "Đơn giá", "Thành tiền");
		    content.showText(header);
		    content.newLine();
		    yPosition -= lineHeight;

		    content.showText("-".repeat(80));
		    content.newLine();
		    yPosition -= lineHeight;

		    for (ChiTietHoaDon cthd : dscthd) {
		        // Check for page break before adding item
		        if (yPosition < bottomMargin) {
		            content.endText();
		            content.close();
		            PDPage newPage = new PDPage(customSize);
		            document.addPage(newPage);
		            content = new PDPageContentStream(document, newPage);
		            content.beginText();
		            content.setFont(font, 12);
		            content.setLeading(lineHeight);
		            yPosition = 680;
		            content.newLineAtOffset(20, yPosition);
		        }

		        content.showText(cthd.getMaMonAn().getTenMonAn());
		        content.newLine();
		        yPosition -= lineHeight;

		        String row = String.format("%-34s %-10d %,-15.0f %,-15.0f",
		                " ",
		                cthd.getSoLuong(),
		                cthd.getDonGia(),
		                cthd.getTongTien());
		        content.showText(row);
		        content.newLine();
		        yPosition -= lineHeight;
		    }

		    // Check for page break before adding the summary
		    if (yPosition < bottomMargin + (lineHeight * 8)) { // Reserve space for 8 lines (summary + thank you)
		        content.endText();
		        content.close();
		        PDPage newPage = new PDPage(customSize);
		        document.addPage(newPage);
		        content = new PDPageContentStream(document, newPage);
		        content.beginText();
		        content.setFont(font, 12);
		        content.setLeading(lineHeight);
		        yPosition = 680;
		        content.newLineAtOffset(20, yPosition);
		    }

		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText("-".repeat(80));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText(String.format("Thuế (VAT): 10%%"));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText(String.format("Phí Dịch Vụ: %, .0f VND", hd.getPhiDichVu()));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText(String.format("Tổng trước thuế: %, .0f VND", tongTienTruocThue));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText(String.format("Tổng sau thuế:   %, .0f VND", tongTienSauThue));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText(String.format("Tiền khách đưa:  %, .0f VND", hd.getTienKhachDua()));
		    content.newLine();
		    yPosition -= lineHeight;
		    content.showText(String.format("Tiền trả khách:  %, .0f VND", (hd.getTienKhachDua() - tongTienSauThue)));
		    content.newLine();
		    yPosition -= lineHeight;

		    // ===== Lời cảm ơn =====
		    content.newLine();
		    yPosition -= lineHeight;
		    content.setFont(font, 13);
		    content.showText("         Xin cảm ơn quý khách! Hẹn gặp lại!");
		    content.endText();
		    content.close();

		    document.save(tenFile);
		    System.out.println("Đã tạo hóa đơn: " + tenFile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
