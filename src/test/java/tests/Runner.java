package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class Runner {
	ExtentReports reporter;
	ExtentHtmlReporter htmlReporter;
	ExtentXReporter xReporter;
	ExtentTest test;
	String folderLocation = System.getProperty("user.dir") + "/build/ExtentReport";

	@Test
	public void test() {

		String time = String.valueOf(System.currentTimeMillis());

		System.out.println("Current Time in Mills"+time);

		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		Date resultdate = new Date(Long.valueOf(time));
		System.out.println("Current Time in format "+sdf.format(resultdate));

		String csvFile = "D:/ReportFiles/CCS10102017.data";
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			if (reporter == null) {
				htmlReporter = new ExtentHtmlReporter(folderLocation + "/CronExecution.html");
				htmlReporter.config().setReportName("Cron Report Loader");
				htmlReporter.config().setDocumentTitle("Cron Report Loader");
				

				ExtentXReporter xReporter = new ExtentXReporter("192.168.1.186");

				xReporter.config().setProjectName("CCS");
				xReporter.config().setReportName("CCS");
				xReporter.config().setServerUrl("http://192.168.1.186:1337");

				reporter = new ExtentReports();
				reporter.attachReporter(htmlReporter, xReporter);
				reporter.setReportUsesManualConfiguration(true);
				
				
			}

			while ((line = br.readLine()) != null) {

				String testName = line.substring(line.indexOf("testName"), line.indexOf("testDescription")-1).split("-")[1];
				String testDescription = line.substring(line.indexOf("testDescription"), line.indexOf("startTime")-1).split("-")[1];

				line = line.substring(line.indexOf("startTime"), line.length());
				// use comma as separator
				String[] record = line.split(cvsSplitBy);

				
				test = reporter.createTest(testName, testDescription);
				
				//test.getModel().setStartTime(getTime(Long.valueOf(record[0].split("-")[1])));
				

				for (int i = 0; i < record.length; i++) {
					System.out.println(record[i]);
					if (!record[i].contains("testName") && !record[i].contains("testDescription")) {
						new ParserFlow().getFlowValidation(record[i].split("-")[0], record[i].split("-")[1], test);
					}


				}
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
	
	private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        System.err.println(calendar.getTime());
        return calendar.getTime();      
    }

}
