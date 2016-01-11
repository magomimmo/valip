(set-env!
 :source-paths #{"src"}
 
 :dependencies '[[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [adzerk/boot-test "1.0.7"]
                 [adzerk/boot-cljs "1.7.170-3"]
                 [crisptrutski/boot-cljs-test "0.2.1"]])

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.boot-cljs :refer [cljs]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(deftask testing
  []
  (merge-env! :source-paths #{"test"})
  identity)

(deftask tdd
  "Launch a CLJ TDD Environment"
  []
  (comp
   (testing)
   (watch)
   (test-cljs :namespaces #{'valip.test.core 'valip.test.predicates})
   (test :namespaces #{'valip.test.core 'valip.test.predicates})))
