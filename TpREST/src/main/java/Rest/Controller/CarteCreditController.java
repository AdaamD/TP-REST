package Rest.Controller;


import Rest.Models.CarteCredit;
import Rest.Service.CarteCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cartes-credit")
public class CarteCreditController {

    private final CarteCreditService carteCreditService;

    @Autowired
    public CarteCreditController(CarteCreditService carteCreditService) {
        this.carteCreditService = carteCreditService;
    }

    @GetMapping("/{id}")
    public CarteCredit getCarteCreditById(@PathVariable Long id) {
        return carteCreditService.getCarteCreditById(id);
    }

    @GetMapping
    public List<CarteCredit> getAllCartesCredit() {
        return carteCreditService.getAllCartesCredit();
    }

    // Ajoutez d'autres endpoints en fonction de vos besoins

}
