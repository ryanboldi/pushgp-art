(ns pushgp-art.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [pushgp-art.utils :as utils]))

(defn setup []
  (q/frame-rate 1)
  (let [plushies (repeatedly 16 utils/random-plushy)
        images (map #(utils/plushy->image % 50) plushies)]
    {:plushies plushies
     :images images}))

(defn update [state] state)

(defn draw [old-state]
  (q/background 255)
  (let [images (:images old-state)]
   (dotimes [i (count images)]
    (q/resize (nth images i) 128 128)
      (q/image (nth images i) (* (mod i 4) 128) (* (quot i 4) 128))))
  (q/no-loop))

(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [512 512]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw
  :update update
  :middleware [m/fun-mode])
