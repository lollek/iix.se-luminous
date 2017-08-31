(ns iix.se-luminous.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [iix.se-luminous.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[iix.se-luminous started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[iix.se-luminous has shut down successfully]=-"))
   :middleware wrap-dev})
