(ns ins-app.fields
  )

(defn new-field [value message]
  {:value value :message message})

(defn has-error? [f]
  (f :message))
