(ns sort.data
  (:require
   [sort.util :refer [select-values read-data]]
   [clojure.string :refer [split]]
   [cheshire.core :as json]
   [clojure.spec.alpha :as spec]))

(defn parse-product
  "Parse raw products data. Validate against :sort.spec/product spec.
   Throw a spec explain exception in case that validation fail."
   [data]
   (let [product (json/parse-string data true)]
       (if (spec/valid? :sort.spec/product product)
          product
          (throw (Exception. (spec/explain :sort.spec/product product))))))

(defn parse-listing
  "Parse raw listing data. Validate against :sort.spec/listing spec.
   Throw a spec explain exception in case that validation fail."
   [data]
   (let [listing (json/parse-string data true)]
       (if (spec/valid? :sort.spec/listing listing)
          listing
          (throw (Exception. (spec/explain :sort.spec/listing listing))))))

(def strip-characters "(),.")

(defn- add-samples
  "Add samples to a hash-map based on specified keys values"
  [m keys]
  (let [raw-values (select-values m keys strip-characters)
        samples-values (->> raw-values (mapcat #(split % #"\s+")) (into #{}))]
    (assoc m :samples samples-values)))

; (def products
;   (read-data "resources/products.txt" parse-product))
;
; (def listings
;   (read-data "resources/listings.txt" parse-listing))


(def products
  (let [raw-products (read-data "resources/products.txt" parse-product)]
    (map #(add-samples % [:model :family]) raw-products)))
    ; (map #(add-samples % [:manufacture :model :family]) raw-products)))

(def listings
  (let [raw-listings (read-data "resources/listings.txt" parse-listing)]
    (map #(add-samples % [:title]) raw-listings)))
