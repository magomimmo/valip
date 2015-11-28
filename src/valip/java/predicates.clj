(ns valip.java.predicates
  "Useful validation predicates implemented for JVM Clojure."
  (:require [valip.predicates :as preds]
            [valip.predicates.def :refer (defpredicate)])
  (:import
   java.util.Hashtable
   javax.naming.NamingException
   javax.naming.directory.InitialDirContext))

(defn- dns-lookup [^String hostname ^String type]
  (let [params {"java.naming.factory.initial"
                "com.sun.jndi.dns.DnsContextFactory"}]
    (try
      (.. (InitialDirContext. (Hashtable. params))
          (getAttributes hostname (into-array [type]))
          (get type))
      (catch NamingException _
        nil))))

(defpredicate valid-email-domain?
  "Returns true if the domain of the supplied email address has a MX DNS entry."
  [email]
  [preds/email-address?]
  (if-let [domain (second (re-matches #".*@(.*)" email))]
    (boolean (dns-lookup domain "MX"))))
