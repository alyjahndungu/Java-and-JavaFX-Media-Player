package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

public class PropertiesUtils {
	public static Map<String, String> readDetails()
	{
		Map<String, String> map = new HashMap<String, String>();
		try{
			FileInputStream in = new FileInputStream("Formats/details.properties");
			Properties p = new Properties();
			p.load(in);
			map.put("name", p.getProperty("name"));
			map.put("version", p.getProperty("version"));

		}
		catch (Exception e) {
			 e.printStackTrace();
		}
		return map;
	}

	public static List<String> readFormats()
	{
		List<String> formats = null;
		try
		{
		FileInputStream in = new FileInputStream("Formats/details.properties");
			Properties p = new Properties();
			p.load(in);
			formats = Arrays.asList(p.getProperty("formats").split(","));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return formats;
	}
}
