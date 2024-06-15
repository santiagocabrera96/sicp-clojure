(ns sicp-clojure.1-building-abstractions-with-procedures.1-the-elements-of-programming.6-conditional-expressions-and-predicates)

(defn abs [x]
  (cond (> x 0) x
        (= x 0) 0
        (< x 0) (- x)))
(defn abs [x]
  (cond (< x 0) (- x)
        :else x))
(defn abs [x]
  (if (< x 0)
    (- x)
    x))

(if true
  (println true)
  (println false))

(and false (println true))
(or true (println false))
(not true)

(let [x (rand-int 100)]
  (and (> x 5) (< x 10)))

(defn >= [x y]
  (or (> x y) (= x y)))

(defn >= [x y]
  (not (< x y)))

