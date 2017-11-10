package game.treasurehunter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    @Getter @Setter private Long id;

    @Column(name = "username")
    @Getter @Setter private String name;

    @OneToMany(mappedBy = "account")
    @Getter @Setter private List<Level> levels;
}
