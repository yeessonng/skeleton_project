import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TravelDao {
    private Connection conn;

    public TravelDao() {
        String url = "jdbc:mysql://localhost:3306/travel_db";
        String user = "root";
        String password = "98653232";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver" );
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println( "[에러] " + e.getMessage() );
        } catch (SQLException e) {
            System.out.println( "[에러] " + e.getMessage() );
        }
    }

    //

}
