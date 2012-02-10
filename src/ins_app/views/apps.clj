(ns ins-app.views.apps
  (:use [hiccup.core :only [html h]]
        [hiccup.page-helpers :only [doctype]])
  (:require [ins-app.views.layout :as layout]
            [hiccup.form-helpers :as fh]
            [clojure.zip :as zip]
            [net.cgrand.enlive-html :as en]))

(en/deftemplate entire-form "ins_app/views/app.html" [])

(en/deftemplate entire-form-we "ins_app/views/app.html" [errors]
  [:div.control-group]
  (fn [nd]
    (let [error-groups (map #(str (name (first %)) "-group") errors)
          cls (get-in nd [:attrs :id])]
      (if (some #{cls} error-groups)
        ((en/add-class "error") nd)
        nd))))

(defn new-form []
  (apply str (entire-form)))

(defn new-form-with-errors [errors]
  (apply str (entire-form-we errors)))

(defn success []
  (layout/common "Insurance App"
                 [:h1 "You did it! Stay Strong!"]))
