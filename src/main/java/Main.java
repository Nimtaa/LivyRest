import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;
public class Main {

    public static String sendGet(String targetURL){
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
            return response.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "no response";
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
            System.out.println(response.toString().indexOf("headers"));
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
    public static void main(String[] args) {
        String host = "http://localhost:8998";
        String data = "{'kind': 'spark'}";
        JSONObject json = new JSONObject();
        json.put("kind","spark");
        JSONObject codedata = new JSONObject();
        codedata.put("code","1+1");

        System.out.println(sendGet(host+"/sessions/0/statements"));
        //System.out.println(executePost(host+"/sessions/0/statements",codedata.toString()));
    }
}

















