package org.apache.spark.hw_5

import breeze.linalg.{Vector => BV, axpy => brzAxpy}
import org.apache.spark.ml.linalg.{Vector, Vectors}

abstract class Updater extends Serializable {
  def compute(
               weightsOld: Vector,
               gradient: Vector,
               stepSize: Double,
               iter: Int): Vector
}

class BasicUpdater extends Updater {
  override def compute(weightsOld: Vector,
                       gradient: Vector,
                       stepSize: Double,
                       iter: Int): Vector = {
    val brzWeights: BV[Double] = weightsOld.asBreeze.toDenseVector
    brzAxpy(-stepSize, gradient.asBreeze, brzWeights)
    Vectors.fromBreeze(brzWeights)
  }
}