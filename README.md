# Сервис Облачное хранилище
Дипломный проект по курсу "Java-разработчик с нуля" в Нетологии. 

## Технические характеристики

   - Язык: Java 17
   - IDE: Idea
   - Framework: Spring Boot, Spring Boot Security
   - Управление проектом: Maven
   - Управление версиями: github
   - Тестирование: Mock, Mockito
   - Порт работы back end: 9090
   - Настройки: application.yml
   
## Функциональность

#### Автоизация.

  - Пользователь авторизуется в облаке по логину у паролю, получает token. Все последующие операции в облаке проводятся идентифицируются с помощью token.
  - Запрос на адрес методом POST: http://localhost:9090/login
  - Пример тела запроса: 
  ```JSON
{
  "login": "login1@mail.ru",
  "password": "1"
}  
````

#### Функции в облаке

##### Загрузка файла в облако
  - Запрос на адрес методом POST: http://localhost:9090/file
  - В заголовке запроса передается token
  - В теле запроса - имя файла
 
#### Хранилище
- В качестве хранилища используется СУБД PostgresSql

#### Логирование

  - На стороне сервера происходит логирование с использованием Slf4j


