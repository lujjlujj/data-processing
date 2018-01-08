function modal($uibModalInstance, $scope) {
    $scope.close = function () {
        $uibModalInstance.close();
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss();
    };
}