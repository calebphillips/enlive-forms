(ns ins-app.data)

(def non-empty [#(seq %) "Cannot be empty"])
(def integer [#(try (Integer/parseInt %)
                    (catch NumberFormatException nfe false)) "Must be a valid integer"])

(def field-defs
  {:first-name {:title "First Name" :validator non-empty}
   :last-name {:title "Last Name" :validator non-empty}
   :age {:title "Age" :validator integer}
   :favorite-color {:title "Favorite Color" :validator non-empty}})


;; (def fields-with-values (apply-values data/field-defs params))
;; (def errors? (any-errors? fields-with-values))
(defn add-values [params]
  (apply hash-map
         (mapcat (fn [[name m]] [name (assoc m :value (params name))])
                 field-defs)))

(defn message [field]
  (let [[f m] (field :validator)]
    (when-not (f (field :value))
      m)))

(defn add-messages [fields]
  (let [with-validators (filter (fn [[n m]] (m :validator)) fields)]
    (apply hash-map
           (mapcat (fn [[n m]] [n (assoc m :message (message m))]) with-validators))))

(defn apply-values-to-fields [params]
  (-> params
      add-values
      add-messages))

;; Too dang complicated, extract something!
(defn any-errors? [fields]
  (let [with-validators (filter (fn [[n m]] (m :validator)) fields)]
    (some (fn [[n m]]  (not
                       ((first (m :validator)) (m :value))))
          with-validators)))