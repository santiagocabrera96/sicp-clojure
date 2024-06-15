(ns sicp-clojure.1-building-abstractions-with-procedures.3-formulating-abstractions-with-higher-order-procedures.2-constructing-procedures-using-lambda)

(fn [x] (+ x 4))

(fn [x] (/ 1.0 (* x (+ x 2))))

(defn sum [term a next b]
  (if (> a b)
    0
    (+ (term a)
       (sum term (next a) next b))))
(defn pi-sum [a b]
  (sum (fn [x] (/ 1.0 (* x (+ x 2))))
       a
       (fn [x] (+ x 4))
       b))

(defn integral [f a b dx]
  (sum f
       (+ a (/ dx 2.0))
       (fn [x] (+ x dx))
       b))
(defn square [x] (* x x))
(defn f [x y]
  (let [f-helper (fn [a b]
                   (+ (* x (square a))
                      (* y b)
                      (* a b)))]
    (f-helper (+ 1 (* x y))
              (- 1 y))))

(defn f [x y]
  ((fn [a b]
     (+ (* x (square a))
        (* y b)
        (* a b)))
   (+ 1 (* x y))
   (- 1 y)))

(def x 5)
(+ (let [x 3]
     (+ x (* x 10)))
   x)

(def x 2)
(let [y (+ x 2)
      x 3]
  (* x y))


