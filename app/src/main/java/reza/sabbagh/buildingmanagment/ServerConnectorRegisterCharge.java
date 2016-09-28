package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorRegisterCharge extends AsyncTask{

	private String Link,RequestType,UnitNumber,OldUnitNumber,BuildingNumber,BillType,BillDate,BillAmount,CalculateType,AdminUsername;

	public ServerConnectorRegisterCharge(String link, String requesttype, String unitnumber, String oldunitnumber, String buildingnumber, String billtype
			, String billdate, String billamount, String calculatetype,String adminusername){
		Link = link;
		RequestType = requesttype;
		UnitNumber = unitnumber;
		OldUnitNumber = oldunitnumber;
		BuildingNumber = buildingnumber;
		BillType = billtype;
		BillDate = billdate;
		BillAmount = billamount;
		CalculateType = calculatetype;
		AdminUsername = adminusername;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("unitnumber","UTF8") + "=" + URLEncoder.encode(UnitNumber,"UTF8");
			data += "&" + URLEncoder.encode("oldunitnumber","UTF8") + "=" + URLEncoder.encode(OldUnitNumber,"UTF8");
			data += "&" + URLEncoder.encode("buildingnumber","UTF8") + "=" + URLEncoder.encode(BuildingNumber,"UTF8");
			data += "&" + URLEncoder.encode("billtype","UTF8") + "=" + URLEncoder.encode(BillType,"UTF8");
			data += "&" + URLEncoder.encode("billdate","UTF8") + "=" + URLEncoder.encode(BillDate,"UTF8");
			data += "&" + URLEncoder.encode("billamount","UTF8") + "=" + URLEncoder.encode(BillAmount,"UTF8");
			data += "&" + URLEncoder.encode("calculatetype","UTF8") + "=" + URLEncoder.encode(CalculateType,"UTF8");
			data += "&" + URLEncoder.encode("adminusername","UTF8") + "=" + URLEncoder.encode(AdminUsername,"UTF8");
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
			RegisterChargeActivity.resRegisterCharge = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
