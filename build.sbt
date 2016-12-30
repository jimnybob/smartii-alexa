enablePlugins(AwsLambdaPlugin)

lazy val root = (project in file(".")).
  settings(
    name := "smartii-alexa",
    version := "0.0.1",
    scalaVersion := "2.11.8"
  ).
  settings(
    libraryDependencies ++= dependencies
  )

lazy val dependencies = Seq(
    "com.typesafe.play"            %% "play-ws" % "2.5.10",
    "com.typesafe.akka"            %% "akka-actor" % "2.4.12",
    "com.typesafe.akka"            %% "akka-stream" % "2.4.12",
    "com.amazon.alexa"             % "alexa-skills-kit" % "1.1.3",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.5",
    "org.apache.logging.log4j"     % "log4j-core" % "2.6.2",
    "org.slf4j"                    % "slf4j-api"      % "1.7.21",
    "org.apache.commons"           % "commons-lang3" % "3.4",
    "commons-io"                   % "commons-io" % "2.5",
    "com.amazonaws"                % "aws-lambda-java-core" % "1.1.0",
    "com.amazonaws"                % "aws-java-sdk-dynamodb" % "1.11.31"
)

handlerName := Some("uk.co.smartii.alexa.SmartiiAlexaSpeechletRequestStreamHandler")
region := Some("eu-west-1")

// Exclude commons-logging because it conflicts with the jcl-over-slf4j
libraryDependencies ~= { _ map {
    case m if m.organization.startsWith("com") =>
        m.exclude("commons-logging", "commons-logging")
    case m => m
}}

// Take the first ServerWithStop because it's packaged into two jars
assemblyMergeStrategy in assembly := {
    case PathList("play", "core", "server", "ServerWithStop.class") => MergeStrategy.first
    case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
    case other => (assemblyMergeStrategy in assembly).value(other)
}