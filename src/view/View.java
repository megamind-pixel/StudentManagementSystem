package view;

import controller.Controller;
import model.Student;

import javax.swing.*;
import java.awt.*;

public class View {
    private Controller controller;
    private JFrame frame;
    private JTextArea outputArea;

    public View(Controller controller) {
        this.controller = controller;
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        Color schoolBlue = new Color(173, 216, 230);
        Color lightGreen = new Color(204, 255, 204);
        Font labelFont = new Font("Serif", Font.BOLD, 15);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout(15, 15));
        frame.setContentPane(mainPanel);

        // Title
        JLabel titleLabel = new JLabel("📚 Student Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(schoolBlue);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(fieldFont);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Console Output"));
        mainPanel.add(outputScroll, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(lightGreen);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student & Course Details"));
        mainPanel.add(formPanel, BorderLayout.WEST);

        // Fields
        JTextField studentIdField = new JTextField();
        JTextField studentNameField = new JTextField();
        JTextField lecturerIdField = new JTextField();
        JTextField courseCodeField = new JTextField();
        JTextField searchStudentIdField = new JTextField();

        formPanel.add(createRow("Student ID:", studentIdField, labelFont, fieldFont));
        formPanel.add(createRow("Student Name:", studentNameField, labelFont, fieldFont));
        JButton addStudentButton = new JButton("➕ Add Student");
        formPanel.add(wrapButton(addStudentButton));

        formPanel.add(createRow("Lecturer ID:", lecturerIdField, labelFont, fieldFont));
        formPanel.add(createRow("Course Code:", courseCodeField, labelFont, fieldFont));
        JButton assignLecturerButton = new JButton("🎓 Assign Lecturer");
        formPanel.add(wrapButton(assignLecturerButton));

        formPanel.add(createRow("Search Student ID:", searchStudentIdField, labelFont, fieldFont));
        JButton searchStudentButton = new JButton("🔍 Search Student");
        formPanel.add(wrapButton(searchStudentButton));

        JButton saveExitButton = new JButton("💾 Save & Exit");
        saveExitButton.setBackground(new Color(255, 100, 100));
        saveExitButton.setForeground(Color.WHITE);
        formPanel.add(wrapButton(saveExitButton));

        // Button Actions
        addStudentButton.addActionListener(e -> {
            String id = studentIdField.getText();
            String name = studentNameField.getText();
            controller.addStudent(id, name);
            outputArea.append("✅ Added student: " + name + "\n");
            studentIdField.setText("");
            studentNameField.setText("");
        });

        assignLecturerButton.addActionListener(e -> {
            String lecturerId = lecturerIdField.getText();
            String courseCode = courseCodeField.getText();
            if (controller.assignLecturerToCourse(lecturerId, courseCode)) {
                outputArea.append("✅ Assigned lecturer " + lecturerId + " to course " + courseCode + "\n");
            } else {
                outputArea.append("❌ Assignment failed!\n");
            }
            lecturerIdField.setText("");
            courseCodeField.setText("");
        });

        searchStudentButton.addActionListener(e -> {
            String id = searchStudentIdField.getText();
            Student s = controller.findStudent(id);
            if (s != null) {
                outputArea.append("👨‍🎓 Student: " + s.getName() + "\n");
                outputArea.append("Programme: " + (s.getProgramme() != null ? s.getProgramme().getName() : "None") + "\n");
                outputArea.append("Courses and Scores:\n");
                for (var entry : s.getCourses().entrySet()) {
                    outputArea.append(" - " + entry.getKey().getName() + ": " + entry.getValue() + "\n");
                }
            } else {
                outputArea.append("❌ Student not found!\n");
            }
            searchStudentIdField.setText("");
        });

        saveExitButton.addActionListener(e -> {
            controller.saveData();
            outputArea.append("✅ Data saved. Exiting...\n");
            frame.dispose();
        });

        frame.setVisible(true);
    }

    // Utility method for creating form rows
    private JPanel createRow(String labelText, JTextField field, Font labelFont, Font fieldFont) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        field.setPreferredSize(new Dimension(180, 25));
        field.setFont(fieldFont);
        panel.add(label);
        panel.add(field);
        panel.setOpaque(false);
        return panel;
    }

    // Utility method to center and pad buttons
    private JPanel wrapButton(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        button.setPreferredSize(new Dimension(180, 30));
        button.setFocusPainted(false);
        button.setOpaque(true);
        panel.add(button);
        panel.setOpaque(false);
        return panel;
    }

    public void start() {
        // GUI already initialized
    }
}
