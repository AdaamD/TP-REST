package Rest.Repository;


import Rest.Models.Agence;
import Rest.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgenceRepository extends JpaRepository<Agence, Long> {

    Optional<Agence> findByNom(String nom);

    // Trouver une agence par son nom et mot de passe
    Agence findByNomAndMotDePasse(String nom, String motDePasse);

    // Trouver les hôtels d'une agence par son ID
    @Query("SELECT a.hotels FROM Agence a WHERE a.id = :agenceId")
    List<Hotel> findHotelsByAgenceId(@Param("agenceId") Long agenceId);

    // Vous pouvez ajouter d'autres méthodes selon vos besoins
}

