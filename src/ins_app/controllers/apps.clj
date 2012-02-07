(ns ins-app.controllers.apps
  (:use [compojure.core :only [defroutes GET]])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [ins-app.views.apps :as view]))

(defn new-form []
  (view/new-form))

(defroutes routes
  (GET "/" [] (new-form)))