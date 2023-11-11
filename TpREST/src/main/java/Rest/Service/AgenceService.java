package Rest.Service;


import Rest.Models.Agence;
import Rest.Models.Hotel;
import Rest.Repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenceService {

    private final AgenceRepository agenceRepository;

    @Autowired
    public AgenceService(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    // Récupérer une agence par son nom
    public Agence getAgenceByNom(String nom) {
        return agenceRepository.findByNom(nom);
    }

    // Vérifier les identifiants de connexion de l'agence
    public Agence verifierIdentifiants(String nom, String motDePasse) {
        return agenceRepository.findByNomAndMotDePasse(nom, motDePasse);
    }

    // Récupérer les hôtels d'une agence par son ID
    public List<Hotel> getHotelsByAgenceId(Long agenceId) {
        return agenceRepository.findHotelsByAgenceId(agenceId);
    }

    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id).orElse(null);
    }

    // Récupérer toutes les agences
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    // Vous pouvez ajouter d'autres méthodes en fonction des besoins
}
