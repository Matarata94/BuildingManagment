package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorDeleteBill extends AsyncTask{

	private String Link,RequestType,AdminUsername,UnitNumber,BillType,BillAmount;

	public ServerConnectorDeleteBill(String link, String requesttype, String adminusername, String unitnumber, String billtype, String billamount){
		Link = link;
		RequestType = requesttype;
		AdminUsername = adminusername;
		UnitNumber = unitnumber;
		BillType = billtype;
		BillAmount = billamount;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("adminusername","UTF8") + "=" + URLEncoder.encode(AdminUsername,"UTF8");
			data += "&" + URLEncoder.encode("unitnumber","UTF8") + "=" + URLEncoder.encode(UnitNumber,"UTF8");
			data += "&" + URLEncoder.encode("billtype","UTF8") + "=" + URLEncoder.encode(BillType,"UTF8");
			data += "&" + URLEncoder.encode("billamount","UTF8") + "=" + URLEncoder.encode(BillAmount,"UTF8");
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
			BillsInformationFragment.resChargeInfoDelete = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
