;(ns movie.handler
;  (:require [compojure.core :refer :all]
;            [compojure.route :as route]
;            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;(defroutes app-routes
;  (GET "/" [] "Hello World")
;  (route/not-found "Not Found"))

;(def app
;  (wrap-defaults app-routes site-defaults))

(ns movie.routes
   
  (:use compojure.core
        movie.view
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            ;[ring.util.response :refer [resource-response response]]
            [compojure.response :as response]
            ))

   (defroutes main-routes
    (GET "/" [] (index-page))
    (route/resources "/")
    )
   
