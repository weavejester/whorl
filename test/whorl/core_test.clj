(ns whorl.core-test
  (:use clojure.test
        whorl.core))

(deftest test-sorted
  (let [x (sorted {:z '(2 1) :b 3 :a #{5 4} :c [7 6]})]
    (is (= (pr-str x)
           "{:a #{4 5}, :b 3, :c [7 6], :z (2 1)}"))))

(deftest test-fingerprint
  (are [x y] (= (fingerprint x) (fingerprint y))
    {:b 1 :a 2} {:a 2 :b 1}
    (array-map :b 1 :a 2) (hash-map :a 2 :b 1)
    #{3 2 1} #{1 3 2}
    [1 3 2] [1 3 2]
    {:x {:b 1 :a 2}} {:x {:a 2 :b 1}}))
