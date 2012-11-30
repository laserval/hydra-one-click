package com.findwise.hydra.oneclick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class AdminServiceClient {

	private final String baseUrl;
	
	private static final String LIBRARIES_ENDPOINT = "/libraries/";
	
	public AdminServiceClient() {
		 baseUrl = "http://localhost:9090/hydra";
	}
	
	public AdminServiceClient(String url) {
		baseUrl = url;
	}
	
	public void postStages(Map<String, String> stages) {
		for (Entry<String, String> entry : stages.entrySet()) {
			try {
				postJson(baseUrl + LIBRARIES_ENDPOINT + entry.getKey(), entry.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void postLibraries(Map<String, String> libraries) {
		for (Entry<String, String> entry : libraries.entrySet()) {
			try {
				postFile(baseUrl + LIBRARIES_ENDPOINT + entry.getKey(), entry.getValue());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void postJson(String url, String path) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpContext context = new BasicHttpContext();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);

		File jsonFile = new File(path);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(jsonFile)));

		StringBuilder sb = new StringBuilder();
		String temp;
		while ((temp = reader.readLine()) != null) {
			sb.append(temp);
		}
		reader.close();

		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(sb.toString());
		post.addHeader("content-type", "application/json");
		post.setEntity(entity);
		HttpEntity responseEntity = client.execute(post, context).getEntity();
		client.getConnectionManager().shutdown();
	}

	public void postFile(String url, String path) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpContext context = new BasicHttpContext();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);

		File stageFile = new File(path);

		HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		// For File parameters
		entity.addPart("file", new FileBody(stageFile));

		post.setEntity(entity);

		HttpEntity responseEntity = client.execute(post, context).getEntity();
		client.getConnectionManager().shutdown();

	}
	
}
