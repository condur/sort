(set-env!
 :source-paths #(into % ["src" "test"])
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.10.0"]
                 [cheshire "5.8.1"]
                 [incanter "1.9.3"]

                 ;REPL
                 [org.clojure/tools.namespace "0.3.0-alpha4"]
                 [proto-repl "0.3.1"]
                 [proto-repl-charts "0.3.2"]

                 ;TEST
                 [criterium "0.4.4" :scope "test"]
                 [org.clojure/test.check "0.10.0-alpha3" :scope "test"]
                 [metosin/bat-test "0.4.2" :scope "test"]])

;;Load the main namespace that is needed for "bat-test" and "run" tasks
(require
  '[metosin.bat-test  :refer (bat-test)]
  '[sort.core :refer :all])

(deftask dev
  "Profile setup for development.	Starting the repl with the dev profile...boot dev repl"
  []
  (println "Welcome to boot - DEV profile running")
  (set-env!
   :init-ns 'sort.core
   :source-paths #(into % ["test"])
   :dependencies #(into % '[[org.clojure/tools.namespace "0.2.11"]
                            [criterium "0.4.4"]
                            [proto-repl "0.3.1"]
                            [proto-repl-charts "0.3.2"]
                            [org.clojure/test.check "0.10.0-alpha3" :scope "test"]]))

  ;; Makes clojure.tools.namespace.repl work per https://github.com/boot-clj/boot/wiki/Repl-reloading
  (require 'clojure.tools.namespace.repl)
  (eval '(apply clojure.tools.namespace.repl/set-refresh-dirs
                (get-env :directories)))

  identity)

(deftask run
  "Profile setup for running the project from command line ... boot run"
  []
  ;; Run the main method from sort.core
  (with-pass-thru _
    (sort.core/-main)))
