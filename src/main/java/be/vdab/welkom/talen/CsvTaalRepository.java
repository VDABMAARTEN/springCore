package be.vdab.welkom.talen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Qualifier("CSV")
@Component
public class CsvTaalRepository implements TaalRepository {

    private final String pad;

    public CsvTaalRepository(@Value("${landenCsvPad}") String pad) {
        this.pad = pad;
    }

    @Override
    public List<Taal>findAll(){
        try (var regels = Files.lines(Path.of(pad))){
            return regels.map(regel -> regel.split(","))
                    .map(regel -> new Taal(regel[0], regel[1])).toList();
        }catch(IOException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            throw new IllegalArgumentException("Data in data/talen.csv is incorrect", e);
        }
    }
}