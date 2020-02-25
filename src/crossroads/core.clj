(ns crossroads.core
  (:gen-class))

(defn write-a-record! [filename record]
  (spit filename record :append true))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))