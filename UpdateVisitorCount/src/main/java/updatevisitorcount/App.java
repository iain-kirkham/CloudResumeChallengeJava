package updatevisitorcount;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.HashMap;
import java.util.Map;


/**
 * This main lambda function gets the visitor count and then updates the dynamoDB database,
 * Then once finished updating the database, returns a JSON string containing the updated user value to be displayed
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        String primaryKey = "ID";
        String siteName = "blog";
        String AttributeToUpdate = "visitors";
        String tableName = "cloud-resume-challenge";
        int visitorCount;
        String updatedCount;

        Map<String, String> headers = new HashMap<>();

        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.EU_WEST_1)
                .build();

        visitorCount = GetVisitors.getVisitorCount(ddb, tableName, primaryKey, siteName);
        visitorCount++;
        updatedCount = Integer.toString(visitorCount);
        UpdateVisitors.updateVisitorCount(ddb, tableName, primaryKey, siteName, AttributeToUpdate, updatedCount);
        ddb.close();

        headers.put("Access-Control-Allow-Headers", "application/json");
        headers.put("Access-Control-Allow-Methods", "GET");
        headers.put("Access-Control-Allow-Origin", "https://iainkirkham.dev");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        String output = String.format("{ \"count\": \"%d\" }", visitorCount);
        return response
                .withStatusCode(200)
                .withBody(output);
    }
}