/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wia1002.grp.asgm.pkg3;

/**
 *
 * @author ASUS
 */
import java.io.*;
import java.util.*;

// Main class for FSKTM Student Enrollment System
public class FSKTMStudentEnrollment {
    // Student class to store student information
    static class Student {
        String matricID;
        String name;
        String street1;
        String street2;
        String city;
        String district;
        String state;
        String postcode;
        String programme;
        double cgpa;
        
        public Student(String matricID, String name, String street1, String street2, String city, 
                      String district, String state, String postcode, String programme, double cgpa) {
            this.matricID = matricID;
            this.name = name;
            this.street1 = street1;
            this.street2 = street2;
            this.city = city;
            this.district = district;
            this.state = state;
            this.postcode = postcode;
            this.programme = programme;
            this.cgpa = cgpa;
        }
        
        @Override
        public String toString() {
            return String.format("Matric ID: %s, Name: %s, Programme: %s, CGPA: %.2f", 
                                matricID, name, programme, cgpa);
        }
    }

    // BST Node class for Module 1
    static class BSTNode {
        Student student;
        BSTNode left, right;
        int size; // subtree size
        
        public BSTNode(Student student) {
            this.student = student;
            this.size = 1;
        }
    }

    // Module 1: Student records storage (Binary Search Tree implementation)
    static class StudentBST {
        BSTNode root;
        private Stack<String> history = new Stack<>(); // For undo functionality
        
        // Insert a student into BST
        public void insert(Student student) {
            root = insert(root, student);
            history.push("INSERT " + student.matricID);
            System.out.println("Adding new student...");
            System.out.println("Name: " + student.name);
            System.out.println("Matric ID: " + student.matricID);
            System.out.println("Street 1: " + student.street1);
            System.out.println("Street 2: " + student.street2);
            System.out.println("City: " + student.city);
            System.out.println("District: " + student.district);
            System.out.println("State: " + student.state);
            System.out.println("Postcode: " + student.postcode);
            System.out.println("Programme: " + student.programme);
            System.out.println("CGPA: " + student.cgpa);
            System.out.println("Subtree sizes updated. Current BST height: " + height(root));
        }
        
        private BSTNode insert(BSTNode node, Student student) {
            if (node == null) return new BSTNode(student);
            
            int cmp = student.matricID.compareTo(node.student.matricID);
            if (cmp < 0) {
                node.left = insert(node.left, student);
            } else if (cmp > 0) {
                node.right = insert(node.right, student);
            } else {
                // Duplicate matric ID - update the student info
                node.student = student;
                return node;
            }
            
            node.size = 1 + size(node.left) + size(node.right);
            return node;
        }
        
        // Delete a student from BST
        public void delete(String matricID) {
            System.out.println("Enter student matric ID to delete: " + matricID);
            System.out.println("Deleting...");
            root = delete(root, matricID);
            history.push("DELETE " + matricID);
            System.out.println("Subtree sizes updated. Current BST height: " + height(root));
        }
        
        private BSTNode delete(BSTNode node, String matricID) {
            if (node == null) return null;
            
            int cmp = matricID.compareTo(node.student.matricID);
            if (cmp < 0) {
                node.left = delete(node.left, matricID);
            } else if (cmp > 0) {
                node.right = delete(node.right, matricID);
            } else {
                if (node.right == null) return node.left;
                if (node.left == null) return node.right;
                
                BSTNode temp = node;
                node = min(temp.right);
                node.right = deleteMin(temp.right);
                node.left = temp.left;
            }
            
            node.size = 1 + size(node.left) + size(node.right);
            return node;
        }
        
        private BSTNode min(BSTNode node) {
            if (node.left == null) return node;
            return min(node.left);
        }
        
        private BSTNode deleteMin(BSTNode node) {
            if (node.left == null) return node.right;
            node.left = deleteMin(node.left);
            node.size = 1 + size(node.left) + size(node.right);
            return node;
        }
        
        // Search for a student by matric ID
        public Student search(String matricID) {
            return search(root, matricID);
        }
        
        private Student search(BSTNode node, String matricID) {
            if (node == null) return null;
            
            int cmp = matricID.compareTo(node.student.matricID);
            if (cmp < 0) return search(node.left, matricID);
            else if (cmp > 0) return search(node.right, matricID);
            else return node.student;
        }
        
        // In-order traversal
        public List<Student> inOrderTraversal() {
            List<Student> students = new ArrayList<>();
            inOrderTraversal(root, students);
            return students;
        }
        
        private void inOrderTraversal(BSTNode node, List<Student> students) {
            if (node == null) return;
            inOrderTraversal(node.left, students);
            students.add(node.student);
            inOrderTraversal(node.right, students);
        }
        
        // Helper methods
        private int size(BSTNode node) {
            return node == null ? 0 : node.size;
        }
        
        public int height() {
            return height(root);
        }
        
        private int height(BSTNode node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }
        
        public int balanceFactor(BSTNode node) {
            if (node == null) return 0;
            return height(node.left) - height(node.right);
        }
        
        // Undo last operation
        public void undo() {
            if (history.isEmpty()) {
                System.out.println("No operations to undo.");
                return;
            }
            
            String lastOp = history.pop();
            String[] parts = lastOp.split(" ");
            if (parts[0].equals("INSERT")) {
                delete(parts[1]);
                history.pop(); // Remove the delete operation from history
                System.out.println("Undo INSERT: Deleted student " + parts[1]);
            } else if (parts[0].equals("DELETE")) {
                // In a real implementation, we would need to store the deleted student to reinsert
                System.out.println("Undo DELETE: Would reinsert student " + parts[1]);
            }
        }
    }

    // Module 2: Search engine
    static class SearchEngine {
        private StudentBST bst;
        
        public SearchEngine(StudentBST bst) {
            this.bst = bst;
        }
        
        // Search by matric ID
        public List<Student> searchByMatricID(String matricID) {
            Student student = bst.search(matricID);
            List<Student> result = new ArrayList<>();
            if (student != null) {
                result.add(student);
            }
            return result;
        }
        
        // Search by exact name
        public List<Student> searchByName(String name) {
            List<Student> allStudents = bst.inOrderTraversal();
            List<Student> result = new ArrayList<>();
            for (Student s : allStudents) {
                if (s.name.equalsIgnoreCase(name)) {
                    result.add(s);
                }
            }
            return result;
        }
        
        // Search by partial name (prefix)
        public List<Student> searchByPartialName(String partialName) {
            List<Student> allStudents = bst.inOrderTraversal();
            List<Student> result = new ArrayList<>();
            for (Student s : allStudents) {
                if (s.name.toLowerCase().contains(partialName.toLowerCase())) {
                    result.add(s);
                }
            }
            return result;
        }
        
        // Search by state
        public List<Student> searchByState(String state) {
            List<Student> allStudents = bst.inOrderTraversal();
            List<Student> result = new ArrayList<>();
            for (Student s : allStudents) {
                if (s.state.equalsIgnoreCase(state)) {
                    result.add(s);
                }
            }
            return result;
        }
        
        // Search by programme
        public List<Student> searchByProgramme(String programme) {
            List<Student> allStudents = bst.inOrderTraversal();
            List<Student> result = new ArrayList<>();
            for (Student s : allStudents) {
                if (s.programme.equalsIgnoreCase(programme)) {
                    result.add(s);
                }
            }
            return result;
        }
        
        // Search by CGPA range
        public List<Student> searchByCGPARange(double min, double max) {
            List<Student> allStudents = bst.inOrderTraversal();
            List<Student> result = new ArrayList<>();
            for (Student s : allStudents) {
                if (s.cgpa >= min && s.cgpa <= max) {
                    result.add(s);
                }
            }
            return result;
        }
    }

    // Module 3: Sorting
    static class StudentSorter {
        // Sort students using selection sort
        public List<Student> selectionSort(List<Student> students, String field, boolean ascending) {
            List<Student> sorted = new ArrayList<>(students);
            for (int i = 0; i < sorted.size() - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < sorted.size(); j++) {
                    if (compareStudents(sorted.get(j), sorted.get(minIndex), field) * (ascending ? 1 : -1) < 0) {
                        minIndex = j;
                    }
                }
                if (minIndex != i) {
                    Student temp = sorted.get(i);
                    sorted.set(i, sorted.get(minIndex));
                    sorted.set(minIndex, temp);
                }
            }
            return sorted;
        }
        
        // Sort students using insertion sort
        public List<Student> insertionSort(List<Student> students, String field, boolean ascending) {
            List<Student> sorted = new ArrayList<>(students);
            for (int i = 1; i < sorted.size(); i++) {
                Student key = sorted.get(i);
                int j = i - 1;
                while (j >= 0 && compareStudents(sorted.get(j), key, field) * (ascending ? 1 : -1) > 0) {
                    sorted.set(j + 1, sorted.get(j));
                    j--;
                }
                sorted.set(j + 1, key);
            }
            return sorted;
        }
        
        // Multi-field sorting
        public List<Student> multiFieldSort(List<Student> students, List<String> fields, List<Boolean> ascendingOrders) {
            List<Student> sorted = new ArrayList<>(students);
            sorted.sort((s1, s2) -> {
                for (int i = 0; i < fields.size(); i++) {
                    int cmp = compareStudents(s1, s2, fields.get(i));
                    if (cmp != 0) {
                        return cmp * (ascendingOrders.get(i) ? 1 : -1);
                    }
                }
                return 0;
            });
            return sorted;
        }
        
        // Compare two students based on field
        private int compareStudents(Student s1, Student s2, String field) {
            switch (field.toLowerCase()) {
                case "matricid":
                    return s1.matricID.compareTo(s2.matricID);
                case "name":
                    return s1.name.compareTo(s2.name);
                case "state":
                    return s1.state.compareTo(s2.state);
                case "district":
                    return s1.district.compareTo(s2.district);
                case "postcode":
                    return s1.postcode.compareTo(s2.postcode);
                case "programme":
                    return s1.programme.compareTo(s2.programme);
                case "cgpa":
                    return Double.compare(s1.cgpa, s2.cgpa);
                default:
                    return 0;
            }
        }
    }

    // Module 4: Recursion
    static class RecursiveOperations {
        private StudentBST bst;
        
        public RecursiveOperations(StudentBST bst) {
            this.bst = bst;
        }
        
        // Total number of students
        public int totalStudents() {
            return bst.root == null ? 0 : bst.root.size;
        }
        
        // Total number of students in a programme
        public int countStudentsInProgramme(String programme) {
            return countStudentsInProgramme(bst.root, programme);
        }
        
        private int countStudentsInProgramme(BSTNode node, String programme) {
            if (node == null) return 0;
            int count = node.student.programme.equalsIgnoreCase(programme) ? 1 : 0;
            return count + countStudentsInProgramme(node.left, programme) 
                         + countStudentsInProgramme(node.right, programme);
        }
        
        // Total number of students from a state
        public int countStudentsFromState(String state) {
            return countStudentsFromState(bst.root, state);
        }
        
        private int countStudentsFromState(BSTNode node, String state) {
            if (node == null) return 0;
            int count = node.student.state.equalsIgnoreCase(state) ? 1 : 0;
            return count + countStudentsFromState(node.left, state) 
                       + countStudentsFromState(node.right, state);
        }
        
        // Count students in CGPA range
        public int countStudentsInCGPARange(double min, double max) {
            return countStudentsInCGPARange(bst.root, min, max);
        }
        
        private int countStudentsInCGPARange(BSTNode node, double min, double max) {
            if (node == null) return 0;
            int count = (node.student.cgpa >= min && node.student.cgpa <= max) ? 1 : 0;
            return count + countStudentsInCGPARange(node.left, min, max) 
                         + countStudentsInCGPARange(node.right, min, max);
        }
        
        // Average CGPA for a programme
        public double averageCgpaForProgramme(String programme) {
            int[] countAndSum = averageCgpaForProgramme(bst.root, programme);
            return countAndSum[0] == 0 ? 0 : (double) countAndSum[1] / countAndSum[0];
        }
        
        private int[] averageCgpaForProgramme(BSTNode node, String programme) {
            if (node == null) return new int[]{0, 0};
            
            int[] left = averageCgpaForProgramme(node.left, programme);
            int[] right = averageCgpaForProgramme(node.right, programme);
            
            int count = left[0] + right[0];
            int sum = left[1] + right[1];
            
            if (node.student.programme.equalsIgnoreCase(programme)) {
                count++;
                sum += node.student.cgpa;
            }
            
            return new int[]{count, sum};
        }
        
        // Average CGPA for a state
        public double averageCgpaForState(String state) {
            int[] countAndSum = averageCgpaForState(bst.root, state);
            return countAndSum[0] == 0 ? 0 : (double) countAndSum[1] / countAndSum[0];
        }
        
        private int[] averageCgpaForState(BSTNode node, String state) {
            if (node == null) return new int[]{0, 0};
            
            int[] left = averageCgpaForState(node.left, state);
            int[] right = averageCgpaForState(node.right, state);
            
            int count = left[0] + right[0];
            int sum = left[1] + right[1];
            
            if (node.student.state.equalsIgnoreCase(state)) {
                count++;
                sum += node.student.cgpa;
            }
            
            return new int[]{count, sum};
        }
        
        // BST height
        public int bstHeight() {
            return bst.height();
        }
        
        // Balance factor at root
        public int balanceFactorAtRoot() {
            return bst.balanceFactor(bst.root);
        }
    }

    // Module 5: User Interface and Integration
    static class UserInterface {
        private StudentBST bst;
        private SearchEngine searchEngine;
        private StudentSorter sorter;
        private RecursiveOperations recursiveOps;
        private Scanner scanner;
        
        public UserInterface() {
            bst = new StudentBST();
            searchEngine = new SearchEngine(bst);
            sorter = new StudentSorter();
            recursiveOps = new RecursiveOperations(bst);
            scanner = new Scanner(System.in);
        }
        
        // Main menu
        public void mainMenu() {
            System.out.println("| Welcome to FSKTM Student Enrolment System    |");
            System.out.println("|---|");
            System.out.println("| 1. Save/Load Records    |");
            System.out.println("| 2. Insert Student    |");
            System.out.println("| 3. Delete Student    |");
            System.out.println("| 4. Search Student    |");
            System.out.println("| 5. Sort Student Records    |");
            System.out.println("| 6. Show Statistics    |");
            System.out.println("| 7. History    |");
            System.out.println("| 8. Exit    |");
            
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    saveLoadMenu();
                    break;
                case 2:
                    insertStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    sortStudents();
                    break;
                case 6:
                    showStatistics();
                    break;
                case 7:
                    historyMenu();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            mainMenu();
        }
        
        // Save/Load menu
        private void saveLoadMenu() {
            System.out.println("*Loading/Saving Records*");
            System.out.println("1. Load from CSV");
            System.out.println("2. Save to CSV");
            System.out.println("3. Back to main menu");
            
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    loadFromCSV();
                    break;
                case 2:
                    saveToCSV();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            saveLoadMenu();
        }
        
        // Load from CSV
        private void loadFromCSV() {
            System.out.print("Enter CSV file name: ");
            String filename = scanner.nextLine();
            
            int added = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length >= 10) {
                        Student student = new Student(
                            values[0].trim(), values[1].trim(), values[2].trim(), 
                            values[3].trim(), values[4].trim(), values[5].trim(), 
                            values[6].trim(), values[7].trim(), values[8].trim(), 
                            Double.parseDouble(values[9].trim())
                        );
                        bst.insert(student);
                        added++;
                    }
                }
                System.out.println("*Loading from file*");
                System.out.println("Loaded " + added + " students from file: " + filename);
            } catch (IOException e) {
                System.out.println("Error loading file: " + e.getMessage());
            }
        }
        
        // Save to CSV
        // Save to CSV
        private void saveToCSV() {
            System.out.print("Enter CSV file name: ");
            String filename = scanner.nextLine();

            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                List<Student> students = bst.inOrderTraversal();
                for (Student s : students) {
                    pw.println(String.join(",", 
                        s.matricID, s.name, s.street1, s.street2, s.city, 
                        s.district, s.state, s.postcode, s.programme, String.valueOf(s.cgpa)
                    ));  // Added the missing closing parenthesis here
                }
                System.out.println("*Saving data to file*");
                System.out.println("Saving " + students.size() + " students to: " + filename);
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }
        
        // Insert student
        private void insertStudent() {
            System.out.println("*Inserting new records*");
            
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Matric ID: ");
            String matricID = scanner.nextLine();
            
            System.out.print("Street 1: ");
            String street1 = scanner.nextLine();
            
            System.out.print("Street 2: ");
            String street2 = scanner.nextLine();
            
            System.out.print("City: ");
            String city = scanner.nextLine();
            
            System.out.print("District: ");
            String district = scanner.nextLine();
            
            System.out.print("State: ");
            String state = scanner.nextLine();
            
            System.out.print("Postcode: ");
            String postcode = scanner.nextLine();
            
            System.out.print("Programme (AI/SE/IS/MM/CS): ");
            String programme = scanner.nextLine();
            
            System.out.print("CGPA: ");
            double cgpa = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            
            Student student = new Student(matricID, name, street1, street2, city, 
                                        district, state, postcode, programme, cgpa);
            bst.insert(student);
        }
        
        // Delete student
        private void deleteStudent() {
            System.out.println("*Deleting student record*");
            System.out.print("Enter student matric ID to delete: ");
            String matricID = scanner.nextLine();
            
            Student student = bst.search(matricID);
            if (student != null) {
                System.out.println(student);
                bst.delete(matricID);
            } else {
                System.out.println("Student not found.");
            }
        }
        
        // Search student
        private void searchStudent() {
            System.out.println("*Query searching*");
            System.out.println("Search by: 1 (ID), 2 (Name), 3 (Partial Name), 4 (Address), 5 (Programme), 6 (CGPA): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            List<Student> results = new ArrayList<>();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter matric ID: ");
                    String matricID = scanner.nextLine();
                    results = searchEngine.searchByMatricID(matricID);
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    results = searchEngine.searchByName(name);
                    break;
                case 3:
                    System.out.print("Enter partial name: ");
                    String partialName = scanner.nextLine();
                    results = searchEngine.searchByPartialName(partialName);
                    break;
                case 4:
                    System.out.print("Enter state: ");
                    String state = scanner.nextLine();
                    results = searchEngine.searchByState(state);
                    break;
                case 5:
                    System.out.print("Enter programme (AI/SE/IS/MM/CS): ");
                    String programme = scanner.nextLine();
                    if (!programme.equalsIgnoreCase("AI") && !programme.equalsIgnoreCase("SE") && 
                        !programme.equalsIgnoreCase("IS") && !programme.equalsIgnoreCase("MM") && 
                        !programme.equalsIgnoreCase("CS")) {
                        System.out.println("Error: no such programme");
                        return;
                    }
                    results = searchEngine.searchByProgramme(programme);
                    break;
                case 6:
                    System.out.print("Enter CGPA range to search for (min),(max): ");
                    String rangeInput = scanner.nextLine();
                    String[] rangeParts = rangeInput.split(",");
                    if (rangeParts.length != 2) {
                        System.out.println("Error: please enter a valid range to search for: " + rangeInput);
                        System.out.print("Enter CGPA range to search for (min),(max): ");
                        rangeInput = scanner.nextLine();
                        rangeParts = rangeInput.split(",");
                    }
                    
                    double min, max;
                    try {
                        min = Double.parseDouble(rangeParts[0].trim());
                        max = Double.parseDouble(rangeParts[1].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: invalid CGPA values");
                        return;
                    }
                    
                    if (min > max) {
                        System.out.println("Error: min cannot be greater than max");
                        return;
                    }
                    
                    System.out.printf("Searching for students with CGPA between (%.2f) and (%.2f):\n", min, max);
                    results = searchEngine.searchByCGPARange(min, max);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            if (results.isEmpty()) {
                System.out.println("Search ended. Student not found.");
            } else {
                System.out.println("Results: " + results.size() + " students found");
                for (Student s : results) {
                    System.out.println("- " + s);
                }
            }
        }
        
        // Sort students
        private void sortStudents() {
            System.out.println("*User can choose what field and what order to sort*");
            
            List<String> fields = new ArrayList<>();
            List<Boolean> ascendingOrders = new ArrayList<>();
            
            while (true) {
                System.out.println("Choose which fields to sort. Current selection " + getCurrentSelection(fields, ascendingOrders));
                System.out.println("1. Matric ID");
                System.out.println("2. Name");
                System.out.println("3. Address (State)");
                System.out.println("4. Programme");
                System.out.println("5. CGPA");
                System.out.println("6. Done selecting fields");
                
                System.out.print("Enter choice: ");
                int fieldChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (fieldChoice == 6) break;
                if (fieldChoice < 1 || fieldChoice > 5) {
                    System.out.println("Invalid choice.");
                    continue;
                }
                
                String field;
                switch (fieldChoice) {
                    case 1: field = "matricid"; break;
                    case 2: field = "name"; break;
                    case 3: field = "state"; break;
                    case 4: field = "programme"; break;
                    case 5: field = "cgpa"; break;
                    default: field = "";
                }
                
                System.out.println("You have selected [" + field.toUpperCase() + "]. Which order to sort?");
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                
                System.out.print("Enter choice: ");
                int orderChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (orderChoice != 1 && orderChoice != 2) {
                    System.out.println("Invalid choice. Defaulting to ascending.");
                    orderChoice = 1;
                }
                
                fields.add(field);
                ascendingOrders.add(orderChoice == 1);
            }
            
            if (fields.isEmpty()) {
                System.out.println("No fields selected for sorting.");
                return;
            }
            
            List<Student> students = bst.inOrderTraversal();
            List<Student> sorted = sorter.multiFieldSort(students, fields, ascendingOrders);
            
            System.out.println("*Multi-field sorting*");
            System.out.println("Sorted student list by " + getCurrentSelection(fields, ascendingOrders));
            for (Student s : sorted) {
                System.out.println("- " + s);
            }
        }
        
        private String getCurrentSelection(List<String> fields, List<Boolean> ascendingOrders) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fields.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append("[").append(fields.get(i).toUpperCase()).append("] (")
                  .append(ascendingOrders.get(i) ? "Ascending" : "Descending").append(")");
            }
            return fields.isEmpty() ? "[None]" : sb.toString();
        }
        
        // Show statistics
        private void showStatistics() {
            System.out.println("*Module 4: Recursion*");
            
            // Count students in CGPA range
            int cgpaRangeCount = recursiveOps.countStudentsInCGPARange(3.50, 3.99);
            System.out.println("Students with CGPA between 3.50 and 3.99: " + cgpaRangeCount);
            
            // Count students in programmes
            int aiCount = recursiveOps.countStudentsInProgramme("AI");
            int seCount = recursiveOps.countStudentsInProgramme("SE");
            System.out.println("Students who apply for AI: " + aiCount);
            System.out.println("Students who apply for AI and SE: " + (aiCount + seCount));
            
            // BST height and balance factor
            System.out.println("BST height: " + recursiveOps.bstHeight());
            System.out.println("Balance factor at root: " + recursiveOps.balanceFactorAtRoot() + 
                              (recursiveOps.balanceFactorAtRoot() > 0 ? " (Left-heavy)" : 
                               recursiveOps.balanceFactorAtRoot() < 0 ? " (Right-heavy)" : " (Balanced)"));
        }
        
        // History menu
        private void historyMenu() {
            System.out.println("1. Undo last operation");
            System.out.println("2. Back to main menu");
            
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            if (choice == 1) {
                bst.undo();
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Create sample data to demonstrate the system
        StudentBST bst = new StudentBST();
        
        // Insert sample students
        bst.insert(new Student("WHA10061", "Liew Wei Shiung", 
            "123, Level 4, Apartment FiveSixSeven", "Jalan Lapan", 
            "Petaling Jaya", "Petaling", "Selangor", "47800", 
            "Artificial Intelligence", 2.80));
            
        bst.insert(new Student("WHA10072", "Mohd Aladdin", 
            "456, Jalan Sembilan", "Taman Ten", 
            "Kuala Lumpur", "Kuala Lumpur", "Kuala Lumpur", "50000", 
            "Artificial Intelligence", 3.99));
            
        bst.insert(new Student("WHA10142", "Wong Feng Liew", 
            "789, Jalan Sebelas", "Taman Dua Belas", 
            "Ipoh", "Kinta", "Perak", "30000", 
            "Software Engineering", 3.80));
        
        // Start UI
        UserInterface ui = new UserInterface();
        ui.bst = bst; // Use our BST with sample data
        ui.mainMenu();
    }
}
