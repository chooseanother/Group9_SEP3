/*
 * 12.09.2018 Original version
 */


import RMI.Tier3RMIServerController;

public class Tier3
{
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
