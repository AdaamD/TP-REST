package Rest.Controller;

import Rest.Data.DataInitialization;
import Rest.Models.Client;
import Rest.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final DataInitialization dataInitialization;


    @Autowired
    public ReservationController(ReservationService reservationService, DataInitialization dataInitialization) {
        this.reservationService = reservationService;
        this.dataInitialization = dataInitialization;
    }

    @PutMapping("/make-reservation")
    public ResponseEntity<String> makeReservation(
            @RequestParam String agenceNom,
            @RequestParam String motDePasse,
            @RequestParam int offreId,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam double prixAPayer,
            @RequestParam String dateArrive,
            @RequestParam String dateDepart) {

        // Vérifiez l'identifiant et le mot de passe
        boolean credentialsValid = dataInitialization.validateCredentials(agenceNom, motDePasse);

        if (!credentialsValid) {
            return new ResponseEntity<>("Échec de la réservation. Identifiant ou mot de passe incorrect.", HttpStatus.UNAUTHORIZED);
        }

        // Vérifiez la disponibilité de l'offre
        ResponseEntity<String> availabilityCheck = reservationService.checkAvailability(offreId, dateArrive, dateDepart);
        if (availabilityCheck.getStatusCode() != HttpStatus.OK) {
            // Gérer l'échec de la réservation en raison de la disponibilité de l'offre
            return availabilityCheck;
        }

        // Effectuez la réservation
        ResponseEntity<String> reservationResult = reservationService.makeReservation(agenceNom, motDePasse, offreId, nom, prenom, Client) ;
//LA LIGNE DU DESSUS
        return reservationResult;
    }
}