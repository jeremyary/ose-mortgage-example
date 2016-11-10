package com.redhat.bpms.examples;

import com.redhat.bpms.examples.mortgage.Applicant;
import com.redhat.bpms.examples.mortgage.Application;
import com.redhat.bpms.examples.mortgage.Appraisal;
import com.redhat.bpms.examples.mortgage.Property;
import org.kie.server.client.ProcessServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/***
 * @author jary
 * @since Nov/03/2016
 */
@Path("/applicant")
@RequestScoped
public class ApplicantController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(ApplicantController.class);

    @GET
    @Path("/startApp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response.Status startApp() {

        try {

//            Map<String, Object> payload = new HashMap<>();
//            Map<String, Object> wrapper = new HashMap<>();
//            Map<String, Object> appContent = new HashMap<>();
//            Map<String, Object> applicant = new HashMap<>();
//            Map<String, Object> property = new HashMap<>();
//            Map<String, Object> appraisal = new HashMap<>();
//
//            applicant.put("id", 30L);
//            applicant.put("name", "Bob Smith");
//            applicant.put("ssn", "234552222");
//            applicant.put("income", 150000);
//            applicant.put("creditScore", 780);
//
//            property.put("id", 11L);
//            property.put("address", "123 Where Ave");
//            property.put("price", 400000);
//
//            appraisal.put("id", 64L);
//            appraisal.put("property", property);
//            appraisal.put("date", "2016-10-21T14:00:00");
//            appraisal.put("value", 410000);
//
//            appContent.put("id", 21L);
//            appContent.put("applicant", applicant);
//            appContent.put("property", property);
//            appContent.put("appraisal", appraisal);
//            appContent.put("downPayment", 25000);
//            appContent.put("amortization", 5);
//            appContent.put("mortgageAmount", 250000);
//            appContent.put("apr", 4.5);
////            appContent.put("validationErrors", );
//
//            wrapper.put("com.redhat.bpms.examples.mortgage.Application", appContent);
//            payload.put("application", wrapper);

//            ProcessServicesClient processServicesClient = initClient(Configuration.Users.KIESERVER)
//                    .getServicesClient(ProcessServicesClient.class);

//            processServicesClient.startProcess("56fd159d63cd0e7d301bbac031866889", "com.redhat.bpms.examples.mortgage" +
//                    ".MortgageApplication", payload);

            Applicant applicant = new Applicant();
            applicant.setName("John Smith");
            applicant.setSsn(455651234);
            applicant.setIncome(150000);
            applicant.setCreditScore(780);

            Property property = new Property();
            property.setAddress("123 Factory Ave");
            property.setPrice(300000);

            Appraisal appraisal = new Appraisal();
            appraisal.setProperty(property);
            appraisal.setValue(320000);

            Application application = new Application();
            application.setApplicant(applicant);
            application.setProperty(property);
            application.setAppraisal(appraisal);
            application.setDownPayment(50000);
            application.setAmortization(5);
            application.setMortgageAmount(250000);
            application.setApr(4.5);

            Map<String, Object> params = new HashMap<>();
            params.put("application", gson.toJson(application));

            ProcessServicesClient processServicesClient = initClient(Configuration.Users.KIESERVER)
                    .getServicesClient(ProcessServicesClient.class);

            processServicesClient.startProcess("56fd159d63cd0e7d301bbac031866889", "com.redhat.bpms.examples.mortgage" +
                    ".MortgageApplication", params);

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint startApp...", e);
        }

        return Response.Status.OK;
    }
}
