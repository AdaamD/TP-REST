package Rest.Service;

import Rest.Models.Agence;
import Rest.Models.Chambre;
import Rest.Models.Hotel;
import Rest.Models.Offre;
import Rest.Repository.AgenceRepository;
import Rest.Repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RechercheChambreService {

    /* Attributs */
    private final AgenceRepository agenceRepository;
    private final OffreRepository offreRepository;

    /* Constructeur */
    @Autowired
    public RechercheChambreService(AgenceRepository agenceRepository, OffreRepository offreRepository) {
        this.agenceRepository = agenceRepository;
        this.offreRepository = offreRepository;
    }

    /* Méthodes */

    // Recherche Chambre par Agence
    public List<Offre> rechercheOffreParAgence(String agenceNom, String dateDebut, int nombreLit) {

        Optional<Agence> agenceOptional = agenceRepository.findByNom(agenceNom);

        if (agenceOptional.isPresent()) {
            Agence agence = agenceOptional.get();
            List<Offre> availableOffers = new ArrayList<>();

            // Récupérer toutes les offres existantes
            List<Offre> existingOffers = offreRepository.findAll();

            for (Hotel hotel : agence.getHotels()) {
                for (Chambre chambre : hotel.getChambres()) {
                    if (chambre.getNombreLit() >= nombreLit && chambre.isDisponible()) {

                        // Vérifier si une offre existante pour la chambre actuelle
                        boolean offreExistante = existingOffers.stream()
                                .anyMatch(existingOffer ->
                                        existingOffer.getNumeroChambre() == chambre.getNumChambre()
                                                && !existingOffer.getChambre().isDisponible());

                        // Si aucune offre existante, créer une nouvelle offre
                        if (!offreExistante) {
                            // Création d'une nouvelle offre
                            Offre offre = creationOffre(dateDebut, chambre.getPrix(), chambre.getNumChambre(), chambre.getNombreLit(), hotel.getNom());
                            offre.setChambre(chambre);
                            offre.setImageURL(chambre.getImage());
                            offre.setAgence(agence.getNom());
                            offreRepository.save(offre);
                            availableOffers.add(offre);
                        }
                    }
                }
            }
            return availableOffers;
        } else {
            // Agence non trouvée, renvoie d'une liste vide.
            return Collections.emptyList();
        }
    }

    // Creation d'Offre
    private Offre creationOffre(String dateDebut, int prix, int numeroChambre, int nombreLits, String nomHotel) {
        LocalDate dateDebutObj = LocalDate.parse(dateDebut);
        LocalDate dateExpirationObj = dateDebutObj.plusDays(10);

        Offre offre = new Offre(
                dateDebut,
                dateExpirationObj.toString(),
                prix,
                numeroChambre,
                nombreLits,
                nomHotel
        );

        return offre;
    }
}
