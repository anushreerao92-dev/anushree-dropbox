package org.dropwizard.anushree;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.dropwizard.anushree.Configuration.MyConfig;
import org.dropwizard.anushree.Resources.DropBoxResource;
import org.dropwizard.anushree.Service.DropBoxService;

public class DropBoxApplication extends Application<MyConfig> {
    public static void main(String[] args) throws Exception {
        new DropBoxApplication().run(args);
    }
    @Override
    public void run(MyConfig myConfig, Environment environment) throws Exception {

        DropBoxService dropboxService = new DropBoxService(myConfig.getDropboxConfig());
        final DropBoxResource dropboxResource = new DropBoxResource(dropboxService);
        environment.jersey().register(dropboxResource);

        System.out.println(">>> Dropbox Dropwizard App Started");
    }
}
