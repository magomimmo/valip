(ns valip.test.predicates
  #? (:cljs (:require [valip.predicates :refer [present?
                                                matches
                                                max-length
                                                min-length
                                                email-address?
                                                digits?
                                                integer-string?
                                                decimal-string?
                                                gt
                                                gte
                                                lt
                                                lte
                                                over
                                                under
                                                at-most
                                                at-least
                                                between]]
                      [cljs.test :refer-macros [deftest is]])
      :clj (:use valip.predicates clojure.test)))

(deftest test-present?
  (is (not (present? nil)))
  (is (not (present? "")))
  (is (not (present? " ")))
  (is (present? "foo")))

(deftest test-matches
  (is ((matches #"...") "foo"))
  (is (not ((matches #"...") "foobar")))
  (is (not ((matches #"...") nil))))

(deftest test-max-length
  (is ((min-length 5) "hello"))
  (is ((min-length 5) "hello world"))
  (is (not ((min-length 5) "hi")))
  (is (not ((min-length 5) nil))))

(deftest test-email-address?
  (is (email-address? "foo@example.com"))
  (is (email-address? "foo+bar@example.com"))
  (is (email-address? "foo-bar@example.com"))
  (is (email-address? "foo.bar@example.com"))
  (is (email-address? "foo@example.co.uk"))
  (is (not (email-address? "foo")))
  (is (not (email-address? "foo@bar")))
  (is (not (email-address? "foo bar@example.com")))
  (is (not (email-address? "foo@foo_bar.com")))
  (is (not (email-address? nil))))

(deftest test-digits?
  (is (digits? "01234"))
  (is (not (digits? "04xa")))
  (is (not (digits? "")))
  (is (not (digits? nil))))

(deftest test-integer-string?
  (is (integer-string? "10"))
  (is (integer-string? "-9"))
  (is (integer-string? "0"))
  (is (integer-string? "  8  "))
  (is (not (integer-string? "10,000")))
  (is (not (integer-string? "foo")))
  (is (not (integer-string? "10x")))
  (is (not (integer-string? "1.1")))
  (is (not (integer-string? "")))
  (is (not (integer-string? nil))))

(deftest test-decimal-string?
  (is (decimal-string? "10"))
  (is (decimal-string? "-9"))
  (is (decimal-string? "0"))
  (is (decimal-string? "  8  "))
  (is (decimal-string? "1.1"))
  (is (decimal-string? "3.14159"))
  (is (not (decimal-string? "foo")))
  (is (not (decimal-string? "10x")))
  (is (not (decimal-string? "")))
  (is (not (decimal-string? nil))))

(deftest test-gt
  (is ((gt 10) "11"))
  (is (not ((gt 10) "9")))
  (is (not ((gt 10) "10")))
  (is (not ((gt 10) "")))
  (is (not ((gt 10) nil))))

(deftest test-gte
  (is ((gte 10) "11"))
  (is ((gte 10) "10"))
  (is (not ((gte 10) "9")))
  (is (not ((gte 10) "")))
  (is (not ((gte 10) nil))))

(deftest test-lt
  (is ((lt 10) "9"))
  (is (not ((lt 10) "11")))
  (is (not ((lt 10) "10")))
  (is (not ((lt 10) "")))
  (is (not ((lt 10) nil))))

(deftest test-lte
  (is ((lte 10) "9"))
  (is ((lte 10) "10"))
  (is (not ((lte 10) "11")))
  (is (not ((lte 10) "")))
  (is (not ((lte 10) nil))))

(deftest test-over
  (is (= over gt)))

(deftest test-under
  (is (= under lt)))

(deftest test-at-least
  (is (= at-least gte)))

(deftest test-at-most
  (is (= at-most lte)))

(deftest test-between
  (is ((between 1 10) "5"))
  (is ((between 1 10) "1"))
  (is ((between 1 10) "10"))
  (is (not ((between 1 10) "0")))
  (is (not ((between 1 10) "11")))
  (is (not ((between 1 10) "")))
  (is (not ((between 1 10) nil))))

;;; JVM only

#? (:clj (deftest test-valid-email-domain?
           (is (valid-email-domain? "example@google.com"))
           (is (not (valid-email-domain? "foo@example.com")))
           (is (not (valid-email-domain? "foo@google.com.nospam")))
           (is (not (valid-email-domain? "foo")))
           (is (not (valid-email-domain? nil)))))

#? (:clj (deftest test-url?
           (is (url? "http://google.com"))
           (is (url? "http://foo"))
           (is (not (url? "foobar")))
           (is (not (url? "")))
           (is (not (url? nil)))))
