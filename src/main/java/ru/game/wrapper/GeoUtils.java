package ru.game.wrapper;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class GeoUtils {
    public static JsonArray formatLevelsInfo(JsonArray levels) {
        JsonArray result = new JsonArray();
        for (int i = 0; i < levels.size(); i++) {
            JsonObject formattedLevel = new JsonObject();
            formattedLevel.put("id", levels.getJsonArray(i).getInteger(0));
            formattedLevel.put("name", levels.getJsonArray(i).getString(1));
            formattedLevel.put("description", levels.getJsonArray(i).getString(2));
            result.add(formattedLevel);
        }
        return result;
    }

    public static String levelToGeoJson(JsonObject level){
        String geoJson = "{ \"type\": \"FeatureCollection\",";
        geoJson += "\"features\": [\n" +
                "      { \"type\": \"Feature\",";
        geoJson += " \"geometry\": " + level.getString("area");
        geoJson += ", \"properties\": { \"id\" : "+level.getInteger("id")
                +" , \"name\" : \""+ level.getString("name")
                +"\" , \"description\" : \""+ level.getString("description") + "\"} }, ";
        geoJson += " { \"type\": \"Feature\",\n" +
                "        \"geometry\": ";
        geoJson += level.getString("treasure") + ", \"properties\": {}}]}";
        geoJson = geoJson.replaceAll(" ", "");
        return geoJson;
    }

    public static String areaToGeoJson(JsonObject area){
        String geoJson = "{\n" +
                "      \"type\": \"Feature\",";
        geoJson += " \"geometry\": " + area.getString("area")
                +", \"properties\": {\n" +
                "        \"id\": " + area.getInteger("id") + "}}";
        return geoJson;
    }
}
