package Rest.Service;


import Rest.Data.DataInitialization;
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
public class ComparateurService {

    private final AgenceRepository agenceRepository;
    private final OffreRepository offreRepository;
    private final DataInitialization dataInitialization;


    @Autowired
    public ComparateurService(AgenceRepository agenceRepository, OffreRepository offreRepository, DataInitialization dataInitialization) {
        this.agenceRepository = agenceRepository;
        this.offreRepository = offreRepository;
        this.dataInitialization = dataInitialization;
    }



    private Offre createOffer(String dateDebut, int prix, int numeroChambre, int nombreLits, String nomHotel, String nomAgence) {
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
                nomHotel,
                nomAgence
        );
        return offre;
    }
    public List<Offre> compareOffers(String dateDebut, String dateFin, int nombrePersonnes, int nombreEtoiles) {
        List<Offre> comparedOffers = new ArrayList<>();

        for (Agence agence : dataInitialization.getAgences()) {
            for (Hotel hotel : agence.getHotels()) {
                for (Chambre chambre : hotel.getChambres()) {
                    if (chambre.getNombreLit() >= nombrePersonnes && chambre.isDisponible() && hotel.getNbEtoiles() >= nombreEtoiles) {
                        Offre offre = createOffer(dateDebut, chambre.getPrix(), chambre.getNumChambre(), chambre.getNombreLit(), hotel.getNom(), agence.getNom());
                        offre.setImageURL(chambre.getImage());
                        comparedOffers.add(offre);
                    }
                }
            }
        }

        for (Offre offre : comparedOffers) {
            offreRepository.save(offre);
        }

        return comparedOffers;
    }

}