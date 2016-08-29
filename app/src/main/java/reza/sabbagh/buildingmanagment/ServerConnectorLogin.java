package reza.sabbagh.buildingmanagment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.AsyncTask;

@SuppressWarnings("rawtypes")
public class ServerConnectorLogin extends AsyncTask{

	private String Link="",Username,Password;
	
	public ServerConnectorLogin(String link, String username,String password){
		Link = link;
		Username = username;
		Password = password;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("username","UTF8") + "=" + URLEncoder.encode(Username,"UTF8");
			data += "&" + URLEncoder.encode("password","UTF8") + "=" + URLEncoder.encode(Password,"UTF8");
			URL mylink = new URL(Link);
			URLConnection connect = mylink.openConnection();
			connect.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connect.getOutputStream());
			wr.write(data);
			wr.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line=reader.readLine()) != null){
				sb.append(line);
			}
			LoginActivity.resLogin = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
