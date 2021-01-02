name := """mola"""
organization := "be.tsepton.mola"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.padlet.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.padlet.binders._"
