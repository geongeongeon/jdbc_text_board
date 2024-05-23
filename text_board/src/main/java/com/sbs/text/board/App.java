package com.sbs.text.board;

import com.sbs.text.board.container.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public void run() {
        Scanner sc = Container.scanner;

        int articleLsetId = 0;
        List<Article> articles = new ArrayList<>();

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            if(cmd.equals(("/usr/article/write"))) {
                System.out.println("== 게시물 작성 ==");

                System.out.print("제목) ");
                String title = sc.nextLine();

                System.out.print("내용) ");
                String body = sc.nextLine();

                int id = ++articleLsetId;

                // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
                String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
                //root 계정을 사용할 경우, user = "root", pw = ""
                String user = "geonhee"; // 데이터베이스 사용자 이름
                String password = "gh0204"; // 데이터베이스 비밀번호

                // Connection 객체 선언
                Connection conn = null;
                PreparedStatement prst = null;

                try {
                    // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // 데이터베이스 연결
                    conn = DriverManager.getConnection(url, user, password);

                    // SQL 삽입 명령 준비
                    String sql = String.format(
                            "INSERT INTO article SET " +
                                    "regDate = NOW(), " +
                                    "updateDate = NOW(), " +
                                    "title = '%s', " +
                                    "`body` = '%s'",
                            title, body);


                    System.out.println("sql : " + sql);
                    prst = conn.prepareStatement(sql);

                    // 명령 실행
                    int affetedRows = prst.executeUpdate();
                    System.out.println("affetedRows : " + affetedRows);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("드라이버 로딩 실패!!");
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                    System.out.println("연결 실패!!");
                } finally {
                    // 리소스 해제
                    if (prst != null) {
                        try {
                            prst.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    // 연결 닫기
                    if (conn != null) {
                        try {
                            conn.close();
                            System.out.println("Connection closed.");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Article article = new Article(id, title, body);
                System.out.println("article: " + article);
                articles.add(article);

                System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
            } else if(cmd.equals("/usr/article/list")) {
                System.out.println("== 게시물 리스트 ==");

                if(articles.isEmpty()) {
                    System.out.println("게시물이 존재하지 않습니다.");
                    continue;
                }

                for(int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    System.out.printf("%d | %s\n", article.id, article.title);
                }
            } else if(cmd.equals("/exit")) {
                System.out.println("반복문을 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 명령어를 입력했습니다.");
            }
        }
        sc.close();
    }
}
