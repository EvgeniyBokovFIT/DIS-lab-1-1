package ru.bokov;

import lombok.Getter;
import org.apache.commons.cli.*;

@Getter
public class CommandLineArgumentsParser {
    private String archiveName;
    public void parse(String[] args) throws ParseException {
        var options = new Options();
        options.addOption(Option.builder("f")
                .argName("file")
                .hasArg()
                .desc("archive path")
                .build());
        CommandLineParser commandLineParser = new DefaultParser();
        var commandLine = commandLineParser.parse(options, args);
        if(!commandLine.hasOption("f")) {
            throw new ParseException("Input archive path");
        }
        this.archiveName = commandLine.getOptionValue("f");
    }
}
