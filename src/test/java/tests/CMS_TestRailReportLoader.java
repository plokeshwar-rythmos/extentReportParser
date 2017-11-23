package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;
import com.methods.ParserFlow;
import com.mongodb.MongoClientURI;

public class CMS_TestRailReportLoader {
	ExtentReports reporter;
	ExtentHtmlReporter htmlReporter;
	ExtentXReporter xReporter;
	ExtentTest test;
	String folderLocation = System.getProperty("user.dir") + "/build/ExtentReport";

	@Test
	public void test() throws URISyntaxException {

		String csvFile = "D:/ReportFiles/webservice_run_27_09_2017_11_54_53.708.csv";
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			if (reporter == null) {
				htmlReporter = new ExtentHtmlReporter(folderLocation + "/CMS_ReportLoader.html");
				htmlReporter.config().setReportName("CMS Cron Report Loader");
				htmlReporter.config().setDocumentTitle("CMS Cron Report Loader");

				ExtentXReporter xReporter = new ExtentXReporter(new MongoClientURI("mongodb://pravin:pravin@ds159254.mlab.com:59254/extent"));
				
				
				xReporter.config().setProjectName("CMS Project");

				reporter = new ExtentReports();
				reporter.attachReporter(htmlReporter, xReporter);
				reporter.setReportUsesManualConfiguration(true);

			}

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] record = line.split(cvsSplitBy);

				String testName = record[0];
				String testDescription = record[0];

				test = reporter.createTest(record[1] + "-" + testName, testDescription);
				System.err.println(record[3]+"-"+record[1] + "-" + testName);
				new ParserFlow().cmsReportLoader(record[3], record[1] + "-" + testName, test);
				reporter.flush();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void beforeMethod() {

		if (!new File(folderLocation).exists())
			new File(folderLocation).mkdir();

	}

	@AfterMethod
	public void afterMethod() {
	}


}
