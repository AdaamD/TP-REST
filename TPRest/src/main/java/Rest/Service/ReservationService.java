package Rest.Service;

import Rest.Data.DataInitialization;
import Rest.Models.CarteCredit;
import Rest.Models.Client;
import Rest.Models.Offre;
import Rest.Models.Reservation;
import Rest.Repository.ChambreRepository;
import Rest.Repository.ClientRepository;
import Rest.Repository.OffreRepository;
import Rest.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

/* Attributs */
    private final DataInitialization dataInitialization;
    private final OffreRepository offreRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;


/* Constructeur */
    @Autowired
    public ReservationService(DataInitialization dataInitialization, OffreRepository offreRepository, ClientRepository clientRepository, ReservationRepository reservationRepository, ChambreRepository chambreRepository) {
        this.dataInitialization = dataInitialization;
        this.offreRepository = offreRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.chambreRepository = chambreRepository;
    }

/* Méthodes */

// Effectuer une réservation
    public ResponseEntity<String> effectuerReservation(
            String nomAgence, String mdpAgence, Long offreId, String dateArrive, String dateDepart,
            String nomClient, String prenomClient, String emailClient, int telephoneClient,
            int numeroCarte, String expirationCarte, int codeSecurite) {


    // Verification si l'offre est existante
        Optional<Offre> offreOptional = offreRepository.findById(offreId);
        if (offreOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'offre spécifiée n'existe pas");
        }

        Offre offre = offreOptional.get();


     // Verification de l'existance du client en Base de données
        Optional<Client> existingClientOptional = clientRepository.findByNomAndPrenom(nomClient, prenomClient);

        Client existingClient;
        if (existingClientOptional.isPresent()) {
            existingClient = existingClientOptional.get();
        } else { // Creation du nouveau client
            CarteCredit carteCreditClient = new CarteCredit(numeroCarte, expirationCarte, codeSecurite);
            existingClient = new Client(nomClient, prenomClient, emailClient, telephoneClient, carteCreditClient);
            clientRepository.save(existingClient);
        }
        if (!offre.getChambre().isDisponible()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'offre spécifiée n'est pas disponible pour réservation");
        }

      offre.setPrix((int) (offre.getPrix()*0.95));
     // Creation de la réservation
        Reservation reservation = new Reservation(
                existingClient.getNom(),
                existingClient.getPrenom(),
                existingClient,
                dateArrive,
                dateDepart,
                offre.getPrix(),
                offre
        );
        offre.getChambre().setDisponible(false);
        chambreRepository.save(offre.getChambre());
        offreRepository.save(offre);


        reservationRepository.save(reservation);

        // Conversion la réservation en JSON
        String reservationJson = convertReservationToJson(reservation);
        return ResponseEntity.ok(reservationJson);
    }

// Verification existance du Client en Base de données
    public ResponseEntity<String> existanceClient(String nomAgence, String mdpAgence, String nomClient, String prenomClient) {
        boolean authentificationValide = dataInitialization.validateCredentials(nomAgence, mdpAgence);

        if (!authentificationValide) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Client> existingClientOptional = clientRepository.findByNomAndPrenom(nomClient, prenomClient);

        if (existingClientOptional.isPresent()) {
            return ResponseEntity.ok("Le client existe dans les données.");
        } else {
            return ResponseEntity.ok("Le client n'existe pas dans les données.");
        }
    }

    private String convertReservationToJson(Reservation reservation) {
        double prixOffre = reservation.getOffre().getPrix();

        return String.format(
                "{\"id\": %d, \"nom\": \"%s\", \"prenom\": \"%s\", \"dateArrive\": \"%s\", \"dateDepart\": \"%s\", \"prix\": %s}",
                reservation.getId(),
                reservation.getNom(),
                reservation.getPrenom(),
                reservation.getDateArrive(),
                reservation.getDateDepart(),
                prixOffre
        );
    }
}
