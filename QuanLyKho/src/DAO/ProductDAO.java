/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ProductDTO;
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

// Đối tượng truy cập dữ liệu cho Sản phẩm, Xuất kho, nhập kho, tồn kho
public class ProductDAO {
    Connection conn = null;
    PreparedStatement prepStatement = null;
    PreparedStatement prepStatement2 = null;
    Statement statement = null;
    Statement statement2 = null;
    ResultSet resultSet = null;
    
    public ProductDAO(){
        try{
            conn = new ConnectionSQL().getConn();
            statement = conn.createStatement();
            statement2 = conn.createStatement();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    String suppCode;
    public String getSuppCode(String suppName) {
        try {
            String query = "SELECT SupplierCode FROM Supplier WHERE FullName='" +suppName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppCode = resultSet.getString("SupplierCode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppCode;
    }
    
    String prodCode;
    public String getProdCode(String prodName) {
        try {
            String query = "SELECT ProductCode FROM Product WHERE ProductName='" +prodName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppCode = resultSet.getString("ProductCode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prodCode;
    }
    
    String custCode;
    public String getCustCode(String custName) {
        try {
            String query = "SELECT CustomerCode FROM Supplier WHERE FullName='" +custName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppCode = resultSet.getString("CustomerCode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return custCode;
    }
    
    
    // Phương thức kiểm tra tình trạng còn hàng trong kho
    boolean flag = false;
    public boolean checkStock(String prodCode) {
        try {
            String query = "SELECT * FROM CurrentStock WHERE ProductCode='" +prodCode+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    // Phương thức thêm mới sản phẩm
    public void addProductDAO(ProductDTO productDTO) {
        try {
            String query = "SELECT * FROM Product WHERE ProductName='"
                    + productDTO.getProdName()
                    + "' AND Brand='"
                    + productDTO.getBrand()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Sản phẩm đã được thêm vào.");
            else
                addProdDAO(productDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addProdDAO(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO Product ( ProductCode, ProductName, Brand) "
                    + "VALUES(?,?,?)";
            prepStatement = (PreparedStatement) conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getProdCode());
            prepStatement.setString(2, productDTO.getProdName());
            prepStatement.setString(3, productDTO.getBrand());

            String query2 = "INSERT INTO CurrentStock VALUES(?,?)";
            prepStatement2 = conn.prepareStatement(query2);
            prepStatement2.setString(1, productDTO.getProdCode());
            prepStatement2.setInt(2, productDTO.getQuantity());

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog(null, "Đã thêm sản phẩm và sẵn sàng để bán.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    // Phương thức nhập hàng
    public void addInputInfoDAO(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO InputInfo(SupplierCode, ProductCode, Quantity, InputPrice) VALUES(?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getSuppCode());
            prepStatement.setString(2, productDTO.getProdCode());
            prepStatement.setInt(3, productDTO.getQuantity());
            prepStatement.setDouble(4, productDTO.getInputPrice());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Nhập hàng thành công.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String prodCode = productDTO.getProdCode();
        if(checkStock(prodCode)) {
            try {
                String query = "UPDATE CurrentStock SET Quantity = Quantity + ? WHERE ProductCode = ? ";
                prepStatement = conn.prepareStatement(query);
                prepStatement.setInt(1, productDTO.getQuantity());
                prepStatement.setString(2, prodCode);

                prepStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (!checkStock(prodCode)) {
            try {
                String query = "INSERT INTO CurrentStock (ProductCode, Quantity) VALUES(?,?)";
                prepStatement = (PreparedStatement) conn.prepareStatement(query);
                prepStatement.setString(1, productDTO.getProdCode());
                prepStatement.setInt(2, productDTO.getQuantity());

                prepStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }   
        }
        deleteStock();
    }
    
    // Phương thức xuất kho
    public void outputProductDAO(ProductDTO productDTO) {
        int quantity = 0;
        String prodCode = null;
        try {
            String query = "SELECT * FROM CurrentStock WHERE ProductCode='" +productDTO.getProdCode()+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                prodCode = resultSet.getString("ProductCode");
                quantity = resultSet.getInt("Quantity");
            }
            if (productDTO.getQuantity()>quantity)
                JOptionPane.showMessageDialog(null, "Không đủ hàng cho sản phẩm này.");
            else if (productDTO.getQuantity()<=0)
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng hợp lệ");
            else {
                String stockQuery = "UPDATE CurrentStock SET Quantity = Quantity -'"
                        +productDTO.getQuantity()
                        +"' WHERE ProductCode='"
                        +productDTO.getProdCode()
                        +"'";
                String salesQuery = "INSERT INTO OutputInfo(ProductCode,CustomerCode,Quantity,OutputPrice)" +
                        "VALUES('"+productDTO.getProdCode()+"','"+productDTO.getCustCode()+
                        "','"+productDTO.getQuantity()+"','"+productDTO.getOutputPrice()+"')";
                statement.executeUpdate(stockQuery);
                statement.executeUpdate(salesQuery);
                JOptionPane.showMessageDialog(null, "Xuất sản phẩm thành công.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    // Phương thức cập nhật sản phẩm
    public void editProdDAO(ProductDTO productDTO) {
        try {
            String query = "UPDATE Product SET ProductName = ?, Brand = ? WHERE ProductCode = ?";
            prepStatement = (PreparedStatement) conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getProdName());
            prepStatement.setString(2, productDTO.getBrand());
            prepStatement.setString(3, productDTO.getProdCode());

            String query2 = "UPDATE CurrentStock SET Quantity = ? WHERE ProductCode = ?";
            prepStatement2 = conn.prepareStatement(query2);
            prepStatement2.setInt(1, productDTO.getQuantity());
            prepStatement2.setString(2, productDTO.getProdCode());

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sản phẩm đã được cập nhật.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    public void deleteStock() {
        try {
            String query = "DELETE FROM CurrentStock WHERE ProductCode NOT IN(SELECT ProductCode FROM InputInfo)";
            String query2 = "DELETE FROM OutputInfo WHERE ProductCode NOT IN(SELECT Productcode FROM Product)";
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    // Phương thức xóa 1 sản phẩm 
    public void deleteProductDAO(String code) {
        try {
            String query = "DELETE FROM Product WHERE ProductCode = ?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, code);

            String query2 = "DELETE FROM CurrentStock WHERE ProductCode = ?";
            prepStatement2 = conn.prepareStatement(query2);
            prepStatement2.setString(1, code);

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công.");
        } catch (SQLException e){
            e.printStackTrace();
        }
        deleteStock();
    }
    
    
    
    
    
    // Truy xuất dữ liệu sản phẩm để hiện thị
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT ProductCode as MaSanPham, ProductName as TenSanPham, Brand as ThuongHieu FROM Product ORDER BY Id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    
    
    // Truy xuất dữ liệu bảng nhập kho
    public ResultSet getInputInfo() {
        try {
            String query = "SELECT InputInfo.Id, InputInfo.ProductCode as MaSanPham, ProductName as TenSanPham, "
                    + "SupplierCode as MaNCC ,Quantity as SoLuong, InputPrice as GiaNhap " +
                    "FROM InputInfo INNER JOIN Product " +
                    "ON Product.ProductCode = InputInfo.ProductCode ORDER BY InputInfo.Id;";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    // Truy xuất dữ liệu bảng tồn kho
    public ResultSet getCurrentStockInfo() {
        try {
            String query = """
                    SELECT CurrentStock.ProductCode as MaSanPham, Product.ProductName as TenSanPham,
                           CurrentStock.Quantity as SoLuong, InputInfo.InputPrice as GiaNhap
                    FROM CurrentStock 
                    INNER JOIN Product ON CurrentStock.ProductCode = Product.ProductCode
                    INNER JOIN InputInfo ON CurrentStock.ProductCode = InputInfo.ProductCode
                    
                    """;
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    
    
    // Truy xuất dữ liệu bảng xuất kho
    public ResultSet getOutputInfo() {
        try {
            String query = """
                    SELECT OutputInfo.Id,OutputInfo.CustomerCode as MaKhachHang, OutputInfo.ProductCode as MaSanPham, ProductName as TenSanPham,
                    OutputInfo.Quantity as SoLuong, OutputPrice as GiaXuat
                    FROM OutputInfo INNER JOIN Product
                    ON OutputInfo.ProductCode = Product.ProductCode;
                    """;
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    
    // Phương thức tìm kiếm sản phẩm
    public ResultSet getProductSearch(String text) {
        try {
            String query = "SELECT ProductCode, ProductName Brand FROM Product " +
                    "WHERE ProductCode LIKE '%"+text+"%' OR ProductName LIKE '%"+text+"%' OR Brand LIKE '%"+text+"%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    
    public ResultSet getProdName(String code) {
        try {
            String query = "SELECT ProductName FROM Product WHERE ProductCode='" +code+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }


    public String getCustName(int ID) {
        String name = null;
        try {
            String query = "SELECT FullName FROM Customer " +
                    "INNER JOIN SalesInfo ON Customer.CustomerCode = SalesInfo.CustomerCode " +
                    "WHERE Id='" +ID+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                name = resultSet.getString("FullName");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }
    
    
    // Phương thức hiện thị dữ liệu sản phẩm ở dạng bảng
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
