package comparator;

import java.util.Comparator;

import modelo.Question;

public class CompareQuestionsByUser implements Comparator<Question> {
	
	@Override
    public int compare(Question o1, Question o2) {
		int result = o1.getSeller().getNickname().compareTo(o2.getSeller().getNickname());
        if (result == 0) {
            result = o2.getDate_created().compareTo(o1.getDate_created());
        } else {
            result = -result;
        }
        return result;     
    }
	
}
