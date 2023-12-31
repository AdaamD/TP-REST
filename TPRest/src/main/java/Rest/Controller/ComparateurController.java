package Rest.Controller;

import Rest.Exception.NoOfferException;
import Rest.Models.Offre;
import Rest.Service.ComparateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/comparateur")

public class ComparateurController {

    private final ComparateurService comparateurService;

    private final ObjectMapper objectMapper;


    @Autowired
    public ComparateurController( ComparateurService comparateurService, ObjectMapper objectMapper) {
        this.comparateurService = comparateurService;
        this.objectMapper = objectMapper;
    }

// Service de Comparaison d'offre
    @PostMapping("/compare")
    public ResponseEntity<String> compareOffers(
            @RequestParam String dateDebut,
            @RequestParam String dateFin,
            @RequestParam int nombrePersonnes,
            @RequestParam int nombreEtoiles) {

        // logique métier pour comparer les offres
        List<Offre> comparedOffers = comparateurService.compareOffers(dateDebut, dateFin, nombrePersonnes, nombreEtoiles);

        if (comparedOffers.isEmpty()) {
            throw new NoOfferException("Aucune offre disponible pour les critères spécifiés.");
        }


        // Conversions les offres en format JSON pour la page html
        try {
            String formattedOffers = objectMapper.writeValueAsString(comparedOffers);
            return new ResponseEntity<>(formattedOffers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//Gestion des exceptions
    @ExceptionHandler(NoOfferException.class)
    public ResponseEntity<String> handleNoOfferException(NoOfferException ex) {
        // Vous pouvez personnaliser le message d'erreur ici si nécessaire
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

}