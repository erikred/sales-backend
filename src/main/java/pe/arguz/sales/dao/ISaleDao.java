package pe.arguz.sales.dao;

import org.springframework.data.repository.CrudRepository;
import pe.arguz.sales.model.Sale;

public interface ISaleDao extends CrudRepository<Sale, Long> {

}
