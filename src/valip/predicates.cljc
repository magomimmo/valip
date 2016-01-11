(ns valip.predicates
  "Predicates useful for validating input strings, such as ones from HTML forms."
  #?(:clj (:require [clojure.string :as str]
                    [clojure.edn :refer [read-string]]
                    [valip.macros :refer [defpredicate]])
     :cljs (:require [clojure.string :as str]
                     [cljs.reader :refer [read-string]]))
  #?(:clj (:refer-clojure :exclude [read-string])
     :cljs (:require-macros [valip.macros :refer [defpredicate]]))
  #?(:clj (:import (java.net URI URISyntaxException)
                   java.util.Hashtable
                   javax.naming.NamingException
                   javax.naming.directory.InitialDirContext)
     :cljs (:import goog.Uri)))

(defn present?
  "Returns false if x is nil or blank, true otherwise."
  [x]
  (not (str/blank? x)))

(defn matches
  "Creates a predicate that returns true if the supplied regular expression
  matches its argument."
  [re]
  (fn [s] (boolean (re-matches re (str s)))))

(defn max-length
  "Creates a predicate that returns true if a string's length is less than or
  equal to the supplied maximum."
  [max]
  (fn [s] (<= (count s) max)))

(defn min-length
  "Creates a predicate that returns true if a string's length is greater than
  or equal to the supplied minimum."
  [min]
  (fn [s] (>= (count s) min)))

(defn email-address?
  "Returns true if the email address is valid, based on RFC 2822. Email
  addresses containing quotation marks or square brackets are considered
  invalid, as this syntax is not commonly supported in practise. The domain of
  the email address is not checked for validity."
  [email]
  (let [re (str "(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
                "(?:\\.[a-z0-9!#$%&'*+/=?" "^_`{|}~-]+)*"
                "@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
                "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")]
    (boolean (re-matches (re-pattern re) (str email)))))

(defn integer-string?
  "Returns true if the string represents an integer."
  [s]
  (boolean (re-matches #"\s*[+-]?\d+\s*" (str s))))

(defn decimal-string?
  "Returns true if the string represents a decimal number."
  [s]
  (boolean (re-matches #"\s*[+-]?\d+(\.\d+(M|M|N)?)?\s*" (str s))))

(defn digits?
  "Returns true if a string consists only of numerical digits."
  [s]
  (boolean (re-matches #"\d+" (str s))))

(defn alphanumeric?
  "Returns true if a string consists only of alphanumeric characters."
  [s]
  (boolean (re-matches #"[A-Za-z0-9]+" (str s))))

(defn- parse-number [x]
  (if (and (string? x) (re-matches #"\s*[+-]?\d+(\.\d+M|M|N)?\s*" x))
    (read-string x)))

(defn gt
  "Creates a predicate function for checking if a value is numerically greater
  than the specified number."
  [n]
  (fn [x] (if-let [x (parse-number x)] (> x n))))

(defn lt
  "Creates a predicate function for checking if a value is numerically less
  than the specified number."
  [n]
  (fn [x] (if-let [x (parse-number x)] (< x n))))

(defn gte
  "Creates a predicate function for checking if a value is numerically greater
  than or equal to the specified number."
  [n]
  (fn [x] (if-let [x (parse-number x)] (>= x n))))

(defn lte
  "Creates a predicate function for checking if a value is numerically less
  than or equal to the specified number."
  [n]
  (fn [x] (if-let [x (parse-number x)] (<= x n))))

(def over "Alias for gt" gt)

(def under "Alias for lt" lt)

(def at-least "Alias for gte" gte)

(def at-most "Alias for lte" lte)

(defn between
  "Creates a predicate function for checking whether a number is between two
  values (inclusive)."
  [min max]
  (fn [x]
    (if-let [x (parse-number x)]
      (and (>= x min) (<= x max)))))

#?(:clj (defn url?
          "Returns true if the string is a valid URL."
          [s]
          (try
            (let [uri (URI. (str s))]
              (and (seq (.getScheme uri))
                   (seq (.getSchemeSpecificPart uri))
                   (re-find #"//" (str s))
                   true))
            (catch URISyntaxException _ false)))
   :cljs (defn url?
           [s]
           (let [uri (goog.Uri.parse (str s))]
             (and (seq (.getScheme uri))
                  (re-find #"//" (str s))))))

#?(:clj (defn- dns-lookup [^String hostname ^String type]
          (let [params {"java.naming.factory.initial"
                        "com.sun.jndi.dns.DnsContextFactory"}]
            (try
              (.. (InitialDirContext. (Hashtable. params))
                  (getAttributes hostname (into-array [type]))
                  (get type))
              (catch NamingException _
                nil)))))

#?(:clj (defpredicate valid-email-domain?
          "Returns true if the domain of the supplied email address has a MX DNS entry."
          [email]
          [email-address?]
          (if-let [domain (second (re-matches #".*@(.*)" email))]
            (boolean (dns-lookup domain "MX")))))
