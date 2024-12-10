package be.vdab.welkom.landen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class LandRepository {
    private final String pad;
    public LandRepository(@Value("${landenCsvPad}") String pad){
        this.pad = pad;
    }

  public List<Land> findAll(){
      try(var regels = Files.lines(Path.of(pad))){
          return regels.map(regel -> regel.split(","))
                  .map(regelOnderdelen -> new Land(regelOnderdelen[0], regelOnderdelen[1], Integer.parseInt(regelOnderdelen[2]))).toList();
      }
      catch(IOException | ArrayIndexOutOfBoundsException | NumberFormatException ex){
          throw new IllegalArgumentException("Landenbestand bevat verkeerde data", ex );
      }
  }
}
