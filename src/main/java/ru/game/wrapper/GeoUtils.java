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
        String geoJson = "{\"type\":\"FeatureCollection\",";
        geoJson += "\"features\":[" +
                "{\"type\":\"Feature\",";
        geoJson += "\"geometry\":" + level.getString("area");
        geoJson += ",\"properties\":{\"id\":"+level.getInteger("id")
                +",\"name\":\""+ level.getString("name")
                +"\",\"description\":\""+ level.getString("description") + "\"}},";
        geoJson += "{\"type\":\"Feature\"," +
                "\"geometry\":";
        geoJson += level.getString("treasure") + ",\"properties\":{}}]}";
        return geoJson;
    }

    public static String areaToGeoJson(JsonObject area){
        String geoJson = "{" +
                "\"type\":\"Feature\",";
        geoJson += "\"geometry\":" + area.getString("area")
                +",\"properties\":{" +
                "\"id\":" + area.getInteger("id") + "}}";
        return geoJson;
    }

    public static String tipToGeoJson(JsonArray tip){
        String geoJson = "{" +
                "\"type\":\"Feature\",";
        geoJson += "\"geometry\":" + tip.getString(4)
                +",\"properties\":{" +
                "\"id\":"+ tip.getInteger(0) +
                ",\"level_id\":\""+ tip.getInteger(1)+
                "\",\"name\":\""+tip.getString(2)+
                "\",\"description\":\""+tip.getString(3)+ "\"}}";
        return geoJson;
    }

    public static String tipsToGeoJson(JsonArray tips){

        String geoJson = "{\"type\":\"FeatureCollection\",";
        geoJson += "\"features\":[";

        for(int i =0;i<tips.size()-1;i++){
            geoJson += tipToGeoJson(tips.getJsonArray(i)) + ",";
        }
        geoJson += tipToGeoJson(tips.getJsonArray(tips.size()-1));
        geoJson+="]}";
        return geoJson;
    }

    public static String treasureToGeoJson(JsonObject treasure){
        String geoJson = "{" +
                "\"type\":\"Feature\",";
        geoJson += "\"geometry\":" + treasure.getString("treasure")
                +",\"properties\":{" +
                "\"id\":" + treasure.getInteger("id") + "}}";
        return geoJson;
    }
}
