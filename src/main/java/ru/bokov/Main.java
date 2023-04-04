package ru.bokov;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.ParseException;
import ru.bokov.decompressor.BZip2Decompressor;
import ru.bokov.decompressor.Decompressor;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            var clParser = new CommandLineArgumentsParser();
            clParser.parse(args);
            Decompressor decompressor = new BZip2Decompressor();
            var inputStream = decompressor.decompress(new File(clParser.getArchiveName()));
            log.info("Archive decompressed");
            XMLParser xmlParser = new XMLParser();
            xmlParser.calculateFrequencyOfAttributes(inputStream);
            log.info("Statistic collected");

            System.out.println("Users stats:");
            var usersFrequency = xmlParser.getUserFrequencyMap();
            printStats(usersFrequency);

            System.out.println("Keys stats:");
            var keysFrequency = xmlParser.getKeyFrequencyMap();
            printStats(keysFrequency);

            log.info("Statistic printed");

        } catch (ParseException | IOException | XMLStreamException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printStats(Map<String, Integer> map) {
        map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(System.out::println);
    }
}