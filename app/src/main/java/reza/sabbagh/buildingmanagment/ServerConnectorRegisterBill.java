package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorRegisterBill extends AsyncTask{

	private String Link,RequestType,BillId,BillType,OldBillType,BillDate,BillAmount,OldbillAmount,CalculateType,AdminUsername;

	public ServerConnectorRegisterBill(String link, String requesttype, String billid, String billtype, String oldbilltype, String billdate
			, String billamount, String oldbillamount, String calculatetype, String adminusername){
		Link = link;
		RequestType = requesttype;
		BillId = billid;
		BillType = billtype;
		OldBillType = oldbilltype;
		BillDate = billdate;
		BillAmount = billamount;
		OldbillAmount = oldbillamount;
		CalculateType = calculatetype;
		AdminUsername = adminusername;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("billid","UTF8") + "=" + URLEncoder.encode(BillId,"UTF8");
			data += "&" + URLEncoder.encode("billtype","UTF8") + "=" + URLEncoder.encode(BillType,"UTF8");
			data += "&" + URLEncoder.encode("oldbilltype","UTF8") + "=" + URLEncoder.encode(OldBillType,"UTF8");
			data += "&" + URLEncoder.encode("billdate","UTF8") + "=" + URLEncoder.encode(BillDate,"UTF8");
			data += "&" + URLEncoder.encode("billamount","UTF8") + "=" + URLEncoder.encode(BillAmount,"UTF8");
			data += "&" + URLEncoder.encode("oldbillamount","UTF8") + "=" + URLEncoder.encode(OldbillAmount,"UTF8");
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
			RegisterBillActivity.resRegisterCharge = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
