package Utilities;

import java.util.ArrayList;
import java.util.List;

public class TextDivider {

	public static List<String> textSplicing(String params) {
		List<String> compositeParams=new ArrayList<>();
		
		CharSequence remaining="";
		do{
		int spacePos = params.indexOf(" ", 0);
		CharSequence subseq= params.subSequence(0, spacePos);
		String sub= (String) subseq;
		if(!sub.isEmpty()) {
			compositeParams.add((String)subseq);
		}
		remaining=params.subSequence(spacePos+1, params.length());
		params=(String)remaining;
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
