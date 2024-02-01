package com.xe.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.xe.base.TestBase;

public class DataUtil extends TestBase{
	
	
	/*
	 * This method is to get test data from excel into the test
	 * in the form of a HashTable as it gives the user flexibility to add more columns 
	 * into the data sheet 
	 */
	public static Object[][] getDataIntoHashTable(ExcelReader excel, String sheetName) {
		
		//String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][1];
		Hashtable<String, String> table = null;
		
		for(int rowNum=2; rowNum<=rows; rowNum++) {
			table = new Hashtable<String, String>();
			for(int colNum=0; colNum<cols;colNum++) {
				//data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
				table.put(excel.getCellData(sheetName, colNum, 1),excel.getCellData(sheetName, colNum, rowNum));
			}
			data[rowNum - 2][0] = table;
		}
	
		return data;
	}
	
	/*
	 * This method is to get the screenshot at the end of test
	 * to attached to the test in case of any failure
	 */
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	
	

}
