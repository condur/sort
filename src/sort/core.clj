(ns sort.core
  (:gen-class)
  (:require
    [sort.data :as data]
    [sort.util :refer [select-values]]
    [sort.similarity :as similarity]
    [clojure.string :refer [split lower-case]]
    [cheshire.core :as json]
    [sort.file :as file]
    [sort.transducers :as transducers]
    [clojure.set :refer [intersection]]))

(defn products-by-manufacturers
  "Group products by manufacturer."
  [products]
  (group-by
    (fn [product] (lower-case (:manufacturer product)))
    products))

(defn get-manufacturer
  "Convert listing manufacturer into a collectiong/set of words."
  [listing]
  (->> (select-values listing [:manufacturer])
       (mapcat #(split % #"\s+"))
       (into #{})))

(defn match
  "Match products and listings."
  [products listings]
  (let [products-by-manufacturers (products-by-manufacturers products)
        products-manufacturers (into #{} (keys products-by-manufacturers))]
   (map
    (fn [listing]
      (let [listing-manufacturer (get-manufacturer listing)
            manufacturer (first (intersection listing-manufacturer products-manufacturers))]
        (if (nil? manufacturer)
          {:product {:product_name "UNDEFINED"} :listing listing}
          (let [similar-product (similarity/include-max listing (get products-by-manufacturers manufacturer))]
            {:product similar-product :listing listing}))))
    listings)))

(defn format-match
  "Convert match result data to a desirable format."
  [result]
  (->> result
       (group-by #(get-in % [:product :product_name]))
       (map #(assoc {} :product_name (key %) :listings (transduce transducers/format-listings conj [] (val %))))
       (map #(str (json/generate-string %) "\n"))))


(defn -main
  []
  (let [path "resources/result.txt"
        result (match data/products data/listings)
        formated-result (format-match result)]
    (file/save-coll-to path formated-result)
    (printf "Match is done. Please check the result in %s" path)
    (println "")))
