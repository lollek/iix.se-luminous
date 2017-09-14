(ns iix.se-luminous.test.db.core
  (:require [iix.se-luminous.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [iix.se-luminous.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'iix.se-luminous.config/env
      #'iix.se-luminous.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(defn test-create [t-conn username password]
  (is (= 1 (db/create-user!
             t-conn
             {:username username
              :password password}))))

(defn test-get [t-conn username]
  (let [user (db/get-user t-conn {:username username})]
    (is (= (keys user)
           '(:id, :username, :password, :last_login)))
    (is (> (user :id)
           0))
    (is (= (user :username)
           "Sam"))
    (is (= (user :password)
           "Smith"))
    (is (= (user :last_login)
           nil))))

(defn test-remove [t-conn username]
  (is (= 1 (db/delete-user! t-conn {:username username})))
  (is (= nil (db/get-user t-conn {:username username}))))

(deftest test-users
  (jdbc/with-db-transaction
    [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (test-create t-conn "Sam" "Smith")
    (test-get t-conn "Sam")
    (test-remove t-conn "Sam")))
