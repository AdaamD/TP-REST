package Rest.Repository;


import Rest.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //List<Reservation> findByClientNomAndDateArriveBetween(String nomClient, Date dateArriveDebut, Date dateArriveFin);

    //List<Reservation> findByDateArriveAfterAndDateDepartBefore(Date dateArrive, Date dateDepart);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
