package com.github.pathikrit.babelfish

import org.scalatest._

class BabelFishSpec extends FlatSpec with Matchers {

  "BabelFish" can "eval JavaScript" in {
    val eval = new Evaluator.JS
    eval[Unit]("function sum(a, b) { return a + b; }")

    val i = eval[Int]("sum(1, 2);")
    assert(i + 3 == 6)

    assert(eval.sum[Int](9, 7) == 16)

    /*trait Adder {
      def sum(a: Int, b: Int): Int
    }
    val j = eval.as[Adder]
    println(j.sum(7, 8) == 15)*/
  }
}
