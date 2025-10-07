package ga_2_ds;

//Group Tutorial K2-7 

import java.util.Stack;

public class GA_2_DS {
//Group members:
//Student 1: Lee Yu Xuan (OCC: 12) - Singly Linked List
//Student 2: Kwek Chee Ling (OCC: 11) - Indexed List
//Student 3: Tham Wing Shan & Gan Shu Xian (OCC: 8) - Doubly Linked List with Cursor
//Student 4: Teoh En Xi (OCC: 11) - Undo/Redo with Stack
//Student 5: Yeong Hui Ni (OCC: 12) - Testing

      // ===== Student 1 ===== 
    // Lee Yu Xuan , OCC 12 – Singly Linked List 
    static class SinglyLinkedList {
        static class Node {
            String data;
            Node next;

            Node(String data) {
                this.data = data;
            }
        }

        Node head, tail;

        void addFirst(String msg) {
            Node newNode = new Node(msg);
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head = newNode;
            }
        }

        void addLast(String msg) {
            Node newNode = new Node(msg);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        String removeFirst() {
            if (head == null) return null;
            String msg = head.data;
            head = head.next;
            if (head == null) tail = null;
            return msg;
        }

        void print() {
            Node current = head;
            while (current != null) {
                System.out.print(current.data + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }   
    
    // =========== Student 2 =============
    // Kwek Chee Ling , OCC 11 - Indexed List
    static class SinglyLinkedListIndexed extends SinglyLinkedList{
        void addAt(int index, String msg) {
            Node newNode = new Node(msg);
    
            if (index <= 0 || head == null) {
                addFirst(msg);
                    return;
             }

            Node current = head;
             int count = 0;

            while (count < index - 1 && current.next != null) {
            current = current.next;
            count++;
        }

        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
        tail = newNode;
        }
}

        
        String removeAt(int index){
        if (index == 0) return removeFirst();

            Node current = head;
            for (int i = 0; i < index - 1 && current != null; i++) {
                current = current.next;
            }

            if (current == null || current.next == null) return null;

            String data = current.next.data;
            current.next = current.next.next;
            if (current.next == null) tail = current;
            return data;
        } 
    }
    
    // ===== Student 3,4 ===== 
    // Tham Wing Shan,OCC8 && Gan Shu Xian, OCC8 – Doubly Linked List with Cursor 
    static class DoublyLinkedListWithCursor {
        static class Node {
            String data;
            Node prev, next;

            Node(String data) {
                this.data = data;
            }
        }

        Node head, tail, cursor;

        void insertAtCursor(String msg) {
            Node newNode = new Node(msg);
            if (cursor == null) {
                head = tail = cursor = newNode;
            } else {
                newNode.prev = cursor;
                newNode.next = cursor.next;
                if (cursor.prev != null) {
                    cursor.next.prev = newNode;
                } else {
                    tail = newNode;
                }
                cursor.next = newNode;
                cursor = newNode;
            }
        }

        void moveLeft() {
            if (cursor != null && cursor.prev != null) {
                cursor = cursor.prev;
            }
        }

        void moveRight() {
            if (cursor != null && cursor.next != null) {
                cursor = cursor.next;
            }
        }

        void print() {
            Node current = head;
            while (current != null) {
                if (current == cursor) {
                    System.out.print("[" + current.data + "] <-> ");
                } else {
                    System.out.print(current.data + " <-> ");
                }
                current = current.next;
            }
            System.out.println("null");
        }
    }
    
    // ===== Student 4 ===== 
    // TEOH EN XI , OCC:11 – Undo/Redo with Stack 
    static class UndoRedoManager { 
        Stack<String> undo = new Stack<>(); 
        Stack<String> redo = new Stack<>(); 
 
        void perform(String action) { 
            undo.push(action); 
            redo.clear(); 
        } 
 
        String undo() { 
            if (!undo.isEmpty()) { 
                String action = undo.pop(); 
                redo.push(action); 
                return action; 
            } 
            return null;
        } 
 
        String redo() { 
            if (!redo.isEmpty()) { 
                String action = redo.pop(); 
                undo.push(action); 
                return action; 
            } 
            return null; 
        } 
 
        void printStacks() { 
            System.out.println("Undo Stack: " + undo); 
            System.out.println("Redo Stack: " + redo); 
        }
}
    
    
    // ============= Student 5 =========
    // Yeong Hui Ni, OCC: 12 - Testing
    public static void main (String [] args){
        
        System.out.println("=== SinglyLinkedList ===");
        SinglyLinkedList list = new SinglyLinkedList();
        list.addFirst("Hello");
        list.addLast("How are you?");
        list.removeFirst();
        list.print(); // Output: How are you? -> null

        System.out.println("\n=== SinglyLinkedListIndexed ===");
        SinglyLinkedListIndexed indexedList = new SinglyLinkedListIndexed();
        indexedList.addLast("Hello");
        indexedList.addAt(1, "I'm fine");
        indexedList.removeAt(0);
        indexedList.print(); // Output: I'm fine -> null

        System.out.println("\n=== DoublyLinkedListWithCursor ===");
        DoublyLinkedListWithCursor history = new DoublyLinkedListWithCursor();
        history.insertAtCursor("Hi");
        history.insertAtCursor("Bye");
        history.moveLeft();
        history.insertAtCursor("Wait");
        history.print(); // Output: Hi <-> [Wait] <-> Bye <-> null

        System.out.println("\n=== UndoRedoManager ===");
        UndoRedoManager manager = new UndoRedoManager();
        manager.perform("add:Hi");
        manager.perform("remove:Bye");
        System.out.println("Undo: " + manager.undo());
        System.out.println("Redo: " + manager.redo());
        manager.printStacks();
    }
    
}

    

