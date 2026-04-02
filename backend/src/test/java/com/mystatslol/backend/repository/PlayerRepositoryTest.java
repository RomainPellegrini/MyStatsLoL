package com.mystatslol.backend.repository;

import com.mystatslol.backend.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

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
