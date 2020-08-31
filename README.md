# AnonApp Api
[![Build Status](https://travis-ci.com/OrlovDiga/anonapp-api.svg?branch=master)](https://travis-ci.com/OrlovDiga/anonapp-api)

AnonAppp Api is a backend application for an anonymous restful chat, on web sockets.

## Installation

To use this repository, you need to download it.
Next, you need to create an `application.yml` in _src/main/resource/_ and add the following configuration there:

Application.yml
```
spring:
  jpa:
    database: POSTGRESQL
    show-sql: true
    hibernate:
      ddl-auto: create-drop
      #ddl-auto: update

  datasource:
    platform: postgres
    url: jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
    driver-class-name: org.postgresql.Driver

  mail:
    host: smtp.gmail.com
    port: 587
    username: YOUR_GMAIL_ADDRESS
    password: YOUR_APP_PASSWORD
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

logging:
  file: logs/anonapp.log
  level:
    root: INFO
    org:
      springframework:
        security: DEBUG

resource:
  prefix:
    url: "http://localhost:8080/resources/"
  path:
    images: "src/main/resources/static/files/"

```
__P.S.__ Fully uppercase values ​​are not allowed, you need to replace them with existing values.


In order to find out how to get an app password for gmail, go to the [following link](https://support.google.com/accounts/answer/185833).


You can change `application.yml` to your liking. For example, use a config to connect to another database.


## Usage

This application has multiple entry points:

#### Public routes:

* POST */register* - registration a new user.
* POST */reduction* - recovering user password by username.
* POST|GET */resources* - uploading and receiving files.

#### Routes that require a token in the request header to access:
* WS */api/socket* - сreating a web socket connection.
* POST */api/check* - checking the validity of the token in the header.
* POST */api/home/likes* - get a friend list.
* POST */api/options/changePswd* - user self-change of password.

<details><summary>Request examples: </summary>

### Public routes:
***
#### */register*

 `POST`
```
{
"username": "qwerty@gmail.com",
"password": "1234",
"matchingPassword": "1234"
}
 ```
***

#### */reduction*
`POST`
 ```
{
"username": "qwerty@gmail.com",
}
 ```
***

#### */resources*
`POST`
 ```
{
"extension": ".jpeg",
"data": "BYTE_ARR_TO_BASE_64_ENCODE"
}
 ```
#### */resources/0b0800b2-f4ba-4df2-a018-753a18c86c69.jpg*
`GET`
 ```
And you get the file.
 ```
<br/>
<br/>
### Routes that require a token in the request header to access:
***
#### */api/socket*
`WS` - web socket connection

An example of connection is presented in [Dart language](https://flutter.dev/docs/cookbook/networking/web-sockets):

```
IOWebSocketChannel channel = IOWebSocketChannel.connect(
    Uri(scheme: "ws", host: "localhost", port: 8080, path: "/api/socket"),
    headers: {'token': 'something token'}
    );
```
***
#### _/api/check_
`POST`

The body of this request must be empty, and the header must contain token.

***

#### _/api/home/likes_
`POST`

```
This method is in development ...
```
***
#### _/api/options/changePswd_
`POST`
```
{
"oldPassword": "1234",
"newPassword": "1111",
"matchingNewPassword": "1111"
}
```
***

</details>

## Support

Means of communication with me:
>Mail: __privaloffdim@yandex.ru__ 
>Telegram: __@movage__

## Roadmap
Adding various new functions to the application

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://github.com/OrlovDiga/anonapp-api/blob/master/LICENSE)