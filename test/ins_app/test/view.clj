(ns ins-app.test.view
  (:use [ins-app.view])
  (:use [clojure.test]))

(deftest squash-name-into-fields
  (is (= [{:name :f1 :title "F1" :value :v1 :message :m1}]
         (squash-name-in {:f1 {:title "F1" :value :v1 :message :m1}}))))


