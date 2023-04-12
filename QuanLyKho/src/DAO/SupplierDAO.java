/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.SupplierDTO;
import Database.ConnectionSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author sang
 */

// Đối tượng truy cập dữ liệu cho nhà cung cấp
public class SupplierDAO {
    
    Connection conn = null;
    Statement statement = null;
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;

    public SupplierDAO() {
        try {
            conn = new ConnectionSQL().getConn();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Phương thức thêm mới nhà cung cấp
    public void addSupplierDAO(SupplierDTO supplierDTO) {
        try {
            String query = "SELECT * FROM Supplier WHERE FullName ='"
                    +supplierDTO.getFullName()
                    + "' AND Address ='"
                    +supplierDTO.getAddress()
                    + "' AND Phone ='"
                    +supplierDTO.getPhone()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Nhà cung cấp này đã tồn tại.");
            else
                addFunction(supplierDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addFunction(SupplierDTO supplierDTO) {
        try {
            String query = "INSERT INTO Supplier (SupplierCode, FullName, Address, Phone) VALUES(?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, supplierDTO.getSuppCode());
            prepStatement.setString(2, supplierDTO.getFullName());
            prepStatement.setString(3, supplierDTO.getAddress());
            prepStatement.setString(4, supplierDTO.getPhone());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Phương thức chỉnh sửa nhà cung cấp
    public void editSupplierDAO(SupplierDTO supplierDTO) {
        try {
            String query = "UPDATE Supplier SET FullName = ?, Address = ?, Phone = ? WHERE SupplierCode = ?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, supplierDTO.getFullName());
            prepStatement.setString(2, supplierDTO.getAddress());
            prepStatement.setString(3, supplierDTO.getPhone());
            prepStatement.setString(4, supplierDTO.getSuppCode());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thông tin nhà cung cấp đã được cập nhật.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Phương thức xóa nhà cung cấp
    public void deleteSupplierDAO(String suppCode) {
        try {
            String query = "DELETE FROM Supplier WHERE SupplierCode='" +suppCode+ "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Xóa nhà cung cấp thành công.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Phương thức truy xuất dữ liệu NCC
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT SupplierCode as MaNhaCungCap, FullName as TenNhaCungCap, Address as DiaChi, Phone as SDT FROM Supplier";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    
    
    // Phương thức cập nhật combobox NCC
    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> suppNames = new Vector<>();
        while (resultSet.next()){
            suppNames.add(resultSet.getString("TenNhaCungCap"));
        }
        return new DefaultComboBoxModel<>(suppNames);
    }
    
    // Phương thức hiển thị dữ liệu truy xuất ở dạng bảng
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
    
}
