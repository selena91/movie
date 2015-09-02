(ns movie.view
(:require
            [movie.db :as db]
            [clojure.string :as str]
            [hiccup.page :as hiccP]
            [ring.util.anti-forgery]
            [clojure.java.jdbc :as j]
          ))

(defn gen-page-head
  "Generates page headers"
  [title]
  [:head
   [:title title]
   (hiccP/include-js "https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js")
   (hiccP/include-css "/css/style.css")
   (hiccP/include-css "/bootstrap/css/bootstrap.min.css")
   (hiccP/include-js "/bootstrap/js/bootstrap.min.js")
   (hiccP/include-js "/ajax.js")
   (hiccP/include-js "/star-rating.js")
   (hiccP/include-css "/css/star-rating.css")
   ])

(def navbar
  "Generates a navbar"
  [:nav {:class "navbar navbar-inverse navbar-fixed-top"}
   [:div {:class "container"}
   [:div {:class "navbar-header"}
    [:button {:type "button" :class "navbar-toggle collapsed" :dat-target "#navbar"}    [:span {:class "sr-only"}]
    [:span {:class "sr-only"}]
    [:span {:class "icon-bar"}]
    [:span {:class "icon-bar"}]
    [:span {:class "icon-bar"}]]
     [:a {:class "navbar-brand" :href "#"} [:img {:src "/icon.png" :style "height: 32px; float: left; margin-right: 5px"}] " "]]
    [:div {:id "navbar" :class "collapse navbar-collapse"}
    [:ul {:class "nav navbar-nav"}
     [:li [:a {:class "btn btn-primary" :href "/home"} "Home"]]
     [:li [:a {:class "btn btn-primary" :href "/recommendation"} "Recommended movies "]]
     [:li [:a {:class "btn btn-primary" :href "/movies"} "All movies "]]
     [:li [:a {:class "btn btn-primary" :href "/"} "Log out "]]]]]])


(defn home-page
  "Function which generates home-page"
  []
  (hiccP/html5
   (gen-page-head "Home")
   navbar
   [:h1 "Home"]
   [:div {:class "jumbotron"}
    [:h2" Wellcome"]
    [:p " to the online movie club."]])
  )


(defn login-page
  "Function which generates login-page"
  []
  (hiccP/html5
   (gen-page-head "Login")
   [:div {:class "col-md-4"}]
   [:div {:class "login  col-md-4"}
    [:form {:method "POST" :action "/"}
     [:h2 "Login"]
     [:div {:class "form-group has-feedback"}
     [:label {:class "control-label"}]
     [:input {:type "text" :name "CustomerID" :class "form-control " :required "required" :placeholder "User Name"}]
     [:i {:class "glyphicon glyphicon-user form-control-feedback"}]]
     [:div {:class "form-group has-feedback"}
     [:input {:type "password" :name "Password" :class "form-control" :placeholder "Password"}]
     [:i {:class "glyphicon glyphicon-movk form-control-feedback "}]]
     [:form {:action "/"}
     [:button.btn.btn-info.submit {:style "margin-bottom: 10px" :id "btn-login"} "Login" ]]]
     [:p [:i "Need an account? Register now!"]]
     [:form {:action "/register"}
     [:button.btn.btn-primary.submit "Register" ]]
     [:div {:class "col-md-4"}]]))

(defn register-page
  "Function which generates register-page"
  []
  (hiccP/html5
   (gen-page-head "Register")
   [:div {:class "col-md-4"}]
   [:div {:class "login col-md-4"}
   [:h1 "Create an account"]
   [:div
   [:form {:action "/register" :method "POST"}
     [:input {:type "text" :class "form-control" :id "CustomerID" :name "CustomerID" :placeholder "Username"}]
     [:input {:type "text" :class "form-control" :id "Password" :name "Password" :placeholder "Password"}]
     [:button.btn.btn-primary.submit {:style "margin-top: 10px"} "Register "]
    ]]]))

(defn all-movies-list
  "Function which returns the list of all movies which customer can rate"
  []
  (let [all-movies (db/movie-list)]
    (hiccP/html5
     (gen-page-head "Movies")
     navbar
     [:h1 "All movies"]
     [:div {:class "col-md-4" :style "margin-bottom:20px"}
     [:label {:class "control-label"}]
     [:input {:type "text" :name "Search" :class "form-control " :placeholder "Search"}]]
     [:div {:class "container"}
     [:table {:class "table"}
      [:tr [:th "MovieID"] [:th "Year of release"] [:th "Title"] [:th ""]]
      (for [mov all-movies]
        [:tr [:td (:movieid mov)] [:td (:yearofrelease mov)] [:td (:title mov)] [:td [:input {:class "rating" :type "number" :min "1" :max "5" :step "1"}] ]])]])))

(defn recommended-movies
  "Function which returns the list of top 10 recommended movies
   or top 10 rated movies if user didn't give any rate"
  [CustomerID]
  (let [countRatings (db/count-customer-rating CustomerID)]
    (hiccP/html5
     (gen-page-head "Recommendation")
     (if (> (:total countRatings) 0)
      ([:h1 "Top 10 recommended movies"])
      (let [movie-list (db/top10-movies)]
         [:h1 "Top 10 movies"]
         [:div {:class "col-md-4" :style "margin-bottom:20px"}
         [:label {:class "control-label"}]]
         [:div {:class "container"}
         [:table {:class "table"}
          [:tr [:th "MovieID"] [:th "Year of release"] [:th "Title"] [:th ""]]
          (for [mov movie-list]
            [:tr [:td (:movieid mov)] [:td (:yearofrelease mov)] [:td (:title mov)] [:td [:input {:class "rating" :type "number" :min "1" :max "5" :step "1"}] ]])]])
      ))
   )
)
