package pe.arguz.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "detail_sale")
@NoArgsConstructor
@Builder()
@AllArgsConstructor()
public class DetailSale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@ManyToOne()
    //@JoinColumn(name = "sale_id")
    @ManyToOne()
    @JsonIgnore
    private Sale sale;
    @Column(length = 100)
    private String productName;
    @Column
    private Double quantity;
    @Column(precision = 5)
    private Double price;

    @Column
    @Formula("quantity * price")
    private Double subTotal;
}
