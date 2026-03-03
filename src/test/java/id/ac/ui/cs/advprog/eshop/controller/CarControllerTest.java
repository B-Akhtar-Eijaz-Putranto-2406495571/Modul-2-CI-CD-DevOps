package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarReadService;
import id.ac.ui.cs.advprog.eshop.service.CarWriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarReadService carReadService;

    @Mock
    private CarWriteService carWriteService;

    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carController = new CarController(carReadService, carWriteService);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("car"))
                .andExpect(view().name("createCar"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Pajero Sport")
                        .param("carColor", "Hitam")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:listCar"));

        verify(carWriteService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        Car car = new Car();
        List<Car> allCars = Arrays.asList(car);

        when(carReadService.findAll()).thenReturn(allCars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cars", allCars))
                .andExpect(view().name("carList"));
    }

    @Test
    void testEditCarPage() throws Exception {
        String id = UUID.randomUUID().toString();
        Car car = new Car();

        when(carReadService.findById(id)).thenReturn(car);

        mockMvc.perform(get("/car/editCar/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("car", car))
                .andExpect(view().name("editCar"));
    }

    @Test
    void testEditCarPost() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(post("/car/editCar")
                        .param("carId", id)
                        .param("carName", "Pajero Dakar")
                        .param("carColor", "Putih")
                        .param("carQuantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:listCar"));

        verify(carWriteService, times(1)).update(eq(id), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:listCar"));

        verify(carWriteService, times(1)).deleteCarById(id);
    }
}