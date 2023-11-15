package Rest.Controller;

import Rest.Models.Agence;
import Rest.Models.Hotel;
import Rest.Repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/*  Ce service web va retourner un fihcier json avec les hotels partenaires de cete agence
 disponible quand tu écris ca : http://localhost:8080/agences/Agence%20Tourisme%20Aventure/hotels
* */

@RestController
@RequestMapping("/agences")
public class AgenceController {
    private final AgenceRepository agenceRepository;

    @Autowired
    public AgenceController(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    @GetMapping("/{nomAgence}/hotels")
    public ResponseEntity<List<Hotel>> getHotelsByAgence(@PathVariable String nomAgence) {
        Optional<Agence> agenceOptional = agenceRepository.findByNom(nomAgence);

        if (agenceOptional.isPresent()) {
            Agence agence = agenceOptional.get();
            List<Hotel> hotels = agence.getHotels();
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
