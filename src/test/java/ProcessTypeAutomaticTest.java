import static org.junit.jupiter.api.Assertions.assertEquals;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.test.DmnEngineRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.camunda.bpm.engine.variable.Variables;

public class ProcessTypeAutomaticTest {

        @Rule
        public DmnEngineRule dmnEngineRule = new DmnEngineRule();

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
                        "process-type-decision.dmn"
        })
        public void TestAge() {

                /* Test normal age and normal credit amount */

                // create and add variables
                VariableMap variables = Variables.createVariables();
                variables.putValue("applicantAge", 50);
                variables.putValue("creditAmount", 50000);

                DecisionService decisionService = myProcessEngine.getDecisionService();

                DmnDecisionTableResult result = decisionService.evaluateDecisionTableByKey("Decision1", variables);

                assertEquals(1, result.getResultList().size());
                assertEquals("automatic", result.getSingleResult().getEntry("processType"));

        }

}
