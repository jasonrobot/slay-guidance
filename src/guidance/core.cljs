(ns guidance.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rd]))

(enable-console-print!)

(println "This text is printed from src/guidance/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn render-attr [name value]
  [:div.attribute {:key name}
   [:span.name name] ": " [:span.value value]])

(defn spell-attrs [attrs]
  "Show the name and value for a spell attribute."
    [:div.spell-attrs (map #(apply render-attr %) attrs)])

(defn guidance []
  (let [audio-clip (new js/Audio "./guidance.ogg")
        play-clip #(->> "./guidance.ogg" (new js/Audio) (.play))]
    [:div.guidance
     [:a.d4 {:on-click play-clip}
      [:img.d4-img {:src "./d4.png"}]]
     [:div.short-description "Add 1d4 to your ability check."]
     [:div.full-description
      [:div.spell-name "Guidance"] ;needs a small-caps font
      [:div.italic "Divination cantrip"]
      (spell-attrs [["Casting Time" "1 action"]
                    ["Range" "Touch"]
                    ["Components" "V, S"]
                    ["Duration" "Concentration, up to 1 minute"]])
      [:div.full-text "You touch one willing creature. Once before the spell ends, the target can roll a d4 and add the number rolled to one ability check of its choice. It can roll the die before or after making the ability check. The spell then ends."]]]))

(rd/render [guidance]
           (. js/document (getElementById "app")))