(ns whorl.core
  (:require [clojure.walk :as walk]))

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
