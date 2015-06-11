(ns movie.view
   ;(:require [compojure.core :refer :all]
    ;        [compojure.route :as app-routes]))
          ;  [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use [hiccup core page]))
 
  (defn index-page []
    (html5
      [:head
       [:title "Hello world"]
       (include-css "/css/style.css")]
      [:body
       [:ul
        (for [x (range 1 4)]
          [:li x])]]))
  
  ;(defroutes app-routes
   ; (GET "/" [] "<h1>Seli</h1>"))
  
