(ns sort.data-test
  (:require
   [clojure.test :refer :all]
   [sort.data :refer [parse-product parse-listing]]
   [sort.file :as file]))

(deftest load_json_lines

  (testing "load and parse JSON lines from resources/products file"
    (let [products (file/read-data "resources/products.txt" parse-product)]
      (is (> (count products) 700))))

  (testing "load and parse JSON lines from resources/listings file"
    (let [listings (file/read-data "resources/listings.txt" parse-listing)]
      (is (> (count listings) 20000)))))
