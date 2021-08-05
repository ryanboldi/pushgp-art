(ns pushgp-art.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [pushgp-art.utils :as utils]))

(defn setup []
  (q/frame-rate 1)
  ;(let [plushies (repeatedly 10 utils/random-plushy)
  ;      images (map #(utils/plushy->image % 50) plushies)]
  ;  {:plushies plushies
   ;  :images images}))
)

(defn update [state] state)

(defn draw [old-state]
  (q/background 255)
  (let [plushies (repeatedly 64 utils/random-plushy)
        images (map #(utils/plushy->image % 64) plushies)]
   (dotimes [i (count images)]
      (q/resize (nth images i) 64 64)
      (q/image (nth images i) (* (mod i 8) 64) (* (quot i 8) 64))))
  ;(let [images (:images old-state)]
   ; (dotimes [i (count images)]
  ;   (q/resize (nth i images) 100 100)
     ; (q/image (nth i images) (* i 100))))
  (q/save "assets/64-random.png")
  (q/exit)
  (q/no-loop))

(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [512 512]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw
  :update update
  :middleware [m/fun-mode])
