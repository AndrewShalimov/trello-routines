package com.ictcg.trello;

import org.trello4j.Trello;
import org.trello4j.TrelloException;
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
        app.readCustomFields();
        app.writeCustomFields();
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
        System.out.println();
    }

    private void readCustomFields() {
        System.out.println("----------------- get CustomFields --------------------");
        try {
            Trello trello = new TrelloImpl(trelloKey, trelloAccessToken);
            String cardId = "5ad8c7a5c7a22d61b9051b4c";

            String customFieldId = "5ad8cf9f5487f51cb4d42d27"; //text
            String value = trello.getCustomFieldValue(cardId, customFieldId);
            System.out.println(value);


            customFieldId = "5aa6aec84a557d2deb12b243"; // list
            value = trello.getCustomFieldValue(cardId, customFieldId);
            System.out.println(value);


            customFieldId = "5aa6af1c82fd37e4ef543bdd"; //date
            value = trello.getCustomFieldValue(cardId, customFieldId);
            System.out.println(value);


            customFieldId = "5adc9c2163b2f22b8dd2cdbe"; //checkbox
            value = trello.getCustomFieldValue(cardId, customFieldId);
            System.out.println(value);

            customFieldId = "5adc9c2163b2f22b8dd2cdbe"; //checkbox
            value = trello.getCustomFieldValue("incorrect_card_id", customFieldId);
            System.out.println(value);
            customFieldId = "5edc9c2163b2f22b8dd2cdbe"; //non-existent field
            value = trello.getCustomFieldValue(cardId, customFieldId);
            System.out.println(value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private void writeCustomFields() {
        System.out.println("----------------- set CustomFields --------------------");
        Trello trello = new TrelloImpl(trelloKey, trelloAccessToken);
        String idCard = "5ad8c7a5c7a22d61b9051b4c";

        // Field type is 'text'
        try {
            Object response = trello.setCustomFieldValue(idCard, "5ad8cf9f5487f51cb4d42d27", "TEST_CF");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println(e.getMessage());
        }

        // Field type is 'date'
        try {
            Object response = trello.setCustomFieldValue(idCard, "5aa6af1c82fd37e4ef543bdd", "2017-04-04T09:00:00.000Z");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println(e.getMessage());
        }


        // Field type is 'date' - invalid
        try {
            Object response = trello.setCustomFieldValue(idCard, "5aa6af1c82fd37e4ef543bdd", "2017ddd-04-04T09:00:00.000Z");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println("invalid date: " + e.getMessage());
        }


        // Field type is 'checkbox'
        try {
            Object response = trello.setCustomFieldValue(idCard, "5adc9c2163b2f22b8dd2cdbe", "true");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println(e.getMessage());
        }

        // Field type is 'checkbox' - invalid
        try {
            Object response = trello.setCustomFieldValue(idCard, "5adc9c2163b2f22b8dd2cdbe", "truefle");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println("invalid checkbox: " + e.getMessage());
        }

        // Field type is 'number'
        try {
            Object response = trello.setCustomFieldValue(idCard, "5adc9c956ba7d75663011206", "321");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println(e.getMessage());
        }


        // Field type is 'number' - invalid
        try {
            Object response = trello.setCustomFieldValue(idCard, "5adc9c956ba7d75663011206", "321aa");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println("invalid number: " + e.getMessage());
        }

        // Field type is 'list'
        try {
            Object response = trello.setCustomFieldValue(idCard, "5aa6aec84a557d2deb12b243", "E2");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println(e.getMessage());
        }

        // Field type is 'list' - non exist value for tis CustomField list
        try {
            Object response = trello.setCustomFieldValue(idCard, "5aa6aec84a557d2deb12b243", "E8");
            System.out.println(response.toString());
        } catch (TrelloException e) {
            System.out.println("invalid list value: " + e.getMessage());
        }
    }

}
