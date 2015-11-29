(ns valip.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [valip.test.core]
            [valip.test.predicates]))

(doo-tests 'valip.test.core
          'valip.test.predicates)

