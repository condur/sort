(ns sort.transducers)

(defn not-nil-values
  "Transducer function for selecting not nil values from a hash-map, expecting to run on a sequence of keys"
  [m]
  (comp
    (filter #(some? (m %)))
    (map #(m %))))

(defn strip
  "Transducer function for striping a set of characters from a string"
  [strip-fn strip-chars]
  (comp
    (map #(strip-fn % strip-chars))))

(defn lower-case
  "Transducer function for converting string to all lower-case."
  []
  (comp
    (map #(if (string? %) (clojure.string/lower-case %) %))))

(defn get-values
  "Combine not-nil-values and lower-case transducer funtions"
  [m]
  (comp
    (not-nil-values m)
    (lower-case)))

(defn get-values-and-strip
  "Combine not-nil-values, lower-case and strip transducer funtions"
  [m strip-fn strip-chars]
  (comp
    (not-nil-values m)
    (strip strip-fn strip-chars)
    (lower-case)))
