(ns sort.spec
 (:require
  [clojure.spec.alpha :as spec]))

(def date-ISO-8601-format-regex #"^([\+-]?\d{4}(?!\d{2}\b))((-?)((0[1-9]|1[0-2])(\3([12]\d|0[1-9]|3[01]))?|W([0-4]\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\d|[12]\d{2}|3([0-5]\d|6[1-6])))([T\s]((([01]\d|2[0-3])((:?)[0-5]\d)?|24\:?00)([\.,]\d+(?!:))?)?(\17[0-5]\d([\.,]\d+)?)?([zZ]|([\+-])([01]\d|2[0-3]):?([0-5]\d)?)?)?)?$")

(spec/def ::product_name string?)
(spec/def ::manufacturer string?)
(spec/def ::model string?)
(spec/def ::family string?)
(spec/def ::announced-date (spec/and string? #(re-matches date-ISO-8601-format-regex %)))

(spec/def ::product
  (spec/keys :req-un [::product_name ::manufacturer ::model ::announced-date]
             :opt-un [::family]))

(spec/def ::title string?)
(spec/def ::currency string?)
(spec/def ::price string?)

(spec/def ::listing
  (spec/keys :req-un [::title ::manufacturer ::currency ::price]))
