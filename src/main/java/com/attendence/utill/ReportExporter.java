package com.attendence.utill;


	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.List;
	import org.apache.poi.ss.usermodel.*;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import java.io.FileOutputStream;

	public class ReportExporter {

	    // CSV export
	    public static void exportToCsv(List<?> data, String path) {
	        try (FileWriter writer = new FileWriter(path)) {
	            for (Object row : data) {
	                writer.write(row.toString() + "\n");
	            }
	            System.out.println("CSV exported to: " + path);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // Excel export
	    public static void exportToExcel(List<?> data, String path) {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Report");

	        int rowIndex = 0;
	        for (Object rowData : data) {
	            Row row = sheet.createRow(rowIndex++);
	            Cell cell = row.createCell(0);
	            cell.setCellValue(rowData.toString());
	        }

	        try (FileOutputStream fileOut = new FileOutputStream(path)) {
	            workbook.write(fileOut);
	            workbook.close();
	            System.out.println("Excel exported to: " + path);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}



