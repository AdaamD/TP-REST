package Rest.Controller;

import Rest.Data.DataInitialization;
import Rest.Exception.NoOfferException;
import Rest.Exception.UnauthorizedException;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            return new ResponseEntity<>("L'authentification a échoué. Veuillez vérifier vos informations.", HttpStatus.UNAUTHORIZED);
        }

        // Votre logique métier pour consulter les disponibilités
        List<Offre> availableOffers = rechercheChambreService.getAvailableOffersByAgence(identifiant, dateDebut, nombreLit);

        if (availableOffers.isEmpty()) {
            throw new NoOfferException("Aucune offre disponible pour les critères spécifiés.");
        }

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




        return reservationService.makeReservation(
                nomAgence, mdpAgence, offreId,
                dateArrive, dateDepart,
                nomClient, prenomClient,
                emailClient, telephoneInt,
                numeroCarteInt, expirationCarte, codeSecuriteInt);
    }

    // Ajoutez cette fonction pour convertir la date d'expiration de la carte de chaîne à Date
    private Date parseDate(String expirationCarte) {
        try {
            if (expirationCarte != null) {
                return new SimpleDateFormat("MM/yyyy").parse(expirationCarte);
            } else {
                // Gérez le cas où expirationCarte est null, par exemple, en retournant null ou une date par défaut.
                return null; // ou throw new IllegalArgumentException("expirationCarte ne peut pas être null");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Format de date incorrect pour la carte de crédit");
        }
    }


    @ResponseBody
    @GetMapping("/verifierClient")
    public ResponseEntity<String> verifierClient(
            @RequestParam String nomAgence,
            @RequestParam String mdpAgence,
            @RequestParam String nomClient,
            @RequestParam String prenomClient) {

        return reservationService.verifyClient(nomAgence, mdpAgence, nomClient, prenomClient);
    }


    @GetMapping("/consulterreservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    private Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (ParseException e) {
            // Gérer l'erreur de conversion
            return null;
        }
    }

    @ExceptionHandler(NoOfferException.class)
    public ResponseEntity<String> handleNoOfferException(NoOfferException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
