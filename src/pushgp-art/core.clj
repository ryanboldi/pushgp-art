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
   :angle 0})

(defn draw [_]
  (q/background 255)
  ; create image and draw gradient on it
  (q/image (utils/plushy->image utils/random-plushy) 0 0))


(q/defsketch pushgp-art
  :title "You spin my circle right round"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
