val username = "pathikrit"
val repo = "BabelFish"

name := repo
description := "Invoke dynamic languages from Scala"
organization := s"com.github.$username"
scalaVersion := "2.11.7"
crossScalaVersions := Seq("2.11.7")
crossVersion := CrossVersion.binary
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")
scalacOptions ++= Seq(  //copied from https://tpolecat.github.io/2014/04/11/scalac-flags.html
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:implicitConversions,dynamics",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  //"-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused-import",
  "-Xfuture"
)
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test
fork in Test := true // Hack to get around https://github.com/sbt/sbt/issues/1214

// Publish settings
homepage := Some(url(s"https://github.com/$username/$repo"))
licenses += "MIT" -> url(s"https://github.com/$username/$repo/blob/master/LICENSE")
scmInfo := Some(ScmInfo(url(s"https://github.com/$username/$repo"), s"git@github.com:$username/$repo.git"))
apiURL := Some(url(s"https://$username.github.io/$repo/latest/api/"))
releaseCrossBuild := true
releasePublishArtifactsAction := PgpKeys.publishSigned.value
publishMavenStyle := true
publishArtifact in Test := false
publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
credentials ++= (for {
  username <- sys.env.get("SONATYPE_USERNAME")
  password <- sys.env.get("SONATYPE_PASSWORD")
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq
pomExtra :=
  <developers>
    <developer>
      <id>pathikrit</id>
      <name>Pathikrit Bhowmick</name>
      <url>http://github.com/pathikrit</url>
    </developer>
  </developers>

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  //runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
