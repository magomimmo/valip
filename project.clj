(defproject org.clojars.magomimmo/valip "0.4.0-SNAPSHOT"
  :description "Portable validation library for Clojure and
  ClojureScript, forked from https://github.com/cemerick/valip"
  :url "http://github.com/magomimmo/valip"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]
  :plugins [[lein-cljsbuild "1.1.1"]]
  
  ;; :hooks [leiningen.cljsbuild]

  :cljsbuild {:test-commands
              {"unit" 
               ["phantomjs" :runner
                "target/js/unit-test.js"]}
              :builds {:prod
                       {:source-paths ["src"]
                        :jar true
                        :compiler {:output-to "target/js/main.js"
                                   :optimization :advanced
                                   :pretty-print false
                                   }}
                       :test
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/js/unit-test.js"
                                   :optimization :whitespace
                                   :pretty-print true}}}}
  :clean-targets ^{:protect false} [:target-path "resources"]
  )
