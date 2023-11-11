package Rest.Controller;

import Rest.Models.Agence;
import Rest.Service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agences")
public class AgenceController {

    private final AgenceService agenceService;

    @Autowired
    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @GetMapping("/{id}")
    public Agence getAgenceById(@PathVariable Long id) {
        return agenceService.getAgenceById(id);
    }

    @GetMapping
    public List<Agence> getAllAgences() {
        return agenceService.getAllAgences();
    }

    // Ajoutez d'autres endpoints en fonction de vos besoins

}
