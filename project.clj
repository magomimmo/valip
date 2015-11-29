(defproject org.clojars.magomimmo/valip "0.4.0-SNAPSHOT"
  :description "Portable validation library for Clojure and
  ClojureScript, forked from https://github.com/cemerick/valip"
  :url "http://github.com/magomimmo/valip"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]

  :plugins [[lein-cljsbuild "1.1.1"]]
  
  ;; :hooks [leiningen.cljsbuild]

  :cljsbuild {:builds {;; none
                       :test-none
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/none/test-none.js"
                                   :output-dir "target/none"
                                   :optimization :none
                                   :pretty-print true}}
                       ;; whitespace
                       :test-whitespace
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/whitespace/test-whitespace.js"
                                   :output-dir "target/whitespace"
                                   :optimization :whitespace
                                   :pretty-print true}}
                       ;; simple
                       :test-simple
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/simple/test-simple.js"
                                   :output-dir "target/simple"
                                   :optimization :simple
                                   :pretty-print false}}
                       ;; advanced
                       :test-advanced
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/advanced/test-advanced.js"
                                   :output-dir "target/advanced"
                                   :optimization :advanced
                                   :pretty-print false}}}}

  :clean-targets ^{:protect false} [:target-path "resources"])


