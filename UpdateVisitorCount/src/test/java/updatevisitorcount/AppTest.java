package updatevisitorcount;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.Assert.*;

public class AppTest {
    /**
     * Unit test for checking that the API gateway has the correct response,
     * that the response has the correct CORS headers and the body contains the
     * count label.
     */
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

    /**
     *  Unit test for checking that the Dynamodb methods get the visitor count and that the
     *  dynamoDB table is updated with the correct value.
     */
    @Test
    public void testDynamoDb() {
        DynamoDbClient ddb = DynamoDbClient.builder().region(Region.EU_WEST_1).build();
        String primaryKey = "ID";
        String siteName = "blog";
        String AttributeToUpdate = "visitors";
        String tableName = "cloud-resume-challenge";
        int visitorCount;
        String updatedCount;

        // Get the visitor count from the table
        visitorCount = GetVisitors.getVisitorCount(ddb, tableName, primaryKey, siteName);
        assertTrue(visitorCount > 0);
        updatedCount = Integer.toString(visitorCount + 1);

        UpdateVisitors.updateVisitorCount(ddb, tableName, primaryKey, siteName, AttributeToUpdate, updatedCount);

        // Get the updated count and ensure that it is greater than the original
        int visitorCountUpdated = GetVisitors.getVisitorCount(ddb, tableName, primaryKey, siteName);
        assertTrue(visitorCountUpdated > visitorCount);
    }
}
