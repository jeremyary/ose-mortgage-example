package com.redhat.bpms.examples;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.kie.server.client.*;

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
public class HomeController {

    private static final String URL = "http://mort-dashboard.bxms.ose/kie-server/services/rest/server";
    private static final String USER = "kieserver";
    private static final String PASSWORD = "kieserver1!";
    // TODO: EXTERNALIZE

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    @GET
    @Path("/containers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KieContainerResource> listContainers() {

        List<KieContainerResource> containers = new LinkedList<>();
        try {
            KieServicesClient client = initClient();
            containers = client.listContainers().getResult().getContainers();

        } catch (Exception e) {
            throw e;
        }
        return containers;
    }

    @GET
    @Path("/processes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProcessDefinition> listProcesses() {

        List<KieContainerResource> containers = new LinkedList<>();
        List<ProcessDefinition> processDefinitions = new LinkedList<>();

        try {
            KieServicesClient client = initClient();
            containers = client.listContainers().getResult().getContainers();

            QueryServicesClient queryClient = client.getServicesClient(QueryServicesClient.class);

            for (KieContainerResource container : containers) {
                processDefinitions.addAll(queryClient.findProcessesByContainerId(container.getContainerId(), 0, 100));
            }

        } catch (Exception e) {
            throw e;
        }

        return processDefinitions;
    }

    @GET
    @Path("/running")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProcessInstance> listRunningProcesses() {

        List<KieContainerResource> containers = new LinkedList<>();
        List<ProcessInstance> processInstances = new LinkedList<>();

        try {
            KieServicesClient client = initClient();
            containers = client.listContainers().getResult().getContainers();

            QueryServicesClient queryClient = client.getServicesClient(QueryServicesClient.class);

            for (KieContainerResource container : containers) {
                processInstances.addAll(queryClient.findProcessInstancesByContainerId(container.getContainerId(),
                        new LinkedList<>(), 0, 100));
            }

        } catch (Exception e) {
            throw e;
        }

        return processInstances;
    }

    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskSummary> listTasks() {

        List<KieContainerResource> containers = new LinkedList<>();
        List<ProcessInstance> processInstances = new LinkedList<>();
        List<TaskSummary> tasks = new LinkedList<>();

        try {
            KieServicesClient client = initClient();
            containers = client.listContainers().getResult().getContainers();

            QueryServicesClient queryClient = client.getServicesClient(QueryServicesClient.class);

            for (KieContainerResource container : containers) {
                processInstances.addAll(queryClient.findProcessInstancesByContainerId(container.getContainerId(),
                        new LinkedList<>(), 0, 100));
            }

            UserTaskServicesClient userTaskClient = client.getServicesClient(UserTaskServicesClient.class);

            for (ProcessInstance process : processInstances) {

                tasks.addAll((userTaskClient.findTasksByStatusByProcessInstanceId(process.getId(), new LinkedList<>()
                        , 0, 100)));
            }

        } catch (Exception e) {
            throw e;
        }

        return tasks;
    }

    @GET
    @Path("/workitems")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkItemInstance> listWorkItems() {

        List<KieContainerResource> containers = new LinkedList<>();
        List<ProcessInstance> processInstances = new LinkedList<>();
        List<WorkItemInstance> tasks = new LinkedList<>();

        try {
            KieServicesClient client = initClient();
            containers = client.listContainers().getResult().getContainers();

            QueryServicesClient queryClient = client.getServicesClient(QueryServicesClient.class);

            for (KieContainerResource container : containers) {
                processInstances.addAll(queryClient.findProcessInstancesByContainerId(container.getContainerId(),
                        new LinkedList<>(), 0, 100));
            }

            ProcessServicesClient processServicesClient = client.getServicesClient(ProcessServicesClient.class);

            for (ProcessInstance process : processInstances) {

                tasks.addAll(processServicesClient.getWorkItemByProcessInstance(process.getContainerId(), process.getId
                        ()));
            }

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
