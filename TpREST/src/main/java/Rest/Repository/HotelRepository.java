package Rest.Repository;

import Rest.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByNom(String nom);

    List<Hotel> findByCategorie(String categorie);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
