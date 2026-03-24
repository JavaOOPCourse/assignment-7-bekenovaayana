import java.io.*;
import java.util.*;

public class StudentRecordProcessor {

    // Хранение студентов
    private final List<Student> students = new ArrayList<>();

    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {

        String inputPath = "data/students.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {

            String line;

            while ((line = br.readLine()) != null) {

                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid format");
                    }

                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    // кастомная проверка
                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Score out of range: " + score);
                    }

                    students.add(new Student(name, score));

                    // вывод валидных строк
                    System.out.println("Valid: " + line);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data: " + line);
                } catch (Exception e) {
                    System.out.println("Invalid data: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + inputPath);
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {

        if (students.isEmpty()) {
            System.out.println("No valid data to process.");
            return;
        }

        int sum = 0;

        for (Student s : students) {
            sum += s.getScore();
        }

        averageScore = (double) sum / students.size();

        // максимальный студент
        highestStudent = Collections.max(students, Comparator.comparingInt(Student::getScore));

        // сортировка по убыванию
        students.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {

        String outputPath = "output/report.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            bw.write("Average: " + averageScore);
            bw.newLine();

            if (highestStudent != null) {
                bw.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
                bw.newLine();
            }

            bw.newLine();
            bw.write("Sorted Students:");
            bw.newLine();

            for (Student s : students) {
                bw.write(s.getName() + " - " + s.getScore());
                bw.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

/**
 * Task 6 — кастомное исключение
 */
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

/**
 * Модель студента
 */
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
} 