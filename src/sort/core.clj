(ns sort.core
  (:gen-class)
  (:require [sort.data :as data]
            [sort.util :refer :all]
            [sort.similarity :as similarity]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.test.alpha :as stest]
            [clojure.test :refer :all]
            [clojure.string :refer [join split lower-case]]
            [cheshire.core :as json]
            [incanter.stats :refer :all]
            [criterium.core :as c]
            [sort.file :as file]))

(defn products-by-manufacturers
  "Group products by manufacturer"
  [products]
  (group-by
    (fn [product] (lower-case (:manufacturer product)))
    products))

(defn get-manufacturer
  "Convert listing manufacturer to a collectiong of words."
  [listing]
  (->> (select-values listing [:manufacturer])
       (mapcat #(split % #"\s+"))
       (into #{})))

(defn matching
  "Matching products and listings. "
  [products listings n]
  (let [products-by-manufacturers (products-by-manufacturers products)
        products-manufacturers (into #{} (keys products-by-manufacturers))]
   (map
    (fn [listing]
      (let [listing-manufacturer (get-manufacturer listing)
            manufacturer (first (clojure.set/intersection listing-manufacturer products-manufacturers))]
        (if (nil? manufacturer)
          {:product {:product_name "UNDEFINED"} :listing listing}
          (let [similar-product (similarity/include-max listing (get products-by-manufacturers manufacturer))]
            {:product similar-product :listing listing}))))
    (take n listings))))

(defn format-listings
  [listings]
  (->> listings
       (map #(dissoc % :product))
       (map :listing)
       (map #(dissoc % :samples))))

(defn format-result
  [result]
  (->> result
       (group-by #(get-in % [:product :product_name]))
       (map #(assoc {} :product_name (key %) :listings (format-listings (val %))))
       (map #(str (json/generate-string %) "\n"))))

(defn save-result
  [path result]
  (file/clean path)
  (doall (map #(file/append-to path %) result)))


(deftest matching_test
  (testing "matching"
    (is (= nil
            (save-result "resources/results.txt"
              (format-result
                 (matching data/products data/listings 50000)))))))
