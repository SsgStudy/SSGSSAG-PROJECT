$(document).ready(function() {
    getNowDate();

});

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
    let period = $("#order-period").val().split(' - ');
    orderSearchForm.startDate = convertDateFormat(period[0]);
    orderSearchForm.endDate = convertDateFormat(period[1]);
    orderSearchForm.vIncomingProductSupplierNm = $("#incoming-product-supplier-nm").val();
    orderSearchForm.vWarehouseCd = $("#warehouse-cd").val();

    let orderStatus = $("#order-status").val();
    if (orderStatus!=="선택")
        orderSearchForm.vOrderStatus = orderStatus;
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
                let currentIndex = index + 1;
                let orderCompletionDate = order.dtOrderCompletionDate;
                if (orderCompletionDate===null)
                    orderCompletionDate = '-';
                else {
                    orderCompletionDate = orderCompletionDate.replace('T', ' ');
                }

                let deliveryDate = order.dtDeliveryDate;
                if (deliveryDate===null)
                    deliveryDate = '-';
                else {
                    deliveryDate = deliveryDate.replace('T', ' ');
                }

                tableBody.append(
                    `<tr id="order-single-tr-${currentIndex}">
                        <th>${currentIndex}</th>
                        <td>${order.dtOrderCreatedDate}</td>
                        <td>${order.pkOrderSeq}</td>
                        <td>${order.vOrderType}</td>
                        <td>${order.vIncomingProductSupplierNm}</td>
                        <td>${order.vWarehouseCd}</td>
                        <td>${deliveryDate}</td>
                        <td>${order.vOrderStatus}</td>
                        <td>${orderCompletionDate}</td>
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

function getNowDate() {
    let now = new Date(); // 현재 날짜 및 시간
    let startDate = formatDate(new Date(now.getFullYear(), now.getMonth(), 1));
    let endDate = formatDate(new Date(now.getFullYear(), now.getMonth() + 1, 0));

    $('#order-period').val(startDate + ' - ' + endDate);
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [month, day, year].join('/');
}
