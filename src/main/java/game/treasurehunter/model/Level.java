package game.treasurehunter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Level")
@AllArgsConstructor
@NoArgsConstructor
public class Level implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "levelid")
    @Getter @Setter private Long id;



    @Column(name = "levelname")
    @Getter @Setter private String name;

    @Column(name = "leveldiscription")
    @Getter @Setter private String discription;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @Getter @Setter private Account account;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "level", cascade = CascadeType.ALL)
    @Getter @Setter private LevelData levelData;

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discription='" + discription + '\'' +

                '}';
    }
}
