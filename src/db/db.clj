(ns db.db
(:require [clojure.java.jdbc :as sql]
          [hiccup.page :as hiccP]
          [korma.core :as kormaC]
          [korma.db :as kormadb1]))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "movie.db"})

(defn movie-list
 []
  (sql/query db ["select * from movies"]))




