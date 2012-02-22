(ns ins-app.view
  (:require [net.cgrand.enlive-html :as en]))

(def form-html "ins_app/form.html")
(def layout-html "ins_app/layout.html")

(en/defsnippet field-snip form-html [:.control-group]
  [{field-name :name field-title :title value :value message :message}]
  [:.control-group] (fn [nd]
                      ((en/set-attr :id (str (name field-name) "-group"))
                       (if message
                         ((en/add-class "error") nd)
                         nd)))
  [:label] (en/do->
            (en/content field-title)
            (en/set-attr :for (name field-name)))
  [:input] (en/do->
            (en/set-attr :id (name field-name))
            (en/set-attr :name (name field-name))
            (en/set-attr :value value))
  [:span.help-inline] (en/content message))

(defn squash-name-in [fields]
  (map (fn [[n m]] (assoc m :name n)) fields))

(en/defsnippet form-template form-html [:#the-form] [fields]
  [:fieldset] (en/content
               (map field-snip
                    (squash-name-in fields))))

(en/deftemplate layout layout-html [title & body]
  [:h1] (en/content title)
  [:#content] (en/content body))

(defn new-form [fields]
  (apply str (layout "Home" (form-template fields))))

(defn about []
  (apply str (layout "About")))

(defn contact []
  (apply str (layout "Contact")))

(defn success []
  (apply str (layout "Home" "Application successfully submitted.")))
