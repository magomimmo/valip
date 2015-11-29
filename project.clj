(defproject org.clojars.magomimmo/valip "0.4.0-SNAPSHOT"
  :description "Portable validation library for Clojure and
  ClojureScript, forked from https://github.com/cemerick/valip"
  :url "http://github.com/magomimmo/valip"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-doo "0.1.6-rc.1"]]
  
  :doo {:build "none"}

  :cljsbuild {:builds {;; none
                       :none
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/none/none.js"
                                   :output-dir "target/none"
                                   :main valip.runner
                                   :optimization :none
                                   :pretty-print true}}
                       ;; whitespace
                       :whitespace
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/whitespace/whitespace.js"
                                   :output-dir "target/whitespace"
                                   :main valip.runner
                                   :optimization :whitespace
                                   :pretty-print true}}
                       ;; simple
                       :simple
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/simple/simple.js"
                                   :output-dir "target/simple"
                                   :main valip.runner
                                   :optimization :simple
                                   :pretty-print false}}
                       ;; advanced
                       :advanced
                       {:source-paths ["src" "test"]
                        :compiler {:output-to "target/advanced/advanced.js"
                                   :output-dir "target/advanced"
                                   :main valip.runner
                                   :optimization :advanced
                                   :pretty-print false}}}}
  clean-targets ^{:protect false} [:target-path "resources" "dev-resources"])


