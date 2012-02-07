(ns ins-app.views.apps
  (:use [hiccup.core :only [html h]]
        [hiccup.page-helpers :only [doctype]]
        [hiccup.form-helpers :only [form-to label text-field submit-button]])
  (:require [ins-app.views.layout :as layout]))

(defn app-form []
  [:form {:method "POST", :action "/" }
   (label "first-name" "First Name")
   (text-field "first-name")
   (label "last-name" "Last Name")
   (text-field "last-name")
   [:input {:type "submit" :class "btn-primary" :value "Apply"}]
   ]
  )

(defn new-form []
  (layout/common "Insurance App"
                 (app-form)))