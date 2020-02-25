(ns crossroads.sadpath
  (:require [ring.util.response :as ring-resp]))

(defn handle [{:keys [error data] :as result}]
  (case error
    :crossroads.core/flaky-fn-flaked (ring-resp/status 500)
    :crossroads.core/file-does-not-exist (ring-resp/bad-request "not ok")
    (if (nil? error)
      (ring-resp/response data))))