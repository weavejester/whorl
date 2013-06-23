(ns whorl.core-test
  (:use clojure.test
        whorl.core))

(deftest test-sorted
  (let [x (sorted {:z '(2 1) :b 3 :a #{5 4} :c [7 6]})]
    (is (= (pr-str x)
           "{:a #{4 5}, :b 3, :c [7 6], :z (2 1)}"))))
