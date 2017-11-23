package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
	static FileWriter logWriter;

	public static void main(String[] args) {
		String date = "10042017";

		for(int i=1;i<50;i++){
		String file = System.getProperty("user.dir") + "/" + date + ".data";
		try {
			File dir = new File(file);
			if (dir.exists()) {
				logWriter = new FileWriter(dir, true);
				logWriter.append("\n");
			} else {
				logWriter = new FileWriter(dir, true);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer("this is awesome record "+i);
		writer("I am cool");
		}

	}

	public static void writer(String message) {

		try {
			System.out.println(message);
			logWriter.append(message + ",");
			logWriter.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
