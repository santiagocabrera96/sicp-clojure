(ns sicp-clojure.1-building-abstractions-with-procedures.3-formulating-abstractions-with-higher-order-procedures.3-procedures-as-general-methods)

(defn average [x y] (/ (+ x y) 2.0))
(defn close-enough? [x y]
  (< (abs (- x y)) 0.001))
(defn search [f neg-point pos-point]
  (let [midpoint (average neg-point pos-point)]
    (if (close-enough? neg-point pos-point)
      midpoint
      (let [test-value (f midpoint)]
        (cond (pos? test-value) (search f neg-point midpoint)
              (neg? test-value) (search f midpoint pos-point)
              :else midpoint)))))

(defn half-interval-method [f a b]
  (let [a-value (f a)
        b-value (f b)]
    (cond (and (neg? a-value) (pos? b-value)) (search f a b)
          (and (neg? b-value) (pos? a-value)) (search f b a)
          :else (throw (ex-info "Values are not of opposite sign" {:a a :b b})))))

(half-interval-method clojure.math/sin 2.0 4.0)

(half-interval-method (fn [x] (- (* x x x) (* 2 x) 3))
                      1.0 2.0)

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

(fixed-point clojure.math/cos 1.0)
(fixed-point (fn [y] (+ (clojure.math/sin y) (clojure.math/cos y)))
             1.0)

(defn sqrt [x]
  (fixed-point (fn [y] (/ x y))
               1.0))

(defn sqrt [x]
  (fixed-point (fn [y] (average y (/ x y)))
               1.0))

(time (sqrt 2))
(time (clojure.math/sqrt 2))
