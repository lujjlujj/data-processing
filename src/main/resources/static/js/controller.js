angular.module('loginApp', ['ng', 'ngRoute', 'ngAnimate', 'ui.bootstrap']).controller('FormCtrl', function ($scope, $timeout, $http, $uibModal, $httpParamSerializerJQLike) {

    $scope.event = {
        username: '',
        password: '',
        submit: 'login'
    }
    $scope.alertHiddenRequired = true;
    $scope.spinningHiddenRequired = true;
    $scope.disabledSigninRequired = false;

    $scope.cleanAllInput = function() {
        $scope.event = {
            username: '',
            password: '',
            submit: 'login'
        };
    }

    $scope.submitFormData = function() {
        $scope.alertHiddenRequired = 'ng-hide';
        $scope.spinningHiddenRequired = false;
        $scope.disabledSigninRequired = true;
        $http.post("/login", $httpParamSerializerJQLike($scope.event),
            {headers:{'Content-Type':'application/x-www-form-urlencoded',
                'x-requested-with':'ajax'
            }})
            .then(function successCallback(response) {
                $scope.spinningHiddenRequired = true;
                $scope.disabledSigninRequired = false;
                if (response && response.status && response.status == '200' && response.data.success == 'true') {
                    window.location = "/";
                } else {
                    $scope.showAlert("Error", "Invalid Username and Password.");
                }
            }, function errorCallback(response) {
                $scope.showAlert("Error", "Failed to login because the service is not available.");
                $scope.spinningHiddenRequired = true;
                $scope.disabledSigninRequired = false;
            });
    }
    $scope.showAlert = function (Title, TextContent) {
        $scope.alertContent = TextContent;
        $scope.dialogTitle = Title;
        $scope.textContent = TextContent;
        $scope.alertHiddenRequired = false;
        $timeout(function () {
            $scope.alertHiddenRequired = true;
        },4000);
    };


    $scope.open = function () {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: "templates/messageModal.html",
            controller: "MessageModalCtrl",
            scope: $scope
        });
    };
}).controller("MessageModalCtrl", modal);


angular.module('predictionApp', ['ng', 'ngRoute', 'ui.bootstrap', 'ngAnimate']).directive("filesInput", function () {
    return {
        require: "ngModel",
        link: function postLink(scope, elem, attrs, ngModel) {
            elem.on("change", function (e) {
                var files = elem[0].files;
                ngModel.$setViewValue(files);
            })
        }
    }
}).controller('predictionController', function ($scope, $timeout, $http, $uibModal, $log) {

    $scope.alertHiddenRequired = true;
    $scope.successMsgHiddenRequired = true;
    $scope.showInvalidDataRecordsRequired = true;
    $scope.showPredictionResultRequired = true;
    $scope.continueButtonRequired = true;
    $scope.spinningHiddenRequired = true;
    $scope.disabledPredictRequired = false;

    $http.get("/user")
        .then(function successCallback(response) {
            if (response && response.status && response.status == '200' && response.data != null) {
                $scope.user = response.data;
            }
        }, function errorCallback(response) {
        });

    $scope.logout = function () {

        $http.post("/logout").then(function successCallback(response) {
            if (response && response.status && response.status == '200' && response.data.success == 'true') {
                window.location = '/login';
            } else {
                $scope.showAlert("Error", "Failed to logout.");
            }
        }, function errorCallback(response) {
            $scope.showAlert("Error", "Failed to logout.");
        });
    };

    $scope.confirmPrediction = function () {
        $scope.alertHiddenRequired = true;
        $scope.spinningHiddenRequired = false;
        $scope.disabledPredictRequired = true;
        $http.post("/confirmPrediction").then(function successCallback(response) {
            $scope.spinningHiddenRequired = true;
            $scope.disabledPredictRequired = false;
            if (response && response.status && response.status == '200' && response.data.code == '0') {
                $scope.showSuccessMsg("Message", "The prediction is performed successfully, please review the result.");
                $scope.predictionResult = response.data.returnValue;
                $scope.calculatePredictionResultPagination();
            } else if (response && response.status && response.status == '200' && response.data.code == '2') {
                $scope.showAlert("Error", "The input file is invalid, please double check and upload the file again.");
            } else {
                $scope.showAlert("Error", "Failed to perform prediction due to internal error, please contact system admin.");
            }
        }, function errorCallback(response) {
            $scope.spinningHiddenRequired = true;
            $scope.disabledPredictRequired = false;
            $scope.showAlert("Error", "Failed to perform prediction due to internal error, please contact system admin.");
        });
    };

    $scope.upload = function () {
        $scope.showInvalidDataRecordsRequired = true;
        $scope.continueButtonRequired = true;
        $scope.showPredictionResultRequired = true;
        $scope.successMsgHiddenRequired = true;
        $scope.spinningHiddenRequired = false;
        $scope.disabledPredictRequired = true;
        $scope.test = true;
        if ($scope.fileArray == null || $scope.fileArray == undefined || $scope.fileArray.length == 0) {
            $scope.showAlert("Error", "Please choose file for prediction.");
            $scope.spinningHiddenRequired = true;
            $scope.disabledPredictRequired = false;
            return;
        }
        var formdata = new FormData();
        formdata.append("file", $scope.fileArray[0]);
        $http.post("/predict", formdata, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function successCallback(response) {
            $scope.spinningHiddenRequired = true;
            $scope.disabledPredictRequired = false;
            if (response && response.status && response.status == '200' && response.data.code == '0') {
                $scope.showSuccessMsg("Message", "The prediction is performed successfully, please review the result.");
                $scope.predictionResult = response.data.returnValue;
                $scope.calculatePredictionResultPagination();
            } else if (response && response.status && response.status == '200' && response.data.code == '2') {
                $scope.showAlert("Error", "The input file is invalid, please double check.");
            } else if (response && response.status && response.status == '200' && response.data.code == '3') {
                $scope.showAlert("Error", "Some data fields are empty, please click 'Continue' button if you still want to continue.");
                $scope.validationDatas = response.data.returnValue;
                $scope.calculateValidationDatasPagination();
            } else {
                $scope.showAlert("Error", "Failed to perform prediction due to internal error, please contact system admin.");
            }
        }, function errorCallback(response) {
            $scope.spinningHiddenRequired = true;
            $scope.disabledPredictRequired = false;
            $scope.showAlert("Error", "Failed to perform prediction due to internal error, please contact system admin.");
        });
    };

    $scope.calculateValidationDatasPagination = function() {
        $scope.showInvalidDataRecordsRequired = false;
        $scope.continueButtonRequired = false;
        $scope.vdTotalItems = $scope.validationDatas.length;
        var modulus = $scope.validationDatas.length % 10;
        $scope.vdPageNumber = ($scope.validationDatas.length - modulus) / 10;
        if (modulus > 0) {
            $scope.vdPageNumber = $scope.vdPageNumber + 1;
        }
        $scope.vdCurrentPageNumber = 1;
    }

    $scope.calculatePredictionResultPagination = function() {
        $scope.continueButtonRequired = true;
        $scope.showPredictionResultRequired = false;
        $scope.prTotalItems = $scope.predictionResult.length;
        var modulus = $scope.predictionResult.length % 10;
        $scope.prPageNumber = ($scope.predictionResult.length - modulus) / 10;
        if (modulus > 0) {
            $scope.prPageNumber = $scope.prPageNumber + 1;
        }
        $scope.prCurrentPageNumber = 1;
    }

    $scope.trigger = function (type) {
        $scope.alertHiddenRequired = true;
        $scope.successMsgHiddenRequired = true;
        var format = {};
        format.type = type;
        $http.post("/trigger", format).then(function successCallback(response) {
            if (response && response.status && response.status == '200' && response.data.code == '0') {
                $scope.showSuccessMsg("Message", "The job is triggered successfully.");
            } else {
                $scope.showAlert("Error", "Failed to trigger.");
            }
        }, function errorCallback(response) {
            $scope.showAlert("Error", "Failed to trigger.");
        });
    };

    $scope.getTempValidationDatas = function(vdCurrentPageNumber) {
        var from = ($scope.vdCurrentPageNumber - 1) * 10;
        var to = from + 10;
        $scope.tempValidationDatas = $scope.validationDatas.slice(from, to);
        return $scope.tempValidationDatas;
    };

    $scope.getTempPredictionResult = function(prCurrentPageNumber) {
        var from = ($scope.prCurrentPageNumber - 1) * 10;
        var to = from + 10;
        $scope.tempPredictionResult = $scope.predictionResult.slice(from, to);
        return $scope.tempPredictionResult;
    };

    $scope.logout = function () {

        $http.post("/logout").then(function successCallback(response) {
            if (response && response.status && response.status == '200' && response.data.success == 'true') {
                window.location = "/login";
            } else {
                $scope.showAlert("Error", "Failed to logout.");
            }
        }, function errorCallback(response) {
            $scope.showAlert("Error", "Failed to logout.");
        });
    };

    $scope.showAlert = function (Title, TextContent) {
        $scope.alertContent = TextContent;
        $scope.dialogTitle = Title;
        $scope.textContent = TextContent;
        $scope.alertHiddenRequired = false;
        $timeout(function () {
            $scope.alertHiddenRequired = true;
        }, 10000);
    };

    $scope.showSuccessMsg = function (Title, TextContent) {
        $scope.successMsgContent = TextContent;
        $scope.successMsgHiddenRequired = false;
        $scope.dialogTitle = Title;
        $scope.textContent = TextContent;
        $timeout(function () {
            $scope.successMsgHiddenRequired = true;
        }, 10000);
    };

    $scope.open = function () {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: "templates/messageModal.html",
            controller: "MessageModalCtrl",
            scope: $scope
        });
    };
}).controller("MessageModalCtrl", modal);