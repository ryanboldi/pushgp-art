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

(def plushies (repeatedly 4 utils/random-plushy))
(def children (utils/get-new-plushies plushies '(0 1)))

(defn update [state] state)

(defn draw [old-state]
  (q/background 255)
  (q/image (utils/plushy->image (first plushies) 128) 0 0)
  (q/image (utils/plushy->image (second plushies) 128) 128 0)
  (q/image (utils/plushy->image (nth plushies 2) 128) 256 0)
  (q/image (utils/plushy->image (nth plushies 3) 128) 384 0)
  (q/image (utils/plushy->image (first children) 128) 0 128)
  (q/image (utils/plushy->image (second children) 128) 128 128)
  (q/image (utils/plushy->image (nth children 2) 128) 256 128)
  (q/image (utils/plushy->image (nth children 3) 128) 384 128)
  ;(let [images (:images old-state)]
  ; (dotimes [i (count images)]
  ;  (q/resize (nth images i) 128 128)
  ;    (q/image (nth images i) (* (mod i 4) 128) (* (quot i 4) 128))))
  (q/no-loop))

(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [512 256]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw
  :update update
  :middleware [m/fun-mode])
