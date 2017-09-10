package comparator;

import java.util.Comparator;

import modelo.Question;

public class CompareQuestionsByReverseDate implements Comparator<Question> {

	@Override
    public int compare(Question o1, Question o2) {
		return o1.getDate_created().compareTo(o2.getDate_created());       
    }
	
}
