package Rest.Service;

import Rest.Data.DataInitialization;
import Rest.Models.*;
import Rest.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OffreRepository offreRepository;
    private final ClientRepository clientRepository; // Ajoutez le repository pour accéder à la base de données des clients
    private final DataInitialization dataInitialization;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, OffreRepository offreRepository, ClientRepository clientRepository, DataInitialization dataInitialization) {
        this.reservationRepository = reservationRepository;
        this.offreRepository = offreRepository;
        this.clientRepository = clientRepository;
        this.dataInitialization = dataInitialization;
    }

    public ResponseEntity<String> makeReservation(String agenceNom, String motDePasse, int offreId, String nom, String prenom) {
        // Vérifier l'authentification
        boolean credentialsValid = dataInitialization.validateCredentials(agenceNom, motDePasse);

        if (!credentialsValid) {
            // Gérer l'authentification invalide
            return new ResponseEntity<>("Échec de l'authentification. Vérifiez les informations fournies.", HttpStatus.UNAUTHORIZED);
        }

        // Récupérer l'offre à partir de l'ID
        Offre offre = offreRepository.findById((long) offreId).orElse(null);


        if (offre == null || offre.getChambre() == null) {
            // Gérer l'offre non trouvée ou la chambre non disponible
            return new ResponseEntity<>("Échec de la réservation. L'offre n'est pas disponible.", HttpStatus.BAD_REQUEST);
        }

        if (!offre.getChambre().isDisponible()) {
            // Gérer la chambre non disponible
            return new ResponseEntity<>("Échec de la réservation. La chambre n'est pas disponible.", HttpStatus.BAD_REQUEST);
        }


        // Rechercher le client dans la base de données en fonction du nom et du prénom
        Client client = clientRepository.findByNomAndPrenom(nom, prenom).orElse(null);

        if (client == null) {
            // Gérer l'absence du client dans la base de données
            return new ResponseEntity<>("Échec de la réservation. Client inconnu.", HttpStatus.BAD_REQUEST);
        }

        // Calculer le prix de la réservation (vous pouvez ajouter votre logique ici)
        double prixReservation = calculateReservationPrice(offre);

        // Créer la réservation
        Reservation reservation = new Reservation(nom, prenom, client, prixReservation, offre.getDateDisponibilite(), offre.getDateExpiration(), offre);

        // Enregistrer la réservation dans la base de données
        reservationRepository.save(reservation);

        // Marquer l'offre comme réservée
        offre.getChambre().setDisponible(false);
        offreRepository.save(offre);

        return new ResponseEntity<>("Réservation réussie. Référence de la réservation : " + reservation.getId(), HttpStatus.OK);
    }

    public ResponseEntity<String> checkAvailability(int offreId, String dateArrive, String dateDepart) {
        // Recherche de l'offre dans la base de données
        Offre offre = offreRepository.findById((long) offreId).orElse(null);

        // Vérification de la disponibilité de l'offre
       // if (offre == null || !offre.isDisponible()) {
            // Gérer l'offre non trouvée ou non disponible
         //   return new ResponseEntity<>("Échec de la réservation. L'offre n'est pas disponible.", HttpStatus.BAD_REQUEST);
        //}

        // L'offre est disponible
        return new ResponseEntity<>("L'offre est disponible.", HttpStatus.OK);
    }

    private double calculateReservationPrice(Offre offre) {
        // Ajoutez votre logique de calcul du prix de la réservation ici
        // Par exemple, vous pouvez utiliser le prix de l'offre comme prix de la réservation
        return offre.getPrix();
    }
}