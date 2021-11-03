package linreg

import java.io.File

import breeze.linalg.{*, DenseMatrix, DenseVector, csvread, csvwrite, norm}
import breeze.stats.mean



object Main extends App {

  val trainPath = args(0)
  val testPath = args(1)
  val predictPath = args(2)

  println("reading train data")
  val train = csvread(new File(trainPath), ';', skipLines = 1)

  println("reading test data")
  var test = csvread(new File(testPath), ';', skipLines = 1)

  val model = new LinearRegression()

  model.fit(train(::, 1 to -1), train(::, 0))
  val predictions = model.predict(test(::, 1 to -1))

  println("write linear regression results")
  val f = getOrCreateFile(predictPath)
  csvwrite(f, predictions.asDenseMatrix.t)

  def getOrCreateFile(path: String): File = {
    val f = new File(path)
    if (!f.getParentFile.exists) f.getParentFile.mkdirs
    if (!f.exists) f.createNewFile
    f
  }
}

class LinearRegression() {

  private var w: DenseVector[Double] = DenseVector()
  var isTrained: Boolean = false

  private def normalize(x: DenseMatrix[Double]): DenseMatrix[Double] = {
    x(::, *).map(Col => Col / norm(Col))
  }

  private def mse(x: DenseVector[Double], y: DenseVector[Double]): Double = {
    val diff = x - y
    mean(diff * diff)
  }

  def predict(x: DenseMatrix[Double]): DenseVector[Double] = {
    normalize(x) * this.w
  }

  def fit(x: DenseMatrix[Double], y: DenseVector[Double]) = {
    if (this.isTrained) {
      println("The model has been already trained")
    }

    val xx: DenseMatrix[Double] = normalize(x)
    this.w = DenseVector.zeros[Double](xx.cols)
    var weights = DenseVector.zeros[Double](xx.cols)
    var curEpoch = 1;

    while ((curEpoch <= 10000) && (curEpoch == 1 || norm(this.w  - weights) > 0.001)) {
      for (i <- Range(0, xx.rows).grouped(128)) {
        weights = this.w
        val bx = xx(i, ::)
        val gradW = - 2.0 * bx.t * (y(i) - bx * weights) / i.length.doubleValue
        this.w = weights - 0.01 * gradW
        curEpoch += 1
      }
      if (curEpoch % 100 == 0) {
        println("Epoch :" + curEpoch + " MSE: " + mse(predict(xx), y))
      }
    }
    this.isTrained = true
  }

}
