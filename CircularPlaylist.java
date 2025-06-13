public class CircularPlaylist {
    public static void main(String[] args) {
        CircularPlaylist playlist = new CircularPlaylist();
        playlist.addSong("Bohemian Rhapsody", "Queen", 408);
        playlist.addSong("California", "Eagles", 391);
        playlist.addSong("Stairway to Heaven", "Led Zeppelin", 482);
        playlist.addSong("Imagine", "John Lennon", 183);

        playlist.displayPlaylist();
        playlist.playNext();
        playlist.playNext();
        playlist.playPrevious();
        System.out.println("Currently Playing: " + playlist.getCurrentSong());
        playlist.shuffle();
        playlist.displayPlaylist();
        System.out.println("Total Duration: " + playlist.getTotalDuration());
    }

    class Song {
        String title;
        String artist;
        int duration;
        Song next;

        Song(String title, String artist, int duration) {
            this.title = title;
            this.artist = artist;
            this.duration = duration;
            this.next = null;
        }
    }

    private Song head = null;
    private Song current = null;

    public void addSong(String title, String artist, int duration) {
        Song newSong = new Song(title, artist, duration);
        if (head == null) {
            head = newSong;
            head.next = head;
            current = head;
        } else {
            Song temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newSong;
            newSong.next = head;
        }
    }

    public void removeSong(String title) {
        if (head == null) return;
        Song temp = head, prev = null;
        do {
            if (temp.title.equals(title)) {
                if (prev != null) {
                    prev.next = temp.next;
                    if (temp == head) head = temp.next;
                    if (temp == current) current = temp.next;
                } else {
                    if (head.next == head) {
                        head = null;
                        current = null;
                        return;
                    } else {
                        Song last = head;
                        while (last.next != head) last = last.next;
                        head = head.next;
                        last.next = head;
                        if (current == temp) current = head;
                    }
                }
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);
    }

    public void playNext() {
        if (current != null) current = current.next;
    }

    public void playPrevious() {
        if (current == null || current.next == current) return;
        Song temp = head;
        while (temp.next != current) {
            temp = temp.next;
        }
        current = temp;
    }

    public String getCurrentSong() {
        if (current == null) return "No song is currently playing.";
        return current.title + " - " + current.artist + " (" + formatDuration(current.duration) + ")";
    }

    public void shuffle() {
        if (head == null || head.next == head) return;

        java.util.List<Song> songs = new java.util.ArrayList<>();
        Song temp = head;
        do {
            songs.add(temp);
            temp = temp.next;
        } while (temp != head);

        java.util.Collections.shuffle(songs);

        for (int i = 0; i < songs.size(); i++) {
            songs.get(i).next = songs.get((i + 1) % songs.size());
        }

        head = songs.get(0);
        current = head;
    }

    public String getTotalDuration() {
        int total = 0;
        if (head == null) return "00:00";

        Song temp = head;
        do {
            total += temp.duration;
            temp = temp.next;
        } while (temp != head);

        return formatDuration(total);
    }

    public void displayPlaylist() {
        if (head == null) {
            System.out.println("Playlist is empty.");
            return;
        }

        System.out.println("== Current Playlist ==");
        Song temp = head;
        do {
            System.out.println("- " + temp.title + " - " + temp.artist + " (" + formatDuration(temp.duration) + ")");
            temp = temp.next;
        } while (temp != head);

        System.out.println("Total Duration: " + getTotalDuration());
    }

    private String formatDuration(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%d:%02d", min, sec);
    }
}
