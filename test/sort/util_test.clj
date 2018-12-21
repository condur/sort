(ns sort.util-test
  (:require [sort.util :refer :all]
            [clojure.test :refer :all]))

(let [sample-data {:q "10" :w "20" :e "30" :r "40" :t "AwSrT"}]
  (deftest select_values-
    (testing "simple-case"
      (is (= #{"10" "30"}     (select-values sample-data [:q :e :y])))
      (is (= #{"0" "awsrt"}   (select-values sample-data [:q :e :t] "13"))))))
