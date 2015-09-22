(ns movie.db
(:require [clojure.java.jdbc :as j]
          [hiccup.page :as hiccP]
          [clojure.string :as string]))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "movie.db"})

(defn movie-list
  "List of all movies in db"
  []
    (j/query db ["select * from movies"]))

(defn top10-movies
  "List of top 10 movies - recommended movies for customers who didn't give any rate"
  []
  (j/query db ["select * from movies
                 where MovieID in (
                    select distinct(MovieID)
                    from ratings
                    order by rating desc
                    limit 10
                )"]))

(defn do-login
  "Check if customer exists in the table customers"
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
  "Check if customer gave any rate"
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
  "Function which returns all customer IDs"
  []
    (j/query db
      ["select CustomerID
       from customers"]
      ))

(select-all-customer-ratings 2442)
(select-all-customer-ratings 30878)

(defn calculate-intersection
  "Function which returns number of the same ratings of the same movie of two customers"
  [CustomerID1 CustomerID2]
  (let [ratings1 (select-all-customer-ratings CustomerID1)
        ratings2 (select-all-customer-ratings CustomerID2)]
    (reduce + (flatten
      (for [rate1 ratings1]
        (for [rate2 ratings2]
          (if (and (= (:movieid rate1) (:movieid rate2)) (= (:rating rate1) (:rating rate2)))
            1
            0)))))))

(defn calculate-union
  "Function which returns number of all ratings of two customers"
  [CustomerID1 CustomerID2]
  (let [ratings1 (select-all-customer-ratings CustomerID1)
        ratings2 (select-all-customer-ratings CustomerID2)
        all (+ (count ratings1) (count ratings2))]
     (- all (calculate-intersection CustomerID1 CustomerID2))))


(calculate-intersection 2442 30878)
(calculate-union 2442 30878)

(defn jaccard-index
  "Function which returns Jaccard similarity coeficient - value between 0 and 1"
  [CustomerID1 CustomerID2]
  (/ (calculate-intersection CustomerID1 CustomerID2) (calculate-union CustomerID1 CustomerID2)))

(jaccard-index 2442 30878)

(defn similarity
  "Function which returns similarity with all customers"
  [CustomerID]
  (let [all-customers-list (all-customers)]
    (for [x all-customers-list :let [y (assoc x :index (jaccard-index CustomerID (:customerid x)))]]
       y)))

(similarity 2442)


;TEST SIMILARITY
(def test-list (list {:customerid 30878}
                    {:customerid 337541}
                    {:customerid 786312}
                    {:customerid 93986}
                    {:customerid 4326}
                    {:customerid 880166}))

(defn similarity-test
  [CustomerID]
    (let [all-customers-list test-list]
    (for [x all-customers-list :let [y (assoc x :index (jaccard-index CustomerID (:customerid x)))]]
       y)))

(similarity-test 2442)
;

(defn most-similar-10customers
  "Returns 10 most similar customers"
  [CustomerID]
  (take 10 (reverse (sort-by :index (similarity CustomerID)))))

(defn recommended-movies
  "Returns 10 recommended movies"
  [CustomerID]
  (let [customer10-list (most-similar-10customers CustomerID)]
  (j/query db ["select MovieID,Title
               from movies inner join ratings on movies.MovieID = ratings.MovieID
               where CustomerID in ("
               (string/join "," (into [] (for [x customer10-list] (str (:customerid x)))))")
               and MovieID not in ( select MovieID from ratings where CustomerID = ? )
               order by Rating desc limit 10" CustomerID])))


;TEST RECOMMENDATION
(defn most-similar-3customers-test
  "Returns 10 most similar customers"
  [CustomerID]
  (take 3 (reverse (sort-by :index (similarity-test CustomerID)))))
(most-similar-3customers-test 2442)

(defn recommended-movies-test
  "Returns 3 recommended movies"
  [CustomerID]
  (let [customer3-list (most-similar-3customers-test CustomerID)]
      (j/query db ["select MovieID,Title
               from movies inner join ratings on movies.MovieID = ratings.MovieID
               where CustomerID in ("
               (string/join "," (into []
                    (for [x customer3-list] (str (:customerid x))))) ") order by Rating desc limit 10"])))

(recommended-movies-test 2442)
;



