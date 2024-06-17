(ns sicp-clojure.2-building-abstractions-with-data.1-introduction-to-data-abstraction.2-abstraction-barriers
  (:require [sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.5-greatest-common-divisors :refer [gcd]]))

(defn make-rat [n d] [n d])
(defn numer [x]
  (let [g (gcd (first x) (second x))]
    (/ (first x)
       g)))
(defn denom [x]
  (let [g (gcd (first x) (second x))]
    (/ (second x)
       g)))
