package pe.arguz.sales.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sale")
@NoArgsConstructor
public class Sale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "client_name")
    private String clientName;

    @Column
    private LocalDate date;
    //@OneToMany(cascade= CascadeType.ALL, targetEntity = DetailSale.class)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sale", cascade = CascadeType.PERSIST, orphanRemoval = true)
    //@JoinColumn(name="id")
    //@ListIndexBase()
    private List<DetailSale> products = new ArrayList<>();
    @Column(precision = 5)
    Double total;

    public void addDetail(DetailSale detailSale){
        detailSale.setSale(this);
    }


    public Double calculateTotal(){
        return products!=null? products.stream().mapToDouble(DetailSale::getSubTotal).sum() : 0.00;
    }
}
