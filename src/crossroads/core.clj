(ns crossroads.core
  (:gen-class)
  (:require [ring.util.response :as ring-resp]))

(defn file-exists? [filename]
  (.exists (clojure.java.io/as-file filename)))

(defn write-a-record! [filename record]
  (if (file-exists? filename)
    (do (spit filename record :append true)
        (ring-resp/response "ok"))
    (ring-resp/bad-request "not ok")))

(defn pretend-i-got-a-POST-request [filename record]
  (write-a-record! filename record))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

