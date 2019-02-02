import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String curDate = dateFormat.format(new Date());
		System.out.println(curDate);
		String dt = "19:17:00";
		Date dg =null;
		Date dg1 = null;
		try {
			 dg = dateFormat.parse(dt);
			 dg1 = dateFormat.parse(curDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diff = dg1.getTime() - dg.getTime();
		
		System.out.println(diff/60000);
		
	}

}
