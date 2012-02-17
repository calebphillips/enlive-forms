(ns ins-app.test.view
  (:use [ins-app.view])
  (:use [clojure.test]))


(deftest merging-titles-with-fields
  (is (= [{:name :f1 :title "F 1" :value :v1 :message :m1}]
         (titles+fields
           {:f1 "F 1"}
           {:f1 {:value :v1 :message :m1}}))))


