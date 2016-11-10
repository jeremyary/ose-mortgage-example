package com.redhat.bpms.examples;

import com.google.gson.Gson;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

/***
 * @author jary
 * @since Nov/10/2016
 */
public class BaseController {

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    protected Gson gson = new Gson();

    protected KieServicesClient initClient(Configuration.Users user) {

        KieServicesConfiguration conf = KieServicesFactory.newRestConfiguration(
                Configuration.REST_BASE_URI,
                user.username(),
                user.password());

        conf.setMarshallingFormat(FORMAT);
        return KieServicesFactory.newKieServicesClient(conf);
    }
}
