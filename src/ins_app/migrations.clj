(ns ins-app.migrations
  (:require [clojure.java.jdbc :as sql]))

(def url "postgresql://localhost:5432/ins-app")

(defn create-applications []
  (sql/with-connection url
    (sql/create-table :applications
                      [:id :serial "PRIMARY KEY"]
                      [:first_name :varchar "NOT NULL"]
                      [:last_name :varchar "NOT NULL"]
                      [:age :integer "NOT NULL"]
                      [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn -main []
  (print "Creating database structure...") (flush)
  (create-applications)
  (println " done"))