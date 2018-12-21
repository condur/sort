(ns sort.similarity-test
  (:require
   [sort.similarity :as similarity]
   [clojure.test :refer :all]))

(let [products [{:product_name "Samsung_TL240",
                 :manufacturer "Samsung",
                 :model "TL240",
                 :announced-date "2010-01-05T19:00:00.000-05:00",
                 :samples #{"Samsung" "TL240"},}
                {:product_name "Samsung_TL220",
                 :manufacturer "Samsung",
                 :model "TL220",
                 :announced-date "2009-08-12T20:00:00.000-04:00",
                 :samples #{"Samsung" "TL220"},}
                {:product_name "Samsung_HZ15W",
                 :manufacturer "Samsung",
                 :model "HZ15W",
                 :announced-date "2009-02-22T19:00:00.000-05:00",
                 :samples #{"Samsung" "HZ15W"},}
                {:product_name "Samsung_ST10",
                 :manufacturer "Samsung",
                 :model "ST10",
                 :announced-date "2009-01-07T19:00:00.000-05:00",
                 :samples #{"Samsung" "ST10"}}]

      listing  {:title "Samsung TL240 - Digital camera - compact - 14.2 Mpix - optical zoom: 7 x -
                        supported memory: microSD, microSDHC - black",
                :manufacturer "Samsung",
                :currency "USD",
                :price "210.00",
                :samples #{"Samsung" "compact" "14.2" "microSD," "Mpix" "-" "x" "Digital" "camera" "7"
                           "supported" "optical" "zoom:" "memory:" "TL240" "black" "microSDHC"}}]


 (deftest best_similarity
  (testing "similarity"
   (is (not= nil (similarity/jaccard listing products))))))
