(ns crossroads.core
  (:gen-class)
  (:require [ring.util.response :as ring-resp]
            [crossroads.sadpath :as sadpath]))

(defn file-exists? [filename]
  (.exists (clojure.java.io/as-file filename)))

(defn random-flaky-fn []
  "let's pretend i work up here")

(defn db-layer-write-fn! [filename record]
  (if (file-exists? filename)
    (do (spit filename record :append true)
        {:error nil :data "ok"})
    {:error ::bad-request-not-ok :data nil}))

(defn just-some-pure-function [filename record]
  (println "I'm just here to be an extra layer of complexity")
  (if (= "sad thing happened" (random-flaky-fn))
    {:error (ring-resp/status 500) :data nil}
    (db-layer-write-fn! filename record)))


;; This function should be the only one who knows about the ring-resp library
(defn web-handler-layer-fn [filename record]
  (let [{:keys [error data]} (just-some-pure-function filename record)]
    (if (nil? error)
      (ring-resp/response data)
      (sadpath/handle error data))))

