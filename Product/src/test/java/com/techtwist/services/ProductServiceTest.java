package com.techtwist.services;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.models.TableEntity;
import com.techtwist.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private TableClient tableClient;

    @InjectMocks
    private ProductService productServce;

    private String rowKey;

    @BeforeEach
    void setUp() {
        //mocks = MockitoAnnotations.openMocks(this);
        rowKey = UUID.randomUUID().toString();
    }


    private Product createTestProduct() {
        return new Product("Product1", 10.0, "partition1", rowKey);
    }

    @Test
    void testCreateEntity() {

        Product product = createTestProduct();

        productServce.create(product);

        ArgumentCaptor<TableEntity> entityCaptor = ArgumentCaptor.forClass(TableEntity.class);
        verify(tableClient, times(1)).createEntity(entityCaptor.capture());
        TableEntity capturedEntity = entityCaptor.getValue();

        assertEquals(product.getPartitionKey(), capturedEntity.getPartitionKey());
        assertEquals(rowKey, capturedEntity.getRowKey());
        assertEquals(productServce.productToMap(product), capturedEntity.getProperties());
    }

    @Test
    void testReadEntity() {
        Product product = createTestProduct();

        TableEntity expectedEntity = new TableEntity(product.getPartitionKey(), rowKey);
        when(tableClient.getEntity(product.getPartitionKey(), rowKey)).thenReturn(expectedEntity);
        Product expectedProduct = productServce.mapToProduct(expectedEntity);

        Product productActual = productServce.get(product.getPartitionKey(), rowKey);

        assertEquals(expectedProduct, productActual);
    }

    @Test
    void testUpdateEntity() {
        Product product = createTestProduct();
        product.setName("UpdatedProduct");


        productServce.update(product);

        ArgumentCaptor<TableEntity> entityCaptor = ArgumentCaptor.forClass(TableEntity.class);
        verify(tableClient, times(1)).updateEntity(entityCaptor.capture());
        TableEntity capturedEntity = entityCaptor.getValue();

        assertEquals(product.getPartitionKey(), capturedEntity.getPartitionKey());
        assertEquals(rowKey, capturedEntity.getRowKey());
        assertEquals(productServce.productToMap(product), capturedEntity.getProperties());
    }

    @Test
    void testDeleteEntity() {
        Product product = createTestProduct();

        productServce.delete(product);

        verify(tableClient, times(1)).deleteEntity(product.getPartitionKey(), product.getRowKey());
    }
}