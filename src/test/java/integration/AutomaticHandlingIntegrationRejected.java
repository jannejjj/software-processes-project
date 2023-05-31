package integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.processInstanceQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;



public class AutomaticHandlingIntegrationRejected {

    public ProcessEngine myProcessEngine = ProcessEngineConfiguration
            .createStandaloneInMemProcessEngineConfiguration()
            .setJdbcUrl("jdbc:h2:mem:camunda;DB_CLOSE_DELAY=1000")
            .buildProcessEngine();

    @RegisterExtension
    ProcessEngineExtension extension = ProcessEngineExtension
            .builder()
            .useProcessEngine(myProcessEngine)
            .build();

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule(myProcessEngine);


    @Deployment(resources = { "my_process.bpmn"
         })
    @Test
    public void automaticHandlingReject() {
        ProcessInstance processInstance = runtimeService().createProcessInstanceByKey("Process_0umpg4c")
                .startBeforeActivity("StartEvent_1")
                .setVariable("applicantName", "User")
                .setVariable("applicantAge", 30)
                .setVariable("creditAmount", 70000)
                .setVariable("equityCapital", 50)
                .execute();


        assertThat(task(processInstance)).hasName("Review");
        complete(task(processInstance));

        assertThat(task(processInstance)).hasName("Contact customer");
        complete(task(processInstance));

        runtimeService().suspendProcessInstanceById(processInstance.getId());
    }
}
