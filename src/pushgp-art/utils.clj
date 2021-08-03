(ns pushgp-art.utils
  (:require [propeller.genome :as genome]
            [propeller.push.interpreter :as interpreter]
            [propeller.push.state :as state]))

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

(def random-plushy (genome/make-random-plushy instructions max-initial-plushy-size))

random-plushy

(def push-program (genome/plushy->push random-plushy))

(interpreter/interpret-program 
 (genome/plushy->push random-plushy)
 (assoc state/empty-state :input {:in1 0 :in2 1})
 100)

(defn plushy->image [plushy])