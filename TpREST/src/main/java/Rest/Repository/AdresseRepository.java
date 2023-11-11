package Rest.Repository;


import Rest.Models.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {

    List<Adresse> findByVille(String ville);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
