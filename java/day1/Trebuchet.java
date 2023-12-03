import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Trebuchet {
    public static void part1(byte[] input) {
        Map<Integer, String> mapping = new HashMap<>();
        mapping.put(48, "0");
        mapping.put(49, "1");
        mapping.put(50, "2");
        mapping.put(51, "3");
        mapping.put(52, "4");
        mapping.put(53, "5");
        mapping.put(54, "6");
        mapping.put(55, "7");
        mapping.put(56, "8");
        mapping.put(57, "9");

        int sum = 0;
        String first = "";
        String last = "";
        for (int b : input) {
            if (mapping.containsKey(b)) {
                if (first.isEmpty()) {
                    first = mapping.get(b);
                    last = first;
                } else {
                    last = mapping.get(b);
                }
            }

            if (b == 10) {
                sum += Integer.valueOf(Integer.valueOf(first + last));
                first = "";
                last = "";
            }
        }

        System.out.println("part 1: " + sum);
    }

    public static void part2(byte[] input) {
        Map<Integer, String> numMapping = new HashMap<>();
        numMapping.put(48, "0");
        numMapping.put(49, "1");
        numMapping.put(50, "2");
        numMapping.put(51, "3");
        numMapping.put(52, "4");
        numMapping.put(53, "5");
        numMapping.put(54, "6");
        numMapping.put(55, "7");
        numMapping.put(56, "8");
        numMapping.put(57, "9");

        Map<String, String> strMapping = new HashMap<>();
        strMapping.put("zero", "0");
        strMapping.put("one", "1");
        strMapping.put("two", "2");
        strMapping.put("three", "3");
        strMapping.put("four", "4");
        strMapping.put("five", "5");
        strMapping.put("six", "6");
        strMapping.put("seven", "7");
        strMapping.put("eight", "8");
        strMapping.put("nine", "9");

        int sum = 0;
        String first = "";
        String last = "";
        String seq = "";
        for (int b : input) {
            if (numMapping.containsKey(b)) {
                seq = "";
                if (first.isEmpty()) {
                    first = numMapping.get(b);
                    last = first;
                } else {
                    last = numMapping.get(b);
                }
            } else {
                seq += Character.toString(b);
                Optional<String> match = strMapping.entrySet()
                        .stream()
                        .parallel()
                        .map(Map.Entry::getKey)
                        .filter(seq::contains)
                        .findAny();

                if (match.isPresent()) {
                    if (first.isEmpty()) {
                        first = strMapping.get(match.get());
                        last = first;
                    } else {
                        last = strMapping.get(match.get());
                    }

                    seq = "";
                }
            }

            if (b == 10) {
                sum += Integer.valueOf(Integer.valueOf(first + last));
                first = "";
                last = "";
            }
        }

        System.out.println("part 2: " + sum);
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get(System.getProperty("user.dir") + File.separator + "input.txt");
            byte[] input = Files.readAllBytes(path);

            part1(input);
            part2(input);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
