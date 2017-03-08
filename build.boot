(set-env!
 :source-paths #{"src"}

 :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                 [adzerk/boot-test "1.2.0" :scope "test"]
                 [org.clojure/clojurescript "1.9.494" :scope "provided"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.0" :scope "test"]
                 [doo "0.1.7" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]]
                 )

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.boot-cljs :refer [cljs]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]]
         '[adzerk.bootlaces :refer [bootlaces! build-jar push-snapshot]])

(def +version+ "0.4.0-SNAPSHOT")
(bootlaces! +version+)

(task-options!
push {:ensure-branch nil}
pom {:project 'org.clojars.magomimmo/valip
     :version "0.4.0-SNAPSHOT"
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

 ;; append at the end of `build.boot`
 (deftask install-jar
   []
   (merge-env! :resource-paths #{"src"})
   (comp
    (pom)
    (jar)
    (install)))
