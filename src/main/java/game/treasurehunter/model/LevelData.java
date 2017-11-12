package game.treasurehunter.model;

import com.vividsolutions.jts.geom.Geometry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "leveldata")
@AllArgsConstructor
@NoArgsConstructor
public class LevelData implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataid")
    @Getter @Setter
    private Long id;

    @Column(name = "treasurelocation", columnDefinition = "Geometry")
    @Getter @Setter
    private Geometry treasureLocation;

    @Column(name = "tipscount")
    @Getter @Setter private Integer tipsCount;



    @Column(name = "area", columnDefinition = "Geometry")
    @Getter @Setter private Geometry area;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "levelid")
    @Getter @Setter private Level level;


}
