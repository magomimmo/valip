(set-env!
 :source-paths #{"src"}

 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [adzerk/boot-test "1.2.0"]
                 [org.clojure/clojurescript "1.9.494"]
                 [adzerk/boot-cljs "1.7.228-2"]
                 [crisptrutski/boot-cljs-test "0.3.0"]
                 [doo "0.1.7"]])

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
