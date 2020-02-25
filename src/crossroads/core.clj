(ns crossroads.core
  (:gen-class)
  (:require [crossroads.sadpath :as sadpath]))

(defn file-exists? [filename]
  (.exists (clojure.java.io/as-file filename)))

(defn random-flaky-fn []
  "let's pretend i work up here")

(defn db-layer-write-fn! [filename record]
  (if (file-exists? filename)
    (do (spit filename record :append true)
        {:error nil :data "ok"})
    {:error ::file-does-not-exist :data nil}))

(defn just-some-filler-fn [filename record]
  (if (= "sad thing happened" (random-flaky-fn))
    {:error ::flaky-fn-flaked :data nil}
    (db-layer-write-fn! filename record)))


;; This function should be the only one who knows about the ring-resp library
(defn web-handler-layer-fn [filename record]
  (sadpath/handle (just-some-filler-fn filename record)))

