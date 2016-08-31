package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorUsersInfo extends AsyncTask{

	private String Link,RequestType,BAdminUsername;

	public ServerConnectorUsersInfo(String link, String requesttype, String badminusername){
		Link = link;
		RequestType = requesttype;
		BAdminUsername = badminusername;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("buildingadminusername","UTF8") + "=" + URLEncoder.encode(BAdminUsername,"UTF8");
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
			BuildingInformationFragment.resBuildingInfo = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
