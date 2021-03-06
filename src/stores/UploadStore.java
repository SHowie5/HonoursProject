package stores;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Upload.*;

public class UploadStore{

	public static String filePath = null;
	public static String blipparResponse = null;
	public static String jsonResult = null;
	public static String keyword = null;
	public static ArrayList<String> ebayPics = new ArrayList<String>();
	public static ArrayList<String> htmlResults = new ArrayList<String>();
	public static ArrayList<ebayResults> results = new ArrayList<ebayResults>();
	public static BufferedImage bImage = null;
	public static String filename = null;

	
	public void Uploader() {

	}
	
	public void setEbayResults(double score, String itemID, String title, String galleryUrl, String currentPrice, String itemUrl) {
		results.add(new ebayResults(score, title, itemID, galleryUrl, currentPrice, itemUrl));
	}
		
	public ArrayList<ebayResults> getResults() {
		//Compares the image histogram scores and order results
		Collections.sort(results, Comparator.comparingDouble(h -> h.score));
		return results;
	}

	public void setFilePath(String path) {
		UploadStore.filePath = path;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setBlipparResponse(String response) {
		UploadStore.blipparResponse = response;
		setKeyword(blipparResponse);
	}

	public String getBlipparResponse() {
		return blipparResponse;
	}

	public void setKeyword(String jsonString) {

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = jsonParser.parse(jsonString).getAsJsonObject();
		JsonElement response = jo.getAsJsonArray("responses").get(0);
		JsonObject obj = response.getAsJsonObject();
		JsonObject wd = obj.get("webDetection").getAsJsonObject();
		JsonArray we = wd.getAsJsonArray("webEntities");
		JsonObject desc = we.get(0).getAsJsonObject();
		String keyword = desc.get("description").toString();

		UploadStore.keyword = keyword;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setEbayArray(String pic) {
		ebayPics.add(pic);
	}
	
	public ArrayList<String> getEbayArray() {
		return ebayPics;
	}
	
	public void setHTMLresults(String html){
		htmlResults.add(html);
	}

	
	public ArrayList<String> getHTMLresults() {
		return htmlResults;
	}
	
	public void clearHTML() {
		htmlResults.clear();
	}
	
	public void clearEbayResults() {
		results.clear();
	}
	
	public void setImage(BufferedImage bImage) {
		UploadStore.bImage = bImage;
	}
	
	public BufferedImage getImage() {
		return bImage;
	}
	
	public void setFilename(String filename) {
		UploadStore.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}

}
