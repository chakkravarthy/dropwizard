# Message

How to start the Message application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/craftdemo-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

This is a RESTFul Webservice using DropWizard for creating messages and broadcasting messages to people who follow the message creator. 
Users will see a preview of top 100 messages from their followers.

