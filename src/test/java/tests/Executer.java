package tests;

import java.util.Iterator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.methods.FileHandler;

public class Executer extends HtmlParser {
	
  @Test(dataProvider="dp")
  public void f(String name) {
	  String fileName = name.substring(name.lastIndexOf("\\")+1, name.length());
	  
	  String projectName = fileName.split("_")[0];
	  String reportName = fileName.split("_")[2]+"-"+fileName.split("_")[3].substring(0,  fileName.split("_")[3].indexOf("."));
	  
	  try {
		  initReports(projectName, reportName);
		  generateReport(name);
		  FileHandler.move(name, "true");
		
	} catch (Exception e) {
		System.out.println(e.getMessage());
		  FileHandler.move(name, "false");
	}
	  reporter = null;
	  
  }	
  
  @BeforeMethod
  public void beforeMethod(){
  }
  
  @AfterMethod
  public void afterMethod() {
	  
  }
  
  @DataProvider
  public Iterator<String> dp(){
	  
	  FileHandler.readInputFileList();
	  return FileHandler.fileList.iterator();
	  }

}
