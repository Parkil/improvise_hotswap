# improvise_hotswap (Alpha version)

## overview
Java runtime class redifinition

## start guide
1.make jar

./gradlew jar or gradlew.bat jar or using Intellj Execute Gradle Task

2.set Java VM Options

-javaagent:<make jar path> -Dhotswap.watch.root=<java file root path>

3.run target java app
  
[run successful log]
[main] INFO agent.AgentMain - hotswap starting watch root : <java file root path>
  
[java file root path not exists log]
exception.exception.ConfigException: path[<java file root path>] is invalid file path. hotswap is not running
  
## caution
If you are using spring-boot-devtools in target java app then hotswap won't work



