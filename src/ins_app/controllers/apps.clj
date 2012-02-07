(ns ins-app.controllers.apps
  (:use [compojure.core :only [defroutes GET POST]])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [ins-app.views.apps :as view]))

(defn new-form []
  (view/new-form))

(defn show-success []
  (view/success))

(defn validate [params]
  [])

(defn handle-post [params]
  (let [errors (validate params)]
    (if (seq errors)
      (view/new-form-with-errors errors)
      (ring/redirect "/success"))))

(defroutes routes
  (GET "/" [] (new-form))
  (GET "/success" [] (show-success))
  (POST "/" {params :params} (handle-post params)))