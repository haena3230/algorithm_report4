import java.util.Scanner;
import java.util.*;

// Report4_�����Ÿ��˰���
public class LevenshteinDistance {
	
	// ���� ���ڿ�
	static char x [];
	static char y [];
	
	
	// ���ڿ� ����
	static int n;
	static int m;
	
	// ���� ��������� ���̺�
	static int CostTable [][];
	
	// ���� ���ڿ� ���¸� ������ ���̺�
	static String charTable[][];
	static char xarray[];
	static char yarray[];
	
	// stack ���� ����
	static char stack[] = new char [99] ;
	static int top = 0;
	static int size = 0;
	static int Ctop = 0;
	
	// ���ڿ� ��ȯ ������ ������ �迭
	static String charset[];
	
	// ���ڿ� �Է� �޼ҵ�
	public static void Input() {
		xarray = new char[n];
		yarray = new char[m];
		
		// ���ڿ� x, y �Է�
		System.out.print("���ڿ� x, y �Է� : ");
		
		// String���� �Է� �޾Ƽ� char �迭�� �ѱ��ھ� ����
		Scanner scan = new Scanner(System.in);
		String first;
		String second;
		first = scan.next();
		second = scan.next();
		
		// �Է¹��� ���ڿ� �迭�� ����
		x = new char[first.length()];
		y = new char[second.length()];
		n = x.length;
		m = y.length;
		System.out.print("�Է¹��� ���ڿ� x�� : ");
		for(int i=0;i<n;i++){ 
			x[i]=(first.charAt(i));
			System.out.print(x[i]);
		}
		System.out.println("�̰�, ���� n��  "+n + "�Դϴ�.");
		System.out.print("�Է¹��� ���ڿ� y�� : ");
		for(int i=0;i<m;i++){ 
			y[i]=(second.charAt(i));
			System.out.print(y[i]);
		}
		System.out.println("�̰�, ���� m��  "+m + "�Դϴ�.");
		xarray=x;
		yarray=y;
		
	}
	
	// ����� ���ڿ� ���̺� ��� �޼ҵ�
	public static void CharTable(){
	
		// ������ x���ڿ��� y ���ڿ��� ���¸� �����ִ� �迭 array
		char yyarray[]= new char[m];
		// ����� ���ڿ� ���� ���̺�
		charTable = new String[n+1][m+1];
		
		// �����Ǵ� x�� ���ڿ� �迭�� string���� ����
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
	
	// cost ��� �޼ҵ� 
	public static int Cost(char[] s, char[] t){
		
		CostTable = new int [n+1][m+1];
        for (int i = 1; i <= n; i++) {
        	CostTable[i][0] = i*2; // ���� ��� 2    	
        }

        for (int j = 1; j <= m; j++) {
        	CostTable[0][j] = j*3; // ���� ��� 3 
        }
        
		// cost ���
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
	
	 // Table ��� �޼ҵ�
	public static void CostTableOutput() {
		 for (int i = 0; i <= n; i++) {
		        for (int j = 0; j <=  m; j++) {
		            System.out.print(CostTable[i][j] + " ");
		        }
		        System.out.println("");
		 }
	}

    // �������� ������ stack ����   
	public static void StackPush(char item) {
        stack[top] = item;
        top ++;
        size++;
    }
	
	// �������� ��Ÿ�� stack ����
	public static void StackPop() {
        System.out.print(stack[top]);
        stack[top--] = 0;
    }
	
	
	
	// ������ 
	// ���ڿ� x�� y�� �ε����� �ڿ������� �ٿ����� stack�� �����ߴ� ����ϴ� �Լ��� �ۼ��߽��ϴ�
	public static void getTrace() {
		int a = n; 
		int b = m;
		charset = new String [99];
		
		
		// x,y ���ڿ� �Ѵ� ���������
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
		// ���ڿ� y�� �������� ���������� ����
		if(a == 0 && b != 0) { 
			while (b>=0) {
				StackPush('I');
				charset[Ctop] = charTable[a][b];
				Ctop++;
				b--;
			}
		}
		// ���ڿ� x�� �������� ���������� ����
		else if(b == 0 && a != 0) { 
			while (a>=0) {
				StackPush('D');
				charset[Ctop] = charTable[a][b];
				a--;
			}
		}
		// stack ������ ���� ���
		for(int i=0 ; i<=size;i++)
		StackPop();
		System.out.println("");
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		// �Լ� �� stack ������ ���� ���ڿ� ���� ���� ���
		System.out.println("�Է� ���ڿ��� ���ʷ� ����");		
		
		while(Ctop-1 != 0) {
		System.out.print(charset[Ctop-1]);
		charset[Ctop--] = null;
		System.out.println("");
		}
		
	}
	
	
	
	// main �޼ҵ�
	public static void main(String[] args) {
		Input(); // ���ڿ� x, y �Է�
		
		// ���ڿ� ���� ���̺�
		CharTable();
		
		int resultC = Cost(x, y); // �ּ������Ÿ� ���
		
		System.out.println("");
		System.out.println("cost(i,j) ǥ�� ������ �����ϴ�."); 
		
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		// cost(i,j) ���̺� ���
		CostTableOutput(); 
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		
		System.out.println("");
		// �ּ� ���� ���
		System.out.println("�ּ� ���� �����  " + resultC + "�Դϴ�.");
		
		for (int i= 0; i<m*3;i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		
		// ���� ���� ������
		System.out.print("������ ���� ������ : ");
		getTrace();
		System.out.println("");
		
	 }
	
}
