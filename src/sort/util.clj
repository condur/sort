(ns sort.util
  (:require
   [sort.transducers :as transducers]
   [clojure.java.io :as io]))


(defn read-data
  "Read data from a file containing text format.
   Parse line by line data using the provided parse funtion.
   Return 'nil' if file does not exists."
  [path parse-fn]
  (when (.exists (io/file path))
    (with-open [reader (io/reader path)]
      (persistent!
        (reduce
          (fn [lines line] (conj! lines (parse-fn line)))
          (transient [])
          (line-seq reader))))))


(defn strip
  "Strip a set of characters from a string"
  [coll chars]
  (apply str (remove #((set chars) %) coll)))


(defn select-values
  "Select values of the specified keys from a hash-map, removing the duplicates.
   Optionaly strip characters."
  ([m keys]
   (into #{} (transducers/get-values m) keys))
  ([m keys strip-chars]
   (into #{} (transducers/get-values-and-strip m strip strip-chars) keys)))


(defn- select-values-legacy
  "Legacy/old way of selecting values, for reference purpose

   Select values of the specified keys from a hash-map, removing the duplicates.
   Optionaly strip characters."
  ([m keys]
   (reduce
    (fn [values key]
      (if-let [value (m key)]
        (conj values value)
        values))
    #{}
    keys))
  ([m keys strip-chars]
   (map #(strip % strip-chars) (select-values m keys))))
