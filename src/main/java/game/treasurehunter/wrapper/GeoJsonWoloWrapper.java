package game.treasurehunter.wrapper;

import com.vividsolutions.jts.geom.Geometry;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

public class GeoJsonWoloWrapper {

    private static GeoJSONWriter writer = new GeoJSONWriter();

    public static GeoJSON GeometryWrapper(Geometry g){
        return writer.write(g);
    }
}
