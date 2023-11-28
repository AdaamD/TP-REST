package Rest.Controller;

import Rest.Data.DataInitialization;


import Rest.Exception.NoOfferException;

import Rest.Exception.UnauthorizedException;

import Rest.Models.Client;

import Rest.Models.Offre;

import Rest.Models.Reservation;

import Rest.Repository.AgenceRepository;

import Rest.Repository.ClientRepository;

import Rest.Repository.OffreRepository;

import Rest.Repository.ReservationRepository;

import Rest.Service.RechercheChambreService;

import Rest.Service.ReservationService;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;

import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8080")

@RestController

@RequestMapping("/comparateur")

public class ComparateurController {


    private final DataInitialization dataInitialization;

    private final RechercheChambreService rechercheChambreService;

    private final ObjectMapper objectMapper;


    @Autowired

    public ComparateurController(DataInitialization dataInitialization, RechercheChambreService rechercheChambreService, ObjectMapper objectMapper) {

        this.dataInitialization = dataInitialization;

        this.rechercheChambreService = rechercheChambreService;

        this.objectMapper = objectMapper;

    }


    @PostMapping("/compare")

    public ResponseEntity<String> compareOffers(

            @RequestParam String identifiant,

            @RequestParam String motDePasse,

            @RequestParam String dateDebut,

            @RequestParam String dateFin,

            @RequestParam int nombrePersonnes,

            @RequestParam int nombreEtoiles) {


        // Vérifiez l'identifiant et le mot de passe

        boolean credentialsValid = dataInitialization.validateCredentials(identifiant, motDePasse);


        if (!credentialsValid) {

            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            throw new UnauthorizedException("L'authentification a échoué. Veuillez vérifier vos informations.");

        }


        // Votre logique métier pour comparer les offres

        List<Offre> comparedOffers = rechercheChambreService.compareOffers(dateDebut, dateFin, nombrePersonnes, nombreEtoiles);


        if (comparedOffers.isEmpty()) {

            throw new NoOfferException("Aucune offre disponible pour les critères spécifiés.");

        }


        // Convertir les offres en format JSON pour la page html

        try {

            String formattedOffers = objectMapper.writeValueAsString(comparedOffers);

            return new ResponseEntity<>(formattedOffers, HttpStatus.OK);

        } catch (JsonProcessingException e) {

            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}