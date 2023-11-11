package Rest.Controller;

import Rest.Models.Adresse;
import Rest.Service.AdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adresses")
public class AdresseController {

    private final AdresseService adresseService;

    @Autowired
    public AdresseController(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @GetMapping("/{id}")
    public Adresse getAdresseById(@PathVariable Long id) {
        return adresseService.getAdresseById(id);
    }

    @GetMapping
    public List<Adresse> getAllAdresses() {
        return adresseService.getAllAdresses();
    }

    // Ajoutez d'autres endpoints en fonction de vos besoins

}
