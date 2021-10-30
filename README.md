Summary
--------
- The project has been implemented as a Maven Project using Selenium with Java.
- Page Object Model (POM) design pattern has been used in combination with Page Factory Design Pattern.
- OOP Principles have been used to optimize the code.
- log4j component has been added to generate logs wherever required.

Prerequisites
-------------
- Java 15.0.2
- Apache Maven 3.8.2
- Chrome Browser 95 (or latest)

Instructions to execute the Automation Suite
---------------------------------------------
1) Make sure the prerequisites are installed with the same major version as above. Older versions MIGHT result in compatibility issues.
2) Add Java and Maven to PATH so that you can run them from any directory.
3) On the project page in github, click on the "Code" button (green) and "Download zip"
  (Alternatively if you have git installed, you can do a git clone)
4) Unzip the download .zip file.
5) On the command line, navigate to the project main folder where pom.xml is located.
6) Execute "mvn clean install". This command will download the dependencies, build the project and execute too.
Please be patient while the dependencies are getting downloaded for the first time, and keep your fingers crossed :-)
7) Once the build and run is successful, you can check the execution status for all the Cucumber step definitions on the command line.

**Note**: If mvn clean install throws errors, please open the project in any IDE (preferably IntelliJ) and the IDE will download the dependencies. 
You can also re-build the project.
Please feel free to contact me :-)

