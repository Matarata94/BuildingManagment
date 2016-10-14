package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorUnitsInfo extends AsyncTask{

	private String Link,RequestType,AdminUsername,StaticCharge;

	public ServerConnectorUnitsInfo(String link, String requesttype, String adminusername,String staticcharge){
		Link = link;
		RequestType = requesttype;
		AdminUsername = adminusername;
		StaticCharge = staticcharge;

	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("adminusername","UTF8") + "=" + URLEncoder.encode(AdminUsername,"UTF8");
			data += "&" + URLEncoder.encode("staticcharge","UTF8") + "=" + URLEncoder.encode(StaticCharge,"UTF8");
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
			UnitsInformationFragment.resUnitsInfo = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
