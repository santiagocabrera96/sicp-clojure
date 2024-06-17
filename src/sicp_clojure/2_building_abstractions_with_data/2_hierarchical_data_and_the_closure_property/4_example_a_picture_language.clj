(ns sicp-clojure.2-building-abstractions-with-data.2-hierarchical-data-and-the-closure-property.4-example-a-picture-language
  (:require [utils :refer [draw-line draw-image load-image clear-frame-raw clear init-window]]))

(defn make-segment [p1 p2] [p1 p2])

(defn make-vect [x y] [x y])
(defn xcor-vect [v] (first v))
(defn ycor-vect [v] (second v))
(defn add-vect [v1 v2]
  (make-vect (+ (xcor-vect v1) (xcor-vect v2))
             (+ (ycor-vect v1) (ycor-vect v2))))
(defn sub-vect [v1 v2]
  (make-vect (- (xcor-vect v1) (xcor-vect v2))
             (- (ycor-vect v1) (ycor-vect v2))))

(defn scale-vect [scale v]
  (make-vect (* scale (xcor-vect v))
             (* scale (ycor-vect v))))

(defn make-frame [origin edge1 edge2] [origin edge1 edge2])
(defn origin-frame [frame] (first frame))
(defn edge1-frame [frame] (nth frame 1))
(defn edge2-frame [frame] (nth frame 2))

(defn frame-coord-map [frame]
  (fn [v]
    (add-vect
     (origin-frame frame)
     (add-vect (scale-vect (xcor-vect v)
                           (edge1-frame frame))
               (scale-vect (ycor-vect v)
                           (edge2-frame frame))))))

(defn start-segment [[p1 _]] p1)
(defn end-segment [[_ p2]] p2)

(defn clear-frame [frame]
  (let [origin-frame (origin-frame frame)
        orig-x       (xcor-vect origin-frame)
        orig-y       (ycor-vect origin-frame)
        edge1-frame  (edge1-frame frame)
        edge1-x      (xcor-vect edge1-frame)
        edge1-y      (ycor-vect edge1-frame)
        edge2-frame  (edge2-frame frame)
        edge2-x      (xcor-vect edge2-frame)
        edge2-y      (ycor-vect edge2-frame)]
    (clear-frame-raw orig-x orig-y edge1-x edge1-y edge2-x edge2-y)))

(defn segments->painter [& segment-list]
  (fn [frame]
    (clear-frame frame)
    (run!
     (fn [segment]
       (draw-line
        ((frame-coord-map frame) (start-segment segment))
        ((frame-coord-map frame) (end-segment segment))))
     segment-list)))

(defn wave [frame]
  ((segments->painter
    (make-segment (make-vect 0.20 0.00) (make-vect 0.35 0.50))
    (make-segment (make-vect 0.35 0.50) (make-vect 0.30 0.60))
    (make-segment (make-vect 0.30 0.60) (make-vect 0.15 0.45))
    (make-segment (make-vect 0.15 0.45) (make-vect 0.00 0.60))
    (make-segment (make-vect 0.00 0.80) (make-vect 0.15 0.65))
    (make-segment (make-vect 0.15 0.65) (make-vect 0.30 0.70))
    (make-segment (make-vect 0.30 0.70) (make-vect 0.40 0.70))
    (make-segment (make-vect 0.40 0.70) (make-vect 0.35 0.85))
    (make-segment (make-vect 0.35 0.85) (make-vect 0.40 1.00))
    (make-segment (make-vect 0.60 1.00) (make-vect 0.65 0.85))
    (make-segment (make-vect 0.65 0.85) (make-vect 0.60 0.70))
    (make-segment (make-vect 0.60 0.70) (make-vect 0.75 0.70))
    (make-segment (make-vect 0.75 0.70) (make-vect 1.00 0.40))
    (make-segment (make-vect 1.00 0.20) (make-vect 0.60 0.48))
    (make-segment (make-vect 0.60 0.48) (make-vect 0.80 0.00))
    (make-segment (make-vect 0.40 0.00) (make-vect 0.50 0.30))
    (make-segment (make-vect 0.50 0.30) (make-vect 0.60 0.00))
    )
   frame))


(defn image->painter [image]
  (fn [frame]
    (let [origin-frame (origin-frame frame)
          orig-x       (xcor-vect origin-frame)
          orig-y       (ycor-vect origin-frame)
          edge1-frame  (edge1-frame frame)
          edge1-x      (xcor-vect edge1-frame)
          edge1-y      (ycor-vect edge1-frame)
          edge2-frame  (edge2-frame frame)
          edge2-x      (xcor-vect edge2-frame)
          edge2-y      (ycor-vect edge2-frame)]
      (draw-image image orig-x orig-y edge1-x edge1-y edge2-x edge2-y))))

(defn transform-painter [painter origin corner1 corner2]
  (fn [frame]
    (let [m (frame-coord-map frame)]
      (let [new-origin (m origin)]
        (painter
         (make-frame new-origin
                     (sub-vect (m corner1) new-origin)
                     (sub-vect (m corner2) new-origin)))))))

(defn flip-vert [painter]
  (transform-painter painter
                     (make-vect 0.0 1.0)
                     (make-vect 1.0 1.0)
                     (make-vect 0.0 0.0)))

(defn flip-horiz [painter]
  (transform-painter painter
                     (make-vect 1.0 0.0)
                     (make-vect 0.0 0.0)
                     (make-vect 1.0 1.0)))

(defn shrink-to-upper-right [painter]
  (transform-painter painter
                     (make-vect 0.5 0.5)
                     (make-vect 1.0 0.5)
                     (make-vect 0.5 1.0)))

(defn rotate90 [painter]
  (transform-painter painter
                     (make-vect 1.0 0.0)
                     (make-vect 1.0 1.0)
                     (make-vect 0.0 0.0)))

(defn squash-inwards [painter]
  (transform-painter painter
                     (make-vect 0.0 0.0)
                     (make-vect 0.65 0.35)
                     (make-vect 0.35 0.65)))

(defn beside [painter1 painter2]
  (let [split-point (make-vect 0.5 0.0)
        paint-left (transform-painter painter1
                                      (make-vect 0.0 0.0)
                                      split-point
                                      (make-vect 0.0 1.0))
        paint-right (transform-painter painter2
                                       split-point
                                       (make-vect 1.0 0.0)
                                       (make-vect 0.5 1.0))]
    (fn [frame]
      (paint-left frame)
      (paint-right frame))))

(defn below [painter1 painter2]
  (let [split-point (make-vect 0.0 0.5)
        paint-down (transform-painter painter1
                                       (make-vect 0.0 0.0)
                                       (make-vect 1.0 0.0)
                                       split-point)
        paint-up (transform-painter painter2
                                    split-point
                                    (make-vect 1.0 0.5)
                                    (make-vect 0.0 1.0))
        ]
    (fn [frame]
      (paint-down frame)
      (paint-up frame))))

(defn right-split [painter n]
  (if (= n 0)
    painter
    (let [smaller (right-split painter (- n 1))]
      (beside painter (below smaller smaller)))))

(defn up-split [painter n]
  (if (= n 0)
    painter
    (let [smaller (up-split painter (- n 1))]
      (below painter (beside smaller smaller)))))

(defn corner-split [painter n]
  (if (= n 0)
    painter
    (let [top-left     (beside (up-split painter (- n 1))
                               (up-split painter (- n 1)))
          bottom-right (below (right-split painter (- n 1))
                              (right-split painter (- n 1)))
          top-right (corner-split painter (- n 1))]
      (beside (below painter top-left)
              (below bottom-right top-right)))))

(defn square-limit [painter n]
  (let [quarter (corner-split painter n)]
    (let [half (beside (flip-horiz quarter) quarter)]
      (below (flip-vert half) half))))

(defn square-of-four [tl tr bl br]
  (fn [painter]
    (let [top (beside (tl painter) (tr painter))
          bottom (beside (bl painter) (br painter))]
      (below bottom top))))

(defn flipped-pairs [painter]
  (let [combine4 (square-of-four identity flip-vert identity flip-vert)]
    (combine4 painter)))

(defn rotate180 [painter] (rotate90 (rotate90 painter)))

(defn square-limit [painter n]
  (let [combine4 (square-of-four flip-horiz identity rotate180 flip-vert)]
    (combine4 (corner-split painter n))))

(def wave2 (beside wave (flip-vert wave)))
(def wave4 (below wave2 wave2))
(def wave4 (flipped-pairs wave))


(comment
  (def width 1000)
  (def height 1000)
  (init-window width height)
  (def frame (make-frame (make-vect 0 0) (make-vect width 0) (make-vect 0 height)))

  (def rogers
    (let [rogers-image (load-image "https://libraries.mit.edu/app/uploads/sites/3/2010/12/rogers.jpg")]
      (image->painter rogers-image)))

  ((square-limit wave 8) frame)
  ((square-limit rogers 8) frame)
  (wave frame)
  (do (clear) ((squash-inwards wave) frame))
  (do (clear) ((transform-painter wave (make-vect 0.25 0.0) (make-vect 0.75 0) (make-vect 0.25 1.0)) frame))
  (do (clear) ((transform-painter wave (make-vect 0.0 0.25) (make-vect 1.0 0.25) (make-vect 0.0 0.75)) frame))

  (rogers frame)
  (do (clear) ((squash-inwards rogers) frame))
  (do (clear) ((transform-painter rogers (make-vect 0.25 0.0) (make-vect 0.75 0) (make-vect 0.25 1.0)) frame))
  (do (clear) ((transform-painter rogers (make-vect 0.0 0.25) (make-vect 1.0 0.25) (make-vect 0.0 0.75)) frame))

  (wave2 frame)
  (wave4 frame)

  ((right-split wave 4) frame)
  ((right-split rogers 4) frame)
  ((corner-split wave 4) frame)
  ((corner-split rogers 4) frame)

  )








