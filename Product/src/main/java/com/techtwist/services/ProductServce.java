package com.techtwist.services;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableClientBuilder;
import com.azure.data.tables.models.TableEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServce {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.table-name}")
    private String tableName;

    private TableClient tableClient;

    @PostConstruct
    public void init() {
        tableClient = new TableClientBuilder()
                .connectionString(String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;TableEndpoint=https://%s.table.core.windows.net;", accountName, accountKey, accountName))
                .tableName(tableName)
                .buildClient();
    }

    public void createEntity(String partitionKey, String rowKey, Map<String, Object> properties) {
        TableEntity entity = new TableEntity(partitionKey, rowKey).setProperties(properties);
        tableClient.createEntity(entity);
    }

    public TableEntity readEntity(String partitionKey, String rowKey) {
        return tableClient.getEntity(partitionKey, rowKey);
    }

    public void updateEntity(String partitionKey, String rowKey, Map<String, Object> properties) {
        TableEntity entity = new TableEntity(partitionKey, rowKey).setProperties(properties);
        tableClient.updateEntity(entity);
    }

    public void deleteEntity(String partitionKey, String rowKey) {
        tableClient.deleteEntity(partitionKey, rowKey);
    }
}