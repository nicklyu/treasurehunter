package game.treasurehunter.model;

import com.vividsolutions.jts.geom.Geometry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tip")
@AllArgsConstructor
@NoArgsConstructor
public class Tip implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipid")
    @Getter @Setter
    private Long id;


    @Column(name="name")
    @Getter @Setter
    private String name;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @Column(name = "point", columnDefinition = "Geometry")
    @Getter @Setter
    private Geometry point;

    @ManyToOne
    @JoinColumn(name = "leveldataid", nullable = false)
    @Getter @Setter private LevelData levelData;
}
