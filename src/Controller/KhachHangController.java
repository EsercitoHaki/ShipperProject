package Controller;

import ConnectDataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class KhachHangController implements Initializable {
    @FXML
    private TextField textDiaChiGui;

    @FXML
    private TextField textDiaChiNhan;

    @FXML
    private TextField textTenDonHang;

    @FXML
    private TextField textThuNhap;

    @FXML
    private TableView<KhachHang> KhachHang;

    @FXML
    private TableColumn<KhachHang, String> cDiaChiGuiKH;

    @FXML
    private TableColumn<KhachHang, String> cDiaChiNhanKH;

    @FXML
    private TableColumn<KhachHang, String> cLoaiDonHangKH;

    @FXML
    private TableColumn<KhachHang, String> cTenDonHangKH;

    @FXML
    private TableColumn<KhachHang, String> cThuNhapKH;

    @FXML
    private ComboBox<String> ComboBoxLoaiHang;

    @FXML
    private Label labelDiaChiGui;

    @FXML
    private Label labelDiaChiNhan;

    @FXML
    private Label labelLoaiHang;

    @FXML
    private Label labelMaDonHang;

    @FXML
    private Label labelTenDonHang;

    @FXML
    private Label labelTrangThaiDon;



    public ObservableList<KhachHang> DuLieuKhachHang() {
        ObservableList<KhachHang> CacKhachHang = FXCollections.observableArrayList();
        String sql = "SELECT TenDonHang, LoaiHang, DiaChiGui, DiaChiNhan, ThuNhap FROM DonHang";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            KhachHang khachHang;
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                khachHang = new KhachHang(result.getString("TenDonHang"),
                        result.getString("LoaiHang"),
                        result.getString("DiaChiGui"),
                        result.getString("DiaChiNhan"),
                        result.getString("ThuNhap")
                );
                CacKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CacKhachHang;
    }

    private ObservableList<KhachHang> DanhSachKhachHang;

    public void HienThiKhachHang() {
        DanhSachKhachHang = DuLieuKhachHang();
        cTenDonHangKH.setCellValueFactory(new PropertyValueFactory<>("TenDonHang"));
        cLoaiDonHangKH.setCellValueFactory(new PropertyValueFactory<>("LoaiHang"));
        cDiaChiGuiKH.setCellValueFactory(new PropertyValueFactory<>("DiaChiGui"));
        cDiaChiNhanKH.setCellValueFactory(new PropertyValueFactory<>("DiaChiNhan"));
        cThuNhapKH.setCellValueFactory(new PropertyValueFactory<>("ThuNhap"));
        KhachHang.setItems(DanhSachKhachHang);
    }

    public void ChonLoaiDonHang() {
        try {
            Connection connect = DBConnection.ConnectDB();
            String sql = "SELECT * FROM LoaiHang";
            PreparedStatement prepare = connect.prepareStatement(sql); // PrepareStatement là một đối tượng Java dùng để biểu diễn một câu lệnh SQL 'đã được biên dịch trước'. Một câu lệnh SQL được biên dịch trước và lưu trữ trong một đối tượng PrepareStatement. Đối tượng này có thể được sử dụng để thực thi câu lệnh nhiều lần một cách hiệu quả.
            ResultSet result = prepare.executeQuery();
            ObservableList<String> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(result.getString("LoaiHang"));
            }
            ComboBoxLoaiHang.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HienThiKhachHang();
        ChonLoaiDonHang();
    }

    public void hienThiThongTinDonHangKH() {
        KhachHang selectedDonHang = KhachHang.getSelectionModel().getSelectedItem();
        if (selectedDonHang != null) {
            String tenDonHang = selectedDonHang.getTenDonHang();
            String loaiHang = selectedDonHang.getLoaiHang();
            String diaChiGui = selectedDonHang.getDiaChiGui();
            String diaChiNhan = selectedDonHang.getDiaChiNhan();
            String thuNhap = selectedDonHang.getThuNhap();

            labelMaDonHang.setText("DH00*");
            labelTenDonHang.setText(tenDonHang);
            labelLoaiHang.setText(loaiHang);
            labelDiaChiGui.setText(diaChiGui);
            labelDiaChiNhan.setText(diaChiNhan);
            labelTrangThaiDon.setText("Đang Chờ");

            textTenDonHang.setText(tenDonHang);
            ComboBoxLoaiHang.setValue(loaiHang);
            textDiaChiGui.setText(diaChiGui);
            textDiaChiNhan.setText(diaChiNhan);
            textThuNhap.setText(thuNhap);
        }
    }



    public void SuaDonHang() throws SQLException {
        KhachHang selectedKhachHang = KhachHang.getSelectionModel().getSelectedItem();
        if (selectedKhachHang != null) {
            String tenDonHang = textTenDonHang.getText();
            String loaiHang = ComboBoxLoaiHang.getValue();
            String diaChiGui = textDiaChiGui.getText();
            String diaChiNhan = textDiaChiNhan.getText();
            String thuNhap = textThuNhap.getText();

            try {
                Connection connection = DBConnection.ConnectDB();
                String sql = "UPDATE DonHang SET TenDonHang=?, LoaiHang=?, DiaChiGui=?, DiaChiNhan=?, ThuNhap=? WHERE MaDH=?";

                PreparedStatement statement = connection.prepareStatement(sql);

                statement = connection.prepareStatement(sql);
                statement.setString(1, tenDonHang);
                statement.setString(2, loaiHang);
                statement.setString(3, diaChiGui);
                statement.setString(4, diaChiNhan);
                statement.setString(5, thuNhap);
                statement.setString(6, selectedKhachHang.getMaDH());
                statement.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.ERROR.INFORMATION);
                alert.setTitle("Thông Báo");
                alert.setHeaderText(null);
                alert.setContentText("Sửa đơn hàng thành công!");
                alert.showAndWait();

                HienThiKhachHang();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    public void ThemDonHang(ActionEvent event) throws SQLException {
        String tenDonHang = textTenDonHang.getText();
        String diaChiGui = textDiaChiGui.getText();
        String diaChiNhan = textDiaChiNhan.getText();
        String thuNhap = textThuNhap.getText();
        String loaiHang = ComboBoxLoaiHang.getValue();

        try {
        Connection connect = DBConnection.ConnectDB();
            String sqlQuery = "SELECT MAX(MaDH) AS CurrentMaDH FROM DonHang";
            PreparedStatement pstmt = connect.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            String lastMaDH = "";
            if (rs.next()) {
                lastMaDH = rs.getString("CurrentMaDH");
            }

            int number = Integer.parseInt(lastMaDH.substring(2)) + 1;
            String maDH = "DH" + String.format("%03d", number);

            String insertQuery = "INSERT INTO DonHang (MaDH, TenDonHang, LoaiHang, DiaChiGui, DiaChiNhan, ThuNhap, TrangThaiDon, MaKH) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, maDH);
            pstmt.setString(2, tenDonHang);
            pstmt.setString(3, loaiHang);
            pstmt.setString(4, diaChiGui);
            pstmt.setString(5, diaChiNhan);
            pstmt.setString(6, thuNhap);
            pstmt.setString(7, "Đang Chờ");
            pstmt.setString(8, "KH001");
            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.ERROR.INFORMATION);
            alert.setTitle("Thành Công");
            alert.setHeaderText(null);
            alert.setContentText("Thêm đơn hàng thành công!");
            alert.showAndWait();

            HienThiKhachHang();

            textTenDonHang.clear();
            textDiaChiGui.clear();
            textDiaChiNhan.clear();
            textThuNhap.clear();
            ComboBoxLoaiHang.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Thêm đơn hàng không thành công!");
                alert.showAndWait();
            }
    }
}

