/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package application.views;

import application.dao.AlternatifDao;
import application.dao.CriteriaDao;
import application.dao.KaryawanDao;
import application.dao.SubCriteriaDao;
import application.daoimpl.AlternatifDaoImpl;
import application.daoimpl.CriteriaDaoImpl;
import application.daoimpl.KaryawanDaoImpl;
import application.daoimpl.SubCriteriaDaoImpl;
import application.models.AlternatifModel;
import application.models.CriteriaModel;
import application.models.KaryawanModel;
import application.models.SubCriteriaModel;
import application.models.UserModel;
import application.utils.ComboBoxItem;
import application.utils.DatabaseUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mhdja
 */
public class AlternativeView extends javax.swing.JFrame {
    // Declare form components
    public final CriteriaDao criteriaDao;
    public final SubCriteriaDao subCriteriaDao;
    public final KaryawanDao karyawanDao;
    public final AlternatifDao alternatifDao;
    private JLabel labelContent;
    
    public void getAllData() {
        List<AlternatifModel> alternatifList = alternatifDao.findAll(); // Mengambil data dari metode findAll()

        // Membuat model untuk jTable1 dengan kolom-kolom yang sesuai
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Karyawan", "Nama Kriteria", "Bobot Alternatif"}); // Menentukan nama kolom

        // Mengisi model dengan data dari alternatifList
        for (AlternatifModel alternatif : alternatifList) {
            model.addRow(new Object[]{
                alternatif.getNameAlternatif(), // Nama Pelanggan
                alternatif.getNameKriteria(),   // Nama Kriteria
                alternatif.getBobotAlternatif(), // Bobot Alternatif
            });
        }

        // Set model ke jTable1
        jTable2.setModel(model);
    }

    /**
     * Creates new form AlternativeView
     */
    public AlternativeView() {
        this.criteriaDao = new CriteriaDaoImpl();
        this.subCriteriaDao = new SubCriteriaDaoImpl();
        this.karyawanDao = new KaryawanDaoImpl();
        this.alternatifDao = new AlternatifDaoImpl();
                
        initComponents();
        
        getAllData();
        
        List<CriteriaModel> criteriaList = criteriaDao.findAll();
        List<JComboBox<ComboBoxItem>> comboBoxes = new ArrayList<>();
        
        jPanel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        jPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Pelanggan ===
        JLabel pelangganLabel = new JLabel("Karyawan: ");
        pelangganLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pelangganLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JComboBox<ComboBoxItem> pelangganComboBox = new JComboBox<>();
        pelangganComboBox.setMaximumSize(new Dimension(300, 30));
        pelangganComboBox.setFont(new Font("Arial", Font.PLAIN, 12));

        List<KaryawanModel> pelangganList = karyawanDao.findAll();
        pelangganComboBox.addItem(new ComboBoxItem("Pilih Karyawan", -1));
        for (KaryawanModel pelanggan : pelangganList) {
            pelangganComboBox.addItem(new ComboBoxItem(pelanggan.getName(), pelanggan.getId()));
        }

        // Tambahkan Pelanggan di baris pertama (di atas semua)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        jPanel1.add(pelangganLabel, gbc);

        gbc.gridy++;
        jPanel1.add(pelangganComboBox, gbc);

        // Reset posisi awal untuk kriteria
        gbc.gridwidth = 1;
        int maxRow = 6;
        int row = 0;
        int col = 0;
        int maxUsedRow = 0;

        List<AlternatifModel> alternatifList = new ArrayList<>();

        // === Kriteria dan Subkriteria ===
          for (CriteriaModel criteria : criteriaList) {
              JLabel label = new JLabel(criteria.getName() + ": ");
              label.setFont(new Font("Arial", Font.BOLD, 14));

              JComboBox<ComboBoxItem> comboBox = new JComboBox<>();
              comboBox.setMaximumSize(new Dimension(300, 30));
              comboBox.setFont(new Font("Arial", Font.PLAIN, 12));

              List<SubCriteriaModel> subCriteriaList = subCriteriaDao.findAllByCriteriaId(criteria.getId());
              for (SubCriteriaModel subCriteria : subCriteriaList) {
                  comboBox.addItem(new ComboBoxItem(subCriteria.getDeskripsi(), subCriteria.getId()));
              }

              comboBoxes.add(comboBox);

              gbc.gridx = col * 2;
              gbc.gridy = row + 2;
              jPanel1.add(label, gbc);

              gbc.gridx = col * 2 + 1;
              jPanel1.add(comboBox, gbc);

              maxUsedRow = Math.max(maxUsedRow, gbc.gridy);

              row++;
              if (row >= maxRow) {
                  row = 0;
                  col++;
              }
          }

        // === Tombol ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton simpanButton = new JButton("Simpan");
        JButton editButton = new JButton("Ubah");
        JButton hapusButton = new JButton("Hapus");

        simpanButton.addActionListener(e -> {
            ComboBoxItem selectedPelanggan = (ComboBoxItem) pelangganComboBox.getSelectedItem();
            if (selectedPelanggan == null || selectedPelanggan.getId() == -1) {
                JOptionPane.showMessageDialog(this, "Pilih karyawan terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            alternatifList.clear();

            boolean semuaTerisi = true;
            for (int i = 0; i < criteriaList.size(); i++) {
                CriteriaModel criteria = criteriaList.get(i);
                JComboBox<ComboBoxItem> comboBox = comboBoxes.get(i);
                ComboBoxItem selectedSubKriteria = (ComboBoxItem) comboBox.getSelectedItem();

                if (selectedSubKriteria == null) {
                    semuaTerisi = false;
                    break;
                }

                alternatifList.add(new AlternatifModel(selectedPelanggan.getId(), selectedSubKriteria.getId()));
            }

            if (!semuaTerisi) {
                JOptionPane.showMessageDialog(this, "Semua kriteria harus dipilih.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int rowsInserted = alternatifDao.create(alternatifList);
            getAllData();
            JOptionPane.showMessageDialog(this, rowsInserted + " data berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        });

        // ✅ Tombol Ubah
          editButton.addActionListener(e -> {
              ComboBoxItem selectedPelanggan = (ComboBoxItem) pelangganComboBox.getSelectedItem();
              if (selectedPelanggan == null || selectedPelanggan.getId() == -1) {
                  JOptionPane.showMessageDialog(this, "Pilih karyawan terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
              }

              alternatifList.clear();
              boolean semuaTerisi = true;

              for (int i = 0; i < criteriaList.size(); i++) {
                  JComboBox<ComboBoxItem> comboBox = comboBoxes.get(i);
                  ComboBoxItem selectedSubKriteria = (ComboBoxItem) comboBox.getSelectedItem();

                  if (selectedSubKriteria == null) {
                      semuaTerisi = false;
                      break;
                  }

                  alternatifList.add(new AlternatifModel(selectedPelanggan.getId(), selectedSubKriteria.getId()));
              }

              if (!semuaTerisi) {
                  JOptionPane.showMessageDialog(this, "Semua kriteria harus dipilih.", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
              }

              // Hapus dulu data lama, lalu simpan ulang
              alternatifDao.deleteBulkByKaryawan(selectedPelanggan.getId());
              int rowsUpdated = alternatifDao.create(alternatifList);
              JOptionPane.showMessageDialog(this, rowsUpdated + " data berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
              getAllData();
          });

          // ✅ Tombol Hapus
          hapusButton.addActionListener(e -> {
              ComboBoxItem selectedPelanggan = (ComboBoxItem) pelangganComboBox.getSelectedItem();
              if (selectedPelanggan == null || selectedPelanggan.getId() == -1) {
                  JOptionPane.showMessageDialog(this, "Pilih karyawan terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
              }

              int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus semua data alternatif untuk karyawan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
              if (confirm == JOptionPane.YES_OPTION) {
                  int rowsDeleted = alternatifDao.deleteBulkByKaryawan(selectedPelanggan.getId());
                  JOptionPane.showMessageDialog(this, rowsDeleted + " data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                  getAllData();
              }
          });


       buttonPanel.add(simpanButton);
       buttonPanel.add(editButton);
       buttonPanel.add(hapusButton);


       gbc.gridx = 0;
       gbc.gridy = maxUsedRow + 1;
       gbc.gridwidth = 4;
       gbc.anchor = GridBagConstraints.WEST;
       jPanel1.add(buttonPanel, gbc);
    }
    
    public void start() {
        JFrame frame = new AlternativeView();

        // Set ukuran awal saat pertama kali dibuka
        frame.setSize(1024, 768); 

        // Posisikan ke tengah layar
        frame.setLocationRelativeTo(null);

        // Jangan langsung keluar saat klik close
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.setResizable(false);

        // Listener untuk konfirmasi keluar
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame)e.getSource();

                int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to exit the application?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION){
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
        });

        // Tampilkan frame
        frame.setVisible(true);
    }
    
//    private void initializeComponents() {
//        // Create the sidebar panel and set layout
//        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
//        formPanel.setPreferredSize(new Dimension(250, getHeight())); // Lebar tetap
//        formPanel.setBackground(Color.LIGHT_GRAY);
//
//        // Tambahkan scroll
//        JScrollPane scrollPane = new JScrollPane(formPanel);
//        scrollPane.setPreferredSize(new Dimension(250, getHeight()));
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//        // Panel wrapper untuk membuat scrollPane berada di tengah
//        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        wrapperPanel.setBackground(Color.WHITE); // Bisa disesuaikan
//        wrapperPanel.add(scrollPane);
//
//        // Ambil semua kriteria
//        List<CriteriaModel> criteriaList = criteriaDao.findAll();
//        for (CriteriaModel criteria : criteriaList) {
//            JLabel label = new JLabel(criteria.getName() + ": ");
//            label.setFont(new Font("Arial", Font.BOLD, 14));
//            label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
//
//            JComboBox<ComboBoxItem> comboBox = new JComboBox<>();
//            comboBox.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
//            comboBox.setPreferredSize(new Dimension(200, 30));
//            comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
//
//            List<SubCriteriaModel> subCriteriaList = subCriteriaDao.findAllByCriteriaId(criteria.getId());
//            for (SubCriteriaModel subCriteria : subCriteriaList) {
//                comboBox.addItem(new ComboBoxItem(subCriteria.getDeskripsi(), subCriteria.getId()));
//            }
//
//            comboBox.addActionListener(e -> {
//                ComboBoxItem selectedItem = (ComboBoxItem) comboBox.getSelectedItem();
//                if (selectedItem != null) {
//                    int selectedId = selectedItem.getId();
//                    System.out.println("Selected SubCriteria ID: " + selectedId);
//                }
//            });
//
//            formPanel.add(label);
//            formPanel.add(comboBox);
//        }
//
//        // Panel utama
//        mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setBackground(Color.WHITE);
//        mainPanel.add(wrapperPanel, BorderLayout.CENTER); // Tambahkan ke tengah
//
//
//        // Set default close operation and frame size
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setTitle("Alternative View with Sidebar");
//        setSize(800, 600);  // Adjust size for a better layout
//        setLocationRelativeTo(null);  // Center the window on the screen
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA ALTERNATIF");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/pelni-200.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/back-gpt-70.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.dispose();
        new MenuView().start();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        jLabel6.setOpaque(true);
        jLabel6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel6.setBackground(new Color(70, 130, 180)); // Warna merah elegan
        jLabel6.setForeground(Color.WHITE); // Teks biar kontras
        jLabel6.setFont(jLabel6.getFont().deriveFont(Font.BOLD, 14f));
        jLabel6.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        jLabel6.setBackground(Color.WHITE); // Balik ke putih
        jLabel6.setForeground(Color.BLACK);
        jLabel6.setFont(jLabel6.getFont().deriveFont(Font.PLAIN, 12f));
        jLabel6.setBorder(null);
    }//GEN-LAST:event_jLabel6MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlternativeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlternativeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlternativeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlternativeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlternativeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
