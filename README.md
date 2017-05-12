# About

TeachMe is a Maven Web application that can aggregate educational offerings.

# Getting started

To build this project, execute:

	$ mvn install 
  .
Deploy to your local wildfly server with (need a local wildfly instance running):

	$ mvn wildfly:deploy
  
  
# Wildfly configuration

You will need to add a new module mysql-connector-java to your wildfly server:

  $ wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.42.tar.gz
  $ tar -zxvf mysql-connector-java-5.1.42.tar.gz 
  $ mv mysql-connector-java-5.1.42/mysql-connector-java-5.1.42-bin.jar $WILDFLY_HOME/bin/ (eg: /opt/wildfly/bin/)
  
Start a new instance of wildfly server and then execute:
  
  $ ./jboss-cli.sh 
  
Add mysql-connector-java module and database source (http://stackoverflow.com/questions/39948513/wildfly-mysql-datasource-service-jboss-jdbc-driver-mysql-missing-dependents/#answer-39953187)

Example: 

  $ data-source add --name=teachme --driver-name=mysql --connection-url=jdbc:mysql://deti-tqs-12.ua.pt:3306/teachme --jndi-name=java:/jboss/jdbc/teachme --user-name=INSERT USERNAME HERE --password=PASSWORD HERE

  $ data-source enable --name=teachme

You might need to edit your persistence.xml file located in src/main/resources folder.


  
