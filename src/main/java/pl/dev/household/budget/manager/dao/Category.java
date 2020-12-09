package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

}
