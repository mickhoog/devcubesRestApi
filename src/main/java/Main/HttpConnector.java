package Main;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.tomcat.util.codec.binary.Base64;

public class HttpConnector {

	private String username = "";
	private String password = "";
	
	public HttpConnector(String username, String password) 
	{
		this.username = username;
		this.password = password;		
	}
	
	public HttpConnector()
	{}
	
	public int sendPost(String urlsource, String params, boolean authenticate) {
		int responseCode = -1;
 		try {
 			byte[] postParams = params.getBytes(StandardCharsets.UTF_8);
 			URL url = new URL(urlsource);
 			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
 			if(authenticate) {
 				byte[] userPassByte = (username+":"+password).getBytes(StandardCharsets.UTF_8);
 				String encoded =  new String(Base64.encodeBase64(userPassByte));
 				huc.setRequestProperty("Authorization", "Basic " + encoded);;
 			}
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
 			responseCode = huc.getResponseCode();
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		return responseCode;
 	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
