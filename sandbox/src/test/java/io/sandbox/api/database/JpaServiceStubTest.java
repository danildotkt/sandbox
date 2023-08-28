package io.sandbox.api.database;

import io.grpc.ManagedChannel;
import io.sandbox.grpc.JpaServiceGrpc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class JpaServiceStubTest {

    private JpaServiceStub jpaServiceStub;

    @BeforeEach
    void setUp() {
        jpaServiceStub = new JpaServiceStub();
    }

    @Test
    void testReturnJpaServiceStub() {
        JpaServiceGrpc.JpaServiceBlockingStub stub = jpaServiceStub.returnJpaServiceStub();

        assertNotNull(stub);
    }
}
