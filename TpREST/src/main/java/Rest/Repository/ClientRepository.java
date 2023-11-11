package Rest.Repository;


import Rest.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNomAndPrenom(String nom, String prenom);

    List<Client> findByMail(String mail);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
