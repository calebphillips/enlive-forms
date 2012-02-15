(ns ins-app.controller
  (:use [compojure.core :only [defroutes GET POST]])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [ins-app.view :as view]
            [ins-app.validation :as valid]))

(defn handle-post [params]
  (let [[has-errors? params-with-messsages] (valid/add-messages params)]
    (if has-errors?
      (view/new-form-with-errors params-with-messsages)
      (ring/redirect "/success"))))

(defroutes routes
  (GET "/" [] (view/new-form))
  (GET "/success" [] (view/success))
  (POST "/" {params :params} (handle-post params)))