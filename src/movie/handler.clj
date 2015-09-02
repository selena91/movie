(ns movie.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [movie.view :as view]
            [movie.db :as db]
            [ring.middleware.session]
            [ring.util.response])
  )

(defroutes app-routes
  (GET "/"
       []
       (view/login-page))

  (GET "/home"
       []
       (view/home-page))

  (POST "/"
       [CustomerID Password]
       (:session CustomerID)
       (println (str "Session: " (:session CustomerID)))
       (let [countCustomer (db/do-login CustomerID Password)]
       (if (> (:total countCustomer) 0)
         (view/home-page)
         (view/register-page)))
       )

  (GET "/register"
       []
       (view/register-page))

  (POST "/register"
       [CustomerID Password]
       (db/register CustomerID Password)
       (view/login-page))

  (GET "/movies"
       []
       (view/all-movies-list))

  (GET "/recommendation"
       [CustomerID]
       (view/recommended-movies CustomerID))

  (route/resources "/")
  (route/not-found "Not Found"))

  (def app
  (handler/site app-routes))

