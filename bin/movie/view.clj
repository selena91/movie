(ns movie.view

    (:require [db.db :as db]
           ; [movie.test :as atest]
           ; [clojure.string :as str]
            [hiccup.page :as hicup]))

  (defn index-page []
    (html5
      [:head
       [:title "Hello world"]
       (include-css "/css/style.css")]
      [:body
       [:ul
        (for [x (range 1 4)]
          [:li x])]]))


