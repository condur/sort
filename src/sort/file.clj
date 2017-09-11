(ns sort.file
  (:require
    [clojure.java.io :as io]))

(defn clean
  "Remove the content of the file"
  [path]
  (with-open [wrtr (io/writer path)]
    (.write wrtr "")
    (.close wrtr)))

(defn append-to
  "Append data to a file"
  [path data]
  (with-open [wrtr (io/writer path :append true)]
    (.write wrtr data)
    (.close wrtr)))


(defn save-to
  "Write date to a file, removing all the exisint content"
  [path data]
  (with-open [wrtr (io/writer path :append false)]
    (.write wrtr data)
    (.close wrtr)))
