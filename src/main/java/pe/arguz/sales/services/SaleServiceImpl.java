package pe.arguz.sales.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.arguz.sales.dao.ISaleDao;
import pe.arguz.sales.model.DetailSale;
import pe.arguz.sales.model.Sale;
import pe.arguz.sales.response.SaleResponseRest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SaleServiceImpl implements ISaleService{
    public static final String RESPUESTA_FALLIDA = "Respuesta fallida";
    public static final String RESPUESTA_OK = "Respuesta ok";
    public static final String ERROR = "Error: ";

    private final ISaleDao saleDao;

    @Autowired
    public SaleServiceImpl(ISaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<SaleResponseRest> search() {
        SaleResponseRest responseRest = new SaleResponseRest();
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            List<Sale> saleList = (List<Sale>) saleDao.findAll();
            responseRest.getSaleResponse().setSales(saleList);
            responseRest.setMetadata(RESPUESTA_OK,"200", "Ventas obtenidas");
        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA,"500", "Error al consultar ventas");
            logger.severe(ERROR + e.getMessage());
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<SaleResponseRest> searchById(Long id) {
        SaleResponseRest responseRest = new SaleResponseRest();
        List<Sale> saleList = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            Optional<Sale> sale = saleDao.findById(id);
            if(sale.isPresent()){
                saleList.add(sale.get());
                responseRest.getSaleResponse().setSales(saleList);
                responseRest.setMetadata(RESPUESTA_OK,"200", "Venta encontrada");
            }else {
                responseRest.setMetadata(RESPUESTA_FALLIDA,"404", "No se encontró venta con el ID solicitado");
                return new ResponseEntity<>(responseRest, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA,"500", "Error al consultar por ID");
            logger.severe(ERROR + e.getMessage());
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<SaleResponseRest> save(Sale sale) {
        SaleResponseRest responseRest = new SaleResponseRest();
        List<Sale> saleList = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            sale.getProducts().forEach(sale::addDetail);
            sale.setTotal(sale.getProducts().stream().mapToDouble(DetailSale::calculateSubTotal).sum());
            logger.info("Valor: "+sale.getTotal());
            Sale saleSaved = saleDao.save(sale);
            if (saleSaved != null) {
                saleList.add(saleSaved);
                responseRest.getSaleResponse().setSales(saleList);
                responseRest.setMetadata(RESPUESTA_OK,"200", "Venta guardada");
            }else {
                responseRest.setMetadata(RESPUESTA_FALLIDA,"400", "No se guardó venta");
                return new ResponseEntity<>(responseRest, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA,"500", "Error al guardar venta");
            logger.severe(ERROR + e);
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<SaleResponseRest> update(Sale sale, Long id) {
        SaleResponseRest responseRest = new SaleResponseRest();
        List<Sale> list = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());
        try {

            Optional<Sale> saleSearch = saleDao.findById(id);

            if (saleSearch.isPresent()) {
                // se procederá a actualizar el registro de nombre cliente y fecha
                saleSearch.get().setClientName(sale.getClientName());
                saleSearch.get().setDate(sale.getDate());

                Sale saleToUpdate = saleDao.save(saleSearch.get());

                if (saleToUpdate != null) {
                    list.add(saleToUpdate);
                    responseRest.getSaleResponse().setSales(list);
                    responseRest.setMetadata(RESPUESTA_OK, "200", "Venta actualizada");
                } else {
                    responseRest.setMetadata(RESPUESTA_FALLIDA, "400", "Venta no actualizada");
                    return new ResponseEntity<>(responseRest, HttpStatus.BAD_REQUEST);
                }


            } else {
                responseRest.setMetadata(RESPUESTA_FALLIDA, "404", "Venta no encontrada");
                return new ResponseEntity<>(responseRest, HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            responseRest.setMetadata(RESPUESTA_FALLIDA, "500", "Error al actualizar venta");
            logger.severe(ERROR + e);
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);


        }

        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<SaleResponseRest> deleteById(Long id) {
        SaleResponseRest responseRest = new SaleResponseRest();
        Logger logger = Logger.getLogger(getClass().getName());
        try{
            Optional<Sale> saleDeleted = saleDao.findById(id);
            if (saleDeleted.isPresent()) {
                // se procederá a eliminar venta
                saleDao.deleteById(id);
                responseRest.setMetadata(RESPUESTA_OK, "200", "Venta eliminada");
            }else {
                responseRest.setMetadata(RESPUESTA_FALLIDA, "404", "Venta no encontrada");
                return new ResponseEntity<>(responseRest, HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA, "500", "Error al eliminar venta");
            logger.severe(ERROR + e);
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }
}
