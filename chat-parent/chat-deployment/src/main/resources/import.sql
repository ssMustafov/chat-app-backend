
insert into chat_user (id, username, password, email, name, registeredOn) values (nextval('chat_user_id_seq'), 'admin', 'DBso6SDSfTjAW3Fumj8qhQ==', 'admin@chat.com', 'Admin Admin', '2015-10-08');

insert into chat_room (id, name, description) values (nextval('chat_room_id_seq'), 'General', 'General room. Every new user is added to this room automatically.');

insert into chat_room_chat_user (chat_room_id, users_id) values (1, 1);