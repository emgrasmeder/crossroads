(ns crossroads.core-test
  (:require [clojure.test :refer :all]
            [crossroads.core :as crossroads]
            [clojure.java.shell :as sh]
            [ring.util.response :as ring-resp]))


(def side-effects-folder "side-effects")
(def side-effect-file-1 "test-file.txt")
(def full-side-effect-file-path (format "%s/%s" side-effects-folder side-effect-file-1))

(defn setup []
  (sh/sh "mkdir" side-effects-folder))

(defn cleanup []
  (sh/sh "rm" "-rf" side-effects-folder))

(use-fixtures :each (fn [f] (setup) (f) (cleanup)))


(deftest write-a-record-test
  (testing "should do a side effect"
    (crossroads/write-a-record! full-side-effect-file-path "1 abc")
    (is (= "1 abc"
           (:out (sh/sh "cat" full-side-effect-file-path))))))

(deftest pretend-i-got-a-POST-request-test
  (testing "should return a happy response"
    (is (= (ring-resp/response "ok")
           (crossroads/pretend-i-got-a-POST-request full-side-effect-file-path "doesn't matter")))))