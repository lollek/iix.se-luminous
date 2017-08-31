(ns user
  (:require [mount.core :as mount]
            iix.se-luminous.core))

(defn start []
  (mount/start-without #'iix.se-luminous.core/repl-server))

(defn stop []
  (mount/stop-except #'iix.se-luminous.core/repl-server))

(defn restart []
  (stop)
  (start))


