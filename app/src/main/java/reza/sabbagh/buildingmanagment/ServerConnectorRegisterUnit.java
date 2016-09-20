package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorRegisterUnit extends AsyncTask{

	private String Link,RequestType,UnitNumber,BuildingNumber,OwnerName,ResidentName,FloorNumber,AreaSize,ResidenceDate,NumberOfResident
			,PostalCode,ChargeDefaultAmount,StaticChargeState,AdminUsername;

	public ServerConnectorRegisterUnit(String link, String requesttype, String unitnumber, String buildingnumber, String ownername, String residentname
			, String floornumber, String areasize, String residencedate, String numberofresident, String postalcode, String chargeDefaultamount
			, String staticchargestate, String adminusername){
		Link = link;
		RequestType = requesttype;
		UnitNumber = unitnumber;
		BuildingNumber = buildingnumber;
		OwnerName = ownername;
		ResidentName = residentname;
		FloorNumber = floornumber;
		AreaSize = areasize;
		ResidenceDate = residencedate;
		NumberOfResident = numberofresident;
		PostalCode = postalcode;
		ChargeDefaultAmount = chargeDefaultamount;
		StaticChargeState = staticchargestate;
		AdminUsername = adminusername;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("unitnumber","UTF8") + "=" + URLEncoder.encode(UnitNumber,"UTF8");
			data += "&" + URLEncoder.encode("buildingnumber","UTF8") + "=" + URLEncoder.encode(BuildingNumber,"UTF8");
			data += "&" + URLEncoder.encode("ownername","UTF8") + "=" + URLEncoder.encode(OwnerName,"UTF8");
			data += "&" + URLEncoder.encode("residentname","UTF8") + "=" + URLEncoder.encode(ResidentName,"UTF8");
			data += "&" + URLEncoder.encode("floornumber","UTF8") + "=" + URLEncoder.encode(FloorNumber,"UTF8");
			data += "&" + URLEncoder.encode("areasize","UTF8") + "=" + URLEncoder.encode(AreaSize,"UTF8");
			data += "&" + URLEncoder.encode("residencedate","UTF8") + "=" + URLEncoder.encode(ResidenceDate,"UTF8");
			data += "&" + URLEncoder.encode("numberofresident","UTF8") + "=" + URLEncoder.encode(NumberOfResident,"UTF8");
			data += "&" + URLEncoder.encode("postalcode","UTF8") + "=" + URLEncoder.encode(PostalCode,"UTF8");
			data += "&" + URLEncoder.encode("chargedefaultamount","UTF8") + "=" + URLEncoder.encode(ChargeDefaultAmount,"UTF8");
			data += "&" + URLEncoder.encode("staticchargestate","UTF8") + "=" + URLEncoder.encode(StaticChargeState,"UTF8");
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
			RegisterUnitActivity.resRegisterUnit = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
