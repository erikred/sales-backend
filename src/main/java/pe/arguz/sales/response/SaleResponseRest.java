package pe.arguz.sales.response;

import lombok.Getter;
import lombok.Setter;
import pe.arguz.sales.model.Sale;

@Getter
@Setter
public class SaleResponseRest extends ResponseRest{
    private SaleResponse saleResponse = new SaleResponse();
}
