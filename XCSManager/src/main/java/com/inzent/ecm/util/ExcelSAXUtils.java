package com.inzent.ecm.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ExcelSAXUtils implements SheetContentsHandler {
	private int currentCol = -1;
	private int currentRow = 0;

	String filePath = "";

	private List<List<String>> rows = new ArrayList<List<String>>();
	private List<String> row = new ArrayList<String>();
	private List<String> header = new ArrayList<String>();

	public static ExcelSAXUtils readExcel(File file) throws Exception {

		ExcelSAXUtils sheetHandler = new ExcelSAXUtils();
		
		try {
			OPCPackage opc = OPCPackage.open(file);
			XSSFReader xssfReader = new XSSFReader(opc);
			
			StylesTable styles = xssfReader.getStylesTable();
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);
			
			InputStream inputStream = xssfReader.getSheetsData().next();
			InputSource inputSource = new InputSource(inputStream);
			
			ContentHandler handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);
			XMLReader xmlReader = SAXHelper.newXMLReader();
			xmlReader.setContentHandler(handle);
			xmlReader.parse(inputSource);
			
			inputStream.close();
			opc.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return sheetHandler;
	}

	public List<List<String>> getRows() {
		return rows;
	}

	@Override
	public void startRow(int rowNum) {
		this.currentCol = -1;
		this.currentRow = rowNum;
	}

	@Override
	public void cell(String columnName, String value, XSSFComment comment) {
		int iCol = (new CellReference(columnName)).getCol();
		int emptyCol = iCol - currentCol - 1;

		for (int i = 0; i < emptyCol; i++) {
			row.add("");
		}
		currentCol = iCol;
		row.add(value);
	}

	@Override
	public void headerFooter(String arg0, boolean arg1, String arg2) {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void endRow(int rowNum) {
		if (rowNum == 0) {
			header = new ArrayList(row);
		} else {
			if (row.size() < header.size()) {
				for (int i = row.size(); i < header.size(); i++) {
					row.add("");
				}
			}
			rows.add(new ArrayList(row));
		}
		row.clear();
	}
}
