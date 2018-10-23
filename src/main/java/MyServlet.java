import org.eclipse.jetty.http.HttpStatus;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MyServlet extends HttpServlet {

    private Livy livyInstance;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        livyInstance = new Livy();
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
        JSONObject streamcode = new JSONObject();
        streamcode.put("code","import org.apache.spark.streaming._\n" +
                " import org.apache.spark.streaming.StreamingContext._\n" +
                "val ssc = new StreamingContext(sc,Seconds(5));\n" +
                "val lines = ssc.socketTextStream(\"localhost\",9998);\n" +
                "lines.print()\n" +
                "ssc.start()\n" +
                "ssc.awaitTermination()");

        //create session
//        livyInstance.executePost(host+"/sessions",json.toString());
//
//        resp.getWriter().write("waiting");
//        try {
//            TimeUnit.SECONDS.sleep(30);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        System.out.println(livyInstance.executePost(host+"/sessions/4/statements",streamcode.toString()));
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        JSONObject result =  livyInstance.sendGet(host+"/sessions/4/statements");
//        System.out.println(result.toJSONString());
//        resp.setStatus(HttpStatus.OK_200);
//        resp.getWriter().print(livyInstance.searchJSON(result));

    }
}
