import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GearRatiosPartTwo {
    static Set<Character> nums = Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

    static int handleNumber(String line, int idx) {
        if (nums.contains(line.charAt(idx))) {
            int startIdx = idx;
            int endIdx = idx;

            // to right
            if (!(idx + 1 > line.length() - 1)) {
                while (nums.contains(line.charAt(endIdx + 1))) {
                    endIdx += 1;

                    if (endIdx == line.length() - 1) {
                        break;
                    }
                }
            }

            // to left
            if (idx != 0) {
                while (nums.contains(line.charAt(startIdx - 1))) {
                    startIdx -= 1;

                    if (startIdx == 0) {
                        break;
                    }
                }
            }

            return Integer.valueOf(line.substring(startIdx, endIdx + 1));
        }

        return -1;
    }

    static int handleStar(String topLine, String mainLine, String bottomLine, int idx) {
        var numSet = new HashSet<Integer>();

        // start from top and continue
        /*
         * directions
         * 0 1 2
         * 3 * 4
         * 5 6 7
         */
        var i = 0;
        var j = 7;

        if (topLine.isEmpty()) {
            i = 3;
        }
        if (bottomLine.isEmpty()) {
            j = 4;
        }

        while (i < j + 1 && numSet.size() < 2) {
            switch (i) {
                case 0: {
                    int num = handleNumber(topLine, idx - 1);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 1: {
                    int num = handleNumber(topLine, idx);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 2: {
                    int num = handleNumber(topLine, idx + 1);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 3: {
                    int num = handleNumber(mainLine, idx - 1);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 4: {
                    int num = handleNumber(mainLine, idx + 1);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 5: {
                    int num = handleNumber(bottomLine, idx - 1);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 6: {
                    int num = handleNumber(bottomLine, idx);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
                case 7: {
                    int num = handleNumber(bottomLine, idx + 1);
                    if (num != -1)
                        numSet.add(num);
                    break;
                }
            }

            i++;
        }

        return numSet.size() == 2 ? numSet.stream().reduce(1, (a, b) -> a * b) : 0;
    }

    static void part2(List<String> input) {
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
                if (mainLine.charAt(j) == '*') {
                    sum += handleStar(topLine, mainLine, bottomLine, j);
                }
            }
        }

        System.out.println("part2: " + sum);
    }

    public static void main(String[] args) {
        try {
            var path = Paths.get(System.getProperty("user.dir") + File.separator + "input.txt");
            var input = Files.readAllLines(path);

            part2(input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
