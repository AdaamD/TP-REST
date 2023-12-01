package Rest.Controller;

import Rest.Data.DataInitialization;
import Rest.Exception.NoOfferException;
import Rest.Models.Offre;
import Rest.Models.Reservation;
import Rest.Repository.ReservationRepository;
import Rest.Service.RechercheChambreService;
import Rest.Service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@CrossOrigin(origins = "http://localhost:8080")
@RestController
    @RequestMapping("/agences")
public class AgenceController {

    private final RechercheChambreService rechercheChambreService;
    private final DataInitialization dataInitialization;

    private final ReservationRepository reservationRepository ;
    private final ReservationService reservationService ;

    @Autowired
    public AgenceController(RechercheChambreService rechercheChambreService, DataInitialization dataInitialization, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.rechercheChambreService = rechercheChambreService;
        this.dataInitialization = dataInitialization;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

// Service de Consultation de Chambre
    @GetMapping("/consulter-disponibilite")
    public ResponseEntity<String> consulterDisponibilite(
            @RequestParam String identifiant,
            @RequestParam String motDePasse,
            @RequestParam String dateDebut,
            @RequestParam int nombreLit) {

        // Vérifiez l'authentification
        boolean credentialsValid = dataInitialization.validateCredentials(identifiant, motDePasse);

        if (!credentialsValid) {
            return new ResponseEntity<>("L'authentification a échoué. Veuillez vérifier vos informations.", HttpStatus.UNAUTHORIZED);
        }

        // logique métier pour consulter les disponibilités
        List<Offre> availableOffers = rechercheChambreService.rechercheOffreParAgence(identifiant, dateDebut, nombreLit);

        if (availableOffers.isEmpty()) {
            throw new NoOfferException("Aucune offre disponible pour les critères spécifiés.");
        }

        /* Convertion les offres en format JSON pour la page html */
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String formattedOffers = objectMapper.writeValueAsString(availableOffers);
            return new ResponseEntity<>(formattedOffers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// Service de Réservation de Chambre
    @ResponseBody
    @PostMapping("/effectuerReservation")
    public ResponseEntity<String> effectuerReservation(
            @RequestParam String nomAgence,
            @RequestParam String mdpAgence,
            @RequestParam Long offreId,
            @RequestParam String dateArrive,
            @RequestParam String dateDepart,
            @RequestParam String nomClient,
            @RequestParam String prenomClient,
            @RequestParam(required = false) String emailClient,
            @RequestParam(required = false) Integer telephoneClient,
            @RequestParam(required = false) Integer numeroCarte,
            @RequestParam(required = false) String expirationCarte,
            @RequestParam(required = false) Integer codeSecurite) {

        int telephoneInt = (telephoneClient != null) ? telephoneClient.intValue() : 0;
        int numeroCarteInt = (numeroCarte != null) ? numeroCarte.intValue() : 0;
        int codeSecuriteInt = (codeSecurite != null) ? codeSecurite.intValue() : 0;

        return reservationService.effectuerReservation(
                nomAgence, mdpAgence, offreId,
                dateArrive, dateDepart,
                nomClient, prenomClient,
                emailClient, telephoneInt,
                numeroCarteInt, expirationCarte, codeSecuriteInt);
    }



// Service de Rercherche de client existant
    @ResponseBody
    @GetMapping("/verifierClient")
    public ResponseEntity<String> verifierClient(
            @RequestParam String nomAgence,
            @RequestParam String mdpAgence,
            @RequestParam String nomClient,
            @RequestParam String prenomClient) {
        return reservationService.existanceClient(nomAgence, mdpAgence, nomClient, prenomClient);
    }

// Service de Consultation de Réservations
    @GetMapping("/consulterreservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

// Gestion des Exceptions
    @ExceptionHandler(NoOfferException.class)
    public ResponseEntity<String> handleNoOfferException(NoOfferException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
