package Rest.Repository;


import Rest.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByNomAndPrenom(String nom, String prenom);
    //List<Client> findByMail(String mail);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
