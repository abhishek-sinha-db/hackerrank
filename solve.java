import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

     static long countTriplets(List<Long> arr, long r) {

        Map<Long, List<Long>> numberToIndices = new HashMap<>();
        for (int i = 0; i < arr.size(); i++) {
            if (!numberToIndices.containsKey(arr.get(i))) {
                numberToIndices.put(arr.get(i), new ArrayList<Long>());
            }

            numberToIndices.get(arr.get(i)).add(Long.valueOf(i));
        }

        long result = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) % r != 0) {
                continue;
            }

            long firstNumber = arr.get(i) / r;

            if (arr.get(i) * r > Integer.MAX_VALUE) {
                continue;
            }

            long lastNumber = arr.get(i) * r;

            result += (long) findBeforeCount(numberToIndices, firstNumber, i)
                    * findAfterCount(numberToIndices, lastNumber, i);
        }
        return result;

    }

    static int findBeforeCount(Map<Long, List<Long>> numberToIndices, Long number, int index) {
        if (!numberToIndices.containsKey(number)) {
            return 0;
        }

        List<Long> indices = numberToIndices.get(number);
        int position = Collections.binarySearch(indices, Long.valueOf(index));
        if (position < 0) {
            position = -1 - position;
        }

        return position;
    }

    static int findAfterCount(Map<Long, List<Long>> numberToIndices, Long number, int index) {
        if (!numberToIndices.containsKey(number)) {
            return 0;
        }

        List<Long> indices = numberToIndices.get(number);
        int position = Collections.binarySearch(indices, Long.valueOf(index));
        if (position < 0) {
            position = -1 - position - 1;
        }

        return indices.size() - 1 - position;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nr[0]);

        long r = Long.parseLong(nr[1]);

        List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Long::parseLong)
            .collect(toList());

        long ans = countTriplets(arr, r);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
