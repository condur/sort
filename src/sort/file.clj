(ns sort.file
  (:require
    [clojure.java.io :as io]))

(defn clean
  "Remove the content of the file."
  [path]
  (with-open [wrtr (io/writer path)]
    (.write wrtr "")
    (.close wrtr)))

(defn append-to
  "Append data to a file."
  [path data]
  (with-open [wrtr (io/writer path :append true)]
    (.write wrtr data)
    (.close wrtr)))

(defn save-to
  "Write data to a file, removing all existed content."
  [path data]
  (with-open [wrtr (io/writer path :append false)]
    (.write wrtr data)
    (.close wrtr)))

(defn save-coll-to
  "Write data in a form of a collection to a file, one by one, removing all existed content."
  [path coll]
  (clean path)
  (with-open [wrtr (io/writer path :append true)]
    (doall (for [x coll] (.write wrtr x)))
    (.close wrtr)))


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
