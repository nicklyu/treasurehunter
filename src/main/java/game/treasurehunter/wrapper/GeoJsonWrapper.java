package game.treasurehunter.wrapper;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Component
public class GeoJsonWrapper {
    private FeatureJSON featureJSON = new FeatureJSON();

    public String wrapFeature(SimpleFeature feature) throws IOException {
        StringWriter writer = new StringWriter();
        featureJSON.writeFeature(feature, writer);
        return writer.toString();
    }

    public String wrapFeatureCollection(List<SimpleFeature> features) throws IOException {
        StringWriter writer = new StringWriter();
        SimpleFeatureCollection collection = DataUtilities.collection(features);
        featureJSON.writeFeatureCollection(collection, writer);
        return writer.toString();
    }
}
