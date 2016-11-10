package com.redhat.bpms.examples;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
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
@Path("/home")
@RequestScoped
public class HomeController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GET
    @Path("/containers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KieContainerResource> listContainers() {

        List<KieContainerResource> containers = new LinkedList<>();
        try {
            containers = initClient(Configuration.Users.KIESERVER)
                    .listContainers().getResult().getContainers();

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint listContainers...", e);
        }
        return containers;
    }

    @GET
    @Path("/processes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProcessDefinition> listProcesses() {

        List<ProcessDefinition> processDefinitions = new LinkedList<>();

        try {
            QueryServicesClient queryClient = initClient(Configuration.Users.KIESERVER)
                    .getServicesClient(QueryServicesClient.class);

            for (KieContainerResource container : listContainers()) {
                processDefinitions.addAll(queryClient.findProcessesByContainerId(container.getContainerId(), 0, 100));
            }

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint listProcesses...", e);
        }

        return processDefinitions;
    }

    @GET
    @Path("/running")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProcessInstance> listProcessInstances() {

        List<ProcessInstance> processInstances = new LinkedList<>();

        try {
            QueryServicesClient queryClient = initClient(Configuration.Users.KIESERVER)
                    .getServicesClient(QueryServicesClient.class);

            for (KieContainerResource container : listContainers()) {
                processInstances.addAll(queryClient.findProcessInstancesByContainerId(container.getContainerId(),
                        new LinkedList<>(), 0, 100));
            }

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint listProcessInstances...", e);
        }

        return processInstances;
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskSummary> listTasks() {

        List<TaskSummary> tasks = new LinkedList<>();

        try {
            UserTaskServicesClient userTaskClient = initClient(Configuration.Users.KIESERVER)
                    .getServicesClient(UserTaskServicesClient.class);

            for (ProcessInstance process : listProcessInstances()) {

                tasks.addAll((userTaskClient.findTasksByStatusByProcessInstanceId(process.getId(), new LinkedList<>()
                        , 0, 100)));
            }

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint listTasks...", e);
        }

        return tasks;
    }

    @GET
    @Path("/workitems")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkItemInstance> listWorkItems() {

        List<WorkItemInstance> tasks = new LinkedList<>();

        try {
            ProcessServicesClient processServicesClient = initClient(Configuration.Users.KIESERVER)
                    .getServicesClient(ProcessServicesClient.class);

            for (ProcessInstance process : listProcessInstances()) {

                tasks.addAll(processServicesClient.getWorkItemByProcessInstance(process.getContainerId(), process.getId
                        ()));
            }

        } catch (Exception e) {
            logger.error("ERROR in Rest endpoint listWorkItems...", e);
        }

        return tasks;
    }
}
