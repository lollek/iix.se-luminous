(ns iix.se-luminous.routes.api
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [iix.se-luminous.services.authentication :refer :all]
            [schema.core :as s]))

(defapi api-routes
        {:swagger {:ui "/api/help"
                   :spec "/swagger.json"
                   :data {:info {:version "0.1.0"
                                 :title "iix.se"
                                 :description "API Documentation"}}}}

        (context "/api" []
                 (POST "/login" []
                       :return      Boolean
                       :body-params [username :- String,
                                     password :- String]
                       :summary     "Attempt to login with username and password"
                       (if (authenticate username password)
                         (ok true)
                         (forbidden "Incorrect username or password")))


                 (context "/example" []
                          :tags ["example"]

                          (GET "/plus" []
                               :return       Long
                               :query-params [x :- Long, {y :- Long 1}]
                               :summary      "x+y with query-parameters. y defaults to 1."
                               (ok (+ x y)))

                          (POST "/minus" []
                                :return      Long
                                :body-params [x :- Long, y :- Long]
                                :summary     "x-y with body-parameters."
                                (ok (- x y)))

                          (GET "/times/:x/:y" []
                               :return      Long
                               :path-params [x :- Long, y :- Long]
                               :summary     "x*y with path-parameters"
                               (ok (* x y)))

                          (POST "/divide" []
                                :return      Double
                                :form-params [x :- Long, y :- Long]
                                :summary     "x/y with form-parameters"
                                (ok (/ x y)))

                          (GET "/power" []
                               :return      Long
                               :header-params [x :- Long, y :- Long]
                               :summary     "x^y with header-parameters"
                               (ok (long (Math/pow x y)))))))
