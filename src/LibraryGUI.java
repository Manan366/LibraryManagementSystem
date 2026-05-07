import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibraryGUI extends JFrame {

    ArrayList<Book> books = new ArrayList<>();
    ArrayList<BorrowRecord> records = new ArrayList<>();

    DefaultTableModel bookModel, recordModel;

    public LibraryGUI() {

        setTitle("Library System");
        setSize(750, 450);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(40,40,40));

        // ===== TOP =====
        JPanel top = new JPanel();
        top.setBackground(new Color(60,60,60));

        JTextField t = new JTextField(6);
        JTextField a = new JTextField(6);
        JTextField s = new JTextField(6);
        JTextField q = new JTextField(3);

        JButton add = new JButton("Add");
        add.setBackground(Color.WHITE);

        JButton edit = new JButton("Edit");
        edit.setBackground(Color.ORANGE);

        JButton delete = new JButton("Delete");
        delete.setBackground(Color.RED);

        top.add(new JLabel("Title")); top.add(t);
        top.add(new JLabel("Author")); top.add(a);
        top.add(new JLabel("Section")); top.add(s);
        top.add(new JLabel("Qty")); top.add(q);
        top.add(add); top.add(edit); top.add(delete);

        add(top, BorderLayout.NORTH);

        // ===== TABLES =====
        bookModel = new DefaultTableModel(
                new String[]{"Title","Author","Section","Qty"},0);

        JTable bookTable = new JTable(bookModel);

        recordModel = new DefaultTableModel(
                new String[]{"Name","Book","Borrow","Due"},0);

        JTable recordTable = new JTable(recordModel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(bookTable),
                new JScrollPane(recordTable));

        add(split, BorderLayout.CENTER);

        // ===== BOTTOM =====
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(60,60,60));

        JTextField name = new JTextField(8);

        JButton borrow = new JButton("Borrow");
        borrow.setBackground(Color.CYAN);

        JButton ret = new JButton("Return");
        ret.setBackground(Color.PINK);

        bottom.add(new JLabel("Name"));
        bottom.add(name);
        bottom.add(borrow);
        bottom.add(ret);

        add(bottom, BorderLayout.SOUTH);

        // ===== ADD =====
        add.addActionListener(e -> {
            try {
                books.add(new Book(
                        t.getText(),
                        a.getText(),
                        s.getText(),
                        Integer.parseInt(q.getText())
                ));
                refresh();

                // clear fields
                t.setText(""); a.setText("");
                s.setText(""); q.setText("");

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Invalid input");
            }
        });

        // ===== EDIT =====
        edit.addActionListener(e -> {
            int i = bookTable.getSelectedRow();

            if(i >= 0){
                books.get(i).title = t.getText();
                books.get(i).author = a.getText();
                books.get(i).section = s.getText();
                books.get(i).quantity = Integer.parseInt(q.getText());
                refresh();
            }
        });

        // ===== DELETE =====
        delete.addActionListener(e -> {
            int i = bookTable.getSelectedRow();
            if(i >= 0){
                books.remove(i);
                refresh();
            }
        });

        // ===== AUTO FILL =====
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            int i = bookTable.getSelectedRow();

            if(i >= 0){
                t.setText(books.get(i).title);
                a.setText(books.get(i).author);
                s.setText(books.get(i).section);
                q.setText(String.valueOf(books.get(i).quantity));
            }
        });

        // ===== BORROW =====
        borrow.addActionListener(e -> {
            int i = bookTable.getSelectedRow();

            if(i>=0 && books.get(i).quantity>0){

                books.get(i).quantity--;

                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                java.time.LocalDateTime due = now.plusDays(7);

                records.add(new BorrowRecord(
                        name.getText(),
                        books.get(i).title,
                        now.toString(),
                        due.toString()
                ));

                refresh();
            }
        });

        // ===== RETURN =====
        ret.addActionListener(e -> {
            int i = recordTable.getSelectedRow();

            if(i>=0){
                String bookName = records.get(i).book;

                for(Book b: books){
                    if(b.title.equals(bookName)){
                        b.quantity++;
                        break;
                    }
                }

                records.remove(i);
                refresh();
            }
        });

        // ===== OVERDUE ALERT =====
        new Thread(() -> {
            try{
                while(true){
                    Thread.sleep(10000);

                    java.time.LocalDateTime now = java.time.LocalDateTime.now();

                    for(BorrowRecord r : records){
                        if(now.isAfter(java.time.LocalDateTime.parse(r.dueDate))){
                            JOptionPane.showMessageDialog(this,
                                    "Overdue!\n"+r.name+" has not returned "+r.book);
                            break;
                        }
                    }
                }
            }catch(Exception ignored){}
        }).start();

        setVisible(true);
    }

    void refresh(){
        bookModel.setRowCount(0);
        recordModel.setRowCount(0);

        for(Book b: books){
            bookModel.addRow(new Object[]{
                    b.title,b.author,b.section,b.quantity
            });
        }

        for(BorrowRecord r: records){
            recordModel.addRow(new Object[]{
                    r.name,r.book,r.borrowDate,r.dueDate
            });
        }
    }
}