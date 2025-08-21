package application.daoimpl;

import application.dao.KaryawanDao;
import application.models.KaryawanModel;
import application.utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhdja
 */
public class KaryawanDaoImpl implements KaryawanDao {
    
    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;
    
    public KaryawanDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public List<KaryawanModel> findAll() {
        List<KaryawanModel> listDataAll = new ArrayList<>();

        try {
            query = "SELECT * FROM employees";
            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                KaryawanModel listData = new KaryawanModel();
                listData.setId(resultSet.getInt("id"));
                listData.setNik(resultSet.getString("nik"));   // ✅ ambil nik
                listData.setName(resultSet.getString("name"));
                listData.setJabatan(resultSet.getString("position"));

                listDataAll.add(listData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return listDataAll;
    }

    @Override
    public int create(KaryawanModel karyawan) {
        try {
            query = "INSERT INTO employees(nik, name, position) VALUES (?, ?, ?)";
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, karyawan.getNik());      // ✅ simpan nik
            pstmt.setString(2, karyawan.getName());
            pstmt.setString(3, karyawan.getJabatan());

            // Log sebelum insert
            System.out.println("=== Menyimpan Data Karyawan ===");
            System.out.println("NIK : " + karyawan.getNik());
            System.out.println("Nama: " + karyawan.getName());

            int result = pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                int insertedId = resultSet.getInt(1);
                System.out.println("Data karyawan berhasil disimpan. ID: " + insertedId);
                return insertedId;
            }

            System.out.println("Data karyawan disimpan tanpa ID yang dihasilkan.");
            return result;
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan data karyawan: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int update(KaryawanModel karyawan) {
        int result = 0;
        try {
            // ✅ update termasuk nik
            query = "UPDATE employees SET nik = ?, name = ?, position = ? WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, karyawan.getNik());
            pstmt.setString(2, karyawan.getName());
            pstmt.setString(3, karyawan.getJabatan());
            pstmt.setInt(4, karyawan.getId());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }

        return result;
    }

    @Override
    public int deleteKaryawan(int id) {
        int result = 0;
        try {
            query = "DELETE FROM employees WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }

        return result;
    }
    
    private void closeStatement() {
        try {
            if(pstmt != null){
                pstmt.close();
                pstmt = null;
            }
            if(resultSet != null){
                resultSet.close();
                resultSet = null;
            }   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
