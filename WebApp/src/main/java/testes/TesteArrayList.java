package testes;

import java.util.ArrayList;

public class TesteArrayList {

	
	public static void main(String[] args) {
		ArrayList<Integer> match_ids = new ArrayList<Integer>();
		
		match_ids.add(1);
		match_ids.add(null);
		
		System.out.println(match_ids);
		System.out.println(match_ids.get(0));
		System.out.println(match_ids.get(1));
		System.out.println(match_ids.size());
	}
}
