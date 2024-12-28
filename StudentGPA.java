package studentgpa;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class StudentGPA
{

    
    public static void main(String[] args)
    {
         final int MAX_STUDENTS = 1000;
        String[] studentIDs = new String[MAX_STUDENTS];
        double[] gpas = new double[MAX_STUDENTS];
        int studentCount = 0;

        // Step 1: Load data from "studentdata.txt
       

        
        try (Scanner scanner = new Scanner(new File("studentdata.txt"))) {
            while (scanner.hasNextLine() && studentCount < MAX_STUDENTS) {
                String[] data = scanner.nextLine().split("\\s+");
                studentIDs[studentCount] = data[0];
                gpas[studentCount] = Double.parseDouble(data[1]);
                studentCount++;
            
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found.");
            return;
        }

        // Step 2: Generate GPA Histogram
        int[] histogram = new int[8];
        for (int i = 0; i < studentCount; i++) {
            double gpa = gpas[i];
            if (gpa < 0.5) histogram[0]++;
            else if (gpa < 1.0) histogram[1]++;
            else if (gpa < 1.5) histogram[2]++;
            else if (gpa < 2.0) histogram[3]++;
            else if (gpa < 2.5) histogram[4]++;
            else if (gpa < 3.0) histogram[5]++;
            else if (gpa < 3.5) histogram[6]++;
            else histogram[7]++;
        }

        // Step 3: Display Histogram
        String[] ranges = {
            "0.0 to 0.49", "0.5 to 0.99", "1.0 to 1.49",
            "1.5 to 1.99", "2.0 to 2.49", "2.5 to 2.99",
            "3.0 to 3.49", "3.5 to 4.0"
        };

        System.out.println("GPA Histogram:");
        for (int i = 0; i < histogram.length; i++) {
            int starCount = (int) Math.round(histogram[i] / 10.0);
            System.out.printf("%-15s (%d) ", ranges[i], histogram[i]);
            System.out.println("*".repeat(starCount));
        }
        System.out.println();

        // Step 4: Calculate Class Ranks
        Integer[] rankIndices = new Integer[studentCount];
        for (int i = 0; i < studentCount; i++) {
            rankIndices[i] = i;
        }

        Arrays.sort(rankIndices, Comparator.comparingDouble((Integer i) -> -gpas[i]));

        int[] ranks = new int[studentCount];
        Arrays.fill(ranks, 1);

        for (int i = 1; i < studentCount; i++) {
    if (Double.compare(gpas[rankIndices[i]], gpas[rankIndices[i - 1]]) != 0) {
        ranks[rankIndices[i]] = i + 1;
    } else {
        ranks[rankIndices[i]] = ranks[rankIndices[i - 1]];
    }
}


        // Step 5: Display Student Data with Class Ranks
        System.out.println("Student GPA and Class Rank:");
        for (int i = 0; i < studentCount; i++) {
            int rank = ranks[i];
            double gpa = gpas[i];
            String studentID = studentIDs[i];

            // Find the count of students with the same GPA
            int sameGpaCount = 0;
            for (double studentGpa : gpas) {
                if (studentGpa == gpa) sameGpaCount++;
            }

            // Display the student's information
            if (sameGpaCount > 1) {
                System.out.printf("%s %.2f T%d with %d others\n", studentID, gpa, rank, sameGpaCount - 1);
            } else {
                System.out.printf("%s %.2f %d\n", studentID, gpa, rank);
            }
        }
    }
}


