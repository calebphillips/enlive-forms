(ns ins-app.test.data
  (:use [ins-app.data])
  (:use [clojure.test]))

(defn always-true [v] true)
(defn always-false [v] false)

(def v1 {:fn always-true :message "Fred"})
(def v2 {:fn always-false :message "Joe"})

(def test-defs [[:f1 {:title "F1" :validator v1}]
                [:f2 {:title "F2" :validator v2}]])

(deftest test-is-int?
  (is (is-int? "1"))
  (is (is-int? "456"))
  (is (not (is-int? "abc"))))

(deftest test-add-values
  (binding [field-defs test-defs]
    (is (= [[:f1 {:title "F1" :validator v1 :value nil}]
            [:f2 {:title "F2" :validator v2 :value nil}]]
           (add-values {})))))

(deftest test-message
  (is (= "Joe"
         (message {:validator v2})))
  (is (not (message {:value "Hi"}))))

(deftest test-add-messages
  (is (= [[:f1 {:validator v2 :value "" :message "Joe"}]]
         (add-messages [[:f1 {:validator v2 :value ""}]])))
  (is (= [[:f1 {:value "" :message nil}]]
         (add-messages [[:f1 {:value ""}]]))))

(deftest test-apply-values
  (binding [field-defs test-defs]
    (is (= [[:f1 {:title "F1" :validator v1 :value "v1" :message nil}]
            [:f2 {:title "F2" :validator v2 :value "v2" :message "Joe"}]]
           (apply-values-to-fields {:f1 "v1" :f2 "v2"}))))

  (deftest test-any-errors?
    (is (not (any-errors? {:f1 {:validator v1}})))
    (is (any-errors? {:f1 {:validator v1}
                      :f2 {:validator v2}}))
    (is (not (any-errors? {:f1 {}})))
    ))

(deftest test-valid?
  (is (valid? {:validator v1}))
  (is (not (valid? {:validator v2})))
  (is (valid? {:value "This"})))