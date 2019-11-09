import java.util.HashSet;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
//		String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
//		String[] banned_id = {"fr*d*", "abc1**"};
		// return 2
		
//		String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
//		String[] banned_id = {"*rodo", "*rodo", "******"};
//		// return 2
		
		String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
		String[] banned_id = {"fr*d*", "*rodo", "******", "******"};
//		// return 3
		
		System.out.println(new Solution().solution(user_id, banned_id));
	}

}


class Solution {
    
	class BannedId {
		String bannedID;
		HashSet<String> candidates;
		
		public BannedId(String bannedID) {
			this.bannedID = bannedID;
			candidates = new HashSet<>();
		}
		
		public void addCandidate(String candidate) {
			this.candidates.add(candidate);
		}
		
		public HashSet<String> getCandidates() {
			return this.candidates;
		}
	
		@Override
		public String toString() {
			return this.bannedID + " " + this.candidates + "\n";
		}
		
	}
	
	HashSet<HashSet<String>> mainSet = new HashSet<>();
	BannedId[] bArr;
	public int solution(String[] user_id, String[] banned_id) {
        int answer = 0;
        
        bArr = new BannedId[banned_id.length];
        
        for (int i = 0; i < banned_id.length; i++) {
        	String pattern = banned_id[i];
        	BannedId newBannedId = new BannedId(pattern);
        	for (String uId : user_id) {
        		if (isCandidate(pattern, uId))
        			newBannedId.addCandidate(uId);
        	}
        	bArr[i] = newBannedId;
        }
        
        HashSet<String> temp = new HashSet<>();
       
        dfs(0, banned_id.length, temp);
        
        return mainSet.size();
    }
	
	private void dfs(int i, int n, HashSet<String> temp) {
		if (i == n) {
			mainSet.add(temp);
			return;
		}
		
		BannedId bannedId = bArr[i];
		
		for (Iterator<String> it = bannedId.candidates.iterator(); it.hasNext(); ) {
			String candidate = it.next();
			if (temp.contains(candidate))
				continue;
			else {
				HashSet<String> nextTemp = new HashSet<>(temp);
				nextTemp.add(candidate);
				dfs(i+1, n, nextTemp);
			}
		}
		
	}

	private boolean isCandidate(String pattern, String id) {
		
		if (pattern.length() != id.length())
			return false;
		
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c == '*')
				continue;
			else {
				if (c != id.charAt(i))
					return false;
			}
		}
		
		return true;
	}
	
}