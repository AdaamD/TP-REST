package Rest.Controller;


import Rest.Models.Agence;
import Rest.Repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/agences")
public class Test2 {
    private final AgenceRepository agenceRepository;

    @Autowired
    public Test2(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }


    @GetMapping("/{nomAgence}")
    public Optional<Agence> getAgence(@PathVariable String nomAgence) {
        Optional<Agence> agenceOptional = agenceRepository.findByNom(nomAgence);
        return agenceOptional ;
    }


}
