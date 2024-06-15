(ns sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.6-example-testing-for-primality)

(defn square [x] (* x x))
(defn divides? [a b]
  (= 0 (rem b a)))
(defn find-divisor [n test-divisor]
  (cond (> (square test-divisor) n) n
        (divides? test-divisor n) test-divisor
        :else (recur n (+ test-divisor 1))))
(defn smallest-divisor [n]
  (find-divisor n 2))

(defn prime? [n]
  (= n (smallest-divisor n)))

(defn expmod [base exp m]
  (cond (= exp 0) 1
        (even? exp) (rem (square (expmod base (/ exp 2) m))
                         m)
        :else (rem (* base (expmod base (- exp 1) m))
                   m)))

(defn fermat-test [n]
  (let [try-it (fn [a]
                 (= (expmod a n n) a))]
    (try-it (+ 1 (rand-int (- n 1))))))

(defn fast-prime? [n times]
  (cond (= times 0) true
        (fermat-test n) (fast-prime? n (- times 1))
        :else false))
