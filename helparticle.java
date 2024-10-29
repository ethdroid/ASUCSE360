/*
 


    import java.io.*;
    import java.util.ArrayList;
    import java.util.Scanner;



    class HelpArticle implements Serializable {
        private char[] title;
        private char[] author;

        private char[] abstractText;
        private char[] keywords;
        private char[] body;
        private char[] references;

        // Constructor
        public HelpArticle(String title, String author, String abstractText, String keywords, String body, String references) {
            this.title = encrypt(title).toCharArray();
            this.author = encrypt(author).toCharArray();
            this.abstractText = encrypt(abstractText).toCharArray();
            this.keywords = encrypt(keywords).toCharArray();
            this.body = encrypt(body).toCharArray();
            this.references = encrypt(references).toCharArray();

        }

        // Simulate encryption using Caesar Cipher (shifting by 7)
        private String encrypt(String text) {
            StringBuilder encrypted = new StringBuilder();
            for (char c : text.toCharArray()) {
                encrypted.append((char) (c + 7)); // Shift character by 7
            }
            return encrypted.toString();
        }

        // Simulate decryption using Caesar Cipher (shifting by 7 back)
        private String decrypt(char[] text) {
            StringBuilder decrypted = new StringBuilder();
            for (char c : text) {

                decrypted.append((char) (c - 7)); // Shift character back by 7

            }
            return decrypted.toString();
        }

        // Decrypt and display article
        public void decryptAndDisplay() {
            System.out.println("Title: " + decrypt(title));
            System.out.println("Author: " + decrypt(author));
            System.out.println("Abstract: " + decrypt(abstractText));
            System.out.println("Keywords: " + decrypt(keywords));
            System.out.println("Body: " + decrypt(body));
            System.out.println("References: " + decrypt(references));
        }

        // Clear the contents of the article (simulating setting to blanks)
        public void clear() {


            for (int i = 0; i < title.length; i++) title[i] = ' ';
            for (int i = 0; i < author.length; i++) author[i] = ' ';
            for (int i = 0; i < abstractText.length; i++) abstractText[i] = ' ';
            for (int i = 0; i < keywords.length; i++) keywords[i] = ' ';
            for (int i = 0; i < body.length; i++) body[i] = ' ';
            for (int i = 0; i < references.length; i++) references[i] = ' ';
        }

        // Getters for listing purposes
        public String getTitle() {
            return decrypt(title).trim();
        }

        public String getAuthor() {

            return decrypt(author).trim();
        }
    }



    public class HelpArticleManager {

        private ArrayList<HelpArticle> articles = new ArrayList<>();

        // Save articles to file
        public void saveArticles(String filename) throws IOException {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(articles);
            }


        }

        // Load articles from file
        public void loadArticles(String filename) throws IOException, ClassNotFoundException {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                articles = (ArrayList<HelpArticle>) in.readObject();
            }

        }

        // Create a new article
        public void createArticle(String title, String author, String abstractText, String keywords, String body, String references) {
            HelpArticle article = new HelpArticle(title, author, abstractText, keywords, body, references);
            articles.add(article);
        }

        // Delete an article
        
    public void deleteArticle(int index) {
        if (index >= 0 && index < articles.size()) {
            articles.get(index).clear();  // Clear the article content
            articles.remove(index);       // Remove it from the list
            System.out.println("Article deleted successfully.");
        } else {

            System.out.println("Invalid article number.");
        }
    }




        // List all articles
    public void listArticles() {
        if (articles.isEmpty()) {
            System.out.println("No articles available.");
            return;
        }
        
        for (int i = 0; i < articles.size(); i++) {
            System.out.println((i + 1) + ". Title: " + articles.get(i).getTitle() + ", Author: " + articles.get(i).getAuthor());
        }
    }


        // Display an article
        public void displayArticle(int index) {
            if (index >= 0 && index < articles.size()) {
                articles.get(index).decryptAndDisplay();
            } else {
                System.out.println("Invalid article number.");
            }
        }

        public static void main(String[] args) throws IOException, ClassNotFoundException {
            Scanner scanner = new Scanner(System.in);
            HelpArticleManager manager = new HelpArticleManager();

            while (true) {
                System.out.println("Help Article Manager Menu:");
                System.out.println("1. Create Article");
                System.out.println("2. Delete Article");
                System.out.println("3. List Articles");
                System.out.println("4. Display Article");
                System.out.println("5. Save Articles");
                System.out.println("6. Load Articles");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter Abstract: ");
                        String abstractText = scanner.nextLine();
                        System.out.print("Enter Keywords: ");
                        String keywords = scanner.nextLine();
                        System.out.print("Enter Body: ");
                        String body = scanner.nextLine();
                        System.out.print("Enter References: ");
                        String references = scanner.nextLine();
                        manager.createArticle(title, author, abstractText, keywords, body, references);
                        break;
                    case 2:
                        manager.listArticles();
                        System.out.print("Enter article number to delete: ");
                        int deleteIndex = scanner.nextInt() - 1;
                        manager.deleteArticle(deleteIndex);
                        break;
                    case 3:
                        manager.listArticles();
                        break;
                    case 4:
                        manager.listArticles();
                        System.out.print("Enter article number to display: ");
                        int displayIndex = scanner.nextInt() - 1;
                        manager.displayArticle(displayIndex);
                        break;
                    case 5:
                        System.out.print("Enter filename to save: ");
                        String saveFile = scanner.nextLine();
                        manager.saveArticles(saveFile);
                        break;
                    case 6:
                        System.out.print("Enter filename to load: ");
                        String loadFile = scanner.nextLine();
                        manager.loadArticles(loadFile);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }


 */