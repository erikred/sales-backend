package pe.arguz.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.arguz.sales.model.Sale;
import pe.arguz.sales.response.SaleResponseRest;
import pe.arguz.sales.services.ISaleService;

@RestController
@RequestMapping("/api/v1")
public class SaleRestController {
    private final ISaleService saleService;

    @Autowired
    public SaleRestController(ISaleService saleService){
        this.saleService = saleService;
    }

    /**
     * Obtiene todas las ventas.
     * @return Un Objeto ResponseEntity<CategoryResponseRest> con la respuesta y la metadata
     */
    @GetMapping("/sales")
    public ResponseEntity<SaleResponseRest> searchSales(){
        return saleService.search();
    }

    /**
     * Obtiene la venta por ID.
     * @param id
     * @return Un Objeto ResponseEntity<CategoryResponseRest> con la respuesta y la metadata
     */
    @GetMapping("/sales/{id}")
    public  ResponseEntity<SaleResponseRest> searchSalesById(@PathVariable Long id){
        return saleService.searchById(id);
    }

    @PostMapping("/sales")
    public ResponseEntity<SaleResponseRest> save(@RequestBody Sale sale){
        return saleService.save(sale);
    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<SaleResponseRest> update(@RequestBody Sale sale, @PathVariable Long id){
        return saleService.update(sale, id);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<SaleResponseRest> delete(@PathVariable Long id) {
        return saleService.deleteById(id);
    }
}
