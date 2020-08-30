# AnonApp Api
[![Build Status](https://travis-ci.com/OrlovDiga/anonapp-api.svg?branch=master)](https://travis-ci.com/OrlovDiga/anonapp-api)

AnonAppp Api is a backend application for an anonymous restful chat.

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