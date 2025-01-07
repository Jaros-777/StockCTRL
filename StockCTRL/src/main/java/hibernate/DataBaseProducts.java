package hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class DataBaseProducts {
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer price;
    @Column
    private String description;
}
