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
            $.each(resp, function(index, order) {
                var currentIndex = $(".order-single-tbody tr").length + 1;

                $(".order-single-tbody").append(
                    `<tr>
                    <th>${currentIndex}</th>
                    <td id="order-single-tr-order-created-date-${currentIndex}">${order.dtOrderCreatedDate}</td>
                    <td id="order-single-tr-order-seq-${currentIndex}">${order.pkOrderSeq}</td>
                    <td id="order-single-tr-order-type-${currentIndex}">${order.vOrderType}</td>
                    <td id="order-single-tr-product-supplier-nm-${currentIndex}">${order.vIncomingProductSupplierNm}</td>
                    <td id="order-single-tr-warehouse-cd-${currentIndex}">${order.vWarehouseCd}</td>
                    <td id="order-single-tr-delivery-date-${currentIndex}">${order.dtDeliveryDate}</td>
                    <td id="order-single-tr-order-status-${currentIndex}">${order.vOrderStatus}</td>
                    <td id="order-single-tr-order-complete-date-${currentIndex}">${order.dtOrderCompletionDate}</td>
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

