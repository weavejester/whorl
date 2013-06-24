(ns whorl.core
  (:require [clojure.walk :as walk]
            [pandect.core :as digest]))

(defn sorted
  "Convert a data structure into an equivalent with a fixed ordering."
  [data]
  (walk/postwalk
   (fn [x]
     (cond
      (map? x) (apply sorted-map (apply concat x))
      (set? x) (apply sorted-set x)
      :else    x))
   data))

(defn fingerprint
  "Return a unique ID for the data structure. Equivalent data structures will
  always produce the same ID."
  [data]
  (digest/sha1 (pr-str (sorted data))))
