(ns sicp-clojure.2-building-abstractions-with-data.1-introduction-to-data-abstraction.3-what-is-meant-by-data)

(defn cons [x y]
  (fn [m]
    (cond (= 0 m) x
          (= 1 m) y
          :else (throw (ex-info "Argument not 0 or 1 -- CONS" {:arg m})))))

(defn car [z] (z 0))
(defn cdr [z] (z 1))


