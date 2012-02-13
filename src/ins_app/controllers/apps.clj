(ns ins-app.controllers.apps
  (:use [compojure.core :only [defroutes GET POST]])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [ins-app.views.apps :as view]))

(defn new-form []
  (view/new-form))

(defn show-success []
  (view/success))

(def non-empty [#(empty? %) "Cannot be empty"])

(def validators
  {:first-name non-empty
   :last-name non-empty})

(defn validate-one [[f v]]
  (when-let [[valid? msg] (validators f)]
    (if (valid? v)
      [f v msg])))

(defn validate [params]
  (let [errors (filter seq (map validate-one params))]
    (apply hash-map (interleave (map first errors) (map rest errors)))))

(defn params->vec [params]
  (vec (map (fn [[k v]] [k [v nil]]) params)))

(defn vec->map [pv]
  (apply hash-map (apply concat pv)))

(defn handle-post [params]
  (let [errors (validate params)]
    (if (seq errors)
      (view/new-form-with-errors
        (merge (-> params params->vec vec->map)
               (validate params)))
      (ring/redirect "/success"))))

(defroutes routes
  (GET "/" [] (new-form))
  (GET "/success" [] (show-success))
  (POST "/" {params :params} (handle-post params)))