public class Question
{
    private String statement;
    private String answer;

    public Question(String statement, String answer)
    {
        this.statement = statement;
        this.answer = answer;
    }

    public String getStatement()
    {
        return statement;
    }

    public String getAnswer()
    {
        return answer;
    }
}
