package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil {
	private static final Logger LOGGER=  LogManager.getLogger(ExcelReaderUtil.class);
	
	private ExcelReaderUtil(){
		
	}

	public static  <T> Iterator<T> loadTestData(String xlsxFile,String sheetName,Class<T> clazz)  {
		LOGGER.info("Readig the test data from the .xlsx file {} and the sheet name is {}",xlsxFile,sheetName);
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");

		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			LOGGER.error("Cannot read the excel {}",xlsxFile);
			e.printStackTrace();
		}
		XSSFSheet mySheet=myWorkBook.getSheet(sheetName);
		LOGGER.info("Converting the XSSFSheet {} to POJO Class of type {}", clazz,sheetName);
		
		List<T> dataList=Poiji.fromExcel(mySheet, clazz);
		return dataList.iterator();

	}
}
