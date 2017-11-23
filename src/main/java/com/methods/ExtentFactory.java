package com.methods;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;

public class ExtentFactory {

	public static ExtentReports reporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();

	private synchronized static ExtentReports getExtentReport() {
		String folderLocation = System.getProperty("user.dir") + "/target/ExtentReport";
		
		if(!new File(folderLocation).exists())
			new File(folderLocation).mkdir();
		
		if (reporter == null) {
			// you can get the file name and other parameters here from a
			// config file or global variables

			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "/target/ExtentReport/ReactorExecution.html");
			htmlReporter.config().setReportName("ReactorCx Automation Testing Report");
			htmlReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");

			ExtentXReporter xReporter = new ExtentXReporter("192.168.1.186:27017");

			xReporter.config().setProjectName("Modified Project");

			reporter = new ExtentReports();
			reporter.attachReporter(htmlReporter, xReporter);
		}
		return reporter;
	}

	public synchronized static ExtentTest getTest(String testName, String testDescription) {

		// if this test has already been created return
		if (!nameToTestMap.containsKey(testName)) {
			Long threadID = Thread.currentThread().getId();
			ExtentTest test = getExtentReport().createTest(testName, testDescription);
			nameToTestMap.put(testName, test);
			threadToExtentTestMap.put(threadID, testName);
		}
		return nameToTestMap.get(testName);
	}

	public synchronized static ExtentTest getTest(String testName) {
		return getTest(testName, "");
	}

	/*
	 * At any given instance there will be only one test running in a thread. We
	 * are already maintaining a thread to extentest map. This method should be
	 * used after some part of the code has already called getTest with proper
	 * testcase name and/or description.
	 * 
	 * Reason: This method is not for creating test but getting an existing test
	 * reporter. sometime you are in a utility function and want to log
	 * something from there. Utility function or similar code sections are not
	 * bound to a test hence we cannot get a reporter via test name, unless we
	 * pass test name in all method calls. Which will be an overhead and a rigid
	 * design. However, there is one common association which is the thread ID.
	 * When testng executes a test it puts it launches a new thread, assuming
	 * test level threading, all tests will have a unique thread id hence call
	 * to this function will return the right extent test to use
	 */
	public synchronized static ExtentTest getTest() {
		Long threadID = Thread.currentThread().getId();

		if (threadToExtentTestMap.containsKey(threadID)) {
			String testName = threadToExtentTestMap.get(threadID);
			return nameToTestMap.get(testName);
		}
		// system log, this shouldnt happen but in this crazy times if it did
		// happen log it.
		return null;
	}

	public synchronized static void closeReport() {
		if (reporter != null) {
			reporter.flush();
		}
	}

}