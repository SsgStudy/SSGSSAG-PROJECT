$(document).ready(function() {
    allDeactivateForm();
    $('.alert').hide();
});

// 전역 변수
var orderSearch = {
    "vIncomingProductSupplierNm": null,
    "vWarehouseCd": null,
    "vProductCd": null
};

var order = {
    "pkOrderSeq": null,
    "vOrderStatus": null,
    "vIncomingProductSupplierNm": null,
    "vOrderType": null,
    "dtOrderCreatedDate": null,
    "dtDeliveryDate": "2024-03-21T10:00:00",
};

var orderDetails= []

var saveStatus = {
    "order" : false,
    "orderDetail" : false
}

// 신규
function clickNewBtn() {
    $('#order-register-reset-btn').prop('disabled', false);
    $('.general-button button').prop('disabled', false);

    newOrderForm();
    createOrderSeq();
    activateMasterForm();
}

function createOrderSeq() {
    $.ajax({
        url: '/order/register/order-seq',
        type: 'GET',
        success: function (data) {
            let orderSeq = data.orderSeq;
            $("#order-seq").val(orderSeq);
            $("#order-status").val("미확정");
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
}

function newOrderForm() {
    $("#order-created-date").val(getCurrentDateFormatted());
    $("#order-seq").val('');
    $("#incoming-product-supplier-nm").val('');
    $("#order-status").val('');
    $("#warehouse-cd").val('');
    $("#order-type").val(($('#order-type option[selected]').val()));
}

function activateMasterForm() {
    $('#order-register-form button, .container-fluid input, .container-fluid select')
        .prop('disabled', false);
}

// 초기화
function allDeactivateForm() {
    $('.container-fluid button, .container-fluid input, .container-fluid select')
        .not('#order-register-new-btn, .modal button').prop('disabled', true);
}


// 등록 폼

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


// 발주 - 저장

function clickOrderMasterSave() {
    if (!checkInputFields()) {
        $('#order-master-save-btn').removeAttr('data-target');
        $('.alert-danger a').text('입력되지 않은 값이 있습니다.');
        $('.alert-danger').show();
        $('.alert-danger').delay(2000).fadeOut();
        // $('.alert-danger a').text('');
    }
    else {
        $('#order-master-save-btn').attr('data-target','#exampleModalCenter');
        saveStatus.order = true;
    }
}
function orderRegisterSave() {
    $('.alert-success a').text('발주 마스터가 저장되었습니다.');
    $('.alert-success').show();
    $('.alert-success').delay(2000).fadeOut();

    saveOrderForm();
    activateOrderSingleForm();
}

function activateOrderSingleForm() {
    $('#order-register-order-single-btn-wrap button, #order-register-order-single-btn-wrap button input')
        .prop('disabled', false);
}


function checkEmptyOrderForm() {
    if ($("#order-created-date").val() === "") {
        console.log("날짜 입력하세요");
        return false;
    }

    if ($("#incoming-product-supplier-nm").val() === "") {
        console.log("매입거래처를 선택하세요");
        return false;
    }

    if ($("#warehouse-cd").val() === "") {
        console.log("창고를 선택하세요");
        return false;
    }
    return true;
}

function checkInputFields() {
    let allFilled = true;
    $('.order-master-form input,  .order-master-form select').each(function() {
        console.log("test ", $(this).val())
        if ($(this).val().trim() === ''
            || $(this).val().trim() === '선택'
            || $(this).val().trim() === 'dd/mm/yyyy') {
            allFilled = false;
            // return false;
        }
    });

    return allFilled;
}


function saveOrderForm() {
    order.dtOrderCreatedDate = dateFormatting($("#order-created-date").val());
    order.pkOrderSeq = $("#order-seq").val();
    order.vIncomingProductSupplierNm = $("#incoming-product-supplier-nm").val();
    order.vOrderStatus = $("#order-status").val();
    order.vWarehouseCd = $("#warehouse-cd").val();
    order.vOrderType = $("#order-type").val();

    console.log(order);
}

// 발주 - 삭제
$("#order-register-remove-btn").click(function () {
    swal(
        {
            title: "Confirm",
            text: "삭제하시겠습니까?",
            type: "info",
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true,
        },
        function () {
            setTimeout(function () {
                swal("저장되었습니다.");
            }, 2000);
        }
    );
});


function orderRegisterReset() {
    $("#order-created-date").val('dd/mm/yyyy');
    $("#order-seq").val('');
    $("#incoming-product-supplier-nm").val('');
    $("#order-status").val('');
    $("#warehouse-cd").val('');
    $("#order-type").val(($('#order-type option[selected]').val()));
    $('.order-detail-tbody').empty().append(`
        <tr>
            <th>-</th>
            <th>-</th>
            <th>-</th>
            <th>-</th>
            <th>-</th>
            <th>-</th>
            <th>-</th>
            <th>-</th>
        </tr>
    `)
    $('#input-product-cd').val('');
    allDeactivateForm();
}

function orderObjectDelete() {
    order = {
        "pkOrderSeq": null,
        "vOrderStatus": null,
        "vIncomingProductSupplierNm": null,
        "vOrderType": null,
        "dtOrderCreatedDate": null,
        "dtDeliveryDate": null,
    }

    orderDetails= [];
}

// 발주 상세 - 추가
function createOrderDetailForm() {
    orderSearch.vIncomingProductSupplierNm = order.vIncomingProductSupplierNm;
    orderSearch.vProductCd = $("#input-product-cd").val();
    orderSearch.vWarehouseCd = order.vWarehouseCd;

    $.ajax({
        url: '/order/register/detail',
        type: 'POST',
        contentType:'application/json',
        data:
            JSON.stringify(orderSearch),
        success: function (resp) {
            $("#default-tr").remove();

            let idx = $(".order-detail-tbody tr").length + 1;

            $(".order-detail-tbody").append(
                `<tr>
                    <th>${idx}</th>
                    <td><input type="checkbox" id="order-tr-checked-${idx}" /></td>
                    <td id="order-tr-product-cd-${idx}">${resp.vProductCd}</td>
                    <td id="order-tr-product-nm-${idx}">${resp.vProductNm}</td>
                    <td id="order-tr-inventory-cnt-${idx}">${resp.nInventoryCnt}</td>
                    <td><div class="col-sm-3">
                            <div class="input-group">
                                    <input type="number" id="order-tr-order-cnt-${idx}"">
                            </div>
                        </div>
                    </td>
                    <td id="order-tr-product-price-${idx}">${resp.nProductPrice}</td>
                    <td><div class="col-sm-3">
                            <div class="input-group">
                                    <input type="number" id="order-tr-total-price-${idx}">
                            </div>
                        </div>
                    </td>
                </tr>`
            );
        },
        error: function (xhr, status, error) {
            if (xhr.status === 422) {
                console.log(xhr.responseText);
                showAlertDanger(xhr.responseText);
            }
        }
    });
}


$('body').on('input', '[id^=order-tr-order-cnt-]', function() {
    let currentIndex = this.id.match(/\d+$/)[0];
    let totalPrice = calculateOrderTotalPrice(currentIndex);
    console.log(totalPrice)

    $("#order-tr-total-price-" + currentIndex).val(totalPrice);
});

// 발주 상세 - 저장
function insertOrderAndOrderDetail() {
    saveOrderDetailForm();

    console.log(saveStatus);
    if (saveStatus.order && saveStatus.orderDetail) {
        console.log(order);
        console.log(orderDetails);

        $.ajax({
            url: '/order/register',
            type: 'POST',
            contentType: 'application/json', // 요청의 컨텐츠 타입
            data: JSON.stringify({order: order, orderDetails: orderDetails}), // 데이터를 JSON 문자열로 변환
            success: function (resp) {
                // 성공 시 로직
            },
            error: function (error) {
                console.log('Error:', error);
            }
        });
    }
    else {
        console.log("발주 or 발주 상세 값이 비었습니다.");
    }
}

function saveOrderDetailForm() {
    $('.order-detail-tbody input[type="checkbox"]:checked').each(function() {
        var index = this.id.match(/\d+$/)[0];
        var productCd = $(`#order-tr-product-cd-${index}`).text();
        var orderCnt = +$(`#order-tr-order-cnt-${index}`).val();

        console.log("orderCnt " + orderCnt);

        if (orderCnt.isEmpty || orderCnt===0) {
            console.log("발주 수량 입력");
            saveStatus.orderDetail = false;
            return
        }

        var orderDetail = {
            nOrderCnt: orderCnt,
            vOrderStatus: "미입고",
            vProductCd: productCd,
            pkOrderSeq: order.pkOrderSeq,
            vWarehouseCd: order.vWarehouseCd
        }
        orderDetails.push(orderDetail);
        console.log(orderDetail);
    });

    if (orderDetails.length < 1)
        saveStatus.orderDetail = false;

    else
        saveStatus.orderDetail = true;
}

// 발주 삭제 - 삭제


// 기타
function dateFormatting(dateString) {
    var date = new Date(dateString);

    // 한국 시간대(KST, UTC+9) 설정
    var kstOffset = 9 * 60;
    var localDate = new Date(date.getTime() + kstOffset * 60000);

    var formattedDate = localDate.toISOString().replace('Z', '+09:00').substring(0, 19);

    return formattedDate;
}


function calculateOrderTotalPrice(currentIndex) {
    let cnt = +$(`#order-tr-order-cnt-${currentIndex}`).val();
    let price = +$(`#order-tr-product-price-${currentIndex}`).text();
    return price * cnt;
}

function getCurrentDateFormatted() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    return mm + '/' + dd + '/' + yyyy;
}

function showAlertDanger(text) {
    $('.alert-danger a').text(text);
    $('.alert-danger').show();
    $('.alert-danger').delay(2000).fadeOut();
}
