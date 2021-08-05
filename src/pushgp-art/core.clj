(ns pushgp-art.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [pushgp-art.utils :as utils]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 1))


(defn draw [_]
  (q/background 255)
  
  ;(let [images (->> (repeatedly 10 utils/random-plushy)
 ;                   (map #(utils/plushy->image % 50)))]
    ;(dotimes [i 10]
   ;   (q/image (nth i images) (* 50 i) 0)))
  (q/image (utils/plushy->image (utils/random-plushy) 100) 0 0)
  (q/image (utils/plushy->image (utils/random-plushy) 100) 0 100)
  (q/image (utils/plushy->image (utils/random-plushy) 100) 100 0)
  (q/image (utils/plushy->image (utils/random-plushy) 100) 100 100)
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