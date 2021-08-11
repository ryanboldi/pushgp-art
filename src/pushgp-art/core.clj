(ns pushgp-art.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.set]
            [pushgp-art.utils :as utils]))

(defn setup []
  (q/frame-rate 30)
  (let [plushies (repeatedly 16 utils/random-plushy)
        images (map #(utils/plushy->image % 128) plushies)]
    {:plushies plushies
     :images images
     :selected-indices #{}
     :save-index 0}))

(defn mouse-clicked [state event]
  (let [mx (:x event)
        my (:y event)]
    (update-in state [:selected-indices] #(clojure.set/union % (hash-set (utils/mouse-pos->index 128 4 mx my))))))

(defn key-pressed [state event]
  (cond
    (= 10 (:key-code event))
    (do
      (q/save-frame "/assets/example-1.png")
      (let [new-children (utils/get-new-plushies (:plushies state) (:selected-indices state))
            new-images (map #(utils/plushy->image % 128) new-children)]
        {:plushies new-children
         :images new-images
         :selected-indices #{}
         :save-index (:save-index state)}))
    (= 83 (:key-code event))
    (let [index (utils/mouse-pos->index 128 4 (q/mouse-x) (q/mouse-y))
          image-number (:save-index state)]
      (spit (str "./plushies/plushy" image-number ".txt") (apply list (nth (:plushies state) index)))
      (update-in state [:save-index] inc))
    :else state))

(defn draw [old-state]
  (q/background 255)
  (let [images (:images old-state)]
    (dotimes [i (count images)]
      (q/resize (nth images i) 128 128)
      (q/image (nth images i) (* (mod i 4) 128) (* (quot i 4) 128))))
  (q/no-fill)
  (q/stroke 255 0 0)
  (doseq [i (:selected-indices old-state)]
    (q/rect (* (mod i 4) 128) (* (quot i 4) 128) 128 128)))

(q/defsketch pushgp-art
  :title "PushGP Art"
  :size [512 512]
  :setup setup
  :draw draw
  :mouse-clicked mouse-clicked
  :key-pressed key-pressed
  :middleware [m/fun-mode])