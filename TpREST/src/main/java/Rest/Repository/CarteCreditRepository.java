package Rest.Repository;


import Rest.Models.CarteCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarteCreditRepository extends JpaRepository<CarteCredit, Long> {

    List<CarteCredit> findByNumero(Integer numero);

    // Ajoutez d'autres méthodes de recherche personnalisées si nécessaire
}
