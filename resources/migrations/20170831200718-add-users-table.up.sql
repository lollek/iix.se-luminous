CREATE TABLE users
(id SERIAL PRIMARY KEY,
 username VARCHAR(20),
 password VARCHAR(300),
 last_login TIME);
