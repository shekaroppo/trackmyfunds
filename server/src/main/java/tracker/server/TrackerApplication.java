package tracker.server;

import com.gs.reladomo.serial.jackson.JacksonReladomoModule;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.assets.WebAssetsBundle;
import io.dropwizard.lifecycle.setup.ScheduledExecutorServiceBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tracker.mithra.Mithra;

import java.util.concurrent.ScheduledExecutorService;

public class TrackerApplication extends Application<TrackerConfiguration> {

    public static void main(String[] args) throws Exception {
        new TrackerApplication().run(args);
    }

    @Override
    public String getName() {
        return "track-my-funds";
    }

    @Override
    public void initialize(Bootstrap<TrackerConfiguration> bootstrap) {
        bootstrap.addBundle(new WebAssetsBundle("/web", "/web", "index.html"));
    }

    @Override
    public void run(TrackerConfiguration configuration, Environment environment) {
        Mithra.init();
        environment.getObjectMapper().registerModule(new JacksonReladomoModule(true));
        environment.jersey().register(new TrackerService());
        environment.jersey().register(new PortfolioValueService());
    }
}