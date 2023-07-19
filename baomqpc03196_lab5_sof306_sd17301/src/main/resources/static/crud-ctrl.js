let host = "http://localhost:8080/rest";
const app = angular.module("app", []);
app.controller("ctrl", function ($scope, $http) {
  $scope.form = {};
  $scope.items = [];

  alertSuccess = function (message) { 
    Toastify({
      text: message,
      duration: 3000,
      //destination: "https://github.com/apvarun/toastify-js",
      newWindow: true,
      //close: true,
      gravity: "top", // `top` or `bottom`
      position: "right", // `left`, `center` or `right`
      stopOnFocus: true, // Prevents dismissing of toast on hover
      style: {
        background: "#34c240",
        color: "white",
      },
      onClick: function(){} // Callback after click
    }).showToast();
  }

  alertWarning = function (message) { 
    Toastify({
      text: message,
      duration: 3000,
      //destination: "https://github.com/apvarun/toastify-js",
      newWindow: true,
      //close: true,
      gravity: "top", // `top` or `bottom`
      position: "right", // `left`, `center` or `right`
      stopOnFocus: true, // Prevents dismissing of toast on hover
      style: {
        background: "#fa9f47",
        color: "white",
      },
      onClick: function(){} // Callback after click
    }).showToast();
  }

  alertDanger = function (message) { 
    Toastify({
      text: message,
      duration: 3000,
      //destination: "https://github.com/apvarun/toastify-js",
      newWindow: true,
      //close: true,
      gravity: "top", // `top` or `bottom`
      position: "right", // `left`, `center` or `right`
      stopOnFocus: true, // Prevents dismissing of toast on hover
      style: {
        background: "#d64242",
        color: "white",
      },
      onClick: function(){} // Callback after click
    }).showToast();
  }

  alertResetAndEdit = function (message) { 
    Toastify({
      text: message,
      duration: 3000,
      //destination: "https://github.com/apvarun/toastify-js",
      newWindow: true,
      //close: true,
      gravity: "top", // `top` or `bottom`
      position: "right", // `left`, `center` or `right`
      stopOnFocus: true, // Prevents dismissing of toast on hover
      style: {
        background: "#0090e0",
        color: "white",
      },
      onClick: function(){} // Callback after click
    }).showToast();
  }

  $scope.reset = function () {
    var url = `${host}/students`;
    $scope.form = { gender: true, country: "VN" };
    $http
    .get(url)
    .then((resp) => {
      alertResetAndEdit("Reset");
    })
  };

  $scope.load_all = function () {
    var url = `${host}/students`;
    $http
      .get(url)
      .then((resp) => {
        $scope.items = resp.data;
        alertSuccess("Load dữ liệu thành công");
        console.log("Success", resp);
      })
      .catch((error) => {
        alertDanger("Load dữ liệu thất bại");
        console.log("Error", error);
      });
  };

  $scope.edit = function (email) {
    var url = `${host}/students/${email}`;
    $http
      .get(url)
      .then((resp) => {
        $scope.form = resp.data;
        alertResetAndEdit("Edit: " + resp.data.email)
        console.log("Success", resp);
      })
      .catch((error) => {
        alertDanger("Edit thất bại");
        console.log("Error", error);
      });
  };

  $scope.create = function () {
    var item = angular.copy($scope.form);
    var url = `${host}/students`;
    $http
      .post(url, item)
      .then((resp) => {
        $scope.items.push(item);
        $scope.reset();
        alertSuccess("Thêm thành công");
        console.log("Success", resp);
      })
      .catch((error) => {
        alertDanger("Thêm thất bại");
        console.log("Error", error);
      });
  };

  $scope.update = function () {

    var inputs = document.getElementsByTagName("input");
    for (let index = 0; index < inputs.length; index++) {
      var value = inputs[index].value;
      if(value == "" || value == null){
        alertWarning("Không đủ thông tin để cập nhật");
        return;
      }
    }

    var item = angular.copy($scope.form);
    var url = `${host}/students/${$scope.form.email}`;
    $http
      .put(url, item)
      .then((resp) => {
        var index = $scope.items.findIndex(item => item.email == $scope.form.email);
        $scope.items[index] = resp.data;
        alertSuccess("Cập nhật thành công");
        console.log("Success", resp);
      })
      .catch((error) => {
        alertDanger("Cập nhật thất bại");
        console.log("Error", error);
      });
  };

  $scope.delete = function (email) {
    if(email == "" || email == null){
      alertWarning("Không có email sao xóa?");
      return;
    }
    var url = `${host}/students/${email}`;
    $http
      .delete(url)
      .then((resp) => {
        var index = $scope.items.findIndex(item => item.email == email);
        $scope.items.splice(index, 1);
        $scope.reset();
        console.log("Success", resp);
      })
      .catch((error) => {
        alertDanger("Xóa thất bại");
        console.log("Error", error);
      });
  };
  //Thực hiện tải toàn bộ students
  $scope.load_all();
  $scope.reset();

});