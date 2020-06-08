(ns guidance.server
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]))

(defroutes app
  (GET "/" [] (resp/file-response "index.html" {:root "public"}))
  ;; Compojure is dumb as hell, and I need to specify this
  (GET "/js/main.js" [] (resp/file-response "js/main.js" {:root "public"}))
  (route/resources "/")
  (route/not-found "<h1>Page not found</h1>"))