package Rest.Service;


import Rest.Models.CarteCredit;
import Rest.Repository.CarteCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteCreditService {

    @Autowired
    private CarteCreditRepository carteCreditRepository;

    public List<CarteCredit> getAllCartesCredit() {
        return carteCreditRepository.findAll();
    }

    public CarteCredit getCarteCreditById(Long id) {
        return carteCreditRepository.findById(id).orElse(null);
    }

    // Ajoutez d'autres méthodes pour la logique métier liée aux cartes de crédit
}
