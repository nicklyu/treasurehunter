package game.treasurehunter.wrapper;

import com.vividsolutions.jts.geom.Geometry;
import game.treasurehunter.model.Level;
import game.treasurehunter.model.Tip;
import org.geotools.data.DataUtilities;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.stereotype.Component;

@Component
public class FeatureWrapper {
    final SimpleFeatureType tipType = DataUtilities.createType("Tip",
            "tip:Point:srid-4326," + // <- the geometry attribute: Point type
                    "name:String," +
                    "description:String," + // <- a String attribute
                    "number:Integer, " +
                    "type:String"   // a number attribute
    );

    final SimpleFeatureType areaType = DataUtilities.createType("Area",
                    "area:Polygon:srid-4326," +
                            "name:String," +
                            "description:String," +
                            "type:String"
    );

    final SimpleFeatureType treasureType = DataUtilities.createType("Treasure",
                            "treasure:Point:srid-4326," +
                                    "type:String"
    );

    public FeatureWrapper() throws SchemaException {
    }

    public SimpleFeature getTipFeature(Tip tip, Integer number){
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(tipType);
        featureBuilder.add(tip.getPoint());
        featureBuilder.add(tip.getName());
        featureBuilder.add(tip.getDescription());
        featureBuilder.add(number);
        featureBuilder.add("tip");
        return featureBuilder.buildFeature(null);
    }

    public SimpleFeature getAreaFeature(Level level){
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(areaType);
        featureBuilder.add(level.getLevelData().getArea());
        featureBuilder.add(level.getName());
        featureBuilder.add(level.getDescription());
        featureBuilder.add("area");
        return featureBuilder.buildFeature(level.getId().toString());

    }

    public SimpleFeature getTreasureFeature(Geometry treasure){
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(treasureType);
        featureBuilder.add(treasure);
        featureBuilder.add("treasure");
        return featureBuilder.buildFeature(null);

    }
}
