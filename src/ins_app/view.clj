(ns ins-app.view
  (:require [net.cgrand.enlive-html :as en]))

(def form-html "ins_app/form.html")

(en/defsnippet field-snip form-html [:.control-group]
  [[field-name field-title value message]]
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
  [:span.help-inline] (en/content message)
  )

(def fields [[:first-name "First Name"]
             [:last-name "Last Name"]
             [:age "Age"]])

(defn value-and-message [{:keys [value message]}] [value message])

(en/deftemplate form-template form-html [errors]
  [:fieldset] (en/content
               (map #(field-snip (concat % (value-and-message ((first %) errors))))
                    fields)))

(defn new-form []
  (apply str (form-template {})))

(defn new-form-with-errors [errors]
  (apply str (form-template errors)))

(defn success []
  "You did it!")
