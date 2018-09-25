# Тестовое задание для Java
Написать примитивный mtproto сервер с помощью `java.nio.ByteBuffer` (для парсеров сообщений) и NIO2. Сервер должен уметь выполнять первые 2 команды при инициализации DH-сессии: *req_pq* и *req_DH_params*. На *req_pq* отдает *res_pq* со случайными данными и ждет *req_DH_params* на который отвечает *server_DH_params_ok* и закрывает коннект.

Документация по mtproto:

* https://core.telegram.org/mtproto/auth_key
* https://core.telegram.org/mtproto/description#unencrypted-message

Тестовый проект загружаете на свой github и в комментариях тут пишите ссылку.
