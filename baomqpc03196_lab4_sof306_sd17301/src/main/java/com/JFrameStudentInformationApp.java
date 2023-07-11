package com;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.bean.Student;
import com.bean.StudentMap;
import com.dao.StudentDAO;

public class JFrameStudentInformationApp extends JFrame {

    StudentDAO dao = new StudentDAO();

    StudentMap items;
    String key = null;

    // Khai báo biến tương ứng với các tên của JTextField
    JTextField txtEmail;
    JTextField txtFullname;
    JRadioButton rdoMale;
    JRadioButton rdoFemale;
    JTextField txtMarks;
    JComboBox<String> cbbCountry;

    JButton btnCreate;
    JButton btnUpdate;
    JButton btnDelete;
    JButton btnReset;

    private JTable tblStudent;
    private DefaultTableModel tableModel;

    private JTabbedPane tabbedPane;
    private JPanel tab1;
    private JPanel tab2;

    private void initEvents() {
        // Thêm MouseListener vào tblStudent
        tblStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tblStudentMouseClick(evt);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Thêm code xử lý khi cửa sổ được mở
                load();
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCreateActionPerformed(e);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnUpdateActionPerformed(e);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDeleteActionPerformed(e);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnResetActionPerform(e);
            }
        });
    }

    public JFrameStudentInformationApp() {
        setTitle("Student Information App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Tạo panel chứa label và tab pane
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createLabel(), BorderLayout.NORTH); // Thêm label vào vị trí NORTH (phía trên)
        mainPanel.add(createTabPane(), BorderLayout.CENTER); // Thêm tab pane vào vị trí CENTER (giữa)

        add(mainPanel);

        initEvents();
    }

    void reset() {
        txtEmail.setText("");
        txtFullname.setText("");
        txtMarks.setText("");
        rdoMale.setSelected(true);
        rdoFemale.setSelected(false);
        cbbCountry.setSelectedIndex(0);
        this.key = null;
    }

    private void btnResetActionPerform(java.awt.event.ActionEvent evt) {
        this.reset();
    }

    void delete() {
        if (this.key == null) {
            return;
        }
        try {
            JOptionPane.showMessageDialog(this, this.key);
            dao.delete(this.key);
            this.items.remove(this.key);
            if (this.items == null) {
                return;
            }
            this.reset();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa dữ liệu");
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        this.delete();
        this.reset();
        this.load();
    }

    void update() {
        try {
            Student student = this.getForm();
            dao.update(this.key, student);
            this.items.put(this.key, student);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhất dữ liệu");
        }
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        this.update();
        this.load();
    }

    void create() {
        try {
            Student student = this.getForm();
            this.key = dao.create(student);
            this.load();
            this.items.put(this.key, student);
            JOptionPane.showMessageDialog(this, "Tạo mới thành công");
            tabbedPane.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tạo mới thất bại");
        }
    }

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        this.create();
        this.reset();
        this.load();
    }

    void edit(String key) {
        try {
            Student student = dao.findByKey(key);
            this.key = key;
            this.setForm(student);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi edit");
        }
    }

    private void tblStudentMouseClick(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int row = tblStudent.getSelectedRow();
            String key = (String) items.keySet().toArray()[row];
            this.edit(key);
            tabbedPane.setSelectedIndex(1);
        }
    }

    void load() {
        try {
            this.items = dao.findAll();
            this.fillTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }
    }

    Student getForm() {
        Student student = new Student();
        student.setEmail(txtEmail.getText());
        student.setFullname(txtFullname.getText());
        student.setMarks(Double.parseDouble(txtMarks.getText()));
        student.setGender(rdoMale.isSelected());
        student.setCountry(cbbCountry.getSelectedItem().toString());
        return student;
    }

    void setForm(Student form) {
        txtEmail.setText(form.getEmail());
        txtFullname.setText(form.getFullname());
        txtMarks.setText(form.getMarks().toString());
        rdoMale.setSelected(form.getGender());
        rdoFemale.setSelected(!form.getGender());
        cbbCountry.setSelectedItem(form.getCountry());
    }

    void fillTable() {
        tableModel = (DefaultTableModel) tblStudent.getModel();
        tableModel.setRowCount(0);
        if (items == null) {
            return;
        }
        items.forEach((key, student) -> {
            tableModel.addRow(student.getArray());
        });
    }

    public JLabel createLabel() {
        // Tạo label
        JLabel titleLabel = new JLabel("Student Information");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Đặt khoảng cách với phía trên
        int topPadding = 10;
        int leftPadding = 0;
        int bottomPadding = 0;
        int rightPadding = 0;
        titleLabel.setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));

        return titleLabel;
    }

    public JScrollPane createTable(String... columnHeaders) {

        // Khởi tạo table model
        tableModel = new DefaultTableModel(columnHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tạo JTable dựa trên table model
        tblStudent = new JTable(tableModel);

        // Vô hiệu hóa việc chỉnh sửa trực tiếp trên các ô của bảng
        for (int i = 0; i < tblStudent.getColumnCount(); i++) {
            TableColumn column = tblStudent.getColumnModel().getColumn(i);
            column.setCellEditor(null);
        }

        // Tạo JScrollPane để thêm JTable vào
        JScrollPane scrollPane = new JScrollPane(tblStudent);

        return scrollPane;
    }

    public void createTabPaneList() {
        // Tạo panel cho tab 1
        tab1 = new JPanel(new BorderLayout());
        JScrollPane scrollPane = createTable("Email Address", "Fullname", "Gender", "Marks", "Country");
        tab1.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("List", tab1);
    }

    public void createTabPaneEdit() {
        // Tạo panel cho tab "Edit"
        tab2 = new JPanel(new GridBagLayout());
        tabbedPane.addTab("Edit", tab2);

        // Tạo form dựa trên header của bảng
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần

        String[] textFieldNames = { "txtEmail", "txtFullname", "rdoMale", "rdoFemale", "txtMarks", "cbbCountry" };
        int textFieldIndex = 0;
        int y = 0; // Tọa độ hàng ban đầu

        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            String columnHeader = tableModel.getColumnName(i);
            JLabel label = new JLabel(columnHeader);
            JComponent component = new JComponent() {

            };

            if (columnHeader.equals("Gender")) {
                // Tạo radio button cho Gender
                JPanel radioPanel = new JPanel();
                ButtonGroup genderGroup = new ButtonGroup();

                JRadioButton maleRadioButton = new JRadioButton("Male");
                maleRadioButton.setName(textFieldNames[textFieldIndex]);
                textFieldIndex++;
                // Gán giá trị từ JTextField vào biến tương ứng
                if (maleRadioButton.getName().equals("rdoMale")) {
                    rdoMale = maleRadioButton;
                    rdoMale.setSelected(true);
                    maleRadioButton.setActionCommand("Male");
                    genderGroup.add(rdoMale);
                    radioPanel.add(rdoMale);
                }

                JRadioButton femaleRadioButton = new JRadioButton("Female");
                femaleRadioButton.setName(textFieldNames[textFieldIndex]);
                textFieldIndex++;
                // Gán giá trị từ JTextField vào biến tương ứng
                if (femaleRadioButton.getName().equals("rdoFemale")) {
                    rdoFemale = femaleRadioButton;
                    femaleRadioButton.setActionCommand("Female");
                    genderGroup.add(rdoFemale);
                    radioPanel.add(rdoFemale);
                }

                component = radioPanel;
            } else if (columnHeader.equals("Country")) {
                // Tạo combo box cho Country
                String[] countryOptions = { "VN", "US" };
                JComboBox<String> countryComboBox = new JComboBox<>(countryOptions);
                countryComboBox.setName(textFieldNames[textFieldIndex]);
                textFieldIndex++;
                // Gán giá trị từ JTextField vào biến tương ứng
                if (countryComboBox.getName().equals("cbbCountry")) {
                    cbbCountry = countryComboBox;
                }
                component = cbbCountry;
            } else {
                JTextField textField = new JTextField(20);
                textField.setName(textFieldNames[textFieldIndex]);
                // Gán giá trị từ JTextField vào biến tương ứng
                if (textField.getName().equals("txtEmail")) {
                    txtEmail = textField;
                    component = txtEmail;
                } else if (textField.getName().equals("txtFullname")) {
                    txtFullname = textField;
                    component = txtFullname;
                } else if (textField.getName().equals("txtMarks")) {
                    txtMarks = textField;
                    component = txtMarks;
                }
                textFieldIndex++;
            }

            // Kiểm tra xem có tên còn lại trong mảng không
            if (textFieldIndex < textFieldNames.length - 1) {

            }

            // Thiết lập chiều dài và chiều rộng cho label và component
            label.setPreferredSize(new Dimension(100, 30));
            component.setPreferredSize(new Dimension(200, 30));

            gbc.gridx = 0;
            gbc.gridy = y;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            tab2.add(label, gbc);

            gbc.gridx = 1;
            gbc.gridy = y;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            tab2.add(component, gbc);

            y++;
        }

        // Thêm nút "Create" vào form
        btnCreate = new JButton("Create");
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 0, 10);
        gbc.weighty = 1.0;
        tab2.add(btnCreate, gbc);

        // Thêm các button mới vào form
        btnUpdate = new JButton("Update");
        gbc.gridx = 0;
        gbc.gridy = y + 1;
        gbc.gridwidth = 1;
        tab2.add(btnUpdate, gbc);

        btnDelete = new JButton("Delete");
        gbc.gridx = 1;
        gbc.gridy = y + 1;
        gbc.gridwidth = 1;
        tab2.add(btnDelete, gbc);

        btnReset = new JButton("Reset");
        gbc.gridx = 2;
        gbc.gridy = y + 1;
        gbc.gridwidth = 1;
        tab2.add(btnReset, gbc);

        // Thêm thành phần rỗng để đẩy các thành phần lên phía trên
        gbc.gridx = 0;
        gbc.gridy = y + 2;
        gbc.weighty = 1.0;
        JLabel emptyLabel = new JLabel();
        tab2.add(emptyLabel, gbc);
    }

    public JTabbedPane createTabPane() {

        // Tạo JTabbedPane
        tabbedPane = new JTabbedPane();

        createTabPaneList();
        createTabPaneEdit();

        return tabbedPane;
    }

    public static void main(String[] args) {
        JFrameStudentInformationApp frame = new JFrameStudentInformationApp();
        frame.setVisible(true);
    }
}