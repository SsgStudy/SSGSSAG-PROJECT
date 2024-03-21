// 전역 변수
var orderSearchForm = {
    "vIncomingProductSupplierNm": null,
    "vWarehouseCd": null,
    "vOrderStatus": null,
    "startDate": null,
    "endDate": null
};

// 초기화
function orderReadPageReset() {
    console.log('초기화')
    $("#order-read-search-form").find("input[type=text], select").val("");
    $(".order-single-tbody").empty();
}

// 조회
function searchForm() {
    let period = $("#order-read-date").val().split(' - ');
    orderSearchForm.startDate = convertDateFormat(period[0]);
    orderSearchForm.endDate = convertDateFormat(period[1]);
    orderSearchForm.vIncomingProductSupplierNm = $("#incoming-product-supplier-nm").val();
    orderSearchForm.vWarehouseCd = $("#warehouse-cd").val();

    let orderStatus = $("#order-status").val();
    if (orderStatus!=="선택")
        orderSearchForm.vOrderStatus = orderStatus;

    console.log("검색 폼 " + JSON.stringify(orderSearchForm));
}

function readOrder() {
    searchForm();

    $.ajax({
        url: '/order/read',
        type: 'POST',
        contentType:'application/json',
        data:
            JSON.stringify(orderSearchForm),
        success: function (resp) {
            let tableBody = $('.zero-configuration tbody');
            tableBody.empty();

            $.each(resp, function(index, order) {
                var currentIndex = index + 1;

                tableBody.append(
                    `<tr id="order-single-tr-${currentIndex}">
                        <th>${currentIndex}</th>
                        <td>${order.dtOrderCreatedDate}</td>
                        <td>${order.pkOrderSeq}</td>
                        <td>${order.vOrderType}</td>
                        <td>${order.vIncomingProductSupplierNm}</td>
                        <td>${order.vWarehouseCd}</td>
                        <td>${order.dtDeliveryDate}</td>
                        <td>${order.vOrderStatus}</td>
                        <td>${order.dtOrderCompletionDate}</td>
                    </tr>`
                );
            });
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
}

// 기타
function convertDateFormat(dateStr) {
    var parts = dateStr.split('/');
    return parts[2] + '-' + parts[0] + '-' + parts[1];
}

