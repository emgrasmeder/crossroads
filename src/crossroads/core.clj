(ns crossroads.core
  (:gen-class)
  (:require [ring.util.response :as ring-resp]))

(defn write-a-record! [filename record]
  (spit filename record :append true))

(defn pretend-i-got-a-POST-request [filename record]
  (write-a-record! filename record)
  (ring-resp/response "ok"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

