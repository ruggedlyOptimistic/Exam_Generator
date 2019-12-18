import java.util.List;
import java.util.PriorityQueue;

public class PracticeExam
{
    private List<Question> qList;
    private int numQuestions;
    private int numRight;

    public PracticeExam(List<Question> qList, int numQuestions, int numRight)
    {
        this.qList = qList;
        this.numQuestions = numQuestions;
        this.numRight = numRight;
    }


    public List<Question> getqList() {
        return qList;
    }

    public void setqList(List<Question> qList) {
        this.qList = qList;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumRight(int n)
    {
        this.numRight = n;
    }

    public int getNumRight() {
        return numRight;
    }
}
