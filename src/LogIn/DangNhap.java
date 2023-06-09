package LogIn;

import ConnectDataBase.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhap {
    @FXML
    private Label MessageSignUp;

    @FXML
    private PasswordField Password;

    @FXML
    private Button SignUp;

    @FXML
    private TextField UserNameSignUp;

//    private int isCheckPermission(String username, String password){
//        int permission;
//        Connection connection = DBConnection.ConnectDB();
//        if (connection != null) {
//            DBConnection connectionDB = new DBConnection();
//            String query = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ? AND Quyen = ?";
//            PreparedStatement statement = connectionDB.prepareStatement(query);
//            try {
//                statement.setString(1, username);
//                statement.setString(2, password);
//                statement.setString(3, "Khách Hàng");
//                ResultSet resultSet = statement.executeQuery();
//                if(resultSet.next()){
//                    return permission=1;
//                }else {
//                    return permission=2;
//                }
////                return resultSet.next();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return false;
//    }
    public String checkUserRole(String username) {
        String role = null;
        Connection connection = DBConnection.ConnectDB();
        if (connection != null) {
            DBConnection connectionDB = new DBConnection();
            String query = "SELECT Quyen FROM TaiKhoan WHERE TenDangNhap = ?";
            PreparedStatement statement = connectionDB.prepareStatement(query);
            try {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    role = resultSet.getString("Quyen");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không thể kết nối tới cơ sở dữ liệu");
        }
        return role;
    }

    public void showAlertDialog(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void openFXML(String fxmlPath) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(DangNhap.class.getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void ClickSignUp(javafx.event.ActionEvent event) {
        String username = UserNameSignUp.getText();
        String password = Password.getText();

        Connection connection = DBConnection.ConnectDB();
        if(connection != null){
            DBConnection connectionDB = new DBConnection();
            String query = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
            PreparedStatement statement = connectionDB.prepareStatement(query);
            try {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String role = checkUserRole(username);
                    if (role != null) {
                        switch (role) {
                            case "Khách Hàng":
                                openFXML("/InterfaceFXML/KhachHang.fxml");
                                break;
                            case "Shipper":
                                openFXML("/InterfaceFXML/DonHang.fxml");
                                break;
                            case "Quản Lý":
                                openFXML("/InterfaceFXML/DanhGia.fxml");
//                                showAlertDialog("Lỗi", "Quyền không hợp lệ", Alert.AlertType.ERROR);
                                break;
                            default:
                                showAlertDialog("Lỗi", "Quyền không hợp lệ", Alert.AlertType.ERROR);
                                break;
                        }
                    } else {
                        showAlertDialog("Lỗi", "Tài khoản không thuộc quyền nào!", Alert.AlertType.ERROR);
                    }
                } else {
                    showAlertDialog("Lỗi", "Tài khoản hoặc mật khẩu không chính xác!", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không thể kết nối tới cơ sở dữ liệu");
        }
    }

}


