import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Checker {
	
	
	public static boolean email(String text) {
		Pattern patternMail = Pattern.compile("[a-zA-Z]{1}[a-zA-Z\\d._]+@([a-zA-Z]+.){1,2}((net)|(com)|(org)|(ru)|(by))");
		Matcher matcherMail = patternMail.matcher(text);
		  while (matcherMail.find()) {    	
		      matcherMail.group();
		    }
		return true;
		      
	}

	public static boolean password(String text) {
		Pattern patternPass = Pattern.compile("[a-zA-Z0-9]");
		Matcher matcherPass = patternPass.matcher(text);
		 while (matcherPass.find()) {
		      matcherPass.group();
		    }
		return true;
	}

	
	
}
