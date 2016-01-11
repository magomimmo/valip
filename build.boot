(set-env!
 :source-paths #{"src"}
 
 :dependencies '[[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [adzerk/boot-test "1.1.0"]
                 [adzerk/boot-cljs "1.7.170-3"]
                 [crisptrutski/boot-cljs-test "0.2.1"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.boot-cljs :refer [cljs]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]]
         '[adzerk.bootlaces :refer [build-jar push-snapshot bootlaces!]])

(def +version+ "0.4.0-SNAPSHOT")
(bootlaces! +version+)

(task-options!
 pom {:project 'org.clojars.magomimmo/valip
      :version +version+
      :description "Functional validation library for Clojure and ClojureScript. 
                    Forked from https://github.com/cemerick/valip"
      :url "http://github.com/magomimmo/valip"
      :scm {:url "http://github.com/magomimmo/valip"}
      :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}}
 test {:namespaces #{'valip.test.core 'valip.test.predicates}}
 test-cljs {:namespaces #{'valip.test.core 'valip.test.predicates}})

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
   (test-cljs)
   (test)))
