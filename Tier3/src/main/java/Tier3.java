import RMI.Tier3RMIServerController;

/**
 * @author group9
 * @version 1.0
 */

public class Tier3
{
	/**
	 * Main
	 * @param args args
	 */
	public static void main( String[] args )
	{

		try {
			Tier3RMIServerController controller = new Tier3RMIServerController();
			
			System.out.println( "Tier3 ready" );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}
