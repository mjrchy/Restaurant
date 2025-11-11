package ku.restaurant.controller;


import jakarta.validation.Valid;
import ku.restaurant.dto.RestaurantRequest;
import ku.restaurant.entity.Restaurant;
import ku.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public Page<Restaurant> getAllRestaurants(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(StringUtils.isEmpty(sortBy)) sortBy ="name";


        return restaurantService.getRestaurantsPage(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
    }


    @PostMapping("/restaurants")
    public Restaurant create(@Valid @RequestBody RestaurantRequest restaurant) {
        return restaurantService.create(restaurant);
    }

    @PutMapping("/restaurants")
    public Restaurant update(@RequestBody Restaurant restaurant) {
        return restaurantService.update(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    public Restaurant delete(@PathVariable UUID id) {
        return restaurantService.delete(id);
    }

    @GetMapping("/restaurants/name/{name}")
    public Restaurant getRestaurantByName(@PathVariable String name) {
        return restaurantService.getRestaurantByName(name);
    }

    @GetMapping("/restaurants/location/{location}")
    public List<Restaurant> getRestaurantByLocation(@PathVariable String location) {
        return restaurantService.getRestaurantByLocation(location);
    }


}
