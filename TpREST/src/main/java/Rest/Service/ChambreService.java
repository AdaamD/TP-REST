package Rest.Service;


import Rest.Models.Chambre;
import Rest.Repository.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChambreService {

    @Autowired
    private ChambreRepository chambreRepository;

    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id).orElse(null);
    }

    // Ajoutez d'autres méthodes pour la logique métier liée aux chambres
}
