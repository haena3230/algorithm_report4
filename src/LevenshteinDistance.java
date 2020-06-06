import java.util.Scanner;
import java.util.*;

// Report4_편집거리알고리즘
public class LevenshteinDistance {
	
	// 비교할 문자열
	static char x [];
	static char y [];
	
	
	// 문자열 길이
	static int n;
	static int m;
	
	// 편집 비용저장할 테이블
	static int CostTable [][];
	
	// 편집 문자열 상태를 저장할 테이블
	static String charTable[][];
	static char xarray[];
	static char yarray[];
	
	// stack 관련 변수
	static char stack[] = new char [99] ;
	static int top = 0;
	static int size = 0;
	static int Ctop = 0;
	
	// 문자열 변환 과정을 저장할 배열
	static String charset[];
	
	// 문자열 입력 메소드
	public static void Input() {
		xarray = new char[n];
		yarray = new char[m];
		
		// 문자열 x, y 입력
		System.out.print("문자열 x, y 입력 : ");
		
		// String으로 입력 받아서 char 배열에 한글자씩 저장
		Scanner scan = new Scanner(System.in);
		String first;
		String second;
		first = scan.next();
		second = scan.next();
		
		// 입력받은 문자열 배열에 저장
		x = new char[first.length()];
		y = new char[second.length()];
		n = x.length;
		m = y.length;
		System.out.print("입력받은 문자열 x는 : ");
		for(int i=0;i<n;i++){ 
			x[i]=(first.charAt(i));
			System.out.print(x[i]);
		}
		System.out.println("이고, 길이 n은  "+n + "입니다.");
		System.out.print("입력받은 문자열 y는 : ");
		for(int i=0;i<m;i++){ 
			y[i]=(second.charAt(i));
			System.out.print(y[i]);
		}
		System.out.println("이고, 길이 m은  "+m + "입니다.");
		xarray=x;
		yarray=y;
		
	}
	
	// 연산된 문자열 테이블 출력 메소드
	public static void CharTable(){
	
		// 편집된 x문자열과 y 문자열의 상태를 보여주는 배열 array
		char yyarray[]= new char[m];
		// 연산된 문자열 저장 테이블
		charTable = new String[n+1][m+1];
		
		// 삭제되는 x의 문자열 배열을 string으로 저장
		String X[]= new String[n];
		String Y[]= new String[m];
		String str = String.valueOf(xarray);
		charTable[0][0] = str;
		for(int i=0;i<n;i++) {
			xarray[i]=0;
			String xarrayString = String.valueOf(xarray);
			X[i] = xarrayString;
			
		}
		
		for(int i=0; i<m ;i++) {
			yyarray[i]=yarray[i];
			String yarrayString = String.valueOf(yyarray);
			Y[i] = yarrayString;
			
		}
		
		
		for(int i = 0; i<n;i++) {
			for(int j=0;j<m;j++) {
				charTable[i+1][j+1] = Y[j] + X[i];
			}
		}
	}
	
	// cost 계산 메소드 
	public static int Cost(char[] s, char[] t){
		
		CostTable = new int [n+1][m+1];
        for (int i = 1; i <= n; i++) {
        	CostTable[i][0] = i*2; // 삭제 비용 2    	
        }

        for (int j = 1; j <= m; j++) {
        	CostTable[0][j] = j*3; // 삽입 비용 3 
        }
        
		// cost 계산
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (charTable[i][j] == charTable[i-1][j-1]) {
                	CostTable[i][j] = CostTable[i - 1][j - 1];
                	
                } else {
                	CostTable[i][j] = Math.min(CostTable[i - 1][j] + 2, Math.min(CostTable[i][j - 1] + 3 , CostTable[i - 1][j - 1] + 1));
                }
            }
        }
        return CostTable[n][m];
	}
	
	 // Table 출력 메소드
	public static void CostTableOutput() {
		 for (int i = 0; i <= n; i++) {
		        for (int j = 0; j <=  m; j++) {
		            System.out.print(CostTable[i][j] + " ");
		        }
		        System.out.println("");
		 }
	}

    // 편집순서 저장할 stack 삽입   
	public static void StackPush(char item) {
        stack[top] = item;
        top ++;
        size++;
    }
	
	// 편집순서 나타낼 stack 삭제
	public static void StackPop() {
        System.out.print(stack[top]);
        stack[top--] = 0;
    }
	
	
	
	// 역추적 
	// 문자열 x와 y의 인덱스를 뒤에서부터 줄여가며 stack에 저장했다 출력하는 함수를 작성했습니다
	public static void getTrace() {
		int a = n; 
		int b = m;
		charset = new String [99];
		
		
		// x,y 문자열 둘다 살아있을때
		while(a!=0 && b!=0) {
			if(CostTable[a][b] == CostTable[a - 1][b] + 2) {
				StackPush('D');
				charset[Ctop] = charTable[a][b];
				Ctop++;
				a--;
			}
			else if (CostTable[a][b] == CostTable[a][b-1] + 3) {
				StackPush('I');
				charset[Ctop] = charTable[a][b];
				Ctop++;
				b--;
			}
			else if(CostTable[a][b] == CostTable[a-1][b-1] || CostTable[a][b] == CostTable[a-1][b-1]+1) {
				StackPush('C');
				charset[Ctop] = charTable[a][b];
				Ctop++;
				a--;
				b--;
			}			
		}
		// 문자열 y만 남았을때 끝날때까지 삽입
		if(a == 0 && b != 0) { 
			while (b>=0) {
				StackPush('I');
				charset[Ctop] = charTable[a][b];
				Ctop++;
				b--;
			}
		}
		// 문자열 x만 남았을때 끝날때까지 삭제
		else if(b == 0 && a != 0) { 
			while (a>=0) {
				StackPush('D');
				charset[Ctop] = charTable[a][b];
				a--;
			}
		}
		// stack 삭제를 통한 출력
		for(int i=0 ; i<=size;i++)
		StackPop();
		System.out.println("");
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		// 함수 내 stack 삭제를 통한 문자열 편집 순서 출력
		System.out.println("입력 문자열에 차례로 적용");		
		
		while(Ctop-1 != 0) {
		System.out.print(charset[Ctop-1]);
		charset[Ctop--] = null;
		System.out.println("");
		}
		
	}
	
	
	
	// main 메소드
	public static void main(String[] args) {
		Input(); // 문자열 x, y 입력
		
		// 문자열 편집 테이블
		CharTable();
		
		int resultC = Cost(x, y); // 최소편집거리 계산
		
		System.out.println("");
		System.out.println("cost(i,j) 표는 다음과 같습니다."); 
		
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		// cost(i,j) 테이블 출력
		CostTableOutput(); 
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		
		System.out.println("");
		// 최소 편집 비용
		System.out.println("최소 편집 비용은  " + resultC + "입니다.");
		
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		
		// 연산 종류 역추적
		System.out.print("최적의 편집 순서열 : ");
		getTrace();
		System.out.println("");
		
	 }
	
}
