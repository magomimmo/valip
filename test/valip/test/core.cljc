(ns valip.test.core
  #?(:clj (:require [valip.core :refer [validation-on validate]]
                    [clojure.test :refer [deftest is]])
     :cljs (:require [valip.core :refer [validation-on validate]]
                     [cljs.test :refer-macros [deftest is]])))

(deftest validation-on-test
  (let [p? (fn [x] false)
        v  (validation-on :x p? "error")]
    (is (= (v {:x 1}) {:x ["error"]}))))

(deftest validate-test
  (is (= (validate {:x 17}
           [:x (complement nil?) "must be present"]
           [:y (complement nil?) "must be present"]
           [:x #(> % 18) "must be greater than 18"])
         {:x ["must be greater than 18"]
          :y ["must be present"]})))
