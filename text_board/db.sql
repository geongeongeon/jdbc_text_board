#id: geonhee / pw: gh0204

#데이터 베이스 삭제 및 생성
DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

#게시물 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title CHAR(100) NOT NULL,
	`body` TEXT NOT NULL
);