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

    private final AgenceRepository agenceRepository;
    private final OffreRepository offreRepository;

    @Autowired
    public RechercheChambreService(AgenceRepository agenceRepository, OffreRepository offreRepository) {
        this.agenceRepository = agenceRepository;
        this.offreRepository = offreRepository;
    }

    public List<Offre> getAvailableOffersByAgence(String agenceNom, String dateDebut, int nombreLit) {
        Optional<Agence> agenceOptional = agenceRepository.findByNom(agenceNom);

        if (agenceOptional.isPresent()) {
            Agence agence = agenceOptional.get();
            List<Offre> availableOffers = new ArrayList<>();

            for (Hotel hotel : agence.getHotels()) {
                for (Chambre chambre : hotel.getChambres()) {
                    if (chambre.getNombreLit() >= nombreLit && chambre.isDisponible()) {
                        // Créez une nouvelle offre
                        Offre offre = createOffer(dateDebut, chambre.getPrix(), chambre.getNumChambre(), chambre.getNombreLit(), hotel.getNom());
                        offre.setImageURL(chambre.getImage());
                        offreRepository.save(offre); // Sauvegarde de l'offre en base de données
                        availableOffers.add(offre);
                    }
                }
            }

            return availableOffers;
        } else {
            // Agence non trouvée, vous pouvez gérer cela en lançant une exception ou en renvoyant une liste vide, selon vos besoins.
            return Collections.emptyList();
        }
    }

    private Offre createOffer(String dateDebut, int prix, int numeroChambre, int nombreLits, String nomHotel) {
        // Utilisez la logique appropriée pour calculer la date d'expiration (ici, 10 jours après la date de début)
        // Vous devez également gérer la génération d'un identifiant unique pour chaque offre (par exemple, incrémentez un compteur)

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
