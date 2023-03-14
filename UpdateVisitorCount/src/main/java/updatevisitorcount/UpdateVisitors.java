package updatevisitorcount;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;

public class UpdateVisitors {
    /**
     * Takes in the number of visitors that the site has and then uses DynamoDB to update the number value
     *
     * @param ddb               Amazon dynamoDB client instance
     * @param tableName         Name of table to update
     * @param primaryKey        primary key field ID
     * @param siteName          the name of the site to be found
     * @param AttributeToUpdate the name of the attribute to update in this case visitors
     * @param updatedCount      the new visitor count number
     */
    public static void updateVisitorCount(DynamoDbClient ddb,
                                          String tableName,
                                          String primaryKey,
                                          String siteName,
                                          String AttributeToUpdate,
                                          String updatedCount) {

        HashMap<String, AttributeValue> itemKey = new HashMap<>();
        itemKey.put(primaryKey, AttributeValue.builder()
                .s(siteName)
                .build());

        HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<>();
        updatedValues.put(AttributeToUpdate, AttributeValueUpdate.builder()
                .value(AttributeValue.builder().n(updatedCount).build()) // has to be n otherwise will get converted to a string
                .action(AttributeAction.PUT)
                .build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(itemKey)
                .attributeUpdates(updatedValues)
                .build();

        try {
            ddb.updateItem(request);
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
