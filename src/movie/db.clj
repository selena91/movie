(ns movie.db
(:require [clojure.java.jdbc :as j]
          [hiccup.page :as hiccP]))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "movie.db"})

(defn movie-list
  "List of all movies in db"
  []
    (j/query db ["select * from movies"]))

(defn top10-movies
  "List of top 10 movies"
  []
  (j/query db ["select * from movies
                 where MovieID in (
                    select distinct(MovieID)
                    from ratings
                    order by rating desc
                    limit 10
                )"]))

(defn do-login
  "Check if customer exist in table customers"
  [CustomerID Password]
    (j/query db
      ["select count(CustomerID) as total
       from customers
       where CustomerID = ? and Password = ? " CustomerID Password]
      :result-set-fn first))

(defn register
  "Add new customer to table customers"
  [CustomerID Password]
  (println (str "Id " CustomerID))
    (j/insert! db :customers {:CustomerID CustomerID :Password Password}))


(defn count-customer-rating
  "Check if customer have any rating"
  [CustomerID]
    (j/query db
      ["select count(CustomerID) as total
       from ratings
       where CustomerID = ? " CustomerID]
      :result-set-fn first))

(defn select-all-customer-ratings
  "Function which returns all customer ratings"
  [CustomerID]
    (j/query db
      ["select MovieID, Rating
       from ratings
       where CustomerID = ? " CustomerID]
      ))

(defn all-customers
  "Function which returns all customer ID"
  []
    (j/query db
      ["select CustomerID
       from customers"]
      ))

(defn calculate-intersection
  "Function which returns number of same ratings of two customers"
  [CustomerID1 CustomerID2]
  (let [ratings1 (select-all-customer-ratings CustomerID1)
        ratings2 (select-all-customer-ratings CustomerID2)
        number 0]
    (for [rate1 ratings1]
      (for [rate2 ratings2]
        (if (and (= (:movieid rate1) (:movieid rate2)) (= (:rating rate1) (:rating rate2)))
          (inc number)
        )
      )
    )
  number)
)

(defn calculate-union
  "Function which returns number of all ratings of two customers"
  [CustomerID1 CustomerID2]
  (let [ratings1 (select-all-customer-ratings CustomerID1)
        ratings2 (select-all-customer-ratings CustomerID2)
        all (+ count(ratings1) count(ratings2))
        number (- all (calculate-intersection CustomerID1 CustomerID2))]
   number)
  )

(defn jaccard-index
  "Function which returns Jaccard similarity coeficient - value between 0 and 1"
  [CustomerID1 CustomerID2]
  (let [index (/ (calculate-intersection CustomerID1 CustomerID2) (calculate-union CustomerID1 CustomerID2))]
    index)
  )

(defn similarity
  "Function which returns similarity with all customers"
  [CustomerID]
  (let [all-customers-list (all-customers)]
    (for [customer all-customers-list]
      (let [index (jaccard-index CustomerID (:customerid customer))]
        (assoc {} :customerid (:customerid customer) :jacIndex index)
      )
    )
  ))

  ;(defn top10-similar-customers
   ; "Function which returns top 10 similar customers"
   ; [CustomerID]
   ; (let [similar (similarity CustomerID)]
   ;   )
   ; )
