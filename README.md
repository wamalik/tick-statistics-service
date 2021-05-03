# SOLACTIVE CODE CHALLENGE (v1.03)

This application will provide solution for Solactive Code Challenge

# How to run application:
- This application is built on maven Spring Boot plugin with Spring WebFlux Reactive Non-blocking implementation
- Application requires Java 8/11 installed, and it can run as executable jar with mvn spring-boot:run command

# Assumptions:
-  Key assumption here is that application uses Spring WebFlux framework, which is based on reactive non-blocking I/O so this will help 
   in managing higher concurrency of ticks
-  In memory storage is kept as map to focus more on algorithm implementation


# Improvement:
- performance tests to check the execution time with real load
- in memory dbstore such as redis can be used
