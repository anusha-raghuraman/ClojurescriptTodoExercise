(ns todolist.core
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce app-state (atom {:text "ToDoList"
                          :textval {:name ""}}))


;;this function lists the task in the body

(defn listtask [task]
 [:div
 [:h4 (:name @task)]])

(defn handle-add-list [state]
  (let [val (:value @state)]
    (if val
      (swap! app-state assoc-in [:textval val] {:name val }))
      (swap! state assoc :value nil)))


(defn add-component []
  (let [private-state (atom {:value nil})]
    (fn []
      [:div
      [:input {:type :text
               :value (:value @private-state)
               :on-change
                #(swap! private-state assoc :value (-> % .-target .-value))}]
      [:button.btn.btn-default
        {:on-click #(handle-add-list private-state)} "Add To Your List"]])))

(defn my-page []
  [:div
  [:h2 (:text @app-state)]
  [add-component]
    ;;This piece of code adds to the list.
    (for [k (keys (:textval @app-state))]
     ^{:key k} [listtask (reagent/cursor app-state [:textval k])])
   ]
  )

(reagent/render-component [my-page]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])

