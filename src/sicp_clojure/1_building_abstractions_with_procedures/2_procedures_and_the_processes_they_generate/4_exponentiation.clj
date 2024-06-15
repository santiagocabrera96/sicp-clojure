(ns sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.4-exponentiation)

(defn square [x] (* x x))

(defn expt [b n]
  (if (= n 0)
    1
    (* b (expt b (- n 1)))))

(defn expt-iter [b counter product]
  (if (= counter 0)
    product
    (recur b (- counter 1) (* b product))))

(defn expt [b n]
  (expt-iter b n 1))

(defn fast-expt [b n]
  (cond (= n 0) 1
        (even? n) (square (fast-expt b (/ n 2)))
        :else (* b (fast-expt b (- n 1)))))



