(ns ins-app.core
  (:use [compojure.core :only [defroutes]])
  (:use [ring.middleware.reload :only [wrap-reload]])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [ins-app.controllers.apps]))

(defroutes routes
  ins-app.controllers.apps/routes
  (route/resources "/"))

(def application
  (-> (handler/site routes)
      (wrap-reload)))

(defn start [port]
  (ring/run-jetty
   (var application)
   {:port (or port 8080) :join? false}))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (start port)))
