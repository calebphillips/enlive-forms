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

(def field-titles {:first-name "First Name"
                   :last-name "Last Name"
                   :age "Age"})

;; Smashing any encapsulation in the field here.
;; Should I retain the field and just assoc title into it? 
(defn titles+fields [titles fields]
  (map (fn [[n t]] (merge (hash-map :name n :title t)
                         (fields n))) titles))

(en/defsnippet form-template form-html [:#the-form] [fields]
  [:fieldset] (en/content
               (map field-snip
                    (titles+fields field-titles fields))))

(en/deftemplate layout layout-html [title body]
  [:h1] (en/content title)
  [:#content] (en/content body))

(defn new-form
  ([] (new-form {}))
  ([errors] (apply str (layout "Home" (form-template errors)))))

(defn about []
  (apply str (layout "About" "")))

(defn contact []
  (apply str (layout "Contact" "")))

(defn success []
  (apply str (layout "Home" "Application successfully submitted.")))
