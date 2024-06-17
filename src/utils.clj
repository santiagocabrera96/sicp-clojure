(ns utils)

(defn- not-initialized-window-error [fn-name]
  (throw (ex-info (str "ERROR: " fn-name " undefined because the Window is not initialized, run (init-window width height) to initialize") {})))
(defn draw-line [v1 v2]
  (not-initialized-window-error "draw-line"))
(defn draw-image [image orig-x orig-y edge1-x edge1-y edge2-x edge2-y]
  (not-initialized-window-error "draw-image"))
(defn load-image [url]
  (not-initialized-window-error "load-image"))
(defn clear-frame-raw [orig-x orig-y edge1-x edge1-y edge2-x edge2-y]
  (not-initialized-window-error "clear-frame-raw"))
(defn clear []
  (not-initialized-window-error "clear"))

(clojure.repl.deps/add-libs '{org.openjfx/javafx-controls {:mvn/version "22.0.1"}
                              org.openjfx/javafx-swing    {:mvn/version "22.0.1"}
                              org.openjfx/javafx-fxml     {:mvn/version "22.0.1"}})
(defmacro fx-run "With this macro what you run is run in the JavaFX Application thread. Is needed for all calls related with JavaFx"
  [& code]
  `(let [promise# (promise)] (javafx.application.Platform/runLater (fn [] (deliver promise# (do ~@code)))) @promise#))
(defn init-window [width height]
  (do
    ;Initialize JavaFX toolkit
    (new javafx.embed.swing.JFXPanel)
    ; Set this to false so that the JavaFX runtime is still running after all JavaFX windows close.
    (javafx.application.Platform/setImplicitExit false)

    (let [canvas (new javafx.scene.canvas.Canvas width height)]
      (fx-run
       (let [stage (new javafx.stage.Stage)
             root  (new javafx.scene.Group)
             scene (new javafx.scene.Scene root)]
         (.add (.getChildren root) canvas)
         (.setScene stage scene)
         (.setResizable stage false)
         (.show stage)))

      (defn draw-line [[x1 y1] [x2 y2]]
        (let [gc (.getGraphicsContext2D canvas)]
          (.save gc)
          (.setTransform gc 1 0 0 -1 0 height)
          (.setStroke gc javafx.scene.paint.Color/BLACK)
          (.strokeLine gc x1 y1 x2 y2)
          (.restore gc)))

      (defn draw-image [image orig-x orig-y edge1-x edge1-y edge2-x edge2-y]
        (let [gc (.getGraphicsContext2D canvas)]
          (.save gc)
          (.setTransform gc (/ edge1-x width) (- (/ edge1-y height)) (/ edge2-x width) (- (/ edge2-y height)) orig-x (- height orig-y))
          (.drawImage gc image 0 height width (- height))
          (.restore gc)))

      (defn clear-frame-raw [orig-x orig-y edge1-x edge1-y edge2-x edge2-y]
        (let [gc (.getGraphicsContext2D canvas)]
          (.save gc)
          (.setTransform gc (/ edge1-x width) (- (/ edge1-y height)) (/ edge2-x width) (- (/ edge2-y height)) orig-x (- height orig-y))
          (.clearRect gc 0 0 width height)
          (.restore gc)))

      (defn load-image [url]
        (new javafx.scene.image.Image url))

      (defn clear []
        (.clearRect (.getGraphicsContext2D canvas) 0 0 width height)))))