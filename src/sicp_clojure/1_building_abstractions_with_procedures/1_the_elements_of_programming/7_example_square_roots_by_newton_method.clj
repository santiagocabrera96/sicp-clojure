(ns sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.7-example-square-roots-by-newton-method
  (:require [sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.4-compound-procedures :refer [square]]
            [sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.6-conditional-expressions-and-predicates :refer [abs]]))

(defn good-enough? [guess x]
  (< (abs (- (square guess) x)) 0.001))

(defn average [x y]
  (/ (+ x y) 2))

(defn improve [guess x]
  (average guess (/ x guess)))

(defn sqrt-iter [guess x]
  (if (good-enough? guess x)
    guess
    (recur (improve guess x)
           x)))

(defn sqrt [x]
  (sqrt-iter 1.0 x))

(sqrt 9)
(sqrt (+ 100 37))
(sqrt (+ (sqrt 2) (sqrt 3)))
(square (sqrt 1000))5


