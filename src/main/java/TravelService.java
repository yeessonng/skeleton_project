import java.sql.SQLException;
import java.util.List;

public class TravelService {
    private TravelDao dao = new TravelDao();
    public static int pageCount = 1;

    public List<TravelVO> getTourListByDistrict(String region) throws Exception {
        return dao.findByDistrict(region);
    }

    public boolean showTourListByTitle(String title) throws Exception {
        List<TravelVO> list = dao.findByTitle(title);
        if (list.isEmpty()) {
            return false;
        }

        System.out.println("\n[" + title + "] 키워드로 검색한 관광지 목록");
        System.out.println("============================================================================");
        for (TravelVO vo : list) {
            printTravelInfo(vo);
        }
        return true;
    }

    private void printTravelInfo(TravelVO vo) {
        System.out.println("이름: " + vo.getTitle());
        System.out.println("지역: " + vo.getDistrict());
        System.out.println("주소: " + vo.getAddress());
        String description = vo.getDescription();
        int lineLength = 80;
        System.out.print("소개: ");
        for (int i = 0; i < description.length(); i += lineLength) {
            int end = Math.min(i + lineLength, description.length());
            String part = description.substring(i, end);
            if (i == 0) {
                System.out.println(part);  // 첫 줄은 소개:
            } else {
                System.out.println("     " + part);  // 이후 줄은 들여쓰기
            }
        }
        System.out.println("전화: " + vo.getPhone());
        System.out.println("----------------------------------------------------------------------------");
    }

    public void showPaging() throws SQLException {
        List<TravelVO> list = dao.findBylist(pageCount);
        System.out.println("\n관광지 목록");
        System.out.println("============================================================================");

        for (TravelVO vo : list) {
            System.out.println(" 번호: " + vo.getNo());
            System.out.println(" 이름: " + vo.getTitle());
            System.out.println(" 지역: " + vo.getDistrict());
            System.out.println(" 주소: " + vo.getAddress());
            // 정렬
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
        System.out.println("──────────────────────────────────────────────────────");

        System.out.println("                    ◀ [ " + pageCount + " / " + 12 + " ] ▶");

        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("이전 페이지는 ◀, 다음 페이지는 ▶, 이전 화면 exit");



    }
}