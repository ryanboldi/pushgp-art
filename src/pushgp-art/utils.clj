(ns pushgp-art.utils
  (:require [propeller.genome :as genome]
            [propeller.tools.math :as math]
            [propeller.push.interpreter :as interpreter]
            [propeller.push.state :as state]
            [propeller.push.instructions.numeric]
            [propeller.push.instructions.input-output]
            [propeller.push.instructions.code]
            [propeller.push.instructions.polymorphic]
            [propeller.variation :as variation]
            [quil.core :as q]))

(def max-initial-plushy-size 500)

(def instructions
  (list :in1
        :in2
        :float_add
        :float_subtract
        :float_mult
        :float_quot
        :float_sin
        :float_cos
        :float_tan
        :float_min
        :float_max
        :float_lt
        :float_gt
        :exec_dup
        :exec_if
        'close
        0
        1))

(defn random-plushy [] (genome/make-random-plushy instructions max-initial-plushy-size))

(defn output->pixel-color [num]
  (->> num
       (* 2)
       (dec)
       (math/abs)
       (* 255)
       (math/ceil)))

(defn output->pixel-color-basic [num]
  (->> num
       (* 255)
       (math/ceil)))

(defn plushy->image 
  "expresses the phenotype of a given plushy. Note: Can only be run from inside quil sketch functions"
  [plushy width]
  (let [push-program (genome/plushy->push plushy)
        im (q/create-image width width :alpha)]
    (dotimes [x width]
      (dotimes [y width]
        (let [normalized-x (float (/ x width))
              normalized-y (float (/ y width))
              output (state/peek-stack
                      (interpreter/interpret-program
                       push-program
                       (assoc state/empty-state :input {:in1 normalized-x :in2 normalized-y})
                       100)
                      :float)
              float-output (if (float? output) output 0)]
          (q/set-pixel im x y (q/color (output->pixel-color float-output))))))
    im))

(defn get-new-plushies
  "crosses over the plushies at the selected indices"
  [plushies selected-indices]
  (let [pop-size (count plushies)
        parents (map (partial nth plushies) selected-indices)]
    (loop [children '()]
      (if (= (count children) pop-size)
        children
        (recur (conj children (variation/crossover (rand-nth parents) (rand-nth parents))))))))

(defn mutate-plushy "mutates a random plushy"
  [plushy]
  (if (< (rand) 0.7)
    (variation/uniform-replacement plushy)
    (variation/uniform-addition plushy)))