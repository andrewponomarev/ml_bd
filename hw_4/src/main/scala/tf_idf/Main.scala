package tf_idf

import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.math._
import scala.math.{log =>mathLog}

object Main extends App {

  val data_path = "data/tripadvisor_hotel_reviews.csv";
  val result_path = "data/result"
  val word_max_count: Int = 100

  val cleanString = udf { (s: String) => {
    s.replaceAll("[^0-9a-zA-Z\\s$]", "")
  }}

  val spark = SparkSession.builder()
    .master("local[*]")
    .appName("tf_idf")
    .getOrCreate()

  import spark.implicits._

  val data_frame = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .option("sep", ",")
    .csv(data_path)

  val data_frame_review = data_frame.select(col("Review"))

  val data_frame_cleared = data_frame_review
    .withColumn("Review", cleanString(lower(col("Review"))))
    .withColumn("id", monotonically_increasing_id())

  val words = data_frame_cleared
    .select(col("id"), explode(split(col("Review"), " ")).alias("word"))
    .where(length($"word") > 1)

  val tf = words
    .groupBy("id", "word")
    .agg(count("word").alias("tf"))

  val df = words
    .groupBy("word")
    .agg(countDistinct("id").alias("df"))
    .orderBy(desc("df"))
    .limit(word_max_count)

  val docs_num = data_frame_cleared.count()
  val inv_df = udf {(df: Long) =>
    math.log((docs_num.toDouble + 1)/(df.toDouble + 1))
  }

  val idf = df
    .withColumn("idf", inv_df(col("df")))

  val tfidf = tf.join(idf, "word")
    .withColumn("tfidf", col("tf") * col("idf"))
    .select(col("word"), col("id"), col("tfidf"))

  val pivot = tfidf.groupBy("id")
    .pivot(col("word"))
    .agg(first(col("tfidf"), ignoreNulls = true))
    .na.fill(0.0)

  pivot.show()

  pivot
    .coalesce(1)
    .write
    .option("header", "true")
    .option("sep", ",")
    .csv(result_path)
}
