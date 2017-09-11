(ns sort.similarity
  (:require
    [incanter.stats :refer [jaccard-index]]))

(defn jaccard
  "Find the most similar product for the specified listing based on the Jaccard index.
   http://en.wikipedia.org/wiki/Jaccard_index"
  [listing products]
  (->> products
       (map (fn [product]
              (let [similarity (jaccard-index (:samples product) (:samples listing))]
               (assoc product :similarity similarity))))
       (apply max-key :similarity)))


(defn include-max
  "Find the most similar product for the specified listing by
   checking for maximum product details in the listing."
  [listing products]
  (->> products
       (map (fn [product]
              (let [intersection (clojure.set/intersection (:samples product) (:samples listing))
                    similarity (- (count intersection) (count (:samples product)))]
               (assoc product :similarity similarity))))
       (apply max-key :similarity)))
