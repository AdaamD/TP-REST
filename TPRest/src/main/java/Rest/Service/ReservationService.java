package Rest.Service;

import Rest.Data.DataInitialization;
import Rest.Models.CarteCredit;
import Rest.Models.Client;
import Rest.Models.Offre;
import Rest.Models.Reservation;
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

    private final DataInitialization dataInitialization;
    private final OffreRepository offreRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(DataInitialization dataInitialization, OffreRepository offreRepository, ClientRepository clientRepository, ReservationRepository reservationRepository) {
        this.dataInitialization = dataInitialization;
        this.offreRepository = offreRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<String> makeReservation(
            String nomAgence, String mdpAgence, Long offreId,
            String dateArrive, String dateDepart,
            String nomClient, String prenomClient,
            String emailClient, int telephoneClient,
            int numeroCarte, String expirationCarte, int codeSecurite) {

        boolean authentificationValide = dataInitialization.validateCredentials(nomAgence, mdpAgence);

        if (!authentificationValide) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Optional<Offre> offreOptional = offreRepository.findById(offreId);
        if (offreOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'offre spécifiée n'existe pas");
        }

        Offre offre = offreOptional.get();

        Optional<Client> existingClientOptional = clientRepository.findByNomAndPrenom(nomClient, prenomClient);

        Client existingClient;
        if (existingClientOptional.isPresent()) {
            existingClient = existingClientOptional.get();
        } else {
            CarteCredit carteCreditClient = new CarteCredit(numeroCarte, expirationCarte, codeSecurite);

            existingClient = new Client(nomClient, prenomClient, emailClient, telephoneClient, carteCreditClient);
            clientRepository.save(existingClient);
        }

        Reservation reservation = new Reservation(
                existingClient.getNom(),
                existingClient.getPrenom(),
                existingClient,
                dateArrive,
                dateDepart,
                offre.getPrix(),
                offre
        );

        reservationRepository.save(reservation);

        // Utilisez une bibliothèque JSON (par exemple, Jackson) pour convertir la réservation en JSON
        String reservationJson = convertReservationToJson(reservation);

        // Retournez la chaîne JSON dans la réponse
        return ResponseEntity.ok(reservationJson);
    }

    public ResponseEntity<String> verifyClient(String nomAgence, String mdpAgence, String nomClient, String prenomClient) {
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
