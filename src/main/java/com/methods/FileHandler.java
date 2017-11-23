package com.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

	static public List<String> fileList = new ArrayList<>();

	static String processedFolder = System.getProperty("user.dir") + "/ReportFiles/ProcessedFiles";
	static String errorFolder = System.getProperty("user.dir") + "/ReportFiles/ErrorFiles";
	static String folderPath = System.getProperty("user.dir") + "/ReportFiles/InFiles";

	public static void main(String[] args) {

		
		listFilesAndFilesSubDirectories(folderPath);
		for (int i = 0; i < fileList.size(); i++) {
			String name = fileList.get(i);
			if (name.contains("Copy")) {
				move(name, "true");
			}
			System.out.println(fileList.get(i));

		}
	}
	
	public static void readInputFileList(){
		listFilesAndFilesSubDirectories(folderPath);
	}

	public static void listFilesAndFilesSubDirectories(String directoryName) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				fileList.add(file.getAbsolutePath());
				// System.out.println(file.getName());
			} else if (file.isDirectory()) {
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
	}

	public static void move(String moveFile, String processedIndicator) {

		String fileName = moveFile.substring(moveFile.lastIndexOf("\\") + 1, moveFile.length());

		FileInputStream inStream = null;
		OutputStream outStream = null;
		File bfile = null;
		try {

			File afile = new File(moveFile);
			if (processedIndicator.equalsIgnoreCase("true")) {
				bfile = new File(processedFolder + "/" + fileName);
			} else {
				bfile = new File(errorFolder + "/" + fileName);
			}
			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			// delete the original file
			afile.delete();

			System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
