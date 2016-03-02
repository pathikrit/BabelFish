# BabelFish [![License][licenseImg]][licenseLink] [![CircleCI][circleCiImg]][circleCiLink] [![Codacy][codacyImg]][codacyLink] [![Gitter][gitterImg]][gitterLink]


`BabelFish` is a [dependency-free](build.sbt) [Scala wrapper](src/main/scala/com/github/pathikrit/babelFish/Evaluator.scala) 
around [JSR 223](https://www.jcp.org/en/jsr/detail?id=223) that let's you invoke other languages from Scala on the JVM.

Example invoking JavaScript:
```scala
val eval = new Evaluator.JS
eval[Unit]("function sum(a, b) { return a + b; }")

val i = eval[Int]("sum(1, 2);")
assert(i + 3 == 6)
```

We can use Scala's [Dynamic] too to invoke:
```scala
assert(eval.sum[Int](9, 7) == 16)
```

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

[codacyImg]: https://img.shields.io/codacy/0e2aeb7949bc49e6802afcc43a7a1aa1.svg
[codacyImg2]: https://api.codacy.com/project/badge/grade/0e2aeb7949bc49e6802afcc43a7a1aa1
[codacyLink]: https://www.codacy.com/app/pathikrit/BabelFish/dashboard

[mavenImg]: https://img.shields.io/maven-central/v/com.github.pathikrit/BabelFish_2.11.svg
[mavenImg2]: https://maven-badges.herokuapp.com/maven-central/com.github.pathikrit/BabelFish_2.11/badge.svg
[mavenLink]: http://search.maven.org/#search%7Cga%7C1%7CBabelFish

[gitterImg]: https://img.shields.io/gitter/room/pathikrit/BabelFish.svg
[gitterImg2]: https://badges.gitter.im/Join%20Chat.svg
[gitterLink]: https://gitter.im/pathikrit/BabelFish

[scaladocImg]: http://img.shields.io/:docs-ScalaDoc-blue.svg
[scaladocLink]: http://pathikrit.github.io/BabelFish/latest/api#better.files.File
