import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
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


//        SparkAppHandle spark = new SparkLauncher()
//                .setSparkHome("/usr/local/spark")
//                .setAppResource("/home/nima/IdeaProjects/LivyAppTest/target/LivyAppTest-1.0-SNAPSHOT.jar")
//                .setMainClass("StreamTest")
//                .setMaster("local[2]")
//                .startApplication();
//        resp.setStatus(HttpStatus.OK_200);


        livyInstance = new Livy();
        String host = "http://localhost:8998";
        JSONObject json = new JSONObject();
        json.put("kind","spark");

        JSONObject batchParam = new JSONObject();
        batchParam.put("file","local:/home/nima/IdeaProjects/LivyRest/target/apachelivyresttest-1.0-SNAPSHOT.jar");
        batchParam.put("className","SparkApp");

        //create Batch
        System.out.println(livyInstance.executePost(host+"/batches",batchParam.toString()));

    }
}


















//        livyInstance = new Livy();
//        String host = "http://localhost:8998";
//        JSONObject json = new JSONObject();
//        json.put("kind","spark");
//
//        JSONObject batchParam = new JSONObject();
//        batchParam.put("file","/home/nima/IdeaProjects/LivyAppTest/out/artifacts/LivyAppTest_jar/LivyAppTest.jar");
//        batchParam.put("className","StreamTest");
//
//
//        JSONObject codedata = new JSONObject();
//        codedata.put("code","val NUM_SAMPLES = 100000;\n" +
//                "val count = sc.parallelize(1 to NUM_SAMPLES).map { i =>\n" +
//                "val x = Math.random();\n" +
//                "val y = Math.random();\n" +
//                "if (x*x + y*y < 1) 1 else 0\n" +
//                "}.reduce(_ + _);\n" +
//                "println( 4.0 * count / NUM_SAMPLES)");
//        JSONObject streamcode = new JSONObject();
//        streamcode.put("code","import org.apache.spark.streaming._\n" +
//                " import org.apache.spark.streaming.StreamingContext._\n" +
//                "val ssc = new StreamingContext(sc,Seconds(5));\n" +
//                "val lines = ssc.socketTextStream(\"127.0.0.1\",9998);\n" +
//                "val words = lines.flatMap(_.split(\"\\n\"))\n" +
//                "println(words.print())\n" +
//                "ssc.start()\n" +
//                "ssc.awaitTermination()");


//        livyInstance.deleteSession(host+"/batches/0");
        //create Batch
//        System.out.println(livyInstance.executePost(host+"/batches",batchParam.toString()));
//        livyInstance.sendGet(host+"/batches");
        //create session
      //  livyInstance.executePost(host+"/sessions",json.toString());
//
//        resp.getWriter().write("waidting");
//        try {
//            TimeUnit.SECONDS.sleep(30);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        livyInstance.deleteSession(host+"/sessions/6");

//        System.out.println(livyInstance.executePost(host+"/sessions/7/statements",streamcode.toString()));
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        JSONObject result =  livyInstance.sendGet(host+"/sessions/7/statements");
//        System.out.println(result.toJSONString());
//        resp.setStatus(HttpStatus.OK_200);
//        resp.getWriter().print(livyInstance.searchJSON(result));
