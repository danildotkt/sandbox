package io.sandbox.telegram_bot;

//class TelegramBotTest {
//
//    @Mock
//    private TelegramUpdateHandler telegramUpdateHandler;
//
//    @Mock
//    private TelegramBot telegramBot;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        telegramBot = new TelegramBot(telegramUpdateHandler);
//    }
//
//    @Test
//    void sendMessage_shouldExecuteSendMessage() throws TelegramApiException {
//        Update update = new Update();
//        Message message = new Message();
//        Chat chat = Mockito.mock(Chat.class);
//        Mockito.when(chat.getId()).thenReturn(123456789L);
//        message.setChat(chat);
//        update.setMessage(message);
//        String text = "Hello, world!";
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(message.getChat().getId());
//        sendMessage.setText(text);
//
//        telegramBot.sendMessage(update, text);
//
//        verify(telegramBot, times(1)).execute(sendMessage);
//    }
//}