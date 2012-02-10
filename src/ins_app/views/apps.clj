(ns ins-app.views.apps
  (:use [hiccup.core :only [html h]]
        [hiccup.page-helpers :only [doctype]])
  (:require [ins-app.views.layout :as layout]
            [hiccup.form-helpers :as fh]
            [clojure.zip :as zip]
            [net.cgrand.enlive-html :as en]))

(defn text-field [id label]
  [:div.control-group
   [:label.control-label {:for id} label]
   [:div.controls
    (fh/text-field id)]])

(defn app-form []
  (fh/form-to [:post "/"]
           (text-field "first-name" "First Name")
           (text-field "last-name" "Last Name")
           [:input {:type "submit" :class "btn" :value "Apply"}]))

(en/deftemplate entire-form "ins_app/views/app.html" [])

(defn new-form []
  (apply str (entire-form)))

(defn add-error-class-to-controls [form]
  (loop [loc (zip/vector-zip form)]
    (if (zip/end? loc)
      (zip/root loc)
      (recur
       (zip/next
        (if (= (zip/node loc) :div.control-group)
          (zip/replace loc :div.control-group.error)
          loc))))))

(defn new-form-with-errors [errors]
  (layout/common "Insurance App"
                 (add-error-class-to-controls
                  (app-form))))

(defn success []
  (layout/common "Insurance App"
                 [:h1 "You did it! Stay Strong!"]))
