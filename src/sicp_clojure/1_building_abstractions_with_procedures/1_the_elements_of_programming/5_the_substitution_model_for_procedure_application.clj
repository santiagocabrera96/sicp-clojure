(ns sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.5-the-substitution-model-for-procedure-application
  (:require [sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.4-compound-procedures :refer [square sum-of-squares f]]))


;Applicative order
(f 5)
(sum-of-squares (+ 5 1) (* 5 2))
(sum-of-squares 6 10)
(+ (square 6) (square 10))
(+ (* 6 6) (* 10 10))
(+ 36 100)

;Normal order
(f 5)
(sum-of-squares (+ 5 1) (* 5 2))
(+ (square (+ 5 1)) (square (* 5 2)))
(+ (* (+ 5 1) (+ 5 1)) (* (* 5 2) (* 5 2)))
(+ (* 6 6) (* 10 10))
(+ 36 100)
