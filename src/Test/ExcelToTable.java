package Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToTable extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public ExcelToTable() {
        setTitle("Đọc Excel bằng JFileChooser");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnOpen = new JButton("Chọn file Excel");
        btnOpen.addActionListener(e -> chooseAndReadExcel());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(btnOpen, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void chooseAndReadExcel() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            readExcelToTable(file);
        }
    }

    private void readExcelToTable(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            tableModel.setRowCount(0); // Clear table
            tableModel.setColumnCount(0);

            boolean isFirstRow = true;

            for (Row row : sheet) {
                Object[] rowData = new Object[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData[i] = getCellValue(cell);
                }

                if (isFirstRow) {
                    for (Object col : rowData) {
                        tableModel.addColumn(col);
                    }
                    isFirstRow = false;
                } else {
                    tableModel.addRow(rowData);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file: " + ex.getMessage());
        }
    }

    private Object getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> (DateUtil.isCellDateFormatted(cell)) ?
                            cell.getDateCellValue() : cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExcelToTable().setVisible(true);
        });
    }
}

