package io.sandbox.api.database;

import io.sandbox.grpc.JpaServiceGrpc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
