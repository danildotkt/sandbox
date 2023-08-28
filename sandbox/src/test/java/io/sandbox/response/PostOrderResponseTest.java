package io.sandbox.response;

//class PostOrderResponseTest {
//
//    @Mock
//    Update update;
//
//    @Mock
//    ConcurrentHashMap<Long, UserState> stateMap;
//
//    @Mock
//    TelegramBot telegramBot;
//
//    @BeforeEach
//    public void setup(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//
//    @Test
//    public void testSendMessage() {
//        Message message = mock(Message.class);
//        when(update.getMessage()).thenReturn(message);
//        when(message.getChatId()).thenReturn(123456789L);
//
//        StartResponse.sendResponse(update, stateMap, telegramBot);
//
//        verify(telegramBot, times(1)).sendMessage(eq(update), anyString());
//    }
//}