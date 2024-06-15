(ns sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.8-procedures-as-black-box-abstractions
  (:require clojure.math
            [sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.6-conditional-expressions-and-predicates :refer [abs]]
            [sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.7-example-square-roots-by-newton-method :refer [average]]))

(defn square [x] (* x x))
(defn double [x] (+ x x))
(defn square [x]
  (clojure.math/exp (double (clojure.math/log x))))

(defn square [x] (* x x))
(defn square [y] (* y y))

(defn good-enough? [guess x]
  (< (abs (- (square guess) x)) 0.001))

(defn improve [guess x]
  (average guess (/ x guess)))
(defn sqrt-iter [guess x]
  (if (good-enough? guess x)
    guess
    (recur (improve guess x) x)))
(defn sqrt [x]
  (sqrt-iter 1.0 x))


(defn sqrt [x]
  (let [good-enough? (fn [guess x]
                       (< (abs (- (square guess) x)) 0.001))
        improve (fn [guess x]
                  (average guess (/ x guess)))
        sqrt-iter (fn [guess x]
                    (if (good-enough? guess x)
                      guess
                      (recur (improve guess x) x)))]
    (sqrt-iter 1.0 x)))

(defn sqrt [x]
  (let [good-enough? (fn [guess]
                       (< (abs (- (square guess) x)) 0.001))
        improve (fn [guess]
                  (average guess (/ x guess)))
        sqrt-iter (fn [guess]
                    (if (good-enough? guess)
                      guess
                      (recur (improve guess))))]
    (sqrt-iter 1.0)))
