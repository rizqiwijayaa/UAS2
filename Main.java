public class Main {
    public static void main(String[] args) {
        StudentLinkedList list = new StudentLinkedList();
        list.insertLast("12345", "Andi Pratama", 3.75);
        list.insertLast("12346", "Sari Dewi", 3.82);
        list.insertLast("12347", "Budi Santoso", 3.65);
        list.display();
    }
}

class Student {
    String nim;
    String name;
    double gpa;
    Student next;

    public Student(String nim, String name, double gpa) {
        this.nim = nim;
        this.name = name;
        this.gpa = gpa;
        this.next = null;
    }
}

class StudentLinkedList {
    private Student head;

    public StudentLinkedList() {
        this.head = null;
    }

    public void insertFirst(String nim, String name, double gpa) {
        Student newStudent = new Student(nim, name, gpa);
        newStudent.next = head;
        head = newStudent;
    }

    public void insertLast(String nim, String name, double gpa) {
        Student newStudent = new Student(nim, name, gpa);
        if (head == null) {
            head = newStudent;
            return;
        }
        Student current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newStudent;
    }

    public void insertAt(int position, String nim, String name, double gpa) {
        if (position <= 0) {
            insertFirst(nim, name, gpa);
            return;
        }
        Student newStudent = new Student(nim, name, gpa);
        Student current = head;
        int index = 0;

        while (current != null && index < position - 1) {
            current = current.next;
            index++;
        }

        if (current == null) {
            insertLast(nim, name, gpa);
        } else {
            newStudent.next = current.next;
            current.next = newStudent;
        }
    }

    public void deleteByNim(String nim) {
        if (head == null) return;

        if (head.nim.equals(nim)) {
            head = head.next;
            return;
        }

        Student current = head;
        while (current.next != null && !current.next.nim.equals(nim)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    public Student search(String nim) {
        Student current = head;
        while (current != null) {
            if (current.nim.equals(nim)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void display() {
        System.out.println("=== Data Mahasiswa ===");
        Student current = head;
        while (current != null) {
            System.out.printf("NIM: %s, Nama: %s, IPK: %.2f\n", current.nim, current.name, current.gpa);
            current = current.next;
        }
        System.out.println("Total mahasiswa: " + size());
    }

    public int size() {
        int count = 0;
        Student current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public boolean isEmpty() {
        return head == null;
    }
}