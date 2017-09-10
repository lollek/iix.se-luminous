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
    (is (= {:username  "Sam"
            :password  "Smith"
            :last_login nil}
           (db/get-user t-conn {:username "Sam"})))))
