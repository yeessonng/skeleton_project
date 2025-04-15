import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/travel_db";
        String user = "root";
        String password = "98653232";
        return DriverManager.getConnection(url, user, password);
    }

    public List<TravelVO> findByDistrict(String district) throws Exception {
        List<TravelVO> list = new ArrayList<>();

        String sql = "SELECT * FROM travel WHERE district LIKE ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

    public List<TravelVO> findByTitle(String keyword) throws Exception {
        List<TravelVO> list = new ArrayList<>();

        String sql = "SELECT * FROM travel WHERE title LIKE ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
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

    // 전체 조회
    public List<TravelVO> findBylist(int page) throws SQLException {
        List<TravelVO> list = new ArrayList<>();
        int pageSize = 10; // 한 페이지에 10개 보여줌
        int offset = (page - 1) * pageSize;

        String sql = "SELECT * FROM travel LIMIT ? OFFSET ?";

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TravelVO vo = new TravelVO();
                vo.setNo(rs.getInt("no"));
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