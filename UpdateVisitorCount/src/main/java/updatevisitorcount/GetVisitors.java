package updatevisitorcount;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;
import java.util.Map;

public class GetVisitors {
    /**
     * Gets the visitor count from the DynamoDB table using the attributes,
     * checks that the value returned is of number type,
     * then using string manipulation returns integer for main application logic
     *
     * @param ddb        Amazon dynamoDB client instance
     * @param tableName  Name of table to query
     * @param primaryKey primary key field ID
     * @param siteName   the name of the site to be found
     * @return return number of visitors found in the database
     */
    public static int getVisitorCount(DynamoDbClient ddb, String tableName, String primaryKey, String siteName) {

        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        int numberVisitors = 0;
        String visitorsString = "";

        keyToGet.put(primaryKey, AttributeValue.builder()
                .s(siteName).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();

            if (returnedItem != null) {
                String visitorsAttributeValue = returnedItem.get("visitors").toString();
                if (visitorsAttributeValue.contains("N")) {
                    visitorsString = visitorsAttributeValue.substring(17, visitorsAttributeValue.length() - 1);
                }
                numberVisitors = Integer.parseInt(visitorsString);
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return numberVisitors;
    }
}
