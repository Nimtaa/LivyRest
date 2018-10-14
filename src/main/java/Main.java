import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;


public class Main {


    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            //Send Request
            DataOutputStream dor = new DataOutputStream(
                    connection.getOutputStream()
            );
            dor.writeBytes(urlParameters);
            dor.close();

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
            return response.toString();
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
        System.out.println(executePost(host+"/session",json.toString()));

    }

}
