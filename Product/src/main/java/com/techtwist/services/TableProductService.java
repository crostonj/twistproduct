package com.techtwist.services;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableClientBuilder;
import com.azure.data.tables.models.TableEntity;
import com.techtwist.models.Product;
import com.techtwist.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service("TableProductService") // Matches the value in application.properties
@ConditionalOnProperty(name = "service.tableProductService.enabled", havingValue = "true", matchIfMissing = true)
public class TableProductService implements IProductService {


   // private final String accountName = EnvConfig.get("azure.storage.accountName");
   // private final String accountKey = EnvConfig.get("azure.storage.accountKey");;
   // private final String tableName = EnvConfig.get("azure.storage.tablename");;

    private final String accountName = System.getenv("ACCOUNTNAME");
    private final String accountKey = System.getenv("ACCOUNTKEY");
    private final String tableName = System.getenv("TABLENAME");


    private TableClient tableClient;

    @PostConstruct
    public void init() {
        tableClient = new TableClientBuilder()
                .connectionString(String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;TableEndpoint=https://%s.table.core.windows.net;", accountName, accountKey, accountName))
                .tableName(tableName)
                .buildClient();
    }

    public Product create(Product product) {
        try {
            TableEntity entity = new TableEntity(product.getPartitionKey(), product.getRowKey()).setProperties(productToMap(product));
            tableClient.createEntity(entity);
            return product;
        } catch (Exception e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace(); // Or use a logging framework
            return null; // Or handle the error appropriately
        }
    }

    public Product get(String partitionKey, String rowKey) {
        TableEntity entity = tableClient.getEntity(partitionKey, rowKey);
        return mapToProduct(entity);
    }

    //Get by name
    public Product getByName(String name) {
        try {
            for (TableEntity entity : tableClient.listEntities()) {
                if(entity != null) {          
                    Product product = mapToProduct(entity);
                    if (product.getName().equals(name)) {
                        return product;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace(); // Or use a logging framework
            return null;
        }
    }

    public Product update(Product product) {
        try {
            TableEntity entity = new TableEntity(product.getPartitionKey(), product.getRowKey()).setProperties(productToMap(product));
            tableClient.updateEntity(entity);
            return product;
        } catch (Exception e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace(); // Or use a logging framework
            return null;
        }
    }
    public void delete(Product product) {
        tableClient.deleteEntity(product.getPartitionKey(), product.getRowKey());
    }

    public Map<String, Object> productToMap(Product product) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", product.getName());
        properties.put("price", product.getPrice());
        properties.put("RowKey", product.getRowKey());
        properties.put("PartitionKey", product.getPartitionKey());
        return properties;
    }

    public Product mapToProduct(TableEntity entity) {
        Product product = new Product();
        product.setPartitionKey(entity.getPartitionKey());
        product.setRowKey(entity.getRowKey());
        product.setName((String) entity.getProperty("name"));

        Object priceProperty = entity.getProperty("price");
        if (priceProperty != null) {
            product.setPrice((Double) priceProperty);
        } else {
            product.setPrice(0.0); // Default value or handle as needed
        }
        return product;
    }
}