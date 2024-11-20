        package phaseone;



        import java.awt.*;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Random;
import javax.swing.*;




        public class UserLogin {
            static ArrayList<User> users = new ArrayList<>();
            static ArrayList<Invitation> invitations = new ArrayList<>();
            static int currentUserIndex = -1;
            static CardLayout cardLayout = new CardLayout();
            static JPanel mainPanel = new JPanel(cardLayout);
            static Map<String, Article> articles = new HashMap<>();
            static List<Feedback> specificFeedbackList = new ArrayList<>();
            
// Method to filter articles by their level
private static Map<String, Article> filterArticlesByLevel(String level) {
    Map<String, Article> filteredArticles = new HashMap<>();
    for (Map.Entry<String, Article> entry : articles.entrySet()) {
        // Include articles that match the level or "All" for all articles
        if (entry.getValue().getLevel().equalsIgnoreCase(level) || level.equalsIgnoreCase("All")) {
            filteredArticles.put(entry.getKey(), entry.getValue());
        }
    }
    return filteredArticles;
}

            

            public static void main(String[] args) {
                
                // Main Frame setup
                JFrame frame = new JFrame("User Management System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 500);
                frame.setLocationRelativeTo(null);

                // Panels
                JPanel registrationPanel = createRegistrationPanel();
                JPanel loginPanel = createLoginPanel();
                JPanel finishAccountPanel = createFinishAccountPanel();
                JPanel adminPanel = createAdminPanel();

                // Add panels to main panel
                mainPanel.add(registrationPanel, "Registration");
                mainPanel.add(loginPanel, "Login");
                mainPanel.add(finishAccountPanel, "FinishAccount");
                mainPanel.add(adminPanel, "Admin");
                mainPanel.add(createFinishAccountPanel(), "FinishAccount");
                mainPanel.add(createAdminPanel(), "Admin");
                mainPanel.add(createInstructorPanel(), "Instructor");
        mainPanel.add(createStudentPanel(), "Student");




                // Show Registration panel initially
                cardLayout.show(mainPanel, "Registration");

                // Add main panel to frame
                frame.add(mainPanel);
                frame.setVisible(true);
            }

            public static class Feedback {
                private int userId; // ID of the user giving the feedback
                private String feedbackText; // Feedback content
            
                public Feedback(int userId, String feedbackText) {
                    this.userId = userId;
                    this.feedbackText = feedbackText;
                }
            
                public int getUserId() {
                    return userId;
                }
            
                public String getFeedbackText() {
                    return feedbackText;
                }
            
                @Override
                public String toString() {
                    return "User ID: " + userId + ", Feedback: " + feedbackText;
                }
            }
            
            
            
            static class Article {
                private String id;
                private String title;
                private String content;
                private String createdBy;
                private boolean isPublic;
                private String level; // Content level (e.g., Beginner, Intermediate)
            
                public Article(String id, String title, String content, String createdBy, boolean isPublic, String level) {
                    this.id = id;
                    this.title = title;
                    this.content = content;
                    this.createdBy = createdBy;
                    this.isPublic = isPublic;
                    this.level = level;
                }
                
                
                
                public String getId() {
                    return id;
                }
            
                public String getTitle() {
                    return title;
                }
            
                public String getContent() {
                    return content;
                }
            
                public String getCreatedBy() {
                    return createdBy;
                }

            
            
                public boolean isPublic() {
                    return isPublic;
                }
            
                public String getLevel() {
                    return level;
                }
            

        public void setLevel(String level) {
            this.level = level;
        }
                @Override
                public String toString() {
                    return id + " - " + title + " (Level: " + level + ")";
                }
            }
            
            
            
            private static void resetFinishAccountFields(JTextField... fields) {
                for (JTextField field : fields) {
                    field.setText(""); // Clear the text in each field
                }
            }
            

            // 1. Create Registration Panel for Admin
            private static JPanel createRegistrationPanel() {
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();


                gbc.insets = new Insets(10, 10, 10, 10); // Padding

                JLabel userLabel = new JLabel("Username:");
                JTextField userField = new JTextField(20);
                JLabel passLabel = new JLabel("Password:");
                JPasswordField passField = new JPasswordField(20);
                JLabel confirmPassLabel = new JLabel("Confirm Password:");
                JPasswordField confirmPassField = new JPasswordField(20);
                JButton submitButton = new JButton("Register");

                gbc.gridx = 0;
                gbc.gridy = 0;


                gbc.anchor = GridBagConstraints.EAST;
                panel.add(userLabel, gbc);
                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(userField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.EAST;
                panel.add(passLabel, gbc);
                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(passField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.EAST;
                panel.add(confirmPassLabel, gbc);
                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(confirmPassField, gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.CENTER;
                panel.add(submitButton, gbc);

                submitButton.addActionListener(e -> {


                    String username = userField.getText().trim();
                    String password = new String(passField.getPassword()).trim();
                    String confirmPassword = new String(confirmPassField.getPassword()).trim();

                    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(panel, "Passwords do not match.", "Password Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }



                    if (users.isEmpty()) {
                        User admin = new User(username, password, "Admin");
                        users.add(admin);
                        JOptionPane.showMessageDialog(panel, "Admin account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(mainPanel, "Login");
                    } else {
                        JOptionPane.showMessageDialog(panel, "An admin already exists. Please log in.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                return panel;
            }


            // 6. Student Panel for Student Actions
        private static JPanel createStudentPanel() {
            JPanel panel = new JPanel(new GridLayout(4, 1));

            JButton searchArticlesButton = new JButton("Search Articles");
            JButton viewArticleButton = new JButton("View Article");
            JButton sendFeedbackButton = new JButton("Send Feedback");
            JButton logoutButton = new JButton("Logout");


            panel.add(searchArticlesButton); //rth
            panel.add(viewArticleButton);
            panel.add(sendFeedbackButton);
            panel.add(logoutButton);
            
            
            // Action listeners for Student functionalities
            searchArticlesButton.addActionListener(e -> {
                // Prompt the user to select a search type
                String[] searchOptions = {"Search by Article ID", "Search by Keyword in Title", "Search by Author", "Search by Content Level"};
                String searchType = (String) JOptionPane.showInputDialog(
                        panel,
                        "Select search type:",
                        "Search Articles",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        searchOptions,
                        searchOptions[0]);
            
                if (searchType != null) {
                    String searchKeyword = null;
            
                    switch (searchType) {
                        case "Search by Article ID":
                        case "Search by Keyword in Title":
                            // Prompt user to input search keyword
                            searchKeyword = JOptionPane.showInputDialog(panel, "Enter search keyword:", "Search Articles", JOptionPane.PLAIN_MESSAGE);
                            break;
            
                        case "Search by Author":
                            // Dropdown menu for author
                            String[] authors = {"Instructor", "Admin"};
                            searchKeyword = (String) JOptionPane.showInputDialog(
                                    panel,
                                    "Select author:",
                                    "Search by Author",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    authors,
                                    authors[0]);
                            break;
            
                        case "Search by Content Level":
                            // Dropdown menu for content level
                            String[] levels = {"All", "Beginner", "Intermediate", "Advanced", "Expert"};
                            searchKeyword = (String) JOptionPane.showInputDialog(
                                    panel,
                                    "Select content level:",
                                    "Search by Content Level",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    levels,
                                    levels[0]);
                            break;
                    }
            
                    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                        StringBuilder results = new StringBuilder("Search Results:\n");
            
                        switch (searchType) {
                            case "Search by Article ID":
                                if (articles.containsKey(searchKeyword)) {
                                    Article article = articles.get(searchKeyword);
                                    results.append("ID: ").append(article.getId())
                                            .append("\nTitle: ").append(article.getTitle())
                                            .append("\nAuthor: ").append(article.getCreatedBy())
                                            .append("\nContent Level: ").append(article.getLevel())
                                            .append("\n\n");
                                } else {
                                    JOptionPane.showMessageDialog(panel, "No article found with the specified ID.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                break;
            
                            case "Search by Keyword in Title":
                                for (Article article : articles.values()) {
                                    if (article.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())) {
                                        results.append("ID: ").append(article.getId())
                                                .append("\nTitle: ").append(article.getTitle())
                                                .append("\nAuthor: ").append(article.getCreatedBy())
                                                .append("\nContent Level: ").append(article.getLevel())
                                                .append("\n\n");
                                    }
                                }
                                break;
            
                            case "Search by Author":
                                for (Article article : articles.values()) {
                                    if (article.getCreatedBy().equalsIgnoreCase(searchKeyword)) {
                                        results.append("ID: ").append(article.getId())
                                                .append("\nTitle: ").append(article.getTitle())
                                                .append("\nAuthor: ").append(article.getCreatedBy())
                                                .append("\nContent Level: ").append(article.getLevel())
                                                .append("\n\n");
                                    }
                                }
                                break;
            
                            case "Search by Content Level":
                                for (Article article : articles.values()) {
                                    if (searchKeyword.equals("All") || article.getLevel().equalsIgnoreCase(searchKeyword)) {
                                        results.append("ID: ").append(article.getId())
                                                .append("\nTitle: ").append(article.getTitle())
                                                .append("\nAuthor: ").append(article.getCreatedBy())
                                                .append("\nContent Level: ").append(article.getLevel())
                                                .append("\n\n");
                                    }
                                }
                                break;
            
                            default:
                                JOptionPane.showMessageDialog(panel, "Invalid search type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                        }
            
                        // Display results
                        JOptionPane.showMessageDialog(panel, results.length() > 0 ? results.toString() : "No articles found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Search keyword cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            viewArticleButton.addActionListener(e -> {
                StringBuilder articleList = new StringBuilder("Public Articles:\n");
                for (Map.Entry<String, Article> entry : articles.entrySet()) {
                    Article article = entry.getValue();
                    if (article.isPublic()) {
                        articleList.append(article.getId())
                                .append(" - ")
                                .append(article.getTitle())
                                .append(" (Level: ").append(article.getLevel()).append(")")
                                .append("\n");
                    }
                }
            
                String articleId = JOptionPane.showInputDialog(panel, articleList + "\nEnter Article ID to View or Modify:", "View Article", JOptionPane.PLAIN_MESSAGE);
                if (articleId != null && !articleId.trim().isEmpty() && articles.containsKey(articleId)) {
                    Article article = articles.get(articleId);
                    if (article.isPublic()) {
                        // Display article details
                        JOptionPane.showMessageDialog(panel, "ID: " + article.getId() +
                                "\nTitle: " + article.getTitle() +
                                "\nContent: " + article.getContent() +
                                "\nCreated By: " + article.getCreatedBy() +
                                "\nLevel: " + article.getLevel(), "Article Details", JOptionPane.INFORMATION_MESSAGE);
            
                        // Ask student if they want to change the content level
                        int confirm = JOptionPane.showConfirmDialog(panel, "Do you want to change the content level of this article?", "Change Content Level", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Allow student to select a new level
                            String[] levels = {"All", "Beginner", "Intermediate", "Advanced", "Expert"};
                            String newLevel = (String) JOptionPane.showInputDialog(panel,
                                    "Select a new content level for the article:",
                                    "Change Content Level",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    levels,
                                    article.getLevel()); // Default is current level
            
                            if (newLevel != null) {
                                article.setLevel(newLevel); // Update the article's level
                                JOptionPane.showMessageDialog(panel, "Content level updated to " + newLevel + " successfully!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(panel, "Content level update canceled.", "Update Canceled", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "You do not have permission to view this article.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid or non-existing Article ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            

            
            sendFeedbackButton.addActionListener(e -> {
                String[] options = {"Generic Feedback", "Specific Question"};
                int choice = JOptionPane.showOptionDialog(panel, 
                        "Would you like to provide generic feedback or ask a specific question?",
                        "Feedback Type", 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, options, options[0]);
            
                if (choice == 0) { // Generic Feedback
                    String genericFeedback = "Buttons in the student panel:\n"
                            + "1. Search Articles: Search available articles.\n"
                            + "2. View Article: View details of a specific article.\n"
                            + "3. Send Feedback: Send a question or feedback.\n"
                            + "4. Logout: Log out of your account.";
                    JOptionPane.showMessageDialog(panel, genericFeedback, "Generic Feedback", JOptionPane.INFORMATION_MESSAGE);
            
                } else if (choice == 1) { // Specific Question
                    String specificFeedback = JOptionPane.showInputDialog(panel, 
                            "Enter your specific question or feedback for the instructor/admin:", 
                            "Send Feedback", JOptionPane.PLAIN_MESSAGE);
            
                    if (specificFeedback != null && !specificFeedback.trim().isEmpty()) {
                        specificFeedbackList.add(new UserLogin.Feedback(UserLogin.currentUserIndex, specificFeedback));
                        JOptionPane.showMessageDialog(panel,
                            "Thank you for your feedback! It has been sent to the instructor/admin.",
                            "Feedback Sent", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                            "Feedback cannot be empty.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

                return panel;
            };

        // 5. Instructor Panel for Instructor Actions
        
        private static JPanel createInstructorPanel() {
            JPanel panel = new JPanel(new GridLayout(5, 2)); // Updated GridLayout to add more buttons
        
            JButton searchArticlesButton = new JButton("Search Articles");
            JButton viewArticleButton = new JButton("View Article");
            JButton createArticleButton = new JButton("Create Article");
            JButton editArticleButton = new JButton("Edit Article");
            JButton deleteArticleButton = new JButton("Delete Article");
            JButton backupArticlesButton = new JButton("Backup Articles"); // New Button
            JButton restoreArticlesButton = new JButton("Restore Articles"); // New Button
            JButton logoutButton = new JButton("Logout");
            JButton viewFeedbackButton = new JButton("View Feedback");
            JButton viewArticleGroupButton = new JButton("View Article Group");
            JButton createArticleGroupButton = new JButton("Create Article Group");
            JButton editArticleGroupButton = new JButton("Edit Article Group");
            JButton deleteArticleGroupButton = new JButton("Delete Article Group");
            
            panel.add(viewFeedbackButton);
            panel.add(backupArticlesButton);
            panel.add(restoreArticlesButton);
            panel.add(searchArticlesButton);
            panel.add(viewArticleButton);
            panel.add(createArticleButton);
            panel.add(editArticleButton);
            panel.add(deleteArticleButton);
            panel.add(backupArticlesButton);
            panel.add(restoreArticlesButton);
            panel.add(logoutButton);
            panel.add(viewArticleGroupButton);
            panel.add(createArticleGroupButton);
            panel.add(editArticleGroupButton);
            panel.add(deleteArticleGroupButton);
        
            // Backup Articles ActionListener
            backupArticlesButton.addActionListener(e -> {
                try (FileOutputStream fos = new FileOutputStream("articles_backup.dat");
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(articles); // Serialize articles
                    JOptionPane.showMessageDialog(panel, "Backup successful!", "Backup Articles", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error during backup: " + ex.getMessage(), "Backup Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            restoreArticlesButton.addActionListener(e -> {
                try (FileInputStream fis = new FileInputStream("articles_backup.dat");
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    articles = (Map<String, Article>) ois.readObject(); // Deserialize articles
                    JOptionPane.showMessageDialog(panel, "Restore successful!", "Restore Articles", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error during restore: " + ex.getMessage(), "Restore Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
        
            // Action Listener for View Feedbac
            viewFeedbackButton.addActionListener(e -> {
                if (specificFeedbackList.isEmpty()) {
                    JOptionPane.showMessageDialog(panel,
                        "No feedback available.",
                        "View Feedback",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder feedbackDetails = new StringBuilder();
                    for (Feedback feedback : specificFeedbackList) {
                        // Fetch the username based on the user ID
                        String username = users.get(feedback.getUserId()).getUsername();
                        feedbackDetails.append("\nFeedback from User: ").append(username) // Append username
                                    .append("\nFeedback: ").append(feedback.getFeedbackText())
                                    .append("\n---------------------\n");
                    }
                    JOptionPane.showMessageDialog(panel,
                        feedbackDetails.toString(),
                        "Feedback Details",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });

            // Action listeners for Instructor functionalities
            searchArticlesButton.addActionListener(e -> {
                // Prompt the user to select a search type
                String[] searchOptions = {"Search by Article ID", "Search by Keyword in Title", "Search by Author", "Search by Content Level"};
                String searchType = (String) JOptionPane.showInputDialog(
                        panel,
                        "Select search type:",
                        "Search Articles",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        searchOptions,
                        searchOptions[0]);
            
                if (searchType != null) {
                    String searchKeyword = null;
            
                    switch (searchType) {
                        case "Search by Article ID":
                        case "Search by Keyword in Title":
                            // Prompt user to input search keyword
                            searchKeyword = JOptionPane.showInputDialog(panel, "Enter search keyword:", "Search Articles", JOptionPane.PLAIN_MESSAGE);
                            break;
            
                        case "Search by Author":
                            // Dropdown menu for author
                            String[] authors = {"Instructor", "Admin"};
                            searchKeyword = (String) JOptionPane.showInputDialog(
                                    panel,
                                    "Select author:",
                                    "Search by Author",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    authors,
                                    authors[0]);
                            break;
            
                        case "Search by Content Level":
                            // Dropdown menu for content level
                            String[] levels = {"All", "Beginner", "Intermediate", "Advanced", "Expert"};
                            searchKeyword = (String) JOptionPane.showInputDialog(
                                    panel,
                                    "Select content level:",
                                    "Search by Content Level",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    levels,
                                    levels[0]);
                            break;
                    }
            
                    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                        StringBuilder results = new StringBuilder("Search Results:\n");
            
                        switch (searchType) {
                            case "Search by Article ID":
                                if (articles.containsKey(searchKeyword)) {
                                    Article article = articles.get(searchKeyword);
                                    results.append("ID: ").append(article.getId())
                                            .append("\nTitle: ").append(article.getTitle())
                                            .append("\nAuthor: ").append(article.getCreatedBy())
                                            .append("\nContent Level: ").append(article.getLevel())
                                            .append("\n\n");
                                } else {
                                    JOptionPane.showMessageDialog(panel, "No article found with the specified ID.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                break;
            
                            case "Search by Keyword in Title":
                                for (Article article : articles.values()) {
                                    if (article.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())) {
                                        results.append("ID: ").append(article.getId())
                                                .append("\nTitle: ").append(article.getTitle())
                                                .append("\nAuthor: ").append(article.getCreatedBy())
                                                .append("\nContent Level: ").append(article.getLevel())
                                                .append("\n\n");
                                    }
                                }
                                break;
            
                            case "Search by Author":
                                for (Article article : articles.values()) {
                                    if (article.getCreatedBy().equalsIgnoreCase(searchKeyword)) {
                                        results.append("ID: ").append(article.getId())
                                                .append("\nTitle: ").append(article.getTitle())
                                                .append("\nAuthor: ").append(article.getCreatedBy())
                                                .append("\nContent Level: ").append(article.getLevel())
                                                .append("\n\n");
                                    }
                                }
                                break;
            
                            case "Search by Content Level":
                                for (Article article : articles.values()) {
                                    if (searchKeyword.equals("All") || article.getLevel().equalsIgnoreCase(searchKeyword)) {
                                        results.append("ID: ").append(article.getId())
                                                .append("\nTitle: ").append(article.getTitle())
                                                .append("\nAuthor: ").append(article.getCreatedBy())
                                                .append("\nContent Level: ").append(article.getLevel())
                                                .append("\n\n");
                                    }
                                }
                                break;
            
                            default:
                                JOptionPane.showMessageDialog(panel, "Invalid search type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                        }
            
                        // Display results
                        JOptionPane.showMessageDialog(panel, results.length() > 0 ? results.toString() : "No articles found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Search keyword cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            
            viewArticleButton.addActionListener(e -> {
                StringBuilder articleList = new StringBuilder("Available Articles:\n");
                for (Article article : articles.values()) {
                    articleList.append(article.toString()).append("\n");
                }
                String articleId = JOptionPane.showInputDialog(panel, articleList + "\nEnter Article ID to View:", "View Article", JOptionPane.PLAIN_MESSAGE);
                if (articleId != null && !articleId.trim().isEmpty() && articles.containsKey(articleId)) {
                    Article article = articles.get(articleId);
                    JOptionPane.showMessageDialog(panel, "ID: " + article.getId() +
                            "\nTitle: " + article.getTitle() +
                            "\nContent: " + article.getContent() +
                            "\nCreated By: " + article.getCreatedBy(), "Article Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid or non-existing Article ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            createArticleButton.addActionListener(e -> {
                String articleTitle = JOptionPane.showInputDialog(panel, "Enter Article Title:", "Create Article", JOptionPane.PLAIN_MESSAGE);
                if (articleTitle != null && !articleTitle.trim().isEmpty()) {
                    String articleContent = JOptionPane.showInputDialog(panel, "Enter Article Content:", "Create Article", JOptionPane.PLAIN_MESSAGE);
                    if (articleContent != null && !articleContent.trim().isEmpty()) {
                        boolean isPublic = JOptionPane.showConfirmDialog(panel, "Make this article public for students?", "Public Article", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                        
                        // Instructor creating the article
                        String createdBy = users.get(currentUserIndex).getRoles().get(0);
            
                        String articleId = "A" + (articles.size() + 1); // Generate unique ID
                        articles.put(articleId, new Article(articleId, articleTitle, articleContent, createdBy, isPublic, "All"));
            
                        JOptionPane.showMessageDialog(panel, "Article '" + articleTitle + "' created successfully with ID: " + articleId, "Create Article", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Article content cannot be empty.", "Create Article", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Article title cannot be empty.", "Create Article", JOptionPane.ERROR_MESSAGE);
                }
            });
            

            editArticleButton.addActionListener(e -> {
                String articleId = JOptionPane.showInputDialog(panel, "Enter Article ID to Edit:", "Edit Article", JOptionPane.PLAIN_MESSAGE);
                if (articleId != null && !articleId.trim().isEmpty() && articles.containsKey(articleId)) {
                    Article article = articles.get(articleId);
                    String newContent = JOptionPane.showInputDialog(panel, "Enter New Content for Article ID " + articleId + ":", "Edit Article", JOptionPane.PLAIN_MESSAGE);
                    if (newContent != null && !newContent.trim().isEmpty()) {
                        article.content = newContent; // Update content field
                        JOptionPane.showMessageDialog(panel, "Article ID '" + articleId + "' updated successfully!", "Edit Article", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, "New content cannot be empty.", "Edit Article", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid or non-existing Article ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            

            deleteArticleButton.addActionListener(e -> {
                String articleId = JOptionPane.showInputDialog(panel, "Enter Article ID to Delete:", "Delete Article", JOptionPane.PLAIN_MESSAGE);
                if (articleId != null && articles.containsKey(articleId)) {
                    int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete Article ID " + articleId + "?", "Delete Article", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        articles.remove(articleId);
                        JOptionPane.showMessageDialog(panel, "Article ID '" + articleId + "' deleted successfully!", "Delete Article", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid or non-existing Article ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

            return panel;
        }

            // 2. Admin Panel for Admin Actions (Invite Users, Reset Passwords, Delete Users, List Users)
            private static JPanel createAdminPanel() {
                JPanel panel = new JPanel(new GridLayout(7, 2));

                JButton inviteButton = new JButton("Invite User");
                JButton resetPasswordButton = new JButton("Reset Password");
                JButton deleteUserButton = new JButton("Delete User");
                JButton listUsersButton = new JButton("List Users");
                JButton createArticle = new JButton("Create Articles");
                JButton updateArticle = new JButton("Update Articles");
                JButton viewArticle = new JButton("View Articles");
                JButton deleteArticle = new JButton("Delete Articles");
                JButton listArticle = new JButton("List Articles");
                JButton logoutButton = new JButton("Logout");
                JButton viewFeedbackButton = new JButton("View Feedback");
                JButton backupArticlesButton = new JButton("Backup Articles");
                JButton restoreArticlesButton = new JButton("Restore Articles");
                
                panel.add(backupArticlesButton);
                panel.add(restoreArticlesButton);
                
                panel.add(viewFeedbackButton);
                panel.add(inviteButton);
                panel.add(resetPasswordButton);
                panel.add(deleteUserButton);
                panel.add(listUsersButton);
                panel.add(createArticle);
                panel.add(updateArticle);
                panel.add(viewArticle);
                panel.add(deleteArticle);
                panel.add(listArticle);
                panel.add(logoutButton);

                
                backupArticlesButton.addActionListener(e -> {
                    try (FileOutputStream fos = new FileOutputStream("articles_backup.dat");
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(articles); // Serialize articles
                        JOptionPane.showMessageDialog(panel, "Backup successful!", "Backup Articles", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "Error during backup: " + ex.getMessage(), "Backup Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                
                restoreArticlesButton.addActionListener(e -> {
                    try (FileInputStream fis = new FileInputStream("articles_backup.dat");
                         ObjectInputStream ois = new ObjectInputStream(fis)) {
                        articles = (Map<String, Article>) ois.readObject(); // Deserialize articles
                        JOptionPane.showMessageDialog(panel, "Restore successful!", "Restore Articles", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "Error during restore: " + ex.getMessage(), "Restore Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                
                

                viewFeedbackButton.addActionListener(e -> {
                    if (specificFeedbackList.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, 
                            "No specific feedback available.", 
                            "View Feedback", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        StringBuilder feedbackDetails = new StringBuilder();
                        for (Feedback feedback : specificFeedbackList) {
                            // Fetch the username based on the user ID
                            String username = users.get(feedback.getUserId()).getUsername();
                            feedbackDetails.append("\nFeedback from User: ").append(username) // Append username
                                        .append("\nFeedback: ").append(feedback.getFeedbackText())
                                        .append("\n---------------------\n");
                        }
                        JOptionPane.showMessageDialog(panel, 
                            feedbackDetails.toString(), 
                            "View Feedback", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                
                
                


                // Invite a user
                inviteButton.addActionListener(e -> {
                    String username = JOptionPane.showInputDialog("Enter username to invite:");
                    if (username == null || username.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Invalid username", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String role = JOptionPane.showInputDialog("Assign role (Student, Instructor):");
                    if (role == null || (!role.equalsIgnoreCase("Student") && !role.equalsIgnoreCase("Instructor"))) {
                        JOptionPane.showMessageDialog(panel, "Invalid role", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String inviteCode = generateInviteCode();
                    invitations.add(new Invitation(inviteCode, role));  // Store the invite code
                    JOptionPane.showMessageDialog(panel, "Invite code: " + inviteCode, "Invite Code", JOptionPane.INFORMATION_MESSAGE);
                });



                // Reset user password
                resetPasswordButton.addActionListener(e -> {
                    String username = JOptionPane.showInputDialog("Enter username to reset password:");
                    User user = findUserByUsername(username);
                    if (user == null) {
                        JOptionPane.showMessageDialog(panel, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String oneTimePassword = generateOneTimePassword();
                    user.setOneTimePassword(oneTimePassword);
                    JOptionPane.showMessageDialog(panel, "One-time password: " + oneTimePassword, "Password Reset", JOptionPane.INFORMATION_MESSAGE);
                });

                // Delete user
                deleteUserButton.addActionListener(e -> {
                    String username = JOptionPane.showInputDialog("Enter username to delete:");
                    User user = findUserByUsername(username);
                    if (user != null) {
                        int response = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            users.remove(user);
                            JOptionPane.showMessageDialog(panel, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });



                // List all users
                listUsersButton.addActionListener(e -> {
                    StringBuilder userList = new StringBuilder("Users:\n");
                    for (User user : users) {
                        userList.append(user.getUsername())
                                .append(" - Roles: ").append(user.getRoles())
                                .append("\n");
                    }
                    JOptionPane.showMessageDialog(panel, userList.toString(), "User List", JOptionPane.INFORMATION_MESSAGE);
                });
                // Add action listeners for article buttons

        // Add a map to store articles
        //Map<String, String> articles = new HashMap<>();

        // Add action listeners for article buttons
        createArticle.addActionListener(e -> {
            // Code to handle creating an article
            String articleTitle = JOptionPane.showInputDialog(panel, "Enter Article Title:", "Create Article", JOptionPane.PLAIN_MESSAGE);
            if (articleTitle != null && !articleTitle.trim().isEmpty()) {
                String articleContent = JOptionPane.showInputDialog(panel, "Enter Article Content:", "Create Article", JOptionPane.PLAIN_MESSAGE);
                if (articleContent != null && !articleContent.trim().isEmpty()) {
                    boolean isPublic = JOptionPane.showConfirmDialog(panel, "Make this article public for students?", "Public Article", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                    
                    // Get the role of the currently logged-in user
                    String createdBy = users.get(currentUserIndex).getRoles().get(0); // Assuming the role is stored as the first role

                    // Generate a unique article ID
                    String articleId = "A" + (articles.size() + 1);

                    // Create and store the article
                    String defaultLevel = "All"; // Set a default content level
                    Article newArticle = new Article(articleId, articleTitle, articleContent, createdBy, isPublic, defaultLevel);
                    articles.put(articleId, newArticle);
                                    articles.put(articleId, newArticle);

                    JOptionPane.showMessageDialog(panel, "Article '" + articleTitle + "' created successfully with ID: " + articleId, "Create Article", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Article content cannot be empty.", "Create Article", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Article title cannot be empty.", "Create Article", JOptionPane.ERROR_MESSAGE);
            }
        });



        updateArticle.addActionListener(e -> {
            // Code to handle updating an article
            String articleId = JOptionPane.showInputDialog(panel, "Enter Article ID to Update:", "Update Article", JOptionPane.PLAIN_MESSAGE);
            if (articleId != null && !articleId.trim().isEmpty()) {
                if (articles.containsKey(articleId)) {
                    Article article = articles.get(articleId); // Retrieve the existing Article
                    String newContent = JOptionPane.showInputDialog(panel, "Enter New Content for Article ID " + articleId + ":", "Update Article", JOptionPane.PLAIN_MESSAGE);
                    if (newContent != null && !newContent.trim().isEmpty()) {
                        article.content = newContent; // Update the content of the existing Article
                        JOptionPane.showMessageDialog(panel, "Article ID '" + articleId + "' updated successfully!", "Update Article", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, "New content cannot be empty.", "Update Article", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Article ID not found.", "Update Article", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Article ID cannot be empty.", "Update Article", JOptionPane.ERROR_MESSAGE);
            }
        });


        viewArticle.addActionListener(e -> {
            StringBuilder articleList = new StringBuilder("Available Articles:\n");
            for (Article article : articles.values()) {
                articleList.append(article.toString()).append("\n");
            }
            String articleId = JOptionPane.showInputDialog(panel, articleList + "\nEnter Article ID to View:", "View Article", JOptionPane.PLAIN_MESSAGE);
            if (articleId != null && !articleId.trim().isEmpty() && articles.containsKey(articleId)) {
                Article article = articles.get(articleId);
                JOptionPane.showMessageDialog(panel, "ID: " + article.getId() +
                        "\nTitle: " + article.getTitle() +
                        "\nContent: " + article.getContent() +
                        "\nCreated By: " + article.getCreatedBy(), "Article Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid or non-existing Article ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        deleteArticle.addActionListener(e -> {
            // Code to handle deleting an article
            String articleId = JOptionPane.showInputDialog(panel, "Enter Article ID to Delete:", "Delete Article", JOptionPane.PLAIN_MESSAGE);
            if (articleId != null && !articleId.trim().isEmpty()) {
                if (articles.containsKey(articleId)) {
                    int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete Article ID " + articleId + "?", "Delete Article", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        articles.remove(articleId);
                        JOptionPane.showMessageDialog(panel, "Article ID '" + articleId + "' deleted successfully!", "Delete Article", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Article ID not found.", "Delete Article", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Article ID cannot be empty.", "Delete Article", JOptionPane.ERROR_MESSAGE);
            }
        });
        listArticle.addActionListener(e -> {
            // Code to handle listing all articles
            if (articles.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "No articles available.", "List Articles", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder articleList = new StringBuilder("Available Articles:\n");
                for (Article article : articles.values()) {
                    articleList.append(article.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(panel, articleList.toString(), "List Articles", JOptionPane.INFORMATION_MESSAGE);
            }
        });


                // Logout
                logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

                return panel;
            }

            private static String generateInviteCode() {
                return Integer.toHexString(new Random().nextInt(0xFFFFFF)).toUpperCase();
            }

            static String generateOneTimePassword() {
                return Integer.toHexString(new Random().nextInt(0xFFFFFF)).toUpperCase();
            }

            private static User findUserByUsername(String username) {
                for (User user : users) {
                    if (user.getUsername().equalsIgnoreCase(username)) {
                        return user;
                    }
                }
                return null;
            }


        
            // 3. Create Login Panel for users
        private static JPanel createLoginPanel() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel userLabel = new JLabel("Username:");
            JTextField userField = new JTextField(20);
            JLabel passLabel = new JLabel("Password:");
            JPasswordField passField = new JPasswordField(20);
            JLabel inviteLabel = new JLabel("Invitation Code:");
            JTextField inviteField = new JTextField(20);
            JButton loginButton = new JButton("Login");
            JButton createAccountWithInviteButton = new JButton("Use Invitation Code");



            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(userLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(userField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(passLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(passField, gbc);

            
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(inviteLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(inviteField, gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(loginButton, gbc);
            gbc.gridy = 4;
            panel.add(createAccountWithInviteButton, gbc);

            
            loginButton.addActionListener(e -> {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                int userIndex = validateLogin(username, password);
            
                if (userIndex != -1) {
                    currentUserIndex = userIndex;
                    User currentUser = users.get(currentUserIndex);
            
                    if (!currentUser.isAccountSetupComplete()) {
                        // Redirect to Finish Account Setup Panel
                        cardLayout.show(mainPanel, "FinishAccount");
                    } else if (currentUser.getRoles().contains("Admin")) {
                        // If the user is an Admin, redirect to Admin Panel
                        cardLayout.show(mainPanel, "Admin");
                    } else if (currentUser.getRoles().contains("Instructor")) {
                        // If the user is an Instructor, redirect to Instructor Panel
                        cardLayout.show(mainPanel, "Instructor");
                    } else if (currentUser.getRoles().contains("Student")) {
                        // If the user is a Student, redirect to Student Panel
                        cardLayout.show(mainPanel, "Student");
                    } else {
                        JOptionPane.showMessageDialog(panel, "No valid role assigned to this user.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid login credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            });
            



            // Create Account with Invitation Code Button Action
            createAccountWithInviteButton.addActionListener(e -> {
                String inviteCode = inviteField.getText().trim();
                if (inviteCode.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter an invitation code.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Invitation invitation = validateInvitationCode(inviteCode);
                if (invitation != null) {
                    String username = JOptionPane.showInputDialog("Enter your new username:");
                    String password = JOptionPane.showInputDialog("Enter your new password:");
                    if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                        User newUser = new User(username, password, invitation.getRoles().get(0)); // Only using the first role here for simplicity
                        for (String role : invitation.getRoles()) {
                            newUser.addRole(role); // Add all roles associated with the invitation
                        }
                        users.add(newUser);
                        invitations.remove(invitation);  // Remove the used invitation code
                        JOptionPane.showMessageDialog(panel, "Account created successfully! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(mainPanel, "Login");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Invalid username or password.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid or expired invitation code.", "Invalid Code", JOptionPane.ERROR_MESSAGE);
                }
            });

            return panel;
        }



            // 4. Finish Account Setup Panel
        private static JPanel createFinishAccountPanel() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel emailLabel = new JLabel("Email:");
            JTextField emailField = new JTextField(20);
            
            JLabel firstNameLabel = new JLabel("First Name:");
            JTextField firstNameField = new JTextField(20);

            JLabel middleNameLabel = new JLabel("Middle Name:");
            JTextField middleNameField = new JTextField(20);

            JLabel lastNameLabel = new JLabel("Last Name:");
            JTextField lastNameField = new JTextField(20);

            JLabel preferredFirstNameLabel = new JLabel("Preferred First Name (Optional):");
            JTextField preferredFirstNameField = new JTextField(20);

            JButton finishButton = new JButton("Finish Setup");

            // Arrange components in GridBagLayout
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(emailLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(firstNameLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(firstNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(middleNameLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(middleNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(lastNameLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(lastNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(preferredFirstNameLabel, gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(preferredFirstNameField, gbc);

            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(finishButton, gbc);

            // Action listener for finishing account setup
            finishButton.addActionListener(e -> {
                String email = emailField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String middleName = middleNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String preferredFirstName = preferredFirstNameField.getText().trim();

                if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Email, First Name, and Last Name are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the current user's details
                User currentUser = users.get(currentUserIndex);
                currentUser.setEmail(email);
                currentUser.setFirstName(firstName);
                currentUser.setMiddleName(middleName);
                currentUser.setLastName(lastName);
                currentUser.setPreferredFirstName(preferredFirstName.isEmpty() ? firstName : preferredFirstName);
                currentUser.completeAccountSetup();

                JOptionPane.showMessageDialog(panel, "Account setup completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "Login");
            });

            return panel;
        }

            private static int validateLogin(String username, String password) {
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                        return i;  // Return the index of the user
                    }
                }
                return -1;  // Login failed
            }

            private static Invitation validateInvitationCode(String code) {
                for (Invitation invitation : invitations) {
                    if (invitation.getCode().equalsIgnoreCase(code)) {
                        return invitation;
                    }
                }
                return null; // Code not found  expired
            }

        

        }
