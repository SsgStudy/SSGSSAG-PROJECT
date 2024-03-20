var orderSearch = {
    "pkoOrderSeq": null,
    "vOrderStatus": null,
    "vIncomingProductSupplierNm": "Samsung Electronics",
    "vOrderType": null,
    "dtOrderCreatedDate": null,
    "dtDeliveryDate": null,
    "dtOrderCompletionDate": null,
    "vWarehouseCd": "KR-SEO-02",
    "vProductCd": "880-5678-0523"
};

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

$("#order-register-new-btn").click(function () {

});

$("#order-register-save-ok-btn").off('click').click(function () {

    if (!checkEmptyOrderForm()) {
        console.log("입력되지 않은 값이 있습니다.")
        return;
    }

    $.ajax({
        url: '/order/register/order-seq',
        type: 'GET',
        success: function (data) {
            let orderSeq = data.orderSeq;
            $("#order-seq").val(orderSeq);
            $("#order-status").val("미확정");
            saveOrderForm();
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
});


$("#order-register-remove-ok-btn").click(function () {
    console.log('초기화')
    $("#order-register-form").find("input[type=text], select").val("");
});

// 발주 상세 내역 추가

function createOrderDetailForm() {
    console.log("click");
    $.ajax({
        url: '/order/register/detail',
        type: 'POST',
        contentType:'application/json',
        data:
            JSON.stringify(orderSearch),
        success: function (resp) {
            var currentIndex;
            if ($(".order-detail-tbody tr").isEmpty)
                currentIndex = 1;
            currentIndex = $(".order-detail-tbody tr").length + 1;

            // 새로운 행을 테이블에 추가
            $(".order-detail-tbody").append(
                `<tr>
                    <th>${currentIndex}</th>
                    <td><input type="checkbox" id="order-tr-checked-${currentIndex}" /></td>
                    <td id="order-tr-product-cd-${currentIndex}">${resp.vProductCd}</td>
                    <td id="order-tr-product-nm-${currentIndex}">${resp.vProductNm}</td>
                    <td>Y</td>
                    <td>Y</td>
                    <td id="order-tr-inventory-cnt-${currentIndex}">${resp.nInventoryCnt}</td>
                    <td><div class="col-sm-3">
                            <div class="input-group">
                                    <input type="number" id="order-tr-order-cnt-${currentIndex}"">
                            </div>
                        </div>
                    </td>
                    <td id="order-tr-product-price-${currentIndex}">${resp.nProductPrice}</td>
                    <td><div class="col-sm-3">
                            <div class="input-group">
                                    <input type="number" id="order-tr-total-price-${currentIndex}">
                            </div>
                        </div>
                    </td>
                </tr>`
            );
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
}

$("#order-register-confirm-ok-btn").click(function () {
// ajax
// 발주 DB 삽입
});

$("#order-read-btn").click(function () {
// ajax
// 기간에 해당하는 발주 리스트 조회
});


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

function saveOrderForm() {
    orderSearch.dtOrderCreatedDate = dateFormatting($("#order-created-date").val());
    orderSearch.pkoOrderSeq = $("#order-seq").val();
    orderSearch.vIncomingProductSupplierNm = $("#incoming-product-supplier-nm").val();
    orderSearch.vOrderStatus = $("#order-status").val();
    orderSearch.vWarehouseCd = $("#warehouse-cd").val();
    orderSearch.vOrderType = $("#order-type").val();

    console.log(orderSearch);
}

function dateFormatting(dateString) {
    var date = new Date(dateString);

    var formattedDate = new Intl.DateTimeFormat('en-CA', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
    }).format(date);

    return formattedDate;
}

function calculateOrderTotalPrice(currentIndex) {
    let price = $("order-tr-product-price-"+currentIndex).val();
    let cnt = $("order-tr-order-cnt-"+currentIndex).val();
    return price * cnt;
}