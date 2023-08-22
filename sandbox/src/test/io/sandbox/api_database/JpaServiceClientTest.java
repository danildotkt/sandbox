package io.sandbox.api_database;

//class JpaServiceClientTest {
//    private final ManagedChannel channel = mock(ManagedChannel.class);
//    private final JpaServiceGrpc.JpaServiceBlockingStub stub = mock(JpaServiceGrpc.JpaServiceBlockingStub.class);
//    private JpaServiceClient jpaServiceClient;
//
//    @BeforeEach
//    void setUp() {
//        when(JpaServiceGrpc.newBlockingStub(channel)).thenReturn(stub);
//        jpaServiceClient = new JpaServiceClient();
//    }
//
//    @Test
//    void testIsExist() {
//        long chatId = 12345;
//
//        // Mocking the existById method
//        JpaServiceOuterClass.ExistByIdResponse response = JpaServiceOuterClass.ExistByIdResponse.newBuilder()
//                .setExist(true)
//                .build();
//        when(stub.existById(any(JpaServiceOuterClass.ExistByIdRequest.class))).thenReturn(response);
//
//        boolean result = jpaServiceClient.isExist(chatId);
//
//        assertTrue(result);
//        verify(stub).existById(any(JpaServiceOuterClass.ExistByIdRequest.class));
//    }
//}