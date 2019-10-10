package math;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Wicmage
 */

public class FunctionParser {

	private String function;
	
	// Obtains the function and decomposes it to a tree form
	public FunctionParser(String f) {
		// Locate parenthesis
		List<List<Integer>> parData = Common.locateParenthesis(f);
		this.function=f;
		
		List<String> parenthesis=new ArrayList<String>();
		for(List<Integer> li : parData) {
			if(li.get(0)!=li.get(1)) {
				String p=function.substring(li.get(0)+1, li.get(1));
				parenthesis.add(p);
				function=function.replace("("+p+")", "PARENTHESIS"+parData.indexOf(li));
			}
		}
		
		boolean hasToken=false;
		int i=0;
		for(;i<Common.TOKENS.length;i++)
			if(function.contains(Common.TOKENS[i])) {
				hasToken=true;
				break;
			}
		
		if(hasToken) {
			String[] parts = function.split("\\"+Common.TOKENS[i],2);
			for(int j=0;j<parenthesis.size();j++) {
				if(Common.containsToken(parts[0]))
					parts[0]=parts[0].replace("PARENTHESIS"+j, "(" + parenthesis.get(j) + ")");
				else
					parts[0]=parts[0].replace("PARENTHESIS"+j, parenthesis.get(j));
				if(Common.containsToken(parts[1]))
					parts[1]=parts[1].replace("PARENTHESIS"+j, "(" + parenthesis.get(j) + ")");
				else
					parts[1]=parts[1].replace("PARENTHESIS"+j, parenthesis.get(j));
			}
			this.function = Common.TOKENS[i] + "(";
			this.function+=new FunctionParser(parts[0]).toString() + "," + new FunctionParser(parts[1]).toString()+")";
		}
	}
	
	public String toString() {
		return function;
	}
	
}