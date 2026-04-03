package com.mystatslol.backend.repository;

import com.mystatslol.backend.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    private Player player1;
    private Player player2;

    @BeforeEach
    void setup() {
        // vider la table pour être sûr
        playerRepository.deleteAll();

        // créer des players pour les tests
        player1 = new Player();
        player1.setPuuid("puuid1");
        player1.setGameName("Romain");
        player1.setTagLine("EUW");

        player2 = new Player();
        player2.setPuuid("puuid2");
        player2.setGameName("Lucas");
        player2.setTagLine("NA");

        playerRepository.save(player1);
        playerRepository.save(player2);
    }

    @Test
    void getAllPlayersTest(){
        List<Player> players = playerRepository.findAll();

        assertEquals(2, players.size());
        assertEquals("puuid1",players.get(0).getPuuid());
        assertEquals("Romain",players.get(0).getGameName());
        assertEquals("EUW",players.get(0).getTagLine());
    }

    @Test
    void getPlayerByPuuidTest(){
        Player p = playerRepository.getReferenceById("puuid1");

        assertEquals("puuid1",p.getPuuid());
        assertEquals("Romain",p.getGameName());
        assertEquals("EUW",p.getTagLine());
    }
    @Test
    void savePlayerTest(){
        Player p = new Player();
        p.setPuuid("puuid3");
        p.setGameName("Jordan");
        p.setTagLine("2026");
        int size = playerRepository.findAll().size();
       Player save = playerRepository.save(p);
        assertEquals("puuid3",save.getPuuid());
        assertEquals("Jordan",save.getGameName());
        assertEquals("2026",save.getTagLine());
        assertEquals(size+1,playerRepository.findAll().size());

    }

    @Test
    void updatePlayerTest(){
        Player p = new Player();
        p.setPuuid("puuid1");
        p.setGameName("Romain2");
        p.setTagLine("2026");
        int size = playerRepository.findAll().size();
        Player save = playerRepository.save(p);
        assertEquals("puuid1",save.getPuuid());
        assertEquals("Romain2",save.getGameName());
        assertEquals("2026",save.getTagLine());
        assertEquals(size,playerRepository.findAll().size());
        assertEquals("Romain2",playerRepository.getReferenceById("puuid1").getGameName());

    }

    @Test
    void deletePlayerTest(){
        int size = playerRepository.findAll().size();
        playerRepository.deleteById("puuid1");

        assertThrows(ObjectRetrievalFailureException.class,()->{playerRepository.getReferenceById("puuid1");});
        assertEquals(size-1,playerRepository.findAll().size());


    }

}
