public class TextEditor {
    private StringBuilder content;
    private Action head;
    private Action current;

    public TextEditor() {
        content = new StringBuilder();
        head = null;
        current = null;
    }

    public void insertText(String text, int position) {
        content.insert(position, text);
        Action action = new Action("INSERT", text, position, null);
        addAction(action);
    }

    public void deleteText(int start, int length) {
        if (start >= 0 && start + length <= content.length()) {
            String removed = content.substring(start, start + length);
            content.delete(start, start + length);
            Action action = new Action("DELETE", removed, start, null);
            addAction(action);
        }
    }

    public void replaceText(int start, int length, String newText) {
        if (start >= 0 && start + length <= content.length()) {
            String oldText = content.substring(start, start + length);
            content.replace(start, start + length, newText);
            Action action = new Action("REPLACE", newText, start, oldText);
            addAction(action);
        }
    }

    public void undo() {
        if (current == null) return;

        switch (current.type) {
            case "INSERT":
                content.delete(current.position, current.position + current.text.length());
                break;
            case "DELETE":
                content.insert(current.position, current.text);
                break;
            case "REPLACE":
                content.replace(current.position, current.position + current.text.length(), current.previousText);
                break;
        }
        current = current.prev;
    }

    public void redo() {
        if (current == null && head != null) {
            current = head;
        } else if (current != null && current.next != null) {
            current = current.next;
        } else {
            return;
        }

        switch (current.type) {
            case "INSERT":
                content.insert(current.position, current.text);
                break;
            case "DELETE":
                content.delete(current.position, current.position + current.text.length());
                break;
            case "REPLACE":
                content.replace(current.position, current.position + current.previousText.length(), current.text);
                break;
        }
    }

    public String getCurrentText() {
        return content.toString();
    }

    public void getActionHistory() {
        Action temp = head;
        int i = 1;
        while (temp != null) {
            System.out.println(i++ + ". " + temp.type + " at " + temp.position + " -> \"" + temp.text + "\"");
            temp = temp.next;
        }
    }

    private void addAction(Action action) {
        if (current != null && current.next != null) {
            current.next.prev = null;
            current.next = null;
        }

        if (head == null) {
            head = action;
            current = action;
        } else {
            current.next = action;
            action.prev = current;
            current = action;
        }
    }

    static class Action {
        String type;
        String text;
        int position;
        String previousText;
        Action next;
        Action prev;

        public Action(String type, String text, int position, String previousText) {
            this.type = type;
            this.text = text;
            this.position = position;
            this.previousText = previousText;
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        editor.insertText("Hello World", 0);
        editor.insertText("Beautiful ", 6);
        System.out.println(editor.getCurrentText()); // "Hello Beautiful World"

        editor.undo();
        System.out.println(editor.getCurrentText()); // "Hello World"

        editor.redo();
        System.out.println(editor.getCurrentText()); // "Hello Beautiful World"

        System.out.println("\nAction History:");
        editor.getActionHistory();
    }
}
