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
(def max-initial-plushy-size 100)


(def instructions
  (list :in1
        :in2
        :integer_add
        :integer_subtract
        :integer_mult
        :integer_quot
        :float_sin
        :float_cos
        :float_tan
        :float_from_integer
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

(defn plushy->image [plushy]
  (let [push-program (genome/plushy->push plushy)
        im (q/create-image 100 100 :alpha)]
    (dotimes [x 100]
      (dotimes [y 100]
        (let [normalized-x (float (/ x 100))
              normalized-y (float (/ y 100))
              output (state/peek-stack
                      (interpreter/interpret-program
                       push-program
                       (assoc state/empty-state :input {:in1 normalized-x :in2 normalized-y})
                       100)
                      :float)]
          (q/set-pixel im x y (q/color (* output 255))))))
    im))