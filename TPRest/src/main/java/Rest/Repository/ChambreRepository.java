package Rest.Repository;


import Rest.Models.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    //List<Chambre> findByHotelNomAndNombreLit(int nombreLit, String nomHotel);

    //List<Chambre> findByPrixLessThanEqual(int prixMax);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
