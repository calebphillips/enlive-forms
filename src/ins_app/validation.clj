(ns ins-app.validation)

(def non-empty [#(seq %) "Cannot be empty"])
(def integer [#(try (Integer/parseInt %)
                    (catch NumberFormatException nfe false)) "Must be a valid integer"])
(def validators
  {:first-name non-empty
   :last-name non-empty
   :age integer})

(defn validate-one [[f v]]
  (when-let [[valid? msg] (validators f)]
    (if (not (valid? v))
      {f [v msg]})))

(defn validate [params]
  (let [errors (filter seq (map validate-one params))]
    (reduce merge errors)))

(defn params->msg-vec [params]
  (reduce (fn [m [k v]] (assoc m k [v nil]))
          {}
          params))

;; I think it will be clearer if the params get transformed first
;; and then validate just updates the appropriate messages
;; Right now there are 2 functions generating the same data format
(defn add-messages [params]
  (let [errors (validate params)
        any-errors? (seq errors)]
    [any-errors?
     (merge (params->msg-vec params)
            (validate params))]))