package org.apache.spark.hw_5

import breeze.linalg.sum
import org.apache.spark.ml.linalg.{Vector, Vectors}


class Gradient extends Serializable {

  def compute(data: Vector, label: Double, weights: Vector): (Vector, Double) = {
    val _delta = sum(data.asBreeze *:* weights.asBreeze) - label
    val _loss = _delta * _delta / 2.0
    val _grad = data.copy.asBreeze * _delta
    (Vectors.fromBreeze(_grad), _loss)
  }
}
