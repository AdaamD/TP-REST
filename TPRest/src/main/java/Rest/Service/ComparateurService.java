package Rest.Service;

import Rest.Data.DataInitialization;
import Rest.Exception.ChambreNotFoundException;
import Rest.Models.Agence;
import Rest.Models.Chambre;
import Rest.Models.Hotel;
import Rest.Models.Offre;
import Rest.Repository.ChambreRepository;
import Rest.Repository.OffreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComparateurService {

    /* Attributs */
    private final OffreRepository offreRepository;
    private final DataInitialization dataInitialization;
    private final ChambreRepository chambreRepository;

    /* Constructeur */
    @Autowired
    public ComparateurService(OffreRepository offreRepository, DataInitialization dataInitialization, ChambreRepository chambreRepository) {
        this.offreRepository = offreRepository;
        this.dataInitialization = dataInitialization;
        this.chambreRepository = chambreRepository;
    }

    /* Méthodes */

    // Création d'offre
    private Offre createOffer(String dateDebut, int prix, int numeroChambre, int nombreLits, String nomHotel, String nomAgence) {
        LocalDate dateDebutObj = LocalDate.parse(dateDebut);
        LocalDate dateExpirationObj = dateDebutObj.plusDays(10);

        // Utilisez le ChambreRepository pour récupérer la chambre par son numéro
        Optional<Chambre> chambreOptional = chambreRepository.findByNumChambre(numeroChambre);

        if (chambreOptional.isPresent()) {
            Chambre chambre = chambreOptional.get();

            Offre offre = new Offre(
                    dateDebut,
                    dateExpirationObj.toString(),
                    prix,
                    numeroChambre,
                    nombreLits,
                    nomHotel,
                    nomAgence
            );

            // Assurez-vous d'associer la chambre à l'offre ici
            offre.setChambre(chambre);

            return offre;
        } else {
            // Gérez le cas où la chambre n'est pas trouvée
            throw new ChambreNotFoundException("Chambre non trouvée pour le numéro : " + numeroChambre);
        }
    }

    // Comparaison d'offre
    @Transactional
    public List<Offre> compareOffers(String dateDebut, String dateFin, int nombrePersonnes, int nombreEtoiles) {
        List<Offre> comparedOffers = new ArrayList<>();

        // Récupérer toutes les offres existantes
        List<Offre> existingOffers = offreRepository.findAll();
        for( Offre offre : existingOffers){System.out.println(offre.getChambre().isDisponible()); }

        for (Agence agence : dataInitialization.getAgences()) {
            for (Hotel hotel : agence.getHotels()) {
                for (Chambre chambre : hotel.getChambres()) {
                    if (chambre.getNombreLit() >= nombrePersonnes
                            && chambre.isDisponible()
                            && hotel.getNbEtoiles() >= nombreEtoiles) {

                        // Vérifier si une offre existante pour la chambre actuelle
                        boolean offreExistante = existingOffers.stream()
                                .anyMatch(existingOffer ->
                                        existingOffer.getNumeroChambre() == chambre.getNumChambre()
                                                && !existingOffer.getChambre().isDisponible());

                        // Si aucune offre existante, créer une nouvelle offre
                        if (!offreExistante) {
                            Offre offre = createOffer(dateDebut, chambre.getPrix(), chambre.getNumChambre(), chambre.getNombreLit(), hotel.getNom(), agence.getNom());
                            offre.setImageURL(chambre.getImage());
                            offre.setNumeroChambre(chambre.getNumChambre());
                            offre.setAgence(agence.getNom());
                            offreRepository.save(offre);
                            comparedOffers.add(offre);
                        }
                    }
                }
            }
        }

        return comparedOffers;
    }
}
