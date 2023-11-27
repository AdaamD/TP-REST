package Rest.Service;

import Rest.Data.DataInitialization;
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

    public ResponseEntity<String> makeReservation(String nomAgence, String mdpAgence, Long offreId, String dateArrive , String dateDepart,String nomClient, String prenomClient) {
        boolean authentificationValide = dataInitialization.validateCredentials(nomAgence, mdpAgence);

        if (!authentificationValide) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Offre> offreOptional = offreRepository.findById(offreId);
        if (offreOptional.isEmpty()) {
            return ResponseEntity.status(404).body("L'offre spécifiée n'existe pas");
        }

        Offre offre = offreOptional.get();

        Optional<Client> existingClientOptional = clientRepository.findByNomAndPrenom(nomClient, prenomClient);

        if (existingClientOptional.isEmpty()) {
            return ResponseEntity.status(400).body("Le client n'est pas enregistré. Impossible de faire la réservation.");
        }

        Client existingClient = existingClientOptional.get();

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

        String reservationDetails = String.format(
                "Numéro de réservation : %d\nNom du client : %s\nPrénom du client : %s\nDate d'arrivée : %s\nDate de départ : %s\nPrix de l'offre : %s euros",
                reservation.getId(),
                reservation.getNom(),
                reservation.getPrenom(),
                reservation.getDateArrive(),
                reservation.getDateDepart(),
                reservation.getPrixAPayer()
        );

        // Retournez la chaîne de texte dans la réponse
        return ResponseEntity.ok(reservationDetails);
    }
}
