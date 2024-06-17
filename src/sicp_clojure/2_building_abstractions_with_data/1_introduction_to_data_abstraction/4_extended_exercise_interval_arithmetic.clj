(ns sicp-clojure.2-building-abstractions-with-data.1-introduction-to-data-abstraction.4-extended-exercise-interval-arithmetic)

(defn make-interval [a b])
(defn lower-bound [i])
(defn upper-bound [i])

(defn add-intervals [x y]
  (make-interval (+ (lower-bound x) (lower-bound y))
                 (+ (upper-bound x) (upper-bound y))))

(defn mul-interval [x y]
  (let [p1 (* (lower-bound x) (lower-bound y))
        p2 (* (lower-bound x) (upper-bound y))
        p3 (* (upper-bound x) (lower-bound y))
        p4 (* (upper-bound x) (upper-bound y))]
    (make-interval (min p1 p2 p3 p4)
                   (max p1 p2 p3 p4))))

(defn div-interval [x y]
  (mul-interval x
                (make-interval (/ 1.0 (upper-bound y))
                               (/ 1.0 (lower-bound y)))))

(defn make-center-width [c w]
  (make-interval (- c w) (+ c w)))

(defn center [x]
  (/ (+ (lower-bound x) (upper-bound x)) 2))

(defn width [x]
  (/ (- (lower-bound x) (upper-bound x)) 2))