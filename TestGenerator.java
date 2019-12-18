import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

public class TestGenerator
{
    public static Scanner keyboard = new Scanner(System.in);
    private static String filename = null;
    private static String length = null;

    public static void menu()
    {
        System.out.println("Enter the filename of the test bank you wish to use: ");
        filename = "TestBank/" + keyboard.nextLine();
//        filename = "TestBank/PHIL336.csv";
        System.out.println("Enter the number of questions you'd like to use");
        length = keyboard.nextLine();

        List<Question> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        generateExam(filename, Integer.parseInt(length), questions);
        PracticeExam exam = new PracticeExam(questions, Integer.parseInt(length), 0);

        runTest(exam, questions, answers);
    }

    public static void runTest(PracticeExam exam, List<Question> questions, List<String> answers)
    {
        System.out.println("Starting test..." + "\n");
        int order = 1;
        for (Question q : exam.getqList())
        {
            System.out.printf("[%d]\t%s\n", order++, q.getStatement());
            String myAnswer = keyboard.nextLine();
            answers.add(myAnswer);
        }
        System.out.println("Standby for test results..." + "\n");
        order = 1;
        int i = 0;
        System.out.println("Summary...");
        for (Question q : questions)
        {
            System.out.printf("[%d]\t%s\n", order++, q.getStatement());
            System.out.println("\t\tCorrect Answer: " + q.getAnswer());
            System.out.println("\t\tYour Answer: " + answers.get(i++) + "\n");
        }

        System.out.println("How many questions did you miss?");
        int missed = keyboard.nextInt();
        exam.setNumRight(exam.getNumQuestions()-missed);

        writeSummary(exam);
        menu();
    }

    public static void main(String[] args)
    {
        System.out.println("Good luck on your practice exam!" + "\n");
        menu();
    }

    private static void writeSummary(PracticeExam exam)
    {
        int counter = 0;
        List<String> temp = new ArrayList<>();
        File f = new File("philosphyFinalExamTrend.csv");
        try
        {
            Scanner inputFile = new Scanner(f);
            while (inputFile.hasNextLine())
            {
                String line = inputFile.nextLine();
                temp.add(line);
                counter++;
            }

            temp.add((counter) + "|" + String.valueOf((double) exam.getNumRight()/exam.getNumQuestions())); // the percentage of questions right
            inputFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error reading that file!");
        }

        try
        {
            PrintWriter outputFile = new PrintWriter(f);

            for (String s : temp)
            {
                outputFile.println(s);
            }
            outputFile.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("Error writing to the file!");
            e.printStackTrace();
        }
    }

    private static List<Question> generateExam(String filename, int length, List<Question> qList)
    {
        int progressCounter = 1;
        System.out.println("Loading file..." + filename);
        Random r = new Random();
        File f = new File(filename);
        List<Question> temp = new ArrayList<>();


        try {
            Scanner inputFile = new Scanner(f);
            inputFile.nextLine();   // throw away the header

            while (inputFile.hasNextLine())
            {
                String line = inputFile.nextLine();
//                System.out.println("Loading " + progressCounter++ + " items...");
                String[] components = line.split(Pattern.quote("|"));
                Question q = new Question(components[0], components[1]);
                temp.add(q);
//                System.out.println(q.getStatement() + ", " + q.getAnswer());
//                line = inputFile.nextLine();
            }
            inputFile.close();

            if (length > temp.size())
            {
                System.out.println("The specified test length exceeds the number of questions in the bank!");
                System.out.println("Length = " + temp.size());
            }
            for (int index = 0; index < length; index ++)
            {
                int rand = r.nextInt(temp.size());
                qList.add(temp.get(rand));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error loading that file!");
            e.printStackTrace();
        }

        System.out.println("Done!");
        return qList;
    }
}
