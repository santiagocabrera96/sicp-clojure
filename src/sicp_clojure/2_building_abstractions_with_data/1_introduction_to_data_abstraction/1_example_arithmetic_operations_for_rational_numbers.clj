(ns sicp-clojure.2-building-abstractions-with-data.1-introduction-to-data-abstraction.1-example-arithmetic-operations-for-rational-numbers
  (:require [sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.5-greatest-common-divisors :refer [gcd]]))

(defn make-rat [n d])
(defn numer [r])
(defn denom [r])

(defn add-rat [x y]
  (make-rat (+ (* (numer x)
                  (denom y))
               (* (numer y)
                  (denom x)))
            (* (denom x)
               (denom y))))

(defn sub-rat [x y]
  (make-rat (- (* (numer x)
                  (denom y))
               (* (numer y)
                  (denom x)))
            (* (denom x)
               (denom y))))

(defn mul-rat [x y]
  (make-rat (* (numer x)
               (numer y))
            (* (denom x)
               (denom y))))

(defn div-rat [x y]
  (make-rat (* (numer x)
               (denom y))
            (* (denom x)
               (numer y))))

(defn equal-rat [x y]
  (and (= (numer x)
          (numer y))
       (= (denom x)
          (denom y))))

(def x [1 2])
(first x)
(second x)
(rest x)
(peek x)
(pop x)
(def y [3 4])
(def z [x y])
(first (first z))
(first (second z))

(defn make-rat [n d] [n d])
(defn numer [r] (first r))
(defn denom [r] (second r))
(defn print-rat [r]
  (println (str (numer r) "/" (denom r))))

(def one-half (make-rat 1 2))
(print-rat one-half)
(def one-third (make-rat 1 3))
(print-rat (add-rat one-half one-third))
(print-rat (mul-rat one-half one-third))
(print-rat (add-rat one-third one-third))

(defn make-rat [n d]
  (let [g (gcd n d)]
    [(/ n g) (/ d g)]))

(print-rat (add-rat one-third one-third))



