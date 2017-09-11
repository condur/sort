(ns sort.data-test
  (:require
   [sort.data :refer :all]
   [sort.util :refer [read-data]]
   [clojure.test :refer :all]))

(deftest load_json_lines

  (testing "load and parse JSON lines from resources/products file"
    (let [products (read-data "resources/products.txt" parse-product)]
      (is (> (count products) 700))))

  (testing "load and parse JSON lines from resources/listings file"
    (let [listings (read-data "resources/listings.txt" parse-listing)]
      (is (> (count listings) 20000)))))
