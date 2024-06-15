(ns sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.1-linear-recursion-and-iteration)

(defn factorial [n]
  (if (= n 1)
    1
    (* n (factorial (- n 1)))))

(factorial 6)

(defn fact-iter [product counter max-count]
  (if (> counter max-count)
    product
    (recur (* counter product)
               (+ counter 1)
               max-count)))

(defn factorial [n]
  (fact-iter 1 1 n))
(factorial 6)


