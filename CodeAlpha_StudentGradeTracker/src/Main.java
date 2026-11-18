import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<ArrayList<Double>> allGrades = new ArrayList<ArrayList<Double>>();

        boolean running = true;
        while (running) {
            System.out.println("\n=== Student Grade Tracker ===");
            System.out.println("1. Add a grade");
            System.out.println("2. Show report");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = input.nextLine();

            if (choice.equals("1")) {
                System.out.print("Student name: ");
                String name = input.nextLine().trim();
                if (name.length() == 0) {
                    System.out.println("Please enter a name.");
                    continue;
                }

                System.out.print("Grade from 0 to 100: ");
                String gradeText = input.nextLine();
                double grade;
                try {
                    grade = Double.parseDouble(gradeText);
                } catch (NumberFormatException error) {
                    System.out.println("Please enter a number.");
                    continue;
                }
                if (grade < 0 || grade > 100) {
                    System.out.println("Grade must be from 0 to 100.");
                    continue;
                }

                int studentIndex = -1;
                for (int i = 0; i < names.size(); i++) {
                    if (names.get(i).equalsIgnoreCase(name)) {
                        studentIndex = i;
                    }
                }
                if (studentIndex == -1) {
                    names.add(name);
                    allGrades.add(new ArrayList<Double>());
                    studentIndex = names.size() - 1;
                }
                allGrades.get(studentIndex).add(grade);
                System.out.println("Grade saved.");

            } else if (choice.equals("2")) {
                if (names.size() == 0) {
                    System.out.println("No grades have been recorded yet.");
                } else {
                    System.out.println("\nStudent | Average | Highest | Lowest");
                    for (int i = 0; i < names.size(); i++) {
                        ArrayList<Double> grades = allGrades.get(i);
                        double total = 0;
                        double highest = grades.get(0);
                        double lowest = grades.get(0);
                        for (int j = 0; j < grades.size(); j++) {
                            double currentGrade = grades.get(j);
                            total = total + currentGrade;
                            if (currentGrade > highest) highest = currentGrade;
                            if (currentGrade < lowest) lowest = currentGrade;
                        }
                        double average = total / grades.size();
                        System.out.printf("%s | %.2f | %.2f | %.2f%n", names.get(i), average, highest, lowest);
                    }
                }
            } else if (choice.equals("3")) {
                running = false;
            } else {
                System.out.println("Please choose 1, 2, or 3.");
            }
        }
        System.out.println("Goodbye!");
    }
}
