package Rest.Controller;

import Rest.Data.DataInitialization;
import Rest.Models.Client;
import Rest.Models.Offre;
import Rest.Models.Reservation;
import Rest.Repository.AgenceRepository;
import Rest.Repository.ClientRepository;
import Rest.Repository.OffreRepository;
import Rest.Repository.ReservationRepository;
import Rest.Service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
http://localhost:8080/agences/consult-availability?identifiant=yourUsername&motDePasse=yourPassword&dateDebut=2023-12-01&nombreLit=2
*/

@RestController
@RequestMapping("/agences")
public class AgenceController {

    private final AvailabilityService availabilityService;
    private final DataInitialization dataInitialization;
    private final OffreRepository offreRepository;
    private  final ClientRepository clientRepository ;
    private final ReservationRepository reservationRepository ;

    @Autowired
    public AgenceController(AvailabilityService availabilityService, DataInitialization dataInitialization, OffreRepository offreRepository, AgenceRepository agenceRepository, ClientRepository clientRepository, ReservationRepository reservationRepository) {
        this.availabilityService = availabilityService;
        this.dataInitialization = dataInitialization;
        this.offreRepository = offreRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/consulter-disponibilite")
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
        List<Offre> offresDisponible = availabilityService.getAvailableOffersByAgence(identifiant, dateDebut, nombreLit);

        return new ResponseEntity<>(offresDisponible, HttpStatus.OK);
    }

    @PostMapping("/effectuerReservation")
    public ResponseEntity<String> effectuerReservation(
            @RequestParam String nomAgence,
            @RequestParam String mdpAgence,
            @RequestParam Long offreId,
            @RequestParam String nomClient,
            @RequestParam String prenomClient) {

        boolean authentificationValide = dataInitialization.validateCredentials(nomAgence, mdpAgence);
        if (!authentificationValide) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Rechercher le client par nom et prénom
        Optional<Client> clientOptional = clientRepository.findByNomAndPrenom(nomClient, prenomClient);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Le client spécifié n'existe pas");
        }

        // Vérifier l'existence de l'offre avec l'identifiant offreId
        Optional<Offre> offreOptional = offreRepository.findById(offreId);
        if (offreOptional.isEmpty()) {
            return ResponseEntity.status(404).body("L'offre spécifiée n'existe pas");
        }

        // Récupérer l'offre depuis l'Optional
        Offre offre = offreOptional.get();

        // Récupérer le client depuis l'Optional
        Client client = clientOptional.get();

        // Créer une réservation avec l'offre et le client
        Reservation reservation = new Reservation(
                client.getNom(),
                client.getPrenom(),
                client,
                offre.getPrix()
        );

        // Sauvegarder la réservation
        reservationRepository.save(reservation);

        // Retourner une réponse appropriée
        return ResponseEntity.ok("Réservation effectuée avec succès");
    }

    @GetMapping("/consulterreservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}
