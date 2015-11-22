package ch.uzh.sweng15.backend.servlet;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ch.uzh.sweng15.backend.db.DatabaseAdapter;

public class Application extends ResourceConfig {

private class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(MovieVisualizerAPI.class).to(MovieVisualizerAPI.class);
        bind(DatabaseAdapter.class).to(DatabaseAdapter.class).in(Singleton.class);
    }
}


    public Application() {
       register(new ApplicationBinder());
       packages(true, "ch.uzh.sweng15");
    }
}