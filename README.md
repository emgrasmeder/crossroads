# Crossroads

Playing with the concept of the sadpath in Clojure

Ideal implementation:

```clojure
(ns hello-world)

(defn fn-which-handles-this-error []
  (println "ruh roh"))

(defn do-a-thing [& args]
  {:error false :data "haha"})
  
  (defn do-another-thing [& args]
  {:error ::i-always-fail-for-some-reason :data "haha"})

(defn sad-path-handler [{:keys [error data] :as result}]
  (case error
    :hello-world/i-always-fail-for-some-reason (fn-which-handles-this-error)
    (if (nil? error)
      (ring-resp/response data))
  
(defn main-function [] 
  (while-happy-:> 
    (do-a-thing some args go here)
    (do-another-thing more-args)
    (note-this-isn't-a-threading-macro "hello" 123)))


;; register our custom sad-path-handler (while-happy->) so it short-circuits 
;; whenever any of its fn returns data with a non-nil :error key
(register-sad-path-handler sad-path-handler)
```

