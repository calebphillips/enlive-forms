(ns ins-app.view
  (:require [net.cgrand.enlive-html :as en]))

(def layout-html "ins_app/layout.html")

(en/defsnippet field-snip layout-html [:.control-group]
  [field-name {field-title :title value :value message :message}]
  [:.control-group] (en/add-class (if message "error" ""))
  [:label] (en/do->
            (en/content field-title)
            (en/set-attr :for (name field-name)))
  [:input] (en/do->
            (en/set-attr :id (name field-name))
            (en/set-attr :name (name field-name))
            (en/set-attr :value value))
  [:span.help-inline] (en/content message))

(en/defsnippet form-template layout-html [:#the-form] [fields]
  [:fieldset] (en/content
               (map #(apply field-snip %) fields)))

(en/deftemplate layout layout-html [title & body]
  [:h1] (en/content title)
  [:#content] (en/content body))

(defn new-form [fields]
  (layout "Home"
          (form-template fields)))

(defn about []
  (layout "About"))

(defn contact []
  (layout "Contact"))

(defn success []
  (layout "Success!"
          "Application successfully submitted."))
