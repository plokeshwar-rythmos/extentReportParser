package com.methods;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.aventstack.extentreports.ExtentTest;

public class ParserFlow {

	public enum Key {
		startTime, pass, fail, info, endTime, passed, failed
	}

	public void getFlowValidation(String keyValue, String data, ExtentTest test) {

		switch (getKey(keyValue)) {

		case startTime:
			System.out.println(keyValue + " : " + data);

			break;

		case pass:
			System.out.println(keyValue + " : " + data);
			test.pass(data);
			break;

		case fail:
			System.out.println(keyValue + " : " + data);
			test.fail(data);
			break;

		case info:
			System.out.println(keyValue + " : " + data);
			if (data.contains("href")) {
				String tmp = data.substring(data.indexOf("./") + 1, data.indexOf(".txt") + 4);
				test.info(reportFile("D:/ReportFiles" + tmp, tmp.substring(tmp.indexOf("/") + 1, tmp.indexOf(".txt"))));

			} else {
				test.info(data);
			}
			break;

		case endTime:
			System.out.println(keyValue + " : " + data);
			break;

		default:
			break;
		}

	}

	public void cmsReportLoader(String keyValue, String data, ExtentTest test) {

		switch (getKey(keyValue)) {

		case startTime:
			System.out.println(keyValue + " : " + data);

			break;

		case passed:
			System.out.println(keyValue + " : " + data);
			test.pass(data);
			break;

		case failed:
			System.out.println(keyValue + " : " + data);
			test.fail(data);
			break;

		case info:
			System.out.println(keyValue + " : " + data);
			test.info(data);
			break;

		case endTime:
			System.out.println(keyValue + " : " + data);
			break;

		default:
			break;
		}

	}

	public Key getKey(String keyValue) {

		for (Key k : Key.values()) {
			if (keyValue.equalsIgnoreCase(k.name())) {
				return k;
			}
		}

		return null;
	}

	public String reportFile(String filePath, String fileName) {
		String et = "<details style=\"color: blue;\"><summary><b>" + fileName + "</b></summary>";

		try {
			String fileString = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			et = et +"<textarea name=\"message\" rows=\"40\" cols=\"100\" disabled>" +fileString +"</textarea>"+ "</details>";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return et;

	}

	public static void main(String[] args) {
		try {
			String fileString = new String(
					Files.readAllBytes(Paths.get("D:/ReportFiles/InputMessageXML1507618482338.log")),
					StandardCharsets.UTF_8);
			System.out.println("Contents (Java 7 with character encoding ) : " + fileString);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
