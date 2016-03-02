# BabelFish [![License][licenseImg]][licenseLink] [![CircleCI][circleCiImg]][circleCiLink] [![Codacy][codacyImg]][codacyLink] [![Gitter][gitterImg]][gitterLink]

`BabelFish` is a [dependency-free](build.sbt) [Scala wrapper](src/main/scala/com/github/pathikrit/babelfish/Evaluator.scala) 
around [JSR 223](https://www.jcp.org/en/jsr/detail?id=223) that let's you invoke other languages from Scala on the JVM.

Invoking JavaScript:
```scala
val eval = new Evaluator.JavaScript
eval("function sum(a, b) { return a + b; }")

val i = eval.as[Int]("sum(1, 2);")
assert(i + 3 == 6)
```

We can use Scala's [Dynamic](http://www.scala-lang.org/files/archive/nightly/2.12.x/api/2.12.x/scala/Dynamic.html) too to invoke:
```scala
val j: Int = eval.sum(9, 7)
assert(j == 16)
```

We can even invoke `sum` through an interface:
```scala
trait Adder {
  def sum(a: Int, b: Int): Int
}
val adder: Adder = eval.as[Adder]
assert(adder.sum(7, 8) == 15)
```

Note that we can invoke `sum` for other types too e.g.:
```scala
assert(eval.sum[String]("hello", "world") == "helloworld")
eval.sum[Int]("hello", "world") // Exception!
```

Support for objects:
```scala
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
assert(rick.age[Int] == 28)
```

See the [tests](src/test/scala/com/github/pathikrit/babelfish/BabelFishSpec.scala) for more examples. [![codecov][codecovImg]][codecovLink]

[licenseImg]: https://img.shields.io/github/license/pathikrit/BabelFish.svg
[licenseImg2]: https://img.shields.io/:license-mit-blue.svg
[licenseLink]: LICENSE

[circleCiImg]: https://img.shields.io/circleci/project/pathikrit/BabelFish/master.svg
[circleCiImg2]: https://circleci.com/gh/pathikrit/BabelFish/tree/master.svg
[circleCiLink]: https://circleci.com/gh/pathikrit/BabelFish

[codecovImg]: https://img.shields.io/codecov/c/github/pathikrit/BabelFish/master.svg
[codecovImg2]: https://codecov.io/github/pathikrit/BabelFish/coverage.svg?branch=master
[codecovLink]: http://codecov.io/github/pathikrit/BabelFish?branch=master

[versionEyeImg2]: https://img.shields.io/versioneye/d/pathikrit/BabelFish.svg
[versionEyeImg]: https://www.versioneye.com/user/projects/55f5e7de3ed894001e0003b1/badge.svg
[versionEyeLink]: https://www.versioneye.com/user/projects/55f5e7de3ed894001e0003b1

[codacyImg]: https://img.shields.io/codacy/014bfb25162b469bb0538cca7b4ec18d.svg
[codacyImg2]: https://api.codacy.com/project/badge/grade/014bfb25162b469bb0538cca7b4ec18d
[codacyLink]: https://www.codacy.com/app/pathikrit/BabelFish/dashboard

[mavenImg]: https://img.shields.io/maven-central/v/com.github.pathikrit/BabelFish_2.11.svg
[mavenImg2]: https://maven-badges.herokuapp.com/maven-central/com.github.pathikrit/BabelFish_2.11/badge.svg
[mavenLink]: http://search.maven.org/#search%7Cga%7C1%7CBabelFish

[gitterImg]: https://img.shields.io/gitter/room/pathikrit/BabelFish.svg
[gitterImg2]: https://badges.gitter.im/Join%20Chat.svg
[gitterLink]: https://gitter.im/pathikrit/BabelFish
