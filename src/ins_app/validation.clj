(ns ins-app.validation
  (:require [ins-app.fields :as f]))

;; Rename this ns to data?

(def non-empty [#(seq %) "Cannot be empty"])
(def integer [#(try (Integer/parseInt %)
                    (catch NumberFormatException nfe false)) "Must be a valid integer"])

(def validators
  {:first-name non-empty
   :last-name non-empty
   :age integer
   :favorite-color non-empty})

(defn message-for [fld v validators]
  (when-let [[valid? msg] (validators fld)]
    (if (not (valid? v))
      msg)))

(defn messages [params validators]
  (let [get-msg #(message-for %1 %2 validators)
        fld->msg #(assoc %1 %2 (get-msg %2 %3))]
    (reduce (fn [m [fld v]] (fld->msg m fld v)) {} params)))

(defn values-and-messages [params]
  (let [msgs (messages params validators)
        field-map #(hash-map % (f/new-field (params %) (msgs %)))]
    (reduce merge
            (map field-map (keys params)))))

(defn params->fields [params]
  (values-and-messages params))

(defn any-errors? [fields]
  (seq (filter (fn [[_ fld]] (f/has-error? fld)) fields)))
