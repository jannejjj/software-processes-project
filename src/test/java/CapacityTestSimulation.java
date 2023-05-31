import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class CapacityTestSimulation extends Simulation {

        // Protocol Definition
        HttpProtocolBuilder httpProtocol = HttpDsl.http.baseUrl("http://localhost:8080/engine-rest")
                        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

        // Starting the process before the decision activity in order to test the
        // capacity of the decision matrix
        ScenarioBuilder scn = CoreDsl.scenario("Capacity Test")
                        .exec(http("Start process")
                                        .post("/process-definition/key/Process_0umpg4c/start")
                                        .body(RawFileBody("startRequestBody.json"))
                                        .check(status().is(200)));
        // .exec(http("Check current activity")
        // .get("/task")
        // .check(status().is(200)));

        // Simulation
        public CapacityTestSimulation() {
                this.setUp(scn.injectOpen(atOnceUsers(10)))
                                .protocols(httpProtocol);
        }

}
