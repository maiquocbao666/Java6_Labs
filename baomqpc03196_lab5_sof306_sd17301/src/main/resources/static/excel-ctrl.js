var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http){

    $scope.import = function(){

        var input = document.getElementById('fileInput');
        var files = input.files; // Sử dụng thuộc tính files để lấy danh sách các tệp đã chọn
        if (files.length === 0) {
            console.log('Chưa chọn tệp để import.');
            return;
        }
        var file = files[0]; // Lấy tệp đầu tiên trong danh sách đã chọn

        var reader = new FileReader();
        reader.onloadend = async () => {
            var workbook = new ExcelJS.Workbook(); // chứa toàn bộ file excel
            await workbook.xlsx.load(reader.result);
            const worksheet = workbook.getWorksheet("Sheet1");
            worksheet.eachRow((row, index) => {
                if(index > 1){
                    let student = {
                        email: row.getCell(1).value,
                        fullname: row.getCell(2).value,
                        marks: +row.getCell(3).value,
                        gender: true && row.getCell(4).value,
                        country: row.getCell(5).value
                    }
                    var url = "http://localhost:8080/rest/students";
                    $http.post(url, student).then(resp => {
                        console.log("Success", resp.data);
                    }).catch(error => {
                        console.log("Error", error);
                    })
                }
            })
        }
        reader.readAsArrayBuffer(file);
    }

    $scope.export = function() {
        // Tạo một mảng chứa dữ liệu từ cơ sở dữ liệu hoặc lấy dữ liệu từ API.
        var dataToExport = [];
    
        // Thực hiện request đến API hoặc cơ sở dữ liệu để lấy dữ liệu cần xuất ra Excel
        var url = "http://localhost:8080/rest/students";
        $http.get(url).then(resp => {
            // Gán dữ liệu lấy được vào mảng dataToExport
            dataToExport = resp.data;
    
            // Tạo một file Excel và lưu dữ liệu xuống máy tính
            var workbook = new ExcelJS.Workbook();
            var worksheet = workbook.addWorksheet("Sheet1");
    
            // Định dạng tiêu đề bảng (header)
            var headerRow = worksheet.addRow(["Email", "Fullname", "Marks", "Gender", "Country"]);
            headerRow.font = { bold: true };
            headerRow.fill = {
                type: "pattern",
                pattern: "solid",
                fgColor: { argb: "FFFFFFFF" } // Màu nền đỏ cho tiêu đề
            };

            // Thêm các ô dữ liệu vào hàng tiêu đề và định dạng màu nền trắng cho các ô dữ liệu
            headerRow.eachCell(cell => {
                cell.fill = {
                    type: "pattern",
                    pattern: "solid",
                    fgColor: { argb: "ffa4ffa4" }, // Màu nền trắng cho các ô dữ liệu trong hàng tiêu đề
                };
                cell.border = { top: { style: "thin" }, bottom: { style: "thin" }, left: { style: "thin" }, right: { style: "thin" } };
            });
    
            // Thêm dữ liệu từ mảng dataToExport vào bảng Excel
            dataToExport.forEach(student => {
                var dataRow = worksheet.addRow([
                    student.email,
                    student.fullname,
                    student.marks,
                    student.gender,
                    student.country
                ]);
    
                // Định dạng các ô dữ liệu
                dataRow.alignment = { vertical: "middle", horizontal: "left" }; // Căn giữa dữ liệu trong ô
                dataRow.eachCell(cell => {
                    cell.border = { top: { style: "thin" }, bottom: { style: "thin" }, left: { style: "thin" }, right: { style: "thin" } }; // Định dạng đường viền cho ô
                });
    
                // Định dạng số cho cột điểm (Marks), ví dụ: "0.00" sẽ hiển thị số thập phân với 2 chữ số sau dấu thập phân.
                dataRow.getCell(3).numFmt = "0.00";
            });
    
            // Tính toán kích thước các cột và áp dụng định dạng tự động để giãn ra vừa với dữ liệu
            worksheet.columns.forEach(column => {
                var maxLength = 0;
                column.eachCell({ includeEmpty: true }, cell => {
                    var columnLength = cell.value ? cell.value.toString().length : 10;
                    if (columnLength > maxLength) {
                        maxLength = columnLength;
                    }
                });
                column.width = maxLength < 10 ? 10 : maxLength + 2; // Đảm bảo độ rộng tối thiểu là 10 và thêm một số padding
            });
    
            // Tạo một tệp Excel và tải xuống về máy tính
            workbook.xlsx.writeBuffer().then(function(buffer) {
                var data = new Blob([buffer], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
                var excelFileName = dataToExport.length === 0 ? "empty_students_data.xlsx" : "students_data.xlsx";
                saveAs(data, excelFileName);
            });
        }).catch(error => {
            console.error("Error while fetching data:", error);
        });
    };
})