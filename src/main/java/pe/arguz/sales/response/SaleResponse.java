package pe.arguz.sales.response;

import lombok.Data;
import pe.arguz.sales.model.Sale;

import java.util.List;

@Data
public class SaleResponse {
    private List<Sale> sales;
}
