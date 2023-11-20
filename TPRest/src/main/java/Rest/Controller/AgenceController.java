package Rest.Controller;

import Rest.Data.DataInitialization;
import Rest.Models.Chambre;
import Rest.Models.Offre;
import Rest.Repository.OffreRepository;
import Rest.Service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agences")
public class AgenceController {

    private final AvailabilityService availabilityService;
    private final DataInitialization dataInitialization;
    private final OffreRepository offreRepository;

    @Autowired
    public AgenceController(AvailabilityService availabilityService, DataInitialization dataInitialization, OffreRepository offreRepository) {
        this.availabilityService = availabilityService;
        this.dataInitialization = dataInitialization;
        this.offreRepository = offreRepository;
    }

    @GetMapping("/consult-availability")
    public ResponseEntity<List<Offre>> consultAvailability(
            @RequestParam String identifiant,
            @RequestParam String motDePasse,
            @RequestParam String dateDebut,
            @RequestParam int nombreLit) {

        // Vérifiez l'identifiant et le mot de passe
        boolean credentialsValid = dataInitialization.validateCredentials(identifiant, motDePasse);

        if (!credentialsValid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Votre logique métier pour consulter les disponibilités
        List<Offre> availableOffers = availabilityService.getAvailableOffersByAgence(identifiant, dateDebut, nombreLit);

        return new ResponseEntity<>(availableOffers, HttpStatus.OK);
    }
}
