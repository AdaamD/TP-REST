package Rest.Controller;

import Rest.Data.DataInitialization;
import Rest.Models.Client;
import Rest.Models.Offre;
import Rest.Models.Reservation;
import Rest.Repository.AgenceRepository;
import Rest.Repository.ClientRepository;
import Rest.Repository.OffreRepository;
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
import java.util.Optional;

/*
http://localhost:8080/agences/consult-availability?identifiant=yourUsername&motDePasse=yourPassword&dateDebut=2023-12-01&nombreLit=2
*/
@CrossOrigin(origins = "http://localhost:8080")
@RestController
    @RequestMapping("/agences")
public class AgenceController {

    private final RechercheChambreService rechercheChambreService;
    private final DataInitialization dataInitialization;
    private final OffreRepository offreRepository;
    private  final ClientRepository clientRepository ;
    private final ReservationRepository reservationRepository ;
    private final ReservationService reservationService ;

    @Autowired
    public AgenceController(RechercheChambreService rechercheChambreService, DataInitialization dataInitialization, OffreRepository offreRepository, AgenceRepository agenceRepository, ClientRepository clientRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.rechercheChambreService = rechercheChambreService;
        this.dataInitialization = dataInitialization;
        this.offreRepository = offreRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/consulter-disponibilite")
    public ResponseEntity<String> consulterDisponibilite(
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
        List<Offre> availableOffers = rechercheChambreService.getAvailableOffersByAgence(identifiant, dateDebut, nombreLit);

// Convertir les offres en format JSON pour la page html
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String formattedOffers = objectMapper.writeValueAsString(availableOffers);
            return new ResponseEntity<>(formattedOffers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/effectuerReservation")
    public ResponseEntity<String> effectuerReservation(
            @RequestParam String nomAgence,
            @RequestParam String mdpAgence,
            @RequestParam Long offreId,
            @RequestParam String nomClient,
            @RequestParam String prenomClient) {

        return reservationService.makeReservation(nomAgence, mdpAgence, offreId, nomClient, prenomClient);
    }


    @GetMapping("/consulterreservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}
