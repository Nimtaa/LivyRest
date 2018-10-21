import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void deleteSession (String targetURL){

    }

    public static JSONObject sendGet(String targetURL){
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        //return "no response";
        return null;
    }

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            //connection.connect();
            //Send Request
//            DataOutputStream dor = new DataOutputStream(
//                    connection.getOutputStream()
//            );
            OutputStream wr = connection.getOutputStream();
            wr.write(urlParameters.getBytes("UTF-8"));
            wr.close();
            System.out.println(connection.getResponseCode());
//            dor.write(urlParameters);
//            dor.close();
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
//            int response = connection.getResponseCode();
            return response.toString();
//            return String.valueOf(response)
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "no response";
    }

    private static JSONObject searchJSON (JSONObject object){

        JSONArray childObject = (JSONArray) object.get("statements");
        JSONObject outputObject = (JSONObject) childObject.get(0);
        JSONObject out2Object = (JSONObject) outputObject.get("output");
        JSONObject dataObject = (JSONObject) out2Object.get("data");
        System.out.println("HII "+dataObject.toJSONString());
        return dataObject;


    }
    public static void main(String[] args) {
        String host = "http://localhost:8998";
        JSONObject json = new JSONObject();
        json.put("kind","spark");
        JSONObject codedata = new JSONObject();
        codedata.put("code","val NUM_SAMPLES = 100000;\n" +
                "val count = sc.parallelize(1 to NUM_SAMPLES).map { i =>\n" +
                "val x = Math.random();\n" +
                "val y = Math.random();\n" +
                "if (x*x + y*y < 1) 1 else 0\n" +
                "}.reduce(_ + _);\n" +
                "println( 4.0 * count / NUM_SAMPLES)");

        //executePost(host+"/sessions",json.toString());
        //send code
        //System.out.println(executePost(host+"/sessions/1/statements",codedata.toString()));
        JSONObject result =  sendGet(host+"/sessions/1/statements");
        System.out.println(result.toJSONString());
        System.out.println(searchJSON(result));

        //System.out.println(sendGet(host+"/sessions/3/statements/2").get("output"));
       //System.out.println(executePost(host+"/sessions",json.toString()));
    }

}

















