(ns pushgp-art.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [pushgp-art.utils :as utils]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0}
  )

(defn draw [_]
  (q/background 255)
(let [im1 (utils/plushy->image (utils/random-plushy))
      im2 (utils/plushy->image (utils/random-plushy))
      im3 (utils/plushy->image (utils/random-plushy))
      im4 (utils/plushy->image (utils/random-plushy))
      im5 (utils/plushy->image (utils/random-plushy))]
  (q/image im1 0 0)
  (q/image im2 100 0)
  (q/image im3 200 0)
  (q/image im4 300 0)
  (q/image im5 400 0))
  (q/no-loop))


(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
