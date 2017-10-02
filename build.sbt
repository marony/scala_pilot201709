import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.11",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "pilot_201709",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      // Finch + Finagle + netty
      "io.netty" % "netty-all" % "4.1.+",
      "com.twitter" %% "finagle-core" % "6.45.+",
      "com.twitter" %% "finagle-http" % "6.45.+",
      "com.github.finagle" %% "finch-core" % "0.15.+",
      "com.github.finagle" %% "finch-circe" % "0.15.+",
      "io.circe" %% "circe-generic" % "0.8.+",
      "io.circe" %% "circe-generic" % "0.8.+",
      "io.circe" %% "circe-parser" % "0.8.+",
      "io.circe" %% "circe-generic-extras" % "0.8.+",

      // scalaz
      "org.scalaz" %% "scalaz-core" % "7.2.+",

      // ScalikeJDBC
      "org.scalikejdbc" %% "scalikejdbc" % "3.0.+",

      "org.scalikejdbc" %% "scalikejdbc-interpolation-macro" % "3.0.+",
      "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "3.0.+",
      "org.scalikejdbc" %% "scalikejdbc-config" % "3.0.+",

      "org.postgresql" % "postgresql" % "42.1.+",
      // Redis
      "net.debasishg" %% "redisclient" % "3.+",
      // ZooKeeper
      "org.apache.zookeeper" % "zookeeper" % "3.5.+",
      // kafka
      "org.apache.kafka" %% "kafka" % "0.11.+",
      // Log
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.+" % "compile",
      "org.slf4j" % "slf4j-api" % "1.7.+" % "compile",
      "ch.qos.logback" % "logback-classic"  % "1.2.+"
    ),
    libraryDependencies ~= {
      // logbackを使うのでlog4jを依存関係から削除
      _.map(_.exclude("org.slf4j", "slf4j-log4j12"))
    },
    flywayUrl := "jdbc:postgresql://localhost/pilot",
    flywayUser := "pilot",
    flywayPassword := "pilot"
  )
