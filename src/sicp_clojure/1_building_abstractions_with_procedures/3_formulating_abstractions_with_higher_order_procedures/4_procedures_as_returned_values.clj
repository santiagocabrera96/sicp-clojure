(ns sicp-clojure.1-building-abstractions-with-procedures.3-formulating-abstractions-with-higher-order-procedures.4-procedures-as-returned-values)

(defn average [x y] (/ (+ x y) 2.0))
(defn square [x] (* x x))
(def tolerance 0.00001)
(defn fixed-point [f first-guess]
  (let [close-enough? (fn [v1 v2]
                        (< (abs (- v1 v2)) tolerance))
        try-f (fn [guess]
                (let [next (f guess)]
                  (if (close-enough? guess next)
                    next
                    (recur next))))]
    (try-f first-guess)))
(defn average-damp [f]
  (fn [x] (average x (f x))))

((average-damp square) 10)

(defn sqrt [x]
  (fixed-point (average-damp (fn [y] (/ x y)))
               1.0))
(sqrt 2)

(defn cube-root [x]
  (fixed-point (average-damp (fn [y] (/ x (square y))))
               1.0))

(def dx 0.00001)

(defn deriv [g]
  (fn [x]
    (/ (- (g (+ x dx)) (g x))
       dx)))

(defn cube [x] (* x x x))

((deriv cube) 5)

(defn newton-transform [g]
  (fn [x] (- x (/ (g x) ((deriv g) x)))))

(defn newtons-method [g guess]
  (fixed-point (newton-transform g) guess))

(defn sqrt [x]
  (newtons-method (fn [y] (- (square y) x))
                  1.0))

(defn fixed-point-of-transform [g transform guess]
  (fixed-point (transform g) guess))

(defn sqrt [x]
  (fixed-point-of-transform (fn [y] (/ x y))
                            average-damp
                            1.0))

(defn sqrt [x]
  (fixed-point-of-transform (fn [y] (- (square y) x))
                            newton-transform
                            1.0))

