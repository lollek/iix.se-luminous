-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(username, password)
VALUES (:username, :password);

-- :name get-user :? :1
-- :doc retrieve a user given the username.
SELECT * FROM users
WHERE username = :username;

-- :name delete-user! :! :n
-- :doc delete a user given the username
DELETE FROM users
WHERE username = :username;
