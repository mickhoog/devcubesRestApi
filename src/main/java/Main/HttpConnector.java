package Main;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpConnector {
	
	public HttpConnector() 
	{}
	
	public void sendPost(String urlsource, String params) {
		try {
			byte[] postParams = params.getBytes(StandardCharsets.UTF_8);
			URL url = new URL(urlsource);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("POST");
			huc.setRequestProperty("charset", "utf-8");
			huc.setRequestProperty("Content-Length", Integer.toString(postParams.length));
			huc.setDoOutput(true);
			huc.setUseCaches(false);
			huc.setInstanceFollowRedirects(false);
			huc.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
			try(DataOutputStream dos = new DataOutputStream(huc.getOutputStream())) {
				dos.write(postParams);
			}	
			huc.getResponseCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
