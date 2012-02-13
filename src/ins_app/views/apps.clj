(ns ins-app.views.apps
  (:use [hiccup.core :only [html h]]
        [hiccup.page-helpers :only [doctype]])
  (:require [ins-app.views.layout :as layout]
            [hiccup.form-helpers :as fh]
            [clojure.zip :as zip]
            [net.cgrand.enlive-html :as en]))

(def html-page "ins_app/views/app.html")

(en/defsnippet field-snip html-page [:.control-group]
  [[field-name field-title error?]]
  [:.control-group] (fn [nd]
                      ((en/set-attr :id (str (name field-name) "-group"))
                       (if error?
                         ((en/add-class "error") nd)
                         nd)))
  [:label] (en/do->
            (en/content field-title)
            (en/set-attr :for (name field-name)))
  [:input] (en/do->
            (en/set-attr :id (name field-name))
            (en/set-attr :name (name field-name)))
  [:span.help-inline] (en/content "")
  )

(def fields [[:first-name "First Name"]
             [:last-name "Last Name"]
             [:age "Age"]])

(en/deftemplate form-template "ins_app/views/app.html" [errors]
  [:fieldset] (en/content
               (map #(field-snip (conj % ((first %) errors)))
                    fields)))

(defn new-form []
  (apply str (form-template {})))

(defn new-form-with-errors [errors]
  (apply str (form-template errors)))

(defn success []
  (layout/common "Insurance App"
                 [:h1 "You did it! Stay Strong!"]))
