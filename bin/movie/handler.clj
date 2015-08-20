;(ns movie.handler
;  (:require [compojure.core :refer :all]
;            [compojure.route :as route]
;            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;(defroutes app-routes
;  (GET "/" [] "Hello World")
;  (route/not-found "Not Found"))

;(def app
;  (wrap-defaults app-routes site-defaults))

(ns movie.handler
     (:require [compojure.core :refer :all]
            [movie.view :as view]
            [db.db :as db]
            [compojure.route :as route]
            [compojure.handler :as handler]))


   (defroutes main-routes
    (GET "/" [] (index-page))
    (route/resources "/")
    )

