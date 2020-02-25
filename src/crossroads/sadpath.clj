(ns crossroads.sadpath
  (:require [ring.util.response :as ring-resp]))

(defn handle [error data]
  (if (= error :crossroads.core/bad-request-not-ok)
    (ring-resp/bad-request "not ok")
    error))