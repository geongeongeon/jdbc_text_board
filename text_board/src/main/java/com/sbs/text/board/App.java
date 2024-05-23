package com.sbs.text.board;

import com.sbs.text.board.container.Container;

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

            if(cmd.equals(("user/article/write"))) {
                System.out.println("== 게시물 작성 ==");

                System.out.print("제목) ");
                String title = sc.nextLine();

                System.out.print("내용) ");
                String body = sc.nextLine();

                int id = ++articleLsetId;

                Article article = new Article(id, title, body);
                System.out.println("article: " + article);
                articles.add(article);

                System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
            } else if(cmd.equals("user/article/list")) {
                System.out.println("== 게시물 리스트 ==");

                if(articles.isEmpty()) {
                    System.out.println("게시물이 존재하지 않습니다.");
                    continue;
                }

                for(int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    System.out.printf("%d | %s\n", article.id, article.title);
                }

                //향상된 for문 (역순 안됨)
                /*
                for(Article article : articles) {
                    System.out.printf("%d | %s\n", article.id, article.title);
                }
                 */
            } else if(cmd.equals("exit")) {
                System.out.println("반복문을 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 명령어를 입력했습니다.");
            }
        }
        sc.close();
    }
}
