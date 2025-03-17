package com.techtwist.services;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.TableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServceTest {

    @Mock
    private TableClient tableClient;

    @InjectMocks
    private ProductService productServce;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productServce.init();
    }

    @Test
    void testCreateEntity() {
        String partitionKey = "partition1";
        String rowKey = "row1";
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Product1");

        productServce.createEntity(partitionKey, rowKey, properties);

        ArgumentCaptor<TableEntity> entityCaptor = ArgumentCaptor.forClass(TableEntity.class);
        verify(tableClient, times(1)).createEntity(entityCaptor.capture());
        TableEntity capturedEntity = entityCaptor.getValue();

        assertEquals(partitionKey, capturedEntity.getPartitionKey());
        assertEquals(rowKey, capturedEntity.getRowKey());
        assertEquals(properties, capturedEntity.getProperties());
    }

    @Test
    void testReadEntity() {
        String partitionKey = "partition1";
        String rowKey = "row1";
        TableEntity expectedEntity = new TableEntity(partitionKey, rowKey);
        when(tableClient.getEntity(partitionKey, rowKey)).thenReturn(expectedEntity);

        TableEntity actualEntity = productServce.readEntity(partitionKey, rowKey);

        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void testUpdateEntity() {
        String partitionKey = "partition1";
        String rowKey = "row1";
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "UpdatedProduct");

        productServce.updateEntity(partitionKey, rowKey, properties);

        ArgumentCaptor<TableEntity> entityCaptor = ArgumentCaptor.forClass(TableEntity.class);
        verify(tableClient, times(1)).updateEntity(entityCaptor.capture());
        TableEntity capturedEntity = entityCaptor.getValue();

        assertEquals(partitionKey, capturedEntity.getPartitionKey());
        assertEquals(rowKey, capturedEntity.getRowKey());
        assertEquals(properties, capturedEntity.getProperties());
    }

    @Test
    void testDeleteEntity() {
        String partitionKey = "partition1";
        String rowKey = "row1";

        productServce.deleteEntity(partitionKey, rowKey);

        verify(tableClient, times(1)).deleteEntity(partitionKey, rowKey);
    }
}