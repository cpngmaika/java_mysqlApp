package murach.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("userPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }

    public static void closeStatement(Statement s){
        try{
            if(s != null){
                s.close();
            }
        }catch(SQLException e) {
            System.out.println(e);
        }
    }


    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
