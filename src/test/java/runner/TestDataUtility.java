package runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

public class TestDataUtility {
	public String filePath, sheetName, fileFormat;
	public Workbook workbook;
	public Map<String,String> testData ;
	public int actualRowCount=0, rowCount=0;
	public Integer count;
	public String executionFlag,testSceName,testCasName;
	public FileInputStream testDataFile;
	public File file;
	public Row row;
	public Sheet sheet;
	public DataFormatter formatter;
	boolean executionFlag1= false;
	
	public TestDataUtility(String fileName, String sheetName, String fileFormat){
		this.filePath = System.getProperty("user.dir")+"//src//test//resources//testData//"+fileName;
		this.sheetName = sheetName;
		this.fileFormat= fileFormat;
	}

	public TestDataUtility(String sheetName, String fileFormat){
		if(fileFormat.contentEquals("xls"))
				this.filePath= System.getProperty("user.dir")+"//src//test//resources//testData//"+"TestData.xls";
		if(fileFormat.contentEquals("xlsx"))
				this.filePath= System.getProperty("user.dir")+"//src//test//resources//testData//"+"TestData.xlsx";
		this.sheetName = sheetName;
		this.fileFormat= fileFormat;
	}

	public Map<String,String> readExcel(String scenarioName, String testCaseName) throws IOException
	{   
			//Initialize HashMap for storing Test data		
			testData = new HashMap<String,String>();			

			//Initialize objects to read Test data File
			file = new File(filePath);
			testDataFile = new FileInputStream(file);

			//Create POI Object based on File Format
			if (fileFormat.equals("xls"))
				workbook = new HSSFWorkbook(testDataFile);
			else if (fileFormat.equals("xlsx"))
				workbook = new XSSFWorkbook(testDataFile);

		//Create sheet Object
		sheet= workbook.getSheet(sheetName);
		//Calculate the number of rows having data in the sheet
		rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		//System.out.println("Number of rows: "+rowCount);


		//Traverse through Excel and store Data as Hashmap. Create another Hashmap to store Test data of a Test Case with multiple rows
		for (int i=1; i<=rowCount;i++)
		{
			row=sheet.getRow(i);
			formatter = new DataFormatter(); 
			executionFlag = formatter.formatCellValue(sheet.getRow(i).getCell(0));
			testSceName = formatter.formatCellValue(sheet.getRow(i).getCell(1));
			testCasName = formatter.formatCellValue(sheet.getRow(i).getCell(2));
						
			/*System.out.println("From excel"+testSceName+testCasName);
			System.out.println("From excel"+executionFlag.toLowerCase());
			System.out.println("From data"+scenarioName);
			System.out.println("From data"+testCaseName);
			*/
			
			if(executionFlag.toLowerCase().contentEquals ("true") && scenarioName.contentEquals(testSceName) && testCaseName.contentEquals(testCasName)){
				System.out.println("Test Data reading started for "+scenarioName+":"+testCaseName);
				for (int j = 3; j < row.getLastCellNum(); j=j+2) {
					testData.put(formatter.formatCellValue(sheet.getRow(i).getCell(j)),formatter.formatCellValue(sheet.getRow(i).getCell(j+1)));
				}
				System.out.println("Test Data reading completed for "+scenarioName+":"+testCaseName);
			}
			else if(!(executionFlag.toLowerCase().contentEquals("true")) && scenarioName.contentEquals(testSceName) && testCaseName.contentEquals(testCasName))
			{
			executionFlag1= true;	
			}
			else
			{
				actualRowCount=actualRowCount+1;
			}
		}
		//Exception when Test data is not found for the Test Case Name
		if(actualRowCount==rowCount)
		{
			//System.out.println(actualRowCount);
			Assert.fail("Test Data is not found for the testCase:"+scenarioName+testCaseName);		
		}
		if (executionFlag1==true){
			executionFlag1=false;
			//System.out.println("Test Data is not enabled for this test case");
			Assert.fail("Test Data is not enabled for this test case:"+scenarioName+testCaseName);
		}
		actualRowCount=0;	

		return testData;
	}
}