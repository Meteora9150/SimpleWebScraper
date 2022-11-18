package onlineSearcher;

import java.util.ArrayList;
import java.util.List;

public class TextDivider {

	public static List<String> textSplicing(String params) {
		List<String> compositeParams=new ArrayList<>();
		
		CharSequence remaining="";
		do{
		//System.out.println(" cè uno spazio, rimanente = "+remaining.length());
		int spacePos = params.indexOf(" ", 0);
		//System.out.println(spacePos);
		CharSequence subseq= params.subSequence(0, spacePos);
		//System.out.println((String)subseq);
		String sub= (String) subseq; // non so perchè a casa questa riga dia problemi ma facciamo così
		if(!sub.isEmpty()) {
			compositeParams.add((String)subseq);
		}
		remaining=params.subSequence(spacePos+1, params.length());
		//System.out.println((String)remaining);
		params=(String)remaining;
		//System.out.println(" params remaining = "+params);
		}while(params.contains(" "));
		compositeParams.add((String)remaining);
		return compositeParams;
	}
	
	public static String queryAdapterOnline(List<String> arguments) {
		String result="";
		for(String s:arguments) {
			if(result=="") {
				result=result+s;
			}else {
				result=result+" "+s;
			}

		}
		return result;
	}
}
