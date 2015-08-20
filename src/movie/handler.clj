(ns movie.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            ;[cocktails.db :as db]
            [movie.view :as view]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.anti-forgery]
            [ring.middleware.session]
            ))

(defroutes app-routes
  (GET "/"
       []
       (view/home-page))

  (GET "/login"
       []
       (view/login-page))

  (GET "/register"
       []
       (view/register-page))

  ;for access to public folder and all his content (js,css,img...)
  (route/resources "/"))

  (def app
  (wrap-defaults app-routes nil))

