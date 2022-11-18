package onlineSearcher;

public class NullValuedSearchParameters {

	public static final String[] vocals = {"a","e","i","o","u"};
	public static final String[] consonant = {"q","w","r","t","y","p","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
	public static final String[] junction = {"and","or","per","di","da","in","con","su","tra","fra"};
	
	public static boolean contains(String par) {
		for(String a:vocals) {
			if(par.equals(a)) {
				return true;
			}
		}
		for(String a:consonant) {
			if(par.equals(a)) {
				return true;
			}
		}
		for(String a:junction) {
			if(par.equals(a)) {
				return true;
			}
		}
		return false;
	}
	
}
