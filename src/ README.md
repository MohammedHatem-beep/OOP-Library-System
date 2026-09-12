# Project Structure & Class Responsibilities

## 1. LibraryItem.java

`LibraryItem` is an **abstract class** that represents the common properties and behaviors shared by all library items.

### Responsibilities

* Stores the item ID.
* Stores and validates the item title.
* Keeps track of whether the item is borrowed.
* Generates unique item IDs automatically.
* Keeps a static count of all created items.
* Provides common methods for borrowing and returning items.
* Displays item information.

### Important Methods

* `getId()` – Returns the unique item ID.
* `getTitle()` – Returns the item title.
* `setTitle()` – Updates the title with validation.
* `isBorrowed()` – Checks whether the item is currently borrowed.
* `markBorrowed()` – Marks the item as borrowed.
* `markReturned()` – Marks the item as returned.
* `displayInfo()` – Displays the item's information.
* `getLoanPeriodDays()` – Abstract method implemented by subclasses.
* `getType()` – Abstract method implemented by subclasses.

---

## 2. Book.java

`Book` represents a book in the library.

It **extends `LibraryItem`**, so it inherits the common item properties and behaviors.

### Additional Fields

* `author`
* `pages`

### Responsibilities

* Stores and validates the book's author.
* Stores and validates the number of pages.
* Defines the book's loan period.
* Defines the item type as `Book`.

### Overridden Methods

```java
@Override
public int getLoanPeriodDays() {
    return 21;
}
```

The book can be borrowed for **21 days**.

```java
@Override
public String getType() {
    return "Book";
}
```

---

## 3. Magazine.java

`Magazine` represents a magazine in the library.

It **extends `LibraryItem`**.

### Additional Field

* `issueNumber`

### Responsibilities

* Stores and validates the magazine issue number.
* Defines the magazine's loan period.
* Defines the item type as `Magazine`.

### Overridden Methods

```java
@Override
public int getLoanPeriodDays() {
    return 7;
}
```

The magazine can be borrowed for **7 days**.

```java
@Override
public String getType() {
    return "Magazine";
}
```

---

## 4. DVD.java

`DVD` represents a DVD in the library.

It **extends `LibraryItem`**.

### Additional Field

* `runtimeMinutes`

### Responsibilities

* Stores and validates the DVD runtime.
* Ensures that the runtime is greater than zero.
* Defines the DVD's loan period.
* Defines the item type as `DVD`.

### Overridden Methods

```java
@Override
public int getLoanPeriodDays() {
    return 3;
}
```

The DVD can be borrowed for **3 days**.

```java
@Override
public String getType() {
    return "DVD";
}
```

---

## 5. Member.java

`Member` represents a person registered in the library.

### Fields

* `memberId`
* `name`
* `maxAllowed`
* `borrowedItems`

### Responsibilities

* Stores and validates member information.
* Defines the maximum number of items a member can borrow.
* Keeps track of the items currently borrowed by the member.
* Checks whether the member can borrow more items.
* Adds and removes borrowed items.

### Important Methods

* `getBorrowedCount()` – Returns the number of currently borrowed items.
* `canBorrowMore()` – Checks whether the member is still below their borrowing limit.
* `addBorrowedItem()` – Adds an item to the member's borrowed items.
* `removeBorrowedItem()` – Removes an item from the member's borrowed items.
* `hasBorrowed()` – Checks whether the member currently has a specific item.

The list uses:

```java
List<LibraryItem>
```

instead of a specific class such as `List<Book>` because a member can borrow different types of items such as Books, Magazines, and DVDs.

---

## 6. LibraryException.java

`LibraryException` is a **custom checked exception** used to handle library business rules.

```java
public class LibraryException extends Exception
```

### Purpose

It prevents the program from crashing when an invalid library operation occurs.

### Examples

* Borrowing an item that is already borrowed.
* Borrowing an item that does not exist.
* Borrowing with a non-existing member.
* Exceeding the member's borrowing limit.
* Returning an item that the member did not borrow.

The exception is caught in the `Main` class and displayed as a friendly error message.

---

## 7. Library.java

`Library` is the main class responsible for managing the entire library system.

### Main Collections

```java
Map<String, LibraryItem> catalog;
```

Stores all library items using the item ID as the key.

```java
Map<String, Member> members;
```

Stores all registered members using the member ID as the key.

```java
Set<String> borrowedIds;
```

Stores the IDs of currently borrowed items.

### Main Responsibilities

* Add items to the catalog.
* Register members.
* Borrow items.
* Return items.
* Display the catalog.
* Generate the library report.
* Search for items by title.
* Display available items.

### Important Methods

#### `addItem()`

Adds a new `LibraryItem` to the library catalog.

#### `addMember()`

Registers a new member in the system.

#### `borrowItem()`

Handles the complete borrowing process:

1. Finds the member.
2. Finds the item.
3. Checks whether the item is already borrowed.
4. Checks whether the member has reached the borrowing limit.
5. Marks the item as borrowed.
6. Adds the item to the member's borrowed items.
7. Adds the item ID to `borrowedIds`.

#### `returnItem()`

Handles the return process:

1. Finds the member.
2. Finds the item.
3. Checks whether the member actually borrowed the item.
4. Marks the item as returned.
5. Removes it from the member's borrowed items.
6. Removes its ID from `borrowedIds`.

#### `listCatalog()`

Displays all library items by calling:

```java
item.displayInfo();
```

The method works with the `LibraryItem` type and does not need `instanceof` checks.

#### `printReport()`

Displays:

* Total number of items.
* Number of currently borrowed items.
* Borrowed item IDs.
* Number of items by type.
* Total number of items created.

---

## 8. Main.java

`Main` is the entry point of the application.

It provides the **menu-driven console interface**.

### Menu

```text
===== Library Lending System =====
1. Add Item
2. Add Member
3. Borrow Item
4. Return Item
5. List Catalog
6. Report
7. Exit
```

### Responsibilities

* Displays the main menu.
* Reads user input.
* Creates Books, Magazines, and DVDs.
* Creates library members.
* Calls the appropriate `Library` methods.
* Handles `LibraryException`.
* Validates numeric input.
* Prevents the program from crashing because of invalid user input.

---

