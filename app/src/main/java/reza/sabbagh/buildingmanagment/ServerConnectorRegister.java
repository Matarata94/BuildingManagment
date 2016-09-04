package reza.sabbagh.buildingmanagment;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SuppressWarnings("rawtypes")
public class ServerConnectorRegister extends AsyncTask{

	private String Link,RequestType,RegisterAs,Username,Password,Fname,Lname,PhoneNumber,HomeNumber,Email,BuildingNumber,UnitNumber,OldUnitNumber,AdminUsername;

	public ServerConnectorRegister(String link, String requesttype, String registeras, String username, String password, String fname, String lname, String phonenumber,
								   String homenumber, String email, String buildingnumber, String unitnumber, String oldunitnumber, String adminusername){
		Link = link;
		RequestType = requesttype;
		RegisterAs = registeras;
		Username = username;
		Password = password;
		Fname = fname;
		Lname = lname;
		PhoneNumber = phonenumber;
		HomeNumber = homenumber;
		Email = email;
		BuildingNumber = buildingnumber;
		UnitNumber = unitnumber;
		OldUnitNumber = oldunitnumber;
		AdminUsername = adminusername;
	}
	protected String doInBackground(Object... params) {
		try{
			String data = URLEncoder.encode("requesttype","UTF8") + "=" + URLEncoder.encode(RequestType,"UTF8");
			data += "&" + URLEncoder.encode("registeras","UTF8") + "=" + URLEncoder.encode(RegisterAs,"UTF8");
			data += "&" + URLEncoder.encode("username","UTF8") + "=" + URLEncoder.encode(Username,"UTF8");
			data += "&" + URLEncoder.encode("password","UTF8") + "=" + URLEncoder.encode(Password,"UTF8");
			data += "&" + URLEncoder.encode("fname","UTF8") + "=" + URLEncoder.encode(Fname,"UTF8");
			data += "&" + URLEncoder.encode("lname","UTF8") + "=" + URLEncoder.encode(Lname,"UTF8");
			data += "&" + URLEncoder.encode("phonenumber","UTF8") + "=" + URLEncoder.encode(PhoneNumber,"UTF8");
			data += "&" + URLEncoder.encode("homenumber","UTF8") + "=" + URLEncoder.encode(HomeNumber,"UTF8");
			data += "&" + URLEncoder.encode("email","UTF8") + "=" + URLEncoder.encode(Email,"UTF8");
			data += "&" + URLEncoder.encode("buildingnumber","UTF8") + "=" + URLEncoder.encode(BuildingNumber,"UTF8");
			data += "&" + URLEncoder.encode("unitnumber","UTF8") + "=" + URLEncoder.encode(UnitNumber,"UTF8");
			data += "&" + URLEncoder.encode("oldunitnumber","UTF8") + "=" + URLEncoder.encode(OldUnitNumber,"UTF8");
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
			RegisterUserActivity.resRegister = sb.toString();
		}catch(Exception e){
			
		}
		
		return "";
	}

}
