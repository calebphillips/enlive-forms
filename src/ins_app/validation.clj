(ns ins-app.validation)

(def non-empty [#(seq %) "Cannot be empty"])
(def integer [#(try (Integer/parseInt %)
                    (catch NumberFormatException nfe false)) "Must be a valid integer"])
(def validators
  {:first-name non-empty
   :last-name non-empty
   :age integer})

(defn message-for [f v validators]
  (when-let [[valid? msg] (validators f)]
    (if (not (valid? v))
      msg)))

(defn messages [ps validators]
  (let [get-msg #(message-for %1 %2 validators)]
    (reduce (fn [m [f v]] (assoc m f (get-msg f v))) {} ps)))


(defn values-and-messages [params]
  (let [ms (messages params validators)]
    (reduce merge
            (for [f (keys params)]
              {f {:value (params f) :message (ms f)}}))))

(defn any-errors? [vms]
  (seq (filter (fn [[k v]] (not (nil? (v :message)))) vms)))

;; Alot of the param handling may need to be extracted to
;; its own namespace, then we could just return the vms list
;; and clients could filter on params/has-error?
(defn add-messages [params]
  (let [vms (values-and-messages params)]
    [(any-errors? vms) vms]))