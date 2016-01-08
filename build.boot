(set-env!
 :source-paths #{"src"}

 :dependencies '[[org.clojure/clojure "1.7.0"]
                 [adzerk/boot-test "1.0.7"]])

(require '[adzerk.boot-test :refer [test]])

(deftask testing
  []
  (merge-env! :source-paths #{"test"})
  identity)

(deftask clj-tdd
  "Launch a CLJ TDD Environment"
  []
  (comp
   (testing)
   (watch)
   (test :namespaces #{'valip.test.core 'valip.test.predicates})))
