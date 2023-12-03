import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CubeConundrum {
    public static void part1(List<String> input) {
        var maxMapping = new HashMap<String, Integer>();
        maxMapping.put("red", 12);
        maxMapping.put("green", 13);
        maxMapping.put("blue", 14);

        int sum = 0;
        for (var i = 0; i < input.size(); i++) {
            var sets = input.get(i).split(": ")[1].split("; ");
            var ok = true;

            for (String set : sets) {
                var cubes = set.split(", ");

                for (String cube : cubes) {
                    var values = cube.split(" ");

                    if (Integer.valueOf(values[0]) > maxMapping.get(values[1])) {
                        ok = false;
                    }
                }
            }

            if (ok) {
                sum += i + 1; // + 1: games start from index 1
            }
        }

        System.out.println(sum);
    }

    public static void part2(List<String> input) {
        int sum = 0;
        for (String game : input) {
            var sets = game.split(": ")[1].split("; ");
            var totalCubes = new HashMap<String, Integer>();
            totalCubes.put("red", 0);
            totalCubes.put("green", 0);
            totalCubes.put("blue", 0);

            for (String set : sets) {
                var cubes = set.split(", ");

                for (String cube : cubes) {
                    var values = cube.split(" ");

                    if (totalCubes.get(values[1]) < Integer.valueOf(values[0])) {
                        totalCubes.put(values[1], Integer.valueOf(values[0]));
                    }
                }
            }

            System.out.println(totalCubes);

            sum += totalCubes.entrySet().stream().map(Map.Entry::getValue).reduce(1, (a, b) -> a * b);
        }

        System.out.println(sum);
    }

    public static void main(String[] args) {
        try {
            var path = Paths.get(System.getProperty("user.dir") + File.separator + "input.txt");
            var input = Files.readAllLines(path);

            part1(input);
            part2(input);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
