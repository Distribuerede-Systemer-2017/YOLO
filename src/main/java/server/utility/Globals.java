package server.utility;
import server.utility.Log;
import server.config.Config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;


public class Globals implements ServletContextListener {

    public static Config config = new Config();
    /**
     * This function can be used to initialize Logger and Config classes.
     *
     * We do this in order for the rest of the program to use it afterwards.
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //We init config in order to read the file and set all the variables.
        //Since we're not sure the file exists, we could get a IOException
        try {
            config.initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //log.writeLog(this.getClass().getName(), this, "We've started the system", 2);

        //Here we can initialize our Logger class and
        System.out.println("Context is initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context is destroyed");
    }
}
