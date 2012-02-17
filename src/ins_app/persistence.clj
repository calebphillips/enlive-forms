(ns ins-app.persistence
  (:require [korma.core :as k]
            [korma.db :as db]))

(db/defdb app-db (db/postgres {:db "ins-app"}))

(k/defentity applications)

(defn dash->under [key]
  (keyword (.replace (name key), "-", "_")))

(defn transform-fields [fields]
  (reduce merge
          (map (fn [[k {v :value}]] {(dash->under k) v}) fields)))

(defn save-application [fields]
  (k/insert applications
            (k/values (transform-fields fields))))