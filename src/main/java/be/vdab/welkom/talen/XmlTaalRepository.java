package be.vdab.welkom.talen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Qualifier("XML")
@Component
public class XmlTaalRepository implements TaalRepository {

    private final XMLInputFactory factory = XMLInputFactory.newInstance();

    @Override
    public List<Taal> findAll() {
        var talen = new ArrayList<Taal>();
        try(var bufferedReader = Files.newBufferedReader(Path.of("/data/talen.xml"))){
            var reader = factory.createXMLStreamReader(bufferedReader);

            while(reader.hasNext()) {
                if(reader.isStartElement() && "taal".equals(reader.getLocalName())) {
                    var taalcode = reader.getAttributeValue(0);
                    var naam = reader.getAttributeValue(1);
                    talen.add(new Taal(taalcode, naam));
                }
            }
            return talen;
        }
        catch (IOException | XMLStreamException e){
            throw new IllegalArgumentException("data/talen.xml bevat fouten", e);
        }
    }
}
