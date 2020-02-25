(ns crossroads.core-test
  (:require [clojure.test :refer :all]
            [crossroads.core :as crossroads]
            [clojure.java.shell :as sh]))


(defn setup []
  (sh/sh "mkdir" "side-effects"))

(defn cleanup []
  (sh/sh "rm" "-rf" "side-effects"))

(use-fixtures :each (fn [f] (setup) (f) (cleanup)))



(deftest write-a-record-test
  (testing "should do a side effect"
    (crossroads/write-a-record! "side-effects/test-file.txt" "1 abc")
    (is (= "1 abc" (:out (sh/sh "cat" "side-effects/test-file.txt"))))))
