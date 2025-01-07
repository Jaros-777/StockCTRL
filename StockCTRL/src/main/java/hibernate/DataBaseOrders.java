package hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class DataBaseOrders {
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "status")
    private String orderStatus;
}
