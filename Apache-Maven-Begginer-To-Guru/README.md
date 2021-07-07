# Course link
[Apacha Maven: Begginner To Guru](https://www.udemy.com/course/apache-maven-beginner-to-guru)


# Why use Apache Maven?

* 70% of the build tool market for Java Application
* 20% Gradle
* 10% Ant
* Quick Project Setup
* Projects are modular
* Mature Dependency Management
* Mature build lifecycle
* Robust plugin community

# Apache Maven - De Facto Standard

* Apacha maven has stabilished 'standards' used by other build tools
  * **Maven Standard Directory Layout** - In most part adapted by other tools such as Gradle
  * **Artifact Naming** - Apache Maven helped estabilish how Java artifacts are named.
  * **Artifcat Repository** - Apache Maven estabilished the structure of artifact repositories
* Prior to Maven these 'standards' did not exist
* New build tools are compatible with these 'standards'

# Maven Disvantages

* Projects are described in and XML document, constrained by an XML Schema.
  * Some consider XML 'dated'
* Gradle uses a Groovy DSL, which can offer greater build flexibility
  * Rare the additional flexibility is needed


# Installing Maven on POSIX compatible Operating System (Linux and Mac OS X)

* To install Maven on a POSIX compatible Operating System, such as Linux or Mac OS X
* Ensure JAVA_HOME is set.
  * echo $JAVA_HOME
* Download the binary archive from a download mirror.

* Switch the Maven contents.
  * mv Downloads/apache-maven* /opt/apache-maven

* Add Maven binaries to the PATH and append.
  * export PATH=/opt/apache-maven-3.6.0/bin:$PATH

* Open a new terminal window and type this command.
  * mvn -v
  * This command displays the version information and verifies that Maven is installed on your computer.

* Find maven command
  * which mvn

# Java Compile Process
![](imgs/01.png)

# Packaging Java Applications
![](imgs/02.png)

# Java Packaging
* *.jar - Java ARchive - Zip file containing one or more Java class files.
  * Simple JAR files are typically collection of class files used to compose applications
    * Typically not a complete application
* *.war - Web Application aRchive - Zip file containing web application. Includes one or more jar files, Java class files, and web resources.
* *.ear - Enterprise aRchive - Zip file containing one more WAR files.
  * WAR, EAR files are typically complete applications which are deployed to application servers
    * Tomcat, Weboss, Websphere, etc
* Fat JAR (aka Uber JAR) - Executable Jar containing all dependencies. (Used by Spring Boot)
  * Fat / Uber Jars are typically complete applications which contain embedded application servers
    * Can be deployed stand alone
* Docker Container - Docker image containing runtime environment, JVM, and Java Package.
  * Docker Images are complete applications which can be deployed stand alone


# Creating Java jar files from Command Line
* jar cf myjar.jar HelloWorld.class
  * Instead of HelloWorld.class its possible use *.class
  * cf = create file
* java -classpath myjar.jar HelloWorld
  * adding myjar.jar in classpath and run HelloWorld class

# Using 3rd Party Jars with command line
* javac -classpath ./lib/* HelloWorld.java
  * Compile adding the libraries in classpath
* java -classpath ./lib/*:./ HelloWorld
  * pick up everything at lib and root folder
