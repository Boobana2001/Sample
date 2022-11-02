
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtility
{
   static String className = "GsonUtility";
   static Gson gson = new Gson();
   
   public static String tojson(Object object)
   {
	  // System.out.println(gson.toJson(object));
     return gson.toJson(object);
   }
   
   
   public static String getJsonElementString(String name, String gs)
   {
     try
     {
       JsonObject json = (JsonObject)new JsonParser().parse(gs);
       return json.get(name).getAsString();
     }
     catch (Exception localException) {}
     return null;
   }

}