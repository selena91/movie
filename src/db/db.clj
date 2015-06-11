(ns db.db)

(require '[clojure.java.jdbc :as jdbc])

(def db-spec {
              :subprotocol "mysql"
              :subname "//localhost:3306/movie"
              :user "root"
              :password "" })

(jdbc/with-connection db-spec
  (jdbc/with-query-results rs ["select * from movies"]
    (dorun (map #(println (:Title :MovieID  %)) rs))))
       
;(clojure.pprint/pprint rs)
 
 

