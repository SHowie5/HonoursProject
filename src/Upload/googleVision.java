package Upload;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import javax.imageio.ImageIO;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.org.apache.xml.internal.utils.URI;

import stores.UploadStore;

public class googleVision {

	private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?";
	private static final String API_KEY = "key=AIzaSyCZYDUtP7SqqMp1jAsy7uzvryRLV4qp5hI";
	UploadStore us = new UploadStore();

	public void main(String filePath) throws Exception {
		
		boolean check = checkInput(filePath);
		
		if(check) {
			
			URL serverUrl = new URL(TARGET_URL + API_KEY);
			URLConnection urlConnection = serverUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/json");
			httpConnection.setDoOutput(true);
			
			
			// Request for URL or Google bucket file
			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));
			httpRequestBodyWriter.write
			("{\"requests\": [{ \"features\": [ {\"type\": \"WEB_DETECTION\""
			+"}], \"image\": {\"source\": { \"image_uri\":"
			+"\" "+ filePath +"\"}}}]}");
			
			httpRequestBodyWriter.close();
			
			//String response = httpConnection.getResponseMessage();

			if(httpConnection.getInputStream()==null)
			{
				System.out.println("No stream");
				return;
			}

			Scanner httpResponseScanner = new Scanner(httpConnection.getInputStream());
			String resp = "";while(httpResponseScanner.hasNext())
			{
				String line = httpResponseScanner.nextLine();
				resp += line;
				System.out.println(resp); // alternatively, print the line of response
			}
			httpResponseScanner.close();
			
		} else {
		
			URL serverUrl = new URL(TARGET_URL + API_KEY);
			URLConnection urlConnection = serverUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
	
			
			BufferedImage img = ImageIO.read(new File(filePath));	   
		    String imageString = null;
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	
		        try {
		            ImageIO.write(img, "jpg", bos);
		            byte[] imageBytes = bos.toByteArray();
	
		            Base64 encoder = new Base64();	
		            imageString = Base64.encode(imageBytes);
	
		            bos.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/json");
			httpConnection.setDoOutput(true);
	
			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));
			
			// Request for local file
			httpRequestBodyWriter.write
			("{\"requests\": [{ \"features\": [ {\"type\": \"WEB_DETECTION\""
			+"}], \"image\": {\"content\":"
			+"\" "+ imageString +"\"}}]}");
			
			httpRequestBodyWriter.close();
	
			//String response = httpConnection.getResponseMessage();
	
			if(httpConnection.getInputStream()==null)
			{
				System.out.println("No stream");
				return;
			}
	
			Scanner httpResponseScanner = new Scanner(httpConnection.getInputStream());
			String resp = "";
			while(httpResponseScanner.hasNext())
			{
				String line = httpResponseScanner.nextLine();
				resp += line;					
			}
			//System.out.println(resp); // alternatively, print the line of response
			us.setBlipparResponse(resp);
			httpResponseScanner.close();	
		}
		
	}
	
	public static boolean checkInput(String input) {
		try {
			URI uri = new URI(input);
			return uri.getScheme().equals("http") || uri.getScheme().equals("https") || uri.getScheme().equals("gs");

		} catch (Exception e) {
			return false;
		}

	}

}
