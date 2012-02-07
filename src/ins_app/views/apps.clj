(ns ins-app.views.apps
  (:use [hiccup.core :only [html h]]
        [hiccup.page-helpers :only [doctype]])
  (:require [ins-app.views.layout :as layout]
            [hiccup.form-helpers :as fh]))

(defn text-field [id label]
  (seq [(fh/label id label)
        (fh/text-field id)]))

(defn app-form []
  (fh/form-to [:post "/"]
           (text-field "first-name" "First Name")
           (text-field "last-name" "Last Name")
           [:input {:type "submit" :class "btn" :value "Apply"}]))

(defn new-form []
  (layout/common "Insurance App"
                 (app-form)))

(defn new-form-with-errors [errors]
  (layout/common "Insurance App"
                 [:h1 "What!"]
                 (app-form)))

(defn success []
  (layout/common "Insurance App"
                 [:h1 "You did it! Stay Strong!"]))