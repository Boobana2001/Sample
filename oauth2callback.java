import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Pojo.Acc;
import Pojo.GooglePojo;
@WebServlet("/oauth2callback")
public class oauth2callback extends HttpServlet
{
   private static final long serialVersionUID = 1L;
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
	   
     try
     {
       String code = request.getParameter("code");
    	   
       String urlParameters = "code=" + 
         code + 
         "&client_id=1000.RVUY3ONH23YXJKN25MK9BG6CH8WJOZ" +
         "&client_secret=152213348c42bc37c8ae0207d6f03a2df5147b4711" + 
         "&redirect_uri=http://localhost:8080/Mail/oauth2callback" +
         "&grant_type=authorization_code";
       
       URL url = new URL("https://accounts.zoho.com/oauth/v2/token");
       URLConnection conn = url.openConnection();
       conn.setDoOutput(true);
       OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
       writer.write(urlParameters);
       writer.flush();
       String line1 = "";
       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       String line,line2;
       while ((line = reader.readLine()) != null)
       {
         line1 = line1 + line;
       }
      String s = GsonUtility.getJsonElementString("access_token", line1);
      
      
      
      URL url1 = new URL("https://mail.zoho.com/api/accounts");
       HttpURLConnection http = (HttpURLConnection) url1.openConnection();
       http.setRequestMethod("GET");
       http.setRequestProperty("Authorization", "Zoho-oauthtoken "+s);
       http.setDoOutput(true);
       http.setDoInput(true);
       http.connect();
      String  line3 = "";
       reader = new BufferedReader(new InputStreamReader( http.getInputStream()));
       while ((line2 = reader.readLine()) != null) {
    	   
         line3 = line3 + line2;
       }
       JsonArray gp = Acc.getAsJsonArray("data", line3);
       JsonObject jsonObj = gp.get(0).getAsJsonObject();
       GooglePojo acc = (GooglePojo)new Gson().fromJson(jsonObj, GooglePojo.class);
      String id=acc.getAccountId();
      
       
       
       URL url2 = new URL("https://mail.zoho.com/api/accounts/"+id+"/messages/view");
       HttpURLConnection http1 = (HttpURLConnection) url2.openConnection();
       http1.setRequestMethod("GET");
       http1.setRequestProperty("Authorization", "Zoho-oauthtoken "+s);
       http1.setDoOutput(true);
       http1.setDoInput(true);
       http1.connect();
      String  temp = "";
      String temp1;
       reader = new BufferedReader(new InputStreamReader( http1.getInputStream()));
       while ((temp1 = reader.readLine()) != null) {
    	   
         temp = temp + temp1;
       }
       writer.close();
       reader.close();
       request.setAttribute("data", temp);
       RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");  
      rd.forward(request, response);
     
     }
     catch (MalformedURLException e)
     {
       e.printStackTrace();
     }
     catch (ProtocolException e)
     {
       e.printStackTrace();
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
   }
   
}