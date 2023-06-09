import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;

public class IotWeather {
    public static void main(String[] args) throws StreamingQueryException {

        System.setProperty("hadoop.home.dir","C:\\hadoop-common-2.2.0-bin-master");

        SparkSession sparkSession = SparkSession.builder().appName("SparkStreamingMessageListener").master("local").getOrCreate();

        StructType weatherType=new StructType().add("quarter","string").add("heatType","string").add("heat","integer").
                add("windType","string").add("wind","integer");

        Dataset<Row> rawData = sparkSession.readStream().schema(weatherType).option("sep",",").csv("E:\\sparkstreaming\\*");

        Dataset<Row> heatData = rawData.select("quarter","heat").where("heat>29");

        StreamingQuery start = heatData.writeStream().format("console").start();

        start.awaitTermination();



    }
}
