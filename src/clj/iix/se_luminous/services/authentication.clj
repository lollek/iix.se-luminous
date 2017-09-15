(ns iix.se-luminous.services.authentication
  (:require [iix.se-luminous.db.core :refer [*db*] :as db]
            [buddy.hashers :as hashers]))

(defn authenticate [username password]
  (let [user (db/get-user {:username username})]
    (if (nil? user)
      false
      (hashers/check password (:password user)))))
