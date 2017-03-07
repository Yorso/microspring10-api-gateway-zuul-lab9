<p align="center">
  <img src="http://www.gregoriopalama.com/wp-content/uploads/2016/06/cloud.png" alt="Spring Cloud Netflix"/>
</p>

##Lab 9 - Simple API Gateway with Zuul

  **Part 1 - Startup**

1.  Stop ALL of the services that you may have running from previous exercises.  If using an IDE you may also wish to close all of the projects that are not related to "lab-9”.

2.  Start the microspring10-cloud-server-lab9 and microspring10-eureka-server-lab9.

3.  Lab 9 has copies of the word server.  Start 5 separate copies of the microspring10-word-server-zuul-lab9, using the profiles "subject", "verb", "article", "adjective", and "noun".  There are several ways to do this, depending on your preference:
  - If you wish to use Maven, open separate command prompts in the target directory and run these commands:
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=subject"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=verb"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=article"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=adjective"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=noun"

4.  Check Eureka at [http://localhost:8010](http://localhost:8010).   Any warnings about running a single instance are expected.  Ensure that each of your 5 applications are eventually listed in the "Application" section, bearing in mind it may take a few moments for the registration process to be 100% complete.	

5.  Optional - If you wish, you can click on the link to the right of any of these servers.  Replace the "/info" with "/" and refresh several times.  You can observe the randomly generated words.  



  **Part 2 - Start and Examine Existing System**

6.  Open microspring10-gateway-lab9.  This is a simple Spring Boot web application.  We will modify it to be a simple API gateway with Zuul.

7.  Examine the templates/sentence.html page.  Notice that it contains JavaScript for making AJAX calls to obtain the different parts of the sentence.  There are 5 separate calls to make, each on a different server.  How can the JavaScript make these calls without encountering cross site scripting restrictions?

8.  Run lab-9-gateway.  Access [http://localhost:8080](http://localhost:8080).  You should encounter errors as the various AJAX calls cannot be completed successfully.  We will fix this next.



  **Part 3 - Implement a Zuul Reverse Proxy**

9.  Stop the microspring10-gateway-zuul-lab9 application.

10.  Convert this application to use Spring Cloud:  Open the pom.xml and change the parent to org.springframework.cloud / spring-cloud-starter-parent / latest stable version (Camden.SR5 at the time of this writing).  If you prefer to use a dependency management section instead of altering the parent, you may do so.

11.  Add the dependency for the config client.  org.springframework.cloud / spring-cloud-config-client.  

12.  Add the dependency for Eureka-based service discovery.  org.springframework.cloud / spring-cloud-starter-eureka.

13.  Add the dependency for Zuul.  org.springframework.cloud / spring-cloud-starter-zuul.

14.  If using Eclipse, the M2E plugin may require you to update your project at this point.  Right click on the project / Maven / Update Project

15.  Setup the application to obtain configuration from the config server on startup.  Do you remember how to do this?  Open bootstrap.yml and add the location of the configuration server.  For a reminder how to do this, consult the configuration of the word server.

16.  Open the main application class and add the annotation to enable Zuul proxy.

17.  Save your work.  Run the application.  Access [http://localhost:8080](http://localhost:8080).  The sentence should build correctly with no errors.  



  **Part 4 - Add a service prefix**  Our web page expects JavaScript and CSS resources to be located under "/js" and "/css" respectively.  Let's adjust our system so that all calls to the back-end microservices are under "/services".
  
18.  Open the templates/sentences.html page.  Find the TODO comment around line 30.  Change the prefix variable to "/services".  Notice how the variable is used in the next few lines.

19.  Refresh the page in the browser.  We should get errors at this point.  Do you understand why?

20.  Open application.yml.   Set the zuul prefix to "/services".  Save all work and restart.


  **Part 5 - Add ETag Support**  At present our server is sending back individual word values for the AJAX requests even if the browser already has the value being sent.  ETags can be used to eliminate the need to send a payload to the client when nothing has changed.
  
21.  Within your browser, open Developer Tools (Internet Explorer / Chrome), Firebug (Firefox), Web Inspector (Safari), refresh the web page, and examine the network activity.  The browser is receiving a 304 code instead of 200 for the JavaScript and CSS files since they are unchanged.  Let's add similar support for the word AJAX calls if they are unchanged.

22.  Open your main Application class and add this Bean:

    ```
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }   
    ```    


23.  Save your work and restart.  Refresh the browser several times.  Notice that we randomly receive 304s for the AJAX requests instead of 200s.  Do you understand why this is random?   

**Reflection**

1.  How does the application know where the individual word services are?  Zuul automatically uses Eureka service discovery.

2.  How did this application know how to contact Eureka?  We used Spring Cloud Config, and the server / repository we are using knows the location.

3.  Why do we get 304s randomly on the AJAX requests?  Since the word values are randomly generated, 304s only occur in the unlikely event that the server returns an identical value to what it returned in the previous request.  

4.  ETags are a great way to optimize web / REST applications, but the ETag usage demonstrated here is impractical for two reasons.  1) the values we are receiving are intended to be random, 304s only occur because our set of seed values is relatively small, and 2) the ETag itself is far larger than any of our words, so we actually consume more bandwidth than we save!

5.  This web site uses Thymeleaf, JQuery, and Bootstrap, though the usage of each is very rudimentary.  The application.properties file has a setting that allows the Thymeleaf template changes to be loaded immediately, which is useful in development.

##Info

- [x] **[Microservices with Spring Cloud (Udemy)](https://www.udemy.com/microservices-with-spring-cloud/learn/v4/overview)**

- [x] **Instructor: [Ken Krueger, Technical Instructor in Software Development topics](https://linkedin.com/in/ken-krueger-43670111)**
