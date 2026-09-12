import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Library {
    private final Map<String, LibraryItem> catalog;
    private final Map<String, Member> members;
    private final Set<String> borrowedIds;

    public Library() {
        catalog = new HashMap<>();
        members = new HashMap<>();
        borrowedIds = new HashSet<>();
    }

    public void addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        catalog.put(item.getId(), item);
    }

    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (members.containsKey(member.getMemberId())) {
            throw new IllegalArgumentException("Member ID already exists.");
        }
        members.put(member.getMemberId(), member);
    }

    public void borrowItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("Member " + memberId + " does not exist.");
        }

        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException("Item " + itemId + " does not exist.");
        }

        if (item.isBorrowed()) {
            throw new LibraryException("item " + itemId + " is already out.");
        }

        if (!member.canBorrowMore()) {
            throw new LibraryException("Member " + memberId + " has reached the borrowing limit.");
        }

        item.markBorrowed();
        member.addBorrowedItem(item);
        borrowedIds.add(itemId);

        System.out.println("Borrowed " + itemId + " to " + memberId + ".");
    }

    public void returnItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("Member " + memberId + " does not exist.");
        }

        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException("Item " + itemId + " does not exist.");
        }

        if (!member.hasBorrowed(item)) {
            throw new LibraryException(
                    "Member " + memberId + " did not borrow item " + itemId + ".");
        }

        item.markReturned();
        member.removeBorrowedItem(item);
        borrowedIds.remove(itemId);

        System.out.println("Returned " + itemId + " from " + memberId + ".");
    }

    public void listCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        for (LibraryItem item : catalog.values()) {
            item.displayInfo();
        }
    }

    public void printReport() {
        Map<String, Integer> itemsByType = new TreeMap<>();

        for (LibraryItem item : catalog.values()) {
            itemsByType.merge(item.getType(), 1, Integer::sum);
        }

        System.out.println("---------- REPORT ----------");
        System.out.println("Total items : " + catalog.size());
        System.out.println("Currently out : " + borrowedIds.size());
        System.out.println("Borrowed ids : " + borrowedIds);
        System.out.println("Items by type : " + itemsByType);
        System.out.println("Total created : " + LibraryItem.getTotalItemsCreated());
        System.out.println("----------------------------");
    }

    public void searchByTitle(String title) {
        boolean found = false;

        for (LibraryItem item : catalog.values()) {
            if (item.getTitle().equalsIgnoreCase(title.trim())) {
                item.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No item found with title: " + title);
        }
    }

    public void listAvailableItems() {
        boolean found = false;

        for (LibraryItem item : catalog.values()) {
            if (!item.isBorrowed()) {
                item.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available items.");
        }
    }
}