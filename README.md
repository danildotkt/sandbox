# sandbox

Привет, это документация телеграм бота sandbox ! 

## Введение

Telegram-бот "sandbox" использует Tinkoff Invest API, чтобы позволить пользователям изучать и имитировать торговлю акциями в безопасной среде. 

С помощью этого бота пользователи могут:

  - Зарегестрировать sandbox  профиль в Tinkoff Invset API с помощью специального токена.
  - Создавайте виртуальный портфель акций и управляйте им.
  - Выполняйте ордера на покупку акций.
  - Отслеживайте эффективность своего виртуального портфеля.
  - Получать список операций по счету.
  - Получать финансовые отчетности компаний эмитентов.

Бот использует следующие команды:

  - /start - для регистрации в tinkoff api 
  - /portfolio - для получения данных о своих активах 
  - /buy_stock - для покупки акций московской биржи 
  - /operations - для получения списка последних операций (максимум 10)
  - /company_data  - для получения ссылки на ресурс с финансовой отчетностью эмитента 

## Настройки

Для запуска проекта необходимо :

  - JDK 17+
  - Docker
  - в папке resources в модуле sandbox ввести telegram bot token и название телеграм бот 
  - собрать проект с помощью команды "mvn package" для генерации Java классов из .proto файлов 
  - запустить docker-compose.yml файл и стартуете модули.

## database модуль 

Данный модуль представляет собой сервис, который обеспечивает сохранение новых пользователей в базу данных и предоставляет доступ к этим данным через gRPC-сервис.

1. **TelegramUser** - сущность, представляющая пользователя sandbox.
2. **TelegramUserConsumer** - сохраняет всех пользователей, полученных от TelegramUserProducer.
3. **TelegramUserRepository** - репозиторий для работы с объектами в базе данных.
4. **JpaServiceImpl** - gRPC-сервис, предоставляющий возможность взаимодействия с базой данных из других модулей.
5. **JpaApplication** - приложение Spring Boot, в котором также запускается сервер JpaServiceImpl.


## kafka модуль 

модуль в котором создается kafka топик;)


## sandbox модуль

Этот модуль является центральным модулем, в котором происходит взаимодействие с пользователем.

1. **JpaService** - интерфейс, предоставляющий некоторые команды для взаимодействия с gRPC сервисом.
2. **JpaServiceClient** - клиент gRPC сервера, который получает данные.
3. **JpaServiceStub** - возвращает stub для взаимодействия с JpaServiceClient.
<br>

4. **InvestAPI** - интерфейс, в котором описаны команды, необходимые для взаимодействия с пользователем.
5. **TinkoffInvestApiClient** - класс, реализующий методы, связанные с взаимодействием с Tinkoff Invest API.
6. **TinkoffInvestStub** - создает канал с метаданными для подключения к sandbox invest api tinkoff и предоставляет stubы.
<br>

8. **TelegramUser** - DTO для отправки в базу данных.
<br>

9. **CommandFactory** - фабрика для создания команд запроса или ответа.
<br>

10. **TelegramUserProducer** - для отправки данных успешно зарегистрированного пользователя.
<br>

11. **BuyStockRequest** - запрашивает у пользователя корректный ввод данных для покупки акции и дает подсказку.
12. **CommandRequest** - интерфейс, описывающий методы, которые должны реализовывать команды запроса.
13. **CompanyDataRequest** - запрашивает у пользователя данные для предоставления финансовой отчетности компании и дает подсказку.
14. **StartRequest** - запрашивает у пользователя токен tinkoff invest.
<br>

15. **BuyStockResponse** - в случае валидных данных и достаточности баланса возвращает сообщение об успешной покупке актива.
16. **CommandResponse** - интерфейс, описывающий методы, которые должны реализовывать команды ответа.
17. **CompanyDataResponse** - в случае валидности тикера возвращает ссылку на сайт smartlab с финансовой отчетностью данной компании.
18. **OperationsResponse** - возвращает список последних 10 операций пользователя или просто список операций, если их меньше 10.
19. **PortfolioResponse** - возвращает список всех активов пользователя и показатели доходности.
20. **StartResponse** - регистрирует нового пользователя и сохраняет в базе данных в случае валидности токена.
<br>

21. **CommandExecutor** - центральный класс, в котором ввод пользователя будет преобразовываться в его состояние и меняться в зависимости от его действий.
22. **TelegramBot** - класс, представляющий собой телеграм-бота.
23. **TelegramUpdateHandler** - класс, обрабатывающий Update, в зависимости от его типа.
<br>

24. **UserState** - перечисление со всеми возможными состояниями пользователя.
<br>

25. **utils** - папка с парсерами для более удобной работы с дробными числами и специальными типами данных Tinkoff API, а также с сообщениями для передачи информации, чтобы они не засоряли основные классы.
<br>


25. **SandboxApplication** - приложение Spring Boot
<br>

   















  
