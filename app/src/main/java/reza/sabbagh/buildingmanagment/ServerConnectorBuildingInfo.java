package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorBuildingInfo extends AsyncTask{

	private String Link,RequestType,BAdminUsername,BNumber,BType,BName,BUnitsCount,BPalque,BAddress;

	public ServerConnectorBuildingInfo(String link, String requesttype, String badminusername, String bnumber, String btype, String bname,
									   String bunitscount, String bplaque, String baddress){
		Link = link;
		RequestType = requesttype;
		BAdminUsername = badminusername;
		BNumber = bnumber;
		BType = btype;
		BName = bname;
		BUnitsCount = bunitscount;
		BPalque = bplaque;
		BAddress = baddress;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("buildingadminusername","UTF8") + "=" + URLEncoder.encode(BAdminUsername,"UTF8");
			data += "&" + URLEncoder.encode("buildingnumber","UTF8") + "=" + URLEncoder.encode(BNumber,"UTF8");
			data += "&" + URLEncoder.encode("buildingtype","UTF8") + "=" + URLEncoder.encode(BType,"UTF8");
			data += "&" + URLEncoder.encode("buildingname","UTF8") + "=" + URLEncoder.encode(BName,"UTF8");
			data += "&" + URLEncoder.encode("buildingunitscount","UTF8") + "=" + URLEncoder.encode(BUnitsCount,"UTF8");
			data += "&" + URLEncoder.encode("buildingplaque","UTF8") + "=" + URLEncoder.encode(BPalque,"UTF8");
			data += "&" + URLEncoder.encode("buildingaddress","UTF8") + "=" + URLEncoder.encode(BAddress,"UTF8");
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
