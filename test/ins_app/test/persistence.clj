(ns ins-app.test.persistence
  (:use [ins-app.persistence])
  (:use [clojure.test]))

(deftest test-dash->under
  (is (= :first_name
         (dash->under :first-name))))

(deftest test-transform-fields
  (is (= {:f1 "v1" :f2 "v2"}
         (transform-fields {:f1 {:value "v1" :message "Fred"} :f2 {:value "v2"}}))))