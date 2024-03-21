// 전역 변수
var orderSearchForm = {
    "vIncomingProductSupplierNm": null,
    "vWarehouseCd": null,
    "vOrderStatus": null,
    "startDate": null,
    "endDate": null
};


// 확정
function orderConfirm() {
    let orderSeq = [];

    $('.order-master-tbody input[type="checkbox"]:checked').each(function() {
        let index = this.id.match(/\d+$/)[0];

        if ($(`#order-td-order-status-${index}`) !== '확정') {
            orderSeq.push(+$(`#order-td-order-seq-${index}`).text());
        }
    });

    let dataToSend = {
        orderSeq: orderSeq
    };

    $.ajax({
        url: '/order/confirm',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(dataToSend),
        success: function(response) {
            console.log('Success:', response);
        },
        error: function(error) {
            console.log('Error:', error);
        }
    });
}


// 초기화
function orderReadPageReset() {
    console.log('초기화')
    $("#order-confirm-search-form").find("input[type=text], select").val("");
    $(".order-single-tbody").empty();
    $(".order-master-tbody").empty();
}

// 조회
function searchForm() {
    let period = $("#order-period").val().split(' - ');
    orderSearchForm.startDate = convertDateFormat(period[0]);
    orderSearchForm.endDate = convertDateFormat(period[1]);
    orderSearchForm.vIncomingProductSupplierNm = $("#incoming-product-supplier-nm").val();
    orderSearchForm.vWarehouseCd = $("#warehouse-cd").val();

    let orderStatus = $("#order-status").val();
    if (orderStatus !== "선택")
        orderSearchForm.vOrderStatus = orderStatus;
}

function getMasterOrderList() {
    searchForm();

    $.ajax({
        url: '/order/read',
        type: 'POST',
        contentType: 'application/json',
        data:
            JSON.stringify(orderSearchForm),
        success: function (resp) {
            let tableBody = $('.zero-configuration tbody');
            tableBody.empty();

            $.each(resp, function (index, resp) {
                var currentIndex = index + 1;
                tableBody.append(
                    `<tr onclick="getOrderSingleList(${resp.pkOrderSeq})">
                        <th>${currentIndex}</th>
                        <td><input type="checkbox" id="order-master-td-checked-${currentIndex}" /></td>
                        <td id="order-master-td-created-date-${currentIndex}">${resp.dtOrderCreatedDate}</td>
                        <td id="order-td-order-seq-${currentIndex}">${resp.pkOrderSeq}</td>
                        <td id="order-td-order-status-${currentIndex}">${resp.vOrderStatus}</td>
                        <td id="order-td-order-type-${currentIndex}">${resp.vOrderType}</td>
                        <td id="order-td-supplier-nm-${currentIndex}">${resp.vIncomingProductSupplierNm}</td>
                        <td id="order-td-order-warehouse-nm-${currentIndex}">${resp.vWarehouseNm}</td>
                    </tr>`
                );
            });
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
}

// 발주 상세

function getOrderSingleList(orderSeq) {

    $.ajax({
        url: `/order/single?order-seq=${orderSeq}`,
        type: "GET",
        success: function (resp) {
            $('.order-single-tbody').empty();
            $.each(resp, function (idx, orderSingle) {
                $('.order-single-tbody').append(
                    `<tr>
                        <th>${idx+1}</th>
                        <td>${orderSingle.vProductCd}</td>
                        <td>${orderSingle.vProductNm}</td>
                        <td>${orderSingle.nInventoryCnt}</td>
                        <td>${orderSingle.nOrderCnt}</td>
                        <td>${orderSingle.nProductPrice}</td>
                        <td>${orderSingle.orderTotalPrice}</td>
                    </tr>`
                );
            })
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