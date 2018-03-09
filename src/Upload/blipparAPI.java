package Upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import stores.*;

//Blippar API code from website: https://developer.blippar.com/portal/vs-api/code-samples/java/

public class blipparAPI {
	static String token = null;
	private static String clientId;
	private static String clientSecret;

	UploadStore us = new UploadStore();

	public void main(String path) throws Exception {
		blipparAPI client = new blipparAPI();
		client.init("276877b7937e4589941d83043411edd6", "fe072874ed264d34b71cf43e9d778a06");
		// Returns Token if Id and Secret keys are not passed
		token = client.getToken();

		// Returns Token if Id and Secret keys are passed
		/*
		 * token = client.getToken("{ enter blippar api client id here }",
		 * "{ enter blippar api client secret here }",null);
		 */

		// System.out.println(token);
		String response = client.imageLookup(path);
		// System.out.println(response);
		us.setBlipparResponse(response);
	}

	void init(String clientId, String clientSecret) {
		blipparAPI.clientId = clientId;
		blipparAPI.clientSecret = clientSecret;
	}

	// Request token
	String getToken(String clientId, String clientSecret, String grantType) throws Exception {
		String url = "https://bauth.blippar.com/token";
		grantType = grantType != null ? grantType : "client_credentials";
		clientId = clientId != null ? clientId : clientId;
		clientSecret = clientSecret != null ? clientSecret : clientSecret;
		Map<String, String> hash = new HashMap<>();
		hash.put("client_id", clientId);
		hash.put("client_secret", clientSecret);
		hash.put("grant_type", grantType);
		return get(url, hash);
	}

	/*
	 * Client object, if id and secret parameters are not passed, default parameters
	 * will be used.
	 */
	String getToken() throws Exception {
		String url = "https://bauth.blippar.com/token";
		Map<String, String> hash = new HashMap<>();
		hash.put("client_id", clientId);
		hash.put("client_secret", clientSecret);
		hash.put("grant_type", "client_credentials");
		return get(url, hash);
	}

	// Method to make an API:GET request for a specified URL
	String get(String url, Map<String, String> parameters) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(url);
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			uriBuilder.addParameter(parameter.getKey(), parameter.getValue());
		}
		URI uri = uriBuilder.build();
		HttpGet request = new HttpGet(uri);
		HttpResponse response = httpclient.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		HttpEntity respEntity = response.getEntity();

		if (respEntity == null) {
			throw new HttpException("GET request failed: " + responseCode);
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	// Method to make an API:POST request to a specified URL
	String post(String url, String path, String auth) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		File file = new File(path);
		HttpEntity mpEntity = MultipartEntityBuilder.create()
				.addBinaryBody("input_image", file, ContentType.create("image/jpg"), file.getName()).build();
		httpPost.setEntity(mpEntity);
		httpPost.setHeader("Authorization", auth);
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity respEntity = response.getEntity();
		if (respEntity == null) {
			throw new HttpException("GET request failed: " + response.getStatusLine().getStatusCode());
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	// POST Request for /v1/imageLookup
	String imageLookup(String path) throws Exception {
		String url = "https://bapi.blippar.com/v1/imageLookup";
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(token);
		String access_token = jsonObject.get("access_token").getAsString();
		String token_type = jsonObject.get("token_type").getAsString();
		String auth = token_type + " " + access_token;
		return post(url, path, auth);
	}

}
