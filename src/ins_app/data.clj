(ns ins-app.data
  (:require [clojure.string :as str]))

(defn is-int? [x]
  (try (Integer/parseInt x)
       (catch NumberFormatException nfe false)))

(def non-empty {:fn #(seq %) :message "Cannot be empty"})
(def integer {:fn #(is-int? %) :message "Must be a valid integer"})

(let [colors #{"Red" "Green" "Blue"}]
  (def valid-color
    {:fn #(colors %)
     :message (str "Must be one of: " (str/join ", " (seq colors)))}))

(def field-defs
  [[:first-name {:title "First Name" :validator non-empty}]
   [:last-name {:title "Last Name" :validator non-empty}]
   [:age {:title "Age" :validator integer}]
   [:favorite-color {:title "Favorite Color" :validator valid-color}]])

(defn validator-fn [field]
  (get-in field [:validator :fn]))

(defn validator-msg [field]
  (get-in field [:validator :message]))

(defn message [field]
  (when-let [f (validator-fn field)]
    (when-not (f (field :value))
      (validator-msg field))))

(defn valid? [field]
  (if-let [f (validator-fn field)]
    (f (field :value))
    true))

(defn add-messages [fields]
  (map (fn [[n m]] [n (assoc m :message (message m))])
       fields))

(defn add-values [params]
  (map (fn [[name m]] [name (assoc m :value (params name))])
          field-defs))

;; Public functions
;; Figure out how to make other fns private and still
;; test them.
(defn apply-values-to-fields [params]
  (-> params
      add-values
      add-messages))

(defn any-errors? [fields]
  (some (fn [[n m]] (not (valid? m))) fields))