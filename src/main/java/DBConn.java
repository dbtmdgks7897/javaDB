import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConn {
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    static Connection conn = null;

    public static Connection dbConnect() {
        Connection conn = null;
        final String driver = "org.mariadb.jdbc.Driver"; //패키지
        final String DB_IP = "localhost"; //접속 아이피
        final String DB_PORT = "3306"; //포트
        final String DB_NAME = "database"; //DB 이름
        final String DB_URL = //DB 연결 방식
                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, "root", "1234");
            if (conn != null) {
                System.out.println("DB 접속 성공");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }
        return conn;
    }

    public static void register() {
//        final String driver = "org.mariadb.jdbc.Driver";
//        final String DB_IP = "localhost";
//        final String DB_PORT = "3306";
//        final String DB_NAME = "database";
//        final String DB_URL =
//                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
//
//        Scanner sc = new Scanner(System.in);
//        Connection conn = null;
//        PreparedStatement pstmt = null;
////        ResultSet rs = null;
//
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(DB_URL, "root", "1234");
//            if (conn != null) {
//                System.out.println("DB 접속 성공");
//            }
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("드라이버 로드 실패");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println("DB 접속 실패");
//            e.printStackTrace();
//        }
        Connection conn = dbConnect();
        Scanner sc = new Scanner(System.in);


        try {
            String sql = "INSERT INTO `database`.`game` (`userid`, `userpw`, `name`) VALUES (?, ?, ?);";
            pstmt = conn.prepareStatement(sql);

            System.out.print("아이디 : ");
            String userId = sc.next();
            System.out.print("패스워드 : ");
            String userPw = sc.next();
            System.out.print("이름 : ");
            String userName = sc.next();

            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);
            pstmt.setString(3, userName);

            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } //public static void save(String userId, String userPw, String userName)

    public static UserDto loginIdPw(String userId, String userPw) {
//        final String driver = "org.mariadb.jdbc.Driver";
//        final String DB_IP = "localhost";
//        final String DB_PORT = "3306";
//        final String DB_NAME = "database";
//        final String DB_URL =
//                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
//
//        Scanner sc = new Scanner(System.in);
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(DB_URL, "root", "1234");
//            if (conn != null) {
//                System.out.println("DB 접속 성공");
//            }
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("드라이버 로드 실패");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println("DB 접속 실패");
//            e.printStackTrace();
//        }
        Connection conn = dbConnect();
        UserDto udto = null;

        try {
            String sql = "select * from `game` where userid=? and userpw=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                udto = new UserDto();
                udto.setName(rs.getString("name"));
                udto.setMarble(rs.getInt("marble"));
                udto.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return udto;
    } // public static boolean getIdPw(String userId, String userPw)

    public static void setMarble(int id, int plusMarble) {
//        final String driver = "org.mariadb.jdbc.Driver";
//        final String DB_IP = "localhost";
//        final String DB_PORT = "3306";
//        final String DB_NAME = "database";
//        final String DB_URL =
//                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
//
//        Scanner sc = new Scanner(System.in);
//        Connection conn = null;
//        PreparedStatement pstmt = null;
////        ResultSet rs = null;
//
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(DB_URL, "root", "1234");
//            if (conn != null) {
//                System.out.println("DB 접속 성공");
//            }
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("드라이버 로드 실패");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println("DB 접속 실패");
//            e.printStackTrace();
//        }
        Connection conn = dbConnect();

        try {
            String sql = "UPDATE `database`.`game` SET `marble`=? WHERE  `id`=?;";
            pstmt = conn.prepareStatement(sql);

            if (plusMarble <= 0) {
                plusMarble += 10;
            }
            pstmt.setInt(1, plusMarble);
            pstmt.setInt(2, id);

            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // public static void saveMarble(String userId, int plusMarble)
}
//    public static int getMarble(String userId, String userPw) {
////        final String driver = "org.mariadb.jdbc.Driver";
////        final String DB_IP = "localhost";
////        final String DB_PORT = "3306";
////        final String DB_NAME = "database";
////        final String DB_URL =
////                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
////
////        Scanner sc = new Scanner(System.in);
////        Connection conn = null;
////        PreparedStatement pstmt = null;
////        ResultSet rs = null;
////
////        try {
////            Class.forName(driver);
////            conn = DriverManager.getConnection(DB_URL, "root", "1234");
////            if (conn != null) {
////                System.out.println("DB 접속 성공");
////            }
////
////        } catch (ClassNotFoundException e) {
////            System.out.println("드라이버 로드 실패");
////            e.printStackTrace();
////        } catch (SQLException e) {
////            System.out.println("DB 접속 실패");
////            e.printStackTrace();
////        }
//        Connection conn = dbConnect();
//        int marble = 10;
//
//        try {
//            String sql = "select * from `game` where userid = ? and userpw = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, userId);
//            pstmt.setString(2, userPw);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                marble = rs.getInt("marble");
//            }
//        } catch (SQLException e) {
//            System.out.println("error: " + e);
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//
//                if (conn != null && !conn.isClosed()) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return marble;
//    }
//}