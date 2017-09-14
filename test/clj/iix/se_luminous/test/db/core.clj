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

(deftest test-users
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-user!
               t-conn
               {:username "Sam"
                :password "Smith"})))
    (let [user (db/get-user t-conn {:username "Sam"})]
      (is (= (keys user)
             '(:id, :username, :password, :last_login)))
      (is (> (user :id)
             0))
      (is (= (user :username)
             "Sam"))
      (is (= (user :password)
             "Smith"))
      (is (= (user :last_login)
             nil)))))
