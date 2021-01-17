package com.benjaminfaal.ps4errorcodes;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class PS4ErrorCodes {

    private static final Map<String, PS4ErrorCode> BY_NAME = new HashMap<>();

    private static final Map<String, PS4ErrorCode> BY_CODE = new HashMap<>();

    private static final Map<String, PS4ErrorCode> BY_RETURNCODE = new HashMap<>();

    private static final Map<Integer, PS4ErrorCode> BY_SIGNED = new HashMap<>();

    private static final Map<Long, PS4ErrorCode> BY_UNSIGNED = new HashMap<>();

    static {
        load("input/error_codes.csv");
        load("input/psdevwiki_error_codes.csv");
    }

    public PS4ErrorCode findByCode(String code) {
        return BY_CODE.get(code);
    }

    public PS4ErrorCode findByReturnCode(String returnCode) {
        return BY_RETURNCODE.get(returnCode.toUpperCase());
    }

    public PS4ErrorCode findByName(String name) {
        return BY_NAME.get(name);
    }

    public PS4ErrorCode findBySigned(int signed) {
        return BY_SIGNED.get(signed);
    }

    public PS4ErrorCode findByUnsigned(long unsigned) {
        return BY_UNSIGNED.get(unsigned);
    }

    private void load(String csvResourceName) {
        fillMaps(parseCsv(csvResourceName));
    }

    private void fillMaps(Stream<PS4ErrorCode> ps4ErrorCodes) {
        ps4ErrorCodes.forEach(ps4ErrorCode -> {
            if (ps4ErrorCode.getName() != null && !ps4ErrorCode.getName().trim().isEmpty()) {
                put(BY_NAME, ps4ErrorCode.getName().toUpperCase(), ps4ErrorCode);
            }
            put(BY_CODE, ps4ErrorCode.getCode().toUpperCase(), ps4ErrorCode);

            if (ps4ErrorCode.getReturnCode() != null && !ps4ErrorCode.getReturnCode().trim().isEmpty()) {
                String returnCodeUppercase = ps4ErrorCode.getReturnCode().toUpperCase();
                put(BY_RETURNCODE, returnCodeUppercase, ps4ErrorCode);

                int signed = Long.decode(returnCodeUppercase).intValue();
                long unsigned = Integer.toUnsignedLong(signed);
                put(BY_SIGNED, signed, ps4ErrorCode);
                put(BY_UNSIGNED, unsigned, ps4ErrorCode);
            }
        });
    }

    private <K> void put(Map<K, PS4ErrorCode> map, K key, PS4ErrorCode ps4ErrorCode) {
        PS4ErrorCode existingErrorCode = map.get(key);
        if (existingErrorCode == null || (existingErrorCode.getDescription() == null || existingErrorCode.getDescription().length() == 0)) {
            map.put(key, ps4ErrorCode);
        }
    }

    @SneakyThrows
    private Stream<PS4ErrorCode> parseCsv(String csvResourceName) {
        return StreamSupport.stream(CSVFormat.DEFAULT.parse(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(csvResourceName))).spliterator(), false)
                .map(record -> {
                    String name = record.get(0);
                    String code = record.get(1);
                    String returnCode = record.get(2).toUpperCase();

                    return new PS4ErrorCode(name, code, returnCode, record.size() == 4 ? record.get(3) : null);
                });
    }

    private void printCsv(List<PS4ErrorCode> ps4ErrorCodes, String resourceName) throws IOException {
        CSVPrinter csvPrinter = new CSVPrinter(new FileWriter("src/main/resources/" + resourceName + ".csv"), CSVFormat.DEFAULT);
        for (PS4ErrorCode ps4ErrorCode : ps4ErrorCodes) {
            csvPrinter.printRecord(ps4ErrorCode.getName(), ps4ErrorCode.getCode(), ps4ErrorCode.getReturnCode(), ps4ErrorCode.getDescription());
        }
        csvPrinter.flush();
        csvPrinter.close();
    }

}
