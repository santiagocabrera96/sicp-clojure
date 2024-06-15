(ns sicp-clojure.1-building-abstractions-with-procedures.3-formulating-abstractions-with-higher-order-procedures.1-procedures-as-arguments)

(defn sum-integers [a b]
  (if (> a b)
    0
    (+ a (sum-integers (+ a 1) b))))

(defn sum-cubes [a b]
  (if (> a b)
    0
    (+ a (sum-cubes (+ a 1) b))))

(defn pi-sum [a b]
  (if (> a b)
    0
    (+ (/ 1.0 (* a (+ a 2)))
       (pi-sum (+ a 4) b))))

(defn sum [term a next b]
  (if (> a b)
    0
    (+ (term a)
       (sum term (next a) next b))))

(defn cube [x] (* x x x))

(defn sum-cubes [a b]
  (sum cube a inc b))
(sum-cubes 1 10)

(defn identity [x] x)

(defn sum-integers [a b]
  (sum identity a inc b))
(sum-integers 1 10)

(defn pi-sum [a b]
  (let [pi-term (fn [x]
                  (/ 1.0 (* x (+ x 2))))
        pi-next (fn [x]
                  (+ x 4))]
    (sum pi-term a pi-next b)))

(* 8 (pi-sum 1 1000))

(defn integral [f a b dx]
  (let [add-dx (fn [x] (+ x dx))]
    (* (sum f (+ a (/ dx 2.0)) add-dx b)
       dx)))
(integral cube 0 1 0.01)
(integral cube 0 1 0.001)


