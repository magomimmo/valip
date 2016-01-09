(ns valip.macros)

(defmacro pfn
  [& [name? & fbody]]
  (let [[name fbody] (if (symbol? name?)
                       [name? fbody]
                       [nil (cons name? fbody)])
        prologue (if name (list 'fn name) (list 'fn))
        [args pre-preds & body] (if (and (-> fbody first vector?)
                                         (-> fbody second vector?))
                                  fbody
                                  (list* (first fbody) nil fbody))]
    (when (> (count args) 1)
      (throw (IllegalArgumentException.
               (str "Validation predicate functions should take only one argument, not " args))))
    `(~@prologue
       ~args
       (and (every? #(% ~@args) ~pre-preds)
            ~@body))))

(defmacro defpredicate
  [name & fdecl]
  (let [[doc & fdecl] (if (string? (first fdecl))
                      fdecl
                      [nil fdecl])
        name (if doc
               (vary-meta name merge {:doc doc})
               name)]
    `(def ~name (pfn ~name ~@fdecl))))
