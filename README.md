# 2019_KAKAO_Developer_intern_03

## 2019 카카오 개발자 겨울 인턴십 코딩테스트 3번

### 1. 문제설명

input으로 `user_id`와 `banned_id`가 들어온다. `banned_id`는 아이디에 `*`문자를 섞어서 가진 아이디의 패턴이며 이와 일치하는 `user_id`하나를 제재 아이디로 선정할 수 있다. 이때 여러 제외되어야 할 아이디의 목록으로 가능한 모든 경우의 수를 return하는 문제.

단, 중복되는 아이디를 선택하여서는 안되며 제외 아이디 목록의 순서가 달라도 아이디 목록의 내용이 동일하다면 같은 것으로 처리하여 하나로 센다.

### 2. 풀이

금지할 아이디패텅 대해 후보 아이디를 담은 클래스를 정의하고 이를 조사한다. 모든 금지 패턴에 대한 아이디 목록을 구하면 DFS를 이용해 하나의 패턴마다 제외 아이디를 선택한 `Set`을 만들어 완성된 제외 목록 리스트를 저장한다. 이 완성된 제외 목록 리스트를 저장할 때 순서만 다른 경우를 세지않게 하기 위해 `HashSet`에 저장한다. 모든 경우에 대한 조사를 마치면 만들어진 목록의 개수를 return한다.

```java

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

```
