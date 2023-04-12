/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.CustomerDTO;
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


// Đối tượng truy cập dữ liệu cho khách hàng
public class CustomerDAO {
    
    Connection conn = null;
    PreparedStatement prepStatement= null;
    Statement statement = null;
    ResultSet resultSet = null;

    public CustomerDAO() {
        try {
            conn = new ConnectionSQL().getConn();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức thêm mới khách hàng
    public void addCustomerDAO(CustomerDTO customerDTO) {
        try {
            String query = "SELECT * FROM Customer WHERE FullName='"
                    +customerDTO.getFullName()
                    + "' AND Address='"
                    +customerDTO.getAddress()
                    + "' AND Phone='"
                    +customerDTO.getPhone()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Khách hàng đã tồn tại.");
            else
                addFunction(customerDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addFunction(CustomerDTO customerDTO) {
        try {
            String query = "INSERT INTO Customer (CustomerCode, FullName, Address, Phone) VALUES(?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, customerDTO.getCustCode());
            prepStatement.setString(2, customerDTO.getFullName());
            prepStatement.setString(3, customerDTO.getAddress());
            prepStatement.setString(4, customerDTO.getPhone());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Khách hàng mới đã được thêm vào.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Phương thức chỉnh sửa khách hàng
    public  void editCustomerDAO(CustomerDTO customerDTO) {
        try {
            String query = "UPDATE Customer SET FullName=?, Address=?, Phone=? WHERE CustomerCode=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, customerDTO.getFullName());
            prepStatement.setString(2, customerDTO.getAddress());
            prepStatement.setString(3, customerDTO.getPhone());
            prepStatement.setString(4, customerDTO.getCustCode());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thông tin khách hàng đã được cập nhật");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức xóa khách hàng
    public void deleteCustomerDAO(String custCode) {
        try {
            String query = "DELETE FROM Customer WHERE CustomerCode='" +custCode+ "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Đã xóa khách hàng.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức truy xuất dữ liệu để hiện thị
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT CustomerCode as MaKhachHang, FullName as TenKhachHang, Address as DiaChi, Phone as SDT FROM Customer";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // 


    public ResultSet getCustName(String custCode) {
        try {
            String query = "SELECT * FROM Customer WHERE CustomerCode='" +custCode+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProdName(String prodCode) {
        try {
            String query = "SELECT ProductName, CurrentStock.Quantity FROM Product " +
                    "INNER JOIN CurrentStock ON Product.ProductCode = CurrentStock.ProductCode " +
                    "WHERE CurrentStock.ProductCode='" +prodCode+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Phương thức hiện thị dữ liệu ở dạng bảng
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
