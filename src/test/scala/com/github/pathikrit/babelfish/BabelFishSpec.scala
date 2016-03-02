package com.github.pathikrit.babelfish

import org.scalatest._

class BabelFishSpec extends FlatSpec with Matchers {

  "BabelFish" can "eval JavaScript" in {
    val eval = new Evaluator.JavaScript
    eval("function sum(a, b) { return a + b; }")

    val i = eval.as[Int]("sum(1, 2);")
    assert(i + 3 == 6)

    val j: Int = eval.sum(9, 7)
    assert(j == 16)

    trait Adder {
      def sum(a: Int, b: Int): Int
    }
    val adder = eval.as[Adder]
    assert(adder.sum(7, 8) == 15)

    assert(eval.sum[String]("hello", "world") == "helloworld")
    a[ClassCastException] must be thrownBy eval.sum[Int]("hello", "world")
    a[NoSuchMethodException] must be thrownBy eval.product(1, 2)
  }

  it can "eval JavaScript classes" in {
    val eval = new Evaluator.JavaScript
    val rick = eval(s"""
      new function () {
        this.name = "Rick";
        this.age = 28;
        this.sayHi = function (friend) {
          return "Hello " + friend + "! My name is " + this.name;
        }
      };
    """)
    assert(rick.sayHi[String]("Anna") == "Hello Anna! My name is Rick")
    //assert(rick.age[Int] == 28)
  }
}
