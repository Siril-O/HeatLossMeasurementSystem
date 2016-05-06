package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.services.*;

import java.beans.PropertyEditorSupport;

public class AbstractController {

    public static final String SLASH = "/";
    public static final String CREATE = "create";
    public static final String LIST = "list";
    public static final String REDIRECT = "redirect:";
    public static final String PAGED_LIST = ".paging.list";
    public static final String MANAGE = "manage";
    public static final String DOT = ".";
    public static final String HOUSE = "house";
    @Autowired
    private PipeService pipeService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private SensorModelService sensorModelService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private MeasurementSectionService measurementSectionService;


    private static <T> T getEntityById(CrudService<T> service, Long id) {
        T entity = service.findById(id);
        if (id <= 0)
            throw new IllegalArgumentException("ID < 0");
        if (entity == null) {
            throw new RuntimeException("Entity with Id: " + id + " not found");
        }
        return entity;
    }

    @InitBinder
    public void bindPipe(WebDataBinder binder) {
        binder.registerCustomEditor(Pipe.class, new PropertyEditorSupportById<>(pipeService));
    }


    @InitBinder
    public void bindHouse(WebDataBinder binder) {
        binder.registerCustomEditor(House.class, new PropertyEditorSupportById<>(houseService));
    }

    @InitBinder
    public void bindSensorModel(WebDataBinder binder) {
        binder.registerCustomEditor(SensorModel.class, new PropertyEditorSupportById<>(sensorModelService));
    }

    @InitBinder
    public void bindApartment(WebDataBinder binder) {
        binder.registerCustomEditor(Apartment.class, new PropertyEditorSupportById<>(apartmentService));
    }

    @InitBinder
    public void bindMeasurementSection(WebDataBinder binder) {
        binder.registerCustomEditor(MeasurementSection.class, new PropertyEditorSupportById<>(measurementSectionService));
    }

    public static class PropertyEditorSupportById<T> extends PropertyEditorSupport {

        private CrudService<T> crudService;

        public PropertyEditorSupportById(CrudService<T> crudService) {
            super();
            this.crudService = crudService;
        }

        @Override
        public void setAsText(String entityId) {
            T entity = null;
            if (entityId != null && !entityId.trim().isEmpty()) {
                Long id = Long.valueOf(entityId);
                entity = getEntityById(crudService, id);
            }
            setValue(entity);
        }

        public CrudService<T> getCrudService() {
            return crudService;
        }

        public void setCrudService(CrudService<T> crudService) {
            this.crudService = crudService;
        }
    }

    protected String redirectToManageHouse(House house, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("houseId", house.getId());
        return REDIRECT + SLASH + HOUSE + SLASH + MANAGE;
    }

}