(ns crossroads.core-test
  (:require [clojure.test :refer :all]
            [crossroads.core :as crossroads]
            [clojure.java.shell :as sh]
            [ring.util.response :as ring-resp]
            [mock-clj.core :as mock-clj]))


(def side-effects-folder "side-effects")
(def side-effect-file-1 "test-file.txt")
(def full-side-effect-file-path (format "%s/%s" side-effects-folder side-effect-file-1))

(defn setup []
  (sh/sh "mkdir" side-effects-folder)
  (sh/sh "touch" full-side-effect-file-path))

(defn cleanup []
  (sh/sh "rm" "-rf" side-effects-folder))

(use-fixtures :each (fn [f]
                      (setup)
                      (f)
                      (cleanup)))


(deftest db-layer-write-fn!-test
  (testing "should do a side effect"
    (crossroads/db-layer-write-fn! full-side-effect-file-path "1 abc")
    (is (= "1 abc"
           (:out (sh/sh "cat" full-side-effect-file-path))))))

(deftest web-handler-layer-fn-test
  (testing "should return a happy response in happy path"
    (is (= (ring-resp/response "ok")
           (crossroads/web-handler-layer-fn full-side-effect-file-path "doesn't matter"))))

  (testing "should return a sad response if filename doesn't exist"
    (is (= (ring-resp/bad-request "not ok")
           (crossroads/web-handler-layer-fn "bad-file" "doesn't matter"))))

  (testing "should return a sad response if random error occurs"
    (mock-clj/with-mock
      [crossroads/random-flaky-fn "sad thing happened"]
      (is (= (ring-resp/status 500)
             (crossroads/web-handler-layer-fn "doesn't matter" "also doesn't matter"))))))

