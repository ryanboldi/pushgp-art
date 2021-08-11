(ns pushgp-art.visualize
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.set]
            [pushgp-art.utils :as utils]))

(defn -main [[image-name image-width] & args]
  (println image-name image-width args))

(defn setup []
  (q/frame-rate 1)
  (let [image-name "zig-zag" image-width 1024
        plushy (read-string (slurp (str "./plushies/" image-name ".txt")))]
    {:plushy plushy
     :image (utils/plushy->image plushy image-width)}))

(defn draw [old-state]
  (q/background 255)
  (q/resize (:image old-state) 1024 1024)
  (q/image (:image old-state) 0 0)
  (q/save-frame "/assets/examples/512x512.png")
  (q/no-loop))

(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [1024 1024]
  :setup setup
  :draw draw
  :middleware [m/fun-mode])
