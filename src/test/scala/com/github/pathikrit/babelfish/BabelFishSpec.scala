package com.github.pathikrit.babelfish

import org.scalatest._

class BabelFishSpec extends FlatSpec with Matchers {

  "BabelFish" can "eval JavaScript" in {
    val eval = new Evaluator.JavaScript
    eval[Unit]("function sum(a, b) { return a + b; }")

    val i: Int = eval("sum(1, 2);")
    assert(i == 3)

    val j: Int = eval.sum(9, 7)
    assert(j == 16)

    trait Adder {
      def sum(a: Int, b: Int): Int
    }
    val k = eval.as[Adder]
    assert(k.sum(7, 8) == 15)

    assert(eval.sum[String]("hello", "world") == "helloworld")
    a[ClassCastException] must be thrownBy eval.sum[Int]("hello", "world")
  }
}
