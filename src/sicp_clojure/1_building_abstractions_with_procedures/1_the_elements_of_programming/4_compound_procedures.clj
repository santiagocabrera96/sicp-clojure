(ns sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.4-compound-procedures)

(defn square [x] (* x x))

(square 21)
(square (+ 2 5))
(square (square 3))

(defn sum-of-squares [x y]
  (+ (square x) (square y)))
(sum-of-squares 3 4)

(defn f [a]
  (sum-of-squares (+ a 1) (* a 2)))
(f 5)
