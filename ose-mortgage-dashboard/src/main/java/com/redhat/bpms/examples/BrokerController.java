package com.redhat.bpms.examples;

import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

/***
 * @author jary
 * @since Nov/03/2016
 */
@Path("/broker")
@RequestScoped
public class BrokerController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(BrokerController.class);

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskSummary> listTasks() {

        List<TaskSummary> tasks = new LinkedList<>();

        try {

//            ProcessServicesClient processServicesClient = client.getServicesClient(ProcessServicesClient.class);
//            TaskInputsDefinition variablesDefinition = processServicesClient.getUserTaskInputDefinitions(
//                    "56fd159d63cd0e7d301bbac031866889", "com.redhat.bpms.examples.mortgage.MortgageApplication",
//                    "Data Correction");

            UserTaskServicesClient queryClient = initClient(Configuration.Users.KIESERVER)
                    .getServicesClient(UserTaskServicesClient.class);

            tasks = queryClient.findTasksAssignedAsPotentialOwner("bobdole", 0, 100);

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint listTasks...", e);
        }

        return tasks;
    }
}
