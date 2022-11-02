package Pojo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Acc
{
   static String className = "Acc";
   static Gson gson = new Gson();
     
   public static JsonArray getAsJsonArray(String name, String gs)
   {
     try
     {
       JsonObject json = (JsonObject)new JsonParser().parse(gs);
       return json.get(name).getAsJsonArray();
     }
     catch (Exception localException) {}
     return null;
   }

}