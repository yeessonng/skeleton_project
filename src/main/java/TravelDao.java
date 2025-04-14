import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {
    public List<TravelVO> findByDistrict(String district) throws SQLException {
        List<TravelVO> list = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/travel_db?useSSL=false&characterEncoding=UTF-8";
        String user = "root";
        String password = "!12345";

        String sql = "SELECT * FROM travel WHERE district LIKE ?";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + district + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TravelVO vo = new TravelVO();
                vo.setDistrict(rs.getString("district"));
                vo.setTitle(rs.getString("title"));
                vo.setDescription(rs.getString("description"));
                vo.setAddress(rs.getString("address"));
                vo.setPhone(rs.getString("phone"));
                list.add(vo);
            }
        }

        return list;
    }
}
