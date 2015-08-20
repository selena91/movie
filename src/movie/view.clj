(ns movie.view
(:require
            ;[cocktails.db :as db]
            [clojure.string :as str]
            [hiccup.page :as hiccP]
            ;[net.cgrand.enlive-html :as html]
            [ring.util.anti-forgery]
            [clojure.java.jdbc :as j]
          ))

(use 'ring.util.anti-forgery)
(use 'selmer.parser)

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
   (hiccP/include-js "/ajaxTest.js")])

(def navbar
  "Generates a navbar "
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
     [:li [:a {:class "btn btn-primary" :href "/"} "Home"]]
     [:li [:a {:class "btn btn-primary" :href "/all-cocktails"} "Top 10 movies "]]
     [:li [:a {:class "btn btn-primary" :href "/similar-recipes"} "Rate "]]
     [:li [:a {:class "btn btn-primary" :href "/search-recipes"} "Search "]]
     [:li [:a {:class "btn btn-primary" :href "/login"} "Log in "]]
     [:li [:a {:class "btn btn-primary" :href "/register"} "Register "]]]]]])


(defn home-page
  "Function which generates home-page"
  []
  (hiccP/html5
   (gen-page-head "Home")
   navbar
   [:h1 "Home"]
   [:div {:class "jumbotron"}
    [:h2" Wellcome"]
    [:p " to the online movie club."]]))


(defn login-page
  "Function which generates login-page"
  []
  (hiccP/html5
   (gen-page-head "login")
   [:div {:class "col-md-4"}]
   [:div {:class "login  col-md-4"}
     [:h2 "Login"]
     [:div {:class "form-group has-feedback"}
     [:label {:class "control-label"}]
     [:input {:type "text" :class "form-control " :placeholder "User Name"}]
     [:i {:class "glyphicon glyphicon-user form-control-feedback"}]]
     [:div {:class "form-group has-feedback"}
     [:input {:type "password" :class "form-control" :placeholder "Password"}]
     [:i {:class "glyphicon glyphicon-lock form-control-feedback "}]]
     [:form {:action "/"}
     [:button.btn.btn-info.submit {:style "margin-bottom: 10px" :id "btn-login"} "Login" ]]
 ;;  [:button  {:type "button" :class "btn-success btn-md" :method="post" :enctype="/" :id "btn-login"}  "Login"]
     [:p [:i "Need an account? Register now!"]]
     [:form {:action "/register"}
     [:button.btn.btn-primary.submit {:id "btn-register"} "Register" ]]
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
   [:form {:action "register-user" :method "post"}
     [:input {:type "text" :class "form-control" :id "username" :name "username" :placeholder "Username"}]
     [:input {:type "text" :class "form-control" :id "password" :name "password" :placeholder "Password"}]
     [:button.btn.btn-primary.submit {:style "margin-top: 10px"} "Register "]
    ]]]))
