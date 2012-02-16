(ns ins-app.test.view
  (:use [ins-app.view])
  (:use [clojure.test]))

(deftest test-value-and-message
  (is (= ["v1" "m1"]
         (value-and-message {:value "v1" :message "m1"}))))