# 📚 Library Management System
> A Java Swing desktop application for managing library books and borrower records.

**Capstone Project | B.Tech Computer Science**  
Akshat Sharma · Manan Rajoria · Samay Chutani  
*Faculty: Deepika Sachdev — School of Computer Science*

---

## Features

- ➕ Add, edit, and delete books
- 📖 Borrow and return books with automatic quantity tracking
- 🗓️ Auto-generates borrow date and due date (+7 days)
- ⚠️ Background overdue alert system
- 📋 Live tables for books and borrow records

---

## Project Structure

```
LibraryManagementSystem/
├── src/
│   ├── Book.java             # Book data model
│   ├── BorrowRecord.java     # Borrow record data model
│   ├── LibraryGUI.java       # Main GUI (Swing)
│   └── Main.java             # Entry point
├── Library_Management_System_Report.pdf
├── .gitignore
└── README.md
```

---

## How to Run

### Requirements
- Java JDK 8 or higher

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/LibraryManagementSystem.git
cd LibraryManagementSystem
```

**2. Compile the source files**
```bash
cd src
javac *.java
```

**3. Run the application**
```bash
java Main
```

---

## OOP Concepts Used

| Concept | Usage |
|---|---|
| Encapsulation | Data stored inside `Book` and `BorrowRecord` classes |
| Abstraction | Simplified interface hiding implementation details |
| Collections | `ArrayList` used for dynamic data storage |
| Event Handling | Action listeners on all buttons |

---

## Technologies

- **Language:** Java
- **GUI:** Java Swing
- **Data Storage:** ArrayList (in-memory)
- **Date/Time:** `java.time` API

---

## Future Enhancements

- [ ] MySQL database integration
- [ ] User login system
- [ ] Fine calculation for overdue books
- [ ] Search and filter functionality
