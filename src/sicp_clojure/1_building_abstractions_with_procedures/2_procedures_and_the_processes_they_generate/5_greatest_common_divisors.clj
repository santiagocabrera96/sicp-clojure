(ns sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.5-greatest-common-divisors)

(defn gcd [a b]
  (if (= b 0)
    a
    (recur b (rem a b))))
