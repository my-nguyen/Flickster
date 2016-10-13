package com.nguyen.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by My on 10/12/2016.
 */

public class Genres extends HashMap<Integer, String> {
   private static Genres sInstance;

   public static Genres getInstance() {
      return sInstance;
   }

   public Genres(JSONArray array) {
      for (int i = 0; i < array.length(); i++) {
         try {
            JSONObject object = array.getJSONObject(i);
            int id = object.getInt("id");
            String name = object.getString("name");
            put(id, name);
         } catch (JSONException e) {
            e.printStackTrace();
         }
      }

      sInstance = this;
   }
}
