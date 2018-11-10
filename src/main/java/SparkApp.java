import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.Arrays;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.split;

public class SparkApp {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("StreamTestLivy")
                .getOrCreate();

        Dataset<Row> europetemp = spark.readStream().format("socket")
                .option("host","127.0.0.1")
                .option("port","9998")
                .load();

        europetemp.as(Encoders.STRING())
                .flatMap((FlatMapFunction<String,String>) x -> Arrays.asList(x.split("\n")).iterator(), Encoders.STRING());

        Dataset<Row> splitted = europetemp
                .withColumn("timestamp",split(col("value"),",")
                        .getItem(0).cast("Timestamp"))
                .withColumn("city",split(col("value"),",").getItem(1))
                .withColumn("temperature",split(col("value"),",").getItem(2));
        Dataset<Row> withoutValue = splitted.drop(col("value"));

//        Dataset<Row> groupByT = withoutValue.groupBy("timestamp").count();

        System.out.println("output print ??");
        StreamingQuery query = withoutValue.writeStream()
//                .format("csv")
//                .option("path","/home/nima/Desktop/livy")
//                .option("checkpointLocation","/home/nima/Desktop/livy")
                .format("console")
                .outputMode("update")
                .start();

        try {
            query.awaitTermination();
        } catch (StreamingQueryException e) {
            e.printStackTrace();
        }

    }
    }

