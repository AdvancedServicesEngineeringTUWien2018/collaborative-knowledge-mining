package at.ac.tuwien.ase.cost.estimator.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/health")
@Api(value = "Health Center", description = "Methods for checking the health of this microservice.")
public class HealthController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "A method to check whether this microservice is online and responsive.")
    public HealthStatus ok() {
        return new HealthStatus(true);
    }

    /**
     * A dto for the health status.
     */
    private static class HealthStatus {

        private boolean ok;
        private Instant timestamp;

        private HealthStatus(boolean ok) {
            this.ok = ok;
            this.timestamp = Instant.now();
        }

        public boolean isOk() {
            return ok;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return "HealthStatus{" +
                    "ok=" + ok +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }
}
