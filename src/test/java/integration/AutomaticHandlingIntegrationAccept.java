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
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.camunda.bpm.engine.variable.Variables;

public class AutomaticHandlingIntegrationAccept {

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

    @Test
    @Deployment(resources = { "my_process.bpmn",
            "process-type-decision.dmn", "at/fhv/AutomaticHandling.java"
         })
    public void automaticHandlingAccept() {
        ProcessInstance processInstance = runtimeService().createProcessInstanceByKey("Process_0umpg4c")
                .startBeforeActivity("Activity_1reqwv6")
                .setVariable("applicantName", "User")
                .setVariable("applicantAge", 30)
                .setVariable("creditAmount", 70000)
                .setVariable("equityCapital", 50000)
                //TODO remove this and let it handle from automatic test class
                .setVariable("approved", true)
                .execute();


        assertThat(task(processInstance)).hasName("Choose process type");
        complete(task(processInstance));
        
        assertThat(task(processInstance)).hasName("Automatic handling");
        complete(task(processInstance));

        assertThat(task(processInstance)).hasName("Prepare and sign paperwork");
        complete(task(processInstance));

        assertThat(task(processInstance)).hasName("Contact customer");
        complete(task(processInstance));

        assertThat(task(processInstance)).hasName("Get customer signature");
        complete(task(processInstance));

        assertThat(task(processInstance)).hasName("Store paperwork");
        complete(task(processInstance));

        runtimeService().suspendProcessInstanceById(processInstance.getId());
    }
}
