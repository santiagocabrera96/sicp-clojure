(ns sicp-clojure.1-building-abstractions-with-procedures.2-procedures-and-the-processes-they-generate.2-tree-recursion)

(defn fib [n]
  (cond (= n 0) 0
        (= n 1) 1
        :else (+ (fib (- n 1))
                 (fib (- n 2)))))


(defn fib-iter [a b count]
  (if (= 0 count)
    b
    (recur (+ a b) a (- count 1))))

(defn fib [n]
  (fib-iter 1 0 n))

(defn first-denomination [kinds-of-coins]
  (case kinds-of-coins
    1 1
    2 5
    3 10
    4 25
    5 50))

(defn cc [amount kinds-of-coins]
  (cond (= amount 0) 1
        (or (< amount 0) (= kinds-of-coins 0)) 0
        :else (+ (cc amount
                     (- kinds-of-coins 1))
                 (cc (- amount
                        (first-denomination kinds-of-coins))
                     kinds-of-coins))))

(defn count-change [amount]
  (cc amount 5))

(count-change 100)