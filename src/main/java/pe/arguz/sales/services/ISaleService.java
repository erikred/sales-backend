package pe.arguz.sales.services;

import org.springframework.http.ResponseEntity;
import pe.arguz.sales.model.Sale;
import pe.arguz.sales.response.SaleResponseRest;

public interface ISaleService {
    ResponseEntity<SaleResponseRest> search();
    ResponseEntity<SaleResponseRest> searchById(Long id);
    ResponseEntity<SaleResponseRest> save(Sale sale);

}
