package Rest.Data;


import Rest.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Rest.Models.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/* Le .save(...) sert a sauvegarder l'entité que t'as crée/modifié */
@Configuration
@EnableTransactionManagement
    public class DataInitialization {

        private Logger logger = LoggerFactory.getLogger(DataInitialization.class);

        @Bean
        public CommandLineRunner initDatabase(
                AdresseRepository adresseRepository,
                AgenceRepository agenceRepository,
                CarteCreditRepository carteCreditRepository,
                ChambreRepository chambreRepository,
                ClientRepository clientRepository,
                HotelRepository hotelRepository,
                ReservationRepository reservationRepository) {
            return args -> {

            //Hotel
                Hotel hotel1 = new Hotel("Hotel Montpellier", "Catégorie1", new Adresse("France", "Paris", "Champs-Élysées", "Quartier1", 12345), 3);
                hotelRepository.save(hotel1);
                logger.info("   Hotel saved: " + hotel1);

                Hotel hotel2 = new Hotel("Hotel Paris", "Catégorie2", new Adresse("2 Avenue des Pins", "Paris", "75001", null, null), 4);
                hotelRepository.save(hotel2);
                logger.info("   Hotel saved: " + hotel2);

            //Agence
                Agence agence1 = new Agence("Agence Voyage Express", "password");
                agenceRepository.save(agence1);
                logger.info("   Agence saved: " + agence1);

                Agence agence2 = new Agence("Agence Tourisme Aventure", "pass123");
                agenceRepository.save(agence2);
                agence2.ajouterHotel(hotel2);
                agenceRepository.save(agence2);
                logger.info("   Agence saved: " + agence2);
                logger.info("hotel de l'agence 2 "+agence2.getHotels());


                //CarteCredit
                CarteCredit carteCredit1 = new CarteCredit(123456789, new Date(), 123);
                carteCreditRepository.save(carteCredit1);
                logger.info("   CarteCredit saved: " + carteCredit1);

                CarteCredit carteCredit2 = new CarteCredit(987654321, new Date(), 456);
                carteCreditRepository.save(carteCredit2);
                logger.info("   CarteCredit saved: " + carteCredit2);

            //Chambre
                Chambre chambre1 = new Chambre(1, hotel1, 1, 1, 100);
                chambre1.setDisponible(true);
                //chambre1.setImage("chemin/vers/image1.jpg");
                chambreRepository.save(chambre1);
                logger.info("   Chambre saved: " + chambre1);

                Chambre chambre2 = new Chambre(2, hotel2, 2, 2, 150);
                chambre2.setDisponible(true);
                //chambre2.setImage("chemin/vers/image2.jpg");
                chambreRepository.save(chambre2);
                logger.info("   Chambre saved: " + chambre2);


            //Client
                CarteCredit carteCreditClient1 = new CarteCredit(111111111, new Date(), 111);
                Client client1 = new Client("John", "Doe", "john.doe@example.com", 123456789, carteCreditClient1);
                clientRepository.save(client1);
                logger.info("   Client saved: " + client1);

                CarteCredit carteCreditClient2 = new CarteCredit(222222222, new Date(), 222);
                Client client2 = new Client("Jane", "Doe", "jane.doe@example.com", 987654321, carteCreditClient2);
                clientRepository.save(client2);
                logger.info("   Client saved: " + client2);


            //Reservation
                Reservation reservation1 = new Reservation("John", "Doe", client1, 100, "2023-01-01", "2023-01-05");
                reservationRepository.save(reservation1);
                logger.info("   Reservation saved: " + reservation1);

                Reservation reservation2 = new Reservation("Jane", "Doe", client2, 150, "2023-01-02", "2023-01-06");
                reservationRepository.save(reservation2);
                logger.info("   Reservation saved: " + reservation2);
            };
        }
    }


