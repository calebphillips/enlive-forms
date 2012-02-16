(ns ins-app.controller
  (:use [compojure.core :only [defroutes GET POST]])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [ins-app.view :as view]
            [ins-app.validation :as valid]))

(defn handle-post [params]
  (let [fields (valid/params->fields params)]
    (if (valid/any-errors? fields)
      (view/new-form-with-errors fields)
      (ring/redirect "/success"))))

(defroutes routes
  (GET "/" [] (view/new-form))
  (GET "/success" [] (view/success))
  (POST "/" {params :params} (handle-post params)))