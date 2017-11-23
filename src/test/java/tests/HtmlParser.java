package tests;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;


public class HtmlParser {
	
	ExtentReports reporter;
	ExtentHtmlReporter htmlReporter;
	ExtentXReporter xReporter;
	ExtentTest test;
	

	public static void main(String[] args) {
		
		//initReports();

		
	}
	
	public void generateReport(String filePath){
		Document htmlFile = null;
		try {
			htmlFile = Jsoup.parse(new File(filePath), "ISO-8859-1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String title = htmlFile.title();
		Elements p = htmlFile.getElementsByAttribute("bdd");
		for(Element e : p){
			System.err.println("================================================================");
			System.out.println("Test Case : "+e.getElementsByTag("span").get(0).text());
			String testDescription = e.getElementsByTag("div").get(3).attr("class", "test-desc").text();
			test = createTest(reporter, e.getElementsByTag("span").get(0).text(), testDescription);
			
			
			
			Elements tables = e.getElementsByTag("table");
			for(Element table : tables){
				Elements trs = table.getElementsByTag("tr");
					for(Element tr : trs ){
						System.out.println(tr.attr("status"));
						
						Elements tds = tr.getElementsByTag("td");
						if(tds.size()>0){
							report(tr.attr("status"), tr.getElementsByTag("td").get(2).attr("class", "step-details").text());
						System.out.println(tr.getElementsByTag("td").get(2).attr("class", "step-details").text());
						}
					}
				
				
			}
			reporter.flush();
			
			System.err.println("================================================================");
		}
			System.out.println(title);
	}
	
	
	public ExtentTest createTest(ExtentReports extentReports, String testCaseName, String testcaseDescription){
		return extentReports.createTest(testCaseName, testcaseDescription);
	}
	
	public void initReports(String projectname, String reportName){
		
		if (reporter == null) {
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/"+projectname+reportName+".html");
			htmlReporter.config().setReportName(reportName);
			htmlReporter.config().setDocumentTitle(projectname);

			ExtentXReporter xReporter = new ExtentXReporter("192.168.1.186:27017");
			
			
			xReporter.config().setProjectName(projectname);
			xReporter.config().setReportName(reportName);

			reporter = new ExtentReports();
			reporter.attachReporter(htmlReporter, xReporter);
			
			reporter.setReportUsesManualConfiguration(true);

		}
		
	}
	
	public void report(String status, String comment){
		if(status.equalsIgnoreCase("info")){
			test.info(comment);
		}else if(status.equalsIgnoreCase("fail")){
			test.fail(comment);
		}else if(status.equalsIgnoreCase("fatal")){
			test.fatal(comment);
		}else{
			test.pass(comment);
		}
		
	}

}
