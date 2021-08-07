(ns pushgp-art.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.set]
            [pushgp-art.utils :as utils]))

(defn setup []
  (q/frame-rate 30)
  (let [plushies (repeatedly 16 utils/random-plushy)
        images (map #(utils/plushy->image % 32) plushies)]
    {:plushies plushies
     :images images
     :selected-indices #{}}))

(defn update [state] state)

(defn mouse-clicked 
  ([state event]
  (let [mx (:x event)
        my (:y event)]
    (update-in state [:selected-indices] #(clojure.set/union % (hash-set (utils/mouse-pos->index 128 4 mx my)))))))

(defn draw [old-state]
  (q/background 255)
  (println (:selected-indices old-state))
  (let [images (:images old-state)]
    (dotimes [i (count images)]
      (q/resize (nth images i) 128 128)
      (q/image (nth images i) (* (mod i 4) 128) (* (quot i 4) 128)))))

(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [512 512]
  :setup setup
  :draw draw
  :update update
  :mouse-clicked mouse-clicked
  :middleware [m/fun-mode])