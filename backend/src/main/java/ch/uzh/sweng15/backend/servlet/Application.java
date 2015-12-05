package ch.uzh.sweng15.backend.servlet;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ch.uzh.sweng15.backend.db.CSVExporter;
import ch.uzh.sweng15.backend.db.DatabaseAdapter;

/**
 * This class is necessary for the dependency injection setup of the servlet.
 * Otherwise there is not much done here.
 * @author Raffael Theiler
 *
 */
public class Application extends ResourceConfig {

/**
 * The binder defines how instances of classes are created during the lifetime of
 * the webapplication
 * @author Raffael Theiler
 *
 */
private class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
    	//api endpoints have to be defined:
        bind(MovieVisualizerAPI.class).to(MovieVisualizerAPI.class);
        
        //services have to be defined
        //we want this to be a singleton instance (otherwise it will leak resources)
        
        //this is our database.
        bind(DatabaseAdapter.class).to(DatabaseAdapter.class).in(Singleton.class);
        
        //exporter
        bind(CSVExporter.class).to(CSVExporter.class).in(Singleton.class);
        
    }
}

	/**
	 * Create a new Application
	 */
    public Application() {
    	
    	//register the custom binder
       register(new ApplicationBinder());
       
       //register all classes from the package
       packages(true, "ch.uzh.sweng15");
    }
}