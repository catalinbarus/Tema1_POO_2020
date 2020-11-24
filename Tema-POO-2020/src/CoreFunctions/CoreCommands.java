package CoreFunctions;

import fileio.*;

import java.util.Map;

public class CoreCommands {

    public void Add_Favorite(ShowInput clip, UserInputData user) {
        for(Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if(entry.getKey() == clip.getTitle()) {
                user.getFavoriteMovies().add(clip.getTitle());
            }
        }
    }

   public void Add_View(ShowInput clip, UserInputData user) {
       for(Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
           if(entry.getKey() == clip.getTitle()) {
               user.getHistory().put(entry.getKey(), user.getHistory().get(entry.getKey()) + 1);
           }
           else
           {
               user.getHistory().put(entry.getKey(), 1);
           }
       }
   }

    public void Add_Rating()
}
