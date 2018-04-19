package com.ictcg.trello;

import org.trello4j.Trello;
import org.trello4j.TrelloImpl;
import org.trello4j.model.Board;
import org.trello4j.model.Card;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Application {

    private String trelloKey = "";
    private String trelloAccessToken = "";

    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.readProperties();
        app.connectionTreloJTest();
    }

    private void readProperties() {
        try {
            Properties prop = new Properties();
            InputStream in = getClass().getResourceAsStream("/app.properties");
            prop.load(in);
            trelloKey = prop.getProperty("trello.key");
            trelloAccessToken = prop.getProperty("trello.access.token");
            in.close();
        } catch (IOException e) {
            System.out.println("Error reading 'app.properties'");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void connectionTreloJTest() {

        try {
            Trello trello = new TrelloImpl(trelloKey, trelloAccessToken);
            List<Board> boards = trello.getAllBoards();
            System.out.println(" ---------------- Boards list:");
            boards.forEach(board -> {
                System.out.println(board);
            });

            String boardId = boards.get(0).getId();
            List<Card> cards = trello.getCardsByBoard(boardId);
            System.out.println(String.format(" ---------------- Cards list for Board '%s':", boards.get(0).getName()));
            cards.forEach(card -> {
                System.out.println(card);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
