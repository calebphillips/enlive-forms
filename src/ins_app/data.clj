(ns ins-app.data)

(defn is-int? [x]
  (try (Integer/parseInt x)
       (catch NumberFormatException nfe false)))

(def non-empty [#(seq %) "Cannot be empty"])
(def integer [#(is-int? %) "Must be a valid integer"])

(def field-defs
  {:first-name {:title "First Name" :validator non-empty}
   :last-name {:title "Last Name" :validator non-empty}
   :age {:title "Age" :validator integer}
   :favorite-color {:title "Favorite Color" :validator non-empty}
   :sandwich {:title "Sandwich"}})


(defn add-values [params]
  (apply hash-map
         (mapcat (fn [[name m]] [name (assoc m :value (params name))])
                 field-defs)))

;; WHOA! Awkwardness around validators
;; Map destructing works with missing keys (returns nil), maybe thats
;; a better fit
(defn message [field]
  (when (field :validator)
    (let [[f m] (field :validator)]
      (when-not (f (field :value))
        m))))

(defn valid? [field]
  (if (field :validator)
    (let [[f msg] (field :validator)]
      (f (field :value)))
    true))

(defn add-messages [fields]
  (apply hash-map
         (mapcat (fn [[n m]] [n (assoc m :message (message m))]) fields)))

(defn apply-values-to-fields [params]
  (-> params
      add-values
      add-messages))

(defn any-errors? [fields]
  (some (fn [[n m]] (not (valid? m))) fields))