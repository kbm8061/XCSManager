package com.inzent.ecm.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.inzent.ecm.vo.ElementVO;

public class ExcelUtils {

	private static final String EXT_XLS = "XLS";
	private static final String EXT_XLSX = "XLSX";

	public ArrayList<ElementVO> readExcel(String filePath) throws Exception {
		File file = new File(filePath);
		String ext = FilenameUtils.getExtension(file.getName());
		ArrayList<ElementVO> resultMap = null;

		if (EXT_XLS.equalsIgnoreCase(ext)) {
			resultMap = readExcelForXls(filePath);
		} else if (EXT_XLSX.equalsIgnoreCase(ext)) {
//			resultMap = readExcelForXlsx(filePath);
			resultMap = readExcelForSax(filePath);
		}

		return resultMap;
	}

	@SuppressWarnings("resource")
	private ArrayList<ElementVO> readExcelForXls(String filePath) throws Exception {
		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();
		ArrayList<ElementVO> resultList = new ArrayList<ElementVO>();

		for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);

			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			ElementVO vo = new ElementVO();

			for (int columnindex = 0; columnindex <= cells; columnindex++) {
				HSSFCell cell = row.getCell(columnindex);
				String value = "";

				if (cell == null) {
					continue;
				} else {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;

					case HSSFCell.CELL_TYPE_NUMERIC:
						value = String.valueOf(cell.getNumericCellValue());
						break;

					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BLANK:
						value = String.valueOf(cell.getBooleanCellValue());

					case HSSFCell.CELL_TYPE_ERROR:
						value = String.valueOf(cell.getErrorCellValue());
					}
				}

				switch (columnindex) {
				case 0:
					vo.setElementId(value.trim());
					break;

				case 1:
					vo.setOwner(value.trim());
					break;
					
				case 2:
					vo.setIndexKey(value.trim());
					break;

				default:
					break;
				}
			}
			resultList.add(vo);
		}

		return resultList;
	}
	
	@SuppressWarnings("resource")
	private ArrayList<ElementVO> readExcelForXlsx(String filePath) throws Exception {
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		int rows = sheet.getPhysicalNumberOfRows();
		ArrayList<ElementVO> resultList = new ArrayList<ElementVO>();

		for (int rowindex = 1; rowindex < rows; rowindex++) {

			XSSFRow row = sheet.getRow(rowindex);
			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			ElementVO vo = new ElementVO();

			for (int columnindex = 0; columnindex < cells; columnindex++) {

				XSSFCell cell = row.getCell(columnindex);
				String value = "";

				if (cell == null) {
					continue;
				} else {
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;

					case XSSFCell.CELL_TYPE_NUMERIC:
						value = String.valueOf((int) cell.getNumericCellValue());
						break;

					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;

					case XSSFCell.CELL_TYPE_BLANK:
						value = String.valueOf(cell.getBooleanCellValue());
						break;

					case XSSFCell.CELL_TYPE_ERROR:
						value = String.valueOf(cell.getErrorCellValue());

					}
				}

				switch (columnindex) {
				case 0:
					vo.setElementId(value.trim());
					break;

				case 1:
					vo.setOwner(value.trim());
					break;
					
				case 2:
					vo.setIndexKey(value.trim());
					break;

				default:
					break;
				}
			}
			
			if (vo.getElementId() != null && vo.getIndexKey() != null)
				resultList.add(vo);
		}

		return resultList;
	}

	private ArrayList<ElementVO> readExcelForSax(String filePath) throws Exception {
		ExcelSAXUtils excelSheetHandler = ExcelSAXUtils.readExcel(new File(filePath));
		List<List<String>> excelDatas = excelSheetHandler.getRows();
		ArrayList<ElementVO> resultList = new ArrayList<ElementVO>();

		int iCol = 0;

		for (List<String> dataRow : excelDatas) {
			ElementVO vo = new ElementVO();

			for (String value : dataRow) {
				switch (iCol) {
				case 0:
					vo.setElementId(value.trim());
					break;

				case 1:
					vo.setOwner(value.trim());
					break;
					
				case 2:
					vo.setIndexKey(value.trim());
					break;

				default:
					break;
				}
				iCol++;
			}
			
			resultList.add(vo);
			
			iCol = 0;
		}

		return resultList;
	}
	
	public SXSSFWorkbook getWorkbook(List<ElementVO> voList) throws Exception {
		SXSSFWorkbook workbook = new SXSSFWorkbook();

		Sheet sheet = workbook.createSheet();

		Row row = null;
		Cell cell = null;

		String[] headerKey = { "ELEMENTID", "INDEXKEY", "OWNER", "CLASSIFICATION", "STATUS", "ERRCODE", "MAPPINGTABLEINFO",
				"VOLUMEID", "FILEPATH", "REGUSER", "CRTDATE", "REGDATE", "PROCESSDATE", "HISTORYID", "REALTIMEYN",
				"SPDATE", "RVDATE", "DELETEDAY" };

		CellStyle headerStyle = CellStyleSetting(workbook, "header");
		CellStyle dataStyle = CellStyleSetting(workbook, "data");

		row = sheet.createRow(0);

		for (int i = 0; i < headerKey.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headerKey[i]);
			cell.setCellStyle(headerStyle);
		}

		for (int i = 0; i < voList.size(); i++) {
			row = sheet.createRow(i + 1);
			int cellIdx = 0;

			ElementVO vo = voList.get(i);

			cell = row.createCell(cellIdx++);
			if (vo.getElementId() != null)
				cell.setCellValue(vo.getElementId().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getIndexKey() != null)
				cell.setCellValue(vo.getIndexKey().trim());
			cell.setCellStyle(dataStyle);
			
			cell = row.createCell(cellIdx++);
			if (vo.getOwner() != null)
				cell.setCellValue(vo.getOwner().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getClassification() != null)
				cell.setCellValue(vo.getClassification().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getStatus() != null)
				cell.setCellValue(vo.getStatus().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getErrCode() != null)
				cell.setCellValue(vo.getErrCode().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getMappingTableInfo() != null)
				cell.setCellValue(vo.getMappingTableInfo().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getVolumeId() != null)
				cell.setCellValue(vo.getVolumeId().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getFilePath() != null)
				cell.setCellValue(vo.getFilePath().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getRegUser() != null)
				cell.setCellValue(vo.getRegUser().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getCrtDate() != null)
				cell.setCellValue(vo.getCrtDate().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getRegDate() != null)
				cell.setCellValue(vo.getRegDate().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getProcessDate() != null)
				cell.setCellValue(vo.getProcessDate().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getHistoryId() != null)
				cell.setCellValue(vo.getHistoryId().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getRealtimeYn() != null)
				cell.setCellValue(vo.getRealtimeYn().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getSpDate() != null)
				cell.setCellValue(vo.getSpDate().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getRvDate() != null)
				cell.setCellValue(vo.getRvDate().trim());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			if (vo.getDeleteDay() != null)
				cell.setCellValue(vo.getDeleteDay().trim());
			cell.setCellStyle(dataStyle);
		}

		for (int i = 0; i < headerKey.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}

		return workbook;
	}

	public CellStyle CellStyleSetting(SXSSFWorkbook workbook, String kind) {
		CellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

		if (kind.equals("header")) {
			cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}

		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Font fontOfGothic = workbook.createFont();
		fontOfGothic.setFontName("¸¼Àº °íµñ");
		cellStyle.setFont(fontOfGothic);

		return cellStyle;
	}
}