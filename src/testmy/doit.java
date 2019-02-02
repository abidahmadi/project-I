package testmy;

public class doit {

	public static int doit( int arr[], int value ){  
	     int i = 0 , count = 0 , len = arr.length;
	     while( i < len ){
	       if( arr[i] == value )
	         count += 1;
	         i++;
	     } 
	     return count;
	  }
	  

	public static void main(String[] args) {
	
		int []arr={2,3,4,2,3,3,4,3};
		int a = 3;
	int re=  doit( arr, a);
System.out.println(re);
	}

}
