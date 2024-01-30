package pe.arguz.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Entity
@Table(name = "detail_sale")
public class DetailSale implements Serializable {

    @PrePersist
    public void prePersist() {
        subTotal= quantity*price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JsonIgnore
    private Sale sale;
    @Column(length = 100)
    private String productName;
    @Column(precision = 5)
    private Double quantity;
    @Column(precision = 5)
    private Double price;

    @Column(precision = 5)
    private Double subTotal;


    public Double calculateSubTotal() {
        return quantity*price;
    }
}
