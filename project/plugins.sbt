// Comment to get more information during initialization
logLevel := Level.Warn

// Resolvers
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// Sbt plugins
//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.12")
//
//addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.22")
//
//addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.7")
//
//addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")
//
//addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.4")
//
//addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.2")

// Sbt plugins
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.8-0.6")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.25")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.15")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"  % "0.5.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.4")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.2")
