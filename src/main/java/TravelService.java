import java.sql.SQLException;
import java.util.List;

public class TravelService {
    private TravelDao dao = new TravelDao();



    public void showTourListByDistrict(String district) throws SQLException {
        List<TravelVO> list = dao.findByDistrict(district);


        if (list.isEmpty()) {
            System.out.println("해당 지역의 관광지를 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n [" + district + "] 지역 관광지 목록");
        System.out.println("============================================================================");

        for (TravelVO vo : list) {
            System.out.println(" 이름: " + vo.getTitle());
            System.out.println(" 지역: " + vo.getDistrict());
            System.out.println(" 주소: " + vo.getAddress());
            // System.out.print(" 소개: ");
            // printMultiline(vo.getDescription(), 80); // 글자수 마다 줄바꿈
            // 정령
            String description = vo.getDescription();
            int lineLength = 80;  // 몇 글자마다 줄바꿈할지 (조절 가능)
            System.out.print(" 소개: ");
            for (int i = 0; i < description.length(); i += lineLength) {
                int end = Math.min(i + lineLength, description.length());
                String part = description.substring(i, end);
                if (i == 0) {
                    System.out.println(part);  // 첫 줄은 소개: 옆에 출력
                } else {
                    System.out.println("       " + part);  // 이후 줄은 들여쓰기
                }
            }
            System.out.println(" 전화: " + vo.getPhone());
            System.out.println("-----------------------------------------------------------------------");

        }
    }

    public void printMultiline(String text, int lineLength) {
        for (int i = 0; i < text.length(); i += lineLength) {
            int end = Math.min(i + lineLength, text.length());
            System.out.println(text.substring(i, end));
        }
    }
}