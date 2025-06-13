public class Student {
    public static void main(String[] args) {
        StudentLinkedList list = new StudentLinkedList();
        list.add("12345", 3.75);
        list.add("12346", 3.82);
        list.add("12347", 3.65);

        System.out.println("// Sebelum sort");
        list.printList();

        list.sortByGPA();

        System.out.println("\n// Setelah sortByGPA()");
        list.printList();
    }
}

class StudentNode {  // <== Ganti dari 'Student'
    String NIM;
    double GPA;
    StudentNode next;

    public StudentNode(String NIM, double GPA) {
        this.NIM = NIM;
        this.GPA = GPA;
        this.next = null;
    }
}

class StudentLinkedList {
    StudentNode head;

    public void add(String NIM, double GPA) {
        StudentNode newStudent = new StudentNode(NIM, GPA);
        if (head == null) {
            head = newStudent;
        } else {
            StudentNode temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newStudent;
        }
    }

    public void printList() {
        StudentNode curr = head;
        while (curr != null) {
            System.out.println("NIM: " + curr.NIM + ", IPK: " + curr.GPA);
            curr = curr.next;
        }
    }

    public void sortByGPA() {
        if (head == null) return;
        boolean swapped;
        do {
            swapped = false;
            StudentNode curr = head;
            while (curr.next != null) {
                if (curr.GPA < curr.next.GPA) {
                    String tmpNIM = curr.NIM;
                    double tmpGPA = curr.GPA;
                    curr.NIM = curr.next.NIM;
                    curr.GPA = curr.next.GPA;
                    curr.next.NIM = tmpNIM;
                    curr.next.GPA = tmpGPA;
                    swapped = true;
                }
                curr = curr.next;
            }
        } while (swapped);
    }

    public void reverse() {
        StudentNode prev = null;
        StudentNode curr = head;
        while (curr != null) {
            StudentNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
    }

    public StudentNode findHighestGPA() {
        if (head == null) return null;
        StudentNode max = head;
        StudentNode curr = head.next;
        while (curr != null) {
            if (curr.GPA > max.GPA) {
                max = curr;
            }
            curr = curr.next;
        }
        return max;
    }

    public StudentNode[] getStudentsAboveGPA(double threshold) {
        int count = 0;
        StudentNode curr = head;
        while (curr != null) {
            if (curr.GPA > threshold) count++;
            curr = curr.next;
        }

        StudentNode[] result = new StudentNode[count];
        curr = head;
        int idx = 0;
        while (curr != null) {
            if (curr.GPA > threshold) {
                result[idx++] = curr;
            }
            curr = curr.next;
        }
        return result;
    }

    public StudentLinkedList mergeSortedList(StudentLinkedList otherList) {
        StudentLinkedList result = new StudentLinkedList();
        StudentNode dummy = new StudentNode("", 0);
        StudentNode tail = dummy;

        StudentNode a = this.head;
        StudentNode b = otherList.head;

        while (a != null && b != null) {
            if (a.GPA >= b.GPA) {
                tail.next = new StudentNode(a.NIM, a.GPA);
                a = a.next;
            } else {
                tail.next = new StudentNode(b.NIM, b.GPA);
                b = b.next;
            }
            tail = tail.next;
        }

        while (a != null) {
            tail.next = new StudentNode(a.NIM, a.GPA);
            tail = tail.next;
            a = a.next;
        }

        while (b != null) {
            tail.next = new StudentNode(b.NIM, b.GPA);
            tail = tail.next;
            b = b.next;
        }

        result.head = dummy.next;
        return result;
    }
}