(ns iix.se-luminous.test.services.authentication
  (:require [iix.se-luminous.db.core :refer [*db*] :as db]
            [iix.se-luminous.services.authentication :refer :all]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [iix.se-luminous.config :refer [env]]
            [mount.core :as mount]
            [buddy.hashers :as hashers]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'iix.se-luminous.config/env
      #'iix.se-luminous.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-authenticate
  (jdbc/with-db-transaction
    [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= false (authenticate "UnknownUser" "secret")))
    (is (= 1 (db/create-user! {:username "Test"
                               :password (hashers/derive "secret")})))
    (is (= true (authenticate "Test" "secret")))
    (is (= false (authenticate "Test" "WrongPassword")))))
