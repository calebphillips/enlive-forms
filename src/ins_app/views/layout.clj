(ns ins-app.views.layout
  (:use [hiccup.core :only [html]]
        [hiccup.page-helpers :only [doctype include-css]]))

(defn nav-bar []
  [:div.navbar.navbar-fixed-top
   [:div.navbar-inner
    [:div.container-fluid
     [:a.brand {:href "#"} "Insurance for You!"]
     ]]])

(defn common [title & body]
  (html
   (doctype :html5)
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    (include-css "/stylesheets/bootstrap.css")
    ]
   [:body
    (nav-bar)
    [:div.container-fluid
     [:div.row-fluid
      [:div.span-12
       [:div.hero-unit
        body
        ]]]]]))
