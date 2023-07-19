app = angular.module("app", []);
app.controller("ctrl", function($scope, $http){
    var url  = `http://localhost:8080/rest/files/images`;
    $scope.filenames = [];
    $scope.url = function(filename){
        return `${url}/${filename}`;
    }
    $scope.list = function () { 
        $http.get(url).then(resp => {
            $scope.filesnames = resp.data;
        }).catch(error => {
            console.log("Error", error);
        })
    }
    $scope.upload = function (files) {
        var form = new FormData();
        for(var i = 0; i < files.length; i++){
            form.append("files", files[i]);
            //alert(files[i]);
        }

        $http.post(url, form, {
            transformRequest: angular.identity,
            headers: {"Content-Type": undefined},
        }).then(resp=>{
            alert(resp.data);
            $scope.filenames.push(...resp.data);
        }).catch(error=>{
            console.log("Errors", error);
        })
    }
    $scope.delete = function (filename) {
        $http.delete(`${url}/${filename}`).then(resp => {
            let i = $scope.filenames.findIndex(name => name == filename);
            $scope.filenames.splice(i, 1);
        }).catch(error => {
            console.log("Error", error);
        })
    }
    $scope.list();
})