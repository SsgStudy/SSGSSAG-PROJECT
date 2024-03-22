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
    $("#order-status").val(($('#order-status option[selected]').val()));
    $("#order-read-search-form").find("input[type=text]").val("");
    getNowDate();
    $(".order-master-tbody").empty().append(
        `
            <tr class="odd">
                <td valign="top" colspan="8" class="dataTables_empty">No data available in table</td>
            </tr>
        `
    );
}

// 검색폼
$(".input-supplier").click(function() {
    console.log("실행?")
    $.ajax({
        url: '/incoming/supplier',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            let tableBody = $("#purchaserSearchInputBox .table-responsive tbody");
            tableBody.empty();
            $.each(data, function(index, item) {
                let row = "<tr>" +
                    "<td>" + (index + 1) + "</td>" +
                    "<td>" + item.vincomingProductSupplierNm + "</td>" +
                    "</tr>";
                tableBody.append(row);
            });
        },
        error: function(xhr, status, error) {
            alert("An error occurred: " + error);
        }
    });
});

$("#purchaserSearchInputBox .table-responsive tbody").on('click', 'tr', function() {
    let supplierName = $(this).find('td:nth-child(2)').text();
    $('.input-supplier').val(supplierName);
    $('#purchaserSearchInputBox').modal('hide');
});

$('.input-whsearch').click(function() {
    let formData = {
        name: $("#warehouseNameInput").val(),
        location: $("#warehouseLocationSelect").val(),
        type: $("#warehouseTypeSelect").val()
    };

    $.ajax({
        type: "POST",
        url: "/warehouse/search",
        data: $.param(formData),
        contentType: 'application/x-www-form-urlencoded',
        success: function(data) {
            let tableBody = $('#warehouseSearchInputBox .table-responsive tbody');
            tableBody.empty();
            $.each(data, function(i, warehouse) {
                let row = "<tr>" +
                    "<td>" + (i + 1) + "</td>" +
                    "<td>" + warehouse.swarehouseType + "</td>" +
                    "<td>" + warehouse.vwarehouseCd + "</td>" +
                    "<td>" + warehouse.vwarehouseNm + "</td>" +
                    "</tr>";
                tableBody.append(row);
            });
        },
        error: function(error) {
            console.error("Error: ", error);
        }
    });
});

$('#warehouseSearchInputBox .table-responsive tbody').on('click', 'tr', function() {
    let warehouseCode = $(this).find('td:nth-child(3)').text();
    $('.input-whsearch').val(warehouseCode);
    $('#warehouseSearchInputBox').modal('hide');
});


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
