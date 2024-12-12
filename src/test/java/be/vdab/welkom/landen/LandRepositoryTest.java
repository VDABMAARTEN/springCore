package be.vdab.welkom.landen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
// enkele andere imports
@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(LandRepository.class)
class LandRepositoryTest {
    private final LandRepository landRepository;
    private final String pad;
    LandRepositoryTest(LandRepository landRepository,
                       @Value("${landenCsvPad}") String pad) {
        this.landRepository = landRepository;
        this.pad = pad;
    }
    @Test
    void erZijnEvenveelLandenAlsHetAantalRegelsInHetBestand() throws IOException {
        try (var regels = Files.lines(Path.of(pad))) {
            assertThat(landRepository.findAll().size()).isEqualTo(regels.count());
        }
    }
    @Test
    void hetEersteLandBevatDeDataUitDeEersteRegel() throws IOException {
        try (var regels = Files.lines(Path.of(pad))) {
            regels.findFirst().ifPresent(eersteRegel -> {
                var eersteLand = landRepository.findAll().get(0);
                assertThat(eersteLand.getCode() + "," + eersteLand.getNaam() + "," +
                        eersteLand.getOppervlakte()) // land data concateneren tot één string
                        .isEqualTo(eersteRegel); // deze string moet gelijk zijn aan de 1° regel
            });
        }
    }
}





/*
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(LandRepository.class)

class LandRepositoryTest {
    private final LandRepository landRepository;
    private final String pad;

    LandRepositoryTest(LandRepository landRepository,
                       @Value("${landenCsvPad}") String pad) {
        this.landRepository = landRepository;
        this.pad = pad;
    }

    @Test
    void erZijnEvenveelLandenAlsHetAantalRegelsInHetBestand() throws IOException{
        try(var regels = Files.lines(Path.of(pad))){
            assertThat(landRepository.findAll().size()).isEqualTo(regels.count());
        }
    }

    @Test
    void hetEersteLandBevatDeDataUitDeEersteRegel() throws IOException{
        try(var regels = Files.lines(Path.of(pad))){
            regels.findFirst().ifPresent(eersteRegel -> {
                var eersteLand = landRepository.findAll().get(0);
                assertThat(eersteLand.getCode() + ","
                        + eersteLand.getNaam() + "," + eersteLand.getOppervlakte()).isEqualTo(eersteRegel);
            });
        }
    }

}
*/
