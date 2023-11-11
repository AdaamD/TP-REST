package Rest.Service;


import Rest.Models.Adresse;
import Rest.Repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;

    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }

    public Adresse getAdresseById(Long id) {
        return adresseRepository.findById(id).orElse(null);
    }

    // Ajoutez d'autres méthodes pour la logique métier liée aux adresses
}
