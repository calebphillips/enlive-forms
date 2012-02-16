(ns ins-app.test.validation
  (:use [ins-app.validation])
  (:use [clojure.test]))

(defn always-false [& args] false)

(deftest test-message-for
  (is (= "Invalid!"
         (message-for :f1 :v1 {:f1 [always-false "Invalid!"]})))
  (is (= nil
         (message-for :f1 :v1 {}))))

(deftest test-messages
  (is (= {:f1 "Invalid!"}
         (messages {:f1 :v1} {:f1 [always-false "Invalid!"]}))))

(deftest test-values-and-messages
  (binding [validators {:f2 [always-false "Invalid!"]}]
    (is (= {:f1 {:value :v1 :message nil}
            :f2 {:value :v2 :message "Invalid!"}}
           (values-and-messages {:f1 :v1 :f2 :v2})))))

(deftest test-any-errors
  (is (any-errors? {:f1 {:value "asd" :message nil} :f2 {:value "" :message "Invalid"}}))
  (is (not (any-errors? {:f1 {:value "" :message nil} :f2 {:value "" :message nil}})))
  )

