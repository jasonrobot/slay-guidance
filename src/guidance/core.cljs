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

(defn render-spell-attrs [attrs]
  "Show the name and value for a spell attribute."
  [:div.spell-attrs (map #(apply render-attr %) attrs)])

(defn spell [{:keys [image audio name type short-desc long-desc spell-attrs]}]
  [:div.spell
   [:a {:on-click (when audio #(->> audio (new js/Audio) (.play)))}
    [:img.spell_image {:src image}]]
   (when short-desc [:div.spell_short-description short-desc])
   (when (some (complement nil?) [name type spell-attrs long-desc])
     [:div.spell_full-description
      (when name [:div.spell_name name])
      (when type [:div.italic "Divination cantrip"])
      (when spell-attrs (render-spell-attrs spell-attrs))
      (when long-desc [:div.full-text long-desc])])])

(defn guidance []
  (spell {:image "./d4.png"
          :audio "./guidance.ogg"
          :name "Guidance"
          :type "Divination cantrip"
          :short-desc "Add 1d4 to your ability check."
          :long-desc "You touch one willing creature. Once before the spell ends, the target can roll a d4 and add the number rolled to one ability check of its choice. It can roll the die before or after making the ability check. The spell then ends."
          :spell-attrs [["Casting Time" "1 action"]
                        ["Range" "Touch"]
                        ["Components" "V, S"]
                        ["Duration" "Concentration, up to 1 minute"]]}))

(rd/render [guidance]
           (. js/document (getElementById "app")))