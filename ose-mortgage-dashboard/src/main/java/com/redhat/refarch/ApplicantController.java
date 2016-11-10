package com.redhat.refarch;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.definition.TaskInputsDefinition;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/***
 * @author jary
 * @since Nov/03/2016
 */
@Path("/applicant")
@RequestScoped
public class ApplicantController {

    private static final String URL = "http://mort-dashboard.bxms.ose/kie-server/services/rest/server";
    private static final String USER = "kieserver";
    private static final String PASSWORD = "kieserver1!";
    // TODO: EXTERNALIZE

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    @GET
    @Path("/startApp")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskSummary> startApp() {

        List<TaskSummary> tasks = new LinkedList<>();

        try {
            KieServicesClient client = initClient();

            ProcessServicesClient processServicesClient = client.getServicesClient(ProcessServicesClient.class);

            Map<String,Object> payload = new HashMap<>();
            Map<String,Object> wrapper = new HashMap<>();
            Map<String,Object> appContent = new HashMap<>();
            Map<String,Object> applicant = new HashMap<>();
            Map<String,Object> property = new HashMap<>();
            Map<String,Object> appraisal = new HashMap<>();

            applicant.put("id", 30L);
            applicant.put("name", "Bob Smith");
            applicant.put("ssn", "234552222");
            applicant.put("income", 150000);
            applicant.put("creditScore", 780);

            property.put("id",11L);
            property.put("address", "123 Where Ave");
            property.put("price", 400000);

            appraisal.put("id", 64L);
            appraisal.put("property", property);
            appraisal.put("date", "2016-10-21T14:00:00");
            appraisal.put("value", 410000);

            appContent.put("id", 21L);
            appContent.put("applicant", applicant);
            appContent.put("property", property);
            appContent.put("appraisal", appraisal);
            appContent.put("downPayment", 25000);
            appContent.put("amortization", 5);
            appContent.put("mortgageAmount", 250000);
            appContent.put("apr", 4.5);
//            appContent.put("validationErrors", );

            wrapper.put("com.redhat.bpms.examples.mortgage.Application", appContent);
            payload.put("application", wrapper);

            processServicesClient.startProcess("56fd159d63cd0e7d301bbac031866889", "com.redhat.bpms.examples.mortgage" +
                    ".MortgageApplication", payload);

        } catch (Exception e) {
            throw e;
        }

        return tasks;
    }

    private KieServicesClient initClient() {

        KieServicesConfiguration conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        conf.setMarshallingFormat(FORMAT);
        return KieServicesFactory.newKieServicesClient(conf);
    }
}
