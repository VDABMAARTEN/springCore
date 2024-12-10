package be.vdab.welkom;

import be.vdab.welkom.landen.LandRepository;
import be.vdab.welkom.talen.TaalRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    private final LandRepository landRepository;
    private final TaalRepository taalRepository;
    public MyRunner(LandRepository landRepository, @Qualifier ("XML") TaalRepository taalRepository) {
        this.landRepository = landRepository;
        this.taalRepository = taalRepository;
    }

    @Override
    public void run(String... args){
        try{
            landRepository.findAll().forEach(land -> System.out.println(land.getNaam()));
            System.out.println();
            taalRepository.findAll().forEach(taal -> System.out.println(taal.getNaam()));
        }catch(IllegalArgumentException e){
            e.printStackTrace(System.err);
        }
    }
}
