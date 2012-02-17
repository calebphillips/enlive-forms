(ns ins-app.view
  (:require [net.cgrand.enlive-html :as en]))

(def form-html "ins_app/form.html")

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

(defn titles+fields [titles fields]
  (map (fn [[n t]] (merge (hash-map :name n :title t)
                         (fields n))) titles))

(en/deftemplate form-template form-html [fields]
  [:fieldset] (en/content
               (map field-snip
                    (titles+fields field-titles fields))))

(defn new-form
  ([] (new-form {}))
  ([errors] (apply str (form-template errors))))

(defn success []
  "You did it!")
