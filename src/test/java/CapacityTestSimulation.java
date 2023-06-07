import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class CapacityTestSimulation extends Simulation {

        // Protocol Definition
        HttpProtocolBuilder httpProtocol = HttpDsl.http.baseUrl("http://localhost:8080/engine-rest")
                        .acceptHeader("application/json")
                        .userAgentHeader("Gatling/Performance Test");

        // Starting the process
        ScenarioBuilder scn = CoreDsl.scenario("Capacity Test")
                        .exec(http("Start process")
                                        .post("/process-definition/key/Process_0umpg4c/start")
                                        .body(RawFileBody("startRequestBody.json"))
                                        .check(status().is(200)));

        // Simulation
        public CapacityTestSimulation() {
                this.setUp(scn.injectOpen(atOnceUsers(1)))
                                .protocols(httpProtocol);
        }

}
