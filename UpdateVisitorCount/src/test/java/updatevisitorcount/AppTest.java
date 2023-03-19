package updatevisitorcount;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void successfulResponse() {
        App app = new App();
        APIGatewayProxyResponseEvent result = app.handleRequest(null, null);
        assertEquals(200, result.getStatusCode().intValue());
        assertEquals("application/json", result.getHeaders().get("Access-Control-Allow-Headers"));
        assertEquals("GET", result.getHeaders().get("Access-Control-Allow-Methods"));
        assertEquals("https://iainkirkham.dev", result.getHeaders().get("Access-Control-Allow-Origin"));
        String content = result.getBody();
        assertNotNull(content);
        assertTrue(content.contains("\"count\":"));
    }
}
