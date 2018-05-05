package com.pro_crafting.tools.recordjarconverter.service;

import javax.enterprise.context.Dependent;
import java.io.InputStream;
import java.util.*;

@Dependent
public class RecordJarService {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String RECORD_SEPERATOR = "%%";
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    public List<Map<String, String>> convert(InputStream content, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = DEFAULT_ENCODING;
        }

        List<Map<String, String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(content, encoding);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Each record is separated from other
            // records by at least one line beginning with the sequence
            // These "seperator lines" can also contain comments, but for now don't worry about them
            if (line.startsWith(RECORD_SEPERATOR)) {
                continue;
            }

            Map<String, String> record = new HashMap<>();
            line = parseRecord(scanner, record, line);
            records.add(record);
        }
        return records;
    }

    /**
     * Parses a record in the scanner. Parsing takes place till the first RECORD_SEPERATOR is encountered.
     * @param scanner scanner reading the record jar file
     * @param record record currently looked at
     * @return Returns the currently read line;
     */
    private String parseRecord(Scanner scanner, Map<String, String> record, String currentLine) {
        String line = currentLine;
        do {
            if (line == null || line.startsWith(RECORD_SEPERATOR)) {
                break;
            }

            if (line.startsWith(" ")) {
                line = null;
                continue;
            }

            // The separator MAY be surrounded on either side by any amount of
            // horizontal whitespace (tab or space characters).
            String[] tokens = line.split(FIELD_SEPERATOR);
            String key = tokens[0].trim();
            String body = tokens[1].trim();
            line = parseBody(scanner, record, key, body);


            if (!scanner.hasNextLine() && line == null) {
                break;
            }
        } while (true);

        return line;
    }

    /**
     * Parses a fields body of a field in a record. Folding is respected. The field will be added to the record under the specified key.
     * @param scanner scanner reading the record jar file
     * @param record record currently looked at
     * @param key key of the field
     * @param body body of the field
     * @return Either the currently read line, or null if no new line was read;
     */
    private String parseBody(Scanner scanner, Map<String, String> record, String key, String body) {
        String line = null;
        StringBuilder logicalLine = new StringBuilder();
        do {
            body = body.trim();
            if (body.endsWith(LINE_CONTINUATION)) {
                body = body.substring(0, body.length() - 1);
            }
            logicalLine.append(body);

            if (!scanner.hasNextLine()) {
                break;
            }

            body = line = scanner.nextLine();
            if (!line.startsWith(" ")) {
                break;
            }
        } while (true);

        record.put(key, logicalLine.toString());
        return line;
    }
}
