import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GearRatios {
    static Set<Character> nums = Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

    // checks the string for a number starting from `startIdx` and returns the
    // `endIdx`
    static int getNumber(String line, int startIdx) {
        var endIdx = startIdx;

        for (var i = startIdx + 1; i < line.length(); i++) {
            if (nums.contains(line.charAt(i))) {
                endIdx++;
            } else {
                break;
            }
        }

        return endIdx;
    }

    static int checkAdjacent(String line, int adjIdx, int startIdx, int endIdx) {
        int parsed = -1;

        if (line.charAt(adjIdx) != '.' && !(nums.contains(line.charAt(adjIdx)))) {
            parsed = Integer.valueOf(line.substring(startIdx, endIdx + 1));
        }

        return parsed;
    }

    static int checkRange(String mainLine, String secondLine, int startIdx, int endIdx) {
        int parsed = -1;
        String range = "";

        if (startIdx == 0) {
            range = secondLine.substring(startIdx, endIdx + 1 + 1);
        } else if (endIdx + 1 > secondLine.length() - 1) {
            range = secondLine.substring(startIdx - 1, endIdx + 1);
        } else {
            range = secondLine.substring(startIdx - 1, endIdx + 1 + 1);
        }

        for (var k = 0; k < range.length(); k++) {
            if (range.charAt(k) != '.' && !(nums.contains(range.charAt(k)))) {
                return parsed = Integer.valueOf(mainLine.substring(startIdx, endIdx + 1));
            }
        }

        return parsed;
    }

    static void part1(List<String> input) {
        var topLine = "";
        var mainLine = "";
        var bottomLine = "";

        int sum = 0;
        for (var i = 0; i < input.size(); i++) {
            if (i == 0) { // handle first line
                mainLine = input.get(i);
                bottomLine = input.get(i + 1);
            } else if (i + 1 > input.size() - 1) { // handle last line
                topLine = mainLine;
                mainLine = input.get(i);
                bottomLine = "";
            } else {
                topLine = mainLine;
                mainLine = input.get(i);
                bottomLine = input.get(i + 1);
            }

            for (var j = 0; j < mainLine.length(); j++) {
                if (nums.contains(mainLine.charAt(j))) {
                    var endIdx = getNumber(mainLine, j);

                    // check main line
                    if (j != 0) { // left
                        int parsed = checkAdjacent(mainLine, j - 1, j, endIdx);
                        if (parsed != -1) {
                            sum += parsed;
                            j = endIdx + 1;
                            continue;
                        }
                    }
                    if (!(endIdx + 1 > mainLine.length() - 1)) { // right
                        int parsed = checkAdjacent(mainLine, endIdx + 1, j, endIdx);
                        if (parsed != -1) {
                            sum += parsed;
                            j = endIdx + 1;
                            continue;
                        }
                    }

                    // check top line
                    if (!topLine.isEmpty()) {
                        int parsed = checkRange(mainLine, topLine, j, endIdx);
                        if (parsed != -1) {
                            sum += parsed;
                            j = endIdx + 1;
                            continue;
                        }
                    }

                    // check bottom line
                    if (!bottomLine.isEmpty()) {
                        int parsed = checkRange(mainLine, bottomLine, j, endIdx);
                        if (parsed != -1) {
                            sum += parsed;
                            j = endIdx + 1;
                            continue;
                        }
                    }

                    j = endIdx + 1;
                }
            }
        }

        System.out.println("part1: " + sum);
    }

    public static void part2(List<String> input) {

    }

    public static void main(String[] args) {
        try {
            var path = Paths.get(System.getProperty("user.dir") + File.separator + "input.txt");
            var input = Files.readAllLines(path);

            part1(input);
            part2(input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
