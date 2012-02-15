(ns ins-app.test.validation
  (:use [ins-app.validation])
  (:use [clojure.test]))

(deftest test-validate-one
  (is (= {:field ["value" "Wrong"]}
         (validate-one [:field "value"] {:field [(fn [v] false) "Wrong"]}))))
