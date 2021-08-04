(ns pushgp-art.utils
  (:require [propeller.genome :as genome]
            [propeller.push.interpreter :as interpreter]
            [propeller.push.state :as state]
            [propeller.push.instructions.numeric]
            [propeller.push.instructions.input-output]
            [propeller.push.instructions.code]
            [propeller.push.instructions.polymorphic]
            [quil.core :as q]))

(def population-size 9)
(def max-initial-plushy-size 1000)

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

(repeatedly
  population-size
  #(hash-map :plushy (genome/make-random-plushy
                      instructions
                      max-initial-plushy-size)))

(defn random-plushy [] (genome/make-random-plushy instructions max-initial-plushy-size))

random-plushy

(def push-program (genome/plushy->push random-plushy))

push-program

(state/peek-stack (interpreter/interpret-program
 push-program
 (assoc state/empty-state :input {:in1 256 :in2 256})
 100) :float)

(defn output->pixel-color [num]
  (->> num
       (* 2)
       (dec)
       (#(if (< % 0) (* -1 %) %))
       (float)
       (* 255.0)))

(output->pixel-color 1)

(defn plushy->image [plushy width]
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
                       width)
                      :float)]
          (q/set-pixel im x y (q/color (output->pixel-color output))))))
    im))