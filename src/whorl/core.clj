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

(def canonize-edn #'sorted)

;; TODO: verify that the types are JSONable in some sense?
(defn canonize-json
  [data]
  ;; prewalk is necessary rather than postwalk so that
  ;; we can detect MapEntry. With postwalk they are vanilla
  ;; vectors by the time the function sees them.
  (sorted
   (walk/prewalk
    (fn [x]
      (cond
       (and (vector? x)
            (not (instance? clojure.lang.MapEntry x)))
       (seq x)

       (keyword? x) (-> x (str) (subs 1))

       :else x))
    data)))

(defn- data-sha1
  [data]
  (digest/sha1 (pr-str data)))

(defn- weak-hash-map []
  (java.util.Collections/synchronizedMap (java.util.WeakHashMap.)))

(defn cached-fingerprinter
  "Return a unique ID for the data structure. Equivalent data structures will
  always produce the same ID."
  [canonizer]
  (let [cache (weak-hash-map)]
    (fn [data]
      (or (.get cache data)
          (let [print (data-sha1 (canonizer data))]
            (.put cache data print)
            print)))))

(def fingerprint
  "Return a unique ID for the data structure. Equivalent data structures will
  always produce the same ID."
  (cached-fingerprinter canonize-edn))

(def fingerprint-json
  "Return a unique ID for the JSON version of the data structure. Keywords
   are treated the same as strings, and vectors are treated the same as
   lists."
  (cached-fingerprinter canonize-json))
